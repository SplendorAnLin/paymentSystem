package com.yl.account.api.bean.response;

import java.util.Date;

import com.yl.account.api.bean.BussinessResponse;
import com.yl.account.api.enums.FreezeStatus;
import com.yl.account.api.enums.ThawType;

/**
 * 账户冻结明细查询响应
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月21日
 * @version V1.0.0
 */
public class AccountFreezeDetailQueryResponse extends BussinessResponse {

	private static final long serialVersionUID = 8575174921970028607L;

	/** 系统编码 */
	private String systemCode;
	/** 业务编码 */
	private String bussinessCode;
	/** 系统交易流水 */
	private String transFlow;
	/** 冻结日期 */
	private Date freezeDate;
	/** 账号 */
	private String accountNo;
	/** 预冻金额 */
	private Double preFreezeBalance;
	/** 冻结金额 */
	private double freezeBalance;
	/** 请款金额 */
	private Double requestBalance;
	/** 请款次数 */
	private int requestBalanceCount;
	/** 冻结状态 */
	private FreezeStatus status;
	/** 待入账日期 */
	private Date waitAccountDate;
	/** 解冻方式 */
	private ThawType unFreezeType;
	/** 解冻日期 */
	private Date unFreezeDate;

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getBussinessCode() {
		return bussinessCode;
	}

	public void setBussinessCode(String bussinessCode) {
		this.bussinessCode = bussinessCode;
	}

	public String getTransFlow() {
		return transFlow;
	}

	public void setTransFlow(String transFlow) {
		this.transFlow = transFlow;
	}

	public Date getFreezeDate() {
		return freezeDate;
	}

	public void setFreezeDate(Date freezeDate) {
		this.freezeDate = freezeDate;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public Double getPreFreezeBalance() {
		return preFreezeBalance;
	}

	public void setPreFreezeBalance(Double preFreezeBalance) {
		this.preFreezeBalance = preFreezeBalance;
	}

	public double getFreezeBalance() {
		return freezeBalance;
	}

	public void setFreezeBalance(double freezeBalance) {
		this.freezeBalance = freezeBalance;
	}

	public Double getRequestBalance() {
		return requestBalance;
	}

	public void setRequestBalance(Double requestBalance) {
		this.requestBalance = requestBalance;
	}

	public int getRequestBalanceCount() {
		return requestBalanceCount;
	}

	public void setRequestBalanceCount(int requestBalanceCount) {
		this.requestBalanceCount = requestBalanceCount;
	}

	public FreezeStatus getStatus() {
		return status;
	}

	public void setStatus(FreezeStatus status) {
		this.status = status;
	}

	public Date getWaitAccountDate() {
		return waitAccountDate;
	}

	public void setWaitAccountDate(Date waitAccountDate) {
		this.waitAccountDate = waitAccountDate;
	}

	public ThawType getUnFreezeType() {
		return unFreezeType;
	}

	public void setUnFreezeType(ThawType unFreezeType) {
		this.unFreezeType = unFreezeType;
	}

	public Date getUnFreezeDate() {
		return unFreezeDate;
	}

	public void setUnFreezeDate(Date unFreezeDate) {
		this.unFreezeDate = unFreezeDate;
	}

	@Override
	public String toString() {
		return "AccountFreezeDetailQueryResponse [systemCode=" + systemCode + ", bussinessCode=" + bussinessCode + ", transFlow=" + transFlow + ", freezeDate="
				+ freezeDate + ", accountNo=" + accountNo + ", preFreezeBalance=" + preFreezeBalance + ", freezeBalance=" + freezeBalance + ", requestBalance="
				+ requestBalance + ", requestBalanceCount=" + requestBalanceCount + ", status=" + status + ", waitAccountDate=" + waitAccountDate
				+ ", unFreezeType=" + unFreezeType + ", unFreezeDate=" + unFreezeDate + "]";
	}

}
