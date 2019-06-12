package com.yl.account.model;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 商户余额收支明细表
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月21日
 * @version V1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDayDetail extends AutoStringIDModel {

	/**
	 * @Description 一句话描述方法用法
	 * @see 需要参考的类或方法
	 * @author guangzhi.ji
	 */
	private static final long serialVersionUID = 2359997485976490131L;
	/** 业务日期 */
	private Date busiDate;
	/** 交易日期 */
	private Date transTime;
	/** 业务类型 */
	private String bussinessCode;
	/** 资金标识 */
	private String symbol;
	/** 发生金额汇总 */
	private double dayOccu;

	public AccountDayDetail() {
		setCreateTime(new Date());
		setVersion(System.currentTimeMillis());
	}

	public Date getBusiDate() {
		return busiDate;
	}

	public void setBusiDate(Date busiDate) {
		this.busiDate = busiDate;
	}

	public String getBussinessCode() {
		return bussinessCode;
	}

	public void setBussinessCode(String bussinessCode) {
		this.bussinessCode = bussinessCode;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public double getDayOccu() {
		return dayOccu;
	}

	public void setDayOccu(double dayOccu) {
		this.dayOccu = dayOccu;
	}

	public Date getTransTime() {
		return transTime;
	}

	public void setTransTime(Date transTime) {
		this.transTime = transTime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccountDayDetail [busiDate=");
		builder.append(busiDate);
		builder.append(", transTime=");
		builder.append(transTime);
		builder.append(", bussinessCode=");
		builder.append(bussinessCode);
		builder.append(", symbol=");
		builder.append(symbol);
		builder.append(", dayOccu=");
		builder.append(dayOccu);
		builder.append("]");
		return builder.toString();
	}
}
