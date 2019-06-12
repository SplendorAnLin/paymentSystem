package com.yl.pay.pos.constant;

public class ProxyPayConstant {

	/** 代付系统异常 **/
	public static final String PROXY_PAY_SYSTEM_ERROR ="96";
	/** 成功 **/
	public static final String PROXY_PAY_SUCC = "00"; 
	/** 交易失败 **/
	public static final String PROXY_PAY_TRANS_FAIL = "01";
	/** 系统未开放或暂时关闭，请稍后再试 **/
	public static final String PROXY_PAY_NOT_OPEN = "02";
	/** 交易通讯超时 **/
	public static final String PROXY_PAY_TIMEOUT = "03";
	/** 交易状态未明 **/
	public static final String PROXY_PAY_TRANS_STAUS_UNKOWN = "04";
	/** 连接超时 **/
	public static final String PROXY_PAY_CENNECT_TIMEOUT = "13";
	/** 订单已存在 **/
	public static final String PROXY_PAY_ORDER_EXIST = "16";
	/** 交易不存在 **/
	public static final String PROXY_PAY_TRANS_NOT_EXIST = "17";
	/** 费率类型不存在**/
	public static final String PROXY_PAY_FEE_TYPE_NOT_EXIST = "18";
	/** 提现金额超限 **/
	public static final String PROXY_PAY_AMOUNT_LIMMIT = "19";
	/** 收款人卡号限额或笔数达上线 **/
	public static final String PROXY_PAY_LIMMIT_OR_MAXMUM = "20";
	/** 失败 **/
	public static final String PROXY_PAY_FAIL = "21";
	/** 其他失败 **/
	public static final String PROXY_PAY_OTHER_FAIL = "22";
	/** 黑名单结算卡 **/
	public static final String PROXY_PAY_BLACK_CARD = "26";
	/** 交易失败，详情请咨询您的发卡行**/
	public static final String PROXY_PAY_ISSUER_PROBLEM = "60";
	/** 必要参数为空 **/
	public static final String PROXY_PAY_PARAM_NULL = "Y001";
	/** IP验证不通过 **/
	public static final String PROXY_PAY_IP_ILLEGAL = "Y002";
	/** 验签数据不通过 **/
	public static final String PROXY_PAY_SIGN_ILLEGAL = "Y003";
	/** 联系运维人员 **/
	public static final String PROXY_PAY_CALL_WORKER = "Y004";
	/** 未知错误，联系运维人员**/
	public static final String PROXY_PAY_UNKOWN = "UNKOWN";
	
}
