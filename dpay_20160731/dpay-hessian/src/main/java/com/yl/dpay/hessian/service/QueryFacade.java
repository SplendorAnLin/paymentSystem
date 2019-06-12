package com.yl.dpay.hessian.service;

import java.util.Map;

import com.yl.dpay.hessian.beans.RequestBean;

/**
 * 代付查询远程接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月17日
 * @version V1.0.0
 */
public interface QueryFacade {

	public static final String VALUELIST_INFO = "valueListInfo";
	public static final String VALUELIST = "valueList";

	/**
	 * 查询服务
	 * 
	 * @param queryId
	 *            查询ID
	 * @param params
	 *            查询参数
	 * @return
	 */
	public Map<String, Object> query(String queryId, Map<String, Object> params);
	
	/**
	 * 根据流水号查询
	 */
	public RequestBean findByFlowNo(String flowNo);
	
	/**
	 * 代付历史查询
	 */
	
	public Map<String, Object> history(String queryId, Map<String, Object> params);
	
}
