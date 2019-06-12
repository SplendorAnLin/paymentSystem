package com.yl.realAuth.hessian.bean;

import java.io.Serializable;

import com.yl.realAuth.hessian.enums.AuthBusiType;
import com.yl.realAuth.hessian.enums.AuthOrderStatus;
import com.yl.realAuth.hessian.enums.AuthResult;

/**
 * 实名认证订单查询
 * @author kewei.liu
 * @since 2015年6月10日10:53:33
 */
public class AuthOrderQueryBean implements Serializable {
	private static final long serialVersionUID = -4363226642015317025L;
	/** 订单号 **/
	private String orderCode;
	/** 业务类型 */
	private AuthBusiType busiType;
	/** 合作方唯一订单号 */
	private String requestCode;
	/** 合作方编号 */
	private String customerNo;
	/** 姓名 */
	private String payerName;
	/** 身份证号 */
	private String certNo;
	/** 手机号 */
	private String payerMobNo;
	/** 银行卡号 */
	private String bankCardNo;
	/** 认证结果 */
	private AuthResult authResult;
	/** 订单状态 */
	private AuthOrderStatus authOrderStatus;
	/** 创建起始日期 */
	private String createStartTime;
	/** 创建终止日期 */
	private String createEndTime;
	/** 完成起始日期 */
	private String completeStartTime;
	/** 完成终止日期 */
	private String completeEndTime;
	/**
	 * 银行编号 Add By Liu.Meng 20160311
	 */
	private String bankCode;

	private String interfaceCode;

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public AuthBusiType getBusiType() {
		return busiType;
	}

	public void setBusiType(AuthBusiType busiType) {
		this.busiType = busiType;
	}

	public String getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
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

	public String getCreateStartTime() {
		return createStartTime;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public void setCreateStartTime(String createStartTime) {
		this.createStartTime = createStartTime;
	}

	public String getCreateEndTime() {
		return createEndTime;
	}

	public void setCreateEndTime(String createEndTime) {
		this.createEndTime = createEndTime;
	}

	public String getCompleteStartTime() {
		return completeStartTime;
	}

	public void setCompleteStartTime(String completeStartTime) {
		this.completeStartTime = completeStartTime;
	}

	public String getCompleteEndTime() {
		return completeEndTime;
	}

	public void setCompleteEndTime(String completeEndTime) {
		this.completeEndTime = completeEndTime;
	}

	public AuthResult getAuthResult() {
		return authResult;
	}

	public void setAuthResult(AuthResult authResult) {
		this.authResult = authResult;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public AuthOrderStatus getAuthOrderStatus() {
		return authOrderStatus;
	}

	public void setAuthOrderStatus(AuthOrderStatus authOrderStatus) {
		this.authOrderStatus = authOrderStatus;
	}

	public String getInterfaceCode() {
		return interfaceCode;
	}

	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}

	@Override
	public String toString() {
		return "AuthOrderQueryBean [orderCode=" + orderCode + ", busiType=" + busiType + ", requestCode=" + requestCode + ", customerNo=" + customerNo
				+ ", payerName=" + payerName + ", certNo=" + certNo + ", payerMobNo=" + payerMobNo + ", bankCardNo=" + bankCardNo + ", authResult=" + authResult
				+ ", authOrderStatus=" + authOrderStatus + ", createStartTime=" + createStartTime + ", createEndTime=" + createEndTime + ", completeStartTime="
				+ completeStartTime + ", completeEndTime=" + completeEndTime + ", bankCode=" + bankCode + ",interfaceCode=" + interfaceCode + "]";
	}

}
