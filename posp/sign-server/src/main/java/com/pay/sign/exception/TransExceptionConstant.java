package com.pay.sign.exception;

/**
 * Title: 交易异常码定义
 * Description: 
 * Copyright: Copyright (c)2015
 * Company: pay
 * @author zhongxiang.ren
 */

public class TransExceptionConstant {
	
	//系统内部异常(0000XX)
	
	public static final String SYSTEM_EXCEPTION 			= "111111";		//系统未知异常   96		
	
	public static final String CUSTOMER_IS_NULL 			= "000001";  	//商户不存在	
	public static final String CUSTOMER_IS_INACTIVE 		= "000002";  	//商户已经关闭	
	public static final String POS_IS_NULL 					= "000003";  	//终端不存在 	
	public static final String POS_IS_INACTIVE 				= "000004";  	//终端已经关闭
	public static final String SHOP_IS_NULL 				= "000005";	 	//网点不存在	
	public static final String SHOP_IS_INACTIVE 			= "000006";  	//商户已经关闭	
	public static final String TRX_FUNCTION_LIMIT 			= "000007";	  	//交易权限限制	
	public static final String CUSTOMER_INVALID		 		= "000008";		//商户无效	
	public static final String CUSTOMER_IN_BLACKLIST 		= "000009";		//商户已入黑名单	
	public static final String TRX_CARDTYPE_LIMIT 			= "000010";		//交易卡种限制	
	public static final String ORDER_IS_EXIST 				= "000011";		//订单已存在，流水号重复	
	public static final String TRANSTYPE_NOT_PERMIT 		= "000012";		//无交易权限	
	public static final String AMOUNT_INVALID 				= "000013"; 	//金额无效	
	public static final String ORDER_CREATE_FAIL 			= "000014";		//订单创建失败
	public static final String POS_MAC_CHECKERR				= "000015";		//POS终端MAC校验错
	public static final String POS_MAC_GENERATEERR			= "000016";		//生成POS终端MAC错
	public static final String SOURCE_PAYMENT_IS_NULL		= "000017";		//原交易记录不存在	
	public static final String TRX_MCC_LIMIT 				= "000018";		//交易MCC限制	
	public static final String CARD_INVALID 				= "000019";  	//卡无效 
	public static final String CUSTOMERFEE_IS_NULL			= "000020";		//未设置商户费率
	public static final String SHOPNO_NOT_EUQAL 			= "000021"; 	//网点不匹配	
	public static final String SOURCE_ORDER_ISNULL			= "000022";		//原订单不存在
	public static final String SOURCE_ORDER_STATUSERR		= "000023";		//原订单状态不正确,不允许撤销
	public static final String PURCHASE_VOID_TIMEERR		= "000024";		//此时间段不允许撤销交易
	public static final String PURCHASE_VOID_PANERR			= "000025";		//撤销交易卡号与原交易不符
	public static final String REVERSAL_TIME_ERR			= "000026";		//此时间段不允许冲正交易
	public static final String SOURCE_ORDER_CREDITED 		= "000027";		//原订单已入账 
	public static final String DIAL_NUMBER_ERROR			= "000028";		//拨号POS电话号码校验失败
	public static final String SINGLE_AMOUNT_LIMIT			= "000029";		//单笔交易金额限制
	public static final String ACCUMULATED_AMOUNT_LIMIT    	= "000030";		//累计交易金额限制
	public static final String PRE_AUTH_COMP_PAYMENT_IS_EXIST = "000031";	//存在预授权完成记录（预授权只能完成一次）
	
	public static final String ORDER_IS_NULL				= "000041";		//订单为空
	public static final String PAYMENT_IS_NULL				= "000042";		//交易为空 
	public static final String BANKTERMINAL_IS_NULL 		= "000043";		//银行终端为空 
	public static final String POSREQUEST_IS_NULL			= "000044";		//POS交易请求为空 
				
	//银行接口及路由异常码(0001XX)

	public static final String BANK_NEED_REVERSAL			= "000100";		//银行交易通讯异常，需冲正
	public static final String INTERFACE_EXCEPTION			= "000101";		//银行接口调用失败
	public static final String BANK_TRX_HANDLE_FAIL 		= "000102";		//银行交易处理失败	
	public static final String BANK_TRX_HANDLE_TIMEOUT 		= "000103";		//银行交易处理超时
	public static final String INTERFACE_ESSC_CREATEMAC		= "000104";		//银行接口生成MAC失败	
	public static final String INTERFACE_ESSC_PINTRANS		= "000105";		//银行接口PIN转加密失败
	public static final String INTERFACE_ESSC_TESTMAC		= "000106";		//银行接口MAC校验错
	public static final String BANK_NEED_RESIGNIN			= "000107";		//银行交易需重新签到	
	public static final String BANK_NOT_FOUND 				= "000108"; 	//找不到对应银行	
	public static final String NO_USEABLE_BANKCHANNEL 		= "000109";		//无可用银行通道
	public static final String NO_USEABLE_BANKTERMINAL 		= "000110";		//无可用银行终端	
	public static final String ROUTE_PROFILE_OVER_SIZE 		= "000111";		//存在多条匹配的路由规则
	public static final String ROUTE_RULE_ERROR				= "000112";		//路由规则配置错
	
	
}


