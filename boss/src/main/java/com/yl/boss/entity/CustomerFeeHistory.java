package com.yl.boss.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.yl.agent.api.enums.Status;
import com.yl.boss.enums.FeeType;
import com.yl.boss.enums.ProductType;

/**
 * 商户费率信息历史Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
@Entity
@Table(name = "CUSTOMER_FEE_HISTORY")
public class CustomerFeeHistory extends AutoIDEntity{
	
	private static final long serialVersionUID = -3374131465600669380L;
	private String customerNo;				//商户编号
	private ProductType productType;		//产品类型
	private FeeType feeType;				//费率类型
	private double fee;						//费率
	private double minFee;					//最低费率
	private double maxFee;					//最高费率
	private Status status;					//状态
	private Date createTime;
	private String oper;
	
	@Column(name = "CUSTOMER_NO", length = 30)
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
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
	
	@Column(name = "FEE", precision = 10, scale = 4)
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
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
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "STATUS", columnDefinition = "VARCHAR(30)")
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	@Column(name = "OPER", length = 50)
	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}
	
	public CustomerFeeHistory(CustomerFee customerFee, String oper) {
		super();
		this.customerNo = customerFee.getCustomerNo();
		this.productType = customerFee.getProductType();
		this.feeType = customerFee.getFeeType();
		this.fee = customerFee.getFee();
		this.minFee = customerFee.getMinFee();
		this.maxFee = customerFee.getMaxFee();
		this.status = customerFee.getStatus();
		this.createTime = new Date();
		this.oper = oper;
	}
	
	public CustomerFeeHistory() {
		super();
	}
	
}
