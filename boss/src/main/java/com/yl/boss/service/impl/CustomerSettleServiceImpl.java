package com.yl.boss.service.impl;

import java.util.List;

import com.yl.boss.dao.CustomerSettleDao;
import com.yl.boss.dao.CustomerSettleHistoryDao;
import com.yl.boss.entity.CustomerSettle;
import com.yl.boss.entity.CustomerSettleHistory;
import com.yl.boss.service.CustomerSettleService;

/**
 * 商户结算卡服务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public class CustomerSettleServiceImpl implements CustomerSettleService {
	
	private CustomerSettleDao customerSettleDao;
	private CustomerSettleHistoryDao customerSettleHistoryDao;

	@Override
	public void update(CustomerSettle customerSettle, String oper) {
		CustomerSettle custSettle = customerSettleDao.findByCustNo(customerSettle.getCustomerNo());
		if(custSettle != null){
			custSettle.setAccountNo(customerSettle.getAccountNo());
			custSettle.setAccountName(customerSettle.getAccountName());
			custSettle.setBankCode(customerSettle.getBankCode());
			custSettle.setCustomerType(customerSettle.getCustomerType());
			custSettle.setOpenBankName(customerSettle.getOpenBankName());
			customerSettleDao.update(custSettle);
			
			CustomerSettleHistory customerSettleHistory = new CustomerSettleHistory(custSettle, oper);
			customerSettleHistoryDao.create(customerSettleHistory);
		}
	}

	@Override
	public CustomerSettle findByCustNo(String custNo) {
		return customerSettleDao.findByCustNo(custNo);
	}

	@Override
	public List<CustomerSettleHistory> findHistByCustNo(String custNo) {
		return customerSettleHistoryDao.findByCustNo(custNo);
	}

	public CustomerSettleDao getCustomerSettleDao() {
		return customerSettleDao;
	}

	public void setCustomerSettleDao(CustomerSettleDao customerSettleDao) {
		this.customerSettleDao = customerSettleDao;
	}

	public CustomerSettleHistoryDao getCustomerSettleHistoryDao() {
		return customerSettleHistoryDao;
	}

	public void setCustomerSettleHistoryDao(
			CustomerSettleHistoryDao customerSettleHistoryDao) {
		this.customerSettleHistoryDao = customerSettleHistoryDao;
	}

}
