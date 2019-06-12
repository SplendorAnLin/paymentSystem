package com.yl.payinterface.core.context;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yl.payinterface.core.handler.ChannelRecionHandler;

/**
 * 对账配置
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月11日
 * @version V1.0.0
 */
@Configuration
public class ChannelRecionConfig {
	
	@Resource
	private ChannelRecionHandler cmbcHandler584001;
	
	@Resource
	private ChannelRecionHandler cmbcHandler584002;

	@Bean
	public Map<String, ChannelRecionHandler> channelRecionHandlers() {
		Map<String, ChannelRecionHandler> tradeHandlers = new HashMap<>();
		tradeHandlers.put("CMBC_584001-REMIT", cmbcHandler584001);
		tradeHandlers.put("CMBC_584002-REMIT", cmbcHandler584002);
		return tradeHandlers;
	}
}
