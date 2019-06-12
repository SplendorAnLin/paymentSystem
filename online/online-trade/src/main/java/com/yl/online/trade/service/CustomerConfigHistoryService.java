package com.yl.online.trade.service;

import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.online.model.model.CustomerConfigHistory;

/**
 * 商户交易配置历史服务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
public interface CustomerConfigHistoryService {
	
	Object findAll(Page page, Map<String, Object> params);
	
	void create (CustomerConfigHistory customerConfigHistory);
}