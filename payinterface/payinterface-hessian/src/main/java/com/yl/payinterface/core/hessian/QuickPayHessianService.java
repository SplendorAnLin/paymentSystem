package com.yl.payinterface.core.hessian;

import java.util.Map;

import com.yl.payinterface.core.bean.AuthSaleBean;
import com.yl.payinterface.core.bean.QuickPayBean;

/**
 * 认真支付处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月27日
 * @version V1.0.0
 */
public interface QuickPayHessianService {
	/**
	 * 获取短信验证码
	 * params
	 * @return
	 */
	Map<String, String> sendVerifyCode(Map<String, String> params);

	/**
	 * 支付
	 * @param quickPayBean
	 * @return
	 */
	Map<String, String> pay(QuickPayBean quickPayBean);

	/**
	 * 消费
	 * @param quickPayBean 认证消费bean
	 * @return
	 */
	Map<String,String> sale(QuickPayBean quickPayBean);

	/**
	 * 订单查询
	 * @param merOrderId 支付订单号
	 * @return
	 */
	Map<String,String> queryOrder(String merOrderId, String interfaceCode);
	
	/**
	 * 支付完成接口
	 * @param completeMap
	 */
	void complete(Map<String, String> completeMap);
}
