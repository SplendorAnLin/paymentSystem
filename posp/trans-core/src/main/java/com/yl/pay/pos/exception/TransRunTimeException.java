package com.yl.pay.pos.exception;

/**
 * Title: 系统运行时异常
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class TransRunTimeException extends RuntimeException{
	protected String message;
	
	public TransRunTimeException(String msg) {
        super(msg);
        message =msg;
    }

    public TransRunTimeException(String msg, Throwable ex) {
        super(msg, ex);
        message =msg;
    }
    public TransRunTimeException(Throwable ex){
        super("",ex);
        
    }
	public String getMessage() {
		return message;
	}
}
