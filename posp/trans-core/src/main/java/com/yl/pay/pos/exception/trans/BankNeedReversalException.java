package com.yl.pay.pos.exception.trans;

import com.yl.pay.pos.exception.TransException;
import com.yl.pay.pos.exception.TransExceptionConstant;

/**
 * Title: 银行交易需冲正异常
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class BankNeedReversalException extends TransException{
	
	private static String msg=TransExceptionConstant.BANK_NEED_REVERSAL;
	
	public BankNeedReversalException(Throwable ex) {
		super(msg, ex);
	}

	public BankNeedReversalException() {
		super(msg);
	}
	public BankNeedReversalException(String msg) {
		super(msg);
	}	
}
