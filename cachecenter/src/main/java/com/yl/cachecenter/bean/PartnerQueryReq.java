package com.yl.cachecenter.bean;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 合伙人查询请求Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月16日
 * @version V1.0.0
 */
public class PartnerQueryReq {
	@NotNull
	@Size(min = 1, max = 500)
	private String[] partnerCodes;
	@NotNull
	private String[] fields;

	public String[] getPartnerCodes() {
		return partnerCodes;
	}

	public void setPartnerCodes(String[] partnerCodes) {
		this.partnerCodes = partnerCodes;
	}

	public String[] getFields() {
		return fields;
	}

	public void setFields(String[] fields) {
		this.fields = fields;
	}

}
