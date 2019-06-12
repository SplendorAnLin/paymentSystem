package com.yl.payinterface.core.hessian;

import java.util.Map;

import com.yl.payinterface.core.bean.MobileInfoBean;

/**
 * 移动支付Hessian服务
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月6日
 * @version V1.0.0
 */
public interface MobilePayHessianService {
	/**
	 * 获取短信验证码
	 * @param payerMobNo 手机号
	 * @param merOrderId 支付订单号
	 * @return
	 */
	String sendVerifyCode(String payerMobNo, String merOrderId);

	/**
	 * 移动支付支付请求
	 * @param mobileInfoBean
	 * @return
	 */
	Map<String, Object> mobilePay(MobileInfoBean mobileInfoBean);

}
