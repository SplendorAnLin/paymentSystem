package com.yl.boss.api.bean;
import java.util.Date;

import com.yl.boss.api.enums.FeeType;
import com.yl.boss.api.enums.ProductType;
import com.yl.boss.api.enums.ProfitType;
import com.yl.boss.api.enums.Status;

/**
 * 服务商分润信息Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月16日
 * @version V1.0.0
 */
public class AgentFee extends BaseBean{
	
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
	
	public String getAgentNo() {
		return agentNo;
	}
	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}
	public ProductType getProductType() {
		return productType;
	}
	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
	public FeeType getFeeType() {
		return feeType;
	}
	public void setFeeType(FeeType feeType) {
		this.feeType = feeType;
	}
	public ProfitType getProfitType() {
		return profitType;
	}
	public void setProfitType(ProfitType profitType) {
		this.profitType = profitType;
	}
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}
	public double getProfitRatio() {
		return profitRatio;
	}
	public void setProfitRatio(double profitRatio) {
		this.profitRatio = profitRatio;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public double getMinFee() {
		return minFee;
	}
	public void setMinFee(double minFee) {
		this.minFee = minFee;
	}
	public double getMaxFee() {
		return maxFee;
	}
	public void setMaxFee(double maxFee) {
		this.maxFee = maxFee;
	}
	
}
