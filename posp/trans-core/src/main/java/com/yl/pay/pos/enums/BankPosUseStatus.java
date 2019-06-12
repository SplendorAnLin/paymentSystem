package com.yl.pay.pos.enums;


/**
 * Title: 银行终端使用状态
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun
 */

public enum BankPosUseStatus {
	
	 IDLE, 		//空闲，可用
	 BUSY,		//忙碌，不可用
	 SIGNING;	//签到中，不可用	
}
