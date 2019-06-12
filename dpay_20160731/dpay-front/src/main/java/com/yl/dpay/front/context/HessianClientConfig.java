package com.yl.dpay.front.context;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import com.yl.dpay.hessian.service.DpayFacade;
import com.yl.dpay.hessian.service.QueryFacade;
import com.yl.dpay.hessian.service.ServiceConfigFacade;

/**
 * Hessian客户端配置
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月21日
 * @version V1.0.0
 */
@Configuration
@PropertySource("classpath:/serverHost.properties")
public class HessianClientConfig {
	@Value("${dpay-core}")
	private String dpayserviceHost;

	@Bean
	public HessianProxyFactoryBean serviceConfigFacade() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(dpayserviceHost + "/remote/serviceConfigFacade");
		factoryBean.setServiceInterface(ServiceConfigFacade.class);
		factoryBean.setConnectTimeout(15000);
		factoryBean.setReadTimeout(15000);
		return factoryBean;
	}

	@Bean
	public HessianProxyFactoryBean dpayFacade() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(dpayserviceHost + "/remote/dpayFacade");
		factoryBean.setServiceInterface(DpayFacade.class);
		factoryBean.setConnectTimeout(15000);
		factoryBean.setReadTimeout(60000);
		return factoryBean;
	}

	@Bean
	public HessianProxyFactoryBean queryFacade() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(dpayserviceHost + "/remote/queryFacade");
		factoryBean.setServiceInterface(QueryFacade.class);
		factoryBean.setConnectTimeout(5000);
		factoryBean.setReadTimeout(5000);
		return factoryBean;
	}

}
