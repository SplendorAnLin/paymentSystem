package com.yl.online.gateway.context;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yl.online.gateway.service.QueryHandler;

/**
 * 查询交易配置
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月2日
 * @version V1.0.0
 */
@Configuration
public class QueryConfig {

	@Resource
	private QueryHandler queryHandler;
	
	@Bean
	public Map<String, QueryHandler> queryHandlers() {
		Map<String, QueryHandler> queryHandlers = new HashMap<>();
		/** 即时单笔查询接口 */
		queryHandlers.put("YL-QUERY", queryHandler);
		return queryHandlers;
	}
}
