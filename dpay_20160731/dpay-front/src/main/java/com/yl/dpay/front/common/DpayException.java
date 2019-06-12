package com.yl.dpay.front.common;

/**
 * 自定义异常类
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月21日
 * @version V1.0.0
 */
public class DpayException extends RuntimeException {

	private static final long serialVersionUID = 5795181183523697144L;
	private String code;
	private String message;
	private String businessId;
	private Exception e;

	public DpayException(String code) {
		super(code);
		this.code = code;
		this.message = code;
	}

	public DpayException(String code, String message) {
		super(code);
		this.code = code;
		this.message = message;
	}

	public DpayException(String code, String message, Exception e) {
		super(code);
		this.code = code;
		this.message = message;
		this.e=e;
	}

	public DpayException(String code, String message, String businessId) {
		super(code);
		this.code = code;
		this.message = message;
		this.businessId = businessId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public Exception getE() {
		return e;
	}

	public void setE(Exception e) {
		this.e = e;
	}

}
