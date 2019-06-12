package com.yl.dpay.core.context;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;

import com.yl.dpay.hessian.service.CallbackFacade;
import com.yl.dpay.hessian.service.DpayConfigFacade;
import com.yl.dpay.hessian.service.DpayFacade;
import com.yl.dpay.hessian.service.QueryFacade;
import com.yl.dpay.hessian.service.RequestFacade;
import com.yl.dpay.hessian.service.ServiceConfigFacade;

/**
 * Hessian服务端面配置
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月11日
 * @version V1.0.0
 */
@Configuration
public class HessianServerConfig {
	@Resource
	private ServiceConfigFacade serviceConfigFacade;
	@Resource
	private DpayFacade dpayFacade;
	@Resource
	private	QueryFacade queryFacade;
	@Resource
	private CallbackFacade callbackFacade;
	@Resource
	private DpayConfigFacade dpayConfigFacade;
	@Resource
	private RequestFacade requestFacade;

	@Bean(name = "/remote/requestFacade")
	public HessianServiceExporter requestFacade() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(requestFacade);
		exporter.setServiceInterface(RequestFacade.class);
		return exporter;
	}
	
	@Bean(name = "/remote/serviceConfigFacade")
	public HessianServiceExporter serviceConfigFacade() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(serviceConfigFacade);
		exporter.setServiceInterface(ServiceConfigFacade.class);
		return exporter;
	}
	
	@Bean(name = "/remote/dpayFacade")
	public HessianServiceExporter dpayFacade() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(dpayFacade);
		exporter.setServiceInterface(DpayFacade.class);
		return exporter;
	}
	
	@Bean(name = "/remote/queryFacade")
	public HessianServiceExporter queryFacade() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(queryFacade);
		exporter.setServiceInterface(QueryFacade.class);
		return exporter;
	}
	
	@Bean(name = "/remote/callbackFacade")
	public HessianServiceExporter callbackFacade() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(callbackFacade);
		exporter.setServiceInterface(CallbackFacade.class);
		return exporter;
	}
	
	@Bean(name = "/remote/dpayConfigFacade")
	public HessianServiceExporter dpayConfigFacade() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(dpayConfigFacade);
		exporter.setServiceInterface(DpayConfigFacade.class);
		return exporter;
	}

}
