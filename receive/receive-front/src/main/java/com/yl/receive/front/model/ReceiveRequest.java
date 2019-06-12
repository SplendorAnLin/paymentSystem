package com.yl.receive.front.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 代收信息
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月17日
 * @version V1.0.0
 */
public class ReceiveRequest implements Serializable{

	private static final long serialVersionUID = 5275001943055344889L;
	/** 银行编号 */
	@NotBlank
	private String bankCode;
	/** 所有者编号 */
	@NotBlank
	private String ownerId;
	/** 账户号 */
	@NotBlank
	private String accountNo;
	/** 账户名 */
	@NotBlank
	private String accountName;
	/** 账户类型 */
	@NotBlank
	private String accType;
	/** 账号类型 */
	@NotBlank
	private String accNoType;
	/** 证件号码 */
	@NotBlank
	private String cerNo;
	/** 证件类型 */
	@NotBlank
	private String cerType;
	/** 金额 */
	@NotBlank
	private double amount;
	/** 通知地址 */
	private String notifyUrl;
	/** 备注 */
	private String remark;
	/** 开户行 */
	private String openBank;
	/** 商家订单号 */
	@NotBlank
	private String customerOrderCode;
	/** 手机号码 */
	private String phone;
	/** 省份 */
	private String province;
	/** 城市 */
	private String city;
	
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccType() {
		return accType;
	}
	public void setAccType(String accType) {
		this.accType = accType;
	}
	public String getAccNoType() {
		return accNoType;
	}
	public void setAccNoType(String accNoType) {
		this.accNoType = accNoType;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCerNo() {
		return cerNo;
	}
	public void setCerNo(String cerNo) {
		this.cerNo = cerNo;
	}
	public String getCerType() {
		return cerType;
	}
	public void setCerType(String cerType) {
		this.cerType = cerType;
	}
	public String getOpenBank() {
		return openBank;
	}
	public void setOpenBank(String openBank) {
		this.openBank = openBank;
	}
	public String getCustomerOrderCode() {
		return customerOrderCode;
	}
	public void setCustomerOrderCode(String customerOrderCode) {
		this.customerOrderCode = customerOrderCode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	@Override
	public String toString() {
		return "ReceiveRequest [bankCode=" + bankCode + ", ownerId=" + ownerId
				+ ", accountNo=" + accountNo + ", accountName=" + accountName
				+ ", accType=" + accType + ", accNoType=" + accNoType
				+ ", cerNo=" + cerNo + ", cerType=" + cerType + ", amount="
				+ amount + ", notifyUrl=" + notifyUrl + ", remark=" + remark
				+ ", openBank=" + openBank + ", customerOrderCode="
				+ customerOrderCode + ", phone=" + phone + ", province="
				+ province + ", city=" + city + "]";
	}
	
}
