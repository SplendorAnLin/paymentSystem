package com.yl.payinterface.core.exception;

/**
 * 业务异常
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月16日
 * @version V1.0.0
 */
public class BusinessException extends Exception {

	private static final long serialVersionUID = 8524968815067035898L;

	public BusinessException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public BusinessException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public BusinessException(Throwable cause) {
		super(cause);
	}

}
