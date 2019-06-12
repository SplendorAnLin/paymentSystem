package com.yl.boss.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pay.common.util.Md5Util;
import com.yl.boss.api.enums.KeyType;
import com.yl.boss.api.enums.ProductType;
import com.yl.boss.dao.CustomerKeyDao;
import com.yl.boss.dao.CustomerKeyHistoryDao;
import com.yl.boss.entity.CustomerKey;
import com.yl.boss.entity.CustomerKeyHistory;
import com.yl.boss.service.CustomerKeyService;
import com.yl.boss.utils.RSAUtils;

/**
 * 商户密钥服务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public class CustomerKeyServiceImpl implements CustomerKeyService {
	
	private CustomerKeyDao customerKeyDao;
	private CustomerKeyHistoryDao customerKeyHistoryDao;

	@Override
	public void create(CustomerKey customerKey, String oper) {
		
		if(customerKey.getKeyType() == KeyType.RSA){
			try {
				Map<String, Object> keyMap = RSAUtils.genKeyPair();
				customerKey.setKey(RSAUtils.getPublicKey(keyMap));
				customerKey.setPrivateKey(RSAUtils.getPrivateKey(keyMap));
			} catch (Exception e) {
				throw new RuntimeException("create custmoerKey customerNo:"+customerKey.getCustomerNo()+" error:",e);
			}
		}
		
		if(customerKey.getKeyType() == KeyType.MD5){
			customerKey.setKey(Md5Util.hmacSign(customerKey.getCustomerNo(), String.valueOf(System.nanoTime())));
		}
		customerKey.setCreateTime(new Date());
		customerKeyDao.create(customerKey);
		
		CustomerKeyHistory customerKeyHistory =  new CustomerKeyHistory(customerKey, oper);
		customerKeyHistoryDao.create(customerKeyHistory);
	}

	@Override
	public void update(CustomerKey customerKey, String oper) {
		CustomerKey custKey = customerKeyDao.findById(customerKey.getId());
		if(custKey != null){
			custKey.setKey(customerKey.getKey());
			custKey.setPrivateKey(customerKey.getPrivateKey());
			custKey.setKeyType(customerKey.getKeyType());
			customerKeyDao.update(custKey);
			
			CustomerKeyHistory customerKeyHistory = new CustomerKeyHistory(customerKey, oper);
			customerKeyHistoryDao.create(customerKeyHistory);
		}
	}

	@Override
	public CustomerKey findById(long id) {
		return customerKeyDao.findById(id);
	}

	@Override
	public List<CustomerKey> findByCustNo(String custNo) {
		return customerKeyDao.findByCustNo(custNo);
	}

	@Override
	public CustomerKey findBy(String custNo, ProductType productType) {
		return customerKeyDao.findBy(custNo, productType);
	}

	@Override
	public List<CustomerKeyHistory> findHistByCustNo(String custNo) {
		return customerKeyHistoryDao.findByCustNo(custNo);
	}

	public CustomerKeyDao getCustomerKeyDao() {
		return customerKeyDao;
	}

	public void setCustomerKeyDao(CustomerKeyDao customerKeyDao) {
		this.customerKeyDao = customerKeyDao;
	}

	public CustomerKeyHistoryDao getCustomerKeyHistoryDao() {
		return customerKeyHistoryDao;
	}

	public void setCustomerKeyHistoryDao(CustomerKeyHistoryDao customerKeyHistoryDao) {
		this.customerKeyHistoryDao = customerKeyHistoryDao;
	}

	@Override
	public CustomerKey findBy(String custNo, KeyType keyType) {
		return customerKeyDao.findBy(custNo, keyType);
	}

	@Override
	public String findByKey(String key) {
		return customerKeyDao.findByKey(key);
	}
}