package com.yl.payinterface.core.hessian;

import java.util.Map;

import com.yl.payinterface.core.bean.AuthSaleBean;

/**
 * 认证支付处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月27日
 * @version V1.0.0
 */
public interface AuthPayHessianService {
	/**
	 * 获取短信验证码
	 * params
	 * @return
	 */
	Map<String, String> sendVerifyCode(Map<String, String> params);

	/**
	 * 支付
	 * @param authSaleBean
	 * @return
	 */
	Map<String, String> pay(AuthSaleBean authSaleBean);

	/**
	 * 消费
	 * @param authSaleBean 认证消费bean
	 * @return
	 */
	Map<String,String> sale(AuthSaleBean authSaleBean);

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
