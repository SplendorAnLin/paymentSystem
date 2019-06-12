package com.yl.payinterface.core.hessian;

import java.util.Map;

import com.yl.payinterface.core.bean.InterfaceRemitBillHessian;

/**
 * 付款hessian协议服务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月22日
 * @version V1.0.0
 */
public interface RemitHessianService {

	/**
	 * @Description 付款
	 * @param interfaceRemitBillHessian 付款单hessian实体
	 * @return Map<String,String>
	 * @see 需要参考的类或方法
	 */
	Map<String,String> remit(InterfaceRemitBillHessian interfaceRemitBillHessian);

	/**
	 * @Description 查詢付款
	 * @param remitBatchHessian 付款批次hessian实体
	 * @return Map<String,String>
	 * @see 需要参考的类或方法
	 */
	Map<String,String> query(Map<String, String> map);
	
	/**
	 * 代付完成接口
	 * @param completeMap
	 * @author qiujian
	 * 2016年11月27日
	 */
	public void complete(Map<String, String> completeMap);
}
