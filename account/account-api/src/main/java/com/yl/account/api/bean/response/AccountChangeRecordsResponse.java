/**
 * 
 */
package com.yl.account.api.bean.response;

import java.util.List;

import com.lefu.commons.utils.Page;
import com.yl.account.api.bean.AccountChangeDetailBean;
import com.yl.account.api.bean.BussinessResponse;

/**
 * 变更响应信息
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月14日
 * @version V1.0.0
 */
public class AccountChangeRecordsResponse extends BussinessResponse {

	private static final long serialVersionUID = -2497281953522895862L;

	private List<AccountChangeDetailBean> accountChangeDetailBeans;

	private Page<?> page;

	public List<AccountChangeDetailBean> getAccountChangeDetailBeans() {
		return accountChangeDetailBeans;
	}

	public void setAccountChangeDetailBeans(List<AccountChangeDetailBean> accountChangeDetailBeans) {
		this.accountChangeDetailBeans = accountChangeDetailBeans;
	}

	public Page<?> getPage() {
		return page;
	}

	public void setPage(Page<?> page) {
		this.page = page;
	}

	@Override
	public String toString() {
		return "AccountChangeRecordsResponse [accountChangeDetailBeans=" + accountChangeDetailBeans + ", page=" + page + "]";
	}

}
