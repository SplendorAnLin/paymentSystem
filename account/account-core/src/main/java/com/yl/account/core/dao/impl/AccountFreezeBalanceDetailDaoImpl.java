/**
 * 
 */
package com.yl.account.core.dao.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yl.account.core.dao.AccountFreezeBalanceDetailDao;
import com.yl.account.core.dao.mapper.AccountFreezeBalanceDetailBaseMapper;
import com.yl.account.model.AccountFreezeBalanceDetail;

/**
 * 冻结资金明细数据处理接口实现实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月22日
 * @version V1.0.0
 */
@Repository("accountFreezeBalanceDetailDao")
public class AccountFreezeBalanceDetailDaoImpl implements AccountFreezeBalanceDetailDao {

	@Resource
	private AccountFreezeBalanceDetailBaseMapper accountFreezeBalanceDetailBaseMapper;

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountFreezeBalanceDetailDao#create(com.lefu.account.model.AccountFreezeBalanceDetail)
	 */
	@Override
	public void create(AccountFreezeBalanceDetail accountFreezeBalanceDetail) {
		accountFreezeBalanceDetail.setCreateTime(new Date());
		accountFreezeBalanceDetail.setVersion(System.currentTimeMillis());
		accountFreezeBalanceDetailBaseMapper.create(accountFreezeBalanceDetail);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountFreezeBalanceDetailDao#findBy(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean findBy(String systemCode, String systemFlowId, String bussinessCode, double consultAmt) {
		return accountFreezeBalanceDetailBaseMapper.findBy(systemCode, systemFlowId, bussinessCode, consultAmt) != 0 ? true : false;
	}
}
