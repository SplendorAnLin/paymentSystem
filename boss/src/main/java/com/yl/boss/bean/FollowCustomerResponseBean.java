package com.yl.boss.bean;

public class FollowCustomerResponseBean {

	public static final String SUCCESS = "SUCCESS";
	public static final String FAILED = "FAILED";
	
	private String result;
	
	private String msg;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
