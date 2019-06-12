package com.yl.recon.api.core.bean.order;

import com.yl.recon.api.core.bean.BaseEntity;
import com.yl.utils.excel.ExcelField;

import java.util.Date;

/**
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2018年01月05
 */
public class AccountOrder extends BaseEntity {
	@ExcelField(name = "账户编号", column = "A", columnWidth = "15")
	private String accountNo;
	@ExcelField(name = "交易订单号", column = "B", columnWidth = "25")
	private String tradeOrderCode;
	@ExcelField(name = "订单金额", column = "C", round = "2")
	private Double amount;
	@ExcelField(name = "资金变动方向", column = "D", dict = "CAPITAL_IDENTIFICATION")
	private String fundSymbol;
	@ExcelField(name = "交易时间", column = "E", formatDate = "yyyy-MM-dd HH:mm:ss", columnWidth = "20")
	private Date transTime;
	@ExcelField(name = "系统编码", column = "F")
	private String systemCode;
	@ExcelField(name = "业务编码", column = "G", dict = "BUSINESS_TYPE", columnWidth = "25")
	private String bussinessCode;
	@ExcelField(name = "对账日期", column = "H", formatDate = "yyyy-MM-dd", columnWidth = "15")
	private Date reconDate;
	@ExcelField(name = "是否是手续费", column = "I")
	private String isFee;

	private String transType;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getTradeOrderCode() {
		return tradeOrderCode;
	}

	public void setTradeOrderCode(String tradeOrderCode) {
		this.tradeOrderCode = tradeOrderCode;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getFundSymbol() {
		return fundSymbol;
	}

	public void setFundSymbol(String fundSymbol) {
		this.fundSymbol = fundSymbol;
	}

	public Date getTransTime() {
		return transTime;
	}

	public void setTransTime(Date transTime) {
		this.transTime = transTime;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getBussinessCode() {
		return bussinessCode;
	}

	public void setBussinessCode(String bussinessCode) {
		this.bussinessCode = bussinessCode;
	}

	public Date getReconDate() {
		return reconDate;
	}

	public void setReconDate(Date reconDate) {
		this.reconDate = reconDate;
	}

	public String getIsFee() {
		return isFee;
	}

	public void setIsFee(String isFee) {
		this.isFee = isFee;
	}
}