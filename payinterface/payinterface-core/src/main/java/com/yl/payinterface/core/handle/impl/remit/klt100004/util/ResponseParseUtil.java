package com.yl.payinterface.core.handle.impl.remit.klt100004.util;

import com.thoughtworks.xstream.XStream;
import com.yl.payinterface.core.handle.impl.remit.klt100004.common.dto.IMessage;
import com.yl.payinterface.core.handle.impl.remit.klt100004.resolve.ICustomizeResolver;
import com.yl.payinterface.core.handle.impl.remit.klt100004.resolve.SingleAgentpayNotifyRequestCustomizeResolver;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.EBatchResponse;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.SinglepayResolveException;

/**
 * 开联通
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/3
 */
public class ResponseParseUtil {
	public static IMessage doParseResponse(String xmlString)throws SinglepayResolveException {

		XStream requestXstream = null;
		try {
			requestXstream = new XStream();

			ICustomizeResolver customerParser =new SingleAgentpayNotifyRequestCustomizeResolver();
			customerParser.doCustomizeResolve(requestXstream);

			return (IMessage) requestXstream.fromXML(xmlString);
		} catch (Exception e) {
			e.printStackTrace();
			// LoggerUtil.warn("将响应XML报文转换为对象时发生异常：" + e);
			throw new SinglepayResolveException(EBatchResponse.E0001.name(),
					EBatchResponse.E0001.getValue(), "将XML报文转换为对象时发生异常", e);
		}

	}
}
