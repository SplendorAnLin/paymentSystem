package com.yl.recon.core.context;
/**
 * @author 聚合支付有限公司
 * @since 2018年01月16
 * @version V1.0.0
 */
public enum DataBaseSourceType {
	RECON("对账数据源"),
	ACCOUNT("账户数据源"),
	PAYINTERFACE("接口数据源"),
	REAL_AUTH("实名认证数据源"),
	ONLINE_TRADE("线上交易数据源"),
	DPAY("代付数据源");
	private final String message;

	DataBaseSourceType(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}


