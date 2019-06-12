package com.pay.sign.exception;


/**
 * Title: 系统异常
 * Description: 需要强制处理
 * Copyright: Copyright (c)2015
 * Company: pay
 * @author zhongxiang.ren
 */

public class TransException extends Exception{
	protected String message;
	
	public TransException(String msg) {
        super(msg);
        message = msg;
    }

    public TransException(String msg, Throwable ex) {
        super(msg, ex);
        message = msg;
    }
    public TransException(Throwable ex){
        super("",ex);
        
    }
	
//    public String toString() {
//		String str = super.toString();
//		return str + "[" + message + "]";
//	}

	public String getMessage() {
		return message;
	}
}
