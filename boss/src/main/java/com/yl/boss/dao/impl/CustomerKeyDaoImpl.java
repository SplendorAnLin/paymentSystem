package com.yl.boss.dao.impl;

import java.util.List;

import com.yl.boss.api.enums.KeyType;
import com.yl.boss.api.enums.ProductType;
import com.yl.boss.dao.CustomerKeyDao;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.entity.CustomerKey;

/**
 * 商户密钥数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public class CustomerKeyDaoImpl implements CustomerKeyDao {
	
	private EntityDao entityDao;

	@Override
	public void create(CustomerKey customerKey) {
		entityDao.persist(customerKey);
	}

	@Override
	public void update(CustomerKey customerKey) {
		entityDao.update(customerKey);
	}

	@Override
	public CustomerKey findById(long id) {
		return entityDao.findById(CustomerKey.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerKey> findByCustNo(String custNo) {
		String hql = "from CustomerKey where customerNo = ?";
		return entityDao.find(hql,custNo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public CustomerKey findBy(String custNo, ProductType productType) {
		String hql = "from CustomerKey where custNo = ? and productType = ?";
		List<CustomerKey> list = entityDao.find(hql,custNo,productType);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CustomerKey findBy(String custNo, KeyType keyType) {
		String hql = "from CustomerKey where customerNo = ? and keyType = ?";
		List<CustomerKey> list = entityDao.find(hql,custNo,keyType);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String findByKey(String key) {
		String hql = "from CustomerKey where public_key = ? and key_type = ?";
		List<CustomerKey> list = entityDao.find(hql,key,KeyType.MD5);
		if(list != null && list.size() > 0){
			return list.get(0).getCustomerNo();
		}
		return null;
	}
}