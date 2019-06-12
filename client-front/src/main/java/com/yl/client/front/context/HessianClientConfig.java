package com.yl.client.front.context;

import com.yl.boss.api.interfaces.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import com.lefu.commons.hessian.Hessian2Input3020;
import com.lefu.commons.hessian.HessianProxyFactoryExtends;
import com.yl.account.api.dubbo.AccountQueryInterface;
import com.yl.customer.api.interfaces.CustOperInterface;
import com.yl.dpay.hessian.service.DpayFacade;
import com.yl.online.trade.hessian.OnlineCustomerConfigHessianService;
import com.yl.online.trade.hessian.OnlineTradeQueryHessianService;
import com.yl.pay.pos.api.interfaces.PosOrderHessianService;

/**
 * Hessian客户端配置
 * 
 * @author 聚合支付有限公司
 * @since 2017年4月24日
 * @version V1.0.0
 */
@Configuration
@PropertySource("classpath:/serverHost.properties")
public class HessianClientConfig {
	@Value("${boss}")
	private String bossHost;
	@Value("${cust}")
	private String custHost;
	@Value("${agent}")
	private String agentHost;
	@Value("${dpay}")
	private String dpayHost;
	@Value("${online}")
	private String onlineHost;
	@Value("${account}")
	private String accountHost;
	@Value("${trans-core}")
	private String posHost;

	@Bean
	public HessianProxyFactoryBean oemInterface() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(bossHost + "/remote/oemInterface");
		factoryBean.setServiceInterface(OemInterface.class);
		factoryBean.setChunkedPost(false);
		HessianProxyFactoryExtends proxyFactory = new HessianProxyFactoryExtends();
		proxyFactory.setHessian2Input(Hessian2Input3020.class);
		factoryBean.setProxyFactory(proxyFactory);
		return factoryBean;
	}
	
	@Bean
	public HessianProxyFactoryBean queryaccount() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(accountHost + "/remote/accountQueryInterface");
		factoryBean.setServiceInterface(AccountQueryInterface.class);
		factoryBean.setConnectTimeout(500000);
		factoryBean.setReadTimeout(500000);
		return factoryBean;
	}
	
	@Bean
	public HessianProxyFactoryBean custOperInterface(){
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(custHost + "/remote/custOperInterface");
		factoryBean.setServiceInterface(CustOperInterface.class);
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
		factoryBean.setConnectTimeout(500000);
		factoryBean.setReadTimeout(500000);
		return factoryBean;
	}
	
	@Bean
	public HessianProxyFactoryBean onlineTradeQueryHessianService() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(onlineHost + "/hessian/onlineTradeQueryHessianService");
		factoryBean.setServiceInterface(OnlineTradeQueryHessianService.class);
		factoryBean.setConnectTimeout(500000);
		factoryBean.setReadTimeout(500000);
		return factoryBean;
	}
	
	@Bean
	public HessianProxyFactoryBean onlineCustomerConfigHessianService() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(onlineHost + "/hessian/onlineCustomerConfigHessianService");
		factoryBean.setServiceInterface(OnlineCustomerConfigHessianService.class);
		factoryBean.setConnectTimeout(500000);
		factoryBean.setReadTimeout(500000);
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
		return factoryBean;
	}

    @Bean
	public HessianProxyFactoryBean deviceInterface() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(bossHost + "/remote/deviceInterface");
		factoryBean.setServiceInterface(DeviceInterface.class);
		factoryBean.setChunkedPost(false);
		HessianProxyFactoryExtends proxyFactory = new HessianProxyFactoryExtends();
		proxyFactory.setHessian2Input(Hessian2Input3020.class);
		factoryBean.setProxyFactory(proxyFactory);
		return factoryBean;
	}

	@Bean
	public HessianProxyFactoryBean adInterface() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(bossHost + "/remote/adInterface");
		factoryBean.setServiceInterface(AdInterface.class);
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
		factoryBean.setChunkedPost(false);
		HessianProxyFactoryExtends proxyFactory = new HessianProxyFactoryExtends();
		proxyFactory.setHessian2Input(Hessian2Input3020.class);
		factoryBean.setProxyFactory(proxyFactory);
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
		return factoryBean;
	}
	
	@Bean
	public HessianProxyFactoryBean posOrder() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(posHost + "/remote/posOrder");
		factoryBean.setServiceInterface(PosOrderHessianService.class);
		factoryBean.setChunkedPost(false);
		HessianProxyFactoryExtends proxyFactory = new HessianProxyFactoryExtends();
		proxyFactory.setHessian2Input(Hessian2Input3020.class);
		factoryBean.setProxyFactory(proxyFactory);
		return factoryBean;
	}
	
	@Bean
	public HessianProxyFactoryBean bankCustomerInterface() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(bossHost + "/remote/oneCustomerMultiCodeInterface");
		factoryBean.setServiceInterface(OneCustomerMultiCodeInterface.class);
		factoryBean.setChunkedPost(false);
		HessianProxyFactoryExtends proxyFactory = new HessianProxyFactoryExtends();
		proxyFactory.setHessian2Input(Hessian2Input3020.class);
		factoryBean.setProxyFactory(proxyFactory);
		return factoryBean;
	}
}