package com.yl.online.gateway.service;

import com.yl.online.model.model.PartnerRequest;

/**
 * 合作方请求信息业务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月8日
 * @version V1.0.0
 */
public interface PartnerRequestService {
	/**
	 * 创建合作方请求流水
	 * @param partnerRequest 合作方请求信息
	 */
	void save(PartnerRequest partnerRequest);
	
	/**
	 * 根据合作方请求流水号查询合作方请求流水
	 * @param requestCode 合作方请求流水号
	 * @return PartnerRequest
	 */
	PartnerRequest queryPartnerRequestByOutOrderId(String requestCode,String receiver);
}
