package com.yl.boss.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.yl.boss.enums.Status;

/**
 * 秘钥
 *
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */

@Entity
@Table(name = "SECRET_KEY")
public class SecretKey extends AutoIDEntity{
	private String keyName;
	private String key;
	private String checkValue;


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
