package com.yl.online.trade.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yl.online.model.bean.TradeContext;
import com.yl.online.model.enums.BusinessType;
import com.yl.online.trade.exception.BusinessException;
import com.yl.online.trade.service.SalesTradeCompleteHandler;
import com.yl.online.trade.service.TradeHandler;

/**
 * 交易完成处理服务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
@Service("salesTradeCompleteHandler")
public class SalesTradeCompleteHandlerImpl implements SalesTradeCompleteHandler {
	
	@Resource
	private Map<BusinessType, TradeHandler> tradeHandlers;
	
	@Override
	public void complete(Map<String, String> requestParameters) {
		TradeHandler tradeHandler = tradeHandlers.get(BusinessType.valueOf(requestParameters.get("businessType")));
		TradeContext tradeContext = new TradeContext();
		tradeContext.setRequestParameters(requestParameters);
		try {
			tradeHandler.complete(tradeContext);
		} catch (BusinessException e) {
			throw new RuntimeException(e);
		}
	}

}
