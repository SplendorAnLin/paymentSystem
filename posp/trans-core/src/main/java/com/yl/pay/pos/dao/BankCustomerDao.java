package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.BankCustomer;
import com.yl.pay.pos.enums.Status;
import com.yl.pay.pos.enums.YesNo;

import java.util.List;

/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2012
 * Company: com.yl.pay
 * @author jinyan.lou
 */
public interface BankCustomerDao {
	/**
	 * 查询本代本商户号
	 * @param bankInterfaceCode
	 * @param mccCategory
	 * @param mcc
	 * @param status
	 * @param orgCode
	 * @return
	 */
	public List<BankCustomer> findLefuBy(String bankInterfaceCode, String mccCategory, String mcc, Status status, String orgCode);
	/**
	 * 查询非实名备用商户号
	 * @param bankInterfaceCode
	 * @param bankCategory
	 * @param limitAmount
	 * @param isReal
	 * @param status
	 * @return
	 */
	public String findBy(String bankInterfaceCode, String bankCategory, Double limitAmount, YesNo isReal, Status status, String provinCode);
	/**
	 * 校验商户号是否已超额
	 * @param bankInterfaceCode
	 * @param bankCustomerNo
	 * @param limitAmount
	 * @return
	 */
	public boolean getIsLimitAmount(String bankInterfaceCode, String bankCustomerNo, Double limitAmount);
	
	/**
	 * 根据银行商户编号查询商户信息
	 * @param bankCustomerNo
	 * @return
	 */
	public BankCustomer findByBankCustomerNo(String bankCustomerNo);
    
}
