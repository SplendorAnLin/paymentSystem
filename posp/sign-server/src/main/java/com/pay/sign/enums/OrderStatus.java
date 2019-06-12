package com.pay.sign.enums;

/**
 * Title: 订单状态
 * Description:  
 * Copyright: Copyright (c)2015
 * Company: pay
 * @author zhongxiang.ren
 */
public enum OrderStatus {
	
	INIT,			//初始化
	AUTHORIZED,		//已授权
	REPEAL,			//撤销
	SUCCESS,		//成功
	REVERSALED,		//已冲正
	SETTLED 		//已结算	
}
