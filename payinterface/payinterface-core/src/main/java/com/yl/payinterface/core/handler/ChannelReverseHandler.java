package com.yl.payinterface.core.handler;

import java.util.Map;


/**
 * 接口外部补单接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年09月22日
 * @version V1.0.0
 */
public interface ChannelReverseHandler {

	/**
	 * 接口外部补单
	 * @param params
	 */
	Map<String,String> reverse(Map<String,String> params);
}
