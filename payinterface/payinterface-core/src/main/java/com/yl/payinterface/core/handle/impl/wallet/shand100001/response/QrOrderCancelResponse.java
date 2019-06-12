package com.yl.payinterface.core.handle.impl.wallet.shand100001.response;

/**
 * 二维码订单撤销响应
 * 
 * @author AnLin
 * @since 2016年8月7日
 * @version V1.0.0
 */
public class QrOrderCancelResponse extends SandpayResponse {
	
	private QrOrderCancelResponseBody body;
	
	public QrOrderCancelResponseBody getBody() {
		return body;
	}
	public void setBody(QrOrderCancelResponseBody body) {
		this.body = body;
	}

	public static class QrOrderCancelResponseBody {
		private String orderCode;  // 商户订单号
		private String tradeNo;  // 交易流水号
		private String oriorderStatus;  // 原订单状态
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
		public String getOriorderStatus() {
			return oriorderStatus;
		}
		public void setOriorderStatus(String oriorderStatus) {
			this.oriorderStatus = oriorderStatus;
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
