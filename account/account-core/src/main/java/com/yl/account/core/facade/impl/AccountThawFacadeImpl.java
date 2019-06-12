/**
 * 
 */
package com.yl.account.core.facade.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.response.AccountThawResponse;
import com.yl.account.api.enums.HandlerResult;
import com.yl.account.api.enums.Status;
import com.yl.account.bean.AccountThaw;
import com.yl.account.core.C;
import com.yl.account.core.ExceptionMessages;
import com.yl.account.core.exception.BussinessException;
import com.yl.account.core.exception.BussinessRuntimeException;
import com.yl.account.core.facade.AccountThawFacade;
import com.yl.account.core.service.AccountChangeDetailService;
import com.yl.account.core.service.AccountFreezeBalanceDetailService;
import com.yl.account.core.service.AccountFreezeDetailService;
import com.yl.account.core.service.AccountService;
import com.yl.account.enums.FreezeHandleType;
import com.yl.account.enums.FreezeStatus;
import com.yl.account.model.Account;
import com.yl.account.model.AccountFreezeDetail;

/**
 * 账务解冻处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月9日
 * @version V1.0.0
 */
@Component("accountThawFacade")
public class AccountThawFacadeImpl implements AccountThawFacade {

	private static Logger logger = LoggerFactory.getLogger(AccountThawFacadeImpl.class);

	@Resource
	private AccountService accountService;
	@Resource
	private AccountFreezeBalanceDetailService accountFreezeBalanceDetailService;
	@Resource
	private AccountFreezeDetailService accountFreezeDetailService;
	@Resource
	private AccountChangeDetailService accountChangeDetailService;

	/*
	 * (non-Javadoc)
	 * @see com.yl.account.core.facade.AccountThawFacade#thaw(com.yl.account.api.bean.AccountBussinessInterfaceBean)
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BussinessRuntimeException.class)
	@Override
	public AccountThawResponse thaw(AccountBussinessInterfaceBean accountBussinessInterfaceBean) {
		AccountThawResponse accountThawResponse = null;
		try {
			AccountThaw accountThaw = JsonUtils.toJsonToObject(accountBussinessInterfaceBean.getTradeVoucher(), AccountThaw.class);

			logger.info("账务系统接收业务系统请求[解冻]指令：{}", JsonUtils.toJsonString(accountThaw));

			Account freezeAccount = accountService.findAccountByCode(accountThaw.getAccountNo());
			if (freezeAccount == null) throw new BussinessException(ExceptionMessages.ACCOUNT_NOT_EXISTS);

			AccountFreezeDetail accountFreezeDetail = accountFreezeDetailService.findAccountFreezeDetailByFreezeNo(accountThaw.getFreezeNo());
			if (accountFreezeDetail == null) throw new BussinessException(ExceptionMessages.ACCOUNT_FREEZE_DETAI_NOT_EXISTS);
			if (!accountFreezeDetail.getStatus().equals(FreezeStatus.FREEZE)) throw new BussinessException(ExceptionMessages.ACCOUNT_STATUS_NOT_FREEZE);

			double thawAmount = AmountUtils.subtract(accountFreezeDetail.getFreezeBalance(), accountFreezeDetail.getRequestBalance() == null ? 0
					: accountFreezeDetail.getRequestBalance());
			accountFreezeDetail.setFreezeLimit(new Date());
			logger.info("[解冻]流水信息：{}, 解冻金额：{}", accountFreezeDetail, thawAmount);

			if (AmountUtils.greater(thawAmount, freezeAccount.getFreezeBalance())) throw new BussinessException(
					ExceptionMessages.ACCOUNT_THAW_AMOUNT_GREATETHAN_TOTAL_FREEZE);

			if (!accountBussinessInterfaceBean.getSystemCode().equals(C.APP_NAME)) accountChangeDetailService.create(
					accountBussinessInterfaceBean.getBussinessCode(), accountBussinessInterfaceBean.getRequestTime(), accountThaw.getAccountNo(),
					freezeAccount.getType(), freezeAccount.getStatus(), accountBussinessInterfaceBean.getSystemCode(),
					accountBussinessInterfaceBean.getSystemFlowId(), freezeAccount.getUserNo(), freezeAccount.getUserRole(), freezeAccount.getBalance(),
					freezeAccount.getFreezeBalance(), freezeAccount.getTransitBalance(), null, thawAmount, accountBussinessInterfaceBean.getOperator(),
					accountBussinessInterfaceBean.getRemark());

			accountFreezeBalanceDetailService.create(accountBussinessInterfaceBean.getSystemCode(), freezeAccount.getCode(),
					accountBussinessInterfaceBean.getSystemFlowId(), thawAmount, null, FreezeHandleType.THAW.name());
			accountFreezeDetailService.thawComplete(accountFreezeDetail);
			accountService.subtractFreeze(freezeAccount, thawAmount);

			accountThawResponse = new AccountThawResponse();
			accountThawResponse.setAccountNo(accountThaw.getAccountNo());
			accountThawResponse.setFinishTime(new Date());
			accountThawResponse.setResult(HandlerResult.SUCCESS);
			accountThawResponse.setStatus(Status.SUCCESS);
		} catch (Exception e) {
			logger.error("{}", e);
			throw new BussinessRuntimeException(e.getMessage());
		}
		return accountThawResponse;
	}

	/**
	 * 根据冻结编号查询账户编号
	 */
	public String queryFreezeAccount(String freezeNo){
		AccountFreezeDetail accountFreezeDetail = accountFreezeDetailService.findAccountFreezeDetailByFreezeNo(freezeNo);
		if (accountFreezeDetail != null) {
			return accountFreezeDetail.getAccountNo();
		}
		return "";
	}
}
