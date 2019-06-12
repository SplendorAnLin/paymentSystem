package com.yl.customer.dao;

import com.yl.customer.entity.Customer;

/**
 * 商户数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月3日
 * @version V1.0.0
 */
public interface CustomerDao {

	public Customer findByUserName(String username);
	
	public Customer findByCustomerNo(String customerNo);
	
	public void create(Customer busCustomer);
	
	public void update(Customer busCustomer);
	
	public Customer findById(Long id);
	
}
