package com.yl.payinterface.core.handler;

import java.util.Map;

import com.lefu.hessian.exception.BusinessException;
import com.yl.payinterface.core.bean.AuthRequestResponseBean;
import com.yl.payinterface.core.bean.TradeContext;
import com.yl.payinterface.core.model.InterfaceRequest;

public interface RealNameAuthHandler {
	/**
	 * 交易处理
	 * 
	 * @param tradeContext
	 *            交易上下文
	 * @throws BusinessException
	 *             业务异常
	 */
	Map<String, Object> trade(TradeContext tradeContext) throws BusinessException;

	/**
	 * 交易完成验签
	 * 
	 * @param receiveResponseBean
	 *            资金通道代收响应结果Bean
	 * @param tradeConfigs
	 *            交易配置
	 * @return Map<String, Object> 完成处理参数
	 * @throws BusinessException
	 */
	Map<String, Object> signatureVerify(AuthRequestResponseBean authRequestResponseBean, String tradeConfigs) throws BusinessException;

	/**
	 * 交易完成处理
	 * 
	 * @param completeParameters
	 *            处理参数
	 * @return InterfaceRequest 提供方接口请求记录
	 * @throws BusinessException
	 */
	InterfaceRequest complete(Map<String, Object> completeParameters) throws BusinessException;

	/**
	 * 交易查询
	 * 
	 * @param tradeContext
	 *            交易上下文
	 * @throws BusinessException
	 *             业务异常
	 */
	Object[] query(TradeContext tradeContext) throws BusinessException;

}
