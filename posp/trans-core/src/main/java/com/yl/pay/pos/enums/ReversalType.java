package com.yl.pay.pos.enums;

/**
 * Title: 冲正类型
 * Description: 
 * Copyright: Copyright (c)2012
 * Company: com.yl.pay
 * @author jun
 */

public enum ReversalType {
	
	/**
	 * POS端发起冲正
	 */
	POS_REVERSAL,
	
	/**
	 * 系统内部发起冲正
	 */
	SYSTEM_REVERSAL,
	/**
	 * 撤销
	 */
	PURCHASE_VOID
	
}
