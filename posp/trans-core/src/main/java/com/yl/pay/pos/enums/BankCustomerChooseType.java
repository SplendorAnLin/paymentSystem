package com.yl.pay.pos.enums;


/**
 * Title: 银行商户号选择类型
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun
 */

public enum BankCustomerChooseType {
	/**
	 * 商户级，一户一码
	 */
	CUSTOMER,
	/**
	 * 大商户号，系统级
	 */
	SYSTEM,
	/**
	 * 先商户级，再走系统级
	 */
	CUSTOMER_SYSTEM,
	/**
	 * 先系统级，再商户级
	 */
	SYSTEM_CUSTOMER
	
}
