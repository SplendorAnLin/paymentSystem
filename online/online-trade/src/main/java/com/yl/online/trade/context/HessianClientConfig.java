package com.yl.online.trade.context;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import com.lefu.commons.hessian.Hessian2Input3020;
import com.lefu.commons.hessian.HessianProxyFactoryExtends;
import com.yl.account.api.dubbo.AccountInterface;
import com.yl.account.api.dubbo.AccountQueryInterface;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.boss.api.interfaces.ShareProfitInterface;
import com.yl.dpay.hessian.service.ServiceConfigFacade;
import com.yl.online.gateway.hessian.SalesNotifyHessianService;
import com.yl.payinterface.core.hessian.AuthPayHessianService;
import com.yl.payinterface.core.hessian.ChannelReverseHessianService;
import com.yl.payinterface.core.hessian.InternetbankHessianService;
import com.yl.payinterface.core.hessian.PayInterfaceHessianService;
import com.yl.payinterface.core.hessian.QuickPayHessianService;
import com.yl.payinterface.core.hessian.WalletpayHessianService;

/**
 * Hessian客户端配置
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月24日
 * @version V1.0.0
 */
@Configuration
@PropertySource("classpath:/serverHost.properties")
public class HessianClientConfig {
	@Value("${accountHost}")
	private String accountHost;
	@Value("${payinterfaceHost}")
	private String payinterfaceHost;
	@Value("${bossHost}")
	private String bossHost;
	@Value("${gatewayHost}")
	private String gatewayHost;
	@Value("${dpayCoreHost}")
	private String dpayCoreHost;

	@Bean
	public HessianProxyFactoryBean accountQueryInterface() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(accountHost + "/remote/accountQueryInterface");
		factoryBean.setServiceInterface(AccountQueryInterface.class);
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
	public HessianProxyFactoryBean internetbankHessianService() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(payinterfaceHost + "/hessian/internetbankHessianService");
		factoryBean.setServiceInterface(InternetbankHessianService.class);
		factoryBean.setChunkedPost(false);
		return factoryBean;
	}
	
	@Bean
	public HessianProxyFactoryBean payInterfaceHessianService() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(payinterfaceHost + "/hessian/payInterfaceHessianService");
		factoryBean.setServiceInterface(PayInterfaceHessianService.class);
		return factoryBean;
	}
	
	@Bean
	public HessianProxyFactoryBean authPayHessianService() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(payinterfaceHost + "/hessian/authPayHessianService");
		factoryBean.setServiceInterface(AuthPayHessianService.class);
		return factoryBean;
	}
	
	@Bean
	public HessianProxyFactoryBean quickPayHessianService() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(payinterfaceHost + "/hessian/quickPayHessianService");
		factoryBean.setServiceInterface(QuickPayHessianService.class);
		return factoryBean;
	}
	
	@Bean
	public HessianProxyFactoryBean walletpayHessianService() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(payinterfaceHost + "/hessian/walletpayHessianService");
		factoryBean.setServiceInterface(WalletpayHessianService.class);
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
	public HessianProxyFactoryBean shareProfitInterface() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(bossHost + "/remote/shareProfitInterface");
		factoryBean.setServiceInterface(ShareProfitInterface.class);
		factoryBean.setOverloadEnabled(true);
		factoryBean.setChunkedPost(false);
		HessianProxyFactoryExtends proxyFactory = new HessianProxyFactoryExtends();
		proxyFactory.setHessian2Input(Hessian2Input3020.class);
		factoryBean.setProxyFactory(proxyFactory);
		return factoryBean;
	}
	
	@Bean
	public HessianProxyFactoryBean salesNotifyHessianService() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(gatewayHost + "/hessian/salesNotifyHessianService");
		factoryBean.setServiceInterface(SalesNotifyHessianService.class);
		return factoryBean;
	}
	
	@Bean
	public HessianProxyFactoryBean serviceConfigFacade() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(dpayCoreHost + "/remote/serviceConfigFacade");
		factoryBean.setServiceInterface(ServiceConfigFacade.class);
		return factoryBean;
	}
	
	@Bean
	public HessianProxyFactoryBean channelReverseHessianService() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(payinterfaceHost + "/hessian/channelReverseHessianService");
		factoryBean.setServiceInterface(ChannelReverseHessianService.class);
		return factoryBean;
	}
}