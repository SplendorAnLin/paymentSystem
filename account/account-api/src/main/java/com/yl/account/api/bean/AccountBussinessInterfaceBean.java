package com.yl.account.api.bean;

import java.io.Serializable;
import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotBlank;

import com.yl.account.api.bean.request.TradeVoucher;

/**
 * 账务业务处理请求
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月15日
 * @version V1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountBussinessInterfaceBean implements Serializable {

	private static final long serialVersionUID = 368733302821413194L;

	/** 系统编码 */
	@NotBlank
	private String systemCode;
	/** 系统流水号 */
	@NotBlank
	private String systemFlowId;
	/** 业务类型码 */
	@NotBlank
	private String bussinessCode;
	/** 请求时间 */
	@NotNull
	private Date requestTime;
	/** 具体业务处理请求 */
	@Valid
	private TradeVoucher tradeVoucher;
	/** 操作人 */
	@NotBlank
	private String operator;
	/** 备注 */
	private String remark;

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getSystemFlowId() {
		return systemFlowId;
	}

	public void setSystemFlowId(String systemFlowId) {
		this.systemFlowId = systemFlowId;
	}

	public String getBussinessCode() {
		return bussinessCode;
	}

	public void setBussinessCode(String bussinessCode) {
		this.bussinessCode = bussinessCode;
	}

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	public TradeVoucher getTradeVoucher() {
		return tradeVoucher;
	}

	public void setTradeVoucher(TradeVoucher tradeVoucher) {
		this.tradeVoucher = tradeVoucher;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "AccountBussinessInterfaceBean [systemCode=" + systemCode + ", systemFlowId=" + systemFlowId + ", bussinessCode=" + bussinessCode + ", requestTime=" + requestTime + ", tradeVoucher=" + tradeVoucher + ", operator=" + operator + ", remark=" + remark + "]";
	}

}
