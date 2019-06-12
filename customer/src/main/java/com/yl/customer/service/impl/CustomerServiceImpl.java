package com.yl.customer.service.impl;

import java.util.List;

import com.yl.boss.api.bean.Customer;
import com.yl.boss.api.bean.CustomerFee;
import com.yl.boss.api.bean.CustomerKey;
import com.yl.boss.api.bean.CustomerSettle;
import com.yl.boss.api.enums.KeyType;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.customer.service.CustomerService;
import com.yl.dpay.hessian.beans.ServiceConfigBean;
import com.yl.dpay.hessian.service.ServiceConfigFacade;

/**
 * 商户业务接口处理
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月20日
 * @version V1.0.0
 */
public class CustomerServiceImpl implements CustomerService {
	
	private ServiceConfigFacade serviceConfigFacade;
	
	private CustomerInterface customerInterface;

	@Override
	public Customer findCustByRemote(String customerNo) {
		return customerInterface.getCustomer(customerNo);
	}

	@Override
	public CustomerKey findCustKeyByRemote(String customerNo, KeyType keyType) {
		return customerInterface.getCustomerKey(customerNo, keyType);
	}

	@Override
	public CustomerSettle findByCustSettleByRemote(String customerNo) {
		return customerInterface.getCustomerSettle(customerNo);
	}

	@Override
	public List<CustomerFee> findByCustFeeByRemote(String customerNo) {
		return customerInterface.getCustomerFeeList(customerNo);
	}

	@Override
	public ServiceConfigBean findByCustDpayConfigByRemote(String customerNo) {
		return serviceConfigFacade.query(customerNo);
	}

	public void setServiceConfigFacade(ServiceConfigFacade serviceConfigFacade) {
		this.serviceConfigFacade = serviceConfigFacade;
	}

	public void setCustomerInterface(CustomerInterface customerInterface) {
		this.customerInterface = customerInterface;
	}
	
}
