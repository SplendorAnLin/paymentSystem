package com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.impl.query.response;


import com.yl.payinterface.core.handle.impl.remit.klt100004.common.dto.impl.Sign;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.SingleAgentpayService;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.SinglepayResolveException;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.ISingle;

public class AccontBalanceQueryResponse implements ISingle {
	private AccountBalanceQueryResponseEnvelope envelope;

	private Sign sign = new Sign();

	public AccountBalanceQueryResponseEnvelope getEnvelope() {
		return envelope;
	}

	public void setEnvelope(AccountBalanceQueryResponseEnvelope envelope) {
		this.envelope = envelope;
	}

	public Sign getSign() {
		return sign;
	}

	public void setSign(Sign sign) {
		this.sign = sign;
	}
	
	public String toXML() throws SinglepayResolveException {
		return SingleAgentpayService.resolverAdapter.doRenderResponse(this,this.getEnvelope().getHead().getVersion());
	}
}