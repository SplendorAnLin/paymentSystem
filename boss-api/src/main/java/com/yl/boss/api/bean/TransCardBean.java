package com.yl.boss.api.bean;

import java.util.Date;
import com.yl.boss.api.enums.CardAttr;
import com.yl.boss.api.enums.CardStatus;
import com.yl.boss.api.enums.CardType;

public class TransCardBean extends BaseBean {
	
	private static final long serialVersionUID = 8746056677651009951L;

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

	
	
	
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSettleCode() {
		return settleCode;
	}

	public void setSettleCode(String settleCode) {
		this.settleCode = settleCode;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	public String getCardAlias() {
		return cardAlias;
	}

	public void setCardAlias(String cardAlias) {
		this.cardAlias = cardAlias;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCnapsCode() {
		return cnapsCode;
	}

	public void setCnapsCode(String cnapsCode) {
		this.cnapsCode = cnapsCode;
	}

	public String getClearBank() {
		return clearBank;
	}

	public void setClearBank(String clearBank) {
		this.clearBank = clearBank;
	}

	public String getClearBankCode() {
		return clearBankCode;
	}

	public void setClearBankCode(String clearBankCode) {
		this.clearBankCode = clearBankCode;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public Date getTiedTime() {
		return tiedTime;
	}

	public void setTiedTime(Date tiedTime) {
		this.tiedTime = tiedTime;
	}

	public Date getUnlockTime() {
		return unlockTime;
	}

	public void setUnlockTime(Date unlockTime) {
		this.unlockTime = unlockTime;
	}

	public CardStatus getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(CardStatus cardStatus) {
		this.cardStatus = cardStatus;
	}

	public CardAttr getCardAttr() {
		return cardAttr;
	}

	public void setCardAttr(CardAttr cardAttr) {
		this.cardAttr = cardAttr;
	}
}