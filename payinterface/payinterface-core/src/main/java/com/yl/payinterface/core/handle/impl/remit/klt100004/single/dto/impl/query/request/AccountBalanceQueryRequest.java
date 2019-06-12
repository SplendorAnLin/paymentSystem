package com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.impl.query.request;

import com.yl.payinterface.core.handle.impl.remit.klt100004.common.dto.impl.Sign;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.SingleAgentpayService;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.SinglepayResolveException;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.ISingle;

/**
 * 代付可用余额查询，包括账户余额和授信额度
 * @author cgb
 *
 */
public class AccountBalanceQueryRequest implements ISingle {
	private Sign sign = new Sign();
	private AccountBalanceQueryRequestEnvelope envelope;
	private String mchtIP;
	private String mchtUrl;
	
	public Sign getSign() {
		return sign;
	}
	public void setSign(Sign sign) {
		this.sign = sign;
	}
	public AccountBalanceQueryRequestEnvelope getEnvelope() {
		return envelope;
	}
	public void setEnvelope(AccountBalanceQueryRequestEnvelope envelope) {
		this.envelope = envelope;
	}

	public String getMchtIP() {
		return mchtIP;
	}

	public void setMchtIP(String mchtIP) {
		this.mchtIP = mchtIP;
	}

	public String getMchtUrl() {
		return mchtUrl;
	}

	public void setMchtUrl(String mchtUrl) {
		this.mchtUrl = mchtUrl;
	}
	public String toXML() throws SinglepayResolveException {
		return SingleAgentpayService.resolverAdapter.doRenderRequest(this,this.getEnvelope().getHead().getVersion());
	}
}
