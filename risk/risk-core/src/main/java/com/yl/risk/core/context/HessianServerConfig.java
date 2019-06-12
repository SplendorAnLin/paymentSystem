package com.yl.risk.core.context;

import com.lefu.commons.web.springmvc.interceptor.HessianPermissionInterceptor;
import com.yl.risk.api.hessian.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;
import javax.annotation.Resource;

/**
 * Hessian服务端面配置
 * 
 * @author 聚合支付有限公司
 * @since 2017年11月10日
 * @version V1.0.0
 */
@Configuration
public class HessianServerConfig {

    @Resource
    private RiskCaseHessianService riskCaseHessianService;

    @Resource
    private RiskProcessorHessianService riskProcessorHessianService;

    @Resource
    private RiskRuleConfigHessianService riskRuleConfigHessianService;

    @Resource
    private RiskHandlerHessianService riskHandlerHessianService;

    @Resource
    private RiskOrderRecordHessianService riskOrderRecordHessianService;


    @Bean(name = "/hessian/riskCaseHessianService")
    public HessianServiceExporter riskCaseHessianService() {
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(riskCaseHessianService);
        exporter.setServiceInterface(RiskCaseHessianService.class);
        exporter.setInterceptors(new Object[] { new HessianPermissionInterceptor() });
        return exporter;
    }

    @Bean(name = "/hessian/riskProcessorHessianService")
    public HessianServiceExporter riskProcessorHessianService() {
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(riskProcessorHessianService);
        exporter.setServiceInterface(RiskProcessorHessianService.class);
        exporter.setInterceptors(new Object[] { new HessianPermissionInterceptor() });
        return exporter;
    }

    @Bean(name = "/hessian/riskRuleConfigHessianService")
    public HessianServiceExporter riskRuleConfigHessianService() {
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(riskRuleConfigHessianService);
        exporter.setServiceInterface(RiskRuleConfigHessianService.class);
        exporter.setInterceptors(new Object[] { new HessianPermissionInterceptor() });
        return exporter;
    }

    @Bean(name = "/hessian/riskHandlerHessianService")
    public HessianServiceExporter riskHandlerHessianService() {
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(riskHandlerHessianService);
        exporter.setServiceInterface(RiskHandlerHessianService.class);
        exporter.setInterceptors(new Object[] { new HessianPermissionInterceptor() });
        return exporter;
    }

    @Bean(name = "/hessian/riskOrderRecordHessianService")
    public HessianServiceExporter riskOrderRecordHessianService(){
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(riskOrderRecordHessianService);
        exporter.setServiceInterface(RiskOrderRecordHessianService.class);
        exporter.setInterceptors(new Object[]{ new HessianPermissionInterceptor() });
        return exporter;
    }
}