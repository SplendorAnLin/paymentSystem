package com.yl.boss.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.yl.agent.api.enums.Status;
import com.yl.boss.api.enums.FeeType;
import com.yl.boss.api.enums.ProductType;
import com.yl.boss.api.enums.ProfitType;

/**
 * 服务商分润信息历史Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
@Entity
@Table(name = "AGENT_FEE_HISTORY")
public class AgentFeeHistory extends AutoIDEntity{
	
	private static final long serialVersionUID = -3374131465600669380L;
	private String agentNo;					//服务商编号
	private ProductType productType;		//产品类型
	private FeeType feeType;				//费率类型
	private ProfitType profitType;			//分润类型  固定、比例 
	private double fee;						//费率
	private double minFee;					//最低费率
	private double maxFee;					//最高费率
	private double profitRatio;				//分润比例
	private Status status;					//状态  禁用：停止分润
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
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "PRODUCT_TYPE", columnDefinition = "VARCHAR(30)")
	public ProductType getProductType() {
		return productType;
	}
	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "FEE_TYPE", columnDefinition = "VARCHAR(30)")
	public FeeType getFeeType() {
		return feeType;
	}
	public void setFeeType(FeeType feeType) {
		this.feeType = feeType;
	}
	
	@Column(name = "FEE", precision = 2, scale = 4)
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}
	
	@Column(name = "PROFIT_RATIO", precision = 2, scale = 4)
	public double getProfitRatio() {
		return profitRatio;
	}
	public void setProfitRatio(double profitRatio) {
		this.profitRatio = profitRatio;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "STATUS", columnDefinition = "VARCHAR(30)")
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	@Column(name = "OPER", length = 30)
	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "PROFIT_TYPE", columnDefinition = "VARCHAR(30)")
	public ProfitType getProfitType() {
		return profitType;
	}
	public void setProfitType(ProfitType profitType) {
		this.profitType = profitType;
	}
	
	@Column(name = "MIN_FEE", precision = 10, scale = 4)
	public double getMinFee() {
		return minFee;
	}
	public void setMinFee(double minFee) {
		this.minFee = minFee;
	}
	
	@Column(name = "MAX_FEE", precision = 10, scale = 4)
	public double getMaxFee() {
		return maxFee;
	}
	public void setMaxFee(double maxFee) {
		this.maxFee = maxFee;
	}
	
	public AgentFeeHistory(AgentFee agentFee, String oper) {
		super();
		this.agentNo = agentFee.getAgentNo();
		this.productType = agentFee.getProductType();
		this.feeType = agentFee.getFeeType();
		this.profitType = agentFee.getProfitType();
		this.fee = agentFee.getFee();
		this.maxFee = agentFee.getMaxFee();
		this.minFee = agentFee.getMinFee();
		this.profitRatio = agentFee.getProfitRatio();
		this.status = agentFee.getStatus();
		this.createTime = new Date();
		this.oper = oper;
	}
	
}
