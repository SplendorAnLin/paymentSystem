package com.yl.boss.api.bean;

import java.util.Date;

import com.yl.boss.api.enums.AgentType;

/**
 * 服务商结算卡信息Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月16日
 * @version V1.0.0
 */
public class AgentSettle extends BaseBean{
	
	private static final long serialVersionUID = 3769700217691475240L;
	private String agentNo;					//服务商编号
	private String accountName;				//账户名称
	private String accountNo;				//账户编号
	private AgentType agentType;			//服务商类型(企业|个体户)
	private String bankCode;				//所属银行编号
	private String openBankName;			//开户行名称
	private Date createTime;
	
	public String getAgentNo() {
		return agentNo;
	}
	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
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
	public AgentType getAgentType() {
		return agentType;
	}
	public void setAgentType(AgentType agentType) {
		this.agentType = agentType;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getOpenBankName() {
		return openBankName;
	}
	public void setOpenBankName(String openBankName) {
		this.openBankName = openBankName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
