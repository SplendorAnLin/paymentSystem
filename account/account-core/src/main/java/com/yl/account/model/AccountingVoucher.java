package com.yl.account.model;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.account.bean.TradeVoucher;

/**
 * 记账凭证
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public class AccountingVoucher extends AutoStringIDModel {

	private static final long serialVersionUID = 2775253464865183133L;

	/** 系统编码 */
	@NotNull
	private String systemCode;
	/** 系统流水号 */
	@NotNull
	private String systemFlowId;
	/** 业务类型码 */
	@NotNull
	private String bussinessCode;
	/** 请求时间 */
	private Date requestTime;
	/** 服务器地址 */
	private String serverHost;
	/** 交易凭证 */
	@NotNull
	@Valid
	private TradeVoucher tradeVoucher;
	/** 操作人 */
	@NotNull
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

	public String getServerHost() {
		return serverHost;
	}

	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}

	public String getTradeVoucher() {
		return JsonUtils.toJsonString(tradeVoucher);
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
		return "AccountingVoucher [systemCode=" + systemCode + ", systemFlowId=" + systemFlowId + ", bussinessCode=" + bussinessCode + ", requestTime="
				+ requestTime + ", serverHost=" + serverHost + ", tradeVoucher=" + tradeVoucher + ", operator=" + operator + ", remark=" + remark + "]";
	}

}
