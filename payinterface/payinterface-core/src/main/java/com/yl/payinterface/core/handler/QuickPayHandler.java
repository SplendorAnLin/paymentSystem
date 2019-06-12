package com.yl.payinterface.core.handler;

import java.util.Map;

/**
 * 认证交易处理
 *
 * @author 聚合支付有限公司
 * @since 2016年12月14日
 * @version V1.0.0
 */
public interface QuickPayHandler {

	/**
	 * 获取短信验证码
	 * @param params
	 * @return
	 */
	Map<String, String> sendVerifyCode(Map<String, String> params);

	/**
	 * 验证码支付
	 * @param map
	 * @return
	 */
	Map<String, String> authPay(Map<String, String> map);
	/**
	 * 消费
	 * @param map 认证消费bean
	 * @return
	 */
	Map<String, String> sale(Map<String, String> map);
	/**
	 * 查詢接口
	 * @param map 认证支付查询参数
	 * @return Map<String, String>
	 */
	Map<String, String> query(Map<String, String> map);
}
