package com.yl.agent.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.yl.agent.enums.RefundType;
import com.yl.agent.enums.RefundmentStatus;

/**
 * 退款记录Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月1日
 * @version V1.0.0
 */
public class RefundmentBean implements Serializable{

	private static final long serialVersionUID = -3559577395722340637L;
	/** 主键 */
	private Long id;
	/** 编号 */
	private String code;
	/** 版本号 */
	private Long version;
	/** 创建时间 */
	private Date createTime;
	/** 交易订单 */
	private TradeOrderBean tradeOrder;
	/** 退款金额 */
	private BigDecimal amount;
	/** 退款状态 */
	private RefundmentStatus status;
	/** 退款限次 */
	private int refundValidateCount;
	/** 退款种类*/
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
	/** 退款失败原因*/
	private String reason;
	/**备注 */
	private String remark;
	

	public TradeOrderBean getTradeOrder() {
		return tradeOrder;
	}

	public void setTradeOrder(TradeOrderBean tradeOrder) {
		this.tradeOrder = tradeOrder;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
