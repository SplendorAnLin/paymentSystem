package com.yl.pay.pos.exception.trans;

import com.yl.pay.pos.exception.TransException;
import com.yl.pay.pos.exception.TransExceptionConstant;


/**
 * Title: 银行需重新签到异常
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class BankNeedReSignInException extends TransException{
	private static String msg=TransExceptionConstant.BANK_NEED_RESIGNIN;
	
	public BankNeedReSignInException(Throwable ex) {
		super(msg, ex);
		// TODO Auto-generated constructor stub
	}

	public BankNeedReSignInException() {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	public BankNeedReSignInException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	
	
}
