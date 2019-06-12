
package com.yl.dpay.hessian.beans;

/**
 * 代付运行时异常Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月15日
 * @version V1.0.0
 */
@SuppressWarnings("serial")
public class DpayRuntimeException extends RuntimeException {
	/**
	 * 非法操作（退款）
	 */
	public static final String INVALID_OPERATION_REFUND = "dpay_001";
	/**
	 * 非法操作（处理）
	 */
	public static final String INVALID_OPERATION_HANDLE = "dpay_002";
	/**
	 * 非法操作（失败）
	 */
	public static final String INVALID_OPERATION_FAIL = "dpay_003";
	/**
	 * 非法操作（请求退款）
	 */
	public static final String INVALID_OPERATION_REQUEST_REFUND = "dpay_004";
	/**
	 * 非法操作（确认退款）
	 */
	public static final String INVALID_OPERATION_CONFIRM_REFUND = "dpay_005";
	/**
	 * 非法操作（代付成功）
	 */
	public static final String INVALID_OPERATION_SUCCESS = "dpay_006";
	/**
	 * 非法操作（撤销退款）
	 */
	public static final String INVALID_OPERATION_CANCEL_REFUND = "dpay_007";
	/**
	 * 非法的请求号
	 */
	public static final String INVALID_REQUESTNO = "dpay_008";
	/**
	 * 非法金额
	 */
	public static final String INVALID_AMOUNT_AUDIT = "dpay_009";
	/**
	 * 产品不可用
	 */
	public static final String PRODUCT_DISABLE = "dpay_010";
	
	/**
	 * 账户异常
	 */
	public static final String ACCOUNT_ERROR = "dpay_011";
	
	/**
	 * 非法请求
	 */
	public static final String INVALID_REQUEST = "dpay_012";
	
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public DpayRuntimeException(String code,String message) {
		super(message);
		this.code = code;
	}
	
}
