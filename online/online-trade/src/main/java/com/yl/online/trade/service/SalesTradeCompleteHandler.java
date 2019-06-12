package com.yl.online.trade.service;

import java.util.Map;

/**
 * 交易完成处理服务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
public interface SalesTradeCompleteHandler {
	
	/**
	 * 交易完成
	 * @param requestParameters 交易完成参数
	 * @return Object
	 */
	void complete(Map<String, String> requestParameters);
}
