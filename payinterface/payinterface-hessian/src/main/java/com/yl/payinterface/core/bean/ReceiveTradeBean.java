package com.yl.payinterface.core.bean;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 代收业务处理Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月24日
 * @version V1.0.0
 */
public class ReceiveTradeBean implements Serializable {

	private static final long serialVersionUID = -3758549242410013166L;

	/** 业务请求的支付接口编号 */
	@NotNull
	private String interfaceCode;
	/** 业务请求的支付接口提供方编号 */
	@NotNull
	private String InterfaceProviderCode;
	/** 业务编号 */
	private String businessCode;
	/** 业务订单编号 */
	@NotNull
	private String businessOrderID;
	/** 订单金额 */
	@Digits(integer = 10, fraction = 2)
	private double amount;
	/** 账户号 */
	@NotNull
	private String accountNo;
	/** 账户名 */
	@NotNull
	private String accountName;
	/** 账户类型 */
	private String accountType;
	/** 证件类型 */
	private String certType;
	/** 证件号码 */
	private String certCode;
	/** 账号类型 */
	private String accountNoType;
	@NotNull
	@Pattern(regexp = "PAY")
	private String tradeType;
	/** 开户行 */
	@NotNull
	private String bankName;
	/** 开户行编号 */
	@NotNull
	private String bankCode;
	/** 省份 */
	private String province;
	/** 城市 */
	private String city;
	/** 用途 */
	private String usage;
	/** 备注 */
	private String remark;
	/** 业务订单时间 */
	@NotNull
	private Date businessOrderTime;
	/** 卡类型 */
	private String cardType;
	/** 所有者编号 */
	private String ownerId;
	/** 手机号 */
	private String phone;
	
	public String getInterfaceCode() {
		return interfaceCode;
	}
	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}
	public String getInterfaceProviderCode() {
		return InterfaceProviderCode;
	}
	public void setInterfaceProviderCode(String interfaceProviderCode) {
		InterfaceProviderCode = interfaceProviderCode;
	}
	public String getBusinessCode() {
		return businessCode;
	}
	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}
	public String getBusinessOrderID() {
		return businessOrderID;
	}
	public void setBusinessOrderID(String businessOrderID) {
		this.businessOrderID = businessOrderID;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
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
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
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
	public String getUsage() {
		return usage;
	}
	public void setUsage(String usage) {
		this.usage = usage;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getBusinessOrderTime() {
		return businessOrderTime;
	}
	public void setBusinessOrderTime(Date businessOrderTime) {
		this.businessOrderTime = businessOrderTime;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getAccountNoType() {
		return accountNoType;
	}
	public void setAccountNoType(String accountNoType) {
		this.accountNoType = accountNoType;
	}
	public String getCertType() {
		return certType;
	}
	public void setCertType(String certType) {
		this.certType = certType;
	}
	public String getCertCode() {
		return certCode;
	}
	public void setCertCode(String certCode) {
		this.certCode = certCode;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Override
	public String toString() {
		return "ReceiveTradeBean [interfaceCode=" + interfaceCode
				+ ", InterfaceProviderCode=" + InterfaceProviderCode
				+ ", businessCode=" + businessCode + ", businessOrderID="
				+ businessOrderID + ", amount=" + amount + ", accountNo="
				+ accountNo + ", accountName=" + accountName + ", accountType="
				+ accountType + ", certType=" + certType + ", certCode="
				+ certCode + ", accountNoType=" + accountNoType
				+ ", tradeType=" + tradeType + ", bankName=" + bankName
				+ ", bankCode=" + bankCode + ", province=" + province
				+ ", city=" + city + ", usage=" + usage + ", remark=" + remark
				+ ", businessOrderTime=" + businessOrderTime + ", cardType="
				+ cardType + ", ownerId=" + ownerId + ", phone=" + phone + "]";
	}
}
