package com.yl.payinterface.core.handle.impl.wallet.shand100001.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.yl.payinterface.core.handle.impl.wallet.shand100001.response.QrClearFileDownloadResponse;

/**
 * 二维码清理文件下载请求
 * 
 * @author AnLin
 * @since 2016年8月7日
 * @version V1.0.0
 */
public class QrClearFileDownloadRequest extends SandpayRequest<QrClearFileDownloadResponse> {
	
	private QrClearFileDownloadRequestBody body;
	
	public QrClearFileDownloadRequestBody getBody() {
		return body;
	}
	public void setBody(QrClearFileDownloadRequestBody body) {
		this.body = body;
	}

	public static class QrClearFileDownloadRequestBody {
		private String clearDate;  // 清算日期
		private String fileType;  // 文件返回类型
		private String extend;  // 扩展域
		public String getClearDate() {
			return clearDate;
		}
		public void setClearDate(String clearDate) {
			this.clearDate = clearDate;
		}
		public String getFileType() {
			return fileType;
		}
		public void setFileType(String fileType) {
			this.fileType = fileType;
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
	public Class<QrClearFileDownloadResponse> getResponseClass() {
		return QrClearFileDownloadResponse.class;
	}

	/* (non-Javadoc)
	 * @see cn.com.sandpay.api.SandpayRequest#getTxnDesc()
	 */
	@Override
	@JSONField(serialize=false) 
	public String getTxnDesc() {
		return "qrOrderPay";
	}

}
