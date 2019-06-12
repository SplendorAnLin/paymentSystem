package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.BankCustomerConfigDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.BankChannel;
import com.yl.pay.pos.entity.BankCustomerConfig;
import com.yl.pay.pos.enums.Status;

import java.util.List;

/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2012
 * Company: com.yl.pay
 * @author jun
 */

public class BankCustomerConfigDaoImpl implements BankCustomerConfigDao {
	private EntityDao entityDao;
	
	@Override
	public BankCustomerConfig findById(Long id) {
		return entityDao.findById(BankCustomerConfig.class, id);
	}

	@Override
	public List<BankCustomerConfig> findByBankInterfaceAndBankChannelAndBankCustomerNo(String bankInterface, BankChannel bankChannel, String bankCustomerNo) {
		String hql="from BankCustomerConfig g where g.bankInterface=? and g.bankChannelCode=? and g.bankCustomerNo=? and g.status=? ";
		return entityDao.find(hql, bankInterface,bankChannel.getCode(),bankCustomerNo,Status.TRUE);
	}

	@Override
	public List<BankCustomerConfig> findByBankInterfaceAndBankChannelAndCustomerNo(
			String bankInterface, BankChannel bankChannel, String customerNo) {
		String hql="from BankCustomerConfig g where g.bankInterface=? and g.bankChannelCode=? and g.customerNo=? and g.status=? ";
		return entityDao.find(hql, bankInterface,bankChannel.getCode(),customerNo,Status.TRUE);
	}
	
	@Override
	public List<BankCustomerConfig> findByBankInterfaceAndBankChannelAndCustomerNoAndPosCati(
			String bankInterface, BankChannel bankChannel, String customerNo,String posCati) {
		String hql="from BankCustomerConfig g where g.bankInterface=? and g.bankChannelCode=? and g.customerNo=? and g.posCati=? and g.status=? ";
		return entityDao.find(hql, bankInterface,bankChannel.getCode(),customerNo,posCati,Status.TRUE);
	}

	@Override
	public List<BankCustomerConfig> findByBankInterfaceAndBankCustomerNoAndCustomerNo(
			String bankInterface, String bankCustomerNo, String customerNo) {
		String hql="from BankCustomerConfig g where g.bankInterface=? and g.bankCustomerNo=? and g.customerNo=? and g.status=? ";
		return entityDao.find(hql, bankInterface,bankCustomerNo,customerNo,Status.TRUE);
	}

	
	@Override
	public BankCustomerConfig findByBankCustomerNo(String bankCustomerNo) {
		String hql="from BankCustomerConfig g where g.bankCustomerNo=? and g.status=? ";
		return (BankCustomerConfig) entityDao.find(hql,bankCustomerNo,Status.TRUE).get(0);
	}
	
	@Override
	public BankCustomerConfig create(BankCustomerConfig config) {
		entityDao.persist(config);
		return config;
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}


}
