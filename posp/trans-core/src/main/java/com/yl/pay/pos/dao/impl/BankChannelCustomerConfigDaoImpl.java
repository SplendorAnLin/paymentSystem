package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.BankChannelCustomerConfigDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.BankChannelCustomerConfig;
import com.yl.pay.pos.enums.BankCustomerLevel;
import com.yl.pay.pos.enums.Status;

import java.util.List;


/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun
 */

public class BankChannelCustomerConfigDaoImpl implements BankChannelCustomerConfigDao{
	private EntityDao entityDao;

	@Override
	public BankChannelCustomerConfig create(BankChannelCustomerConfig bankChannelCustomerConfig) {
		entityDao.persist(bankChannelCustomerConfig);
		return bankChannelCustomerConfig;
	}

	@Override
	public List<BankChannelCustomerConfig> findByBankInterfaceAndBankChannelCodeAndBankCustomerNo(
			String bankInterfaceCode, String bankChannelCode,
			String bankCustomerNo) {	
		String hql="from BankChannelCustomerConfig b where b.bankInterface.code=? and b.bankChannelCode=? and b.bankCustomerNo=? and b.status=?";
		return entityDao.find( hql, bankInterfaceCode,bankChannelCode,bankCustomerNo,Status.TRUE);
	}

	@Override
	public List<BankChannelCustomerConfig> findByBankInterfaceAndBankChannelCodeAndCustomerNo(String bankInterfaceCode, String bankChannelCode,
			String customerNo,BankCustomerLevel customerLevel) {
		String hql="from BankChannelCustomerConfig b where b.bankInterface.code=? and b.bankChannelCode=? and b.customerLevel=? and b.status=? ";
		if(BankCustomerLevel.CUSTOMER.equals(customerLevel)){
			hql+=" and b.customerNo=?";
			return entityDao.find(hql, bankInterfaceCode,bankChannelCode,customerLevel,Status.TRUE,customerNo);
		}else{
			return entityDao.find(hql, bankInterfaceCode,bankChannelCode,customerLevel,Status.TRUE);
		}
	}

	@Override
	public List<BankChannelCustomerConfig> findByBankInterfaceAndChannelAndCustomerNo(
			String bankInterfaceCode,String bankChannelCode, String bankCustomerNo, String customerNo) {
		String hql="from BankChannelCustomerConfig b where b.bankInterface.code=? and b.bankChannelCode=? and b.bankCustomerNo=? and b.customerNo=? ";
		return entityDao.find( hql, bankInterfaceCode,bankChannelCode,bankCustomerNo,customerNo);
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}
	
	
	
}	


