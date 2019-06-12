package com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.impl.response;

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
public class Response  implements ISingle {
	private ResponseEnvelope envelope;

	private Sign sign = new Sign();

	public Sign getSign() {
		return sign;
	}

	public void setSign(Sign sign) {
		this.sign = sign;
	}

	public ResponseEnvelope getEnvelope() {
		return envelope;
	}

	public void setEnvelope(ResponseEnvelope envelope) {
		this.envelope = envelope;
	}
	
	public String toXML() throws SinglepayResolveException {
		return SingleAgentpayService.resolverAdapter.doRenderResponse(this,this.getEnvelope().getHead().getVersion());
	}
}