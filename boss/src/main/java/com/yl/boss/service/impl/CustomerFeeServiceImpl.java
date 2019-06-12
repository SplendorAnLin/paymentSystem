package com.yl.boss.service.impl;

import java.util.Date;
import java.util.List;

import com.yl.agent.api.enums.Status;
import com.yl.boss.enums.ProductType;
import com.yl.boss.dao.CustomerFeeDao;
import com.yl.boss.dao.CustomerFeeHistoryDao;
import com.yl.boss.entity.CustomerFee;
import com.yl.boss.entity.CustomerFeeHistory;
import com.yl.boss.service.CustomerFeeService;

/**
 * 商户费率服务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public class CustomerFeeServiceImpl implements CustomerFeeService {
	
	private CustomerFeeDao customerFeeDao;
	private CustomerFeeHistoryDao customerFeeHistoryDao;

	@Override
	public void create(CustomerFee customerFee, String oper) {
		customerFee.setCreateTime(new Date());
		customerFeeDao.create(customerFee);
		
		CustomerFeeHistory customerFeeHistory = new CustomerFeeHistory(customerFee, oper);
		customerFeeHistoryDao.create(customerFeeHistory);
	}

	@Override
	public void update(CustomerFee customerFee, String oper) {
		CustomerFee custFee = customerFeeDao.findById(customerFee.getId());
		if(custFee != null){
			custFee.setFee(customerFee.getFee());
			custFee.setFeeType(customerFee.getFeeType());
			custFee.setStatus(customerFee.getStatus()!=null?customerFee.getStatus():custFee.getStatus());
			custFee.setMaxFee(customerFee.getMaxFee());
			custFee.setMinFee(customerFee.getMinFee());
			customerFeeDao.update(custFee);
			
			CustomerFeeHistory customerFeeHistory = new CustomerFeeHistory(custFee, oper);
			customerFeeHistoryDao.create(customerFeeHistory);
		}
	}
	

	@Override
	public void delete(CustomerFee customerFee, String oper) {
		CustomerFee custFee = customerFeeDao.findById(customerFee.getId());
		if(custFee != null){
			if(null==customerFee.getFeeType()&&null==customerFee.getProductType()){//费率类型和产品类型都变为空
				customerFeeDao.delete(custFee);
			}
			
			CustomerFeeHistory customerFeeHistory = new CustomerFeeHistory(custFee, oper);
			customerFeeHistoryDao.create(customerFeeHistory);
		}
	}

	@Override
	public CustomerFee findBy(String custNo, ProductType productType) {
		return customerFeeDao.findBy(custNo, productType);
	}

	@Override
	public List<CustomerFee> findByCustNo(String custNo) {
		return customerFeeDao.findByCustNo(custNo);
	}

	@Override
	public CustomerFee findById(long id) {
		return customerFeeDao.findById(id);
	}

	@Override
	public List<CustomerFeeHistory> findHistByCustNo(String custNo) {
		return customerFeeHistoryDao.findByCustNo(custNo);
	}

	public CustomerFeeDao getCustomerFeeDao() {
		return customerFeeDao;
	}

	public void setCustomerFeeDao(CustomerFeeDao customerFeeDao) {
		this.customerFeeDao = customerFeeDao;
	}

	public CustomerFeeHistoryDao getCustomerFeeHistoryDao() {
		return customerFeeHistoryDao;
	}

	public void setCustomerFeeHistoryDao(CustomerFeeHistoryDao customerFeeHistoryDao) {
		this.customerFeeHistoryDao = customerFeeHistoryDao;
	}

}
