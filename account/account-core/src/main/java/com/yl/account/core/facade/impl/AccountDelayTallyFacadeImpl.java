/**
 * 
 */
package com.yl.account.core.facade.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.DateUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.response.AccountDelayTallyResponse;
import com.yl.account.api.enums.HandlerResult;
import com.yl.account.api.enums.Status;
import com.yl.account.bean.AccountDelayTally;
import com.yl.account.bean.TallyVoucher;
import com.yl.account.core.ExceptionMessages;
import com.yl.account.core.exception.BussinessException;
import com.yl.account.core.exception.BussinessRuntimeException;
import com.yl.account.core.facade.AccountDelayTallyFacade;
import com.yl.account.core.service.AccountBehaviorService;
import com.yl.account.core.service.AccountFreezeBalanceDetailService;
import com.yl.account.core.service.AccountFreezeDetailService;
import com.yl.account.core.service.AccountRecordedDetailService;
import com.yl.account.core.service.AccountService;
import com.yl.account.core.service.AccountTransitBalanceDetailService;
import com.yl.account.core.service.AccountTransitSummaryService;
import com.yl.account.core.utils.AccountFundSymbolUtils;
import com.yl.account.enums.AccountStatus;
import com.yl.account.enums.Currency;
import com.yl.account.enums.DelayType;
import com.yl.account.enums.FreezeHandleType;
import com.yl.account.enums.FreezeStatus;
import com.yl.account.enums.TransitDebitSeq;
import com.yl.account.model.Account;
import com.yl.account.model.AccountBehavior;
import com.yl.account.model.AccountFreezeDetail;
import com.yl.account.model.AccountTransitBalanceDetail;
import com.yl.account.model.AccountTransitSummary;

/**
 * 延迟标记入账接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月9日
 * @version V1.0.0
 */
@Component
public class AccountDelayTallyFacadeImpl implements AccountDelayTallyFacade {

	private static Logger logger = LoggerFactory.getLogger(AccountDelayTallyFacadeImpl.class);

	@Resource
	private AccountService accountService;
	@Resource
	private AccountBehaviorService accountBehaviorService;
	@Resource
	private AccountFreezeDetailService accountFreezeDetailService;
	@Resource
	private AccountRecordedDetailService accountRecordedDetailService;
	@Resource
	private AccountTransitSummaryService accountTransitSummaryService;
	@Resource
	private AccountFreezeBalanceDetailService accountFreezeBalanceDetailService;
	@Resource
	private AccountTransitBalanceDetailService accountTransitBalanceDetailService;

	/*
	 * (non-Javadoc)
	 * @see com.yl.account.core.facade.AccountDelayCreditFacade#delayCredit(com.yl.account.api.bean.AccountBussinessInterfaceBean)
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BussinessRuntimeException.class)
	@Override
	public AccountDelayTallyResponse delayTally(AccountBussinessInterfaceBean accountBussinessInterfaceBean) {
		AccountDelayTallyResponse accountDelayCreditResponse = null;
		try {
			AccountDelayTally accountDelayTally = JsonUtils.toJsonToObject(accountBussinessInterfaceBean.getTradeVoucher(), AccountDelayTally.class);

			logger.info("账务系统接收业务系统[延迟入账]指令：{}", accountDelayTally);

			List<AccountTransitBalanceDetail> accountTransitBalanceDetails = accountTransitBalanceDetailService.findTransitBalanceDetailBy(accountDelayTally
					.getAccountNo(), accountDelayTally.getOrigTransOrder(), accountDelayTally.getOrigBussinessCode(),
					StringUtils.concatToSB(accountDelayTally.getOrigBussinessCode(), "_FEE").toString(),
					accountDelayTally.getDelayType().equals(DelayType.CREDIT) ? TransitDebitSeq.ASC : TransitDebitSeq.DESC);
			if (accountTransitBalanceDetails == null || accountTransitBalanceDetails.isEmpty()) throw new BussinessRuntimeException(
					ExceptionMessages.ACCOUNT_TRANSIT_DETAI_NOT_EXISTS);

			for (AccountTransitBalanceDetail originalAccountTransitBalanceDetail : accountTransitBalanceDetails) {
				Account account = accountService.findAccountByCode(originalAccountTransitBalanceDetail.getAccountNo());
				if (account == null) throw new BussinessException(ExceptionMessages.ACCOUNT_NOT_EXISTS);

				AccountTransitSummary accountTransitSummary = accountTransitSummaryService.findTransitSummaryBy(account.getCode(), null);
				if (accountTransitSummary != null) {

					accountTransitSummary.setWaitAccountAmount(AmountUtils.subtract(
							accountTransitSummary.getWaitAccountAmount(),
							AccountFundSymbolUtils.convertToRealAmount(originalAccountTransitBalanceDetail.getSymbol(),
									originalAccountTransitBalanceDetail.getTransAmount())));

					accountTransitSummaryService.addOrSubstractWaitAccountAmount(accountTransitSummary);
				}

				originalAccountTransitBalanceDetail.setWaitAccountDate(accountDelayTally.getWaitAccountDate() == null ? new Date() : accountDelayTally
						.getWaitAccountDate());
				accountTransitBalanceDetailService.modifyWaitAccountDate(originalAccountTransitBalanceDetail); // 标识待入账日期

				TallyVoucher tallyVoucher = new TallyVoucher();
				tallyVoucher.setAccountNo(accountDelayTally.getAccountNo());
				tallyVoucher.setCurrency(Currency.RMB);
				tallyVoucher.setTransOrder(accountDelayTally.getOrigTransOrder());

				double delayCreditAmount = AccountFundSymbolUtils.convertToRealAmount(originalAccountTransitBalanceDetail.getSymbol(),
						originalAccountTransitBalanceDetail.getTransAmount());

				if (accountDelayTally.getDelayType().equals(DelayType.CREDIT)) {

					if (account.getStatus().equals(AccountStatus.FREEZE) || account.getStatus().equals(AccountStatus.END_IN)) throw new BussinessException(
							ExceptionMessages.ACCOUNT_STATUS_NOT_NORMAL);
					if (accountDelayTally.getWaitAccountDate() == null) throw new BussinessException(ExceptionMessages.PARAM_ERROR);

					if (DateUtils.getMinTime(accountDelayTally.getWaitAccountDate()).compareTo(new Date()) > 0) {
						accountTransitSummary = accountTransitSummaryService.findTransitSummaryBy(accountDelayTally.getAccountNo(),
								DateUtils.getMinTime(accountDelayTally.getWaitAccountDate()));

						if (accountTransitSummary == null) {
							originalAccountTransitBalanceDetail.setTransAmount(delayCreditAmount);
							accountTransitSummaryService.create(originalAccountTransitBalanceDetail);
						} else {
							accountTransitSummary.setWaitAccountAmount(AmountUtils.add(accountTransitSummary.getWaitAccountAmount(), delayCreditAmount));
							accountTransitSummaryService.addOrSubstractWaitAccountAmount(accountTransitSummary);
						}

					} else { // 实时入账
						account.setTransitBalance(AmountUtils.subtract(account.getTransitBalance(), delayCreditAmount));
						accountService.subtractTransit(account);

						AccountBehavior accountBehavior = accountBehaviorService.findAccountBehavior(tallyVoucher.getAccountNo());
						if (accountBehavior != null && accountBehavior.getPreFreezeCount() > 0) {
							List<AccountFreezeDetail> accountFreezeDetails = accountFreezeDetailService.findAccountFreezeDetailBy(tallyVoucher.getAccountNo(),
									FreezeStatus.PRE_FREEZE_ING);
							for (AccountFreezeDetail accountFreezeDetail : accountFreezeDetails) {
								// 需预冻金额
								double remainPreFreezeBalance = AmountUtils.subtract(accountFreezeDetail.getPreFreezeBalance(),
										accountFreezeDetail.getFreezeBalance());
								if (delayCreditAmount < remainPreFreezeBalance) {
									accountFreezeBalanceDetailService.create(accountBussinessInterfaceBean.getSystemCode(), tallyVoucher.getAccountNo(),
											tallyVoucher.getTransOrder(), delayCreditAmount, null, FreezeHandleType.PRE_FREEZE.name());

									accountFreezeDetailService.addFreezeBalance(accountFreezeDetail, delayCreditAmount);
									accountService.addFreezeBalance(account, delayCreditAmount);
									break;
								} else {
									// 可入账户金额
									double valueableCreditBalance = AmountUtils.subtract(delayCreditAmount, remainPreFreezeBalance);
									accountFreezeBalanceDetailService.create(accountBussinessInterfaceBean.getSystemCode(), tallyVoucher.getAccountNo(),
											tallyVoucher.getTransOrder(), remainPreFreezeBalance, null, FreezeHandleType.PRE_FREEZE.name());

									accountFreezeDetailService.preFreezeComplete(accountFreezeDetail, remainPreFreezeBalance);
									accountBehavior.setPreFreezeCount(accountBehavior.getPreFreezeCount() - 1);
									accountBehaviorService.addOrSubtractPreFreezeCount(accountBehavior);
									accountService.addFreezeBalance(account, remainPreFreezeBalance);
									delayCreditAmount = valueableCreditBalance;
								}
							}
						}
					}
				} else {

					if (account.getStatus().equals(AccountStatus.FREEZE) || account.getStatus().equals(AccountStatus.EN_OUT)) throw new BussinessException(
							ExceptionMessages.ACCOUNT_STATUS_NOT_NORMAL);
					if (accountDelayTally.getWaitAccountDate() != null) throw new BussinessException(ExceptionMessages.PARAM_ERROR);

					accountRecordedDetailService.create(account.getCode(), account.getUserNo(), account.getUserRole(),
							accountBussinessInterfaceBean.getSystemFlowId(), accountBussinessInterfaceBean.getBussinessCode(), account.getCurrency(),
							originalAccountTransitBalanceDetail.getTransFlow(), new Date(), accountBussinessInterfaceBean.getSystemCode(),
							accountDelayTally.getWaitAccountDate(), originalAccountTransitBalanceDetail.getTransAmount(),
							AccountFundSymbolUtils.inverseSymbol(originalAccountTransitBalanceDetail.getSymbol()), null, null, account.getBalance());

					accountService.subtractValueableAndTransitBalance(account, delayCreditAmount);
				}
			}

			accountDelayCreditResponse = new AccountDelayTallyResponse();
			accountDelayCreditResponse.setAccountNo(accountDelayTally.getAccountNo());
			accountDelayCreditResponse.setFinishTime(new Date());
			accountDelayCreditResponse.setResult(HandlerResult.SUCCESS);
			accountDelayCreditResponse.setStatus(Status.SUCCESS);
			accountDelayCreditResponse.setTransOrder(accountDelayTally.getOrigTransOrder());

		} catch (Exception e) {
			logger.error("{}", e);
			throw new BussinessRuntimeException(e.getMessage());
		}
		return accountDelayCreditResponse;
	}
}
