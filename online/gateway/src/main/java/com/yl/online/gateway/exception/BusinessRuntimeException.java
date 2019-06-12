package com.yl.online.gateway.exception;

/**
 * 业务运行时异常
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月6日
 * @version V1.0.0
 */
public class BusinessRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -6182516439129902812L;

	public BusinessRuntimeException() {
		super();
	}

	public BusinessRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BusinessRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessRuntimeException(String message) {
		super(message);
	}

	public BusinessRuntimeException(Throwable cause) {
		super(cause);
	}

}
