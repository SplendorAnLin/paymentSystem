package com.yl.payinterface.core.bean;

import java.io.Serializable;

import com.yl.payinterface.core.enums.RefundType;

/**
 * 接口退款信息Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月24日
 * @version V1.0.0
 */
public class RefundInfo implements Serializable {

	private static final long serialVersionUID = 3189363605326010124L;
	/** 退款种类 */
	private RefundType type;
	/** 支持部分退款 */
	private boolean supportPartRefund = true;
	/** 单笔订单最多允许的接口退款次数 */
	private Integer countLimit;
	/** 退款有效时间 */
	private Integer validityDays;

	public boolean isSupportPartRefund() {
		return supportPartRefund;
	}

	public void setSupportPartRefund(boolean supportPartRefund) {
		this.supportPartRefund = supportPartRefund;
	}

	public Integer getCountLimit() {
		return countLimit;
	}

	public void setCountLimit(Integer countLimit) {
		this.countLimit = countLimit;
	}

	public Integer getValidityDays() {
		return validityDays;
	}

	public void setValidityDays(Integer validityDays) {
		this.validityDays = validityDays;
	}

	public RefundType getType() {
		return type;
	}

	public void setType(RefundType type) {
		this.type = type;
	}

	/* 
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RefundInfo [type=");
		builder.append(type);
		builder.append(", supportPartRefund=");
		builder.append(supportPartRefund);
		builder.append(", countLimit=");
		builder.append(countLimit);
		builder.append(", validityDays=");
		builder.append(validityDays);
		builder.append("]");
		return builder.toString();
	}

}
