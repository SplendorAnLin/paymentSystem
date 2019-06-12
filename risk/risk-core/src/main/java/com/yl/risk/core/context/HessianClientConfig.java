package com.yl.risk.core.context;

import com.yl.online.trade.hessian.OnlineTradeHessianService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

/**
 * Hessian 客户端配置
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/11/16
 */
@Configuration
@PropertySource("classpath:/system.properties")
public class HessianClientConfig {

    @Value("${online-trade}")
    private String onlineTradeHost;

    @Bean
    public HessianProxyFactoryBean onlineTradeHessianService() {
        HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
        factoryBean.setServiceUrl(onlineTradeHost + "/hessian/onlineTradeHessianService");
        factoryBean.setServiceInterface(OnlineTradeHessianService.class);
        factoryBean.setOverloadEnabled(true);
        factoryBean.setChunkedPost(false);
        return factoryBean;
    }
}