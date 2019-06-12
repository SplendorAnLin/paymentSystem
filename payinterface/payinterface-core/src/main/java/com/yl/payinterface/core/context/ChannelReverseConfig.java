package com.yl.payinterface.core.context;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yl.payinterface.core.handler.ChannelReverseHandler;

/**
 * 补单配置
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月11日
 * @version V1.0.0
 */
@Configuration
public class ChannelReverseConfig {



	@Bean
	public Map<String, ChannelReverseHandler> channelReverseHandlers() {
		Map<String, ChannelReverseHandler> tradeHandlers = new HashMap<>();
		return tradeHandlers;
	}
}
