package com.yl.boss.dao;

import com.yl.boss.entity.ReportCustomer;

public interface ReportCustomerDao {
	/**
	 * 创建报备信息
	 * @param reportCustomer
	 */
	void create(ReportCustomer reportCustomer);
	/**
	 * 更新报备信息
	 * @param reportCustomer
	 */
	void update(ReportCustomer reportCustomer);
	/**
	 * 根据id查询报备信息
	 * @param id
	 * @return
	 */
	ReportCustomer findById(long id);
}
