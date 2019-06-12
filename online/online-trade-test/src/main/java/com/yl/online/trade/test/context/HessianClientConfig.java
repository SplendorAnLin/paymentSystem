package com.yl.online.trade.test.context;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Hessian客户端配置
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月22日
 * @version V1.0.0
 */
@Configuration
@PropertySource("classpath:/serverHost.properties")
public class HessianClientConfig {}
