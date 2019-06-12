package com.yl.pay.pos.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lefu.commons.utils.lang.DateUtils;
import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.request.AccountQuery;
import com.yl.account.api.bean.request.AccountSpecialCompositeTally;
import com.yl.account.api.bean.request.SpecialTallyVoucher;
import com.yl.account.api.bean.response.AccountCompositeTallyResponse;
import com.yl.account.api.bean.response.AccountQueryResponse;
import com.yl.account.api.dubbo.AccountInterface;
import com.yl.account.api.dubbo.AccountQueryInterface;
import com.yl.account.api.enums.AccountType;
import com.yl.account.api.enums.Currency;
import com.yl.account.api.enums.FundSymbol;
import com.yl.account.api.enums.HandlerResult;
import com.yl.account.api.enums.Status;
import com.yl.account.api.enums.UserRole;
import com.yl.pay.pos.entity.Order;
import com.yl.pay.pos.entity.ProxyPayInfo;
import com.yl.pay.pos.enums.YesNo;
import com.yl.pay.pos.service.AccountBussinessService;

public class AccountBussinessServiceImpl implements AccountBussinessService{
	private static final Log log = LogFactory.getLog(AccountBussinessServiceImpl.class);
	private AccountInterface accountInterface;
	private AccountQueryInterface accountQueryInterface;
	
	public AccountInterface getAccountInterface() {
		return accountInterface;
	}

	public void setAccountInterface(AccountInterface accountInterface) {
		this.accountInterface = accountInterface;
	}

	public AccountQueryInterface getAccountQueryInterface() {
		return accountQueryInterface;
	}

	public void setAccountQueryInterface(AccountQueryInterface accountQueryInterface) {
		this.accountQueryInterface = accountQueryInterface;
	}

	@Override
	public void specialCompositeTally(Order order) {
		try {
			AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
			accountBussinessInterfaceBean.setBussinessCode("POS_CREDIT");
			accountBussinessInterfaceBean.setOperator("POSP");
			accountBussinessInterfaceBean.setRemark("收单入账");
			accountBussinessInterfaceBean.setRequestTime(new Date());
			accountBussinessInterfaceBean.setSystemCode("POSP");
			accountBussinessInterfaceBean.setSystemFlowId(order.getExternalId());

			AccountSpecialCompositeTally accountCompositeTally = new AccountSpecialCompositeTally();

			// 交易金额
			SpecialTallyVoucher amount = new SpecialTallyVoucher();
			amount.setAccountType(AccountType.CASH);
			amount.setUserNo(order.getCustomerNo());
			amount.setUserRole(UserRole.CUSTOMER);
			amount.setAmount(order.getAmount());
			amount.setSymbol(FundSymbol.PLUS);
			amount.setCurrency(Currency.RMB);
			amount.setFee(order.getCustomerFee());
			amount.setFeeSymbol(FundSymbol.SUBTRACT);
			amount.setTransDate(order.getCompleteTime());
			amount.setTransOrder(order.getExternalId());

			AccountQuery accountQuery = new AccountQuery();
			accountQuery.setUserNo(order.getCustomerNo());
			accountQuery.setUserRole(UserRole.CUSTOMER);
			accountQuery.setAccountType(AccountType.CASH);
			AccountQueryResponse accountQueryResponse = accountQueryInterface.findAccountBy(accountQuery);

			if (accountQueryResponse.getAccountBeans().get(0).getCycle() == 0) {
				amount.setWaitAccountDate(new Date());
			} else {
				amount.setWaitAccountDate(DateUtils.addDays(new Date(), accountQueryResponse.getAccountBeans().get(0).getCycle()));
			}

			List<SpecialTallyVoucher> tallyVouchers = new ArrayList<SpecialTallyVoucher>();
			tallyVouchers.add(amount);

			accountCompositeTally.setSpecialTallyVouchers(tallyVouchers);
			accountBussinessInterfaceBean.setTradeVoucher(accountCompositeTally);

			AccountCompositeTallyResponse accountCompositeTallyResponse = accountInterface.specialCompositeTally(accountBussinessInterfaceBean);
			Status status = accountCompositeTallyResponse.getStatus();
			HandlerResult handleResult = accountCompositeTallyResponse.getResult();
			if (!(Status.SUCCESS.equals(status) && HandlerResult.SUCCESS.equals(handleResult)))
				throw new RuntimeException("POS支付调用新账务系统,入账失败!");

			// 入账成功
			order.setCreditStatus(YesNo.Y);
			order.setCreditTime(new Date());
		} catch (Exception e) {
			log.error("account failed accounting !... [tradeOrder:{"+order.getExternalId()+"}]", e);
		}
		
	}
	
	@Override
	public void specialCompositeTally(ProxyPayInfo proxyPayInfo) {
		try {
			AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
			accountBussinessInterfaceBean.setBussinessCode("DPAY_DEBIT");
			accountBussinessInterfaceBean.setOperator("POSP");
			accountBussinessInterfaceBean.setRemark("收单入账");
			accountBussinessInterfaceBean.setRequestTime(new Date());
			accountBussinessInterfaceBean.setSystemCode("POSP");
			accountBussinessInterfaceBean.setSystemFlowId(proxyPayInfo.getExternalId());

			AccountSpecialCompositeTally accountCompositeTally = new AccountSpecialCompositeTally();

			// 交易金额
			SpecialTallyVoucher amount = new SpecialTallyVoucher();
			amount.setAccountType(AccountType.CASH);
			amount.setUserNo(proxyPayInfo.getCustomerNo());
			amount.setUserRole(UserRole.CUSTOMER);
			amount.setAmount(Double.parseDouble(proxyPayInfo.getCustAmount()));
			amount.setSymbol(FundSymbol.SUBTRACT);
			amount.setCurrency(Currency.RMB);
			amount.setFee(Double.parseDouble(proxyPayInfo.getProxyPayCost()));
			amount.setFeeSymbol(FundSymbol.SUBTRACT);
			amount.setTransDate(proxyPayInfo.getOrderCompleteTime());
			amount.setTransOrder(proxyPayInfo.getExternalId());

			AccountQuery accountQuery = new AccountQuery();
			accountQuery.setUserNo(proxyPayInfo.getCustomerNo());
			accountQuery.setUserRole(UserRole.CUSTOMER);
			accountQuery.setAccountType(AccountType.CASH);
			AccountQueryResponse accountQueryResponse = accountQueryInterface.findAccountBy(accountQuery);

			if (accountQueryResponse.getAccountBeans().get(0).getCycle() == 0) {
				amount.setWaitAccountDate(new Date());
			} else {
				amount.setWaitAccountDate(DateUtils.addDays(new Date(), accountQueryResponse.getAccountBeans().get(0).getCycle()));
			}

			List<SpecialTallyVoucher> tallyVouchers = new ArrayList<SpecialTallyVoucher>();
			tallyVouchers.add(amount);

			accountCompositeTally.setSpecialTallyVouchers(tallyVouchers);
			accountBussinessInterfaceBean.setTradeVoucher(accountCompositeTally);

			AccountCompositeTallyResponse accountCompositeTallyResponse = accountInterface.specialCompositeTally(accountBussinessInterfaceBean);
			Status status = accountCompositeTallyResponse.getStatus();
			HandlerResult handleResult = accountCompositeTallyResponse.getResult();
			if (!(Status.SUCCESS.equals(status) && HandlerResult.SUCCESS.equals(handleResult)))
				throw new RuntimeException("POS支付调用新账务系统,入账失败!");


		} catch (Exception e) {
			log.error("account failed accounting !... [tradeOrder:{"+proxyPayInfo.getExternalId()+"}]", e);
		}
		
	}

}
