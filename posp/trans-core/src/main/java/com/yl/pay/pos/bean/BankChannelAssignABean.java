package com.yl.pay.pos.bean;

import java.util.List;

/**
 * @author haitao.liu
 */
public class BankChannelAssignABean {
	private String bankBillType;
	private String bankConnectType;
	private String chooseType;
	private List<BankChannelAssignBBean> twoBeans;

	public String getBankBillType() {
		return bankBillType;
	}

	public void setBankBillType(String bankBillType) {
		this.bankBillType = bankBillType;
	}

	public String getBankConnectType() {
		return bankConnectType;
	}

	public void setBankConnectType(String bankConnectType) {
		this.bankConnectType = bankConnectType;
	}

	public List<BankChannelAssignBBean> getTwoBeans() {
		return twoBeans;
	}

	public void setTwoBeans(List<BankChannelAssignBBean> twoBeans) {
		this.twoBeans = twoBeans;
	}

	public String getChooseType() {
		return chooseType;
	}

	public void setChooseType(String chooseType) {
		this.chooseType = chooseType;
	}
}
