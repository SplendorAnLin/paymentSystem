package com.yl.payinterface.core.context;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yl.payinterface.core.handler.ReceiveHandler;

/**
 * 支付接口-代收相关配置
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月11日
 * @version V1.0.0
 */
@Configuration
public class ReceiveTradeConfig {
	

	
	@Resource
	private ReceiveHandler mcokReceiveHandler;
	
	@Bean
	public Map<String, ReceiveHandler> receiveHandlers() {
		Map<String, ReceiveHandler> tradeHandlers = new HashMap<>();
		tradeHandlers.put("MOCK-RECEIVE", mcokReceiveHandler);
		return tradeHandlers;
	}
}
