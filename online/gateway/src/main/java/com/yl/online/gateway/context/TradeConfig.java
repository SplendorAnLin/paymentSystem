package com.yl.online.gateway.context;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yl.online.gateway.service.TradeHandler;

/**
 * 交易相关配置
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月2日
 * @version V1.0.0
 */
@Configuration
public class TradeConfig {
	@Resource
	private TradeHandler salesTradeHandler;

	@Bean
	public Map<String, TradeHandler> tradeHandlers() {
		Map<String, TradeHandler> tradeHandlers = new HashMap<>();
		tradeHandlers.put("YL-PAY", salesTradeHandler);
		return tradeHandlers;
	}
}
