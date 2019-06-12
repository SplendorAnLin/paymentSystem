package com.yl.payinterface.core.handle.impl.wallet.shand100001.response;

/**
 * 二维码订单退款响应
 * 
 * @author AnLin
 * @since 2016年8月7日
 * @version V1.0.0
 */
public class QrOrderRefundResponse extends SandpayResponse {
	
	private QrOrderRefundResponseBody body;
	
	public QrOrderRefundResponseBody getBody() {
		return body;
	}
	public void setBody(QrOrderRefundResponseBody body) {
		this.body = body;
	}

	public static class QrOrderRefundResponseBody {
		private String orderCode;  // 商户订单号
		private String tradeNo;  // 交易流水号
		private String refoundAount;  // 实际退货金额
		private String surplusAmount;  // 剩余可退金额
		private String refoundTime;  // 退货时间
		private String clearDate;  // 清算日期
		private String extend;  // 扩展域
		public String getOrderCode() {
			return orderCode;
		}
		public void setOrderCode(String orderCode) {
			this.orderCode = orderCode;
		}
		public String getTradeNo() {
			return tradeNo;
		}
		public void setTradeNo(String tradeNo) {
			this.tradeNo = tradeNo;
		}
		public String getRefoundAount() {
			return refoundAount;
		}
		public void setRefoundAount(String refoundAount) {
			this.refoundAount = refoundAount;
		}
		public String getSurplusAmount() {
			return surplusAmount;
		}
		public void setSurplusAmount(String surplusAmount) {
			this.surplusAmount = surplusAmount;
		}
		public String getRefoundTime() {
			return refoundTime;
		}
		public void setRefoundTime(String refoundTime) {
			this.refoundTime = refoundTime;
		}
		public String getClearDate() {
			return clearDate;
		}
		public void setClearDate(String clearDate) {
			this.clearDate = clearDate;
		}
		public String getExtend() {
			return extend;
		}
		public void setExtend(String extend) {
			this.extend = extend;
		}
	}
}
