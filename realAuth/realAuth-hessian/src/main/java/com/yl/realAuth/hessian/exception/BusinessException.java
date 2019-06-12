package com.yl.realAuth.hessian.exception;

/**
 * 业务异常
 * @author Shark
 * @since 2015年6月3日
 */
public class BusinessException extends Exception {

	private static final long serialVersionUID = 8524968815067035898L;

	public BusinessException() {
		super();
	}

	public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

}
