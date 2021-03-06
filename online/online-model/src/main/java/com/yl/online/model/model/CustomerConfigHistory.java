package com.yl.online.model.model;

/**
 * 商户配置历史
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月16日
 * @version V1.0.0
 */
public class CustomerConfigHistory extends AutoStringIDModel {

	private static final long serialVersionUID = -4332458715763405301L;
	
	/** 开始时间 **/
	private String startTime;
	/** 结束时间 **/
	private String endTime;
	/** 支付方式 **/
	private String productType;
	/** 商户编号 **/
	private String customerNo;
	/** 单笔最大金额 **/
	private Double maxAmount; 
	/** 单笔最小金额 **/
	private Double minAmount;
	/** 日交易上限 **/
	private Double dayMax;
	/** 操作人 **/
	private String operator;
	/** 操作原因 **/
	private String reason;
	
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public Double getMaxAmount() {
		return maxAmount;
	}
	public void setMaxAmount(Double maxAmount) {
		this.maxAmount = maxAmount;
	}
	public Double getMinAmount() {
		return minAmount;
	}
	public void setMinAmount(Double minAmount) {
		this.minAmount = minAmount;
	}
	public Double getDayMax() {
		return dayMax;
	}
	public void setDayMax(Double dayMax) {
		this.dayMax = dayMax;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
}