package com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.impl.request;

import com.yl.payinterface.core.handle.impl.remit.klt100004.common.dto.impl.Sign;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.SingleAgentpayService;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.SinglepayResolveException;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.ISingle;

/**
 * 开联通
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/3
 */
public class Request implements ISingle {
	private RequestEnvelope envelope;

	private Sign sign = new Sign();

	public RequestEnvelope getEnvelope() {
		return envelope;
	}

	public void setEnvelope(RequestEnvelope envelope) {
		this.envelope = envelope;
	}

	public Sign getSign() {
		return sign;
	}

	public void setSign(Sign sign) {
		this.sign = sign;
	}
	public String toXML() throws SinglepayResolveException {
		return SingleAgentpayService.resolverAdapter.doRenderRequest(this,this.getEnvelope().getHead().getVersion());
	}
}