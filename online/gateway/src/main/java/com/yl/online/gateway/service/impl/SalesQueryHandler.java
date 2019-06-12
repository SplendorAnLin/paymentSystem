package com.yl.online.gateway.service.impl;

import com.lefu.commons.utils.lang.DateFormatUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.security.DigestUtils;
import com.yl.online.gateway.Constants;
import com.yl.online.gateway.ExceptionMessages;
import com.yl.online.gateway.bean.PartnerQueryResponse;
import com.yl.online.gateway.enums.SignType;
import com.yl.online.gateway.exception.BusinessException;
import com.yl.online.gateway.service.QueryHandler;
import com.yl.online.model.bean.Payment;
import com.yl.online.model.enums.OrderStatus;
import com.yl.online.model.model.Order;
import com.yl.online.model.model.PartnerQueryRequest;
import com.yl.online.trade.hessian.OnlineTradeHessianService;
import com.yl.online.trade.hessian.OnlineTradeQueryHessianService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 合作方订单查询服务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月8日
 * @version V1.0.0
 */
@Service
public class SalesQueryHandler implements QueryHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(SalesQueryHandler.class);
	@Resource
	private OnlineTradeQueryHessianService onlineTradeQueryHessianService;
	@Resource
	private OnlineTradeHessianService onlineTradeHessianService;
	
	@SuppressWarnings("unchecked")
	@Override
	public PartnerQueryResponse query(PartnerQueryRequest partnerQueryRequest, String cipher) throws BusinessException {
		// 响应码
		String responseCode = "0000";
		// 响应描述
		String responseMsg = "通信成功";
		PartnerQueryResponse partnerQueryResponse = new PartnerQueryResponse();
		
		logger.info("合作方编号为：{}, 且合作方订单号为：{} 查询支付交易结果..", partnerQueryRequest.getPartner(), partnerQueryRequest.getOutOrderId());
		Order order = JsonUtils.toObject(onlineTradeQueryHessianService.findOrderBy(partnerQueryRequest.getPartner(), partnerQueryRequest.getOutOrderId()), Order.class);
		if (order == null) throw new BusinessException(ExceptionMessages.TRADE_ORDER_NOT_EXISTS);
		partnerQueryResponse.setOrderCode(order.getCode());
		// 支付时间
		if(order.getSuccessPayTime() != null){
			partnerQueryResponse.setPayTime(DateFormatUtils.format(order.getSuccessPayTime(),"yyyyMMddHHmmss"));
		}

		Payment payment = onlineTradeHessianService.queryLastPayment(order.getCode());

		partnerQueryResponse.setAmount(String.valueOf(order.getAmount()));
		partnerQueryResponse.setFee(String.valueOf(order.getReceiverFee()));
		partnerQueryResponse.setPartner(order.getReceiver());
		Map<String, String> status = switchOrderStatus(order);
		partnerQueryResponse.setPartnerOrderStatus(status.get("orderStatus")); // 支付状态
		partnerQueryResponse.setOrderTime(DateFormatUtils.format(order.getOrderTime(), "yyyyMMddHHmmss"));
		partnerQueryResponse.setResponseCode(responseCode);
		partnerQueryResponse.setResponseMsg(responseMsg);
		partnerQueryResponse.setSignType(SignType.MD5.name());
		partnerQueryResponse.setOutOrderId(partnerQueryRequest.getOutOrderId());
		partnerQueryResponse.setResultCode(payment.getResponseCode());
		partnerQueryResponse.setResultMsg(payment.getResponseInfo());
		
		Map<String, String> params = JsonUtils.toJsonToObject(partnerQueryResponse, Map.class);
		// 按参数名排序
		ArrayList<String> paramNames = new ArrayList<>(params.keySet());
		Collections.sort(paramNames);
		
		// 组织待签名信息
		StringBuilder signSource = new StringBuilder();
		Iterator<String> iterator = paramNames.iterator();
		while (iterator.hasNext()) {
			String paramName = iterator.next();
			if (!Constants.PARAM_NAME_SIGN.equals(paramName) && StringUtils.notBlank(params.get(paramName))) {
				signSource.append(paramName).append("=").append(params.get(paramName));
				if (iterator.hasNext()){
					signSource.append("&");
				}
			}
		}
		
		// 计算签名
		String calSign = null;
		if (SignType.MD5.name().equals(partnerQueryRequest.getSignType())) {
			signSource.append(cipher);
			try {
				logger.info("支付网关订单接口查询计算签名原串：{}", signSource);
				calSign = DigestUtils.md5DigestAsHex(signSource.toString().getBytes(partnerQueryRequest.getInputCharset()));
			} catch (UnsupportedEncodingException e) {
				throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_ERROR, "-", Constants.PARAM_NAME_INPUT_CHARSET).toString());
			}
		}
		partnerQueryResponse.setSign(calSign);
		return partnerQueryResponse;
	}

	/**
	 * 转义状态码
	 * @param order 交易订单实体
	 * @return Map<String, String>
	 */
	private Map<String, String> switchOrderStatus(Order order) {
		// 支付状态
		OrderStatus orderStatus = order.getStatus();
		Map<String, String> status = new HashMap<String, String>();
		String payStatus = "WAIT_PAY"; // 等待支付
		if (OrderStatus.SUCCESS.equals(orderStatus)) payStatus = "SUCCESS"; // 支付成功
		else if (OrderStatus.FAILED.equals(orderStatus)) payStatus = "FAILED"; // 支付失败
		else if (OrderStatus.CLOSED.equals(orderStatus)) payStatus = "CLOSED"; // 订单关闭
		status.put("orderStatus", payStatus);
		
		// 退款状态
//		RefundStatus orderRefundStatus = order.getRefundStatus();
//		String refundStatus = "02"; // 未退款
//		if (RefundStatus.REFUND_ALL.equals(orderRefundStatus)) refundStatus = "00"; // 全部退款
//		else if(RefundStatus.REFUND_PART.equals(orderRefundStatus)) refundStatus = "01"; // 部分退款
//		else if (RefundStatus.REFUND_PART.equals(orderRefundStatus)) refundStatus = "03"; // 发起退款
//		status.put("refundStatus", refundStatus);
		
		return status;
	}

}
