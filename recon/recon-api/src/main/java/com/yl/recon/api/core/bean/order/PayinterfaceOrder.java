package com.yl.recon.api.core.bean.order;


import com.yl.recon.api.core.bean.BaseEntity;
import com.yl.utils.excel.ExcelField;

import java.util.Date;

/**
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2018年01月05
 */
public class PayinterfaceOrder extends BaseEntity {

	@ExcelField(name = "接口编码", column = "A", columnWidth = "20")
	private String interfaceCode;
	@ExcelField(name = "接口类型", column = "B", columnWidth = "20", dict = "INTERFACE_TYPE_NAME")
	private String interfaceType;
	@ExcelField(name = "接口订单号", column = "C", columnWidth = "25")
	private String interfaceOrderCode;
	@ExcelField(name = "通道订单号", column = "D", columnWidth = "25")
	private String bankChannelCode;
	@ExcelField(name = "交易订单号", column = "E", columnWidth = "25")
	private String tradeOrderCode;
	@ExcelField(name = "订单金额", column = "F", round = "2")
	private Double amount;
	@ExcelField(name = "手续费", column = "G", round = "2")
	private Double fee;
	@ExcelField(name = "交易完成时间", column = "H", columnWidth = "20", formatDate = "yyyy-MM-dd HH:mm:ss")
	private Date transTime;
	@ExcelField(name = "对账日期", column = "I", columnWidth = "20", formatDate = "yyyy-MM-dd")
	private Date reconDate;
	@ExcelField(name = "备注", column = "K", columnWidth = "20")
	private String remark;


	public String getInterfaceCode() {
		return interfaceCode;
	}

	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
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

	public String getBankChannelCode() {
		return bankChannelCode;
	}

	public void setBankChannelCode(String bankChannelCode) {
		this.bankChannelCode = bankChannelCode;
	}

	public String getTradeOrderCode() {
		return tradeOrderCode;
	}

	public void setTradeOrderCode(String tradeOrderCode) {
		this.tradeOrderCode = tradeOrderCode;
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
}