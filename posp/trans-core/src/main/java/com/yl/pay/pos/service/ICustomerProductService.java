package com.yl.pay.pos.service;

/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2014
 * Company: com.yl.pay
 * @author jun
 */
public interface ICustomerProductService {
	/**
	 * 是否调单商户/需要延迟入账的商户
	 * @param customerNo
	 * @return
	 */
	public boolean isCheckBillCustomer(String customerNo);
	
}
