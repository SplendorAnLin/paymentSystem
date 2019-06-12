package com.yl.online.trade.context;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yl.online.model.enums.BusinessType;
import com.yl.online.trade.service.TradeHandler;

/**
 * 通用交易相关配置
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月24日
 * @version V1.0.0
 */
@Configuration
public class CommonTradeConfig {
	@Resource
	private TradeHandler salesTradeHandler;

	@Bean
	public Map<BusinessType, TradeHandler> tradeHandlers() {
		Map<BusinessType, TradeHandler> tradeHandlers = new HashMap<>();
		tradeHandlers.put(BusinessType.SAILS, salesTradeHandler);
		return tradeHandlers;
	}
}
