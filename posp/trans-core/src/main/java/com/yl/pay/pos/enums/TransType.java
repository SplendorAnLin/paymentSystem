package com.yl.pay.pos.enums;

/**
 * Title: 交易类型
 * Description:  运行状态
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public enum TransType {
	
	BALANCE_INQUIRY,			//余额查询
	AVAILABLE_FUNDSINQUIRY,		//可用余额查询
	PURCHASE,					//消费
	PURCHASE_VOID,				//消费撤销
	PURCHASE_REVERSAL,			//消费冲正
	PURCHASE_VOID_REVERSAL,		//消费撤销冲正
	PURCHASE_REFUND,			//消费退货
	SPECIFY_QUANCUN,			//指定圈存
	SPECIFY_QUANCUN_REVERSAL,   //指定圈存冲正
	NOT_SPECIFY_QUANCUN,		//非指定圈存
	NOT_SPECIFY_QUANCUN_REVERSAL,//非指定圈存冲正
	CASH_RECHARGE_QUNCUN,		//現金充值
	CASH_RECHARGE_QUNCUN_REVERSAL,//現金充值沖正
	OFFLINE_PURCHASE,			//脱机消费
	PURCHASE_SCRIPT_NOTICE, 	//消费脚本通知
	SPECIFY_QUANCUN_NOTICE,		//指定脚本通知
	NOT_SPECIFY_QUANCUN_NOTICE, //非指定脚本通知
	BALANCE_INQUIRY_NOTICE,		//查询脚本通知
	PREAUTH_SCRIPT_NOTICE,		//预授权脚本通知
	CASH_RECHARGE_QUNCUN_NOTICE,//現金充值腳本通知
	//管理类
	
	SIGN_IN,					//签到
	SIGN_OFF,					//签退
	SETTLE,						//结算
	BATCH_UP,					//批上送
	DOWNLOAD_MAIN_KEY,			//下载主密钥
	DOWNLOAD_IC_REQUEST,	//下载IC公钥请求
	DOWNLOAD_IC_TRANSFER,	 //下载IC公钥传递
	DOWNLOAD_IC_END,		     //下载IC公钥结束
	//预授权类
	
	PRE_AUTH,					//预授权
	PRE_AUTH_VOID,				//预授权撤销
	PRE_AUTH_REVERSAL,			//预授权冲正
	PRE_AUTH_VOID_REVERSAL,		//预授权	撤销冲正
	PRE_AUTH_COMP,				//预授权完成
	PRE_AUTH_COMP_VOID,			//预授权完成撤销
	PRE_AUTH_COMP_REVERSAL,		//预授权完成冲正
	PRE_AUTH_COMP_VOID_REVERSAL	//预授权完成撤销冲正
	
}
