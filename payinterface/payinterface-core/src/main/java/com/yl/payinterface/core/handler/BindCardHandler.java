package com.yl.payinterface.core.handler;

import java.util.Map;

/**
 * 绑卡处理接口
 *
 * @author 聚合支付有限公司
 * @since 2017年08月15日
 * @version V1.0.0
 */
public interface BindCardHandler {

	/**
	 * 银行卡绑定
	 * @param params 银行卡绑定参数
	 * @return
	 */
	Map<String, String> bindCard(Map<String, String> params);

	/**
	 * 银行卡绑定查询
	 * @param params 查询银行卡绑定参数
	 * @return Map<String, String>
	 */
	Map<String, String> queryBindCard(Map<String, String> params);
}
