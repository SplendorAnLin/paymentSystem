package com.yl.realAuth.hessian.bean;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.yl.realAuth.hessian.enums.AuthBusiType;
import com.yl.realAuth.hessian.enums.AuthOrderStatus;
import com.yl.realAuth.hessian.enums.AuthResult;
import com.yl.realAuth.hessian.enums.CardType;

/**
 * 实名认证订单查询结果
 * @author kewei.liu
 * @since 2015年7月3日
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReaNameAuthOrderBean implements Serializable {

	private static final long serialVersionUID = 8423282597603100365L;
	/** 主键 */
	private String id;
	/** 订单编号 */
	private String code;
	/** 版本号 */
	private Long version;
	/** 创建时间 */
	private Date createTime;
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
	/** 认证结果 */
	private AuthResult authResult;
	/** 订单状态 */
	private AuthOrderStatus authOrderStatus;
	/** 异步通知地址 */
	private String notifyURL;
	/** 是否实时 */
	private String isActual;
	/** 姓名 */
	private String payerName;
	/** 身份证号 */
	private String certNo;
	/** 手机号 */
	private String payerMobNo;
	/** 银行卡号 */
	private String bankCardNo;
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
	/** 银行卡号,中间对应的密文 */
	private String bankCardNoEncrypt;
	/** 身份证密文 */
	private String certNoEncrypt;
	/** 卡类型 */
	private CardType cardType;
	/** 返回信息 */
	private String responseMsg;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public AuthBusiType getBusiType() {
		return busiType;
	}

	public void setBusiType(AuthBusiType busiType) {
		this.busiType = busiType;
	}

	public String getBusinessFlag1() {
		return businessFlag1;
	}

	public void setBusinessFlag1(String businessFlag1) {
		this.businessFlag1 = businessFlag1;
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

	public String getNotifyURL() {
		return notifyURL;
	}

	public void setNotifyURL(String notifyURL) {
		this.notifyURL = notifyURL;
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

	public AuthResult getAuthResult() {
		return authResult;
	}

	public void setAuthResult(AuthResult authResult) {
		this.authResult = authResult;
	}

	public AuthOrderStatus getAuthOrderStatus() {
		return authOrderStatus;
	}

	public void setAuthOrderStatus(AuthOrderStatus authOrderStatus) {
		this.authOrderStatus = authOrderStatus;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
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

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	public String getIsActual() {
		return isActual;
	}

	public void setIsActual(String isActual) {
		this.isActual = isActual;
	}
}
