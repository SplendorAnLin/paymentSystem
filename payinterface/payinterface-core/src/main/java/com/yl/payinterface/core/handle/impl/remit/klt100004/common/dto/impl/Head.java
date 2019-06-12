package com.yl.payinterface.core.handle.impl.remit.klt100004.common.dto.impl;

import com.yl.payinterface.core.handle.impl.remit.klt100004.common.dto.IHead;

/**
 * 开联通
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/3
 */
public class Head implements IHead {

	/**
	 * 版本号
	 */
	private String version;

	/**
	 * 字符集
	 */
	private String charset = "UTF-8";

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the charset
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * @param charset
	 *            the charset to set
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}
}