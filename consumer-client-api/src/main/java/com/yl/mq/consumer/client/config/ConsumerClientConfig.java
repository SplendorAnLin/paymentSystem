package com.yl.mq.consumer.client.config;

import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.yl.mq.consumer.client.ConsumerMessage;
import com.yl.mq.consumer.client.listener.ConsumerMessageListener;

/**
 * 消费者客户端配置
 *
 * @author AnLin
 * @since 2017年4月17日
 */
@Configuration
@PropertySource("classpath:/consumer-client.proptites")
public abstract class ConsumerClientConfig {

	private static final Logger logger = LoggerFactory.getLogger(ConsumerClientConfig.class);
	
	@Value("${consumerId}")
	private String consumerId;
	@Value("${accessKey}")
	private String accessKey;
	@Value("${secretKey}")
	private String secretKey;

	@Bean(destroyMethod = "shutdown")
	public Consumer consumer() {
		Properties properties = new Properties();
		properties.put(PropertyKeyConst.ConsumerId, consumerId);
		properties.put(PropertyKeyConst.AccessKey, accessKey);
		properties.put(PropertyKeyConst.SecretKey, secretKey);
		Consumer consumer = ONSFactory.createConsumer(properties);
		consumer.start();
		return consumer;
	}
	
	/**
	 * 注册消费者监听
	 * @return
	 */
	@Bean
	public abstract boolean listener();
	
	protected void subscribe(Consumer consumer, String topic, final ConsumerMessageListener listener){
		consumer.subscribe(topic, "*", new MessageListener() {
			public Action consume(Message message, ConsumeContext context) {
				logger.info("receive message:{}", message);
				listener.consumerListener(new ConsumerMessage(message));
				return Action.CommitMessage;
			}
		});
	}

}
