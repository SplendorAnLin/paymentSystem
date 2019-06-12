package com.yl.account.api.bean.request;

import java.util.Date;

import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.enums.FundSymbol;

/**
 * 账户历史查询请求
 * 
 * @author 聚合支付有限公司
 * @since 2016年2月24日
 * @version V1.0.0
 */
public class AccountHistoryQuery extends AccountBussinessInterfaceBean {

	private static final long serialVersionUID = 7958560271244554575L;

	/** 用户编号 */
	private String userNo;
	/** 账号 */
	private String accountNo;
	/** 资金标识 */
	private FundSymbol symbol;
	/** 交易订单号 */
	private String transOrder;
	/** 业务类型 */
	private String buizCode;
	/** 创建开始日期 */
	private Date startCreateDate;
	/** 创建结束日期 */
	private Date endCreateDate;
	/** 创建开始交易日期 */
	private Date startTransTime;
	/** 创建结束交易日期 */
	private Date endTransTime;

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public FundSymbol getSymbol() {
		return symbol;
	}

	public void setSymbol(FundSymbol symbol) {
		this.symbol = symbol;
	}

	public String getTransOrder() {
		return transOrder;
	}

	public void setTransOrder(String transOrder) {
		this.transOrder = transOrder;
	}

	public String getBuizCode() {
		return buizCode;
	}

	public void setBuizCode(String buizCode) {
		this.buizCode = buizCode;
	}

	public Date getStartCreateDate() {
		return startCreateDate;
	}

	public void setStartCreateDate(Date startCreateDate) {
		this.startCreateDate = startCreateDate;
	}

	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	public Date getStartTransTime() {
		return startTransTime;
	}

	public void setStartTransTime(Date startTransTime) {
		this.startTransTime = startTransTime;
	}

	public Date getEndTransTime() {
		return endTransTime;
	}

	public void setEndTransTime(Date endTransTime) {
		this.endTransTime = endTransTime;
	}

	@Override
	public String toString() {
		return "AccountHistoryQuery [userNo=" + userNo + ", accountNo=" + accountNo + ", symbol=" + symbol + ", transOrder=" + transOrder + ", buizCode=" + buizCode
				+ ", startCreateDate=" + startCreateDate + ", endCreateDate=" + endCreateDate + ", startTransTime=" + startTransTime + ", endTransTime="
				+ endTransTime + "]";
	}

}
