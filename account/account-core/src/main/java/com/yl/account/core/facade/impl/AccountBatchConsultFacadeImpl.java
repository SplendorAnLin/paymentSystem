/**
 * 
 */
package com.yl.account.core.facade.impl;

import java.util.Date;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.request.AccountBatchConsult;
import com.yl.account.api.bean.request.AccountConsult;
import com.yl.account.api.bean.response.AccountBatchConsultResponse;
import com.yl.account.api.bean.response.AccountConsultResponse;
import com.yl.account.api.enums.HandlerResult;
import com.yl.account.api.enums.Status;
import com.yl.account.core.exception.BussinessRuntimeException;
import com.yl.account.core.facade.AccountBatchConsultFacade;
import com.yl.account.core.facade.AccountConsultFacade;

/**
 * 批量请款处理门面实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月9日
 * @version V1.0.0
 */
@Component
public class AccountBatchConsultFacadeImpl implements AccountBatchConsultFacade {

	private static Logger logger = LoggerFactory.getLogger(AccountBatchConsultFacadeImpl.class);

	@Resource
	private AccountConsultFacade accountConsultFacade;

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.facade.AccountBatchConsultFacade#batchConsult(com.lefu.account.api.bean.AccountBussinessInterfaceBean)
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BussinessRuntimeException.class)
	@Override
	public AccountBatchConsultResponse batchConsult(AccountBussinessInterfaceBean accountBussinessInterfaceBean) {
		AccountBatchConsultResponse accountBatchConsultResponse = null;
		try {
			AccountBatchConsult accountBatchConsult = JsonUtils.toJsonToObject(accountBussinessInterfaceBean.getTradeVoucher(), AccountBatchConsult.class);

			logger.info("账务系统接收业务系统[批量请款]指令：{}", accountBatchConsult);

			boolean isSucc = true;
			double remainBalance = 0;

			Set<Entry<String, AccountConsult>> entrySet = accountBatchConsult.getAccountConsults().entrySet();
			for (Entry<String, AccountConsult> entry : entrySet) {

				accountBussinessInterfaceBean.setBussinessCode(entry.getKey());
				accountBussinessInterfaceBean.setTradeVoucher(entry.getValue());

				AccountConsultResponse consultResponse = accountConsultFacade.consult(accountBussinessInterfaceBean);

				if (HandlerResult.FAILED.equals(consultResponse.getResult())) isSucc = false;
				remainBalance = consultResponse.getRemainAmount();
			}

			accountBatchConsultResponse = new AccountBatchConsultResponse();
			accountBatchConsultResponse.setStatus(isSucc ? Status.SUCCESS : Status.FAILED);
			accountBatchConsultResponse.setRemainAmount(remainBalance);
			accountBatchConsultResponse.setResult(HandlerResult.SUCCESS);
			accountBatchConsultResponse.setFinishTime(new Date());
		} catch (Exception e) {
			logger.error("{}", e);
			throw new BussinessRuntimeException(e.getMessage());
		}
		return accountBatchConsultResponse;
	}
}
