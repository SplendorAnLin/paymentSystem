package com.yl.online.trade.hessian;

import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.online.model.model.CustomerConfigHistory;

/**
 * 商户交易配置历史查询接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月13日
 * @version V1.0.0
 */
public interface OnlineCustomerConfigHistoryHessianService {
	
	Object findAllById(Page page, Map<String, Object> params);
	
	void create(CustomerConfigHistory customerConfigHistory);
}