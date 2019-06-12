package com.yl.online.model.model;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 合作方查询请求信息
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月16日
 * @version V1.0.0
 */
public class PartnerQueryRequest extends AutoStringIDModel {

	private static final long serialVersionUID = 6786803609017857184L;
	/** 查询标示码 */
	@NotBlank
	private String queryCode;
	/** 字符集 */
	@NotBlank
	private String inputCharset;
	/** 合作方编号 */
	@NotBlank
	private String partner;
	/** 合作方订单编号 */
	@NotBlank
	private String outOrderId;
	/** 签名方式 */
	@NotBlank
	private String signType;
	/** 合作方签名串 */
	@NotBlank
	private String sign;
	/** 原始请求信息 */
	private String originalRequest;

	public String getQueryCode() {
		return queryCode;
	}

	public void setQueryCode(String queryCode) {
		this.queryCode = queryCode;
	}

	public String getInputCharset() {
		return inputCharset;
	}

	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getOutOrderId() {
		return outOrderId;
	}

	public void setOutOrderId(String outOrderId) {
		this.outOrderId = outOrderId;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getOriginalRequest() {
		return originalRequest;
	}

	public void setOriginalRequest(String originalRequest) {
		this.originalRequest = originalRequest;
	}

}
