package com.yl.agent.exception;

/**
 * 操作员登录异常信息
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月11日
 * @version V1.0.0
 */
public class LoginException extends RuntimeException {

	public LoginException(String message) {
		super(message);
	}

	public LoginException(Throwable ex) {
		super(ex);
	}

	public LoginException(String message, Throwable ex) {
		super(message, ex);
	}

}
