/**
 * 
 */
package com.yl.account.model;

import java.util.Date;

import com.yl.account.enums.AccountType;
import com.yl.account.enums.AdjustStatus;
import com.yl.account.enums.ExpireOperate;
import com.yl.account.enums.FundSymbol;
import com.yl.account.enums.HandleStatus;
import com.yl.account.enums.NoticeStatus;
import com.yl.account.enums.UserRole;

/**
 * 调账流水明细
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public class AccountAdjustVoucher extends AutoStringIDModel {

	private static final long serialVersionUID = 6192089044347915738L;
	/** 系统编码 */
	private String system;
	/** 业务类型 */
	private String bussinessCode;
	/** 账户编码 */
	private String accountNo;
	/** 账户类型 */
	private AccountType accountType;
	/** 用户编号 */
	private String userNo;
	/** 用户角色 */
	private UserRole userRole;
	/** 系统交易订单号 */
	private String transOrder;
	/** 调账状态 */
	private AdjustStatus status;
	/** 调账方向 */
	private FundSymbol fundSymbol;
	/** 调账金额 */
	private double amount;
	/** 未冻结金额 */
	private Double unFreezeAmount;
	/** 过期日期 */
	private Integer expireTime;
	/** 过期操作方式 */
	private ExpireOperate expireOperate;
	/** 系统流水 */
	private String flowId;
	/** 冻结编码 */
	private String freezeNo;
	/** 处理状态 */
	private HandleStatus handleStatus;
	/** 通知状态 */
	private NoticeStatus noticeStatus;
	/** 流程实例Id */
	private String processInstanceId;
	/** 操作人 */
	private String operator;
	/** 操作原因 */
	private String reason;
	/** 处理时间 */
	private Date lastModifyTime;
	/** 备注 */
	private String remark;

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getBussinessCode() {
		return bussinessCode;
	}

	public void setBussinessCode(String bussinessCode) {
		this.bussinessCode = bussinessCode;
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

	public String getTransOrder() {
		return transOrder;
	}

	public void setTransOrder(String transOrder) {
		this.transOrder = transOrder;
	}

	public AdjustStatus getStatus() {
		return status;
	}

	public void setStatus(AdjustStatus status) {
		this.status = status;
	}

	public FundSymbol getFundSymbol() {
		return fundSymbol;
	}

	public void setFundSymbol(FundSymbol fundSymbol) {
		this.fundSymbol = fundSymbol;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Double getUnFreezeAmount() {
		return unFreezeAmount;
	}

	public void setUnFreezeAmount(Double unFreezeAmount) {
		this.unFreezeAmount = unFreezeAmount;
	}

	public Integer getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Integer expireTime) {
		this.expireTime = expireTime;
	}

	public ExpireOperate getExpireOperate() {
		return expireOperate;
	}

	public void setExpireOperate(ExpireOperate expireOperate) {
		this.expireOperate = expireOperate;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getFreezeNo() {
		return freezeNo;
	}

	public void setFreezeNo(String freezeNo) {
		this.freezeNo = freezeNo;
	}

	public HandleStatus getHandleStatus() {
		return handleStatus;
	}

	public void setHandleStatus(HandleStatus handleStatus) {
		this.handleStatus = handleStatus;
	}

	public NoticeStatus getNoticeStatus() {
		return noticeStatus;
	}

	public void setNoticeStatus(NoticeStatus noticeStatus) {
		this.noticeStatus = noticeStatus;
	}

	public String getOperator() {
		return operator;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
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

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "AccountAdjustVoucher [system=" + system + ", bussinessCode=" + bussinessCode + ", accountNo=" + accountNo + ", accountType=" + accountType
				+ ", userNo=" + userNo + ", userRole=" + userRole + ", transOrder=" + transOrder + ", status=" + status + ", fundSymbol=" + fundSymbol + ", amount="
				+ amount + ", unFreezeAmount=" + unFreezeAmount + ", expireTime=" + expireTime + ", expireOperate=" + expireOperate + ", flowId=" + flowId
				+ ", freezeNo=" + freezeNo + ", handleStatus=" + handleStatus + ", noticeStatus=" + noticeStatus + ", processInstanceId=" + processInstanceId
				+ ", operator=" + operator + ", reason=" + reason + ", lastModifyTime=" + lastModifyTime + ", remark=" + remark + "]";
	}

}
