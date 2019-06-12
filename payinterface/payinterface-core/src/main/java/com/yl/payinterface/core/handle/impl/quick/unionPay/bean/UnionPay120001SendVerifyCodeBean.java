package com.yl.payinterface.core.handle.impl.quick.unionPay.bean;

public class UnionPay120001SendVerifyCodeBean extends UnionPay120001CommonBean {

	/** 姓名 */
	private String name;
	/** 卡号 */
	private String pan;
	/** 手机号 */
	private String phone;
	/** 清算类型 */
	private String settletype;
	/** 金额 */
	private String amount;
	
	public UnionPay120001SendVerifyCodeBean(UnionPay120001CommonBean commonBean) {
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

	@Override
	public String toString() {
		return "UnionPay120001SendVerifyCodeBean [name=" + name + ", pan=" + pan + ", phone=" + phone + ", settletype=" + settletype + ", amount=" + amount + ", toString()=" + super.toString() + "]";
	}

}
