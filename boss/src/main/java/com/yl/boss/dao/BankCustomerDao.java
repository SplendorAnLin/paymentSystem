package com.yl.boss.dao;



import java.util.List;

import com.lefu.commons.utils.Page;
import com.yl.boss.entity.BankCustomer;

/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2012
 * Company: com.yl.pay
 * @author jinyan.lou
 */
public interface BankCustomerDao {
	
	/**
	 * 根据银行商户编号查询商户信息
	 * @param bankCustomerNo
	 * @return
	 */
	public BankCustomer findByBankCustomerNo(String bankCustomerNo);
	
	/**
	 * 根据地区查询银行商户
	 * @param page
	 * @param org
	 * @param category
	 * @return
	 */
	public List<BankCustomer> findByOrg(Page<BankCustomer> page,String org, String category);
	
	/**
	 * 根据地区查询银行商户个数
	 * @param page
	 * @param org
	 * @param category
	 * @return
	 */
	public Integer findCountByOrg(String org, String category);
    
}
