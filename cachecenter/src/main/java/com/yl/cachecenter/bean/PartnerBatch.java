package com.yl.cachecenter.bean;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yl.cachecenter.model.Partner;

/**
 * 批量商户
 * 
 * @author 聚合支付有限公司
 * @since 2017年4月28日
 * @version V1.0.0
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PartnerBatch {
	boolean isSuccess;

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	@NotNull
	@Size(max=500)
	List<Partner> partners;

	public List<Partner> getPartners() {
		return partners;
	}

	public void setPartners(List<Partner> partners) {
		this.partners = partners;
	}

}
