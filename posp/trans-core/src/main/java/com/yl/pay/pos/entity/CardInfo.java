package com.yl.pay.pos.entity;

import com.yl.pay.pos.enums.*;

import javax.persistence.*;
import java.util.Date;

/**
 * Title: 卡片信息
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: lepay
 * @author haitao.liu
 */

@Entity
@Table(name = "CARD_INFO")
public class CardInfo extends BaseEntity{

	private String pan;					//卡号
	private String track2;				//二磁（加密）
	private String pin;					//个人标识码
	private String cvv2;				//CVV2
	private Date createTime;			//创建时间
	private Bank issuer;				//发卡行	
	private CardType cardType;			//卡类型
	private CurrencyType currencyType;	//币种
	private String cardVerifyCode;		//卡标识号
	
	@Column(name = "PAN", length = 50)
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	
	@Column(name = "TRACK2", length = 1024)
	public String getTrack2() {
		return track2;
	}
	public void setTrack2(String track2) {
		this.track2 = track2;
	}
	
	@Column(name = "PIN", length = 128)
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	
	@Column(name = "CVV2", length = 20)
	public String getCvv2() {
		return cvv2;
	}
	public void setCvv2(String cvv2) {
		this.cvv2 = cvv2;
	}
	
	@Column(name = "CREATE_TIME", columnDefinition = "DATETIME")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@ManyToOne(fetch = FetchType.EAGER )
	public Bank getIssuer() {
		return issuer;
	}
	public void setIssuer(Bank issuer) {
		this.issuer = issuer;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "CARD_TYPE", columnDefinition = "VARCHAR(20)")
	public CardType getCardType() {
		return cardType;
	}
	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}
		
	@Enumerated(value = EnumType.STRING)
	@Column(name = "CURRENCY_TYPE", columnDefinition = "VARCHAR(20)")
	public CurrencyType getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(CurrencyType currencyType) {
		this.currencyType = currencyType;
	}
	
	@Column(name = "CARD_VERIFY_CODE", length = 20)
	public String getCardVerifyCode() {
		return cardVerifyCode;
	}
	public void setCardVerifyCode(String cardVerifyCode) {
		this.cardVerifyCode = cardVerifyCode;
	}	
	
	
}
