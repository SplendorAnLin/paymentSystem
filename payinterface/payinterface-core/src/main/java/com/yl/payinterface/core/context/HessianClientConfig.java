package com.yl.payinterface.core.context;

import com.lefu.commons.hessian.Hessian2Input3020;
import com.lefu.commons.hessian.HessianProxyFactoryExtends;
import com.yl.account.api.dubbo.AccountInterface;
import com.yl.account.api.dubbo.AccountQueryInterface;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.chat.interfaces.WechatInterface;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import com.yl.dpay.hessian.service.CallbackFacade;
import com.yl.online.trade.hessian.OnlineTradeHessianService;
import com.yl.realAuth.hessian.AuthResponseHessianService;

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
	@Value("${online-trade}")
	private String onlineTradeHost;
	@Value("${dpay-code}")
	private String dpayCoreHost;
	@Value("${realAuth-code}")
	private String realAuthHost;
	@Value("${boss-host}")
	private String bossHost;
	@Value("${chat-host}")
	private String chatHost;
	@Value("${accountHost}")
	private String accountHost;

	@Bean
	public HessianProxyFactoryBean onlineTradeHessianService() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(onlineTradeHost + "/hessian/onlineTradeHessianService");
		factoryBean.setServiceInterface(OnlineTradeHessianService.class);
		return factoryBean;
	}

	@Bean
	public HessianProxyFactoryBean callbackFacade() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(dpayCoreHost + "/remote/callbackFacade");
		factoryBean.setServiceInterface(CallbackFacade.class);
		return factoryBean;
	}

	@Bean
	public HessianProxyFactoryBean authResponseHessianService() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(realAuthHost + "/hessian/authResponseHessianService");
		factoryBean.setServiceInterface(AuthResponseHessianService.class);
		return factoryBean;
	}

	@Bean
	public HessianProxyFactoryBean wechatInterface() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(chatHost + "/hessian/wechatInterface");
		factoryBean.setServiceInterface(WechatInterface.class);
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

}
