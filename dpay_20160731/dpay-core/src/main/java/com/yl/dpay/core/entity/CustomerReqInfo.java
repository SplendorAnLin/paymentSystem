package com.yl.dpay.core.entity;

import java.io.Serializable;
import java.util.Date;

import com.yl.dpay.core.enums.CardType;
import com.yl.dpay.core.enums.CerType;

/**
 * 代付请求记录
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月12日
 * @version V1.0.0
 */
public class CustomerReqInfo implements Serializable {

	private static final long serialVersionUID = -2987984879649840727L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * 版本号
	 */
	private int optimistic;

	/**
	 * 创建时间
	 */
	private Date createDate;

	/**
	 * 请求类型
	 */
	private String requestType;

	/**
	 * 商户编号
	 */
	private String customerNo;

	/**
	 * 版本号
	 */
	private String versionCode;

	/**
	 * 商户参数
	 */
	private String customerParam;

	/**
	 * 商户请求时间
	 */
	private String customerRequestTime;

	/**
	 * 代付信息密文
	 */
	private String cipherText;

	/**
	 * 商户订单号
	 */
	private String cutomerOrderCode;

	/**
	 * 收款账号
	 */
	private String accountNo;

	/**
	 * 收款人
	 */
	private String accountName;

	/**
	 * 金额
	 */
	private String amount;

	/**
	 * 账户类型
	 */
	private String accountType;

	/**
	 * 用途描述
	 */
	private String description;

	/**
	 * 通知地址
	 */
	private String notifyUrl;

	/**
	 * ip
	 */
	private String ip;

	/**
	 * 域名
	 */
	private String domain;

	/**
	 * 卡类型
	 */
	private CardType cardType;

	/**
	 * 证件类型
	 */
	private CerType cerType;

	/**
	 * 证件编号
	 */
	private String cerNo;

	/**
	 * 银行编号
	 */
	private String bankCode;
	
	/**
	 * 银行名称
	 */
	private String bankName;
	
	/**
	 * 有效期
	 */
	private String validity;
	
	/**
	 * CVV2
	 */
	private String cvv;
	
	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getOptimistic() {
		return optimistic;
	}

	public void setOptimistic(int optimistic) {
		this.optimistic = optimistic;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getCustomerParam() {
		return customerParam;
	}

	public void setCustomerParam(String customerParam) {
		this.customerParam = customerParam;
	}

	public String getCustomerRequestTime() {
		return customerRequestTime;
	}

	public void setCustomerRequestTime(String customerRequestTime) {
		this.customerRequestTime = customerRequestTime;
	}

	public String getCipherText() {
		return cipherText;
	}

	public void setCipherText(String cipherText) {
		this.cipherText = cipherText;
	}

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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	public CerType getCerType() {
		return cerType;
	}

	public void setCerType(CerType cerType) {
		this.cerType = cerType;
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
	
}
