/**
 * 
 */
package com.yl.account.api.bean.response;

import java.util.List;

import com.lefu.commons.utils.Page;
import com.yl.account.api.bean.AccountBean;
import com.yl.account.api.bean.BussinessResponse;

/**
 * 账户信息查询响应
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月22日
 * @version V1.0.0
 */
public class AccountQueryResponse extends BussinessResponse {

	private static final long serialVersionUID = 2738343722378764817L;

	private Page<?> page;

	private List<AccountBean> accountBeans;

	public Page<?> getPage() {
		return page;
	}

	public void setPage(Page<?> page) {
		this.page = page;
	}

	public List<AccountBean> getAccountBeans() {
		return accountBeans;
	}

	public void setAccountBeans(List<AccountBean> accountBeans) {
		this.accountBeans = accountBeans;
	}

	@Override
	public String toString() {
		return "AccountQueryResponse [page=" + page + ", accountBeans=" + accountBeans + "]";
	}

}
