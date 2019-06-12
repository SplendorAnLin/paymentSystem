package com.yl.payinterface.core.handler;

import java.util.Map;


/**
 * 接口对账接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年12月14日
 * @version V1.0.0
 */
public interface ChannelRecionHandler {

	/**
	 * 对账单下载
	 */
	Map<String,String> recion(Map<String,String> params);
}
