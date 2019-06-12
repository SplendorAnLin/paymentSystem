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
import com.yl.account.bean.AccountTransitVoucher;
import com.yl.account.bean.TallyVoucher;
import com.yl.account.bean.TradeVoucher;
import com.yl.account.core.ExceptionMessages;
import com.yl.account.core.exception.BussinessException;
import com.yl.account.core.exception.BussinessRuntimeException;
import com.yl.account.core.facade.AccountTallyFacade;
import com.yl.account.core.service.AccountChangeDetailService;
import com.yl.account.core.service.AccountRecordedDetailService;
import com.yl.account.core.service.AccountService;
import com.yl.account.core.service.AccountTransitBalanceDetailService;
import com.yl.account.core.service.AccountTransitSummaryService;
import com.yl.account.core.utils.AccountFundSymbolUtils;
import com.yl.account.core.utils.MQsendMessage;
import com.yl.account.enums.AccountStatus;
import com.yl.account.enums.FundSymbol;
import com.yl.account.enums.SummaryStatus;
import com.yl.account.model.Account;
import com.yl.account.model.AccountTransitSummary;
import com.yl.mq.producer.client.ProducerClient;
import com.yl.mq.producer.client.ProducerMessage;
/**
 * 账务出账接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月9日
 * @version V1.0.0
 */
@Component("accountDebitFacade")
public class AccountTallyDebitFacadeImpl implements AccountTallyFacade {

	private static Logger logger = LoggerFactory.getLogger(AccountTallyDebitFacadeImpl.class);

	@Resource
	private AccountService accountService;
	@Resource
	private AccountTransitBalanceDetailService accountTransitBalanceDetailService;
	@Resource
	private AccountTransitSummaryService accountTransitSummaryService;
	@Resource
	private AccountChangeDetailService accountChangeDetailService;
	@Resource
	private AccountRecordedDetailService accountRecordedDetailService;
	@Resource
	MQsendMessage mqSendMessage;
	@Resource
	ProducerClient producerClient;
	/*
	 * (non-Javadoc)
	 * @see com.yl.account.core.facade.AccountTallyFacade#tally(com.yl.account.bean.TallyVoucher, java.lang.String)
	 */
	@Override
	public void tally(TradeVoucher tradeVoucher, AccountBussinessInterfaceBean accountBussinessInterfaceBean) {
		try {
			TallyVoucher tallyVoucher = (TallyVoucher) tradeVoucher;

			logger.info("账务系统接收业务系统请求[出账]指令：{}", JsonUtils.toJsonString(tallyVoucher));
			Account account = accountService.findAccountByCode(tallyVoucher.getAccountNo());
			if (tallyVoucher instanceof AccountTransitVoucher) { // Capital account types transit
				if (account == null) throw new BussinessException(ExceptionMessages.ACCOUNT_NOT_EXISTS);
				if (account.getStatus().equals(AccountStatus.FREEZE) || (accountBussinessInterfaceBean.getBussinessCode().equals("ADJUST") ? false : account
						.getStatus().equals(AccountStatus.EN_OUT))) throw new BussinessException(ExceptionMessages.ACCOUNT_STATUS_NOT_NORMAL);

				if (accountRecordedDetailService.findBy(account.getCode(), tallyVoucher.getTransOrder(), accountBussinessInterfaceBean.getSystemCode(),
						accountBussinessInterfaceBean.getBussinessCode(), accountBussinessInterfaceBean.getSystemFlowId())) return;

				if (accountBussinessInterfaceBean.getBussinessCode().equals("TRANSFER") || accountBussinessInterfaceBean.getBussinessCode().equals("ADJUST")) accountChangeDetailService
						.create(accountBussinessInterfaceBean.getBussinessCode(), new Date(), account.getCode(), account.getType(), account.getStatus(),
								accountBussinessInterfaceBean.getSystemCode(), tallyVoucher.getTransOrder(), account.getUserNo(), account.getUserRole(),
								account.getBalance(), account.getFreezeBalance(), account.getTransitBalance(), tallyVoucher.getSymbol(), tallyVoucher.getAmount(),
								accountBussinessInterfaceBean.getOperator(), accountBussinessInterfaceBean.getRemark());

				AccountTransitVoucher accountTransitVoucher = (AccountTransitVoucher) tallyVoucher;
				if (accountTransitVoucher.getTransitBalance() == null) throw new BussinessException(ExceptionMessages.PARAM_NOT_EXISTS);

				AccountTransitSummary valuelessTransitSummary = accountTransitSummaryService.findTransitSummaryBy(tallyVoucher.getAccountNo(), null);

				double transitBalance = AccountFundSymbolUtils.convertToRealAmount(FundSymbol.SUBTRACT, accountTransitVoucher.getTransitBalance());
				double debitAmount = AmountUtils.add(
						transitBalance,
						accountTransitVoucher.getTransitFeeFundSymbol() == null || accountTransitVoucher.getTransitFeeBalance() == null ? 0 : AccountFundSymbolUtils
								.convertToRealAmount(accountTransitVoucher.getTransitFeeFundSymbol(), accountTransitVoucher.getTransitFeeBalance()));

				double remainTransitBalance = AmountUtils.add(
						AmountUtils.subtract(account.getTransitBalance(), valuelessTransitSummary == null ? 0 : valuelessTransitSummary.getWaitAccountAmount()),
						debitAmount);

				if (remainTransitBalance < 0) throw new BussinessException(ExceptionMessages.ACCOUNT_VALUEABLE_TRANSIT_NOT_ENOUGH);

				double nextSubtractTransitBalance = Math.abs(debitAmount);

				if (tallyVoucher.getWaitAccountDate() == null) {
					if (accountTransitVoucher.getTransitDebitSeq() == null) throw new BussinessException(ExceptionMessages.PARAM_NOT_EXISTS);
					List<AccountTransitSummary> accountTransitSummarys = accountTransitSummaryService.findBatchTransitSummaryBySeq(tallyVoucher.getAccountNo(),
							accountTransitVoucher.getTransitDebitSeq());
					for (AccountTransitSummary accountTransitSummary : accountTransitSummarys) {
						if (nextSubtractTransitBalance >= accountTransitSummary.getWaitAccountAmount()) {
							accountTransitBalanceDetailService.create(account.getCode(), tallyVoucher.getCurrency(), tallyVoucher.getTransOrder(),
									tallyVoucher.getTransDate(), accountBussinessInterfaceBean.getSystemCode(), accountTransitSummary.getWaitAccountAmount(),
									FundSymbol.SUBTRACT, null, null, accountBussinessInterfaceBean.getBussinessCode(), tallyVoucher.getWaitAccountDate());

							nextSubtractTransitBalance = AmountUtils.subtract(nextSubtractTransitBalance, accountTransitSummary.getWaitAccountAmount());

							accountTransitSummary.setWaitAccountAmount(0d);
							accountTransitSummary.setStatus(SummaryStatus.SUMMARY_ING);
							accountTransitSummaryService.modifySummary(accountTransitSummary);
						} else {
							accountTransitBalanceDetailService.create(account.getCode(), tallyVoucher.getCurrency(), tallyVoucher.getTransOrder(),
									tallyVoucher.getTransDate(), accountBussinessInterfaceBean.getSystemCode(), nextSubtractTransitBalance, FundSymbol.SUBTRACT,
									accountTransitVoucher.getTransitFeeBalance(), accountTransitVoucher.getTransitFeeFundSymbol(),
									accountBussinessInterfaceBean.getBussinessCode(), tallyVoucher.getWaitAccountDate());

							accountTransitSummary
									.setWaitAccountAmount(AmountUtils.subtract(accountTransitSummary.getWaitAccountAmount(), nextSubtractTransitBalance));
							accountTransitSummaryService.addOrSubstractWaitAccountAmount(accountTransitSummary);
							nextSubtractTransitBalance = 0;
							break;
						}
					}

					accountRecordedDetailService.create(account.getCode(), account.getUserNo(), account.getUserRole(),
							accountBussinessInterfaceBean.getSystemFlowId(), accountBussinessInterfaceBean.getBussinessCode(), tallyVoucher.getCurrency(),
							tallyVoucher.getTransOrder(), tallyVoucher.getTransDate(), accountBussinessInterfaceBean.getSystemCode(),
							tallyVoucher.getWaitAccountDate(), accountTransitVoucher.getTransitBalance(), FundSymbol.SUBTRACT,
							accountTransitVoucher.getTransitFeeBalance(), accountTransitVoucher.getTransitFeeFundSymbol(), account.getBalance());

					accountService.addValueableAndTransitBalance(account, debitAmount);

				} else { // Cancel
					accountTransitBalanceDetailService.create(account.getCode(), tallyVoucher.getCurrency(), tallyVoucher.getTransOrder(),
							tallyVoucher.getTransDate(), accountBussinessInterfaceBean.getSystemCode(), accountTransitVoucher.getTransitBalance(),
							FundSymbol.SUBTRACT, accountTransitVoucher.getTransitFeeBalance(), accountTransitVoucher.getTransitFeeFundSymbol(),
							accountBussinessInterfaceBean.getBussinessCode(), tallyVoucher.getWaitAccountDate());

					AccountTransitSummary fallbackSummary = accountTransitSummaryService.findTransitSummaryBy(tallyVoucher.getAccountNo(),
							DateUtils.getMinTime(tallyVoucher.getWaitAccountDate()));

					if (fallbackSummary == null) throw new BussinessException(ExceptionMessages.ACCOUNT_SUMMARY_NOT_EXISTS);

					double remainSummaryWaitAccountAmount = AmountUtils.add(fallbackSummary.getWaitAccountAmount(), debitAmount);
					if (remainSummaryWaitAccountAmount < 0) throw new BussinessException(ExceptionMessages.ACCOUNT_SUMMARY_VALUEABLE_TRANSIT_NOT_ENOUGH);
					if (remainSummaryWaitAccountAmount == 0) fallbackSummary.setStatus(SummaryStatus.SUMMARY_COMPLETE);
					fallbackSummary.setWaitAccountAmount(remainSummaryWaitAccountAmount);

					accountTransitSummaryService.addOrSubstractWaitAccountAmount(fallbackSummary);

					accountRecordedDetailService.create(account.getCode(), account.getUserNo(), account.getUserRole(),
							accountBussinessInterfaceBean.getSystemFlowId(), accountBussinessInterfaceBean.getBussinessCode(), tallyVoucher.getCurrency(),
							tallyVoucher.getTransOrder(), tallyVoucher.getTransDate(), accountBussinessInterfaceBean.getSystemCode(),
							tallyVoucher.getWaitAccountDate(), accountTransitVoucher.getTransitBalance(), FundSymbol.SUBTRACT,
							accountTransitVoucher.getTransitFeeBalance(), accountTransitVoucher.getTransitFeeFundSymbol(), account.getBalance());

					accountService.subtractValueableAndTransitBalance(account, nextSubtractTransitBalance);
				}
			} else { // Capital account types available
				if (account == null) throw new BussinessException(ExceptionMessages.ACCOUNT_NOT_EXISTS);
				if (account.getStatus().equals(AccountStatus.FREEZE) || accountBussinessInterfaceBean.getBussinessCode().equals("ADJUST") ? false : account
						.getStatus().equals(AccountStatus.EN_OUT)) throw new BussinessException(ExceptionMessages.ACCOUNT_STATUS_NOT_NORMAL);

				if (accountRecordedDetailService.findBy(account.getCode(), tallyVoucher.getTransOrder(), accountBussinessInterfaceBean.getSystemCode(),
						accountBussinessInterfaceBean.getBussinessCode(), accountBussinessInterfaceBean.getSystemFlowId())) return;

				double debitValueableBalance = AccountFundSymbolUtils.convertToRealAmount(tallyVoucher.getSymbol(), tallyVoucher.getAmount());

				if (tallyVoucher.getFeeSymbol() != null && tallyVoucher.getFee() != null) debitValueableBalance = AmountUtils.add(debitValueableBalance,
						AccountFundSymbolUtils.convertToRealAmount(tallyVoucher.getFeeSymbol(), tallyVoucher.getFee()));

				double valueableBalance = AmountUtils.subtract(AmountUtils.subtract(account.getBalance(), account.getTransitBalance()), account.getFreezeBalance());

				if (Math.abs(debitValueableBalance) > valueableBalance) throw new BussinessException(ExceptionMessages.ACCOUNT_VALUEABLE_BALANCE_NOT_ENOUGH);

				accountRecordedDetailService.create(account.getCode(), account.getUserNo(), account.getUserRole(), accountBussinessInterfaceBean.getSystemFlowId(),
						accountBussinessInterfaceBean.getBussinessCode(), tallyVoucher.getCurrency(), tallyVoucher.getTransOrder(), tallyVoucher.getTransDate(),
						accountBussinessInterfaceBean.getSystemCode(), tallyVoucher.getWaitAccountDate(), tallyVoucher.getAmount(), tallyVoucher.getSymbol(),
						tallyVoucher.getFee(), tallyVoucher.getFeeSymbol(), account.getBalance());

				accountService.addOrSubtractValueableBalance(account, debitValueableBalance);
			}
			mqSendMessage.send(tradeVoucher,account.getUserNo(),accountBussinessInterfaceBean.getBussinessCode());			
//			try{
//				SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				Map<String, Object> map=new HashMap<String, Object>();
//				map.put("accountNo", account.getUserNo());
//				map.put("currency", account.getCurrency());
//				map.put("transOrder",tallyVoucher.getTransOrder());
//				if(tallyVoucher.getTransDate()!=null){
//					map.put("transDate", df.format(tallyVoucher.getTransDate()).toString());
//				}
//				map.put("amount", tallyVoucher.getAmount());
//				map.put("symbol", tallyVoucher.getSymbol());
//				map.put("fee", tallyVoucher.getFee());
//				map.put("feeSymbol", tallyVoucher.getFeeSymbol());
//				if(tallyVoucher.getWaitAccountDate()!=null){
//					map.put("waitAccountDate",df.format(tallyVoucher.getWaitAccountDate()).toString());
//				}
//				producerClient.sendMessage(new ProducerMessage("yl-data-topic","account_accountvoucher_tag",JsonUtils.toJsonString(map).getBytes()));
//				logger.info("推送主题 yl-data-topic,标签：账户流水  成功，商户编号{}，消息体{}", account.getUserNo(),map);
//			} catch (Exception e) {
//					logger.error("推送账户流水到数据中心失败，原因{}",e);
//				}
		} catch (Exception e) {
			logger.error("{}", e);
			throw new BussinessRuntimeException(e.getMessage());
		}
	}
}
