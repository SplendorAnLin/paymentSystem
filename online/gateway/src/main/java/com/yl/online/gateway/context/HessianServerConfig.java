package com.yl.online.gateway.context;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;

import com.lefu.commons.web.springmvc.interceptor.HessianPermissionInterceptor;
import com.yl.online.gateway.hessian.SalesNotifyHessianService;

/**
 * Hessian服务端面配置
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月2日
 * @version V1.0.0
 */
@Configuration
public class HessianServerConfig {
	@Resource
	private SalesNotifyHessianService salesNotifyHessianService;

	@Bean(name = "/hessian/salesNotifyHessianService")
	public HessianServiceExporter salesNotifyHessianService() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(salesNotifyHessianService);
		exporter.setServiceInterface(SalesNotifyHessianService.class);
		exporter.setInterceptors(new Object[] { new HessianPermissionInterceptor() });
		return exporter;
	}
	
}
