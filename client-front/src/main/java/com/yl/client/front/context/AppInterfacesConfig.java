package com.yl.client.front.context;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yl.client.front.handler.AppHandler;

/**
 * App接口配置
 * 
 * @author 聚合支付有限公司
 * @since 2017年4月24日
 * @version V1.0.0
 */
@Configuration
public class AppInterfacesConfig {
	
	@Resource
	private AppHandler customerHandLer;
	@Resource
	private AppHandler accountHandLer;
//	@Resource
//	private AppHandler agentHandLer;
	@Resource
	private AppHandler bossHandLer;
	@Resource
	private AppHandler dpayHandLer;
	@Resource
	private AppHandler onlineHandLer;
	@Resource
	private AppHandler oneCustomerMultiCodeHandler;
	
	@Bean
	public Map<String,AppHandler> appHandlers(){
		Map<String,AppHandler> appHandlers= new HashMap<>();
		appHandlers.put("customer",customerHandLer);
		appHandlers.put("account",accountHandLer);
//		appHandlers.put("agent",agentHandLer);
		appHandlers.put("boss",bossHandLer);
		appHandlers.put("dpay",dpayHandLer);
		appHandlers.put("online",onlineHandLer);
		appHandlers.put("oneCustomerMultiCode",oneCustomerMultiCodeHandler);
		return appHandlers;
	}
}
