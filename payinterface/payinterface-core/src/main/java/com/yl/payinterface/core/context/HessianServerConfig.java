package com.yl.payinterface.core.context;

import com.lefu.commons.web.springmvc.interceptor.HessianPermissionInterceptor;
import com.yl.payinterface.core.hessian.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;

import javax.annotation.Resource;

/**
 * Hessian服务端面配置
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月11日
 * @version V1.0.0
 */
@Configuration
public class HessianServerConfig {
	@Resource
	private InternetbankHessianService internetbankHessianService;
	@Resource
	private PayInterfaceHessianService payInterfaceHessianService;
	@Resource
	private AuthPayHessianService authPayHessianService;
	@Resource
	private QuickPayHessianService quickPayHessianService;
	@Resource
	private InterfaceProviderHessianService interfaceProviderHessianService;
	@Resource
	private RemitHessianService remitHessianService;
	@Resource
	private WalletpayHessianService walletpayHessianService;
	@Resource
	private InterfaceRequestHessianService interfaceRequestHessianService;
	@Resource
	private ReceiveHessianService receiveHessianService;
	@Resource
	private RealNameAuthHessianService realNameAuthHessianService;
	@Resource
	private ChannelReverseHessianService channelReverseHessianService;
	@Resource
	private BindCardHessianService bindCardHessianService;
	@Resource
	private BindCardInfoHessianService bindCardInfoHessianService;
	@Resource
	private QuickPayOpenCardHessianService quickPayOpenCardHessianService;
	@Resource
	private QuickPayFilingInfoHessianService quickPayFilingInfoHessianService;
	
	@Bean(name = "/hessian/internetbankHessianService")
	public HessianServiceExporter internetbankHessianService() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(internetbankHessianService);
		exporter.setServiceInterface(InternetbankHessianService.class);
		exporter.setInterceptors(new Object[] { new HessianPermissionInterceptor() });
		return exporter;
	}

	@Bean(name = "/hessian/payInterfaceHessianService")
	public HessianServiceExporter payInterfaceHessianService() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(payInterfaceHessianService);
		exporter.setServiceInterface(PayInterfaceHessianService.class);
		exporter.setInterceptors(new Object[] { new HessianPermissionInterceptor() });
		return exporter;
	}

	@Bean(name = "/hessian/authPayHessianService")
	public HessianServiceExporter authPayTmpHessianService() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(authPayHessianService);
		exporter.setServiceInterface(AuthPayHessianService.class);
		exporter.setInterceptors(new Object[] { new HessianPermissionInterceptor() });
		return exporter;
	}

	@Bean(name = "/hessian/quickPayHessianService")
	public HessianServiceExporter quickPayHessianService() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(quickPayHessianService);
		exporter.setServiceInterface(QuickPayHessianService.class);
		exporter.setInterceptors(new Object[] { new HessianPermissionInterceptor() });
		return exporter;
	}
	
	@Bean(name = "/hessian/interfaceProviderHessianService")
	public HessianServiceExporter interfaceProviderHessianService() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(interfaceProviderHessianService);
		exporter.setServiceInterface(InterfaceProviderHessianService.class);
		exporter.setInterceptors(new Object[] { new HessianPermissionInterceptor() });
		return exporter;
	}

	@Bean(name = "/hessian/remitHessianService")
	public HessianServiceExporter remitHessianService() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(remitHessianService);
		exporter.setServiceInterface(RemitHessianService.class);
		exporter.setInterceptors(new Object[] { new HessianPermissionInterceptor() });
		return exporter;
	}
	
	@Bean(name = "/hessian/walletpayHessianService")
	public HessianServiceExporter walletpayHessianService() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(walletpayHessianService);
		exporter.setServiceInterface(WalletpayHessianService.class);
		exporter.setInterceptors(new Object[] { new HessianPermissionInterceptor() });
		return exporter;
	}
	
	@Bean(name = "/hessian/interfaceRequestHessianService")
	public HessianServiceExporter interfaceRequestHessianService() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(interfaceRequestHessianService);
		exporter.setServiceInterface(InterfaceRequestHessianService.class);
		exporter.setInterceptors(new Object[] { new HessianPermissionInterceptor() });
		return exporter;
	}
	
	@Bean(name = "/hessian/receiveHessianService")
	public HessianServiceExporter receiveHessianService() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(receiveHessianService);
		exporter.setServiceInterface(ReceiveHessianService.class);
		exporter.setInterceptors(new Object[] { new HessianPermissionInterceptor() });
		return exporter;
	}
	
	@Bean(name = "/hessian/realNameAuthHessianService")
	public HessianServiceExporter realNameAuthHessianService() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(realNameAuthHessianService);
		exporter.setServiceInterface(RealNameAuthHessianService.class);
		exporter.setInterceptors(new Object[] { new HessianPermissionInterceptor() });
		return exporter;
	}
	
	@Bean(name = "/hessian/channelReverseHessianService")
	public HessianServiceExporter channelReverseHessianService() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(channelReverseHessianService);
		exporter.setServiceInterface(ChannelReverseHessianService.class);
		exporter.setInterceptors(new Object[] { new HessianPermissionInterceptor() });
		return exporter;
	}

	@Bean(name = "/hessian/bindCardHessianService")
	public HessianServiceExporter bindCardHessianService() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(bindCardHessianService);
		exporter.setServiceInterface(BindCardHessianService.class);
		exporter.setInterceptors(new Object[] { new HessianPermissionInterceptor() });
		return exporter;
	}
	@Bean(name = "/hessian/bindCardInfoHessianService")
	public HessianServiceExporter authPayBindCardInfoHessianService() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(bindCardInfoHessianService);
		exporter.setServiceInterface(BindCardInfoHessianService.class);
		exporter.setInterceptors(new Object[] { new HessianPermissionInterceptor() });
		return exporter;
	}
	@Bean(name = "/hessian/quickPayOpenCardHessianService")
	public HessianServiceExporter quickPayOpenCardHessianService() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(quickPayOpenCardHessianService);
		exporter.setServiceInterface(QuickPayOpenCardHessianService.class);
		exporter.setInterceptors(new Object[] { new HessianPermissionInterceptor() });
		return exporter;
	}
	@Bean(name = "/hessian/quickPayFilingInfoHessianService")
	public HessianServiceExporter quickPayFilingInfoHessianService() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(quickPayFilingInfoHessianService);
		exporter.setServiceInterface(QuickPayFilingInfoHessianService.class);
		exporter.setInterceptors(new Object[] { new HessianPermissionInterceptor() });
		return exporter;
	}
}