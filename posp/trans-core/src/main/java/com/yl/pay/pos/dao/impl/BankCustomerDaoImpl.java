package com.yl.pay.pos.dao.impl;

import com.pay.common.util.StringUtil;
import com.yl.pay.pos.dao.BankCustomerDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.BankCustomer;
import com.yl.pay.pos.entity.BankCustomerAmountLimit;
import com.yl.pay.pos.enums.Status;
import com.yl.pay.pos.enums.YesNo;
import org.apache.commons.lang.math.RandomUtils;

import java.util.List;

/**
 * Title: Description: Copyright: Copyright (c)2012 Company: com.yl.pay
 * 
 * @author jinyan.lou
 */
@SuppressWarnings("unchecked")
public class BankCustomerDaoImpl implements BankCustomerDao {
	private EntityDao entityDao;

	@Override
	public List<BankCustomer> findLefuBy(final String bankInterfaceCode, final String mccCategory,final String bankMcc,  Status status, String  orgCode) {
		String hql = "from BankCustomer b where 1=1 and b.mccCategory=? and b.status=? and bankInterfaceCode=? ";
		if(StringUtil.notNull(bankMcc)){
			hql+=" and b.mcc='"+bankMcc+"'";
		}
		if(StringUtil.notNull(orgCode)){
			hql+=" and substr(b.organization,0,2)='"+orgCode+"'";
		}
		
		return entityDao.find(hql,mccCategory,status,bankInterfaceCode);
	}


	@Override
	public String findBy(String bankInterfaceCode, String bankCategory,
			Double limitAmount, YesNo isReal, Status status,String provinCode) {
		String finalBankCustomerNo=null;
		String hql="from BankCustomerAmountLimit bac where bac.bankInterfaceCode=? and bac.bankMccCategory=? and bac.mccCategory not in ('11','12','13','14','15','16') and bac.provinCode=? and bac.isReally=? and bac.status=? and bac.transAmount<? and rownum<5";
		List<BankCustomerAmountLimit> bankCustomers=entityDao.find(hql,bankInterfaceCode,bankCategory,provinCode,isReal,status,limitAmount);
		if(bankCustomers!=null&&bankCustomers.size()>0){
			finalBankCustomerNo=bankCustomers.get(RandomUtils.nextInt(bankCustomers.size())).getBankCustomerNo();
		}
		return finalBankCustomerNo;
	}

	@Override
	public boolean getIsLimitAmount(String bankInterfaceCode,
			String bankCustomerNo, Double limitAmount) {
		boolean flag=true;
		String hql="from BankCustomerAmountLimit bac where bac.bankInterfaceCode=? and bac.bankCustomerNo=? and bac.transAmount<?";
		List<BankCustomerAmountLimit> bankCustomers=entityDao.find(hql,bankInterfaceCode,bankCustomerNo,limitAmount);
		if(bankCustomers!=null&&bankCustomers.size()>0){
			if(!Status.TRUE.equals(bankCustomers.get(0).getStatus())){
				flag=false;
			}
		}
		return flag;
	}
	
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}


	@Override
	public BankCustomer findByBankCustomerNo(String bankCustomerNo) {
		String hql = "from BankCustomer b where 1=1 and b.bankCustomerNo=? and b.status=? ";
		return (BankCustomer) entityDao.find(hql,bankCustomerNo,Status.TRUE).get(0);
	}
	
}
