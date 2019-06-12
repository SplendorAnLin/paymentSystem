package com.yl.online.trade.remote.hessian.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lefu.commons.utils.Page;
import com.yl.online.model.model.CustomerConfig;
import com.yl.online.model.model.CustomerConfigHistory;
import com.yl.online.trade.hessian.OnlineCustomerConfigHessianService;
import com.yl.online.trade.service.CustomerConfigHistoryService;
import com.yl.online.trade.service.CustomerConfigService;

/**
 * 商户交易配置操作接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月13日
 * @version V1.0.0
 */
@Service("onlineCustomerConfigHessianService")
public class OnlineCustomerConfigHessianServiceImpl implements OnlineCustomerConfigHessianService {

	@Resource
	private CustomerConfigService customerConfigService;
	@Resource
	private CustomerConfigHistoryService customerConfigHistoryService;
	
	@Override
	public Object findAll(Page page, Map<String, Object> params) {
		return customerConfigService.findAll(page, params);
	}

	@Override
	public void create(Map<String, Object> customerConfig,String reason,String operator) {
		CustomerConfig config = new CustomerConfig();
		CustomerConfigHistory DB = new CustomerConfigHistory();
		//config.setCode(customerConfig.get("Code").toString());
		config.setCustomerNo(customerConfig.get("customerNo").toString());
		config.setDayMax((Double)customerConfig.get("dayMax"));
		config.setEndTime(customerConfig.get("endTime").toString());
		config.setMaxAmount((Double)customerConfig.get("maxAmount"));
		config.setMinAmount((Double)customerConfig.get("minAmount"));
		config.setProductType(customerConfig.get("productType").toString());
		config.setStartTime(customerConfig.get("startTime").toString());
		config.setCreateTime(new Date());
		DB.setCustomerNo(customerConfig.get("customerNo").toString());
		DB.setDayMax((Double)customerConfig.get("dayMax"));
		DB.setEndTime(customerConfig.get("endTime").toString());
		DB.setMaxAmount((Double)customerConfig.get("maxAmount"));
		DB.setMinAmount((Double)customerConfig.get("minAmount"));
		DB.setProductType(customerConfig.get("productType").toString());
		DB.setStartTime(customerConfig.get("startTime").toString());
		DB.setCreateTime(config.getCreateTime());
		DB.setOperator(operator);
		DB.setReason(reason);
		customerConfigService.create(config);
		customerConfigHistoryService.create(DB);
	}

	@Override
	public CustomerConfig findById(String id) {
		return customerConfigService.findById(id);
	}

	@Override
	public void modifyConfig(CustomerConfig customerConfig,String reason,String operator) {
		CustomerConfigHistory DB = new CustomerConfigHistory();
		DB.setCreateTime(customerConfig.getCreateTime());
		DB.setCustomerNo(customerConfig.getCustomerNo());
		DB.setDayMax(customerConfig.getDayMax());
		DB.setStartTime(customerConfig.getStartTime());
		DB.setEndTime(customerConfig.getEndTime());
		DB.setMaxAmount(customerConfig.getMaxAmount());
		DB.setMinAmount(customerConfig.getMinAmount());
		DB.setProductType(customerConfig.getProductType());
		DB.setOperator(operator);
		DB.setReason(reason);
		customerConfigService.modifyConfig(customerConfig);
		customerConfigHistoryService.create(DB);
	}

	@Override
	public boolean queryProductTypeExistsByCustNo(String customerNo, String productType) {
		return customerConfigService.queryProductTypeExistsByCustNo(customerNo, productType);
	}

	@Override
	public List<CustomerConfig> queryAllByCustomerNo(String customerNo) {
		return customerConfigService.queryAllByCustomerNo(customerNo);
	}

}