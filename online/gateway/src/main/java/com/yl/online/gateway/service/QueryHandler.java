package com.yl.online.gateway.service;

import com.yl.online.gateway.bean.PartnerQueryResponse;
import com.yl.online.gateway.exception.BusinessException;
import com.yl.online.model.model.PartnerQueryRequest;

/**
 * 合作方订单查询服务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月8日
 * @version V1.0.0
 */
public interface QueryHandler {
	
	/**
	 * 查询订单支付结果
	 * @param partnerQueryRequest 合作方请求信息
	 * @param cipher 密钥信息
	 * @return PartnerQueryResponse 合作方查询响应
	 * @throws BusinessException
	 */
	PartnerQueryResponse query(PartnerQueryRequest partnerQueryRequest, String cipher) throws BusinessException;
}
