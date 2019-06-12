package com.yl.realAuth.core.context;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;

import com.lefu.commons.web.springmvc.interceptor.HessianPermissionInterceptor;
import com.yl.realAuth.hessian.AuthConfigHessianService;
import com.yl.realAuth.hessian.AuthResponseHessianService;
import com.yl.realAuth.hessian.AuthTradeHessianService;
import com.yl.realAuth.hessian.BindCardInfoHessianService;
import com.yl.realAuth.hessian.ChannelInfoHessianService;
import com.yl.realAuth.hessian.RealNameAuthOrderHessianService;
import com.yl.realAuth.hessian.RoutingTemplateHessianService;

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
	private AuthConfigHessianService authConfigHessianService;
	@Resource
	private AuthTradeHessianService authTradeHessianService;
	@Resource
	private AuthResponseHessianService authResponseHessianService;
	@Resource
	private ChannelInfoHessianService channelInfoHessianService;
	@Resource
	private RoutingTemplateHessianService routingTemplateHessianService;
	@Resource
	private BindCardInfoHessianService bindCardInfoHessianService;
	@Resource
	private RealNameAuthOrderHessianService realNameAuthOrderHessianService;
	@Bean(name = "/hessian/authConfigHessianService")
	public HessianServiceExporter authConfigHessianService() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(authConfigHessianService);
		exporter.setServiceInterface(AuthConfigHessianService.class);
		exporter.setInterceptors(new Object[] { new HessianPermissionInterceptor() });
		return exporter;
	}

	@Bean(name = "/hessian/authTradeHessianService")
	public HessianServiceExporter authTradeHessianService() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(authTradeHessianService);
		exporter.setServiceInterface(AuthTradeHessianService.class);
		exporter.setInterceptors(new Object[] { new HessianPermissionInterceptor() });
		return exporter;
	}

	@Bean(name = "/hessian/authResponseHessianService")
	public HessianServiceExporter authResponseHessianService() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(authResponseHessianService);
		exporter.setServiceInterface(AuthResponseHessianService.class);
		exporter.setInterceptors(new Object[] { new HessianPermissionInterceptor() });
		return exporter;
	}

	@Bean(name = "/hessian/channelInfoHessianService")
	public HessianServiceExporter channelInfoHessianService() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(channelInfoHessianService);
		exporter.setServiceInterface(ChannelInfoHessianService.class);
		exporter.setInterceptors(new Object[] { new HessianPermissionInterceptor() });
		return exporter;
	}

	@Bean(name = "/hessian/routingTemplateHessianService")
	public HessianServiceExporter routingTemplateHessianService() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(routingTemplateHessianService);
		exporter.setServiceInterface(RoutingTemplateHessianService.class);
		exporter.setInterceptors(new Object[] { new HessianPermissionInterceptor() });
		return exporter;
	}
	@Bean(name = "/hessian/bindCardInfoHessianService")
	public HessianServiceExporter bindCardInfoHessianService() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(bindCardInfoHessianService);
		exporter.setServiceInterface(BindCardInfoHessianService.class);
		exporter.setInterceptors(new Object[] { new HessianPermissionInterceptor() });
		return exporter;
	}
	@Bean(name = "/hessian/realNameAuthOrderHessianService")
	public HessianServiceExporter realNameAuthOrderHessianService() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(realNameAuthOrderHessianService);
		exporter.setServiceInterface(RealNameAuthOrderHessianService.class);
		exporter.setInterceptors(new Object[] { new HessianPermissionInterceptor() });
		return exporter;
	}
}
