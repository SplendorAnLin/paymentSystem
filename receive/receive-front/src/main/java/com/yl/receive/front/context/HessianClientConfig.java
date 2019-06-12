package com.yl.receive.front.context;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import com.lefu.commons.hessian.Hessian2Input3020;
import com.lefu.commons.hessian.HessianProxyFactoryExtends;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.receive.hessian.CustomerReceiveConfigHessian;
import com.yl.receive.hessian.ReceiveQueryFacade;
import com.yl.receive.hessian.ReceiveTradeHessian;

/**
 * Hessian客户端配置
 *
 * @author 聚合支付有限公司
 * @since 2016年8月12日
 * @version V1.0.0
 */
@Configuration
@PropertySource("classpath:/serverHost.properties")
public class HessianClientConfig {
	@Value("${receiveHost}")
	private String receiveHost;

	@Value("${bossHost}")
	private String bossHost;

	@Bean
	public HessianProxyFactoryBean receiveQueryFacade() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(receiveHost + "/remote/receiveQueryFacade");
		factoryBean.setServiceInterface(ReceiveQueryFacade.class);
		factoryBean.setChunkedPost(false);
		factoryBean.setOverloadEnabled(true);
		return factoryBean;
	}

	@Bean
	public HessianProxyFactoryBean receiveTradeHessian() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(receiveHost + "/hessian/receiveTradeHessian");
		factoryBean.setServiceInterface(ReceiveTradeHessian.class);
		factoryBean.setChunkedPost(false);
		return factoryBean;
	}


	@Bean
	public HessianProxyFactoryBean customerReceiveConfigHessian() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(receiveHost + "/remote/customerReceiveConfigHessian");
		factoryBean.setServiceInterface(CustomerReceiveConfigHessian.class);
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
}
