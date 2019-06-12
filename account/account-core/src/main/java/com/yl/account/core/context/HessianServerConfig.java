package com.yl.account.core.context;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;

import com.yl.account.api.dubbo.AccountInterface;
import com.yl.account.api.dubbo.AccountQueryInterface;
import com.yl.account.api.dubbo.AccountRealQueryInterface;

/**
 * Hessian服务端面配置
 * 
 * @author 聚合支付有限公司
 * @since 2016年2月23日
 * @version V1.0.0
 */
@Configuration
public class HessianServerConfig {
	
	@Resource
	private AccountInterface accountInterface;
	@Resource
	private AccountQueryInterface accountQueryInterface;
	@Resource
	private	AccountRealQueryInterface accountRealQueryInterface;

	@Bean(name = "/remote/accountInterface")
	public HessianServiceExporter accountInterface() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(accountInterface);
		exporter.setServiceInterface(AccountInterface.class);
		return exporter;
	}
	
	@Bean(name = "/remote/accountQueryInterface")
	public HessianServiceExporter accountQueryInterface() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(accountQueryInterface);
		exporter.setServiceInterface(AccountQueryInterface.class);
		return exporter;
	}
	
	@Bean(name = "/remote/accountRealQueryInterface")
	public HessianServiceExporter accountRealQueryInterface() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(accountRealQueryInterface);
		exporter.setServiceInterface(AccountRealQueryInterface.class);
		return exporter;
	}

}
