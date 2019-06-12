package com.yl.payinterface.core.context;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yl.payinterface.core.handler.RealNameAuthHandler;

/**
 * 实名认证交易配置
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月11日
 * @version V1.0.0
 */
@Configuration
public class RealAuthTradeConfig {
	
	@Resource
	private RealNameAuthHandler realAuthJDHandler;
	@Resource
	private RealNameAuthHandler REALAUTH_YLZF_110001;
	@Resource
	private RealNameAuthHandler realAuthALiPayHandler;
	@Resource
	private RealNameAuthHandler realAuthALiPay330002Handler;

	@Bean
	public Map<String, RealNameAuthHandler> realNameAuthHandlers() {
		Map<String, RealNameAuthHandler> tradeHandlers = new HashMap<>();
		tradeHandlers.put("REALAUTH_YLZF_110001", REALAUTH_YLZF_110001);
		tradeHandlers.put("REALAUTH-JD_100000", realAuthJDHandler);
		tradeHandlers.put("REALAUTH_ALIPAY_330001", realAuthALiPayHandler);
		tradeHandlers.put("REALAUTH_ALIPAY_330002", realAuthALiPay330002Handler);
		return tradeHandlers;
	}
}
