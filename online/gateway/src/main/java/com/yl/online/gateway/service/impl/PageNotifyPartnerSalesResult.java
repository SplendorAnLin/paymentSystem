package com.yl.online.gateway.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.hessian.bean.AuthBean;
import com.lefu.hessian.util.HessianSignUtils;
import com.yl.online.gateway.ExceptionMessages;
import com.yl.online.gateway.service.PartnerRequestService;
import com.yl.online.gateway.service.TradeHandler;
import com.yl.online.model.model.Order;
import com.yl.online.model.model.PartnerRequest;
import com.yl.online.trade.hessian.OnlineTradeHessianService;

/**
 * 页面通知合作方支付结果
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月8日
 * @version V1.0.0
 */
@Component("pageNotifyPartnerSalesResult")
public class PageNotifyPartnerSalesResult {
	
	@Resource
	private OnlineTradeHessianService onlineTradeHessianService;
	@Resource
	private PartnerRequestService partnerRequestService;
	@Resource
	private TradeHandler tradeHandler;
	
	/**
	 * 通知商户消费结果
	 * @param authBean 认证Bean
	 * @param response 响应实体
	 * @param orderId 交易订单编号
	 * @throws Exception
	 */
	public void pageNotifyPartner(AuthBean authBean, HttpServletResponse response, String orderId) throws Exception {
		authBean.setInvokeSystem("online-trade");
		authBean.setTimestamp(System.currentTimeMillis());
		authBean.setSign(HessianSignUtils.md5DigestAsHex(StringUtils.concatToSB(authBean.toString(), orderId, "123456").toString().getBytes()));
		// 查询交易订单
		Order order = onlineTradeHessianService.queryOrderByCode(orderId);
		if (order == null) throw new RuntimeException(ExceptionMessages.TRADE_ORDER_NOT_EXISTS);
		// 查询合作方请求信息
		PartnerRequest partnerRequest = partnerRequestService.queryPartnerRequestByOutOrderId(order.getRequestCode(),order.getReceiver());
		if (partnerRequest == null) throw new RuntimeException(ExceptionMessages.RECEIVER_NOT_EXISTS);
		
		if (StringUtils.isNotBlank(order.getRedirectURL())) {
			// 组装返回商户的URL以及参数并且签名
			String responseCustomerUrl = tradeHandler.createResponse(order, partnerRequest, true);
			response.sendRedirect(responseCustomerUrl);
		}
	}
}
