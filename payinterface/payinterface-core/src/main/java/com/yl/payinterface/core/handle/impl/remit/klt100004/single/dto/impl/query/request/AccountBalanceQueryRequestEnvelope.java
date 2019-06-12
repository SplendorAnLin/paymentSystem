package com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.impl.query.request;

import com.yl.payinterface.core.handle.impl.remit.klt100004.common.dto.impl.Head;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.IEnvelope;

/**
 * 开联通
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/3
 */
public class AccountBalanceQueryRequestEnvelope implements IEnvelope {
	private Head head = new Head();
	private AccountBalanceQueryRequestBody body;
	public Head getHead() {
		return head;
	}
	public void setHead(Head head) {
		this.head = head;
	}
	public AccountBalanceQueryRequestBody getBody() {
		return body;
	}
	public void setBody(AccountBalanceQueryRequestBody body) {
		this.body = body;
	}
}