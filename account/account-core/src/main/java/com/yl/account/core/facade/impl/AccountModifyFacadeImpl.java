/**
 * 
 */
package com.yl.account.core.facade.impl;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.request.AccountModify;
import com.yl.account.api.bean.response.AccountModifyResponse;
import com.yl.account.api.enums.HandlerResult;
import com.yl.account.core.ExceptionMessages;
import com.yl.account.core.exception.BussinessException;
import com.yl.account.core.exception.BussinessRuntimeException;
import com.yl.account.core.facade.AccountModifyFacade;
import com.yl.account.core.service.AccountChangeDetailService;
import com.yl.account.core.service.AccountService;
import com.yl.account.enums.AccountStatus;
import com.yl.account.enums.AccountType;
import com.yl.account.enums.UserRole;
import com.yl.account.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 账户修改处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月9日
 * @version V1.0.0
 */
@Component
public class AccountModifyFacadeImpl implements AccountModifyFacade {

	private static Logger logger = LoggerFactory.getLogger(AccountModifyFacadeImpl.class);

	@Resource
	private AccountService accountService;
	@Resource
	private AccountChangeDetailService accountChangeDetailService;

	/*
	 * (non-Javadoc)
	 * @see com.yl.account.core.facade.AccountModifyFacade#modifyAccountInfo(com.yl.account.api.bean.request.AccountModify)
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BussinessRuntimeException.class)
	@Override
	public AccountModifyResponse modifyAccountInfo(AccountBussinessInterfaceBean accountBussinessInterfaceBean) {
		AccountModifyResponse accountModifyResponse = null;
		try {
			AccountModify accountModify = JsonUtils.toJsonToObject(accountBussinessInterfaceBean, AccountModify.class);

			logger.info("账务系统接收业务系统[账户更新]指令：{}", accountModify);

			Account account = accountService.findAccountByCode(accountModify.getAccountNo());
			if (account == null) throw new BussinessException(ExceptionMessages.ACCOUNT_NOT_EXISTS);

			accountChangeDetailService.create(accountModify.getBussinessCode(), accountModify.getRequestTime(), accountModify.getAccountNo(), AccountType.CASH,
					AccountStatus.valueOf(accountModify.getAccountStatus().name()), accountModify.getSystemCode(), accountModify.getSystemFlowId(),
					accountModify.getUserNo(), UserRole.CUSTOMER, account.getBalance(), account.getFreezeBalance(), account.getTransitBalance(), null, null,
					accountModify.getOperator(), accountBussinessInterfaceBean.getRemark());
			account.setCycle(accountModify.getCycle()!=null?accountModify.getCycle():account.getCycle());//传没传入账周期 如果传就修改为传入的值
			accountService.modifyAccountInfo(account, AccountStatus.valueOf(accountModify.getAccountStatus().name()));

			accountModifyResponse = new AccountModifyResponse();
			accountModifyResponse.setResult(HandlerResult.SUCCESS);
			accountModifyResponse.setAccountNo(account.getCode());
			accountModifyResponse.setFinishTime(new Date());
		} catch (Exception e) {
			logger.error("{}", e);
			throw new BussinessRuntimeException(e.getMessage());
		}
		return accountModifyResponse;
	}
}
