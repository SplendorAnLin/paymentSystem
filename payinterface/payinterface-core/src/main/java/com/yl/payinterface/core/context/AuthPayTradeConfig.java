package com.yl.payinterface.core.context;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.yl.payinterface.core.handler.AuthPayHandler;

/**
 * 认证支付交易配置
 *
 * @author 聚合支付有限公司
 * @since 2016年11月11日
 * @version V1.0.0
 */
@Configuration
public class AuthPayTradeConfig {

    @Resource
    private AuthPayHandler klt100001Handler;

    @Resource
    private AuthPayHandler shand100001Handler;

    @Resource
    private AuthPayHandler yw440301Handler;

    @Bean
    public Map<String, AuthPayHandler> authPayHandlers() {
        Map<String, AuthPayHandler> tradeHandlers = new HashMap<>();
        tradeHandlers.put("AUTHPAY_KLT-100001", klt100001Handler);
        tradeHandlers.put("AUTHPAY_KLT-100002", klt100001Handler);
        tradeHandlers.put("AUTHPAY_KLT-100003", klt100001Handler);
        tradeHandlers.put("AUTHPAY_SHAND-100001", shand100001Handler);
        tradeHandlers.put("AUTHPAY_YW-440301", yw440301Handler);
        return tradeHandlers;
    }
}