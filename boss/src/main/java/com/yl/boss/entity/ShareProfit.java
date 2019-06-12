package com.yl.boss.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.yl.boss.api.enums.ProductType;
import com.yl.boss.api.enums.ProfitType;




/**
 * 分润信息Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */


@Entity
@Table(name = "SHARE_PROFIT")
public class ShareProfit extends AutoIDEntity {

	private static final long serialVersionUID = -2790426432706044178L;
	private String orderCode; // 订单编号
	private String customerNo; // 商户编号
	private ProductType productType; // 产品类型
	private Double amount; // 订单金额
	private Double fee; // 订单手续费
	private String interfaceCode; // 接口编号
	private Double channelCost; // 通道成本
	private Date orderTime; // 订单完成时间
	private String source; // 订单来源

	private String agentNo; // 代理商编号
	private Double customerFee; // 商户费率
	private Double agentFee; // 代理商费率
	private ProfitType profitType; // 分润类型 固定、比例
	private Double profitRatio; // 分润比例
	private Double agentProfit; // 代理商利润
	private Double platformProfit; // 平台利润

	private Integer agentLevel; // 代理商级别
	private String directAgent; // 代理商的直接代理商
	private Double directAgentFee; // 直接代理商费率
	private ProfitType directProfitType; // 直接代理商分润类型 固定、比例
	private Double directProfitRatio; // 直接代理商分润比例
	private Double directAgentProfit; // 直接代理商利润
	private String indirectAgent; // 代理商的间接代理商
	private Double indirectAgentFee; // 间接代理商费率
	private ProfitType indirectProfitType; // 间接代理商分润类型 固定、比例
	private Double indirectProfitRatio; // 间接代理商分润比例
	private Double indirectAgentProfit; // 间接代理商利润

	private Date createTime;

	@Column(name = "ORDER_CODE", length = 50)
	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	@Column(name = "CUSTOMER_NO", length = 30)
	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	@Enumerated(value = EnumType.STRING)
	@Column(name = "PRODUCT_TYPE", columnDefinition = "VARCHAR(30)")
	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	@Column(name = "AMOUNT", precision = 10, scale = 2)
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "FEE", precision = 10, scale = 2)
	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	@Column(name = "INTERFACE_CODE", length = 30)
	public String getInterfaceCode() {
		return interfaceCode;
	}

	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}

	@Column(name = "CHANNEL_COST", precision = 10, scale = 5)
	public Double getChannelCost() {
		return channelCost;
	}

	public void setChannelCost(Double channelCost) {
		this.channelCost = channelCost;
	}

	@Column(name = "ORDER_TIME", columnDefinition = "DATE")
	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	@Column(name = "SOURCE", length = 30)
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "AGENT_NO", length = 30)
	public String getAgentNo() {
		return agentNo;
	}

	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}

	@Column(name = "CUSTOMER_FEE", precision = 2, scale = 5)
	public Double getCustomerFee() {
		return customerFee;
	}

	public void setCustomerFee(Double customerFee) {
		this.customerFee = customerFee;
	}

	@Column(name = "AGENT_FEE", precision = 2, scale = 5)
	public Double getAgentFee() {
		return agentFee;
	}

	public void setAgentFee(Double agentFee) {
		this.agentFee = agentFee;
	}

	@Enumerated(value = EnumType.STRING)
	@Column(name = "PROFIT_TYPE", columnDefinition = "VARCHAR(30)")
	public ProfitType getProfitType() {
		return profitType;
	}

	public void setProfitType(ProfitType profitType) {
		this.profitType = profitType;
	}

	@Column(name = "PROFIT_RATIO", precision = 2, scale = 5)
	public Double getProfitRatio() {
		return profitRatio;
	}

	public void setProfitRatio(Double profitRatio) {
		this.profitRatio = profitRatio;
	}

	@Column(name = "CREATE_TIME", columnDefinition = "DATE")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "AGENT_PROFIT", precision = 10, scale = 2)
	public Double getAgentProfit() {
		return agentProfit;
	}

	public void setAgentProfit(Double agentProfit) {
		this.agentProfit = agentProfit;
	}

	@Column(name = "PLATFROM_PROFIT", precision = 10, scale = 2)
	public Double getPlatformProfit() {
		return platformProfit;
	}

	public void setPlatformProfit(Double platformProfit) {
		this.platformProfit = platformProfit;
	}

	@Column(name = "AGENT_LEVEL", length = 10)
	public Integer getAgentLevel() {
		return agentLevel;
	}

	public void setAgentLevel(Integer agentLevel) {
		this.agentLevel = agentLevel;
	}

	@Column(name = "DIRECT_AGENT", length = 30)
	public String getDirectAgent() {
		return directAgent;
	}

	public void setDirectAgent(String directAgent) {
		this.directAgent = directAgent;
	}

	@Column(name = "DIRECT_AGENT_FEE", precision = 10, scale = 5)
	public Double getDirectAgentFee() {
		return directAgentFee;
	}

	public void setDirectAgentFee(Double directAgentFee) {
		this.directAgentFee = directAgentFee;
	}

	@Enumerated(value = EnumType.STRING)
	@Column(name = "DIRECT_PROFIT_TYPE", columnDefinition = "VARCHAR(30)")
	public ProfitType getDirectProfitType() {
		return directProfitType;
	}

	public void setDirectProfitType(ProfitType directProfitType) {
		this.directProfitType = directProfitType;
	}

	@Column(name = "DIRECT_PROFIT_RATIO", precision = 10, scale = 5)
	public Double getDirectProfitRatio() {
		return directProfitRatio;
	}

	public void setDirectProfitRatio(Double directProfitRatio) {
		this.directProfitRatio = directProfitRatio;
	}

	@Column(name = "INDIRECT_AGENT", length = 30)
	public String getIndirectAgent() {
		return indirectAgent;
	}

	public void setIndirectAgent(String indirectAgent) {
		this.indirectAgent = indirectAgent;
	}

	@Column(name = "INDIRECT_AGENT_FEE", precision = 10, scale = 5)
	public Double getIndirectAgentFee() {
		return indirectAgentFee;
	}

	public void setIndirectAgentFee(Double indirectAgentFee) {
		this.indirectAgentFee = indirectAgentFee;
	}

	@Enumerated(value = EnumType.STRING)
	@Column(name = "INDIRECT_PROFIT_TYPE", columnDefinition = "VARCHAR(30)")
	public ProfitType getIndirectProfitType() {
		return indirectProfitType;
	}

	public void setIndirectProfitType(ProfitType indirectProfitType) {
		this.indirectProfitType = indirectProfitType;
	}

	@Column(name = "INDIRECT_PROFIT_RATIO", precision = 10, scale = 5)
	public Double getIndirectProfitRatio() {
		return indirectProfitRatio;
	}

	public void setIndirectProfitRatio(Double indirectProfitRatio) {
		this.indirectProfitRatio = indirectProfitRatio;
	}
	
	@Column(name = "DIRECT_AGENT_PROFIT", precision = 10, scale = 2)
	public Double getDirectAgentProfit() {
		return directAgentProfit;
	}

	public void setDirectAgentProfit(Double directAgentProfit) {
		this.directAgentProfit = directAgentProfit;
	}

	@Column(name = "INDIRECT_AGENT_PROFIT", precision = 10, scale = 2)
	public Double getIndirectAgentProfit() {
		return indirectAgentProfit;
	}

	public void setIndirectAgentProfit(Double indirectAgentProfit) {
		this.indirectAgentProfit = indirectAgentProfit;
	}

	@Override
	public String toString() {
		return "ShareProfit [orderCode=" + orderCode + ", customerNo=" + customerNo + ", productType=" + productType
				+ ", amount=" + amount + ", fee=" + fee + ", interfaceCode=" + interfaceCode + ", channelCost="
				+ channelCost + ", orderTime=" + orderTime + ", source=" + source + ", agentNo=" + agentNo
				+ ", customerFee=" + customerFee + ", agentFee=" + agentFee + ", profitType=" + profitType
				+ ", profitRatio=" + profitRatio + ", agentProfit=" + agentProfit + ", platformProfit=" + platformProfit
				+ ", agentLevel=" + agentLevel + ", directAgent=" + directAgent + ", directAgentFee=" + directAgentFee
				+ ", directProfitType=" + directProfitType + ", directProfitRatio=" + directProfitRatio
				+ ", directAgentProfit=" + directAgentProfit + ", indirectAgent=" + indirectAgent
				+ ", indirectAgentFee=" + indirectAgentFee + ", indirectProfitType=" + indirectProfitType
				+ ", indirectProfitRatio=" + indirectProfitRatio + ", indirectAgentProfit=" + indirectAgentProfit
				+ ", createTime=" + createTime + "]";
	}

}
