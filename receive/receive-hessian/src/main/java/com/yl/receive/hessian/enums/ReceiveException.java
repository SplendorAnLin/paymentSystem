package com.yl.receive.hessian.enums;

/**
 * 代收异常信息
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月13日
 * @version V1.0.0
 */
public enum ReceiveException {

	PARAMSERROR("1000", "参数异常"),

	UNKNOW("9999", "系统异常"),

	CHANNEL("1001", "无可用渠道");

	private final String code;
	private final String message;

	ReceiveException(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public static String getStatus(String code) {
		for (ReceiveException t : ReceiveException.values()) {
			if (code.equals(t.getCode())) {
				return t.getMessage();
			}
		}
		return null;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
