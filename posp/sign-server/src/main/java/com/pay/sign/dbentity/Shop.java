package com.pay.sign.dbentity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.pay.sign.dao.util.BaseEntity;

/**
 * Title: POS SHOP
 * Description:  POS网点
 * Copyright: Copyright (c)2015
 * Company: pay
 * @author zhongxiang.ren
 */

@Entity
@Table(name = "SHOP")
public class Shop extends BaseEntity{

	private static final long serialVersionUID = 1L;
	private String shopNo;			//网点编号
	
	@Column(name = "SHOP_NO", length = 30)
	public String getShopNo() {
		return shopNo;
	}
	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}
	
}
