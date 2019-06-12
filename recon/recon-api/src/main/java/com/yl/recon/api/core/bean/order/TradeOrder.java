package com.yl.recon.api.core.bean.order;


import com.yl.recon.api.core.bean.BaseEntity;
import com.yl.recon.api.core.enums.TransType;
import com.yl.utils.excel.ExcelField;

import java.util.Date;

/**
 * 交易订单
 *
 * @author AnLin
 * @since 2017/6/21
 */
 public class TradeOrder extends BaseEntity {
	@ExcelField(name = "交易订单号", column = "A", columnWidth = "25")
	private String tradeOrderCode;
	@ExcelField(name = "交易类型", column = "B")
	private TransType transType;
	@ExcelField(name = "支付类型", column = "C", dict = "ONLINE_PAYTYPE")
	private String payType;
	@ExcelField(name = "接口类型", column = "D", columnWidth = "15")
	private String interfaceType;
	@ExcelField(name = "接口编号", column = "E", columnWidth = "15")
	private String interfaceCode;
	@ExcelField(name = "接口订单号", column = "F", columnWidth = "25")
	private String interfaceOrderCode;
	@ExcelField(name = "订单金额", column = "G", round = "2")
	private Double amount;
	@ExcelField(name = "订单手续费", column = "H", round = "2")
	private Double fee;
	@ExcelField(name = "交易完成时间", column = "I", formatDate = "yyyy-MM-dd HH:mm:ss", columnWidth = "20")
	private Date transTime;
	@ExcelField(name = "对账日期", column = "K", formatDate = "yyyy-MM-dd", columnWidth = "15")
	private Date reconDate;
	@ExcelField(name = "备注", column = "L", columnWidth = "15")
	private String remark;

	public TradeOrder() {
	}

	public String getTradeOrderCode() {
		return tradeOrderCode;
	}

	public void setTradeOrderCode(String tradeOrderCode) {
		this.tradeOrderCode = tradeOrderCode;
	}

	public TransType getTransType() {
		return transType;
	}

	public void setTransType(TransType transType) {
		this.transType = transType;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}

	public String getInterfaceOrderCode() {
		return interfaceOrderCode;
	}

	public void setInterfaceOrderCode(String interfaceOrderCode) {
		this.interfaceOrderCode = interfaceOrderCode;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public Date getTransTime() {
		return transTime;
	}

	public void setTransTime(Date transTime) {
		this.transTime = transTime;
	}

	public Date getReconDate() {
		return reconDate;
	}

	public void setReconDate(Date reconDate) {
		this.reconDate = reconDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getInterfaceCode() {
		return interfaceCode;
	}

	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}
}
