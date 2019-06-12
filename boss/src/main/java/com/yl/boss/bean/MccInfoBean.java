package com.yl.boss.bean;

import java.io.Serializable;

public class MccInfoBean implements Serializable{

	private String mcc;				//MCC
	private String category;		//类别
	private String name;			//简称
	public String getMcc() {
		return mcc;
	}
	public void setMcc(String mcc) {
		this.mcc = mcc;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
