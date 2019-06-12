package com.yl.chat.context;

import com.lefu.commons.hessian.Hessian2Input3020;
import com.lefu.commons.hessian.HessianProxyFactoryExtends;
import com.yl.agent.api.interfaces.AgentOperInterface;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.boss.api.interfaces.DeviceInterface;
import com.yl.boss.api.interfaces.ProtocolInterface;
import com.yl.customer.api.interfaces.CustOperInterface;
import com.yl.dpay.hessian.service.DpayFacade;
import com.yl.online.trade.hessian.OnlineTradeQueryHessianService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

/**
 * Hessian客户端配置
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017年4月21日
 */
@Configuration
@PropertySource("classpath:/serverHost.properties")
public class HessianClientConfig {

    @Value("${customerHost}")
    private String customerHost;

    @Value("${agentHost}")
    private String agentHost;

    @Value("${onlineHost}")
    private String onlineHost;

    @Value("${dpayHost}")
    private String dpayHost;

    @Value("${bossHost}")
    private String bossHost;


    @Bean
    public HessianProxyFactoryBean protocolInterface() {
        HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
        factoryBean.setServiceUrl(bossHost + "/remote/protocolInterface");
        factoryBean.setServiceInterface(ProtocolInterface.class);
        factoryBean.setOverloadEnabled(true);
        factoryBean.setChunkedPost(false);
        HessianProxyFactoryExtends proxyFactory = new HessianProxyFactoryExtends();
        proxyFactory.setHessian2Input(Hessian2Input3020.class);
        factoryBean.setProxyFactory(proxyFactory);
        return factoryBean;
    }

    @Bean
    public HessianProxyFactoryBean dpayFacade() {
        HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
        factoryBean.setServiceUrl(dpayHost + "/remote/dpayFacade");
        factoryBean.setServiceInterface(DpayFacade.class);
        factoryBean.setOverloadEnabled(true);
        factoryBean.setChunkedPost(false);
        return factoryBean;
    }

    @Bean
    public HessianProxyFactoryBean onlineTradeQueryHessianService() {
        HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
        factoryBean.setServiceUrl(onlineHost + "/hessian/onlineTradeQueryHessianService");
        factoryBean.setServiceInterface(OnlineTradeQueryHessianService.class);
        factoryBean.setOverloadEnabled(true);
        factoryBean.setChunkedPost(false);
        return factoryBean;
    }

    @Bean
    public HessianProxyFactoryBean customerInterface() {
        HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
        factoryBean.setServiceUrl(bossHost + "/remote/customerInterface");
        factoryBean.setServiceInterface(CustomerInterface.class);
        factoryBean.setChunkedPost(false);
        HessianProxyFactoryExtends proxyFactory = new HessianProxyFactoryExtends();
        proxyFactory.setHessian2Input(Hessian2Input3020.class);
        factoryBean.setProxyFactory(proxyFactory);
        return factoryBean;
    }

    @Bean
    public HessianProxyFactoryBean deviceInterface() {
        HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
        factoryBean.setServiceUrl(bossHost + "/remote/deviceInterface");
        factoryBean.setServiceInterface(DeviceInterface.class);
        factoryBean.setOverloadEnabled(true);
        factoryBean.setChunkedPost(false);
        HessianProxyFactoryExtends proxyFactory = new HessianProxyFactoryExtends();
        proxyFactory.setHessian2Input(Hessian2Input3020.class);
        factoryBean.setProxyFactory(proxyFactory);
        return factoryBean;
    }

    @Bean
    public HessianProxyFactoryBean custOperInterface() {
        HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
        factoryBean.setServiceUrl(customerHost + "/remote/custOperInterface");
        factoryBean.setServiceInterface(CustOperInterface.class);
        factoryBean.setOverloadEnabled(true);
        factoryBean.setChunkedPost(false);
        HessianProxyFactoryExtends proxyFactory = new HessianProxyFactoryExtends();
        proxyFactory.setHessian2Input(Hessian2Input3020.class);
        factoryBean.setProxyFactory(proxyFactory);
        return factoryBean;
    }

    @Bean
    public HessianProxyFactoryBean agentOperInterface() {
        HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
        factoryBean.setServiceUrl(agentHost + "/remote/agentOperInterface");
        factoryBean.setServiceInterface(AgentOperInterface.class);
        factoryBean.setOverloadEnabled(true);
        factoryBean.setChunkedPost(false);
        HessianProxyFactoryExtends proxyFactory = new HessianProxyFactoryExtends();
        proxyFactory.setHessian2Input(Hessian2Input3020.class);
        factoryBean.setProxyFactory(proxyFactory);
        return factoryBean;
    }
}