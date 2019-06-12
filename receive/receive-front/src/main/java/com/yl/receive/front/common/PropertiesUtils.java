package com.yl.receive.front.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.ResourcePropertySource;

/**
 * Properties工具类
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月12日
 * @version V1.0.0
 */
public class PropertiesUtils {
	private static Logger log = LoggerFactory.getLogger(PropertiesUtils.class);
	public static ResourcePropertySource partnerKeySource;

	static {
		try {
			init();
		} catch (Exception e) {
			log.error("配置文件初始化异常",e);
		}
	}

	public static void init() throws Exception {
		partnerKeySource = new ResourcePropertySource("partnerKey", "classpath:partnerKey.properties");

	}
}
