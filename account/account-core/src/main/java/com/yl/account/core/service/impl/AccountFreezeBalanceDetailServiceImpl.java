/**
 * 
 */
package com.yl.account.core.service.impl;

import com.yl.account.core.dao.AccountFreezeBalanceDetailDao;
import com.yl.account.core.service.AccountFreezeBalanceDetailService;
import com.yl.account.core.utils.CodeBuilder;
import com.yl.account.enums.Currency;
import com.yl.account.model.AccountFreezeBalanceDetail;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 冻结资金明细业务处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月22日
 * @version V1.0.0
 */
@Service
public class AccountFreezeBalanceDetailServiceImpl implements AccountFreezeBalanceDetailService {

	@Resource
	private AccountFreezeBalanceDetailDao accountFreezeBalanceDetailDao;

	/*
	 * (non-Javadoc)
	 * @see com.yl.account.core.service.AccountFreezeBalanceDetailService#create(com.yl.account.model.AccountFreezeBalanceDetail)
	 */
	@Override
	public void create(String systemCode, String accountNo, String transOrder, double amount, Date freezeLimit, String freezeHandleType) {
		AccountFreezeBalanceDetail accountFreezeBalanceDetail = new AccountFreezeBalanceDetail();
		accountFreezeBalanceDetail.setCode(CodeBuilder.getOrderIdByUUId());
		accountFreezeBalanceDetail.setAccountNo(accountNo);
		accountFreezeBalanceDetail.setCurrency(Currency.RMB);
		accountFreezeBalanceDetail.setHandleTime(new Date());
		accountFreezeBalanceDetail.setSystemCode(systemCode);
		accountFreezeBalanceDetail.setTransFlow(transOrder);
		accountFreezeBalanceDetail.setTransAmount(amount);
		accountFreezeBalanceDetail.setFreezeLimit(freezeLimit);
		accountFreezeBalanceDetail.setFreezeHandleType(freezeHandleType);
		accountFreezeBalanceDetailDao.create(accountFreezeBalanceDetail);
	}

	/*
	 * (non-Javadoc)
	 * @see com.yl.account.core.service.AccountFreezeBalanceDetailService#findBy(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean findBy(String systemCode, String systemFlowId, String bussinessCode, double consultAmt) {
		return accountFreezeBalanceDetailDao.findBy(systemCode, systemFlowId, bussinessCode, consultAmt);
	}
}
