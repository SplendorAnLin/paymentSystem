package com.yl.payinterface.core.context;

import com.yl.payinterface.core.handler.QuickPayHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 认证支付交易配置
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年11月11日
 */
@Configuration
public class QuickPayTradeConfig {

    @Resource
    private QuickPayHandler quickKingPass100001Handler;

    @Bean
    public Map<String, QuickPayHandler> quickPayHandlers() {
        Map<String, QuickPayHandler> tradeHandlers = new HashMap<>();
        tradeHandlers.put("QUICKPAY_KINGPASS-100001", quickKingPass100001Handler);
        return tradeHandlers;
    }
}