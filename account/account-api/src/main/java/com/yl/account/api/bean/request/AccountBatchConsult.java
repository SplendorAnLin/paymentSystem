/**
 * 
 */
package com.yl.account.api.bean.request;

import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 批量请款请求
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public class AccountBatchConsult extends TradeVoucher {

	private static final long serialVersionUID = 2850444649770575432L;

	@NotNull
	@Valid
	private Map<String, AccountConsult> accountConsults;

	public Map<String, AccountConsult> getAccountConsults() {
		return accountConsults;
	}

	public void setAccountConsults(Map<String, AccountConsult> accountConsults) {
		this.accountConsults = accountConsults;
	}

	@Override
	public String toString() {
		return "AccountBatchConsult [accountConsults=" + accountConsults + "]";
	}

}
