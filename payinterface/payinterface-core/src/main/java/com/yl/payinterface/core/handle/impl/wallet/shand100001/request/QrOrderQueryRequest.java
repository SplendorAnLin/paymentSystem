package com.yl.payinterface.core.handle.impl.wallet.shand100001.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.yl.payinterface.core.handle.impl.wallet.shand100001.response.QrOrderQueryResponse;

/**
 * 二维码订单查询请求
 * 
 * @author AnLin
 * @since 2016年8月7日
 * @version V1.0.0
 */
public class QrOrderQueryRequest extends SandpayRequest<QrOrderQueryResponse> {
	
	private QrOrderQueryRequestBody body;
	
	public QrOrderQueryRequestBody getBody() {
		return body;
	}
	public void setBody(QrOrderQueryRequestBody body) {
		this.body = body;
	}

	public static class QrOrderQueryRequestBody {
		private String orderCode;  // 商户订单号
		private String extend;  // 扩展域
		public String getOrderCode() {
			return orderCode;
		}
		public void setOrderCode(String orderCode) {
			this.orderCode = orderCode;
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
	public Class<QrOrderQueryResponse> getResponseClass() {
		return QrOrderQueryResponse.class;
	}

	/* (non-Javadoc)
	 * @see cn.com.sandpay.api.SandpayRequest#getTxnDesc()
	 */
	@Override
	@JSONField(serialize=false) 
	public String getTxnDesc() {
		return "qrOrderQuery";
	}

}
