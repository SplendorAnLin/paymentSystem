package com.yl.payinterface.core.handler;

import java.util.Map;

/**
 * 退款接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年09月22日
 * @version V1.0.0
 */
public interface RefundHandler {
	
	/**
	 * 退款
	 * @param refundParams
	 * @return
	 */
	public Map<String,String> refund(Map<String,String> refundParams);

	/**
	 * 退款查询
	 * @param refundParams
	 * @return
	 */
	public Map<String,String> refundQuery(Map<String,String> refundParams);

}
