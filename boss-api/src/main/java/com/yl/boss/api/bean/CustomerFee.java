package com.yl.boss.api.bean;

import java.util.Date;

import com.yl.boss.api.enums.FeeType;
import com.yl.boss.api.enums.ProductType;
import com.yl.boss.api.enums.Status;

/**
 * 商户费率Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月16日
 * @version V1.0.0
 */
public class CustomerFee extends BaseBean{

	private static final long serialVersionUID = -7174072730494371075L;
	private String customerNo;				//商户编号
	private ProductType productType;		//产品类型
	private FeeType feeType;				//费率类型
	private double fee;						//费率
	private double minFee;					//最低费率
	private double maxFee;					//最高费率
	private Status status;					//状态
	private Date createTime;
	
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
	public FeeType getFeeType() {
		return feeType;
	}
	public void setFeeType(FeeType feeType) {
		this.feeType = feeType;
	}
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
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
	
}
