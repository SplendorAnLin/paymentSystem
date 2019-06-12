package com.yl.payinterface.core.handle.impl.remit.klt100004.resolve;

import com.thoughtworks.xstream.XStream;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.SinglepayResolveException;

/**
 * 自定义报文解析器扩展
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/3
 */
public interface ICustomizeResolver {

	void doCustomizeResolve(XStream xStream) throws SinglepayResolveException;

	String[] getSupportedVersionNoAndDirection();
}