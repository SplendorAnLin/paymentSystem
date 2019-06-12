package com.yl.payinterface.core.handle.impl.remit.klt100004.resolve;

import com.thoughtworks.xstream.XStream;
import com.yl.payinterface.core.handle.impl.remit.klt100004.common.dto.impl.Head;
import com.yl.payinterface.core.handle.impl.remit.klt100004.common.dto.impl.Sign;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.PaymentVersionNo;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.SinglepayResolveException;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.IBody;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.impl.query.request.QueryRequest;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.impl.query.request.QueryRequestBody;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.impl.query.request.QueryRequestEnvelope;

/**
 * 开联通
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/3
 */
public class SingleAgentpayQueryRequestCustomizeResolver implements ICustomizeResolver {
	public void doCustomizeResolve(XStream xStream) throws SinglepayResolveException {
		xStream.alias("request", QueryRequest.class);
		xStream.alias("envelope", QueryRequestEnvelope.class);
		xStream.alias("head", Head.class);
		xStream.alias("body", IBody.class, QueryRequestBody.class);
		xStream.alias("sign", Sign.class);

	}

	public String[] getSupportedVersionNoAndDirection() {
		return new String[] { PaymentVersionNo.V1077 + ResolverAdapter.DIRECTION_REQUEST };
	}
}