package com.yl.account.api.bean.response;

import java.util.List;

import com.lefu.commons.utils.Page;
import com.yl.account.api.bean.BussinessResponse;

/**
 * ������ϸ��ѯ��Ӧ
 * 
 * @author ����֧�����޹�˾
 * @since 2016��5��21��
 * @version V1.0.0
 */
public class AccountFreezeQueryResponse extends BussinessResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = -212322190985740522L;
	
	private Page<?> page;
	
	private List<AccountFreezeDetailResponse> accountFreezeDetailResponseBeans;
	
	
	public Page<?> getPage() {
		return page;
	}


	public void setPage(Page<?> page) {
		this.page = page;
	}


	public List<AccountFreezeDetailResponse> getAccountFreezeDetailResponseBeans() {
		return accountFreezeDetailResponseBeans;
	}


	public void setAccountFreezeDetailResponseBeans(List<AccountFreezeDetailResponse> accountFreezeDetailResponseBeans) {
		this.accountFreezeDetailResponseBeans = accountFreezeDetailResponseBeans;
	}


	@Override
	public String toString() {
		return "AccountFreezeDetailResponse [page=" + page + ", accountFreezeDetailResponseBeans=" + accountFreezeDetailResponseBeans + "]";
	}
}