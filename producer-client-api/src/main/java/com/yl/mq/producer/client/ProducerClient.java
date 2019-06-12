package com.yl.mq.producer.client;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;

/**
 * 生产者客户端
 *
 * @author AnLin
 * @since 2017年4月17日
 */
@Component
public class ProducerClient {
	
	private static final Logger logger = LoggerFactory.getLogger(ProducerClient.class);

	@Resource
	private Producer producer;

	public void sendMessage(ProducerMessage message) {
		logger.info("request:{}", message);
		SendResult sendResult = producer
				.send(new Message(message.getTopic(), message.getTag(), message.getKey(), message.getBody()));
		logger.info("response:{}", sendResult);
	}
}
