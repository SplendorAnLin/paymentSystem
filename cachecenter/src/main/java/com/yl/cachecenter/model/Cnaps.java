package com.yl.cachecenter.model;

import java.io.Serializable;

/**
 * 联行号
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public class Cnaps implements Serializable {
	private static final long serialVersionUID = 4155728512536154913L;
	/** 联行号 */
	private String code;
	/** 清算行号 */
	private String clearingBankCode;
	/** 行政区划码administrativeDivisionCode */
	private String adCode;
	/** 名称 */
	private String name;
	/** 清算行级别 */
	private int clearingBankLevel;
	/** 銀行编号 */
	private String providerCode;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getClearingBankCode() {
		return clearingBankCode;
	}

	public void setClearingBankCode(String clearingBankCode) {
		this.clearingBankCode = clearingBankCode;
	}

	public String getAdCode() {
		return adCode;
	}

	public void setAdCode(String adCode) {
		this.adCode = adCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getClearingBankLevel() {
		return clearingBankLevel;
	}

	public void setClearingBankLevel(int clearingBankLevel) {
		this.clearingBankLevel = clearingBankLevel;
	}

	public String getProviderCode() {
		return providerCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Cnaps [code=");
		builder.append(code);
		builder.append(", clearingBankCode=");
		builder.append(clearingBankCode);
		builder.append(", adCode=");
		builder.append(adCode);
		builder.append(", name=");
		builder.append(name);
		builder.append(", clearingBankLevel=");
		builder.append(clearingBankLevel);
		builder.append(", providerCode=");
		builder.append(providerCode);
		builder.append("]");
		return builder.toString();
	}

}
