package com.yl.boss.service.impl;

import com.yl.boss.dao.CustomerHistoryDao;
import com.yl.boss.service.CustomerHistoryService;

/**
 * 商户历史记录业务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public class CustomerHistoryServiceImpl implements CustomerHistoryService {

	private CustomerHistoryDao customerHistoryDao;
	
	@Override
	public String getLastInfo(String customerNo) {
		return customerHistoryDao.getLastInfo(customerNo);
	}

	@Override
	public void deleteLastInfo(String customerNo) {
		customerHistoryDao.deleteLastInfo(customerNo);
	}

	
	
	public CustomerHistoryDao getCustomerHistoryDao() {
		return customerHistoryDao;
	}

	public void setCustomerHistoryDao(CustomerHistoryDao customerHistoryDao) {
		this.customerHistoryDao = customerHistoryDao;
	}
}