package com.yl.pay.pos.interfaces;


/**
 * Title: 银行接口签到
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: pay
 * @author jun
 */

public interface IBankSignInAction {
	
	public int executeSignIn(String bankCustomerNo, String bankPosCati) throws Exception;
	
}
