package com.yl.client.front.context;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yl.mq.consumer.client.ConsumerClient;
import com.yl.mq.consumer.client.config.ConsumerClientConfig;
import com.yl.mq.consumer.client.listener.ConsumerMessageListener;

/**
 * 消费者配置
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月11日
 * @version V1.0.0
 */
@Configuration
public class ConsumerConfig extends ConsumerClientConfig {

	@Resource
	private ConsumerMessageListener changesFundsListener;
	@Resource
	private ConsumerClient consumerClient;
	
	private static Properties prop = new Properties();
	static {
		try {
			prop.load(ConsumerConfig.class.getClassLoader().getResourceAsStream("consumer-client.proptites"));
		} catch (IOException e) {}
	}

	@Bean
	@Override
	public boolean listener() {
		String topic = prop.getProperty("topic");
		consumerClient.subscribe(topic, changesFundsListener);
		return true;
	}

}
