package com.yl.account.core.exception;

/**
 * 业务异常
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月24日
 * @version V1.0.0
 */
public class BussinessException extends Exception {

	private static final long serialVersionUID = 8524968815067035898L;

	public BussinessException() {
		super();
	}

	public BussinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BussinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BussinessException(String message) {
		super(message);
	}

	public BussinessException(Throwable cause) {
		super(cause);
	}

}
