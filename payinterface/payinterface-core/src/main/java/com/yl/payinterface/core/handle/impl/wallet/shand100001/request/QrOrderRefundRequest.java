package com.yl.payinterface.core.handle.impl.wallet.shand100001.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.yl.payinterface.core.handle.impl.wallet.shand100001.response.QrOrderRefundResponse;

/**
 * 二维码订单退款请求
 * 
 * @author AnLin
 * @since 2016年8月7日
 * @version V1.0.0
 */
public class QrOrderRefundRequest extends SandpayRequest<QrOrderRefundResponse> {
	
	private QrOrderRefundRequestBody body;
	
	public QrOrderRefundRequestBody getBody() {
		return body;
	}
	public void setBody(QrOrderRefundRequestBody body) {
		this.body = body;
	}

	public static class QrOrderRefundRequestBody {
		private String orderCode;  // 商户订单号
		private String oriOrderCode;  // 原商户订单号
		private String refundAmount;  // 退货金额
		private String refundReason;  // 退货原因
		private String extend;  // 扩展域
		public String getOrderCode() {
			return orderCode;
		}
		public void setOrderCode(String orderCode) {
			this.orderCode = orderCode;
		}
		public String getOriOrderCode() {
			return oriOrderCode;
		}
		public void setOriOrderCode(String oriOrderCode) {
			this.oriOrderCode = oriOrderCode;
		}
		public String getRefundAmount() {
			return refundAmount;
		}
		public void setRefundAmount(String refundAmount) {
			this.refundAmount = refundAmount;
		}
		public String getRefundReason() {
			return refundReason;
		}
		public void setRefundReason(String refundReason) {
			this.refundReason = refundReason;
		}
		public String getExtend() {
			return extend;
		}
		public void setExtend(String extend) {
			this.extend = extend;
		}
	}

	/* (non-Javadoc)
	 * @see cn.com.sandpay.api.SandpayRequest#getResponseClass()
	 */
	@Override
	@JSONField(serialize=false) 
	public Class<QrOrderRefundResponse> getResponseClass() {
		return QrOrderRefundResponse.class;
	}

	/* (non-Javadoc)
	 * @see cn.com.sandpay.api.SandpayRequest#getTxnDesc()
	 */
	@Override
	@JSONField(serialize=false) 
	public String getTxnDesc() {
		return "qrOrderRefund";
	}

}
