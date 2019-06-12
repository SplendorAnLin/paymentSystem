package com.yl.client.front.model;

import java.io.Serializable;
import java.util.Date;

import com.yl.client.front.enums.Status;

public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String customerNo;
	private String jgId;
	private Status stauts;
	private int error;
	private Date createTime;
	private String customerImg;
	
	public Long getId() {
		return id;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public String getJgId() {
		return jgId;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public void setJgId(String jgId) {
		this.jgId = jgId;
	}
	public Status getStauts() {
		return stauts;
	}
	public void setStauts(Status stauts) {
		this.stauts = stauts;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getError() {
		return error;
	}
	public void setError(int error) {
		this.error = error;
	}
	public String getCustomerImg() {
		return customerImg;
	}
	public void setCustomerImg(String customerImg) {
		this.customerImg = customerImg;
	}
	
}
