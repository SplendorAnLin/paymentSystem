package com.yl.pay.pos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Title: 银行
 * Copyright: Copyright (c)2011
 * Company: lepay
 * @author haitao.liu
 */

@Entity
@Table(name = "SECRET_KEY")
public class SecretKey  extends BaseEntity{
	private String keyName;			//
	private String key;		//
	private String checkValue;		//


	@Column(name = "KEY_VAL", length = 50)
	public String getKey() {
		return key;
	}	

	@Column(name = "KEY_NAME", length = 50)
	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Column(name = "CHECK_VALUE", length = 50)
	public String getCheckValue() {
		return checkValue;
	}

	public void setCheckValue(String checkValue) {
		this.checkValue = checkValue;
	}
	
}
