package com.yl.recon.core.context;

import javax.annotation.Resource;

import com.yl.recon.api.core.remote.ReconInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;
import com.lefu.commons.web.springmvc.interceptor.HessianPermissionInterceptor;

/**
 * Hessian服务端面配置
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年2月23日
 */
@Configuration
public class HessianServerConfig {


	@Resource
	private ReconInterface reconInterface;


	@Bean(name = "/remote/reconInterface")
	public HessianServiceExporter reconInterface() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(reconInterface);
		exporter.setServiceInterface(ReconInterface.class);
		exporter.setInterceptors(new Object[]{new HessianPermissionInterceptor()});
		return exporter;
	}

}