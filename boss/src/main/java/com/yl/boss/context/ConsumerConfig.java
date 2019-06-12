package com.yl.boss.context;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.yl.mq.consumer.client.config.ConsumerClientConfig;
import com.yl.mq.consumer.client.listener.ConsumerMessageListener;

/**
 * 消费者配置
 * 
 * @author AnLin
 * @since 2017年4月21日
 */
public class ConsumerConfig extends ConsumerClientConfig {

	private ConsumerMessageListener shareMessageListener;
	private static Properties properties = new Properties();
	static{
		try {
			properties.load(new InputStreamReader(ConsumerConfig.class.getClassLoader().getResourceAsStream("consumer-client.proptites")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Consumer consumer() {
		properties.put(PropertyKeyConst.ConsumerId, properties.getProperty("consumerId"));
		properties.put(PropertyKeyConst.AccessKey, properties.getProperty("accessKey"));
		properties.put(PropertyKeyConst.SecretKey, properties.getProperty("secretKey"));
		Consumer consumer = ONSFactory.createConsumer(properties);
		consumer.start();
		super.subscribe(consumer, properties.getProperty("topic"), shareMessageListener);
		return consumer;
	}
	
	@Override
	public boolean listener() {
		return true;
	}

	public void setShareMessageListener(ConsumerMessageListener shareMessageListener) {
		this.shareMessageListener = shareMessageListener;
	}

}
