package com.yl.mq.producer.client.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;

/**
 * 生产者客户端配置
 *
 * @author AnLin
 * @since 2017年4月17日
 */
@Configuration
@PropertySource("classpath:/producer-client.properties")
public class ProducerClientConfig {
	
	@Value("${producerId}")
	private String producerId;
	@Value("${accessKey}")
	private String accessKey;
	@Value("${secretKey}")
	private String secretKey;
	@Value("${addr}")
	private String addr;
	
	@Bean(destroyMethod = "shutdown")
	public Producer producer(){
		Properties properties = new Properties();
		properties.put(PropertyKeyConst.ProducerId, producerId);
		properties.put(PropertyKeyConst.AccessKey, accessKey);
		properties.put(PropertyKeyConst.SecretKey, secretKey);
		properties.put(PropertyKeyConst.ONSAddr, addr);
		Producer producer = ONSFactory.createProducer(properties);
		producer.start();
		return producer;
	}

}
