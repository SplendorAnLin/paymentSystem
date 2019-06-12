package com.yl.pay.pos.interfaces.P360001;


/**
 * Title: 南昌建行常量
 * Description:  常量
 * Copyright: Copyright (c)2011
 * Company: pay
 * @author xiaojun.yang
 */

public class Constant {
	
	//接口类型（两位）+ 地区编码（四位与身份证同）+ 银行名称（工，家，招，建，中）	
	public static final String SEQ_SYSTEM_TRACKE_NUMBER = "SEQ_SYSTEMTRACKENUMBER_P360001";
	
	//frp code
	public static final String INTERFACE_CODE = "P360001";
	
	//0域
	public static final String TRANS_TYPE_BALANCE_QUERY="0200";//可用余额查询
	public static final String TRANS_TYPE_PURCHASE="0200";//消费
	public static final String TRANS_TYPE_REVESAL="0400";//冲正
	public static final String TRANS_TYPE_PURCHASE_VOID="0200";//消费撤销
	public static final String TRANS_TYPE_VOID_REVESAL="0400";//消费撤销冲正
	public static final String TRANS_TYPE_SIGN_ON="0800";//签到
	public static final String TRANS_TYPE_SIGN_OFF="0820";//签退
	public static final String TRANS_TYPE_REFUND="0220";//退货
	public static final String TRANS_TYPE_DAISHOU="0200";//代收
	public static final String TRANS_TYPE_DAIFU="0200";//代付
	
	public static final String MTI_PREAUTH = "0100";									//预授权
	public static final String MTI_PREAUTH_VOID = "0100";								//预授权撤销
	public static final String MTI_PREAUTH_REVERSAL = "0420";							//预授权冲正
	public static final String MTI_PREAUTH_VOID_REVERSAL = "0420";						//预授权撤销冲正
	public static final String MTI_PREAUTH_COMP = "0200";								//预授权完成
	public static final String MTI_PREAUTH_COMP_VOID = "0200";							//预授权完成撤销
	public static final String MTI_PREAUTH_COMP_REVERSAL = "0420";						//预授权完成冲正
	public static final String MTI_PREAUTH_COMP_VOID_REVERSAL = "0420";					//预授权完成撤销冲正
	
	//3域
	public static final String PROC_BALANCE_QUERY="310000";
	public static final String PROC_PURCHASE="000000";
	public static final String PROC_PURCHASE_REVESAL="000000";
	public static final String PROC_PURCHASE_VOID="200000";
	public static final String PROC_RURCHASE_VOID_REVESAL="200000";
	public static final String PROC_REFUND="200000";
	public static final String PROC_DAISHOU="040000";
	public static final String PROC_DAISHOU_REVESAL="040000";
	public static final String PROC_DAIFU="240000";
	public static final String PROC_DAIFU_REVESAL="240000";
	
	public static final String PROC_PREAUTH="030000";
	public static final String PROC_PREAUTH_COMP="000000";
	public static final String PROC_PREAUTH_COMP_VOID="200000";
	public static final String PROC_PREAUTH_VOID="200000";
	
	//22域
	public static final String ENTRY_MODE_021="021";//刷卡带密码
	public static final String ENTRY_MODE_011="011";//手工输入卡号,带密码
	public static final String ENTRY_MODE_022="022";//刷卡不带密码
	public static final String ENTRY_MODE_012="012";//手工输入卡号,不带密码
	public static final String ENTRY_MODE_051="051";//插卡带密码
	public static final String ENTRY_MODE_052="052";//插卡不带密码
	// 24域
	public static final String NATIONAL_NETWORK="009";
	
	// 25域
	public static final String POS_COND_CODE_10="10";//深圳建行  表示卡号带密码
	public static final String POS_COND_CODE_06="06";//深圳建行  表示卡号不带密码
	
	//响应码 39域
	public static final String BANK_RESP_CODE_00="00";//  00--成功
	public static final String BANK_RESP_CODE_63="63";//  63--需要重新签到
	public static final String BANK_RESP_CODE_A0="A0";//  A0--需要重新签到
	public static final String BANK_RESP_CODE_98="98";//  96--需要进行冲正
	
	// 49域
	public static final String CURRCY_CODE_TRANS="156";
	public static final String CUSTOMER_NAME_AFTER ="－乐富";//43 
	// 不生成MAC的交易类型
	public static final String NOMACTYPE_SIGNIN="0800";//签到
	public static final String NOMACTYPE_SIGNOFF="0820";//签退
	
	public static final String TRXBATCHNO="000000";//建行交易批次号
	public static final String NETWORKMESSAGECODE="000";//建行网络信息码
	public static final String TRXTYPEPURCHASE="22";//消费/冲正交易类型
	public static final String TRXTYPEPURCHASEVOID="23";//消费撤消/冲正交易类型
	public static final String TRXTYPEPURCHASEREFUND="25";//消费退货交易类型
	
	// 消息类型码
	public static final String MESSAGETYPECODE="00";
	public static final String BALANCE_INQUIRY_MESSAGETYPECODE="01";//查询
	public static final String PRE_AUTHORIZATION_MESSAGETYPECODE="10";//预授权/冲正/追加预授权/冲正
	public static final String PRE_AUTHORIZATIONVOID_MESSAGETYPECODE="11";//预授权撤销/冲正
	public static final String PRE_AUTHORIZATION_COMPLETION_MESSAGETYPECODE="20";//预授权完成（联机）/冲正
	public static final String PRE_AUTHORIZATION_COMPLETION_VOID_MESSAGETYPECODE="21";//预授权完成撤销/冲正
	public static final String PURCHASE_MESSAGETYPECODE="22";//消费/冲正
	public static final String PURCHASEVOID_MESSAGETYPECODE="23";//消费撤销/冲正
	public static final String OFFLINEPRE_AUTHORIZATION_COMPLETION_MESSAGETYPECODE="24";//预授权完成（离线）
	public static final String PURCHASE_REFUND_MESSAGETYPECODE="25";//退货
	
	// 网络管理信息码
	public static final String SIGNIN_DES_NETMES_INFOCODE="001";//POS 终端签到
	public static final String SIGN_OFF_NETMES_INFOCODE="002";//POS 终端签退
	public static final String SIGNIN_3DES_NETMES_INFOCODE="003";//POS 终端签到（双倍长密钥算法)
	public static final String SETTLE_NETMES_INFOCODE="201";//POS 终端批结算
	public static final String DEFAULT_NETMES_INFOCODE="000";//缺省值
	
	//密钥
	public static final String ESSC_MAC = "zak";
	public static final String ESSC_PIN = "zpk";
	public static final String ESSC_MK = "zmk";
	
	
	public static final String TEST_PAN = "6227075400235327";						//测试卡号	
	public static final String TEST_TRACK2 = "6227075400235327=17065019523020000000";	//测试二磁	
	public static final String TEST_BATCHNO = "000001";									//测试批次号	
//	public static final String TEST_BANK_CUSTOMERNO = "105584099990033";				//测试商户号	
//	public static final String TEST_BANK_POSCATI = "00000001";							//消费终端号
	public static final String TEST_BANK_CUSTOMERNO = "882451202890495";				//测试商户号	
	public static final String TEST_BANK_POSCATI = "00000483";							//消费终端号
}
