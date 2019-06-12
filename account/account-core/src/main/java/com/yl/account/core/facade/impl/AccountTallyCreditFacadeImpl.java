/**
 * 
 */
package com.yl.account.core.facade.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.DateUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.bean.TallyVoucher;
import com.yl.account.bean.TradeVoucher;
import com.yl.account.core.ExceptionMessages;
import com.yl.account.core.exception.BussinessException;
import com.yl.account.core.exception.BussinessRuntimeException;
import com.yl.account.core.facade.AccountTallyFacade;
import com.yl.account.core.service.AccountBehaviorService;
import com.yl.account.core.service.AccountChangeDetailService;
import com.yl.account.core.service.AccountFreezeBalanceDetailService;
import com.yl.account.core.service.AccountFreezeDetailService;
import com.yl.account.core.service.AccountRecordedDetailService;
import com.yl.account.core.service.AccountService;
import com.yl.account.core.service.AccountTransitBalanceDetailService;
import com.yl.account.core.service.AccountTransitSummaryService;
import com.yl.account.core.utils.AccountFundSymbolUtils;
import com.yl.account.core.utils.MQsendMessage;
import com.yl.account.enums.AccountStatus;
import com.yl.account.enums.FreezeHandleType;
import com.yl.account.enums.FreezeStatus;
import com.yl.account.model.Account;
import com.yl.account.model.AccountBehavior;
import com.yl.account.model.AccountFreezeDetail;
import com.yl.account.model.AccountTransitBalanceDetail;
import com.yl.account.model.AccountTransitSummary;
import com.yl.mq.producer.client.ProducerClient;
import com.yl.mq.producer.client.ProducerMessage;

/**
 * 账务入账处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月9日
 * @version V1.0.0
 */
@Component("accountCreditFacade")
public class AccountTallyCreditFacadeImpl implements AccountTallyFacade {

	private static Logger logger = LoggerFactory.getLogger(AccountTallyCreditFacadeImpl.class);

	@Resource
	private AccountService accountService;
	@Resource
	private AccountTransitBalanceDetailService accountTransitBalanceDetailService;
	@Resource
	private AccountTransitSummaryService accountTransitSummaryService;
	@Resource
	private AccountRecordedDetailService accountRecordedDetailService;
	@Resource
	private AccountBehaviorService accountBehaviorService;
	@Resource
	private AccountChangeDetailService accountChangeDetailService;
	@Resource
	private AccountFreezeDetailService accountFreezeDetailService;
	@Resource
	private AccountFreezeBalanceDetailService accountFreezeBalanceDetailService;
	@Resource
	MQsendMessage mqSendMessage;
	@Resource
	ProducerClient producerClient;
	/*
	 * (non-Javadoc)
	 * @see com.yl.account.core.facade.AccountTallyFacade#tally(com.yl.account.api.bean.AccountBussinessInterfaceBean)
	 */
	@Override
	public void tally(TradeVoucher tradeVoucher, AccountBussinessInterfaceBean accountBussinessInterfaceBean) {
		try {
			TallyVoucher tallyVoucher = (TallyVoucher) tradeVoucher;

			logger.info("账务系统接收业务系统请求[入账]指令：{}", JsonUtils.toJsonString(tallyVoucher));

			Account account = accountService.findAccountByCode(tallyVoucher.getAccountNo());
			if (account == null) throw new BussinessException(ExceptionMessages.ACCOUNT_NOT_EXISTS);

			if (account.getStatus().equals(AccountStatus.FREEZE) || account.getStatus().equals(AccountStatus.END_IN)) throw new BussinessException(
					ExceptionMessages.ACCOUNT_STATUS_NOT_NORMAL);

			if (accountRecordedDetailService.findBy(account.getCode(), tallyVoucher.getTransOrder(), accountBussinessInterfaceBean.getSystemCode(),
					accountBussinessInterfaceBean.getBussinessCode(), accountBussinessInterfaceBean.getSystemFlowId())) return;

			if (accountBussinessInterfaceBean.getBussinessCode().equals("TRANSFER") || accountBussinessInterfaceBean.getBussinessCode().equals("ADJUST")) accountChangeDetailService
					.create(accountBussinessInterfaceBean.getBussinessCode(), new Date(), account.getCode(), account.getType(), account.getStatus(),
							accountBussinessInterfaceBean.getSystemCode(), tallyVoucher.getTransOrder(), account.getUserNo(), account.getUserRole(),
							account.getBalance(), account.getFreezeBalance(), account.getTransitBalance(), tallyVoucher.getSymbol(), tallyVoucher.getAmount(),
							accountBussinessInterfaceBean.getOperator(), accountBussinessInterfaceBean.getRemark());

			double creditAmount = AmountUtils.add(tallyVoucher.getAmount(), tallyVoucher.getFeeSymbol() == null || tallyVoucher.getFee() == null ? 0
					: AccountFundSymbolUtils.convertToRealAmount(tallyVoucher.getFeeSymbol(), tallyVoucher.getFee()));

			if (creditAmount < 0) throw new BussinessException(ExceptionMessages.ACCOUNT_CREDIT_AMOUNT_NOT_NEGATIVE);

			Date waitAccountDate = tallyVoucher.getWaitAccountDate() == null ? tallyVoucher.getWaitAccountDate() : DateUtils.getMinTime(tallyVoucher
					.getWaitAccountDate());

			if (waitAccountDate == null || waitAccountDate.compareTo(new Date()) > 0) {

				AccountTransitBalanceDetail accountTransitBalanceDetail = accountTransitBalanceDetailService.create(account.getCode(), tallyVoucher.getCurrency(),
						tallyVoucher.getTransOrder(), tallyVoucher.getTransDate(), accountBussinessInterfaceBean.getSystemCode(), tallyVoucher.getAmount(),
						tallyVoucher.getSymbol(), tallyVoucher.getFee(), tallyVoucher.getFeeSymbol(), accountBussinessInterfaceBean.getBussinessCode(),
						waitAccountDate);

				AccountTransitSummary accountTransitSummary = accountTransitSummaryService.findTransitSummaryBy(account.getCode(), waitAccountDate);
				if (accountTransitSummary == null) accountTransitSummaryService.create(accountTransitBalanceDetail);
				else {
					accountTransitSummary.setWaitAccountAmount(AmountUtils.add(accountTransitSummary.getWaitAccountAmount(), creditAmount));
					accountTransitSummaryService.addOrSubstractWaitAccountAmount(accountTransitSummary);
				}

				accountRecordedDetailService.create(tallyVoucher.getAccountNo(), account.getUserNo(), account.getUserRole(),
						accountBussinessInterfaceBean.getSystemFlowId(), accountBussinessInterfaceBean.getBussinessCode(), tallyVoucher.getCurrency(),
						tallyVoucher.getTransOrder(), tallyVoucher.getTransDate(), accountBussinessInterfaceBean.getSystemCode(), waitAccountDate,
						tallyVoucher.getAmount(), tallyVoucher.getSymbol(), tallyVoucher.getFee(), tallyVoucher.getFeeSymbol(), account.getBalance());

				accountService.addValueableAndTransitBalance(account, creditAmount);
			} else { // 实时入账
				accountRecordedDetailService.create(tallyVoucher.getAccountNo(), account.getUserNo(), account.getUserRole(),
						accountBussinessInterfaceBean.getSystemFlowId(), accountBussinessInterfaceBean.getBussinessCode(), tallyVoucher.getCurrency(),
						tallyVoucher.getTransOrder(), tallyVoucher.getTransDate(), accountBussinessInterfaceBean.getSystemCode(), waitAccountDate,
						tallyVoucher.getAmount(), tallyVoucher.getSymbol(), tallyVoucher.getFee(), tallyVoucher.getFeeSymbol(), account.getBalance());

				accountService.addOrSubtractValueableBalance(account, creditAmount);

				AccountBehavior accountBehavior = accountBehaviorService.findAccountBehavior(tallyVoucher.getAccountNo());
				if (accountBehavior != null && accountBehavior.getPreFreezeCount() > 0) {
					List<AccountFreezeDetail> accountFreezeDetails = accountFreezeDetailService.findAccountFreezeDetailBy(tallyVoucher.getAccountNo(),
							FreezeStatus.PRE_FREEZE_ING);
					for (AccountFreezeDetail accountFreezeDetail : accountFreezeDetails) {
						// 需预冻金额
						double remainPreFreezeBalance = AmountUtils.subtract(accountFreezeDetail.getPreFreezeBalance(), accountFreezeDetail.getFreezeBalance());
						if (creditAmount < remainPreFreezeBalance) {
							accountFreezeBalanceDetailService.create(accountBussinessInterfaceBean.getSystemCode(), tallyVoucher.getAccountNo(),
									tallyVoucher.getTransOrder(), creditAmount, null, FreezeHandleType.PRE_FREEZE.name());
							accountFreezeDetailService.addFreezeBalance(accountFreezeDetail, creditAmount);
							accountService.addFreezeBalance(account, creditAmount);
							break;
						} else {
							// 可入账户金额
							double valueableCreditBalance = AmountUtils.subtract(creditAmount, remainPreFreezeBalance);
							accountFreezeBalanceDetailService.create(accountBussinessInterfaceBean.getSystemCode(), tallyVoucher.getAccountNo(),
									tallyVoucher.getTransOrder(), remainPreFreezeBalance, null, FreezeHandleType.PRE_FREEZE.name());
							accountFreezeDetailService.preFreezeComplete(accountFreezeDetail, remainPreFreezeBalance);
							accountBehavior.setPreFreezeCount(accountBehavior.getPreFreezeCount() - 1);
							accountBehaviorService.addOrSubtractPreFreezeCount(accountBehavior);
							accountService.addFreezeBalance(account, remainPreFreezeBalance);
							creditAmount = valueableCreditBalance;
						}
					}
				}
			}
			mqSendMessage.send(tradeVoucher,account.getUserNo(),accountBussinessInterfaceBean.getBussinessCode());
		} catch (Exception e) {
			logger.error("{}", e);
			throw new BussinessRuntimeException(e.getMessage());
		}
	}
}
