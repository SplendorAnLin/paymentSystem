package com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto;

import com.yl.payinterface.core.handle.impl.remit.klt100004.common.dto.IMessage;
import com.yl.payinterface.core.handle.impl.remit.klt100004.common.dto.ISign;

/**
 * 开联通
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/3
 */
public interface ISingle extends IMessage {

	public static String ENCODING = "UTF-8";
	
	IEnvelope getEnvelope();

	ISign getSign();
}