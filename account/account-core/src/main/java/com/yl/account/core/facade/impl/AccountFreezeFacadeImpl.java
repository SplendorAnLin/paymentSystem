/**
 * 
 */
package com.yl.account.core.facade.impl;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.response.AccountFreezeResponse;
import com.yl.account.api.enums.HandlerResult;
import com.yl.account.bean.AccountFreeze;
import com.yl.account.core.ExceptionMessages;
import com.yl.account.core.exception.BussinessException;
import com.yl.account.core.exception.BussinessRuntimeException;
import com.yl.account.core.facade.AccountFreezeFacade;
import com.yl.account.core.service.AccountChangeDetailService;
import com.yl.account.core.service.AccountFreezeBalanceDetailService;
import com.yl.account.core.service.AccountFreezeDetailService;
import com.yl.account.core.service.AccountService;
import com.yl.account.enums.AccountStatus;
import com.yl.account.enums.FreezeHandleType;
import com.yl.account.model.Account;
import com.yl.account.model.AccountFreezeDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 账务可用余额冻结处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月9日
 * @version V1.0.0
 */
@Component
public class AccountFreezeFacadeImpl implements AccountFreezeFacade {

	private static Logger logger = LoggerFactory.getLogger(AccountFreezeFacadeImpl.class);

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
	 * @see com.yl.account.core.facade.AccountFreezeFacade#freeze(com.yl.account.api.bean.AccountBussinessInterfaceBean)
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BussinessRuntimeException.class)
	@Override
	public AccountFreezeResponse freeze(AccountBussinessInterfaceBean accountBussinessInterfaceBean) {
		AccountFreezeResponse accountFreezeResponse = null;
		try {
			AccountFreeze accountFreeze = JsonUtils.toJsonToObject(accountBussinessInterfaceBean.getTradeVoucher(), AccountFreeze.class);

			logger.info("账务系统接收业务系统[冻结]指令：{}", accountFreeze);

			Account account = accountService.findAccountByCode(accountFreeze.getAccountNo());
			if (account == null) throw new BussinessException(ExceptionMessages.ACCOUNT_NOT_EXISTS);

			if (AccountStatus.FREEZE.equals(account.getStatus()) || AccountStatus.EN_OUT.equals(account.getStatus())) throw new BussinessException(
					ExceptionMessages.ACCOUNT_ALREADY_FREEZE);

			double valueableBalance = AmountUtils.subtract(account.getBalance(), AmountUtils.add(account.getTransitBalance(), account.getFreezeBalance()));

			if (accountFreeze.getFreezeAmount() > valueableBalance) throw new BussinessException(ExceptionMessages.ACCOUNT_FREEZE_AMOUNT_GREATETHAN_VALUEABLE_AMOUNT);

			accountChangeDetailService.create(accountBussinessInterfaceBean.getBussinessCode(), accountBussinessInterfaceBean.getRequestTime(),
					accountFreeze.getAccountNo(), account.getType(), account.getStatus(), accountBussinessInterfaceBean.getSystemCode(),
					accountBussinessInterfaceBean.getSystemFlowId(), account.getUserNo(), account.getUserRole(), account.getBalance(), account.getFreezeBalance(),
					account.getTransitBalance(), null, accountFreeze.getFreezeAmount(), accountBussinessInterfaceBean.getOperator(),
					accountBussinessInterfaceBean.getRemark());

			accountFreezeBalanceDetailService
					.create(accountBussinessInterfaceBean.getSystemCode(), accountFreeze.getAccountNo(), accountBussinessInterfaceBean.getSystemFlowId(),
							accountFreeze.getFreezeAmount(), accountFreeze.getFreezeLimit(), FreezeHandleType.FREEZE.name());
			AccountFreezeDetail accountFreezeDetail = accountFreezeDetailService.createFreezeDetail(accountBussinessInterfaceBean, accountFreeze.getAccountNo(),
					accountFreeze.getFreezeAmount(), accountFreeze.getFreezeLimit());

			accountService.addFreezeBalance(account, accountFreeze.getFreezeAmount());

			accountFreezeResponse = new AccountFreezeResponse();
			accountFreezeResponse.setResult(HandlerResult.SUCCESS);
			accountFreezeResponse.setFinishTime(new Date());
			accountFreezeResponse.setFreezeNo(accountFreezeDetail.getFreezeNo());

		} catch (Exception e) {
			logger.error("{}", e);
			throw new BussinessRuntimeException(e.getMessage());
		}
		return accountFreezeResponse;
	}
}