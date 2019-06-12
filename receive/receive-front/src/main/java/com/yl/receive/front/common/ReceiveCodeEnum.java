package com.yl.receive.front.common;

/**
 * 代收编码枚举
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月12日
 * @version V1.0.0
 */
public enum ReceiveCodeEnum {
	
	RECEIVE_SUCCESS("0000", "代收成功"),
	RECEIVE_FAILED("0001", "代收失败"),
	SUCCESS("0002", "受理成功"),
	FAIL("0003", "受理失败"),
	PROCESS("0004", "处理中"),
	PARAMS_ERROR("1001", "参数错误"),
	CUSTNO_EXIST("1002","商户不存在"),
	NOT_OPEN("1003","商户未开通代收"),
	SINGLE_AMOUNT_ERROR("1004", "单笔限额超限"),
	SEQNO_EXIST("1005", "商户订单号已存在"),
	ACCOUNT_NO_ERROR("1006", "帐号错误"),
	IP_ERROR("1007","非绑定IP"),
	DOMIAN_ERROR("1008","非绑定域名"),
	DECRYPT_ERROR("1009","数据解密异常"),
	ENCRYPT_ERROR("1010","数据加密异常"),
	SINGLE_LOW_AMOUNT_ERROR("1011", "单笔发起金额不得小于10元"),
	SYS_ERROR("9999","系统异常");



	private final String code;
	private final String message;

	ReceiveCodeEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public static String getMessage(String code) {
		for (ReceiveCodeEnum t : ReceiveCodeEnum.values()) {
			if (code.equals(t.getCode())) {
				return t.getCode()+"-"+t.getMessage();
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
