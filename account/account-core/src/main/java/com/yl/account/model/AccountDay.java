package com.yl.account.model;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.yl.account.enums.UserRole;

/**
 * 商户余额收支表
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDay extends AutoStringIDModel {

	/**
	 * @Description 一句话描述方法用法
	 * @see 需要参考的类或方法
	 * @author guangzhi.ji
	 */
	private static final long serialVersionUID = 2359997485976490131L;
	/** 业务日期 */
	private Date busiDate;
	/** 用户编号 */
	private String userNo;
	/** 用户角色 */
	private UserRole userRole;
	/** 期初余额 */
	private double dayInitial;
	/** 收入 */
	private double dayReceip;
	/** 支出 */
	private double dayPay;
	/** 期末 */
	private double dayEnd;
	/** 冻结金额 */
	private double dayFreeze;
	/** 解冻金额 */
	private double dayUnFree;
	/** 未付金额 */
	private double dayNoRemit;
	/** 系统核算 */
	private double daySysEnd;
	/** 网银期末 */
	private double dayFundsEnd;
	/** 系统网银差异 */
	private double dayDiff;

	public Date getBusiDate() {
		return busiDate;
	}

	public void setBusiDate(Date busiDate) {
		this.busiDate = busiDate;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public double getDayInitial() {
		return dayInitial;
	}

	public void setDayInitial(double dayInitial) {
		this.dayInitial = dayInitial;
	}

	public double getDayReceip() {
		return dayReceip;
	}

	public void setDayReceip(double dayReceip) {
		this.dayReceip = dayReceip;
	}

	public double getDayPay() {
		return dayPay;
	}

	public void setDayPay(double dayPay) {
		this.dayPay = dayPay;
	}

	public double getDayEnd() {
		return dayEnd;
	}

	public void setDayEnd(double dayEnd) {
		this.dayEnd = dayEnd;
	}

	public double getDayFreeze() {
		return dayFreeze;
	}

	public void setDayFreeze(double dayFreeze) {
		this.dayFreeze = dayFreeze;
	}

	public double getDayUnFree() {
		return dayUnFree;
	}

	public void setDayUnFree(double dayUnFree) {
		this.dayUnFree = dayUnFree;
	}

	public double getDayNoRemit() {
		return dayNoRemit;
	}

	public void setDayNoRemit(double dayNoRemit) {
		this.dayNoRemit = dayNoRemit;
	}

	public double getDayFundsEnd() {
		return dayFundsEnd;
	}

	public void setDayFundsEnd(double dayFundsEnd) {
		this.dayFundsEnd = dayFundsEnd;
	}

	public double getDaySysEnd() {
		return daySysEnd;
	}

	public void setDaySysEnd(double daySysEnd) {
		this.daySysEnd = daySysEnd;
	}

	public double getDayDiff() {
		return dayDiff;
	}

	public void setDayDiff(double dayDiff) {
		this.dayDiff = dayDiff;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccountDay [busiDate=");
		builder.append(busiDate);
		builder.append(", userNo=");
		builder.append(userNo);
		builder.append(", userRole=");
		builder.append(userRole);
		builder.append(", dayInitial=");
		builder.append(dayInitial);
		builder.append(", dayReceip=");
		builder.append(dayReceip);
		builder.append(", dayPay=");
		builder.append(dayPay);
		builder.append(", dayEnd=");
		builder.append(dayEnd);
		builder.append(", dayFreeze=");
		builder.append(dayFreeze);
		builder.append(", dayUnFree=");
		builder.append(dayUnFree);
		builder.append(", dayNoRemit=");
		builder.append(dayNoRemit);
		builder.append(", daySysEnd=");
		builder.append(daySysEnd);
		builder.append(", dayFundsEnd=");
		builder.append(dayFundsEnd);
		builder.append(", dayDiff=");
		builder.append(dayDiff);
		builder.append("]");
		return builder.toString();
	}
}
