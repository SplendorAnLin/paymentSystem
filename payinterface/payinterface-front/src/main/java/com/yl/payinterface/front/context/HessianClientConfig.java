package com.yl.payinterface.front.context;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import com.yl.payinterface.core.hessian.BindCardInfoHessianService;
import com.yl.payinterface.core.hessian.AuthPayHessianService;
import com.yl.payinterface.core.hessian.InterfaceRequestHessianService;
import com.yl.payinterface.core.hessian.InternetbankHessianService;
import com.yl.payinterface.core.hessian.PayInterfaceHessianService;
import com.yl.payinterface.core.hessian.QuickPayHessianService;
import com.yl.payinterface.core.hessian.ReceiveHessianService;
import com.yl.payinterface.core.hessian.RemitHessianService;
import com.yl.payinterface.core.hessian.WalletpayHessianService;

/**
 * Hessian客户端配置
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月17日
 * @version V1.0.0
 */
@Configuration
@PropertySource("classpath:/serverHost.properties")
public class HessianClientConfig {
	@Value("${payinterface-core}")
	private String payinterfaceCoreHost;

	@Bean
	public HessianProxyFactoryBean payInterfaceHessianService() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(payinterfaceCoreHost + "/hessian/payInterfaceHessianService");
		factoryBean.setServiceInterface(PayInterfaceHessianService.class);
		return factoryBean;
	}
	
	@Bean
	public HessianProxyFactoryBean remitHessianService() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(payinterfaceCoreHost + "/hessian/remitHessianService");
		factoryBean.setServiceInterface(RemitHessianService.class);
		return factoryBean;
	}
	
	

	@Bean
	public HessianProxyFactoryBean internetbankHessianService() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(payinterfaceCoreHost + "/hessian/internetbankHessianService");
		factoryBean.setServiceInterface(InternetbankHessianService.class);
		return factoryBean;
	}

	@Bean
	public HessianProxyFactoryBean receiveHessianService() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(payinterfaceCoreHost + "/hessian/receiveHessianService");
		factoryBean.setServiceInterface(ReceiveHessianService.class);
		return factoryBean;
	}
	
	@Bean
	public HessianProxyFactoryBean walletpayHessianService() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(payinterfaceCoreHost + "/hessian/walletpayHessianService");
		factoryBean.setServiceInterface(WalletpayHessianService.class);
		return factoryBean;
	}
	
	@Bean
	public HessianProxyFactoryBean authPayHessianService() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(payinterfaceCoreHost + "/hessian/authPayHessianService");
		factoryBean.setServiceInterface(AuthPayHessianService.class);
		return factoryBean;
	}
	@Bean
	public HessianProxyFactoryBean quickPayHessianService() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(payinterfaceCoreHost + "/hessian/quickPayHessianService");
		factoryBean.setServiceInterface(QuickPayHessianService.class);
		return factoryBean;
	}
	
	
	@Bean
	public HessianProxyFactoryBean interfaceRequestHessianService() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(payinterfaceCoreHost + "/hessian/interfaceRequestHessianService");
		factoryBean.setServiceInterface(InterfaceRequestHessianService.class);
		return factoryBean;
	}
	@Bean
	public HessianProxyFactoryBean bindCardInfoHessianService() {
		HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
		factoryBean.setServiceUrl(payinterfaceCoreHost + "/hessian/bindCardInfoHessianService");
		factoryBean.setServiceInterface(BindCardInfoHessianService.class);
		return factoryBean;
	}

}
