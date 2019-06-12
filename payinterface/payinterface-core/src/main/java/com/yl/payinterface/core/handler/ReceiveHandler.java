package com.yl.payinterface.core.handler;

import java.util.Map;

import com.lefu.hessian.exception.BusinessException;
import com.yl.payinterface.core.model.InterfaceRequest;


/**
 * 代收交易接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年09月22日
 * @version V1.0.0
 */
public interface ReceiveHandler {

	/**
	 * 交易处理
	 * @param tradeContext 交易上下文
	 * @throws BusinessException 业务异常
	 */
	Map<String,String> trade(Map<String, String> completeParameters);

	/**
	 * 交易完成处理
	 * @param completeParameters 处理参数
	 * @return InterfaceRequest 提供方接口请求记录
	 * @throws BusinessException
	 */
	InterfaceRequest complete(Map<String, String> completeParameters);

	/**
	 * 交易查询
	 * @param tradeContext 交易上下文
	 * @throws BusinessException 业务异常
	 */
	Map<String,String> query(Map<String, String> completeParameters);
	
	
	/**
	 * 送白名单
	 * @param tradeContext 交易上下文
	 * @throws BusinessException 业务异常
	 */
	Map<String,String> whiteList(Map<String, String> completeParameters);

}
