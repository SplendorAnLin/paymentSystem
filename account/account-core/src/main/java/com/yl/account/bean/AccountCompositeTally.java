/**
 * 
 */
package com.yl.account.bean;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 复合记账请求
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public class AccountCompositeTally extends TradeVoucher {

	private static final long serialVersionUID = -6994391533618748179L;

	/** 出入账实体 */
	@NotNull
	@Valid
	private List<TallyVoucher> tallyVouchers;

	public List<TallyVoucher> getTallyVouchers() {
		return tallyVouchers;
	}

	public void setTallyVouchers(List<TallyVoucher> tallyVouchers) {
		this.tallyVouchers = tallyVouchers;
	}

}
