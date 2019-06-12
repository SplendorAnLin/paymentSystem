package com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.impl.query.response;

import com.yl.payinterface.core.handle.impl.remit.klt100004.common.dto.impl.Head;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.IEnvelope;

/**
 * 开联通
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/3
 */
public class AccountBalanceQueryResponseEnvelope implements IEnvelope {
	private Head head = new Head();

	private AccountBalanceQueryResponseBody body;

	public Head getHead() {
		return head;
	}

	public void setHead(Head head) {
		this.head = head;
	}

	public AccountBalanceQueryResponseBody getBody() {
		return body;
	}

	public void setBody(AccountBalanceQueryResponseBody body) {
		this.body = body;
	}
}