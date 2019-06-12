package com.yl.payinterface.core.handle.impl.b2c.shand100001.response;

/**
 * 网关订单交易响应
 * 
 * @author AnLin
 * @since 2016年11月16日
 * @version V1.0.0
 */
public class GatewayOrderPayResponse extends SandpayResponse {

	private GatewayOrderPayResponseBody body;
	
	public GatewayOrderPayResponseBody getBody() {
		return body;
	}
	public void setBody(GatewayOrderPayResponseBody body) {
		this.body = body;
	}

	public static class GatewayOrderPayResponseBody {
		private String orderCode;  // 商户订单号
		private String totalAmount;  // 订单金额
		private String credential;  // 支付凭证
		private String tradeNo;  // 交易流水号
		private String buyerPayAmount;  // 买家付款金额
		private String discAmount;  // 优惠金额
		private String payTime;  // 支付时间
		private String clearDate;  // 清算日期
		public String getOrderCode() {
			return orderCode;
		}
		public void setOrderCode(String orderCode) {
			this.orderCode = orderCode;
		}
		public String getTotalAmount() {
			return totalAmount;
		}
		public void setTotalAmount(String totalAmount) {
			this.totalAmount = totalAmount;
		}
		public String getCredential() {
			return credential;
		}
		public void setCredential(String credential) {
			this.credential = credential;
		}
		public String getTradeNo() {
			return tradeNo;
		}
		public void setTradeNo(String tradeNo) {
			this.tradeNo = tradeNo;
		}
		public String getBuyerPayAmount() {
			return buyerPayAmount;
		}
		public void setBuyerPayAmount(String buyerPayAmount) {
			this.buyerPayAmount = buyerPayAmount;
		}
		public String getDiscAmount() {
			return discAmount;
		}
		public void setDiscAmount(String discAmount) {
			this.discAmount = discAmount;
		}
		public String getPayTime() {
			return payTime;
		}
		public void setPayTime(String payTime) {
			this.payTime = payTime;
		}
		public String getClearDate() {
			return clearDate;
		}
		public void setClearDate(String clearDate) {
			this.clearDate = clearDate;
		}
	}
}
