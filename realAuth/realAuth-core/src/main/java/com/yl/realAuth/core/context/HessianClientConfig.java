package com.yl.realAuth.core.context;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import com.lefu.commons.hessian.Hessian2Input3020;
import com.lefu.commons.hessian.HessianProxyFactoryExtends;
import com.yl.account.api.dubbo.AccountInterface;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.payinterface.core.hessian.RealNameAuthHessianService;

/**
 * Hessian客户端配置
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月11日
 * @version V1.0.0
 */
@Configuration
@PropertySource("classpath:/serverHost.properties")
public class HessianClientConfig {
	@Value("${payinterface-core}")
	private String payinterfaceCoreHost;

	@Value("${accountHost}")
	private String accountHost;
	
	@Value("${bossHost}")
	private String bossHost;

	@Bean
	public HessianProxyFactoryBean realNameAuthHessianService() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(payinterfaceCoreHost + "/hessian/realNameAuthHessianService");
		factoryBean.setServiceInterface(RealNameAuthHessianService.class);
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
	public HessianProxyFactoryBean accountInterface() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(accountHost + "/remote/accountInterface");
		factoryBean.setServiceInterface(AccountInterface.class);
		factoryBean.setChunkedPost(false);
		return factoryBean;
	}

}
