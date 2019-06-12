package com.yl.receive.core.context;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import com.lefu.commons.hessian.Hessian2Input3020;
import com.lefu.commons.hessian.HessianProxyFactoryExtends;
import com.yl.account.api.dubbo.AccountInterface;
import com.yl.boss.api.interfaces.AgentInterface;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.boss.api.interfaces.ShareProfitInterface;
import com.yl.payinterface.core.hessian.ReceiveHessianService;

/**
 * Hessian客户端配置
 *
 * @author 聚合支付有限公司
 * @since 2016年8月25日
 * @version V1.0.0
 */
@Configuration
@PropertySource("classpath:/serverHost.properties")
public class HessianClientConfig {
	@Value("${payinterfaceHost}")
	private String payinterfaceHost;
	@Value("${accountHost}")
	private String accountHost;
	@Value("${bossHost}")
	private String bossHost;


	@Bean
	public HessianProxyFactoryBean receiveHessianService() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(payinterfaceHost + "/hessian/receiveHessianService");
		factoryBean.setServiceInterface(ReceiveHessianService.class);
		factoryBean.setChunkedPost(false);
		return factoryBean;
	}
	@Bean
	public HessianProxyFactoryBean accountInterface() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(accountHost + "/remote/accountInterface");
		factoryBean.setServiceInterface(AccountInterface.class);
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
	public HessianProxyFactoryBean agentInterface() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(bossHost + "/remote/agentInterface");
		factoryBean.setServiceInterface(AgentInterface.class);
		factoryBean.setChunkedPost(false);
		HessianProxyFactoryExtends proxyFactory = new HessianProxyFactoryExtends();
		proxyFactory.setHessian2Input(Hessian2Input3020.class);
		factoryBean.setProxyFactory(proxyFactory);
		proxyFactory.setOverloadEnabled(true);
		return factoryBean;
	}
	@Bean
	public HessianProxyFactoryBean shareProfitInterface() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(bossHost + "/remote/shareProfitInterface");
		factoryBean.setServiceInterface(ShareProfitInterface.class);
		factoryBean.setChunkedPost(false);
		HessianProxyFactoryExtends proxyFactory = new HessianProxyFactoryExtends();
		proxyFactory.setHessian2Input(Hessian2Input3020.class);
		factoryBean.setProxyFactory(proxyFactory);
		proxyFactory.setOverloadEnabled(true);
		return factoryBean;
	}

}
