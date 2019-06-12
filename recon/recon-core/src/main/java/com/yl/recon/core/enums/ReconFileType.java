package com.yl.recon.core.enums;

/**
 * 对账文件类型
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2018年01月05
 */
public enum ReconFileType {
	PAYINTERFACE("接口"), ACCOUNT("账户"), BANK("银行通道"), ONLINE("线上交易"), REAL_AUTH("实名认证"), DPAY("代付"),RECEIVE("代收");
	private final String remark;

	ReconFileType(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return remark;
	}
}
