package com.yl.realAuth.front.context;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import com.lefu.commons.hessian.Hessian2Input3020;
import com.lefu.commons.hessian.HessianProxyFactoryExtends;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.realAuth.hessian.AuthConfigHessianService;
import com.yl.realAuth.hessian.AuthTradeHessianService;

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
	@Value("${auth-core}")
	private String authCoreHost;
	@Value("${bossHost}")
	private String bossHost;

	@Bean
	public HessianProxyFactoryBean authTradeHessianService() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(authCoreHost + "/hessian/authTradeHessianService");
		factoryBean.setServiceInterface(AuthTradeHessianService.class);
		factoryBean.setChunkedPost(false);
		factoryBean.setOverloadEnabled(true);
		return factoryBean;
	}

	@Bean
	public HessianProxyFactoryBean authConfigHessianService() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(authCoreHost + "/hessian/authConfigHessianService");
		factoryBean.setServiceInterface(AuthConfigHessianService.class);
		factoryBean.setChunkedPost(false);
		factoryBean.setOverloadEnabled(true);
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
}
