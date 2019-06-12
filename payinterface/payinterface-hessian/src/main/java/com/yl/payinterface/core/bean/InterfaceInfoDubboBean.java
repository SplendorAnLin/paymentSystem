package com.yl.payinterface.core.bean;

import java.io.Serializable;

import com.yl.payinterface.core.enums.InterfaceInfoStatusDubbo;
import com.yl.payinterface.core.enums.InterfaceTypeDubbo;

/**
 * 接口信息Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月24日
 * @version V1.0.0
 */
public class InterfaceInfoDubboBean implements Serializable {

	private static final long serialVersionUID = -1646748116499825649L;
	/** 编号 */
	private String code;
	/** 接口提供方信息 */
	private String provider;
	/** 名称 */
	private String name;
	/** 接口类型 */
	private InterfaceTypeDubbo type;
	/** 状态 */
	private InterfaceInfoStatusDubbo status;
	/** 描述 */
	private String description;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public InterfaceTypeDubbo getType() {
		return type;
	}

	public void setType(InterfaceTypeDubbo type) {
		this.type = type;
	}

	public InterfaceInfoStatusDubbo getStatus() {
		return status;
	}

	public void setStatus(InterfaceInfoStatusDubbo status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
