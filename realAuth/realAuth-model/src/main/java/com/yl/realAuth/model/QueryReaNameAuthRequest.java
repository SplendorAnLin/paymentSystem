package com.yl.realAuth.model;



/**
 * 实名认证查询请求参数
 * @author zhibin.cui
 * @since 2016年6月23日
 */
public class QueryReaNameAuthRequest  {

	private static final long serialVersionUID = 6154858068879659732L;
	/** 合作方编号 */
	private String customerNo;
	/** 合作方唯一订单号 */
	private String requestCode;
	/** 参数编码字符集 */
	private String inputCharset;
	/** 签名方式 */
	private String signType;
	/** 签名*/
	private String sign;
	/** 请求时间*/
	private String requestTime;
	
	
	
	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}

	public String getInputCharset() {
		return inputCharset;
	}

	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}

	

	@Override
	public String toString() {
		return "QueryReaNameAuthRequest [customerNo=" + customerNo + ", requestCode=" + requestCode + ", inputCharset=" + inputCharset + ", signType=" + signType
				+ ", sign=" + sign + ", requestTime=" + requestTime + "]";
	}

}
