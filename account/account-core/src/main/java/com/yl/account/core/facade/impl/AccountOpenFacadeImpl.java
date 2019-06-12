/**
 * 
 */
package com.yl.account.core.facade.impl;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.request.AccountOpen;
import com.yl.account.api.bean.response.AccountOpenResponse;
import com.yl.account.api.enums.HandlerResult;
import com.yl.account.core.ExceptionMessages;
import com.yl.account.core.exception.BussinessRuntimeException;
import com.yl.account.core.facade.AccountOpenFacade;
import com.yl.account.core.service.AccountChangeDetailService;
import com.yl.account.core.service.AccountService;
import com.yl.account.core.utils.CodeBuilder;
import com.yl.account.enums.AccountStatus;
import com.yl.account.enums.AccountType;
import com.yl.account.enums.UserRole;
import com.yl.account.model.AccountChangeDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 开户接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月9日
 * @version V1.0.0
 */
@Component
public class AccountOpenFacadeImpl implements AccountOpenFacade {

	private static Logger logger = LoggerFactory.getLogger(AccountOpenFacadeImpl.class);

	@Resource
	private AccountService accountService;
	@Resource
	private AccountChangeDetailService accountChangeDetailService;

	/*
	 * (non-Javadoc)
	 * @see com.yl.account.core.facade.AccountOpenFacade#openAccount(com.yl.account.api.bean.request.AccountOpen)
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BussinessRuntimeException.class)
	@Override
	public AccountOpenResponse openAccount(AccountBussinessInterfaceBean accountBussinessInterfaceBean) {
		try {
			AccountOpen accountOpen = JsonUtils.toJsonToObject(accountBussinessInterfaceBean, AccountOpen.class);

			logger.info("账务系统接收业务系统[开户]指令：{}", accountOpen);

			AccountChangeDetail accountChangeDetail = accountChangeDetailService.findAccountBySystemId(accountOpen.getSystemCode(), accountOpen.getSystemFlowId(),
					accountBussinessInterfaceBean.getBussinessCode());

			String accountNo = "";
			if (accountChangeDetail == null) {
				accountNo = CodeBuilder.getOrderIdByUUId();

				try {
					accountChangeDetailService.create(accountOpen.getBussinessCode(), accountOpen.getRequestTime(), accountNo,
							AccountType.valueOf(accountOpen.getAccountType().name()), AccountStatus.NORMAL, accountOpen.getSystemCode(),
							accountOpen.getSystemFlowId(), accountOpen.getUserNo(), UserRole.valueOf(accountOpen.getUserRole().name()), 0d, 0d, 0d, null, null,
							accountOpen.getOperator(), accountBussinessInterfaceBean.getRemark());
				} catch (DuplicateKeyException e) {
					accountChangeDetail = accountChangeDetailService.findAccountBySystemId(accountOpen.getSystemCode(), accountOpen.getSystemFlowId(),
							accountBussinessInterfaceBean.getBussinessCode());
					return constructResponse(accountOpen, accountChangeDetail.getAccountNo());
				}

				accountService.openAccount(accountOpen, accountNo);
			} else accountNo = accountChangeDetail.getAccountNo();

			return constructResponse(accountOpen, accountNo);
		} catch (Exception e) {
			logger.error("{}", e);
			throw new BussinessRuntimeException(ExceptionMessages.DATABASE_OPERATOR_ERROR);
		}
	}

	/**
	 * @Description 填充响应报文信息
	 * @param accountOpen 账户开户请求信息
	 * @param accountNo 账号
	 * @return AccountOpenResponse 开户响应信息
	 * @see 需要参考的类或方法
	 */
	private AccountOpenResponse constructResponse(AccountOpen accountOpen, String accountNo) {
		AccountOpenResponse accountOpenResponse;
		accountOpenResponse = new AccountOpenResponse();
		accountOpenResponse.setAccountNo(accountNo);
		accountOpenResponse.setAccountType(accountOpen.getAccountType());
		accountOpenResponse.setUserNo(accountOpen.getUserNo());
		accountOpenResponse.setResult(HandlerResult.SUCCESS);
		accountOpenResponse.setFinishTime(new Date());
		return accountOpenResponse;
	}
}
