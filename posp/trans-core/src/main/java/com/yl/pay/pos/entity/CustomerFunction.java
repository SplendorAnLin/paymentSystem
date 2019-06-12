package com.yl.pay.pos.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Title: 商户交易权限
 * Description: 白名单规则
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

@Entity

@Table(name = "CUSTOMER_FUNCTION")
public class CustomerFunction extends BaseEntity{

	private Customer customer;				//商户	
	private String trsanType;				//交易类型
	private String cardType;				//卡类型
	private Date lastUpdateTime;			//最后更新时间
	
	@ManyToOne(fetch = FetchType.LAZY )
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	@Column(name = "CARD_TYPE", columnDefinition = "VARCHAR(20)")
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
	@Column(name = "TRANS_TYPE", columnDefinition = "VARCHAR(30)")
	public String getTrsanType() {
		return trsanType;
	}	
	public void setTrsanType(String trsanType) {
		this.trsanType = trsanType;
	}
	
	@Column(name = "LAST_UPDATE_TIME", columnDefinition = "DATETIME")
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
}
