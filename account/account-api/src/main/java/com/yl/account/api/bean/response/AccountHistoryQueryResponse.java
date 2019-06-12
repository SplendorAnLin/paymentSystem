/**
 * 
 */
package com.yl.account.api.bean.response;

import java.util.List;

import com.lefu.commons.utils.Page;
import com.yl.account.api.bean.AccountRecordedDetailBean;
import com.yl.account.api.bean.BussinessResponse;

/**
 * 账户历史响应信息
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月22日
 * @version V1.0.0
 */
public class AccountHistoryQueryResponse extends BussinessResponse {

	private static final long serialVersionUID = 6930717549866142797L;

	private Page<?> page;

	private List<AccountRecordedDetailBean> accountRecordedDetailBeans;

	public Page<?> getPage() {
		return page;
	}

	public void setPage(Page<?> page) {
		this.page = page;
	}

	public List<AccountRecordedDetailBean> getAccountRecordedDetailBeans() {
		return accountRecordedDetailBeans;
	}

	public void setAccountRecordedDetailBeans(List<AccountRecordedDetailBean> accountRecordedDetailBeans) {
		this.accountRecordedDetailBeans = accountRecordedDetailBeans;
	}

	@Override
	public String toString() {
		return "AccountHistoryQueryResponse [page=" + page + ", accountRecordedDetailBeans=" + accountRecordedDetailBeans + "]";
	}

}
