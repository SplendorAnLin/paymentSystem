package com.yl.recon.api.core.bean.order;


import com.yl.utils.excel.ExcelField;

import java.io.Serializable;
import java.util.Date;

/**
 * 银行通道订单基类
 *
 * @author AnLin
 * @since 2017/6/21
 */
public class BaseBankChannelOrder implements Serializable {
	private String code;
	private long version;
	@ExcelField(name = "创建时间", column = "H", formatDate = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	@ExcelField(name = "接口编码", column = "A")
	private String interfaceInfoCode;
	@ExcelField(name = "银行订单号", column = "B", columnWidth = "25")
	private String bankOrderCode;
	@ExcelField(name = "接口订单号", column = "C", columnWidth = "25")
	private String interfaceOrderCode;
	@ExcelField(name = "订单金额", column = "D", round = "2")
	private Double amount;
	@ExcelField(name = "手续费", column = "E", round = "2")
	private Double fee;
	@ExcelField(name = "交易时间", column = "F", columnWidth = "25")
	private Date transTime;
	@ExcelField(name = "对账日期", column = "G")
	private Date reconDate;


	public String getInterfaceInfoCode() {
		return interfaceInfoCode;
	}

	public void setInterfaceInfoCode(String interfaceInfoCode) {
		this.interfaceInfoCode = interfaceInfoCode;
	}

	public String getBankOrderCode() {
		return bankOrderCode;
	}

	public void setBankOrderCode(String bankOrderCode) {
		this.bankOrderCode = bankOrderCode;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BaseBankChannelOrder() {
	}
}
