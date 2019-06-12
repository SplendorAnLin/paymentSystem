package com.yl.pay.pos.enums;

/**
 * Title: 订单状态
 * Description:  
 * Copyright: Copyright (c)2010
 * Company: com.yl.pay
 * @author haitao.liu
 */
public enum OrderStatus {
	
	INIT,			//初始化
	AUTHORIZED,		//已授权
	REPEAL,			//撤销
	SUCCESS,		//成功
	REVERSALED,		//已冲正
	SETTLED, 		//已结算
	ACCEPTED		//脚本通知
}
