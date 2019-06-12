package com.yl.pay.pos.service;

import com.yl.pay.pos.entity.Customer;

public interface CustomerService {
	//创建
	public boolean create(Customer customer);
	//更新
	public boolean update(Customer customer);
	//根据商户编号查询
	public Customer findByCustomerNo(String customerNo);
}
