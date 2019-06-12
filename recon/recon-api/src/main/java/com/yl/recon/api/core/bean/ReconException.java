package com.yl.recon.api.core.bean;

import java.util.Date;

import com.yl.recon.api.core.enums.HandleStatus;
import com.yl.recon.api.core.enums.ReconExceptionType;
import com.yl.recon.api.core.enums.ReconType;
import com.yl.utils.excel.ExcelField;


/**
 * 对账异常信息
 *
 * @author AnLin
 * @since 2017/6/21
 */
public class ReconException extends BaseEntity {

	@ExcelField(name = "对账单编号", column = "A", columnWidth = "15")
	private Long reconOrderId;
	@ExcelField(name = "对账日期", column = "B", columnWidth = "15", formatDate = "yyyy-MM-dd" )
	private Date reconDate;
	@ExcelField(name = "对账类型", column = "C", dict = "RECON_TYPE", columnWidth = "15")
	private ReconType reconType;
	@ExcelField(name = "金额", column = "D", round = "2")
	private double amount;
	@ExcelField(name = "交易订单/账户订单", column = "E", columnWidth = "25")
	private String tradeOrderCode;
	@ExcelField(name = "接口订单/银行通道订单", column = "F", columnWidth = "25")
	private String interfaceOrderCode;
	@ExcelField(name = "对账异常类型", column = "G", columnWidth = "15",dict ="RECON_EXCEPTION_TYPE")
	private ReconExceptionType reconExceptionType;
	@ExcelField(name = "处理状态", column = "H", dict = "HANDLE_STATUS", columnWidth = "15")
	private HandleStatus handleStatus;
	@ExcelField(name = "处理备注", column = "I", columnWidth = "15")
	private String handleRemark;
	@ExcelField(name = "操作员", column = "K", columnWidth = "15")
	private String oper;

	public Long getReconOrderId() {
		return reconOrderId;
	}

	public void setReconOrderId(Long reconOrderId) {
		this.reconOrderId = reconOrderId;
	}

	public Date getReconDate() {
		return reconDate;
	}

	public void setReconDate(Date reconDate) {
		this.reconDate = reconDate;
	}

	public ReconType getReconType() {
		return reconType;
	}

	public void setReconType(ReconType reconType) {
		this.reconType = reconType;
	}

	public String getTradeOrderCode() {
		return tradeOrderCode;
	}

	public void setTradeOrderCode(String tradeOrderCode) {
		this.tradeOrderCode = tradeOrderCode;
	}

	public String getInterfaceOrderCode() {
		return interfaceOrderCode;
	}

	public void setInterfaceOrderCode(String interfaceOrderCode) {
		this.interfaceOrderCode = interfaceOrderCode;
	}

	public ReconExceptionType getReconExceptionType() {
		return reconExceptionType;
	}

	public void setReconExceptionType(ReconExceptionType reconExceptionType) {
		this.reconExceptionType = reconExceptionType;
	}

	public HandleStatus getHandleStatus() {
		return handleStatus;
	}

	public void setHandleStatus(HandleStatus handleStatus) {
		this.handleStatus = handleStatus;
	}

	public String getHandleRemark() {
		return handleRemark;
	}

	public void setHandleRemark(String handleRemark) {
		this.handleRemark = handleRemark;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}