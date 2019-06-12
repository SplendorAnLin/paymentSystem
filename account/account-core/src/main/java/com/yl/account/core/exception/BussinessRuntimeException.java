package com.yl.account.core.exception;

/**
 * 业务运行时异常
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月24日
 * @version V1.0.0
 */
public class BussinessRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -6182516439129902812L;

	public BussinessRuntimeException() {
		super();
	}

	public BussinessRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BussinessRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public BussinessRuntimeException(String message) {
		super(message);
	}

	public BussinessRuntimeException(Throwable cause) {
		super(cause);
	}

}
