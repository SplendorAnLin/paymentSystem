package com.yl.dpay.hessian.beans;

import java.io.Serializable;
import java.util.List;

/**
 * 接口响应Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月15日
 * @version V1.0.0
 */
public class InterfaceResponseBean implements Serializable {

	private static final long serialVersionUID = 3584358438652538751L;
	
	/**
	 * 批次状态
	 */
	private boolean batchStatus;
	/**
	 * 失败信息
	 */
	private String failMsg;
	/**
	 * 总金额
	 */
	private Double totalAmount = 0D;
	/**
	 * 成功总金额
	 */
	private Double totalSuccessAmount = 0D;
	/**
	 * 失败总金额
	 */
	private Double totalFailAmount = 0D;
	/**
	 * 总笔数
	 */
	private Integer totalCount = 0;
	/**
	 * 成功总笔数
	 */
	private Integer totalSuccessCount = 0;
	/**
	 * 失败总笔数
	 */
	private Integer totalFailCount = 0;
	/**
	 * 总付款人手续费
	 */
	private Double totalPayerFee = 0D;
	/**
	 * 总收款人手续费
	 */
	private Double totalRevFee = 0D;
	/**
	 * 批次编号
	 */
	private String batchNo;
	/**
	 * 持有者ID
	 */
	private String ownerId;
	/**
	 * 持有者角色
	 */
	private String ownerRole;
	/**
	 * 流水号
	 */
	private String flowNo;
	/**
	 * 代付请求
	 */
	private List<ResponseBean> responseList;

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Double getTotalPayerFee() {
		return totalPayerFee;
	}

	public void setTotalPayerFee(Double totalPayerFee) {
		this.totalPayerFee = totalPayerFee;
	}

	public Double getTotalRevFee() {
		return totalRevFee;
	}

	public void setTotalRevFee(Double totalRevFee) {
		this.totalRevFee = totalRevFee;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerRole() {
		return ownerRole;
	}

	public void setOwnerRole(String ownerRole) {
		this.ownerRole = ownerRole;
	}

	public String getFlowNo() {
		return flowNo;
	}

	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}

	public List<ResponseBean> getResponseList() {
		return responseList;
	}

	public void setResponseList(List<ResponseBean> responseList) {
		this.responseList = responseList;
	}

	public Double getTotalSuccessAmount() {
		return totalSuccessAmount;
	}

	public void setTotalSuccessAmount(Double totalSuccessAmount) {
		this.totalSuccessAmount = totalSuccessAmount;
	}

	public Double getTotalFailAmount() {
		return totalFailAmount;
	}

	public void setTotalFailAmount(Double totalFailAmount) {
		this.totalFailAmount = totalFailAmount;
	}

	public Integer getTotalSuccessCount() {
		return totalSuccessCount;
	}

	public void setTotalSuccessCount(Integer totalSuccessCount) {
		this.totalSuccessCount = totalSuccessCount;
	}

	public Integer getTotalFailCount() {
		return totalFailCount;
	}

	public void setTotalFailCount(Integer totalFailCount) {
		this.totalFailCount = totalFailCount;
	}

	public String getFailMsg() {
		return failMsg;
	}

	public void setFailMsg(String failMsg) {
		this.failMsg = failMsg;
	}

	public boolean isBatchStatus() {
		return batchStatus;
	}

	public void setBatchStatus(boolean batchStatus) {
		this.batchStatus = batchStatus;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InterfaceResponseBean [batchStatus=");
		builder.append(batchStatus);
		builder.append(", failMsg=");
		builder.append(failMsg);
		builder.append(", totalAmount=");
		builder.append(totalAmount);
		builder.append(", totalSuccessAmount=");
		builder.append(totalSuccessAmount);
		builder.append(", totalFailAmount=");
		builder.append(totalFailAmount);
		builder.append(", totalCount=");
		builder.append(totalCount);
		builder.append(", totalSuccessCount=");
		builder.append(totalSuccessCount);
		builder.append(", totalFailCount=");
		builder.append(totalFailCount);
		builder.append(", totalPayerFee=");
		builder.append(totalPayerFee);
		builder.append(", totalRevFee=");
		builder.append(totalRevFee);
		builder.append(", batchNo=");
		builder.append(batchNo);
		builder.append(", ownerId=");
		builder.append(ownerId);
		builder.append(", ownerRole=");
		builder.append(ownerRole);
		builder.append(", flowNo=");
		builder.append(flowNo);
		builder.append(", responseList=");
		builder.append(responseList);
		builder.append("]");
		return builder.toString();
	}
}
