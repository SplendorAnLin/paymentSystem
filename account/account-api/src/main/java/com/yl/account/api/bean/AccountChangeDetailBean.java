package com.yl.account.api.bean;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.yl.account.api.enums.AccountStatus;
import com.yl.account.api.enums.AccountType;
import com.yl.account.api.enums.FundSymbol;
import com.yl.account.api.enums.UserRole;

/**
 * 账务变更明细Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月15日
 * @version V1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountChangeDetailBean implements Serializable {

	private static final long serialVersionUID = 2889739803308815687L;

	/** 编号 */
	private String code;
	/** 创建时间 */
	private Date createTime;
	/** 账户编号 */
	private String accountNo;
	/** 账户类型 */
	private AccountType accountType;
	/** 账户状态 */
	private AccountStatus accountStatus;
	/** 系统编码 */
	private String systemCode;
	/** 系统流水号-唯一 */
	private String systemFlow;
	/** 业务类型码 */
	private String bussinessCode;
	/** 用户编号 */
	private String userNo;
	/** 用户角色 */
	private UserRole userRole;
	/** 资金标识 */
	private FundSymbol symbol;
	/** 交易金额 */
	private Double amount;
	/** 请求日期 */
	private Date requestTime;
	/** 操作人 */
	private String operator;
	/** 变更原因 */
	private String changeReason;
	/** 转可用标识 **/
	private Date ableDate;
	/** 入账周期 **/
	private int cycle;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getSystemFlow() {
		return systemFlow;
	}

	public void setSystemFlow(String systemFlow) {
		this.systemFlow = systemFlow;
	}

	public String getBussinessCode() {
		return bussinessCode;
	}

	public void setBussinessCode(String bussinessCode) {
		this.bussinessCode = bussinessCode;
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

	public FundSymbol getSymbol() {
		return symbol;
	}

	public void setSymbol(FundSymbol symbol) {
		this.symbol = symbol;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}

	public Date getAbleDate() {
		return ableDate;
	}

	public void setAbleDate(Date ableDate) {
		this.ableDate = ableDate;
	}

	public int getCycle() {
		return cycle;
	}

	public void setCycle(int cycle) {
		this.cycle = cycle;
	}

	@Override
	public String toString() {
		return "AccountChangeDetailBean [code=" + code + ", createTime="
				+ createTime + ", accountNo=" + accountNo + ", accountType="
				+ accountType + ", accountStatus=" + accountStatus
				+ ", systemCode=" + systemCode + ", systemFlow=" + systemFlow
				+ ", bussinessCode=" + bussinessCode + ", userNo=" + userNo
				+ ", userRole=" + userRole + ", symbol=" + symbol + ", amount="
				+ amount + ", requestTime=" + requestTime + ", operator="
				+ operator + ", changeReason=" + changeReason + ", ableDate="
				+ ableDate + ", cycle=" + cycle + "]";
	}
	
}
