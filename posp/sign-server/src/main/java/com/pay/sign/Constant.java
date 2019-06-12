package com.pay.sign;

/**
 * Title: 常量
 * Description:  常量
 * Copyright: Copyright (c)2015
 * Company: pay
 * @author zhongxiang.ren
 */

public class Constant {
	
	public static final String ALARM_PHONE_NO="13810314015,15210020251";//告警手机号
	public static final String BANKINTERFACE_ALARM_PHONE_NO="bankInterfaceAlarmPhoneNo";
	public static final String BIZ_ALARM_PHONE_NO="bizAlarmPhoneNo";
	
	public static final String STATUS_TRUE = "TRUE";					//状态可用
	public static final String STATUS_FALSE = "FALSE";					//状态不可用
	public static final int    BANK_MAX_TRX_TIME=2;						//一次交易走银行接口最大次数
	public static final String MESSAGE_CURRENCY_CODE = "156";			//币种代码
	
	
	public final static int FIELD_ID_START = 0;							//用于标识报文的域开始最小数	
	public final static int FIELD_ID_END = 64;							//用于标识报文的域结束最大数	
	public final static int LEN_BITMAP_BASE = 8;						//主位图的长度	
	public final static int LEN_BITMAP_EXTENDED = 8;					//扩展位图的长度	
	public final static int LEN_POS_MESSAGETYPE = 2;					//信息类型的长度
	public final static String CONTENT_POS_TPUD = "6000000042";			//TPUD	
	public final static int LEN_POS_TPUD = 5;							//TPUD的长度	
	public final static int LEN_POS_MESSAGE = 6;						//报文头长度	
	public final static int LEN_CONTENT = 2;			            	//报文长度	
	public final static String CONTENT_POS_Message = "602200000000";	//报文头
	public final static int LEN_POS_LRI = 3;							//拨号POS标识长度
	public final static String CONTENT_POS_LRI = "LRI";                 //拨号POS标识
	public final static int LEN_POS_PHONE = 8;							//拨号POS电话位长度
	public final static int LEN_POS_NODE = 6;							//拨号POS节点信息
	public final static String BANK_INTERFACE_SEQUENCE_HEAD="SEQ_SYSTEMTRACKENUMBER_";   	//
	
	public static final String SYSTEM_DEFAULT_FLAG = "DEFAULT";     	//default
	public static final String SYSTEM = "SYSTEM";
	public static final String SYSTEM_EXCEPTION_RESPONSE = "96";
	
	public static final String SETTLE_START_TIME="23:50:00";
	public static final String SETTLE_END_TIME="00:00:00";
	
	public static final String POS_SEQUENCE = "SEQ_POS_RETRIVL_NUM"; 	//生成参考号SEQ
	public static final String SUCCESS_POS_RESPONSE = "00";				//POS返回码（成功）
	
	public static final String USER_ROLE_CUSTOMER="CUSTOMER";			//用户角色类型"商户"
	public static final String ACCOUNT_BIZ_TYPE_RECEIPT="RECEIPT";		//账户业务类型"收款"
	
	public static final String CARD_ACCEPTOR_NAME = "lefu";				//受卡方名称地址
	
	public static final String SMS_ALARM_CODE_1="TransCore1";			//短信报警平台编号
	public static final String SMS_ALARM_CODE_2="UnionPay_Now";			//
	public static final String SMS_ALARM_CODE_3="UnionPay_Collection";		
	
}
