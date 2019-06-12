package com.yl.online.model.model;

import javax.validation.Valid;
import javax.validation.constraints.Digits;

/**
 * 合作方请求信息
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月16日
 * @version V1.0.0
 */
public class PartnerRequest extends AutoStringIDModel {

	private static final long serialVersionUID = -4094831396929391521L;
	/** 接口编号 */
	private String apiCode;
	/** 参数编码字符集 */
	private String inputCharset;
	/** 签名方式 */
	private String signType;
	/** 签名 */
	private String sign;
	/** 合作方编号 */
	private String partner;
	/** 合作方唯一订单号 */
	private String outOrderId;
	/** 订单金额 */
	@Digits(integer = 10, fraction = 2)
	private String amount;
	/** 业务扩展参数 */
	private String extParam;
	/** 回传参数 */
	private String returnParam;
	/** IP */
	private String ip;
	/** 引用 */
	private String referer;
	/** 原始请求信息 */
	private String originalRequest;
	/** 合作方请求差异化信息 */
	@Valid
	private String info;

	// 移动认证支付信息
	/** 银行卡号 */
	private String bankCardNo;
	/** 有效期 */
	private String valDate;
	/** 安全码 */
	private String cvv;
	/** 姓名 */
	private String payerName;
	/** 身份证号 */
	private String certNo;
	/** 绑定手机号 */
	private String payerMobNo;
	/** 短信验证码 */
	private String verifycode;

	// 微信公众号支付
	/** openId **/
	private String openId;

	/** 支付接口编号 **/
	private String interfaceCode;
	/** 通知地址 **/
	private String notifyUrl;
	/** 支付类型 **/
	private String payType;
	/** 接入方式 **/
	private String accMode;
	/** 微信二维码方式 */
	private String wxNativeType;

	/** 商品名称 */
	private String product;
	/** 页面通知地址 **/
	private String pageNotifyUrl;

	private String authCode;

	// 快捷支付
	/** 结算金额 */
	@Digits(integer = 10, fraction = 2)
	private String settleAmount;
	/** 结算卡卡号 */
	private String settleCardNo;
	/** 结算卡开户行 */
	private String settleName;
	/** 快捷费率 */
	private String quickPayFee;
	/** 结算费 */
	@Digits(integer = 10, fraction = 2)
	private String remitFee;
	/** 结算方式 */
	private String settleType;

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

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getOutOrderId() {
		return outOrderId;
	}

	public void setOutOrderId(String outOrderId) {
		this.outOrderId = outOrderId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
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

	public String getOriginalRequest() {
		return originalRequest;
	}

	public void setOriginalRequest(String originalRequest) {
		this.originalRequest = originalRequest;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
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

	public String getVerifycode() {
		return verifycode;
	}

	public void setVerifycode(String verifycode) {
		this.verifycode = verifycode;
	}

	public String getValDate() {
		return valDate;
	}

	public void setValDate(String valDate) {
		this.valDate = valDate;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getInterfaceCode() {
		return interfaceCode;
	}

	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getAccMode() {
		return accMode;
	}

	public void setAccMode(String accMode) {
		this.accMode = accMode;
	}

	public String getWxNativeType() {
		return wxNativeType;
	}

	public void setWxNativeType(String wxNativeType) {
		this.wxNativeType = wxNativeType;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getPageNotifyUrl() {
		return pageNotifyUrl;
	}

	public void setPageNotifyUrl(String pageNotifyUrl) {
		this.pageNotifyUrl = pageNotifyUrl;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getSettleAmount() {
		return settleAmount;
	}

	public void setSettleAmount(String settleAmount) {
		this.settleAmount = settleAmount;
	}

	public String getSettleCardNo() {
		return settleCardNo;
	}

	public void setSettleCardNo(String settleCardNo) {
		this.settleCardNo = settleCardNo;
	}

	public String getSettleName() {
		return settleName;
	}

	public void setSettleName(String settleName) {
		this.settleName = settleName;
	}

	public String getQuickPayFee() {
		return quickPayFee;
	}

	public void setQuickPayFee(String quickPayFee) {
		this.quickPayFee = quickPayFee;
	}

	public String getRemitFee() {
		return remitFee;
	}

	public void setRemitFee(String remitFee) {
		this.remitFee = remitFee;
	}

	public String getSettleType() {
		return settleType;
	}

	public void setSettleType(String settleType) {
		this.settleType = settleType;
	}

	@Override
	public String toString() {
		return "PartnerRequest [apiCode=" + apiCode + ", inputCharset=" + inputCharset + ", signType=" + signType + ", sign=" + sign + ", partner=" + partner + ", outOrderId=" + outOrderId + ", amount=" + amount + ", extParam=" + extParam + ", returnParam=" + returnParam + ", ip=" + ip
				+ ", referer=" + referer + ", originalRequest=" + originalRequest + ", info=" + info + ", bankCardNo=" + bankCardNo + ", valDate=" + valDate + ", cvv=" + cvv + ", payerName=" + payerName + ", certNo=" + certNo + ", payerMobNo=" + payerMobNo + ", verifycode=" + verifycode
				+ ", openId=" + openId + ", interfaceCode=" + interfaceCode + ", notifyUrl=" + notifyUrl + ", payType=" + payType + ", accMode=" + accMode + ", wxNativeType=" + wxNativeType + ", product=" + product + ", pageNotifyUrl=" + pageNotifyUrl + ", authCode=" + authCode + ", settleAmount="
				+ settleAmount + ", settleCardNo=" + settleCardNo + ", settleName=" + settleName + ", quickPayFee=" + quickPayFee + ", remitFee=" + remitFee + ", settleType=" + settleType + "]";
	}

}
