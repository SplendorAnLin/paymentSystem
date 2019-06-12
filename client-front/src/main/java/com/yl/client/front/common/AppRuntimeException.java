package com.yl.client.front.common;

/**
 * App运行时间异常
 * 
 * @author 聚合支付有限公司
 * @since 2017年4月24日
 * @version V1.0.0
 */
@SuppressWarnings("serial")
public class AppRuntimeException extends Exception{
	
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public AppRuntimeException(String code,String message) {
		super(message);
		this.code = code;
	}
}
