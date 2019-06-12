package com.yl.chat.context;

import com.lefu.commons.web.springmvc.interceptor.HessianPermissionInterceptor;
import com.yl.chat.interfaces.WechatInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;
import javax.annotation.Resource;

/**
 * Hessian服务端面配置
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017年4月21日
 */
@Configuration
public class HessianServerConfig {

    @Resource
    private WechatInterface wechatInterface;

    @Bean(name = "/hessian/wechatInterface")
    public HessianServiceExporter weMenuinterface() {
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(wechatInterface);
        exporter.setServiceInterface(WechatInterface.class);
        exporter.setInterceptors(new Object[]{new HessianPermissionInterceptor()});
        return exporter;
    }
}