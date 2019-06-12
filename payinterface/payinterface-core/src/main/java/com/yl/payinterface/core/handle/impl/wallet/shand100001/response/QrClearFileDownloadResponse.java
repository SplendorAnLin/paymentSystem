package com.yl.payinterface.core.handle.impl.wallet.shand100001.response;

/**
 * 二维码清理文件下载响应
 * 
 * @author AnLin
 * @since 2016年8月7日
 * @version V1.0.0
 */
public class QrClearFileDownloadResponse extends SandpayResponse {
	
	private QrClearFileDownloadResponseBody body;
	
	public QrClearFileDownloadResponseBody getBody() {
		return body;
	}
	public void setBody(QrClearFileDownloadResponseBody body) {
		this.body = body;
	}
	
	public class QrClearFileDownloadResponseBody {
		private String clearDate;  // 清算日期
		private String content;  // 内容
		private String extend;  // 扩展域
		public String getClearDate() {
			return clearDate;
		}
		public void setClearDate(String clearDate) {
			this.clearDate = clearDate;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getExtend() {
			return extend;
		}
		public void setExtend(String extend) {
			this.extend = extend;
		}
	}

}
