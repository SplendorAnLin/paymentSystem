package com.yl.payinterface.core.handle.impl.wallet.shand100001.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.yl.payinterface.core.handle.impl.wallet.shand100001.response.QrOrderCancelResponse;

/**
 * 二维码订单撤销请求
 * 
 * @author AnLin
 * @since 2016年8月7日
 * @version V1.0.0
 */
public class QrOrderCancelRequest extends SandpayRequest<QrOrderCancelResponse> {
	
	private QrOrderCancelRequestBody body;
	
	public QrOrderCancelRequestBody getBody() {
		return body;
	}
	public void setBody(QrOrderCancelRequestBody body) {
		this.body = body;
	}

	public static class QrOrderCancelRequestBody {
		private String orderCode;  // 商户订单号
		private String oriOrderCode;  // 原商户订单号
		private String oriTotalAmount;  // 原订单金额
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
		public String getOriTotalAmount() {
			return oriTotalAmount;
		}
		public void setOriTotalAmount(String oriTotalAmount) {
			this.oriTotalAmount = oriTotalAmount;
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
	public Class<QrOrderCancelResponse> getResponseClass() {
		return QrOrderCancelResponse.class;
	}

	/* (non-Javadoc)
	 * @see cn.com.sandpay.api.SandpayRequest#getTxnDesc()
	 */
	@Override
	@JSONField(serialize=false) 
	public String getTxnDesc() {
		return "qrOrderCancel";
	}

}
