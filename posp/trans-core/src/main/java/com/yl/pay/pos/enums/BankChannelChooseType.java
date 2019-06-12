package com.yl.pay.pos.enums;

/**
 * Title: 银行通道选择类型
 * Description: 选择方式
 * Copyright: Copyright (c)2012
 * Company: com.yl.pay
 * @author jun
 */

public enum BankChannelChooseType {
	
	/**
	 * 按指定顺序
	 */
	INORDER,
	/**
	 * 按成本
	 */
	INCOST,
	/**
	 * 复合，先按指定再按成本
	 */
	COMPLEX
	
}
