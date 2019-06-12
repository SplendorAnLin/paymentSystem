package com.yl.pay.pos.enums;


/**
 * Title: 银行终端运行状态
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun
 */

public enum BankPosRunStatus {
	
	SIGNIN,	//已签到，可进行交易
	SIGNOFF	//已签退，不能进行交易
}
