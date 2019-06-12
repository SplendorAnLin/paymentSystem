package com.yl.pay.pos.bean;

public class ProxyPayQueyrResponseBean {

	private String ordernum; //订单号
	private String code;	//返回码
	private String msg;		//返回信息
	private String sign;
	public String getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	@Override
	public String toString() {
		return "ProxyPayQueyrResponseBean [ordernum=" + ordernum + ", code=" + code + ", msg=" + msg + ", sign=" + sign
				+ "]";
	}
	
	
}
