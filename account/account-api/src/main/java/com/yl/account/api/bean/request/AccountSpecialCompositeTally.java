/**
 * 
 */
package com.yl.account.api.bean.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 复合记账请求
 * 
 * @author 聚合支付有限公司
 * @since 2016年2月24日
 * @version V1.0.0
 */
public class AccountSpecialCompositeTally extends TradeVoucher {

	private static final long serialVersionUID = -6994391533618748179L;

	/** 出入账实体 */
	@NotNull
	@Valid
	private List<SpecialTallyVoucher> specialTallyVouchers;

	public List<SpecialTallyVoucher> getSpecialTallyVouchers() {
		return specialTallyVouchers;
	}

	public void setSpecialTallyVouchers(List<SpecialTallyVoucher> specialTallyVouchers) {
		this.specialTallyVouchers = specialTallyVouchers;
	}

}
