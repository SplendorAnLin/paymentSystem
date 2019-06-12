package com.yl.mq.consumer.client;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.yl.mq.consumer.client.listener.ConsumerMessageListener;

/**
 * 消费者客户端
 *
 * @author AnLin
 * @since 2017年4月17日
 */
@Component
public class ConsumerClient {

	private static final Logger logger = LoggerFactory.getLogger(ConsumerClient.class);

	@Resource
	private Consumer consumer;

	/**
	 * 主题、标签订阅
	 * 
	 * @param topic
	 * 
	 * @param tags
	 *            只支持或运算 tag1 || tag2 || tag3
	 */
	public void subscribe(String topic, String tags, final ConsumerMessageListener listener) {
		logger.info("subscribe topic:{},tags:{},listener:{}", topic, tags, listener.getClass().getName());
		consumer.subscribe(topic, tags, new MessageListener() {
			public Action consume(Message message, ConsumeContext context) {
				logger.info("receive message:{}", message);
				listener.consumerListener(new ConsumerMessage(message));
				return Action.CommitMessage;
			}
		});
	}

	/**
	 * 主题订阅
	 * 
	 * @param topic
	 */
	public void subscribe(String topic, final ConsumerMessageListener listener) {
		logger.info("subscribe topic:{},listener:{}", topic, listener.getClass().getName());
		consumer.subscribe(topic, "*", new MessageListener() {
			public Action consume(Message message, ConsumeContext context) {
				logger.info("receive message:{}", message);
				try {
					listener.consumerListener(new ConsumerMessage(message));
				} catch (Exception e) {
					logger.info("consumer listener error : {}", e);
				}
				return Action.CommitMessage;
			}
		});
	}

	/**
	 * 取消主题订阅
	 * 
	 * @param topic
	 */
	public void unsubscribe(String topic) {
		logger.info("unsubscribe topic:{}", topic);
		consumer.unsubscribe(topic);
	}

	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}
	
}