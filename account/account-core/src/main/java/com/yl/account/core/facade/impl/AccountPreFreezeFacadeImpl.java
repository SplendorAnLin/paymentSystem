/**
 * 
 */
package com.yl.account.core.facade.impl;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.response.AccountPreFreezeResponse;
import com.yl.account.api.enums.HandlerResult;
import com.yl.account.bean.AccountPreFreeze;
import com.yl.account.core.ExceptionMessages;
import com.yl.account.core.exception.BussinessException;
import com.yl.account.core.exception.BussinessRuntimeException;
import com.yl.account.core.facade.AccountPreFreezeFacade;
import com.yl.account.core.service.*;
import com.yl.account.enums.FreezeHandleType;
import com.yl.account.enums.FreezeStatus;
import com.yl.account.model.Account;
import com.yl.account.model.AccountBehavior;
import com.yl.account.model.AccountFreezeDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 预冻处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月9日
 * @version V1.0.0
 */
@Component
public class AccountPreFreezeFacadeImpl implements AccountPreFreezeFacade {

	private static Logger logger = LoggerFactory.getLogger(AccountPreFreezeFacadeImpl.class);

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
	 * @see com.yl.account.core.facade.AccountPreFreezeFacade#preFreeze(com.yl.account.api.bean.AccountBussinessInterfaceBean)
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BussinessRuntimeException.class)
	@Override
	public AccountPreFreezeResponse preFreeze(AccountBussinessInterfaceBean accountBussinessInterfaceBean) {
		AccountPreFreezeResponse accountPreFreezeResponse = null;
		try {
			AccountPreFreeze accountPreFreeze = JsonUtils.toJsonToObject(accountBussinessInterfaceBean.getTradeVoucher(), AccountPreFreeze.class);

			logger.info("账务系统接收业务系统[预冻]指令：{}", accountPreFreeze);

			Account account = accountService.findAccountByCode(accountPreFreeze.getAccountNo());
			if (account == null) throw new BussinessException(ExceptionMessages.ACCOUNT_NOT_EXISTS);

			accountChangeDetailService.create(accountBussinessInterfaceBean.getBussinessCode(), accountBussinessInterfaceBean.getRequestTime(),
					accountPreFreeze.getAccountNo(), account.getType(), account.getStatus(), accountBussinessInterfaceBean.getSystemCode(),
					accountBussinessInterfaceBean.getSystemFlowId(), account.getUserNo(), account.getUserRole(), account.getBalance(), account.getFreezeBalance(),
					account.getTransitBalance(), null, accountPreFreeze.getPreFreezeAmount(), accountBussinessInterfaceBean.getOperator(),
					accountBussinessInterfaceBean.getRemark());

			accountFreezeBalanceDetailService.create(accountBussinessInterfaceBean.getSystemCode(), accountPreFreeze.getAccountNo(),
					accountBussinessInterfaceBean.getSystemFlowId(), accountPreFreeze.getPreFreezeAmount(), accountPreFreeze.getFreezeLimit(),
					FreezeHandleType.PRE_FREEZE.name());

			double valueableBalance = AmountUtils.subtract(account.getBalance(), AmountUtils.add(account.getTransitBalance(), account.getFreezeBalance()));

			AccountFreezeDetail accountFreezeDetail = null;
			if (accountPreFreeze.getPreFreezeAmount() <= valueableBalance) {
				accountFreezeDetail = accountFreezeDetailService.createPreFreezeDetail(accountBussinessInterfaceBean, accountPreFreeze.getAccountNo(),
						accountPreFreeze.getPreFreezeAmount(), accountPreFreeze.getFreezeLimit(), accountPreFreeze.getPreFreezeAmount(),
						FreezeStatus.PRE_FREEZE_COMPLETE);
				accountService.addFreezeBalance(account, accountPreFreeze.getPreFreezeAmount());
				valueableBalance = AmountUtils.subtract(valueableBalance, accountPreFreeze.getPreFreezeAmount());
			} else {
				accountFreezeDetail = accountFreezeDetailService.createPreFreezeDetail(accountBussinessInterfaceBean, accountPreFreeze.getAccountNo(),
						accountPreFreeze.getPreFreezeAmount(), accountPreFreeze.getFreezeLimit(), valueableBalance, FreezeStatus.PRE_FREEZE_ING);

				accountService.addFreezeBalance(account, valueableBalance);
				AccountBehavior accountBehavior = accountBehaviorService.findAccountBehavior(account.getCode());
				if (accountBehavior == null) accountBehaviorService.insert(account);
				else {
					accountBehavior.setPreFreezeCount(accountBehavior.getPreFreezeCount() + 1);
					accountBehaviorService.addOrSubtractPreFreezeCount(accountBehavior);
				}
				valueableBalance = 0;
			}

			accountPreFreezeResponse = new AccountPreFreezeResponse();
			accountPreFreezeResponse.setResult(HandlerResult.SUCCESS);
			accountPreFreezeResponse.setFinishTime(new Date());
			accountPreFreezeResponse.setFreezeNo(accountFreezeDetail.getFreezeNo());
			accountPreFreezeResponse.setRemainBalance(valueableBalance);
		} catch (Exception e) {
			logger.error("{}", e);
			throw new BussinessRuntimeException(e.getMessage());
		}
		return accountPreFreezeResponse;
	}
}
