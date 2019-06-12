package com.yl.payinterface.core.handle.impl.remit.klt100004.resolve;

import com.thoughtworks.xstream.XStream;
import com.yl.payinterface.core.handle.impl.remit.klt100004.common.dto.impl.Head;
import com.yl.payinterface.core.handle.impl.remit.klt100004.common.dto.impl.Sign;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.PaymentVersionNo;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.SinglepayResolveException;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.impl.query.request.AccountBalanceQueryRequest;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.impl.query.request.AccountBalanceQueryRequestBody;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.impl.query.request.AccountBalanceQueryRequestEnvelope;

/**
 * 开联通
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/3
 */
public class AccountBalanceQueryRequestCustomizeResolver implements ICustomizeResolver {

	public void doCustomizeResolve(XStream xStream) throws SinglepayResolveException {
		xStream.alias("request", AccountBalanceQueryRequest.class);
		xStream.alias("envelope", AccountBalanceQueryRequestEnvelope.class);
		xStream.alias("head", Head.class);
		xStream.alias("body", AccountBalanceQueryRequestBody.class);
		xStream.alias("sign", Sign.class);

	}

	public String[] getSupportedVersionNoAndDirection() {
		return new String[] { PaymentVersionNo.V1078 + ResolverAdapter.DIRECTION_REQUEST };
	}
}