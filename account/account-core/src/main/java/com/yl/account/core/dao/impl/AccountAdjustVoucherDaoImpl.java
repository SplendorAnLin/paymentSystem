/**
 * 
 */
package com.yl.account.core.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.lefu.commons.utils.Page;
import com.yl.account.core.dao.AccountAdjustVoucherDao;
import com.yl.account.core.dao.mapper.AccountAdjustVoucherMapper;
import com.yl.account.model.AccountAdjustVoucher;

/**
 * 账户调账数据逻辑处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月22日
 * @version V1.0.0
 */
@Repository("accountAdjustVoucherDao")
public class AccountAdjustVoucherDaoImpl implements AccountAdjustVoucherDao {

	@Resource
	private AccountAdjustVoucherMapper accountAdjustVoucherMapper;

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountAdjustVoucherDao#findBy(java.util.Map)
	 */
	@Override
	public List<AccountAdjustVoucher> findBy(Map<String, Object> params) {
		return accountAdjustVoucherMapper.findBy(params);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountAdjustVoucherDao#modify(com.lefu.account.model.AccountAdjustVoucher, long)
	 */
	@Override
	public void modify(AccountAdjustVoucher accountAdjustVoucher, long version) {
		accountAdjustVoucherMapper.modify(accountAdjustVoucher, version);
	}

	@Override
	public AccountAdjustVoucher findVoucherBy(String system, String systemFlowId, String bussinessCode, String accountNo) {
		return accountAdjustVoucherMapper.findVoucherBy(system, systemFlowId, bussinessCode, accountNo);
	}

	@Override
	public void insert(AccountAdjustVoucher accountAdjustVoucher) {
		Date createTime = new Date();
		accountAdjustVoucher.setCreateTime(createTime);
		accountAdjustVoucher.setLastModifyTime(createTime);
		accountAdjustVoucher.setVersion(System.currentTimeMillis());
		accountAdjustVoucherMapper.insert(accountAdjustVoucher);
	}

	@Override
	public void modifyHandleStatusAndAdjustStatus(AccountAdjustVoucher accountAdjustVoucher, long version) {
		accountAdjustVoucherMapper.modifyHandleStatusAndAdjustStatus(accountAdjustVoucher, version);
	}

	@Override
	public AccountAdjustVoucher findByCode(String code) {
		return accountAdjustVoucherMapper.findByCode(code);
	}

	@Override
	public void modifyVoucher(AccountAdjustVoucher accountAdjustVoucher, long version) {
		accountAdjustVoucherMapper.modifyVoucher(accountAdjustVoucher, version);

	}

	@Override
	public List<AccountAdjustVoucher> findAllAdjHistory(String accountNo ,Page<?> page) {
		return accountAdjustVoucherMapper.findAllAdjHistory(accountNo,page);
	}
}
