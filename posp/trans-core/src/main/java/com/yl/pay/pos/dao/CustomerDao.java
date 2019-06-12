package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.Customer;

import java.util.List;

/**
 * Title: Order Dao
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */
public interface CustomerDao {

	//根据ID查询
	public Customer findById(Long id);
	
	//根据商户编号查询
	public Customer findByCustomerNo(String customerNo);

	//创建
	public Customer create(Customer customer);
	
	//更新
	public Customer update(Customer customer);
	
	//删除
	public void delete(Customer customer);
	
	public List<Customer> findCustomers() ;
	
}
