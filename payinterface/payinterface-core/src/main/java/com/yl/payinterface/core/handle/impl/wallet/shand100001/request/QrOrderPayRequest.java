package com.yl.payinterface.core.handle.impl.wallet.shand100001.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.yl.payinterface.core.handle.impl.wallet.shand100001.response.QrOrderPayResponse;

/**
 * 二维码订单支付请求
 * 
 * @author AnLin
 * @since 2016年8月7日
 * @version V1.0.0
 */
public class QrOrderPayRequest extends SandpayRequest<QrOrderPayResponse>{
	
	private QrOrderPayRequestBody body;
	
	public QrOrderPayRequestBody getBody() {
		return body;
	}
	public void setBody(QrOrderPayRequestBody body) {
		this.body = body;
	}

	public static class QrOrderPayRequestBody {
		private String payTool;  // 支付工具
		private String orderCode;  // 商户订单号
		private String scene;  // 支付场景
		private String authCode;  // 支付授权码
		private String totalAmount;  // 订单金额
		private String subject;  // 订单标题
		private String body;  // 订单描述
		private String txnTimeOut;  // 订单超时时间
		private String storeId;  // 商户门店编号
		private String terminalId;  // 商户终端编号
		private String operatorId;  // 操作员编号
		private String notifyUrl;  // 异步通知地址
		private String bizExtendParams;  // 业务扩展参数
		private String merchExtendParams;  // 商户扩展参数
		private String extend;  // 扩展域
		public String getPayTool() {
			return payTool;
		}
		public void setPayTool(String payTool) {
			this.payTool = payTool;
		}
		public String getOrderCode() {
			return orderCode;
		}
		public void setOrderCode(String orderCode) {
			this.orderCode = orderCode;
		}
		public String getScene() {
			return scene;
		}
		public void setScene(String scene) {
			this.scene = scene;
		}
		public String getAuthCode() {
			return authCode;
		}
		public void setAuthCode(String authCode) {
			this.authCode = authCode;
		}
		public String getTotalAmount() {
			return totalAmount;
		}
		public void setTotalAmount(String totalAmount) {
			this.totalAmount = totalAmount;
		}
		public String getSubject() {
			return subject;
		}
		public void setSubject(String subject) {
			this.subject = subject;
		}
		public String getBody() {
			return body;
		}
		public void setBody(String body) {
			this.body = body;
		}
		public String getTxnTimeOut() {
			return txnTimeOut;
		}
		public void setTxnTimeOut(String txnTimeOut) {
			this.txnTimeOut = txnTimeOut;
		}
		public String getStoreId() {
			return storeId;
		}
		public void setStoreId(String storeId) {
			this.storeId = storeId;
		}
		public String getTerminalId() {
			return terminalId;
		}
		public void setTerminalId(String terminalId) {
			this.terminalId = terminalId;
		}
		public String getOperatorId() {
			return operatorId;
		}
		public void setOperatorId(String operatorId) {
			this.operatorId = operatorId;
		}
		public String getNotifyUrl() {
			return notifyUrl;
		}
		public void setNotifyUrl(String notifyUrl) {
			this.notifyUrl = notifyUrl;
		}
		public String getBizExtendParams() {
			return bizExtendParams;
		}
		public void setBizExtendParams(String bizExtendParams) {
			this.bizExtendParams = bizExtendParams;
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

	/* (non-Javadoc)
	 * @see cn.com.sandpay.cashier.SandpayRequest#getResponseClass()
	 */
	@Override
	@JSONField(serialize=false) 
	public Class<QrOrderPayResponse> getResponseClass() {
		return QrOrderPayResponse.class;
	}

	/* (non-Javadoc)
	 * @see cn.com.sandpay.cashier.SandpayRequest#getTxnDesc()
	 */
	@Override
	@JSONField(serialize=false) 
	public String getTxnDesc() {
		return "qrOrderPay";
	}

}
