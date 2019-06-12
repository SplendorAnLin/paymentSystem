/**
 * 
 */
package com.yl.account.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.account.bean.TradeVoucher;
import com.yl.account.enums.ReverseStatus;

/**
 * 账务MQ消费入账补单实体
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public class AccountCreditReverse extends AutoStringIDModel {

	private static final long serialVersionUID = -3109658082587698797L;

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
	private String tradeVoucher;
	/** 操作人 */
	@NotBlank
	private String operator;
	/** 备注 */
	private String remark;
	/** 补单状态 */
	private ReverseStatus status = ReverseStatus.INIT;
	/** 补单次数 */
	private int count;
	/** 错误信息码 */
	private String exceptionMessage;

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

	public String getTradeVoucher() {
		return tradeVoucher;
	}

	public void setTradeVoucher(TradeVoucher tradeVoucher) {
		this.tradeVoucher = JsonUtils.toJsonString(tradeVoucher);
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

	public ReverseStatus getStatus() {
		return status;
	}

	public void setStatus(ReverseStatus status) {
		this.status = status;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	@Override
	public String toString() {
		return "AccountCreditReverse [systemCode=" + systemCode + ", systemFlowId=" + systemFlowId + ", bussinessCode=" + bussinessCode + ", requestTime="
				+ requestTime + ", tradeVoucher=" + tradeVoucher + ", operator=" + operator + ", remark=" + remark + ", status=" + status + ", count=" + count
				+ ", exceptionMessage=" + exceptionMessage + "]";
	}

}
