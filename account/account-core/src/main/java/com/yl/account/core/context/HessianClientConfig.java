package com.yl.account.core.context;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Hessian客户端配置
 * 
 * @author 聚合支付有限公司
 * @since 2016年2月23日
 * @version V1.0.0
 */
@Configuration
@PropertySource("classpath:/serverHost.properties")
public class HessianClientConfig {

}
