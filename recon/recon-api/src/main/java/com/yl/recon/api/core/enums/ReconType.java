package com.yl.recon.api.core.enums;

/**
 * 对账类型
 *
 * @author AnLin
 * @since 2017/6/21
 */
public enum ReconType {

	/**
	 * 接口和银行
	 */
	INTERFACE_BANK("接口和银行", "A接口->B通道"),
	/**
	 * 接口手续费和通道手续费
	 */
	INTERFACE_BANK_FEE("接口手续费和通道手续费", "A接口手续费->B通道手续费"),
	/**
	 * 接口和交易
	 */
	INTERFACE_TRADE("接口和交易", "A接口->B交易"),
	/**
	 * 接口和代付
	 */
	INTERFACE_REMIT("接口和代付", "A接口->B代付"),
	/**
	 * 接口和实名认证
	 */
	INTERFACE_REAL_AUTH("接口和实名认证", "A接口->B实名认证"),
	/**
	 * 账户和交易
	 */
	ACCOUNT_TRADE("账户和交易", "A账户->B交易"),
	/**
	 * 账户手续费和交易手续费
	 */
	ACCOUNT_TRADE_FEE("账户手续费和交易手续费", "A账户手续费->B交易手续费"),
	/**
	 * 账户和代付
	 */
	ACCOUNT_REMIT("账户和代付", "A账户->B代付"),
	/**
	 * 账户手续费和代付手续费
	 */
	ACCOUNT_REMIT_FEE("账户手续费和代付手续费", "A账户手续费->B代付手续费"),
	/**
	 * 账户手续费和实名认证手续费
	 */
	ACCOUNT_REAL_AUTH_FEE("账户手续费和实名认证手续费", "A账户手续费->B实名认证手续费");


	private final String msg;
	private final String remark;

	ReconType(String msg, String remark) {
		this.msg = msg;
		this.remark = remark;
	}

	public String getRemark() {
		return remark;
	}

	public String getMsg() {
		return msg;
	}
}
