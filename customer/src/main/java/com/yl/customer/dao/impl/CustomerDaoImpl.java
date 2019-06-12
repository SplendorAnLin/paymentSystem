package com.yl.customer.dao.impl;

import java.util.List;

import com.yl.customer.dao.CustomerDao;
import com.yl.customer.dao.EntityDao;
import com.yl.customer.entity.Customer;

/**
 * 商户数据访问接口处理
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月3日
 * @version V1.0.0
 */
public class CustomerDaoImpl implements CustomerDao {

	private EntityDao entityDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public Customer findByUserName(String username) {
		String hql = "from Customer b where name = ?";
		List<Customer> list = entityDao.find(hql, username);
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Customer findByCustomerNo(String customerNo) {
		String hql = "from Customer b where customerNo = ?";
		List<Customer> list = entityDao.find(hql, customerNo);
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void create(Customer busCustomer) {
		entityDao.persist(busCustomer);
		String id = String.valueOf(busCustomer.getId());
		if(id.length() < 5){
			int size = id.length();
			for(int i = 0; i < 5-size-1;i++){
				id = "0"+id;
			}
			id = "1" + id;
			busCustomer.setCustomerNo(id);
			entityDao.update(busCustomer);
		}
		
	}

	@Override
	public void update(Customer busCustomer) {
		entityDao.update(busCustomer);
	}

	@Override
	public Customer findById(Long id) {
		return entityDao.findById(Customer.class, id);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
