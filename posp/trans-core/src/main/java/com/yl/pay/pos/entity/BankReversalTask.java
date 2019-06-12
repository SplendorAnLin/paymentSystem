package com.yl.pay.pos.entity;

import com.yl.pay.pos.enums.BankReversalTaskStatus;
import com.yl.pay.pos.enums.ReversalType;
import com.yl.pay.pos.enums.YesNo;

import javax.persistence.*;
import java.util.Date;

/**
 * Title: 银行冲正任务
 * Description: 
 * Copyright: Copyright (c)2012
 * Company: com.yl.pay
 * @author jun
 */

@Entity
@Table(name = "BANK_REVERSAL_TASK")
public class BankReversalTask extends BaseEntity{
	
	private Payment sourceReversalPayment;				//原payment
	private String amount;								//金额
	private String posEntryModeCode;					//服务点输入方式码
	private Date createTime;							//创建时间
	private Date completeTime;							//完成时间
	private YesNo claim;								//是否已认领
	private BankReversalTaskStatus status;				//状态
	private ReversalType reversalType;					//冲正类型

	/**
	 * @param sourceReversalPayment
	 * @param amount
	 * @param posEntryModeCode
	 * @param reversalType
	 */
	public BankReversalTask(Payment sourceReversalPayment, String amount,
			String posEntryModeCode, ReversalType reversalType) {
		super();
		this.sourceReversalPayment = sourceReversalPayment;
		this.amount = amount;
		this.posEntryModeCode = posEntryModeCode;
		this.reversalType = reversalType;
	}

	public BankReversalTask() {
		super();
	}
	
	@ManyToOne(fetch = FetchType.LAZY )
	public Payment getSourceReversalPayment() {
		return sourceReversalPayment;
	}
	public void setSourceReversalPayment(Payment sourceReversalPayment) {
		this.sourceReversalPayment = sourceReversalPayment;
	}

	@Column(name = "CREATE_TIME", columnDefinition = "DATETIME")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "COMPLETE_TIME", columnDefinition = "DATETIME")
	public Date getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "CLAIM", columnDefinition = "VARCHAR(20)")
	public YesNo getClaim() {
		return claim;
	}
	public void setClaim(YesNo claim) {
		this.claim = claim;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "STATUS", columnDefinition = "VARCHAR(30)")
	public BankReversalTaskStatus getStatus() {
		return status;
	}
	public void setStatus(BankReversalTaskStatus status) {
		this.status = status;
	}

	@Column(name = "AMOUNT", length = 30)
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}

	@Column(name = "POS_ENTRY_MODE", length = 30)
	public String getPosEntryModeCode() {
		return posEntryModeCode;
	}
	public void setPosEntryModeCode(String posEntryModeCode) {
		this.posEntryModeCode = posEntryModeCode;
	}

	@Enumerated(value = EnumType.STRING)
	@Column(name = "REVERSAL_TYPE", columnDefinition = "VARCHAR(30)")
	public ReversalType getReversalType() {
		return reversalType;
	}
	public void setReversalType(ReversalType reversalType) {
		this.reversalType = reversalType;
	}
	
	
}
