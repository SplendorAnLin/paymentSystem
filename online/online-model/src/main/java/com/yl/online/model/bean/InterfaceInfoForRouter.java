package com.yl.online.model.bean;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 路由的接口相关信息Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月13日
 * @version V1.0.0
 */
public class InterfaceInfoForRouter implements Serializable {

	private static final long serialVersionUID = -4884840803149272313L;
	/** 接口编号 */
	@NotBlank
	private String interfaceCode;
	/** 权重或比例 */
	private Integer scale;

	public String getInterfaceCode() {
		return interfaceCode;
	}

	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}

	public Integer getScale() {
		return scale;
	}

	public void setScale(Integer scale) {
		this.scale = scale;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
