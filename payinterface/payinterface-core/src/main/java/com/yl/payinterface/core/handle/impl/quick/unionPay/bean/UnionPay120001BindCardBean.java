package com.yl.payinterface.core.handle.impl.quick.unionPay.bean;

/**
 * @ClassName UnionPay120001BindCardBean
 * @Description 银联绑卡Bean
 * @author 聚合支付
 * @date 2017年11月18日 下午6:04:27
 */
public class UnionPay120001BindCardBean extends UnionPay120001CommonBean {

	private String name;
	/** 卡号 */
	private String pan;

	private String phone;
	// 以下参数开通用
	/** 有效期 */
	private String ExpireDate;

	private String cvn;

	private String smsCode;
	
	public UnionPay120001BindCardBean(UnionPay120001CommonBean commonBean) {
		super.setDmnum(commonBean.getDmnum());
		super.setMsgtype(commonBean.getMsgtype());
		super.setTrano(commonBean.getTrano());
		super.setStan(commonBean.getStan());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getExpireDate() {
		return ExpireDate;
	}

	public void setExpireDate(String expireDate) {
		ExpireDate = expireDate;
	}

	public String getCvn() {
		return cvn;
	}

	public void setCvn(String cvn) {
		this.cvn = cvn;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	@Override
	public String toString() {
		return "UnionPay120001BindCardBean [name=" + name + ", pan=" + pan + ", phone=" + phone + ", ExpireDate=" + ExpireDate + ", cvn=" + cvn + ", smsCode=" + smsCode + ", toString()=" + super.toString() + "]";
	}

}
