package com.yl.payinterface.core.handle.impl.remit.klt100004.resolve;

import com.thoughtworks.xstream.XStream;
import com.yl.payinterface.core.handle.impl.remit.klt100004.common.dto.impl.Head;
import com.yl.payinterface.core.handle.impl.remit.klt100004.common.dto.impl.Sign;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.PaymentVersionNo;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.SinglepayResolveException;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.IBody;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.impl.query.response.SingleAgentpayQueryResponseBody;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.impl.response.Response;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.impl.response.ResponseEnvelope;

/**
 * 开联通
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/3
 */
public class SingleAgentpayQueryResponseCustomizeResolver implements ICustomizeResolver {

	public void doCustomizeResolve(XStream xStream) throws SinglepayResolveException {
		xStream.alias("response", Response.class);
		xStream.alias("envelope", ResponseEnvelope.class);
		xStream.alias("head", Head.class);
		xStream.alias("body",IBody.class, SingleAgentpayQueryResponseBody.class);
		xStream.alias("sign", Sign.class);
	}

	public String[] getSupportedVersionNoAndDirection() {
		return new String[] { PaymentVersionNo.V1077 + ResolverAdapter.DIRECTION_RESPONSE };
	}
}