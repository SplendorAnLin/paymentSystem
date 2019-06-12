package com.yl.online.trade.context;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;

import com.lefu.commons.web.springmvc.interceptor.HessianPermissionInterceptor;
import com.yl.online.trade.hessian.OnlineCustomerConfigHessianService;
import com.yl.online.trade.hessian.OnlineCustomerConfigHistoryHessianService;
import com.yl.online.trade.hessian.OnlineInterfacePolicyHessianService;
import com.yl.online.trade.hessian.OnlinePartnerRouterHessianService;
import com.yl.online.trade.hessian.OnlineTradeHessianService;
import com.yl.online.trade.hessian.OnlineTradeQueryHessianService;

/**
 * Hessian服务端面配置
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月24日
 * @version V1.0.0
 */
@Configuration
public class HessianServerConfig {
	@Resource
	private OnlineTradeHessianService onlineTradeHessianService;
	@Resource
	private OnlineInterfacePolicyHessianService onlineInterfacePolicyHessianService;
	@Resource
	private OnlinePartnerRouterHessianService onlinePartnerRouterHessianService;
	@Resource
	private OnlineTradeQueryHessianService onlineTradeQueryHessianService;
	@Resource
	private OnlineCustomerConfigHessianService onlineCustomerConfigHessianService;
	@Resource
	private OnlineCustomerConfigHistoryHessianService onlineCustomerConfigHistoryHessianService;
	
	@Bean(name = "/hessian/onlineCustomerConfigHistoryHessianService")
	public HessianServiceExporter onlineCustomerConfigHistoryHessianService() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(onlineCustomerConfigHistoryHessianService);
		exporter.setServiceInterface(OnlineCustomerConfigHistoryHessianService.class);
		exporter.setInterceptors(new Object[] { new HessianPermissionInterceptor() });
		return exporter;
	}

	@Bean(name = "/hessian/onlineCustomerConfigHessianService")
	public HessianServiceExporter onlineCustomerConfigHessianService() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(onlineCustomerConfigHessianService);
		exporter.setServiceInterface(OnlineCustomerConfigHessianService.class);
		exporter.setInterceptors(new Object[] { new HessianPermissionInterceptor() });
		return exporter;
	}
	
	@Bean(name = "/hessian/onlineTradeHessianService")
	public HessianServiceExporter onlineTrade() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(onlineTradeHessianService);
		exporter.setServiceInterface(OnlineTradeHessianService.class);
		exporter.setInterceptors(new Object[] { new HessianPermissionInterceptor() });
		return exporter;
	}
	
	@Bean(name = "/hessian/onlineInterfacePolicyHessianService")
	public HessianServiceExporter onlineInterfacePolicy() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(onlineInterfacePolicyHessianService);
		exporter.setServiceInterface(OnlineInterfacePolicyHessianService.class);
		exporter.setInterceptors(new Object[] { new HessianPermissionInterceptor() });
		return exporter;
	}
	
	@Bean(name = "/hessian/onlinePartnerRouterHessianService")
	public HessianServiceExporter onlinePartnerRouter() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(onlinePartnerRouterHessianService);
		exporter.setServiceInterface(OnlinePartnerRouterHessianService.class);
		exporter.setInterceptors(new Object[] { new HessianPermissionInterceptor() });
		return exporter;
	}
	@Bean(name = "/hessian/onlineTradeQueryHessianService")
	public HessianServiceExporter onlineTradeQueryHessianService() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(onlineTradeQueryHessianService);
		exporter.setServiceInterface(OnlineTradeQueryHessianService.class);
		exporter.setInterceptors(new Object[] { new HessianPermissionInterceptor() });
		return exporter;
	}
	
}
