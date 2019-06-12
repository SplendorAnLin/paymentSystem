package com.yl.risk.core.context;

import com.yl.risk.core.handler.RiskHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 风控 Handler
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/11/13
 */
@Configuration
public class RiskConfig {

    @Resource
    private RiskHandler riskHandlerScript;

    @Resource
    private RiskHandler riskHandlerSql;

    @Resource
    private RiskHandler riskHandlerConfig;

    @Bean
    public Map<String, RiskHandler> riskHandlers(){
        Map<String, RiskHandler> riskHandlers = new HashMap<>();
        riskHandlers.put("riskHandlerSql", riskHandlerSql);
        riskHandlers.put("riskHandlerScript", riskHandlerScript);
        riskHandlers.put("riskHandlerConfig", riskHandlerConfig);
        return riskHandlers;
    }
}