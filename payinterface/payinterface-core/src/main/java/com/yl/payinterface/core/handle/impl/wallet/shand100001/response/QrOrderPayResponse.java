package com.yl.payinterface.core.handle.impl.wallet.shand100001.response;

/**
 * 二维码订单支付响应
 * 
 * @author AnLin
 * @since 2016年8月7日
 * @version V1.0.0
 */
public class QrOrderPayResponse extends SandpayResponse {

	private QrOrderPayResponseBody body;
	
	public QrOrderPayResponseBody getBody() {
		return body;
	}
	public void setBody(QrOrderPayResponseBody body) {
		this.body = body;
	}

	public static class QrOrderPayResponseBody {
		private String orderCode;  // 商户订单号
		private String totalAmount;  // 订单金额
		private String tradeNo;  // 交易流水号
		private String buyerPayAmount;  // 买家付款金额
		private String discAmount;  // 优惠金额
		private String payTime;  // 支付时间
		private String clearDate;  // 清算日期
		private String merchExtendParams;  // 商户扩展参数
		private String extend;  // 扩展域
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
		public String getMerchExtendParams() {
			return merchExtendParams;
		}
		public void setMerchExtendParams(String merchExtendParams) {
			this.merchExtendParams = merchExtendParams;
		}
		public String getExtend() {
			return extend;
		}
		public void setExtend(String extend) {
			this.extend = extend;
		}
	}
}
