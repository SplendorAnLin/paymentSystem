package com.yl.payinterface.core.handler;

import java.util.Map;
import java.util.Properties;

import com.lefu.hessian.exception.BusinessException;
import com.yl.payinterface.core.bean.InternetbankSalesResponseBean;
import com.yl.payinterface.core.bean.TradeContext;
import com.yl.payinterface.core.model.InterfaceRequest;

/**
 * 网银交易接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年09月22日
 * @version V1.0.0
 */
public interface InternetbankHandler {

	/**
	 * 交易处理
	 * @param tradeContext 交易上下文
	 * @throws BusinessException 业务异常
	 */
	Object[] trade(TradeContext tradeContext) throws BusinessException;
	
	/**
	 * 交易完成验签
	 * @param internetbankSalesResponseBean 资金通道网银消费响应结果Bean
	 * @param tradeConfigs 交易配置
	 * @return Map<String, Object> 完成处理参数
	 */
	Map<String, Object> signatureVerify(InternetbankSalesResponseBean internetbankSalesResponseBean, Properties tradeConfigs);
	
	/**
	 * 交易完成处理
	 * @param completeParameters 处理参数
	 * @return InterfaceRequest 提供方接口请求记录
	 * @throws BusinessException
	 */
	InterfaceRequest complete(Map<String, Object> completeParameters) throws BusinessException;

	/**
	 * 交易查询
	 * @param tradeContext 交易上下文
	 * @throws BusinessException 业务异常
	 */
	Object[] query(TradeContext tradeContext) throws BusinessException;
}
