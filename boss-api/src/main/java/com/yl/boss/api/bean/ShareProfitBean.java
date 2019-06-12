package com.yl.boss.api.bean;

import java.util.Date;

import com.yl.boss.api.enums.ProductType;
import com.yl.boss.api.enums.ProfitType;

/**
 * 分润Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月16日
 * @version V1.0.0
 */
public class ShareProfitBean extends BaseBean{

	private static final long serialVersionUID = 4341372694207688784L;
	private String orderCode;				//订单编号
	private String customerNo;				//商户编号
	private ProductType productType;		//产品类型	
    private double amount;					//订单金额
    private double fee;						//订单手续费
    private String interfaceCode;			//接口编号
    private double channelCost;				//通道成本
    private Date orderTime;					//订单完成时间
    private String source;					//订单来源
	
    private String agentNo;					//服务商编号
    private double customerFee;				//商户费率
    private double agentFee;				//服务商费率
    private ProfitType profitType;			//分润类型  固定、比例 
    private double profitRatio;				//分润比例
    private double agentProfit;				//服务商利润
    private double platformProfit;			//平台利润
    
    private Date createTime;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public String getInterfaceCode() {
		return interfaceCode;
	}

	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}

	public double getChannelCost() {
		return channelCost;
	}

	public void setChannelCost(double channelCost) {
		this.channelCost = channelCost;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getAgentNo() {
		return agentNo;
	}

	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}

	public double getCustomerFee() {
		return customerFee;
	}

	public void setCustomerFee(double customerFee) {
		this.customerFee = customerFee;
	}

	public double getAgentFee() {
		return agentFee;
	}

	public void setAgentFee(double agentFee) {
		this.agentFee = agentFee;
	}

	public ProfitType getProfitType() {
		return profitType;
	}

	public void setProfitType(ProfitType profitType) {
		this.profitType = profitType;
	}

	public double getProfitRatio() {
		return profitRatio;
	}

	public void setProfitRatio(double profitRatio) {
		this.profitRatio = profitRatio;
	}

	public double getAgentProfit() {
		return agentProfit;
	}

	public void setAgentProfit(double agentProfit) {
		this.agentProfit = agentProfit;
	}

	public double getPlatformProfit() {
		return platformProfit;
	}

	public void setPlatformProfit(double platformProfit) {
		this.platformProfit = platformProfit;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
    
}
