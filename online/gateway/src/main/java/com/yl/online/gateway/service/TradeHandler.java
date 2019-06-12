package com.yl.online.gateway.service;

import com.yl.online.gateway.bean.PayParam;
import com.yl.online.gateway.exception.BusinessException;
import com.yl.online.model.bean.PayResultBean;
import com.yl.online.model.model.Order;
import com.yl.online.model.model.PartnerRequest;

/**
 * 网关交易处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月25日
 * @version V1.0.0
 */
public interface TradeHandler {
	String execute(PartnerRequest partnerRequest) throws BusinessException;

	PayResultBean pay(PayParam payParam) throws BusinessException;
	
	String createResponse(Order order, PartnerRequest partnerRequest, boolean isRedirectUrl) throws BusinessException;
	
}
