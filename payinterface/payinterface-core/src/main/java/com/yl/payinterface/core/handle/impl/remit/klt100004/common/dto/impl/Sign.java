package com.yl.payinterface.core.handle.impl.remit.klt100004.common.dto.impl;

import com.yl.payinterface.core.handle.impl.remit.klt100004.common.dto.ISign;

/**
 * 开联通
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/3
 */
public class Sign implements ISign {

	/**
	 * 验签方式
	 */
	private String signType = "1";

	/**
	 * 证书
	 */
	private String certificate = "";

	/**
	 * 签名
	 */
	private String signContent;

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSignContent() {
		return signContent;
	}

	public void setSignContent(String signContent) {
		this.signContent = signContent;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

}