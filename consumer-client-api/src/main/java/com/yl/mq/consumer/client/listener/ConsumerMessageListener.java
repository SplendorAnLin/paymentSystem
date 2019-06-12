package com.yl.mq.consumer.client.listener;

import com.yl.mq.consumer.client.ConsumerMessage;

/**
 * 消费者消息监听
 *
 * @author AnLin
 * @since 2017年4月18日
 */
public interface ConsumerMessageListener {
	
	/**
	 * 消费监听
	 * @param consumerMessage
	 */
	public void consumerListener(ConsumerMessage consumerMessage);
	
}
