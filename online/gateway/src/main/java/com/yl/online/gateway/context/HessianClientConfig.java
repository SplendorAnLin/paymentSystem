package com.yl.online.gateway.context;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import com.lefu.commons.hessian.Hessian2Input3020;
import com.lefu.commons.hessian.HessianProxyFactoryExtends;
import com.yl.boss.api.interfaces.AgentInterface;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.boss.api.interfaces.DeviceInterface;
import com.yl.boss.api.interfaces.PosInterface;
import com.yl.boss.api.interfaces.ProtocolInterface;
import com.yl.boss.api.interfaces.QRCodeInterface;
import com.yl.online.trade.hessian.OnlinePartnerRouterHessianService;
import com.yl.online.trade.hessian.OnlineTradeHessianService;
import com.yl.online.trade.hessian.OnlineTradeQueryHessianService;
import com.yl.payinterface.core.hessian.AuthPayHessianService;
import com.yl.payinterface.core.hessian.BindCardHessianService;
import com.yl.payinterface.core.hessian.BindCardInfoHessianService;
import com.yl.payinterface.core.hessian.InterfaceRequestHessianService;
import com.yl.payinterface.core.hessian.InternetbankHessianService;
import com.yl.payinterface.core.hessian.MobilePayHessianService;
import com.yl.payinterface.core.hessian.PayInterfaceHessianService;
import com.yl.payinterface.core.hessian.QuickPayHessianService;
import com.yl.payinterface.core.hessian.QuickPayOpenCardHessianService;

/**
 * Hessian客户端配置
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年9月2日
 */
@Configuration
@PropertySource("classpath:/serverHost.properties")
public class HessianClientConfig {
    @Value("${online-trade}")
    private String onlineTradeHost;
    @Value("${bossHost}")
    private String bossHost;
    @Value("${payinterfaceHost}")
    private String payinterfaceHost;

    @Bean
    public HessianProxyFactoryBean onlineTradeHessianService() {
        HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
        factoryBean.setServiceUrl(onlineTradeHost + "/hessian/onlineTradeHessianService");
        factoryBean.setServiceInterface(OnlineTradeHessianService.class);
        factoryBean.setOverloadEnabled(true);
        factoryBean.setChunkedPost(false);
        return factoryBean;
    }

    @Bean
    public HessianProxyFactoryBean onlineTradeQueryHessianService() {
        HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
        factoryBean.setServiceUrl(onlineTradeHost + "/hessian/onlineTradeQueryHessianService");
        factoryBean.setServiceInterface(OnlineTradeQueryHessianService.class);
        factoryBean.setOverloadEnabled(true);
        factoryBean.setChunkedPost(false);
        return factoryBean;
    }

    @Bean
    public HessianProxyFactoryBean onlinePartnerRouterHessianService() {
        HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
        factoryBean.setServiceUrl(onlineTradeHost + "/hessian/onlinePartnerRouterHessianService");
        factoryBean.setServiceInterface(OnlinePartnerRouterHessianService.class);
        factoryBean.setOverloadEnabled(true);
        factoryBean.setChunkedPost(false);
        return factoryBean;
    }

    @Bean
    public HessianProxyFactoryBean customerInterface() {
        HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
        factoryBean.setServiceUrl(bossHost + "/remote/customerInterface");
        factoryBean.setServiceInterface(CustomerInterface.class);
        factoryBean.setOverloadEnabled(true);
        factoryBean.setChunkedPost(false);
        HessianProxyFactoryExtends proxyFactory = new HessianProxyFactoryExtends();
        proxyFactory.setHessian2Input(Hessian2Input3020.class);
        factoryBean.setProxyFactory(proxyFactory);
        return factoryBean;
    }
    @Bean
    public HessianProxyFactoryBean agentInterface() {
    	HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
    	factoryBean.setServiceUrl(bossHost + "/remote/agentInterface");
    	factoryBean.setServiceInterface(AgentInterface.class);
    	factoryBean.setOverloadEnabled(true);
    	factoryBean.setChunkedPost(false);
    	HessianProxyFactoryExtends proxyFactory = new HessianProxyFactoryExtends();
    	proxyFactory.setHessian2Input(Hessian2Input3020.class);
    	factoryBean.setProxyFactory(proxyFactory);
    	return factoryBean;
    }

    @Bean
    public HessianProxyFactoryBean qRCodeInterface() {
        HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
        factoryBean.setServiceUrl(bossHost + "/remote/qRCodeInterface");
        factoryBean.setServiceInterface(QRCodeInterface.class);
        factoryBean.setOverloadEnabled(true);
        factoryBean.setChunkedPost(false);
        HessianProxyFactoryExtends proxyFactory = new HessianProxyFactoryExtends();
        proxyFactory.setHessian2Input(Hessian2Input3020.class);
        factoryBean.setProxyFactory(proxyFactory);
        return factoryBean;
    }

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
    public HessianProxyFactoryBean mobilePayHessianService() {
        HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
        factoryBean.setServiceUrl(payinterfaceHost + "/hessian/mobilePayHessianService");
        factoryBean.setServiceInterface(MobilePayHessianService.class);
        factoryBean.setOverloadEnabled(true);
        factoryBean.setChunkedPost(false);
        return factoryBean;
    }

    @Bean
    public HessianProxyFactoryBean authPayHessianService() {
        HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
        factoryBean.setServiceUrl(payinterfaceHost + "/hessian/authPayHessianService");
        factoryBean.setServiceInterface(AuthPayHessianService.class);
        factoryBean.setOverloadEnabled(true);
        factoryBean.setChunkedPost(false);
        return factoryBean;
    }

    @Bean
    public HessianProxyFactoryBean quickPayHessianService() {
        HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
        factoryBean.setServiceUrl(payinterfaceHost + "/hessian/quickPayHessianService");
        factoryBean.setServiceInterface(QuickPayHessianService.class);
        factoryBean.setOverloadEnabled(true);
        factoryBean.setChunkedPost(false);
        return factoryBean;
    }

    @Bean
    public HessianProxyFactoryBean internetbankHessianService() {
        HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
        factoryBean.setServiceUrl(payinterfaceHost + "/hessian/internetbankHessianService");
        factoryBean.setServiceInterface(InternetbankHessianService.class);
        factoryBean.setOverloadEnabled(true);
        factoryBean.setChunkedPost(false);
        return factoryBean;
    }


    @Bean
    public HessianProxyFactoryBean payInterfaceHessianService() {
        HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
        factoryBean.setServiceUrl(payinterfaceHost + "/hessian/payInterfaceHessianService");
        factoryBean.setServiceInterface(PayInterfaceHessianService.class);
        factoryBean.setOverloadEnabled(true);
        factoryBean.setChunkedPost(false);
        return factoryBean;
    }

    @Bean
    public HessianProxyFactoryBean interfaceRequestHessianService() {
        HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
        factoryBean.setServiceUrl(payinterfaceHost + "/hessian/interfaceRequestHessianService");
        factoryBean.setServiceInterface(InterfaceRequestHessianService.class);
        factoryBean.setOverloadEnabled(true);
        factoryBean.setChunkedPost(false);
        return factoryBean;
    }

    @Bean
    public HessianProxyFactoryBean bindCardHessianService() {
        HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
        factoryBean.setServiceUrl(payinterfaceHost + "/hessian/bindCardHessianService");
        factoryBean.setServiceInterface(BindCardHessianService.class);
        factoryBean.setOverloadEnabled(true);
        factoryBean.setChunkedPost(false);
        return factoryBean;
    }
    @Bean
    public HessianProxyFactoryBean bindCardInfoHessianService() {
    	HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
    	factoryBean.setServiceUrl(payinterfaceHost + "/hessian/bindCardInfoHessianService");
    	factoryBean.setServiceInterface(BindCardInfoHessianService.class);
    	factoryBean.setOverloadEnabled(true);
    	factoryBean.setChunkedPost(false);
    	return factoryBean;
    }
    @Bean
    public HessianProxyFactoryBean quickPayOpenCardHessianService() {
    	HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
    	factoryBean.setServiceUrl(payinterfaceHost + "/hessian/quickPayOpenCardHessianService");
    	factoryBean.setServiceInterface(QuickPayOpenCardHessianService.class);
    	factoryBean.setOverloadEnabled(true);
    	factoryBean.setChunkedPost(false);
    	return factoryBean;
    }

    @Bean
    public HessianProxyFactoryBean posInterface() {
        HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
        factoryBean.setServiceUrl(bossHost + "/remote/posInterface");
        factoryBean.setServiceInterface(PosInterface.class);
        factoryBean.setOverloadEnabled(true);
        factoryBean.setChunkedPost(false);
        HessianProxyFactoryExtends proxyFactory = new HessianProxyFactoryExtends();
        proxyFactory.setHessian2Input(Hessian2Input3020.class);
        factoryBean.setProxyFactory(proxyFactory);
        return factoryBean;
    }

}
