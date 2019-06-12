package com.yl.payinterface.core.handle.impl.wallet.shand100001.response;

/**
 * 二维码订单查询响应
 * 
 * @author AnLin
 * @since 2016年8月7日
 * @version V1.0.0
 */
public class QrOrderQueryResponse extends SandpayResponse {
	
	private QrOrderQueryResponseBody body;
	
	public QrOrderQueryResponseBody getBody() {
		return body;
	}
	public void setBody(QrOrderQueryResponseBody body) {
		this.body = body;
	}

	public static class QrOrderQueryResponseBody {
		private String oriOrderCode;  // 原商户订单号
		private String oriRespCode;  // 原交易应答码
		private String oriRespMsg;  // 原交易应答描述
		private String totalAmount;  // 订单金额
		private String orderStatus;  // 订单状态
		private String oriTradeNo;  // 原交易流水号
		private String buyerPayAmount;  // 买家付款金额
		private String discAmount;  // 优惠金额
		private String payTime;  // 支付时间
		private String clearDate;  // 清算日期
		private String extend;
		public String getOriOrderCode() {
			return oriOrderCode;
		}
		public void setOriOrderCode(String oriOrderCode) {
			this.oriOrderCode = oriOrderCode;
		}
		public String getOriRespCode() {
			return oriRespCode;
		}
		public void setOriRespCode(String oriRespCode) {
			this.oriRespCode = oriRespCode;
		}
		public String getOriRespMsg() {
			return oriRespMsg;
		}
		public void setOriRespMsg(String oriRespMsg) {
			this.oriRespMsg = oriRespMsg;
		}
		public String getTotalAmount() {
			return totalAmount;
		}
		public void setTotalAmount(String totalAmount) {
			this.totalAmount = totalAmount;
		}
		public String getOrderStatus() {
			return orderStatus;
		}
		public void setOrderStatus(String orderStatus) {
			this.orderStatus = orderStatus;
		}
		public String getOriTradeNo() {
			return oriTradeNo;
		}
		public void setOriTradeNo(String oriTradeNo) {
			this.oriTradeNo = oriTradeNo;
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
		public String getExtend() {
			return extend;
		}
		public void setExtend(String extend) {
			this.extend = extend;
		}  // 扩展域
	}
}
