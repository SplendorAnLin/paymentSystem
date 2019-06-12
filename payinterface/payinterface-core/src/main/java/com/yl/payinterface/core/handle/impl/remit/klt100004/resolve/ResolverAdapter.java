package com.yl.payinterface.core.handle.impl.remit.klt100004.resolve;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import com.yl.payinterface.core.handle.impl.remit.klt100004.common.dto.IMessage;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.EBatchResponse;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.SinglepayResolveException;

/**
 * 报文解析器适配器
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/3
 */
public class ResolverAdapter implements IResolver {

	/**
	 * 数据流向之请求
	 */
	public static final String DIRECTION_REQUEST = "REQUEST";

	/**
	 * 数据流向之响应
	 */
	public static final String DIRECTION_RESPONSE = "RESPONSE";

	/**
	 * key为支付报文的版本号. value为对应版本号的自定义报文解析器扩展 .
	 */
	private Map<String, ICustomizeResolver> customizeResolvers = new HashMap<String, ICustomizeResolver>();

	public IResolver addCustomizeResolvers(
			List<ICustomizeResolver> customizeParser) {
		Iterator<ICustomizeResolver> it = customizeParser.iterator();
		while (it.hasNext()) {
			ICustomizeResolver validator = it.next();
			String[] v = validator.getSupportedVersionNoAndDirection();
			int len = v.length;
			for (int i = 0; i < len; i++) {
				customizeResolvers.put(v[i], validator);
			}
		}
		return this;
	}

	public IMessage doParseRequest(String xmlString,String version) throws SinglepayResolveException {

		XStream requestXstream = null;
		try {
			requestXstream = new XStream();

			ICustomizeResolver customerParser = customizeResolvers.get(version + DIRECTION_REQUEST);
			if (null != customerParser) {
				customerParser.doCustomizeResolve(requestXstream);
			}

			return (IMessage) requestXstream.fromXML(xmlString);
		} catch (Exception e) {
			// LoggerUtil.warn("将请求XML报文转换为对象时发生异常：" + e);
			throw new SinglepayResolveException(EBatchResponse.E0001.name(),
					EBatchResponse.E0001.getValue(), "将请求XML报文转换为对象时发生异常", e);
		}

	}

	public IMessage doParseResponse(String xmlString,String version)throws SinglepayResolveException {

		XStream requestXstream = null;
		try {
			requestXstream = new XStream();

			ICustomizeResolver customerParser = customizeResolvers.get(version+ DIRECTION_RESPONSE);
			if (null != customerParser) {
				customerParser.doCustomizeResolve(requestXstream);
			}

			return (IMessage) requestXstream.fromXML(xmlString);
		} catch (Exception e) {
			e.printStackTrace();
			// LoggerUtil.warn("将响应XML报文转换为对象时发生异常：" + e);
			throw new SinglepayResolveException(EBatchResponse.E0001.name(),
					EBatchResponse.E0001.getValue(), "将响应XML报文转换为对象时发生异常", e);
		}

	}

	public String doRenderRequest(Object object,String version) throws SinglepayResolveException {

		XStream responseXstream = null;
		try {
			responseXstream = new XStream();

			ICustomizeResolver customizeRenderer = customizeResolvers.get(version	+ DIRECTION_REQUEST);
			if (null != customizeRenderer) {
				customizeRenderer.doCustomizeResolve(responseXstream);
			}

			Writer writer = new StringWriter();
			responseXstream.marshal(object, new CompactWriter(writer));
			return writer.toString();
		} catch (Exception e) {
			// LoggerUtil.warn("将请求对象转换为XML报文时发生异常：" + e);
			throw new SinglepayResolveException(EBatchResponse.E0001.name(),
					EBatchResponse.E0001.getValue(), "将请求对象转换为XML报文时发生异常", e);
		}

	}

	public String doRenderResponse(Object object,String version) throws SinglepayResolveException {

		XStream responseXstream = null;
		try {
			responseXstream = new XStream();

			ICustomizeResolver customizeRenderer = customizeResolvers.get(version	+ DIRECTION_RESPONSE);
			if (null != customizeRenderer) {
				customizeRenderer.doCustomizeResolve(responseXstream);
			}

			Writer writer = new StringWriter();
			responseXstream.marshal(object, new CompactWriter(writer));
			return writer.toString();
		} catch (Exception e) {
			throw new SinglepayResolveException(EBatchResponse.E0001.name(),
					EBatchResponse.E0001.getValue(), "将响应对象转换为XML报文时发生异常", e);
		}
	}
}