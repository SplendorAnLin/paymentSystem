/**
 * 
 */
package com.yl.account.core.facade.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.bean.AccountThaw;
import com.yl.account.core.C;
import com.yl.account.core.ExceptionMessages;
import com.yl.account.core.exception.BussinessException;
import com.yl.account.core.exception.BussinessRuntimeException;
import com.yl.account.core.facade.AccountPrefreezeThawFacade;
import com.yl.account.core.service.AccountBehaviorService;
import com.yl.account.core.service.AccountChangeDetailService;
import com.yl.account.core.service.AccountFreezeBalanceDetailService;
import com.yl.account.core.service.AccountFreezeDetailService;
import com.yl.account.core.service.AccountService;
import com.yl.account.enums.FreezeHandleType;
import com.yl.account.enums.FreezeStatus;
import com.yl.account.model.Account;
import com.yl.account.model.AccountBehavior;
import com.yl.account.model.AccountFreezeDetail;

/**
 * 预冻解冻处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月9日
 * @version V1.0.0
 */
@Component("accountPrefreezeThawFacade")
public class AccountPrefreezeThawFacadeImpl implements AccountPrefreezeThawFacade {

	private static Logger logger = LoggerFactory.getLogger(AccountPrefreezeThawFacadeImpl.class);

	@Resource
	private AccountService accountService;
	@Resource
	private AccountBehaviorService accountBehaviorService;
	@Resource
	private AccountFreezeBalanceDetailService accountFreezeBalanceDetailService;
	@Resource
	private AccountFreezeDetailService accountFreezeDetailService;
	@Resource
	private AccountChangeDetailService accountChangeDetailService;

	/*
	 * (non-Javadoc)
	 * @see com.yl.account.core.facade.AccountPrefreezeThawFacade#thaw(com.yl.account.api.bean.AccountBussinessInterfaceBean)
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BussinessRuntimeException.class)
	@Override
	public void thaw(AccountBussinessInterfaceBean accountBussinessInterfaceBean) {
		try {
			AccountThaw accountThaw = JsonUtils.toJsonToObject(accountBussinessInterfaceBean.getTradeVoucher(), AccountThaw.class);

			logger.info("账务系统接收业务系统请求[预冻解冻]指令：{}", JsonUtils.toJsonString(accountThaw));

			Account freezeAccount = accountService.findAccountByCode(accountThaw.getAccountNo());
			if (freezeAccount == null) throw new BussinessException(ExceptionMessages.ACCOUNT_NOT_EXISTS);

			AccountFreezeDetail accountFreezeDetail = accountFreezeDetailService.findAccountFreezeDetailByFreezeNo(accountThaw.getFreezeNo());
			if (accountFreezeDetail == null) throw new BussinessException(ExceptionMessages.ACCOUNT_FREEZE_DETAI_NOT_EXISTS);
			if (accountFreezeDetail.getStatus().equals(FreezeStatus.FREEZE) || accountFreezeDetail.getStatus().equals(FreezeStatus.UNFREEZE)) throw new BussinessException(
					ExceptionMessages.ACCOUNT_STATUS_NOT_PRE_FREEZE);

			double rollbackMoney = 0;
			if (accountFreezeDetail.getRequestBalance() == null) rollbackMoney = accountFreezeDetail.getFreezeBalance();
			else rollbackMoney = AmountUtils.subtract(accountFreezeDetail.getFreezeBalance(), accountFreezeDetail.getRequestBalance());

			if (AmountUtils.greater(rollbackMoney, freezeAccount.getFreezeBalance())) throw new BussinessException(
					ExceptionMessages.ACCOUNT_THAW_AMOUNT_GREATETHAN_TOTAL_FREEZE);
			logger.info("[预冻解冻]流水信息：{}, 解冻金额：{}", accountFreezeDetail, rollbackMoney);

			accountFreezeBalanceDetailService.create(accountBussinessInterfaceBean.getSystemCode(), freezeAccount.getCode(),
					accountBussinessInterfaceBean.getSystemFlowId(), rollbackMoney, null, FreezeHandleType.THAW.name());

			if (!accountBussinessInterfaceBean.getSystemCode().equals(C.APP_NAME)) accountChangeDetailService.create(
					accountBussinessInterfaceBean.getBussinessCode(), accountBussinessInterfaceBean.getRequestTime(), accountThaw.getAccountNo(),
					freezeAccount.getType(), freezeAccount.getStatus(), accountBussinessInterfaceBean.getSystemCode(),
					accountBussinessInterfaceBean.getSystemFlowId(), freezeAccount.getUserNo(), freezeAccount.getUserRole(), freezeAccount.getBalance(),
					freezeAccount.getFreezeBalance(), freezeAccount.getTransitBalance(), null, accountFreezeDetail.getFreezeBalance(),
					accountBussinessInterfaceBean.getOperator(), accountBussinessInterfaceBean.getRemark());

			int preFreezeCount = 0;
			if (accountFreezeDetail.getStatus().equals(FreezeStatus.PRE_FREEZE_ING)) preFreezeCount++;

			accountFreezeDetailService.thawComplete(accountFreezeDetail);

			if (!AmountUtils.eq(rollbackMoney, 0d)) {

				accountService.subtractFreeze(freezeAccount, rollbackMoney);

				AccountBehavior accountBehavior = accountBehaviorService.findAccountBehavior(freezeAccount.getCode());

				boolean isFirst = true;
				if (accountBehavior != null && accountBehavior.getPreFreezeCount() > 0) {
					List<AccountFreezeDetail> accountFreezeDetails = accountFreezeDetailService.findAccountFreezeDetailBy(freezeAccount.getCode(),
							FreezeStatus.PRE_FREEZE_ING);
					for (AccountFreezeDetail accountPreFreezeDetail : accountFreezeDetails) {
						// 需预冻金额
						double remainPreFreezeBalance = AmountUtils
								.subtract(accountPreFreezeDetail.getPreFreezeBalance(), accountPreFreezeDetail.getFreezeBalance());
						if (rollbackMoney < remainPreFreezeBalance) {
							accountFreezeBalanceDetailService.create(accountBussinessInterfaceBean.getSystemCode(), freezeAccount.getCode(),
									accountBussinessInterfaceBean.getSystemFlowId(), rollbackMoney, null, FreezeHandleType.PRE_FREEZE.name());

							accountFreezeDetailService.addFreezeBalance(accountPreFreezeDetail, rollbackMoney);
							accountService.addFreezeBalance(freezeAccount, rollbackMoney);
							break;
						} else {
							// 可入账户金额
							double valueableCreditBalance = AmountUtils.subtract(rollbackMoney, remainPreFreezeBalance);
							accountFreezeBalanceDetailService.create(accountBussinessInterfaceBean.getSystemCode(), freezeAccount.getCode(),
									accountBussinessInterfaceBean.getSystemFlowId(), remainPreFreezeBalance, null, FreezeHandleType.PRE_FREEZE.name());
							accountFreezeDetailService.preFreezeComplete(accountPreFreezeDetail, remainPreFreezeBalance);
							if (isFirst && !AmountUtils.eq(preFreezeCount, 0)) accountBehavior.setPreFreezeCount(accountBehavior.getPreFreezeCount()
									- preFreezeCount);
							else accountBehavior.setPreFreezeCount(accountBehavior.getPreFreezeCount() - 1);
							accountBehaviorService.addOrSubtractPreFreezeCount(accountBehavior);
							accountService.addFreezeBalance(freezeAccount, remainPreFreezeBalance);
							rollbackMoney = valueableCreditBalance;
							isFirst = true;
						}
					}
				}
			}

		} catch (Exception e) {
			logger.error("{}", e);
			throw new BussinessRuntimeException(e.getMessage());
		}
	}
}
