package com.yl.payinterface.core.handle.impl.remit.klt100004.resolve;

import com.thoughtworks.xstream.XStream;
import com.yl.payinterface.core.handle.impl.remit.klt100004.common.dto.impl.Head;
import com.yl.payinterface.core.handle.impl.remit.klt100004.common.dto.impl.Sign;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.PaymentVersionNo;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.SinglepayResolveException;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.IBody;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.impl.request.Request;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.impl.request.RequestEnvelope;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.impl.request.agentPay.SingleAgentpayRequestBody;

/**
 * 开联通
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/3
 */
public class SingleAgentpayRequestCustomizeResolver implements ICustomizeResolver {

	public void doCustomizeResolve(XStream xStream) throws SinglepayResolveException {
		xStream.alias("request", Request.class);
		xStream.alias("envelope", RequestEnvelope.class);
		xStream.alias("head", Head.class);
		xStream.alias("body", IBody.class,SingleAgentpayRequestBody.class);
		xStream.alias("sign", Sign.class);

	}

	public String[] getSupportedVersionNoAndDirection() {
		return new String[] { PaymentVersionNo.V1076 + ResolverAdapter.DIRECTION_REQUEST };
	}
}