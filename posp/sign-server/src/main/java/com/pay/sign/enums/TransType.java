package com.pay.sign.enums;

/**
 * Title: 交易类型
 * Description:  运行状态
 * Copyright: Copyright (c)2015
 * Company: pay
 * @author zhongxiang.ren
 */

public enum TransType {
	
	BALANCE_INQUIRY,			//余额查询
	AVAILABLE_FUNDSINQUIRY,		//可用余额查询
	PURCHASE,					//消费
	PURCHASE_VOID,				//消费撤销
	PURCHASE_REVERSAL,			//消费冲正
	PURCHASE_VOID_REVERSAL,		//消费撤销冲正
	PURCHASE_REFUND,			//消费退货
	
	//管理类
	
	SIGN_IN,					//签到
	SIGN_OFF,					//签退
	SETTLE,						//结算
	BATCH_UP,					//批上送
	DOWNLOAD_MAIN_KEY,			//下载主密钥
	
	//预授权类
	
	PRE_AUTH,					//预授权
	PRE_AUTH_VOID,				//预授权撤销
	PRE_AUTH_REVERSAL,			//预授权冲正
	PRE_AUTH_VOID_REVERSAL,		//预授权	撤销冲正
	PRE_AUTH_COMP,				//预授权完成
	PRE_AUTH_COMP_VOID,			//预授权完成撤销
	PRE_AUTH_COMP_REVERSAL,		//预授权完成冲正
	PRE_AUTH_COMP_VOID_REVERSAL,	//预授权完成撤销冲正
	//电子现金
	SPECIFY_QUANCUN,			//指定圈存
	NOT_SPECIFY_QUANCUN,		//非指定圈存
	CASH_RECHARGE_QUNCUN,		//現金充值
	
	
	
	SIGNATURE					//手机图片签名请求
}
