package com.yl.realAuth.model;

import java.util.Date;

import com.yl.realAuth.enums.AuthBusiType;
import com.yl.realAuth.enums.AuthOrderStatus;
import com.yl.realAuth.enums.AuthResult;
import com.yl.realAuth.enums.CardType;

/**
 * 
* @ClassName  ReaNameAuthOrder 
* @Description  
* @author 
* @date 2017年6月22日 下午10:26:40
 */
public class RealNameAuthOrder extends AutoStringIDModel {

	private static final long serialVersionUID = -4094831396929391521L;

	/** 业务类型 */
	
	private AuthBusiType busiType;
	/** 业务标志1 */
	private String businessFlag1;
	/** 合作方唯一订单号 */
	
	private String requestCode;
	/** 合作方编号 */
	
	private String customerNo;
	/** 认证手续费 */
	private Double fee;
	/** 认证接口成本 */
	private Double cost;
	/** 交易状态 */
	
	private AuthOrderStatus authOrderStatus;
	/** 认证状态 */
	
	private AuthResult authResult;
	/** 异步通知地址 */
	private String notifyURL;
	/** 是否实时 */
	
	private String isActual;
	/** 完成时间 */
	private Date completeTime;
	/** 关闭时间 */
	private Date closeTime;
	/** 清算时间 */
	private Date clearTime;
	/** 超时时间 */
	private Date timeOut;
	/** 接口订单号 */
	private String interfaceRequestId;
	/** 通道编号 */
	private String interfaceCode;
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
	/** 卡类型 */
	private CardType cardType;
	/** 返回信息 */
	private String responseMsg;

	/**
	 * 内部错误码 Add By Liu.Meng 20160308
	 */
	private String innerErrorCode;

	/**
	 * 内部错误描述 Add By Liu.Meng 20160308
	 */
	private String innerErrorMsg;

	/**
	 * 银行编号 Add By Liu.Meng 20160311
	 */
	private String bankCode;

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getInnerErrorCode() {
		return innerErrorCode;
	}

	public void setInnerErrorCode(String innerErrorCode) {
		this.innerErrorCode = innerErrorCode;
	}

	public String getInnerErrorMsg() {
		return innerErrorMsg;
	}

	public void setInnerErrorMsg(String innerErrorMsg) {
		this.innerErrorMsg = innerErrorMsg;
	}

	public String getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public AuthResult getAuthResult() {
		return authResult;
	}

	public void setAuthResult(AuthResult authResult) {
		this.authResult = authResult;
	}

	public Date getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}

	public Date getClearTime() {
		return clearTime;
	}

	public void setClearTime(Date clearTime) {
		this.clearTime = clearTime;
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

	public AuthBusiType getBusiType() {
		return busiType;
	}

	public void setBusiType(AuthBusiType busiType) {
		this.busiType = busiType;
	}

	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public String getInterfaceRequestId() {
		return interfaceRequestId;
	}

	public void setInterfaceRequestId(String interfaceRequestId) {
		this.interfaceRequestId = interfaceRequestId;
	}

	public String getInterfaceCode() {
		return interfaceCode;
	}

	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}

	public String getBusinessFlag1() {
		return businessFlag1;
	}

	public void setBusinessFlag1(String businessFlag1) {
		this.businessFlag1 = businessFlag1;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
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

	public Date getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(Date timeOut) {
		this.timeOut = timeOut;
	}

	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	public AuthOrderStatus getAuthOrderStatus() {
		return authOrderStatus;
	}

	public void setAuthOrderStatus(AuthOrderStatus authOrderStatus) {
		this.authOrderStatus = authOrderStatus;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	@Override
	public String toString() {
		return "ReaNameAuthOrder [busiType=" + busiType + ", businessFlag1=" + businessFlag1 + ", requestCode=" + requestCode + ", customerNo=" + customerNo
				+ ", fee=" + fee + ", cost=" + cost + ", authOrderStatus=" + authOrderStatus + ", authResult=" + authResult + ", notifyURL=" + notifyURL
				+ ", isActual=" + isActual + ", completeTime=" + completeTime + ", closeTime=" + closeTime + ", clearTime=" + clearTime + ", timeOut=" + timeOut
				+ ", interfaceRequestId=" + interfaceRequestId + ", interfaceCode=" + interfaceCode + ", payerName=" + payerName + ", payerMobNo=" + payerMobNo
				+ ", bankCardNo=" + bankCardNo + ", bankCardNoEncrypt=" + bankCardNoEncrypt + ", certNo=" + certNo + ", certNoEncrypt=" + certNoEncrypt
				+ ", cardType=" + cardType + ", responseMsg=" + responseMsg + ", innerErrorCode=" + innerErrorCode + ", innerErrorMsg=" + innerErrorMsg
				+ ", bankCode=" + bankCode + "]";
	}

}
