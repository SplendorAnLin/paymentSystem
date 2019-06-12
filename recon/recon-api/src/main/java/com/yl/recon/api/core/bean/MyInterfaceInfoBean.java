package com.yl.recon.api.core.bean;

import java.io.Serializable;

/**
 * 聚合支付有限公司
 *
 * @author 邱健
 * @version V1.0.0
 * @create 2018年01月18
 * @desc 接口信息
 **/
public class MyInterfaceInfoBean implements Serializable{
	/**
	 * 接口编码
	 */
	private String code;
	/**
	 * 通道名称
	 */
	private String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
