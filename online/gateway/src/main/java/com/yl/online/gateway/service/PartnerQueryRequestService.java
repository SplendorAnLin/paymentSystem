package com.yl.online.gateway.service;

import com.yl.online.model.model.PartnerQueryRequest;

/**
 * 合作方查询请求信息业务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月8日
 * @version V1.0.0
 */
public interface PartnerQueryRequestService {
	/**
	 * 创建合作方查询请求流水
	 * @param partnerQueryRequest 合作方请求信息
	 */
	void save(PartnerQueryRequest partnerQueryRequest);
	
	/**
	 * 根据合作方查询请求流水号查询合作方请求流水
	 * @param requestCode 合作方请求流水号
	 * @return PartnerQueryRequest
	 */
	PartnerQueryRequest queryPartnerQueryRequestByOutOrderId(String requestCode);
}
