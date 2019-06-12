/**
 * 
 */
package com.yl.account.core.facade.impl;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.response.AccountCompositeTallyResponse;
import com.yl.account.api.enums.HandlerResult;
import com.yl.account.api.enums.Status;
import com.yl.account.bean.AccountCompositeTally;
import com.yl.account.bean.AccountSpecialCompositeTally;
import com.yl.account.bean.AccountSpecialTransitVoucher;
import com.yl.account.bean.AccountTransitVoucher;
import com.yl.account.bean.SpecialTallyVoucher;
import com.yl.account.bean.TallyVoucher;
import com.yl.account.core.C;
import com.yl.account.core.exception.BussinessRuntimeException;
import com.yl.account.core.facade.AccountCompositeTallyFacade;
import com.yl.account.core.facade.AccountTallyFacade;
import com.yl.account.enums.CompositeTallyType;
import com.yl.account.enums.FundSymbol;
import com.yl.account.enums.MessageType;
import com.yl.mq.producer.client.ProducerClient;
import com.yl.mq.producer.client.ProducerMessage;

/**
 * 复合记账处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月9日
 * @version V1.0.0
 */
@Component
public class AccountCompositeTallyFacadeImpl implements AccountCompositeTallyFacade {

	private static Logger logger = LoggerFactory.getLogger(AccountCompositeTallyFacadeImpl.class);

	@Resource
	private Map<String, AccountTallyFacade> accountTallyFacades;

	/*
	 * (non-Javadoc)
	 * @see com.yl.account.core.facade.AccountCompositeTallyFacade#compositeTally(com.yl.account.api.bean.AccountBussinessInterfaceBean)
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BussinessRuntimeException.class)
	@Override
	public AccountCompositeTallyResponse standardCompositeTally(AccountBussinessInterfaceBean accountBussinessInterfaceBean) {
		AccountCompositeTallyResponse accountCompositeTallyResponse = null;
		try {
			AccountCompositeTally accountCompositeTally = JsonUtils.toJsonToObject(accountBussinessInterfaceBean.getTradeVoucher(), AccountCompositeTally.class);

			for (TallyVoucher tallyVoucher : accountCompositeTally.getTallyVouchers()) {

				FundSymbol symbol = tallyVoucher.getSymbol();
				if (tallyVoucher instanceof AccountTransitVoucher) symbol = FundSymbol.SUBTRACT;

				AccountTallyFacade accountTallyFacade = accountTallyFacades
						.get(StringUtils.concatToSB(CompositeTallyType.STANDARD.name(), symbol.name()).toString());
				accountTallyFacade.tally(tallyVoucher, accountBussinessInterfaceBean);
			}

			accountCompositeTallyResponse = new AccountCompositeTallyResponse();
			accountCompositeTallyResponse.setFinishTime(new Date());
			accountCompositeTallyResponse.setResult(HandlerResult.SUCCESS);
			accountCompositeTallyResponse.setStatus(Status.SUCCESS);
		} catch (Exception e) {
			logger.error("{}", e);
			throw new BussinessRuntimeException(e.getMessage());
		}
		return accountCompositeTallyResponse;
	}

	/*
	 * (non-Javadoc)
	 * @see com.yl.account.core.facade.AccountCompositeTallyFacade#specialCompositeTally(com.yl.account.api.bean.AccountBussinessInterfaceBean)
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BussinessRuntimeException.class)
	@Override
	public AccountCompositeTallyResponse specialCompositeTally(AccountBussinessInterfaceBean accountBussinessInterfaceBean) {
		AccountCompositeTallyResponse accountCompositeTallyResponse = null;
		try {
			AccountSpecialCompositeTally accountSpecialCompositeTally = JsonUtils.toJsonToObject(accountBussinessInterfaceBean.getTradeVoucher(),
					AccountSpecialCompositeTally.class);

			for (SpecialTallyVoucher specialTallyVoucher : accountSpecialCompositeTally.getSpecialTallyVouchers()) {

				FundSymbol symbol = specialTallyVoucher.getSymbol();
				if (specialTallyVoucher instanceof AccountSpecialTransitVoucher) symbol = FundSymbol.SUBTRACT;

				AccountTallyFacade accountSpecialTallyFacade = accountTallyFacades.get(StringUtils.concatToSB(CompositeTallyType.SPECIAL.name(), symbol.name())
						.toString());
				accountSpecialTallyFacade.tally(specialTallyVoucher, accountBussinessInterfaceBean);
			}

			accountCompositeTallyResponse = new AccountCompositeTallyResponse();
			accountCompositeTallyResponse.setFinishTime(new Date());
			accountCompositeTallyResponse.setResult(HandlerResult.SUCCESS);
			accountCompositeTallyResponse.setStatus(Status.SUCCESS);
		} catch (Exception e) {
			logger.error("{}", e);
			throw new BussinessRuntimeException(e.getMessage());
		}
		return accountCompositeTallyResponse;
	}
}
