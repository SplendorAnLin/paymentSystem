package com.yl.boss.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import com.yl.boss.enums.CardAttr;
import com.yl.boss.enums.CardStatus;
import com.yl.boss.enums.CardType;

/**
 * 交易卡历史
 * @author AnLin
 *
 */
@Entity
@Table(name = "TRANS_CARD_HISTORY")
public class TransCardHistory extends AutoIDEntity{

	private static final long serialVersionUID = 1L;

	/** 编号 */
	private String code;
	
	/** 结算卡编号 */
	private String settleCode;
	
	/** 商户编号 */
	private String customerNo;
	
	/** 卡姓名 */
	private String accountName;
	
	/** 卡号 */
	private String accountNo;
	
	/** 卡类型 */
	private CardType cardType;
	
	/** 卡别名 */
	private String cardAlias;
	
	/** 银行名称 */
	private String bankName;
	
	/** 联行号 */
	private String cnapsCode;
	
	/** 清分行 */
	private String clearBank;
	
	/** 清分行号  */
	private String clearBankCode;
	
	/** 身份证号 */
	private String idNumber;
	
	/** 预留手机号 */
	private String phone;
	
	/** 银行编码 */
	private String bankCode;
	
	/** 绑卡时间 */
	private Date tiedTime;
	
	/** 解绑时间 */
	private Date unlockTime;
	
	/** 卡状态 */
	private CardStatus cardStatus;
	
	/** 卡属性 */
	private CardAttr cardAttr;
	
	/** 订单号 */
	private String orderId;
	
	/** 接口编号 */
	private String interfaceCode;
	
	/** 接口请求号 */
	private String interfaceRequestId;
	
	/** 金额 */
	private double amount;
	
	
	
	
	@Column(name = "CODE",length = 50)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "SETTLE_CODE",length = 50)
	public String getSettleCode() {
		return settleCode;
	}

	public void setSettleCode(String settleCode) {
		this.settleCode = settleCode;
	}

	@Column(name = "CUSTOMER_NO",length = 50)
	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	@Column(name = "ACCOUNT_NAME",length = 50)
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	@Column(name = "ACCOUNT_NO",length = 50)
	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	@Enumerated(value = EnumType.STRING)
	@Column(name = "CARD_TYPE", columnDefinition = "VARCHAR(30)")
	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	@Column(name = "CARD_ALIAS",length = 50)
	public String getCardAlias() {
		return cardAlias;
	}

	public void setCardAlias(String cardAlias) {
		this.cardAlias = cardAlias;
	}

	@Column(name = "BANK_NAME",length = 50)
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name = "CNAPS_CODE",length = 50)
	public String getCnapsCode() {
		return cnapsCode;
	}

	public void setCnapsCode(String cnapsCode) {
		this.cnapsCode = cnapsCode;
	}

	@Column(name = "CLEAR_BANK",length = 50)
	public String getClearBank() {
		return clearBank;
	}

	public void setClearBank(String clearBank) {
		this.clearBank = clearBank;
	}

	@Column(name = "CLEAR_BANK_CODE",length = 50)
	public String getClearBankCode() {
		return clearBankCode;
	}

	public void setClearBankCode(String clearBankCode) {
		this.clearBankCode = clearBankCode;
	}

	@Column(name = "ID_NUMBER",length = 50)
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	@Column(name = "PHONE",length = 50)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "BANK_CODE",length = 50)
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	@Column(name = "TIED_TIME", columnDefinition = "DATETIME")
	public Date getTiedTime() {
		return tiedTime;
	}

	public void setTiedTime(Date tiedTime) {
		this.tiedTime = tiedTime;
	}

	@Column(name = "UNLOCK_TIME", columnDefinition = "DATETIME")
	public Date getUnlockTime() {
		return unlockTime;
	}

	public void setUnlockTime(Date unlockTime) {
		this.unlockTime = unlockTime;
	}

	@Enumerated(value = EnumType.STRING)
	@Column(name = "CARD_STATUS", columnDefinition = "VARCHAR(30)")
	public CardStatus getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(CardStatus cardStatus) {
		this.cardStatus = cardStatus;
	}

	@Enumerated(value = EnumType.STRING)
	@Column(name = "CARD_ATTR", columnDefinition = "VARCHAR(30)")
	public CardAttr getCardAttr() {
		return cardAttr;
	}

	public void setCardAttr(CardAttr cardAttr) {
		this.cardAttr = cardAttr;
	}

	@Column(name = "ORDER_ID",length = 50)
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Column(name = "INTERFACE_CODE",length = 50)
	public String getInterfaceCode() {
		return interfaceCode;
	}

	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}

	@Column(name = "AMOUNT", precision = 2, scale = 4)
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Column(name = "INTERFACE_REQUEST_ID", length = 50)
	public String getInterfaceRequestId() {
		return interfaceRequestId;
	}

	public void setInterfaceRequestId(String interfaceRequestId) {
		this.interfaceRequestId = interfaceRequestId;
	}
}