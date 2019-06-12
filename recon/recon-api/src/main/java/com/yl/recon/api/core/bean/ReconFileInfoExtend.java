package com.yl.recon.api.core.bean;


/**
 * 聚合支付有限公司
 *
 * @author 邱健
 * @version V1.0.0
 * @create 2018年01月17
 * @desc 通道方对账文件信息扩展类
 **/
public class ReconFileInfoExtend extends BaseEntity {
	/**
	 * 表头
	 */
	private String header;
	/**
	 * 表尾：合计行标题
	 */
	private String footer;
	/**
	 * 合计标识
	 */
	private String totalFlag;
	/**
	 * 分隔符
	 */
	private String delimiter;
	/**
	 * 接口编码
	 */
	private String interfaceInfoCode;
	/**
	 * 通道名称
	 */
	private String bankName;
	/**
	 * 银行订单号列
	 */
	private String bankOrderCode;
	/**
	 * 接口订单号列
	 */
	private String interfaceOrderCode;
	/**
	 * 订单金额列
	 */
	private String amount;
	/**
	 * 手续费列
	 */
	private String fee;
	/**
	 * 交易时间
	 */
	private String transTime;

	public ReconFileInfoExtend() {
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public String getTotalFlag() {
		return totalFlag;
	}

	public void setTotalFlag(String totalFlag) {
		this.totalFlag = totalFlag;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public String getInterfaceInfoCode() {
		return interfaceInfoCode;
	}

	public void setInterfaceInfoCode(String interfaceInfoCode) {
		this.interfaceInfoCode = interfaceInfoCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
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

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getTransTime() {
		return transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}
}
