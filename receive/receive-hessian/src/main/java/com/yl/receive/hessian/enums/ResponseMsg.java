package com.yl.receive.hessian.enums;

/**
 * 响应消息
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月13日
 * @version V1.0.0
 */
public enum ResponseMsg {


	SUCCESS("0000", "代收成功"),
	FAILED("0001", "代收失败"),
	APPLYSUCCESS("0002", "申请成功"),
	APPLYFAILED("0003", "申请失败"),
	PROCESS("0004", "处理中"),

	// 新增代收同步结果
	RESULTSUCCESS("0006", "代收成功"),
	RESULTFAILED("0007", "代收失败"),


	DAILYAMOUNTPASS("1001", "日限额超限"),
	SINGLEAMOUNTPASS("1002", "单笔限额超限"),
	SINGLEBATCHNUMPASS("1003", "单批次笔数超限"),
	CONTRACTIDERROR("1004", "授权信息错误"),
	BATCHNOEXIST("1005", "批次号已存在"),
	SEQNOEXIST("1006", "商户订单号已存在"),
	CARDILLEGAL("1007", "卡号非法"),
	NOMATCHESOPENBANK("1008", "未匹配到开户行信息"),
	PARAMSERROR("1009", "参数异常"),
	PAY_AMOUNT_IS_SMALL("1010","发起金额太小"),
	NOT_OPEN("1011","代收业务未开通"),
	FEE_DISABLE("1011","代收业费率不可用"),

	//风控处理结果封装  add by yanfei.zhao 20160317
	TRADE_OUT_OF_LIMIT("1011","交易超出限额"),
	TRADE_EXIST_RISK("1012","交易存在风险"),
	NO_AVAILABLE_CHANNEL("1013","无有效路由"),
	INVALID_CUSTOMER("1014","无效商户"),

	NOKEYPARTNER("10005","非授权商户"),
	UNKNOW("9999", "系统异常");

	private final String code;
	private final String message;

	ResponseMsg(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public static String getStatus(String code) {
		for (ResponseMsg t : ResponseMsg.values()) {
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
