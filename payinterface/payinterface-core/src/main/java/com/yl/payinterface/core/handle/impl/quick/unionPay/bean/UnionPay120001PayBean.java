package com.yl.payinterface.core.handle.impl.quick.unionPay.bean;

import java.io.Serializable;

/**
 * @ClassName UnionPay120001PayBean
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author Administrator
 * @date 2017年11月20日 上午10:01:57
 */
public class UnionPay120001PayBean extends UnionPay120001CommonBean implements Serializable {
	private static final long serialVersionUID = -3169056246707271514L;

	private String amount;

	private String settletype;

	private String asynurl;

	private String pan;

	private String name;
	/** 凭证号 */
	private String preSerial;

	private String smsCode;
	
	public UnionPay120001PayBean(UnionPay120001CommonBean commonBean) {
		super.setDmnum(commonBean.getDmnum());
		super.setMsgtype(commonBean.getMsgtype());
		super.setTrano(commonBean.getTrano());
		super.setStan(commonBean.getStan());
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getSettletype() {
		return settletype;
	}

	public void setSettletype(String settletype) {
		this.settletype = settletype;
	}

	public String getAsynurl() {
		return asynurl;
	}

	public void setAsynurl(String asynurl) {
		this.asynurl = asynurl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPreSerial() {
		return preSerial;
	}

	public void setPreSerial(String preSerial) {
		this.preSerial = preSerial;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	@Override
	public String toString() {
		return "UnionPay120001PayBean [amount=" + amount + ", settletype=" + settletype + ", asynurl=" + asynurl + ", pan=" + pan + ", name=" + name + ", preSerial=" + preSerial + ", smsCode=" + smsCode + ", toString()=" + super.toString() + "]";
	}

}
