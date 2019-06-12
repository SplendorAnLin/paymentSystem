package com.yl.dpay.core.enums;

/**
 * 付款审核原因
 *
 * @author 聚合支付有限公司
 * @since 2016年5月12日
 * @version V1.0.0
 */
public enum RemitAdutiReason {
	
	REMIT_AUDIT_MAN("付款审核状态:人工"),
	OWNER_AUDIT_BOSS("用户运营审核:启用"),
	AUDIT_AMOUNT("超过审核金额:");
	
	private final String message;

	RemitAdutiReason(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
