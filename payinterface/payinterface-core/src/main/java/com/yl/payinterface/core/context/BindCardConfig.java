package com.yl.payinterface.core.context;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.yl.payinterface.core.handler.BindCardHandler;

/**
 * 快捷绑卡配置
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月11日
 * @version V1.0.0
 */
@Configuration
public class BindCardConfig {

    @Resource
    private BindCardHandler quickKingPass100001Handler;

	@Bean
	public Map<String, BindCardHandler> bindCardHandlers() {
		Map<String, BindCardHandler> tradeHandlers = new HashMap<>();
		tradeHandlers.put("QUICKPAY_KINGPASS-100001", quickKingPass100001Handler);
		return tradeHandlers;
	}
}