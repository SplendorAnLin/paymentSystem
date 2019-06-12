package com.yl.payinterface.core.handle.impl.wallet.shand100001.response;

/**
 * 二维码订单创造响应
 * 
 * @author AnLin
 * @since 2016年8月7日
 * @version V1.0.0
 */
public class QrOrderCreateResponse extends SandpayResponse {

	private QrOrderCreateResponseBody body;
	
	public QrOrderCreateResponseBody getBody() {
		return body;
	}
	public void setBody(QrOrderCreateResponseBody body) {
		this.body = body;
	}

	public static class QrOrderCreateResponseBody {
		private String orderCode;  // 商户订单号
		private String totalAmount;  // 订单金额
		private String qrCode;  // 二维码码串
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
		public String getQrCode() {
			return qrCode;
		}
		public void setQrCode(String qrCode) {
			this.qrCode = qrCode;
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
