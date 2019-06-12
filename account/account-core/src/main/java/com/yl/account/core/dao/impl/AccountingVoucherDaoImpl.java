/**
 * 
 */
package com.yl.account.core.dao.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yl.account.core.dao.AccountingVoucherDao;
import com.yl.account.core.dao.mapper.AccountingVoucherMapper;
import com.yl.account.model.AccountingVoucher;

/**
 * 记账凭证数据处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
@Repository("accountingVoucherDao")
public class AccountingVoucherDaoImpl implements AccountingVoucherDao {

	@Resource
	AccountingVoucherMapper accountingVoucherMapper;
	
	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountingVoucherDao#recordAccountingVoucher()
	 */
	@Override
	public void create(AccountingVoucher accountingVoucher) {
		accountingVoucher.setCreateTime(new Date());
		accountingVoucher.setVersion(System.currentTimeMillis());
		accountingVoucherMapper.create(accountingVoucher);
	}

}
