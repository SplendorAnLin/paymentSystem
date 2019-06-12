package com.yl.pay.pos.entity;

import com.yl.pay.pos.enums.CardType;

import javax.persistence.*;
import java.util.Date;

/**
 * Title: 发卡行标识代码 
 * Description: bank identification number; BIN
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

@Entity
@Table(name = "BANK_ID_NUMBER")
public class BankIdNumber extends BaseEntity{
  
	private String bin; 			//发卡行标识代码
	private String bankName;		//发卡行名称
	private Bank bank;				//银行
	private String cardName;		//卡名
	private Integer panLength;		//卡号长度
	private Integer verifyLength;	//卡标识长度
	private String verifyCode;		//卡标识号取值
	private CardType cardType;		//卡类型
	private String cardStyle;		//卡的样式、品牌
	private Date releaseDate;		//发布日期 
	private Date createDate;		//创建时间	
	
	@Column(name = "BIN", length = 50)
	public String getBin() {
		return bin;
	}
	public void setBin(String bin) {
		this.bin = bin;
	}
	
	@Column(name = "BANK_NAME", length = 100)
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Column(name = "CARD_NAME", length = 100)
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	
	@Column(name = "VERIFY_LENGTH")
	public Integer getVerifyLength() {
		return verifyLength;
	}
	public void setVerifyLength(Integer verifyLength) {
		this.verifyLength = verifyLength;
	}
	
	@Column(name = "VERIFY_CODE", length = 20)
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "CARD_TYPE", columnDefinition = "VARCHAR(30)")
	public CardType getCardType() {
		return cardType;
	}
	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}
	
	@Column(name = "RELEASE_DATE",columnDefinition = "DATE")
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	@Column(name = "CREATE_DATE",columnDefinition = "DATETIME")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@ManyToOne(fetch = FetchType.LAZY )
	public Bank getBank() {
		return bank;
	}
	public void setBank(Bank bank) {
		this.bank = bank;
	}
	
	@Column(name = "PAN_LENGTH")
	public Integer getPanLength() {
		return panLength;
	}
	public void setPanLength(Integer panLength) {
		this.panLength = panLength;
	}
	
	@Column(name = "CARD_STYLE", length = 30)
	public String getCardStyle() {
		return cardStyle;
	}
	public void setCardStyle(String cardStyle) {
		this.cardStyle = cardStyle;
	}	
	
}
