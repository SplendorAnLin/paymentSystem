package com.yl.online.trade.remote.hessian.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.lefu.commons.utils.Page;
import com.yl.online.model.model.CustomerConfigHistory;
import com.yl.online.trade.hessian.OnlineCustomerConfigHistoryHessianService;
import com.yl.online.trade.service.CustomerConfigHistoryService;

/**
 * 商户交易配置历史查询接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月13日
 * @version V1.0.0
 */
@Service("onlineCustomerConfigHistoryHessianService")
public class OnlineCustomerConfigHistoryHessianServiceImlp implements OnlineCustomerConfigHistoryHessianService {

	@Resource
	private CustomerConfigHistoryService customerConfigHistoryService;
	
	@Override
	public Object findAllById(Page page, Map<String, Object> params) {
		return customerConfigHistoryService.findAll(page, params);
	}

	@Override
	public void create(CustomerConfigHistory customerConfigHistory) {
		customerConfigHistoryService.create(customerConfigHistory);
	}
}