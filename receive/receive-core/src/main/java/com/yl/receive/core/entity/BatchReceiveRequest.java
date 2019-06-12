package com.yl.receive.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 批量请求信息
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月25日
 * @version V1.0.0
 */
public class BatchReceiveRequest implements Serializable {

	private static final long serialVersionUID = 8569438516038487711L;

	/** 所有者编号 */
	@NotNull
	private String partnerId;

	/** 接口版本号 */
	@NotNull
	private String versionCode;

	/** 客户端时间 */
	private String clientTime;

	/** 批次号 */
	@NotNull
	@Size(min = 1, max = 30)
	private String batchNo;

	/** 总行数 */
	@NotNull
	private int totalRow;

	/** 总金额 */
	@NotNull
	private BigDecimal totalAmount;

	/** 商户自定义返回参数 */
	@Size(min = 0, max = 30)
	private String returnParam;

	/** 代收请求集合 */
	@Valid
	private List<InterfaceReceiveRequest> receiveRequestBeans;

	/** 密文 */
	private String cipherText;

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getClientTime() {
		return clientTime;
	}

	public void setClientTime(String clientTime) {
		this.clientTime = clientTime;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public int getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getReturnParam() {
		return returnParam;
	}

	public void setReturnParam(String returnParam) {
		this.returnParam = returnParam;
	}

	public List<InterfaceReceiveRequest> getReceiveRequestBeans() {
		return receiveRequestBeans;
	}

	public void setReceiveRequestBeans(List<InterfaceReceiveRequest> receiveRequestBeans) {
		this.receiveRequestBeans = receiveRequestBeans;
	}

	public String getCipherText() {
		return cipherText;
	}

	public void setCipherText(String cipherText) {
		this.cipherText = cipherText;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BatchRequestInfo [ownerId=");
		builder.append(partnerId);
		builder.append(", versionCode=");
		builder.append(versionCode);
		builder.append(", clientTime=");
		builder.append(clientTime);
		builder.append(", batchNo=");
		builder.append(batchNo);
		builder.append(", totalRow=");
		builder.append(totalRow);
		builder.append(", totalAmount=");
		builder.append(totalAmount);
		builder.append(", returnParam=");
		builder.append(returnParam);
		builder.append(", receiveRequestBeans=");
		builder.append(receiveRequestBeans);
		builder.append(", cipherText=");
		builder.append(cipherText);
		builder.append("]");
		return builder.toString();
	}

}
