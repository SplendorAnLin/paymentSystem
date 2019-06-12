package com.yl.online.trade.hessian.bean;

import com.lefu.hessian.bean.JsonBean;

/**
 * 订单支付远程Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
public class OrderPayBean implements JsonBean {

	private static final long serialVersionUID = 3596930542280609726L;

	/** 合作方编码 */
	private String partnerCode;
	/** 合作方名称 */
	private String partnerName;
	/** 支付金额 */
	private double amount;
	/** 交易卡号 */
	private String cardNo;
	/** 交易卡种 */
	private String cardType;
	/** 处理结果码 */
	private String responseCode;
	/** 签名串 */
	private String signMsg;
	/** 扩展参数 */
	private String extendParams;

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getSignMsg() {
		return signMsg;
	}

	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}

	public String getExtendParams() {
		return extendParams;
	}

	public void setExtendParams(String extendParams) {
		this.extendParams = extendParams;
	}

	@Override
	public String toString() {
		return "OrderPayBean [partnerCode=" + partnerCode + ", partnerName=" + partnerName + ", amount=" + amount + ", cardNo=" + cardNo + ", cardType=" + cardType
				+ ", responseCode=" + responseCode + ", extendParams=" + extendParams + "]";
	}

	public char[] formatResponseMsg() {
		return null;
	}

}
