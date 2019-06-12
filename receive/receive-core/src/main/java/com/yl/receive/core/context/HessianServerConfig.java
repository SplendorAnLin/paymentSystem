package com.yl.receive.core.context;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;

import com.yl.receive.hessian.CustomerReceiveConfigHessian;
import com.yl.receive.hessian.NameListQuery;
import com.yl.receive.hessian.ReceiveFacade;
import com.yl.receive.hessian.ReceiveQueryFacade;
import com.yl.receive.hessian.ReceiveTradeHessian;

/**
 * Hessian服务端面配置
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月25日
 * @version V1.0.0
 */
@Configuration
public class HessianServerConfig {

	@Resource
	private ReceiveTradeHessian receiveTradeHessian;
	@Resource
	private ReceiveQueryFacade receiveQueryFacade;
	@Resource
	private ReceiveFacade recfFacade;
	@Resource
	private CustomerReceiveConfigHessian customerReceiveConfigHessian;
	@Resource
	private NameListQuery nameListQuery;
	
	@Bean(name = "/hessian/receiveTradeHessian")
	public HessianServiceExporter receiveTradeHessian() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(receiveTradeHessian);
		exporter.setServiceInterface(ReceiveTradeHessian.class);
		return exporter;
	}
	
	@Bean(name="/remote/receiveQueryFacade")
	public HessianServiceExporter receiveQueryFacade() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(receiveQueryFacade);
		exporter.setServiceInterface(ReceiveQueryFacade.class);
		return exporter;
	}
	
	@Bean(name="/remote/recfFacade")
	public HessianServiceExporter recfFacade() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(recfFacade);
		exporter.setServiceInterface(ReceiveFacade.class);
		return exporter;
	}
	
	@Bean(name="/remote/customerReceiveConfigHessian")
	public HessianServiceExporter customerReceiveConfigHessian() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(customerReceiveConfigHessian);
		exporter.setServiceInterface(CustomerReceiveConfigHessian.class);
		return exporter;
	}
	
	@Bean(name="/remote/nameListQuery")
	public HessianServiceExporter nameListQuery() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(nameListQuery);
		exporter.setServiceInterface(NameListQuery.class);
		return exporter;
	}
}