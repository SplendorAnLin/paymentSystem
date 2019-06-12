package com.yl.online.model.model;

import java.util.List;

import com.yl.online.model.enums.ShareTerminal;

/**
 * 订单支付
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月16日
 * @version V1.0.0
 */
public class OfflinePartner extends AutoStringIDModel {

	private static final long serialVersionUID = 5958160719544872004L;
	/** 线下商户号 */
	private String posPartnerCode;
	/** 商户类别 */
	private String MCC;
	/** 地区码 */
	private String areaCode;
	/** 终端号 */
	private List<String> terminalIDs;
	/** 共享终端 */
	private ShareTerminal shareTerminal;

	public String getPosPartnerCode() {
		return posPartnerCode;
	}

	public void setPosPartnerCode(String posPartnerCode) {
		this.posPartnerCode = posPartnerCode;
	}

	public String getMCC() {
		return MCC;
	}

	public void setMCC(String mCC) {
		MCC = mCC;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public List<String> getTerminalIDs() {
		return terminalIDs;
	}

	public void setTerminalIDs(List<String> terminalIDs) {
		this.terminalIDs = terminalIDs;
	}

	public ShareTerminal getShareTerminal() {
		return shareTerminal;
	}

	public void setShareTerminal(ShareTerminal shareTerminal) {
		this.shareTerminal = shareTerminal;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OfflinePartner [posPartnerCode=");
		builder.append(posPartnerCode);
		builder.append(", MCC=");
		builder.append(MCC);
		builder.append(", areaCode=");
		builder.append(areaCode);
		builder.append(", terminalIDs=");
		builder.append(terminalIDs);
		builder.append(", shareTerminal=");
		builder.append(shareTerminal);
		builder.append("]");
		return builder.toString();
	}

}
