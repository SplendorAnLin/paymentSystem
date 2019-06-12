package com.yl.pay.pos.dao.impl;


import com.yl.pay.pos.dao.BankInterfaceMccConfigDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.BankInterfaceMccConfig;
import com.yl.pay.pos.entity.BankMccClassConfig;
import com.yl.pay.pos.enums.Status;
import com.yl.pay.pos.enums.YesNo;

public class BankInterfaceMccConfigDaoImpl implements BankInterfaceMccConfigDao {

	private EntityDao entityDao;
	@Override
	public BankInterfaceMccConfig findBy(String bankInterfaceCode,YesNo isOPenConfig) {
		String hql="from BankInterfaceMccConfig bmc where bmc.bankInterfaceCode=? and bmc.isOpenConfig=?";
		return entityDao.findUnique(BankInterfaceMccConfig.class,hql,bankInterfaceCode,isOPenConfig);
	}

	@Override
	public BankMccClassConfig findBy(String bankInterfaceCode, String bankMcc,Status status) {
		String hql="from BankMccClassConfig bcc where  bcc.bankInterfaceCode=? and bcc.bankMcc=? and bcc.status=?";
		return entityDao.findUnique(BankMccClassConfig.class, hql, bankInterfaceCode,bankMcc,status);
	}

	@Override
	public BankMccClassConfig findBy(String bankInterfaceCode,String bankMccCategory, YesNo isFlag,Status status) {
		String hql="from BankMccClassConfig bcc where  bcc.bankInterfaceCode=? and bcc.bankMccCategory=? and bcc.isFlag=? and bcc.status=?";
		return entityDao.findUnique(BankMccClassConfig.class, hql, bankInterfaceCode,bankMccCategory,isFlag,status);
	}
	
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	

	

	
	

}
