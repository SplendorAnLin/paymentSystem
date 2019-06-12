package com.yl.online.trade.hessian.bean;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotBlank;

import com.lefu.hessian.bean.JsonBean;

/**
 * 路由接口相关信息Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
public class InterfaceInfoForRouterBean implements JsonBean {

	private static final long serialVersionUID = 6818379827712672097L;
	
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
