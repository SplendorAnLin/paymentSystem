package com.yl.online.gateway.hessian;

import java.util.Map;

/**
 * 网关商户通知远程接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月17日
 * @version V1.0.0
 */
public interface SalesNotifyHessianService {
	
	/**
	 * 通知商户
	 * @param orderCode
	 * @param orderStatus
	 * @param requestCode
	 * @param receiver
	 */
	public void notify(String orderCode, String orderStatus, String requestCode, String receiver);

	/**
	 * 根据订单号查询通知信息
	 * @param orderCode
	 * @return
	 */
	public Map<String, String> findNotifyInfo(String orderCode);

}
