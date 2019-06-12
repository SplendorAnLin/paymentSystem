package com.yl.account.core.task;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.DateUtils;
import com.yl.account.core.service.AccountDayService;
import com.yl.account.core.service.AccountRecordedDetailService;
import com.yl.account.core.service.AccountService;
import com.yl.account.model.AccountDay;
import com.yl.account.model.AccountDayDetail;

/**
 * 日收支表定时任务
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月12日
 * @version V1.0.0
 */
@Component
public class AccountBalanceJob{

	private static Logger logger = LoggerFactory.getLogger(AccountBalanceJob.class);

	@Resource
	private AccountService accountService;
	@Resource
	private AccountDayService accountDayService;
	@Resource
	private AccountRecordedDetailService accountRecordedDetailService;

	/*
	 * (non-Javadoc)
	 * @see com.lefu.orderliness.proxy.AbstractJob#execute()
	 */
	public void execute() {

		logger.info("*************** AccountDayJob Start ***************");

		Date dailyDate = DateUtils.addDays(new Date(), -1);
		Date dailyStart = DateUtils.getMinTime(dailyDate);
		Date dailyEnd = DateUtils.getMaxTime(dailyDate);

		List<AccountDayDetail> accountDayDetails = accountRecordedDetailService.findBySum(dailyStart, dailyEnd);
		double creditAmount = calculateReceiptPay(accountDayDetails);
		List<Map<String, Double>> accountBalace = accountService.findAccountBalanceBySum(); // 查询当前商户总余额

		//XXX
//		RemitBillStatParam remitBillStatParam = new RemitBillStatParam(); // 查询付款系统未付汇总
//		remitBillStatParam.setDate(dailyDate);
//		RemitBillStatBean remitBillStatBean = remitQueryInterface.stat(remitBillStatParam);

		AccountDay todAccountDay = new AccountDay();
		todAccountDay.setBusiDate(dailyDate);
		todAccountDay.setDayInitial(AmountUtils.add(accountBalace.get(0).get("BALANCE"), creditAmount)); // 今天期初金额

//		if (remitBillStatBean != null) todAccountDay.setDayNoRemit(remitBillStatBean.getTotalAmount());
		todAccountDay.setDaySysEnd(AmountUtils.add(todAccountDay.getDayInitial(), todAccountDay.getDayNoRemit()));

		accountDayService.insert(todAccountDay);

		logger.info("*************** AccountDayJob End ***************");
	}

	/**
	 * @Description 计算昨天T+0的入账汇总
	 * @param accountDayDetails 余额收支明细
	 * @return double 轧差结果
	 * @see 需要参考的类或方法
	 */
	private double calculateReceiptPay(List<AccountDayDetail> accountDayDetails) {
		AccountDay accountDay = new AccountDay();
		if (accountDayDetails != null && accountDayDetails.size() > 0) {
			for (AccountDayDetail accountDayDetail : accountDayDetails) {
				String businessCode = accountDayDetail.getBussinessCode();
				if (businessCode.startsWith("ONLINE") || businessCode.startsWith("PURCHASE") || businessCode.startsWith("PRE_AUTH")) {
					if ("PLUS".equals(accountDayDetail.getSymbol())) accountDay.setDayReceip(AmountUtils.add(accountDay.getDayReceip(),
							accountDayDetail.getDayOccu()));
					else accountDay.setDayPay(AmountUtils.add(accountDay.getDayPay(), accountDayDetail.getDayOccu()));
				}
			}
		}
		return AmountUtils.subtract(accountDay.getDayReceip(), accountDay.getDayPay());
	}
}
