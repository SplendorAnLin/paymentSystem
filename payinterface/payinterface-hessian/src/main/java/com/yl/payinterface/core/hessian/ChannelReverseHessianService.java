package com.yl.payinterface.core.hessian;

import java.util.Map;


/**
 * 通道补单远程接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月27日
 * @version V1.0.0
 */
public interface ChannelReverseHessianService {

	/**
	 * 接口外部补单
	 * @param params
	 */
	Map<String,String> reverse(Map<String, String> params);
}
