package com.yl.recon.api.core.enums;

/**
 * 对账异常类型
 *
 * @author AnLin
 * @since 2017/6/21
 */
public enum ReconExceptionType {

	/**
	 * 银行通道单边
	 */
	BANK("通道单边"),
	/**
	 * 接口单边
	 */
	PAYINTERFACE("接口单边"),
	/**
	 * 交易订单单边
	 */
	ONLINE("交易订单单边"),
	/**
	 * 代付订单单边
	 */
	DPAY("代付订单单边"),
	/**
	 * 实名认证单边
	 */
	REAL_AUTH("实名认证单边"),
	/**
	 * 账户单边
	 */
	ACCOUNT("账户单边"),
	/**
	 * 金额错误
	 */
	AMOUNT_ERR("金额错误");


	private final String remark;

	ReconExceptionType(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return remark;
	}
}