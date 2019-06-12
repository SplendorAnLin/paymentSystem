package com.yl.payinterface.core.handle.impl.remit.klt100004.single;

/**
 * 批量处理异常
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/3
 */
public class SinglepayResolveException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6685954789670374392L;

	/**
	 * 错误代码.
	 */
	private String errorCode;

	/**
	 * 错误信息.
	 */
	private String errorMsg;

	/**
	 * 
	 */
	public SinglepayResolveException() {
		super();
	}

	/**
	 * 自定义errorCode和errorMsg
	 * 
	 * @param errorCode
	 * @param errorMsg
	 * 
	 * @author Angi
	 */
	public SinglepayResolveException(String errorCode, String errorMsg) {
		super(errorMsg);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public SinglepayResolveException(String errorCode, String errorMsg, Throwable cause) {
		super(errorMsg, cause);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public SinglepayResolveException(String errorCode, String errorMsg, String message,
			Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SinglepayResolveException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public SinglepayResolveException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public SinglepayResolveException(Throwable cause) {
		super(cause);
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode
	 *            the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg
	 *            the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
