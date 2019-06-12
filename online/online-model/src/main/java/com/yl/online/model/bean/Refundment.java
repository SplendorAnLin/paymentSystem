package com.yl.online.model.bean;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.yl.online.model.enums.RefundType;
import com.yl.online.model.enums.RefundmentStatus;

/**
 * 退款记录Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月13日
 * @version V1.0.0
 */
public class Refundment implements Serializable {

	private static final long serialVersionUID = -3559577395722340637L;
	/** 交易编号 */
	private String orderCode;
	/** 退款金额 */
	private double amount;
	/** 退款状态 */
	private RefundmentStatus status;
	/** 退款限次 */
	private int refundValidateCount;
	/** 退款种类 */
	private RefundType refundType;
	/** 是否需要审核 */
	private boolean needAudit;
	/** 审核锁定 */
	private boolean auditLock;
	/** 审核人 */
	private String auditor;
	/** 发起退款时间 */
	private Date refundTime;
	/** 退款完成时间 */
	private Date refundCompleteTime;
	/** 退款失败原因 */
	private String reason;
	/** 备注 */
	private String remark;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public RefundmentStatus getStatus() {
		return status;
	}

	public void setStatus(RefundmentStatus status) {
		this.status = status;
	}

	public int getRefundValidateCount() {
		return refundValidateCount;
	}

	public void setRefundValidateCount(int refundValidateCount) {
		this.refundValidateCount = refundValidateCount;
	}

	public RefundType getRefundType() {
		return refundType;
	}

	public void setRefundType(RefundType refundType) {
		this.refundType = refundType;
	}

	public boolean isNeedAudit() {
		return needAudit;
	}

	public void setNeedAudit(boolean needAudit) {
		this.needAudit = needAudit;
	}

	public boolean isAuditLock() {
		return auditLock;
	}

	public void setAuditLock(boolean auditLock) {
		this.auditLock = auditLock;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}

	public Date getRefundCompleteTime() {
		return refundCompleteTime;
	}

	public void setRefundCompleteTime(Date refundCompleteTime) {
		this.refundCompleteTime = refundCompleteTime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
