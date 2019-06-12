package com.yl.dpay.core.service;

import com.yl.dpay.core.entity.ServiceConfigHistory;

/**
 * 代付配置历史服务信息业务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月13日
 * @version V1.0.0
 */
public interface ServiceConfigHistoryService {
	/**
	 * 添加代付配置历史信息
	 * @param serviceConfigHistory
	 */
	public void insert(ServiceConfigHistory serviceConfigHistory);
}
