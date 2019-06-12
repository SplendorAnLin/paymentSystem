package com.yl.pay.pos.enums;

/**
 * Title: 银行路由默认规则类型
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */
public enum BankRouteDefaultRuleType {
	/**
	 * 只走一户一码
	 */
	CUSTOMER,
	/**
	 * 只走大商户
	 */
	SYSTEM,
	/**
	 * 只走一户一码，考虑本对本（成本最低原则）
	 */	
	CUSTOMER_OWN,
	/**
	 * 只走大商户，考虑本对本（成本最低原则）
	 */
	SYSTEM_OWN,
	/**
	 * 先走一户一码，再走大商户
	 */
	CUSTOMER_SYSTEM,
	/**
	 * 先走大商户号，再走一户一码
	 */
	SYSTEM_CUSTOMER,
	/**
	 * 先走一户一码，再走大商户，考虑本对本（成本最低原则）
	 */
	CUSTOMER_SYSTEM_OWN,
	/**
	 * 先走大商户，再走一户一码，考虑本对本（成本最低原则）
	 */
	SYSTEM_CUSTOMER_OWN
	
	
}
