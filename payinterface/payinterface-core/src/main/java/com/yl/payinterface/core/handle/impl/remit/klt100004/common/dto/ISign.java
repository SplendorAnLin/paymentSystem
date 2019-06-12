package com.yl.payinterface.core.handle.impl.remit.klt100004.common.dto;

/**
 * 开联通
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/3
 */
public interface ISign {

	String getSignType();

	String getCertificate();

	String getSignContent();

	void setSignContent(String signContent);

	void setCertificate(String certificate);

}