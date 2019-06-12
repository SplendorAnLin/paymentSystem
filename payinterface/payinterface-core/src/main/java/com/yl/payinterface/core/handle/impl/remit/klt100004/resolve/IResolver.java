package com.yl.payinterface.core.handle.impl.remit.klt100004.resolve;

import java.util.List;
import com.yl.payinterface.core.handle.impl.remit.klt100004.common.dto.IMessage;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.SinglepayResolveException;

/**
 * 解析报文器，负责把XML报文解析为对象或把对象渲染为XML字符串
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/3
 */
public interface IResolver {

	public IResolver addCustomizeResolvers(
            List<ICustomizeResolver> customizeParser);

	/**
	 * 把请求XML报文解析为对象
	 * 
	 * @param xmlString
	 * @return
	 * @throws SinglepayResolveException
	 */
	IMessage doParseRequest(String xmlString, String version)
			throws SinglepayResolveException;

	/**
	 * 把响应XML报文解析为对象
	 * 
	 * @param xmlString
	 * @return
	 * @throws SinglepayResolveException
	 */
	IMessage doParseResponse(String xmlString, String version)
			throws SinglepayResolveException;

	/**
	 * 把请求对象渲染为XML字符串
	 * 
	 * @param object
	 * @return
	 * @throws SinglepayResolveException
	 */
	String doRenderRequest(Object object, String version)
			throws SinglepayResolveException;

	/**
	 * 把响应对象渲染为XML字符串
	 * 
	 * @param object
	 * @return
	 * @throws SinglepayResolveException
	 */
	String doRenderResponse(Object object, String version)
			throws SinglepayResolveException;
}