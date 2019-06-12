package com.yl.customer.service;

import java.util.List;

import com.yl.boss.api.bean.Customer;
import com.yl.boss.api.bean.CustomerFee;
import com.yl.boss.api.bean.CustomerKey;
import com.yl.boss.api.bean.CustomerSettle;
import com.yl.boss.api.enums.KeyType;
import com.yl.dpay.hessian.beans.ServiceConfigBean;

/**
 * 商户业务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月20日
 * @version V1.0.0
 */
public interface CustomerService {
	
	
	/**
	 * @param customerNo
	 * @return
	 */
	public Customer findCustByRemote(String customerNo);
	
	/**
	 * @param customerNo
	 * @param keyType
	 * @return
	 */
	public CustomerKey findCustKeyByRemote(String customerNo, KeyType keyType);
	
	/**
	 * @param customerNo
	 * @return
	 */
	public CustomerSettle findByCustSettleByRemote(String customerNo);
	
	/**
	 * @param customerNo
	 * @return
	 */
	public List<CustomerFee> findByCustFeeByRemote(String customerNo);
	
	/**
	 * @param customerNo
	 * @return
	 */
	public ServiceConfigBean findByCustDpayConfigByRemote(String customerNo);
	
}
