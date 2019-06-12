package com.yl.dpay.hessian.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 代付异常枚举
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月15日
 * @version V1.0.0
 */
public enum DpayExceptionEnum {

	/**
	 * 受理成功，处理中
	 */
	HANDLE("S3101", "受理成功，处理中"),
	/**
	 * 受理成功，待审核
	 */
	WAITAUDIT("S3100", "受理成功，待审核"),
	/**
	 * 商户不存在
	 */
	CUSTNOEXIST("S3011", "商户不存在"),
	/**
	 * 代付信息为空
	 */
	DPAYINFOISNULL("S3012", "代付信息为空"),
	/**
	 * 未开通代付
	 */
	NOTOPEN("S3013", "未开通代付"),
	/**
	 * 解密失败
	 */
	DECRYPTFAILED("S3014", "解密失败"),
	/**
	 * 参数错误
	 */
	PARAMSERR("S3015", "参数错误"),
	/**
	 * 商户订单号参数错误
	 */
	CUSTORDERERR("S3018", "商户订单号参数错误"),
	/**
	 * 用途描述参数错误
	 */
	USERAGEERR("S3019", "用途描述参数错误"),
	/**
	 * 商户订单已存在
	 */
	CUSTORDEREXIST("S3021", "商户订单已存在"),
	/**
	 * 卡类型参数错误
	 */
	CARDTYPEERR("S3022", "卡类型参数错误"),
	/**
	 * 收款帐号参数错误
	 */
	ACCNOERR("S3023", "收款帐号参数错误"),
	/**
	 * 收款人参数错误
	 */
	ACCNAMEERR("S3024", "收款人参数错误"),
	/**
	 * 金额参数错误
	 */
	AMOUNTERR("S3025", "金额参数错误"),
	/**
	 * 账户类型参数错误
	 */
	ACCTYPEERR("S3027", "账户类型参数错误"),
	/**
	 * 通知地址参数错误
	 */
	NOTIFYADDRERR("S3028", "通知地址参数错误"),
	/**
	 * 商户订单时间参数错误
	 */
	DATEERR("S3029", "商户订单时间参数错误"),
	/**
	 * CVV参数错误
	 */
	CVVERR("S3030", "CVV参数错误"),
	/**
	 * 信用卡有效期参数错误
	 */
	VALIDATEERR("S3031", "信用卡有效期参数错误"),
	/**
	 * 商户IP错误
	 */
	IPERR("S3032", "商户IP错误"),
	/**
	 * 商户域名错误
	 */
	DOMAINERR("S3033", "商户域名错误"),
	/**
	 * 认证类型错误
	 */
	CERTYPEERR("S3034", "认证类型错误"),
	/**
	 * 认证号码错误
	 */
	CERNOERR("S3035", "认证号码错误"),
	/**
	 * 开户行信息错误
	 */
	OPENBANKERR("S3036", "开户行信息错误"),
	/**
	 * 付款成功
	 */
	REMITSUCCESS("S3000", "付款成功"),
	/**
	 * 付款失败
	 */
	REMITFAILED("S3001", "付款失败"),
	/**
	 * 付款结果未知
	 */
	REMIT_UNKNOW("S3002", "付款结果未知"),
	/**
	 * 签名错误
	 */
	SIGNERR("S3017", "签名错误"),
	/**
	 * 商户订单不存在
	 */
	CUST_ORDER_NO_EXIST("S3016", "商户订单不存在"),
	/**
	 * 无可用通道
	 */
	NO_AVAILABLE_CHANNEL("S3301", "无可用通道"),
	
	/**
	 * 未开通假日付
	 */
	CUST_NOT_OPEN_HOLIDAY("S3302", "未开通假日付"),
	
	/**
	 * 非付款时间
	 */
	NO_REMIT_TIME("S3201","非付款时间"),
	/**
	 * 系统假日付关闭
	 */
	SYS_HOLIDAY_CLOSE("S3202","系统假日付关闭"),
	/**
	 * 金额超过系统最大限额
	 */
	OUT_SYS_MAX("S3203","金额超过系统最大限额"),
	/**
	 * 金额小于系统最小限额
	 */
	UNDER_SYS_MIN("S3204","金额小于系统最小限额"),
	/**
	 * 单日内同一收款人金额重复
	 */
	DAY_PAYER_REPEAT("S3205","单日内同一收款人金额重复"),
	/**
	 * 费率不可用
	 */
	FEE_DISABLE("S3206", "费率不可用"),
	/**
	 * 金额超过用户最大限额
	 */
	OUT_USER_MAX("S3207","金额超过用户最大限额"),
	/**
	 * 金额小于用户最小限额
	 */
	UNDER_USER_MIN("S3208","金额小于用户最小限额"),
	/**
	 * 商户账户余额不足或账户状态异常
	 */
	CUST_BAL_ERR("S3209","账户余额不足或账户状态异常"),
	/**
	 * 金额超过用户日限额
	 */
	OUT_USER_DAY_MAX("S3210","金额超过用户当日最大限额"),
	/**
	 * 系统异常
	 */
	SYSERR("S3099", "系统异常");

	private final String code;
	private final String message;

	DpayExceptionEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public static String getMessage(String code) {
		for (DpayExceptionEnum t : DpayExceptionEnum.values()) {
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

	public static String getErrMsg(String code) {
		return code + ":" + getMessage(code);
	}

	public static String getConvertCode(String code) {
		Map<String, String> responseCode = new HashMap<String, String>();
		responseCode.put("S3100", "0000");
		responseCode.put("S3101", "0100");
		responseCode.put("S3011", "0001");
		responseCode.put("S3012", "0002");
		responseCode.put("S3022", "0002");
		responseCode.put("S3023", "0002");
		responseCode.put("S3024", "0002");
		responseCode.put("S3025", "0002");
		responseCode.put("S3015", "0002");
		responseCode.put("S3018", "0002");
		responseCode.put("S3019", "0002");
		responseCode.put("S3027", "0002");
		responseCode.put("S3028", "0002");
		responseCode.put("S3029", "0002");
		responseCode.put("S3030", "0002");
		responseCode.put("S3031", "0002");
		responseCode.put("S3032", "0021");
		responseCode.put("S3033", "0022");
		responseCode.put("S3034", "0002");
		responseCode.put("S3035", "0002");
		responseCode.put("S3014", "0003");
		responseCode.put("S3021", "0004");
		responseCode.put("S3026", "0007");
		responseCode.put("S3013", "0005");
		responseCode.put("S3031", "0005");
		responseCode.put("S3032", "0002");
		responseCode.put("S3099", "9999");
		responseCode.put("S3000", "1000");
		responseCode.put("S3001", "1001");
		responseCode.put("S3017", "0009");
		responseCode.put("S3016", "0010");
		responseCode.put("S3209", "0006");
		
		responseCode.put("S3302", "0011");
		responseCode.put("S3201", "0012");
		responseCode.put("S3202", "0013");
		responseCode.put("S3203", "0014");
		responseCode.put("S3204", "0015");
		responseCode.put("S3205", "0016");
		responseCode.put("S3207", "0017");
		responseCode.put("S3208", "0018");
		responseCode.put("S3209", "0019");
		responseCode.put("S3210", "0020");
		
		
		return responseCode.get(code)==null?"9999":responseCode.get(code);
	}

	public static String getConvertMsg(String code) {
		Map<String, String> customerMsg = new HashMap<String, String>();
		customerMsg.put("1000", "代付成功");
		customerMsg.put("1001", "代付失败");
		customerMsg.put("1002", "处理中");
		customerMsg.put("0100", "下单成功,处理中");
		customerMsg.put("0000", "下单成功,待审核");
		customerMsg.put("0001", "无此商户");
		customerMsg.put("0002", "参数错误或为空");
		customerMsg.put("0003", "解密失败");
		customerMsg.put("0004", "商户订单已存在");
		customerMsg.put("0005", "商户状态异常");
		customerMsg.put("0006", "账户余额不足或账户状态异常");
		customerMsg.put("0007", "银行名称有误或不支持该银行");
		customerMsg.put("0008", "请填写准确分支行信息");
		customerMsg.put("0009", "验签错误");
		customerMsg.put("0010", "商户订单不存在");
		
		customerMsg.put("0011", "未开通假日付");
		customerMsg.put("0012", "非付款时间");
		customerMsg.put("0013", "系统假日付关闭");
		customerMsg.put("0014", "金额超过系统最大限额");
		customerMsg.put("0015", "金额低于系统最小限额");
		customerMsg.put("0016", "单日内同一收款人金额重复");
		customerMsg.put("0017", "金额超过商户最大限额");
		customerMsg.put("0018", "金额小于商户最小限额");
		customerMsg.put("0019", "商户账户余额不足或账户状态异常");
		
		customerMsg.put("0020", "金额超过用户最大金额");
		customerMsg.put("0021", "商户IP不符合");
		customerMsg.put("0022", "商户域名不符合");
		customerMsg.put("9999", "系统异常");
		return customerMsg.get(code);
	}
}
