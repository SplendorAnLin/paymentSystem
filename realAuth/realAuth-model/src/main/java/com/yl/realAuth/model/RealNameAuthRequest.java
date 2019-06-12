package com.yl.realAuth.model;


/**
 * 实名认证合作方请求信息
 * @author Shark
 * @since 2015年6月2日
 */
public class RealNameAuthRequest extends AutoStringIDModel {

	private static final long serialVersionUID = -4094831396929391521L;
	/** 接口编号 */
	private String apiCode;
	/** 版本号 */
	private String versionCode;
	/** 参数编码字符集 */
	private String inputCharset;
	/** 签名方式 */
	private String signType;
	/** 签名 */
	private String sign;
	/** 合作方编号 */
	private String customerNo;
	/** 合作方唯一订单号 */
	private String requestCode;
	/** 业务扩展参数 */
	private String extParam;
	/** 回传参数 */
	private String returnParam;
	/** 是否实时 */
	private String isActual;
	/** 业务类型 */
	private String busiType;
	/** 异步通知地址 */
	private String notifyURL;
	/** 姓名 */
	private String payerName;
	/** 手机号 */
	private String payerMobNo;
	/** 银行卡号,中间四位用*号代替 */
	private String bankCardNo;
	/** 银行卡号,中间对应的密文 */
	private String bankCardNoEncrypt;
	/** 身份证,中间四位用*号代替 */
	private String certNo;
	/** 身份证密文 */
	private String certNoEncrypt;
	
	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

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

	public String getExtParam() {
		return extParam;
	}

	public void setExtParam(String extParam) {
		this.extParam = extParam;
	}

	public String getReturnParam() {
		return returnParam;
	}

	public void setReturnParam(String returnParam) {
		this.returnParam = returnParam;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getPayerMobNo() {
		return payerMobNo;
	}

	public void setPayerMobNo(String payerMobNo) {
		this.payerMobNo = payerMobNo;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getNotifyURL() {
		return notifyURL;
	}

	public void setNotifyURL(String notifyURL) {
		this.notifyURL = notifyURL;
	}

	public String getIsActual() {
		return isActual;
	}

	public void setIsActual(String isActual) {
		this.isActual = isActual;
	}

	public String getBusiType() {
		return busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	public String getApiCode() {
		return apiCode;
	}

	public void setApiCode(String apiCode) {
		this.apiCode = apiCode;
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

	public String getBankCardNoEncrypt() {
		return bankCardNoEncrypt;
	}

	public void setBankCardNoEncrypt(String bankCardNoEncrypt) {
		this.bankCardNoEncrypt = bankCardNoEncrypt;
	}

	public String getCertNoEncrypt() {
		return certNoEncrypt;
	}

	public void setCertNoEncrypt(String certNoEncrypt) {
		this.certNoEncrypt = certNoEncrypt;
	}

	

	@Override
	public String toString() {
		return "ReaNameAuthRequest [apiCode=" + apiCode + ", versionCode=" + versionCode + ", inputCharset=" + inputCharset + ", signType=" + signType + ", sign="
				+ sign + ", customerNo=" + customerNo + ", requestCode=" + requestCode + ", extParam=" + extParam + ", returnParam=" + returnParam + ", isActual="
				+ isActual + ", busiType=" + busiType + ", notifyURL=" + notifyURL + ", payerName=" + payerName + ", payerMobNo=" + payerMobNo + ", bankCardNo="
				+ bankCardNo + ", bankCardNoEncrypt=" + bankCardNoEncrypt + ", certNo=" + certNo + ", certNoEncrypt=" + certNoEncrypt + "]";
	}

}
