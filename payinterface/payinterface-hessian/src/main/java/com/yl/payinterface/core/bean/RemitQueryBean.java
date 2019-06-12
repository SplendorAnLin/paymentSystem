package com.yl.payinterface.core.bean;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 付款查询Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月24日
 * @version V1.0.0
 */
public class RemitQueryBean implements Serializable {

	private static final long serialVersionUID = 8266160031208231511L;

	/** 业务请求的支付接口编号 */
	@NotBlank
	private String businessOrderID;

	public String getBusinessOrderID() {
		return businessOrderID;
	}

	public void setBusinessOrderID(String businessOrderID) {
		this.businessOrderID = businessOrderID;
	}

	@Override
	public String toString() {
		return "RemitQueryBean [businessOrderID=" + businessOrderID + "]";
	}

}
