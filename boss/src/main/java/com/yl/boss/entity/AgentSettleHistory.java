package com.yl.boss.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.yl.boss.api.enums.AgentType;

/**
 * 服务商结算卡信息历史Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
@Entity
@Table(name = "AGENT_SETTLE_HISTORY")
public class AgentSettleHistory extends AutoIDEntity{
	
	private static final long serialVersionUID = -3374131465600669380L;
	private String agentNo;					//服务商编号
	private String accountName;				//账户名称
	private String accountNo;				//账户编号
	private AgentType agentType;			//商户类型 对公|对私
	private String bankCode;				//所属银行编号
	private String openBankName;			//开户行名称
	private Date createTime;
	private String oper;
	
	@Column(name = "AGENT_NO", length = 30)
	public String getAgentNo() {
		return agentNo;
	}
	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}
	
	@Column(name = "CREATE_TIME", columnDefinition = "DATE")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "ACCOUNT_NAME", length = 500)
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	@Column(name = "ACCOUNT_NO", length = 200)
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "AGENT_TYPE", columnDefinition = "VARCHAR(30)")
	public AgentType getAgentType() {
		return agentType;
	}
	public void setAgentType(AgentType agentType) {
		this.agentType = agentType;
	}
	
	@Column(name = "BANK_CODE", length = 30)
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	@Column(name = "OPEN_BANK_NAME", length = 500)
	public String getOpenBankName() {
		return openBankName;
	}
	public void setOpenBankName(String openBankName) {
		this.openBankName = openBankName;
	}
	
	@Column(name = "OPER", length = 30)
	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}
	public AgentSettleHistory(){}
	public AgentSettleHistory(AgentSettle agentSettle, String oper) {
		super();
		this.agentNo = agentSettle.getAgentNo();
		this.accountName = agentSettle.getAccountName();
		this.accountNo = agentSettle.getAccountNo();
		this.agentType = agentSettle.getAgentType();
		this.bankCode = agentSettle.getBankCode();
		this.openBankName = agentSettle.getOpenBankName();
		this.createTime = new Date();
		this.oper = oper;
	}
	
}
