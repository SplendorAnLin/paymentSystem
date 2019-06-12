package com.yl.dpay.core.entity;

import java.io.Serializable;
import java.util.Date;

import com.yl.dpay.core.enums.AccountType;
import com.yl.dpay.core.enums.OwnerRole;
import com.yl.dpay.core.enums.QuickPayAutoRequestStatus;
import com.yl.dpay.core.enums.QuickPayRemitType;

/***
 * 快捷自动代付
 * 
 * @author Administrator
 *
 */
public class QuickPayAutoRequest extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 9103506692403012960L;

	private Double remitAmount;

	private Double remitFee;
	/** 持有者ID */
	private String ownerId;
	/** 持有者角色 */
	private OwnerRole ownerRole;
	/** 操作来源 */
	private String operator;
	/** 银行编号 */
	private String bankCode;
	/** 银行名称 */
	private String bankName;
	/** 账户名称 */
	private String accountName;
	/** 账户编号 */
	private String accountNo;
	/** 账户类型 */
	private AccountType accountType;
	/** 发起状态 */
	private QuickPayAutoRequestStatus autoRequestStatus;
	/** 订单号 */
	private String orderCode;
	/** 代付流水号 */
	private String flowNo;
	/** 编号 */
	private String code;
	/** 入账时间 */
	private Date accountDate;
	/** 发起时间 */
	private Date applyDate;
	/** 欲冻编号 */
	private String freezeNo;
	/** 欲冻状态 */
	private QuickPayAutoRequestStatus freezeStatus;
	/** 请款状态 */
	private QuickPayAutoRequestStatus consultRemitStatus;
	/** 请款手续费状态 */
	private QuickPayAutoRequestStatus consultRemitFeeStatus;
	/** 生产付款订单状态 */
	private QuickPayAutoRequestStatus requestStatus;
	/** 快捷自动代付付款方式 */
	private QuickPayRemitType quickPayRemitType;
	/** 代付付接口编号 */
	private String remitInterfaceCode;

	public Double getRemitAmount() {
		return remitAmount;
	}

	public void setRemitAmount(Double remitAmount) {
		this.remitAmount = remitAmount;
	}

	public Double getRemitFee() {
		return remitFee;
	}

	public void setRemitFee(Double remitFee) {
		this.remitFee = remitFee;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public OwnerRole getOwnerRole() {
		return ownerRole;
	}

	public void setOwnerRole(OwnerRole ownerRole) {
		this.ownerRole = ownerRole;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
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

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getFlowNo() {
		return flowNo;
	}

	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getFreezeNo() {
		return freezeNo;
	}

	public void setFreezeNo(String freezeNo) {
		this.freezeNo = freezeNo;
	}

	public QuickPayAutoRequestStatus getAutoRequestStatus() {
		return autoRequestStatus;
	}

	public void setAutoRequestStatus(QuickPayAutoRequestStatus autoRequestStatus) {
		this.autoRequestStatus = autoRequestStatus;
	}

	public QuickPayAutoRequestStatus getFreezeStatus() {
		return freezeStatus;
	}

	public void setFreezeStatus(QuickPayAutoRequestStatus freezeStatus) {
		this.freezeStatus = freezeStatus;
	}

	public QuickPayAutoRequestStatus getConsultRemitStatus() {
		return consultRemitStatus;
	}

	public void setConsultRemitStatus(QuickPayAutoRequestStatus consultRemitStatus) {
		this.consultRemitStatus = consultRemitStatus;
	}

	public QuickPayAutoRequestStatus getConsultRemitFeeStatus() {
		return consultRemitFeeStatus;
	}

	public void setConsultRemitFeeStatus(QuickPayAutoRequestStatus consultRemitFeeStatus) {
		this.consultRemitFeeStatus = consultRemitFeeStatus;
	}

	public QuickPayAutoRequestStatus getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(QuickPayAutoRequestStatus requestStatus) {
		this.requestStatus = requestStatus;
	}

	public QuickPayRemitType getQuickPayRemitType() {
		return quickPayRemitType;
	}

	public void setQuickPayRemitType(QuickPayRemitType quickPayRemitType) {
		this.quickPayRemitType = quickPayRemitType;
	}

	public String getRemitInterfaceCode() {
		return remitInterfaceCode;
	}

	public void setRemitInterfaceCode(String remitInterfaceCode) {
		this.remitInterfaceCode = remitInterfaceCode;
	}

}
