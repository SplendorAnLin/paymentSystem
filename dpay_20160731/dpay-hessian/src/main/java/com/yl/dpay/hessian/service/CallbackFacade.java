package com.yl.dpay.hessian.service;

import com.yl.dpay.hessian.beans.CallbackBean;

/**
 * 付款回调远程接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月17日
 * @version V1.0.0
 */
public interface CallbackFacade {
	
	/**
	 * 付款回调
	 * @param callbackBean
	 */
	public void callback(CallbackBean callbackBean);

}
