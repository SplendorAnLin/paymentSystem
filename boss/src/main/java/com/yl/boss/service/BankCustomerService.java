package com.yl.boss.service;

import java.util.List;

import com.lefu.commons.utils.Page;
import com.yl.boss.entity.BankCustomer;

public interface BankCustomerService {

	/**
	 * 根据地区查询银行商户
	 * @param page
	 * @param category
	 * @return
	 */
	public List<BankCustomer> findByOrg(Page<BankCustomer> page,String org,String category);
	
	
	/**
	 * 根据地区查询银行商户个数
	 * @param page
	 * @param category
	 * @return
	 */
	public Integer findCountByOrg(String org, String category);
	
	/**
	 * 根据银行商户编号查询
	 * @param page
	 * @param org
	 * @return
	 */
	public BankCustomer findByCustomerNo(String bankCustomerNo);
    
}
