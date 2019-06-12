package com.yl.rate.core.context;

import com.lefu.commons.web.springmvc.interceptor.HessianPermissionInterceptor;
import com.yl.rate.interfaces.interfaces.RateInterface;
import com.yl.rate.interfaces.interfaces.RateQueryInterface;
import com.yl.rate.interfaces.interfaces.RateTemplateInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;

import javax.annotation.Resource;


/**
 * Hessian服务端面配置
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年5月11日
 */
@Configuration
public class HessianServerConfig {

    @Resource
    private RateInterface rateInterface;

    @Resource
    private RateTemplateInterface rateTemplateInterface;

    @Resource
    private RateQueryInterface rateQueryInterface;

    @Bean(name = "/hessian/rateInterface")
    public HessianServiceExporter rateInterface() {
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(rateInterface);
        exporter.setServiceInterface(RateInterface.class);
        exporter.setInterceptors(new Object[] { new HessianPermissionInterceptor() });
        return exporter;
    }

    @Bean(name = "/hessian/rateTemplateInterface")
    public HessianServiceExporter rateTemplateInterface() {
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(rateTemplateInterface);
        exporter.setServiceInterface(RateTemplateInterface.class);
        exporter.setInterceptors(new Object[] { new HessianPermissionInterceptor() });
        return exporter;
    }

    @Bean(name = "/hessian/rateQueryInterface")
    public HessianServiceExporter rateQueryInterface() {
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(rateQueryInterface);
        exporter.setServiceInterface(RateQueryInterface.class);
        exporter.setInterceptors(new Object[] { new HessianPermissionInterceptor() });
        return exporter;
    }
}
