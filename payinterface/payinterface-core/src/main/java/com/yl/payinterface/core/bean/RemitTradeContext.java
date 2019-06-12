package com.yl.payinterface.core.bean;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.Id;

import com.yl.payinterface.core.model.InterfaceInfo;

/**
 * 一次付款请求上下文
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月11日
 * @version V1.0.0
 */
@JsonTypeInfo(use = Id.MINIMAL_CLASS, property = "class")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RemitTradeContext implements Serializable {

	private static final long serialVersionUID = -8163641038776472691L;

	/** 业务请求的支付接口编号 */
	private String interfaceInfoCode;
	/** 渠道信息 */
	private InterfaceInfo interfaceInfo;
	/** 资金账户 */
	private FundAccountBean fundAccountBean;
	/** 批次编号 */
	private String batchCode;
	/** 总笔数 */
	private int totalNumber;
	/** 总金额 */
	private double totalAmount;

	public String getInterfaceInfoCode() {
		return interfaceInfoCode;
	}

	public void setInterfaceInfoCode(String interfaceInfoCode) {
		this.interfaceInfoCode = interfaceInfoCode;
	}

	public InterfaceInfo getInterfaceInfo() {
		return interfaceInfo;
	}

	public void setInterfaceInfo(InterfaceInfo interfaceInfo) {
		this.interfaceInfo = interfaceInfo;
	}

	public FundAccountBean getFundAccountBean() {
		return fundAccountBean;
	}

	public void setFundAccountBean(FundAccountBean fundAccountBean) {
		this.fundAccountBean = fundAccountBean;
	}

	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	public int getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

}
