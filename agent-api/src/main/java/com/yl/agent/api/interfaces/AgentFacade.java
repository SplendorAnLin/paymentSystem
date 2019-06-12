package com.yl.agent.api.interfaces;

import java.util.Map;

/**
 * 服务商远程接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月22日
 * @version V1.0.0
 */
public interface AgentFacade {
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
}
