package com.yl.pay.pos.api.bean;

/**
 * Title: 银行
 * Copyright: Copyright (c)2011
 * Company: lepay
 * @author haitao.liu
 */
public class SecretKey  extends BaseEntity{
	private String keyName;			//
	private String key;		//
	private String checkValue;		//


	public String getKey() {
		return key;
	}	

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCheckValue() {
		return checkValue;
	}

	public void setCheckValue(String checkValue) {
		this.checkValue = checkValue;
	}
	
}
