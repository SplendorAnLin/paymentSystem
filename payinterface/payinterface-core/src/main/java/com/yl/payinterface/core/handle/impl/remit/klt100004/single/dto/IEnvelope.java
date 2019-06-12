package com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto;

import com.yl.payinterface.core.handle.impl.remit.klt100004.common.dto.IHead;

/**
 * 开联通
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/3
 */
public interface IEnvelope {

	IHead getHead();

	IBody getBody();
}