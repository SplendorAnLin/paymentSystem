package com.yl.dpay.front.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import net.sf.json.util.JSONStringer;

/**
 * 代付信息bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月22日
 * @version V1.0.0
 */
public class DpayInfoBean implements Serializable {

	private static final long serialVersionUID = -1369668263089526912L;

	/**
	 * 商户订单号
	 */
	@NotNull
	@Size(max = 30)
	private String cutomerOrderCode;

	/**
	 * 收款账号
	 */
	@NotNull
	@Size(max = 50)
	private String accountNo;

	/**
	 * 收款人
	 */
	@NotNull
	@Size(max = 200)
	private String accountName;

	/**
	 * 金额
	 */
	@NotNull
	@Size(max = 20)
	private String amount;

	/**
	 * 账户类型
	 */
	@NotNull
	@Size(max = 20)
	private String accountType;

	/**
	 * 用途描述
	 */
	@Size(max = 200)
	private String description;

	/**
	 * 通知地址
	 */
	@Size(max = 256)
	private String notifyUrl;
	
	/**
	 * 证件类型
	 */
	@NotNull
	private String cerType;
	
	/**
	 * 卡类型
	 */
	@NotNull
	private String cardType;
	
	/**
	 * 证件编号
	 */
	@NotNull
	private String cerNo;
	
	/**
	 * 银行编号
	 */
	private String bankCode;
	
	/**
	 * 有效期
	 */
	private String validity;
	
	/**
	 * CVV2
	 */
	private String cvv;
	
	/**
	 * 银行名称
	 */
	private String bankName;

	public String getCutomerOrderCode() {
		return cutomerOrderCode;
	}

	public void setCutomerOrderCode(String cutomerOrderCode) {
		this.cutomerOrderCode = cutomerOrderCode;
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

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getCerType() {
		return cerType;
	}

	public void setCerType(String cerType) {
		this.cerType = cerType;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCerNo() {
		return cerNo;
	}

	public void setCerNo(String cerNo) {
		this.cerNo = cerNo;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Override
	public String toString() {
		return "DpayInfoBean [cutomerOrderCode=" + cutomerOrderCode
				+ ", accountNo=" + accountNo + ", accountName=" + accountName
				+ ", amount=" + amount + ", accountType=" + accountType
				+ ", description=" + description + ", notifyUrl=" + notifyUrl
				+ ", cerType=" + cerType + ", cardType=" + cardType
				+ ", cerNo=" + cerNo + ", bankCode=" + bankCode + ", validity="
				+ validity + ", cvv=" + cvv + ", bankName=" + bankName + "]";
	}

	public String toJsonString() {
		JSONStringer jsonStringer = new JSONStringer();
		jsonStringer.object();
		jsonStringer.key("accountName");
		jsonStringer.value(accountName);
		jsonStringer.key("accountNo");
		jsonStringer.value(accountNo);
		jsonStringer.key("accountType");
		jsonStringer.value(accountType);
		jsonStringer.key("amount");
		jsonStringer.value(amount);
		jsonStringer.key("bankCode");
		jsonStringer.value(bankCode);
		jsonStringer.key("bankName");
		jsonStringer.value(bankName);
		jsonStringer.key("cardType");
		jsonStringer.value(cardType);
		jsonStringer.key("cerNo");
		jsonStringer.value(cerNo);
		jsonStringer.key("cerType");
		jsonStringer.value(cerType);
		jsonStringer.key("cvv");
		jsonStringer.value(cvv);
		jsonStringer.key("cutomerOrderCode");
		jsonStringer.value(cutomerOrderCode);
		jsonStringer.key("description");
		jsonStringer.value(description);
		jsonStringer.key("notifyUrl");
		jsonStringer.value(notifyUrl);
		jsonStringer.key("validity");
		jsonStringer.value(validity);
		jsonStringer.endObject();
		return jsonStringer.toString();
	}
}
