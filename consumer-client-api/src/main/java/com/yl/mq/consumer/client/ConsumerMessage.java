package com.yl.mq.consumer.client;

import java.io.Serializable;

import com.aliyun.openservices.ons.api.Message;

/**
 * 消费消息信息
 *
 * @author AnLin
 * @since 2017年4月17日
 */
public class ConsumerMessage implements Serializable {

	private static final long serialVersionUID = 719565071810183201L;
	/**
	 * 主题
	 */
	private String topic;
	/**
	 * 消费次数
	 */
	private int reconsumerTimes;
	/**
	 * 消息id
	 */
	private String msgId;
	/**
	 * 标签
	 */
	private String tags;
	/**
	 * 标识
	 */
	private String key;
	/**
	 * 消息体
	 */
	private String body;

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public int getReconsumerTimes() {
		return reconsumerTimes;
	}

	public void setReconsumerTimes(int reconsumerTimes) {
		this.reconsumerTimes = reconsumerTimes;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "ConsumerMessage [topic=" + topic + ", reconsumerTimes=" + reconsumerTimes + ", msgId=" + msgId
				+ ", tags=" + tags + ", key=" + key + ", body=" + body + "]";
	}

	public ConsumerMessage(String topic, int reconsumerTimes, String msgId, String tags, String key, String body) {
		super();
		this.topic = topic;
		this.reconsumerTimes = reconsumerTimes;
		this.msgId = msgId;
		this.tags = tags;
		this.key = key;
		this.body = body;
	}

	public ConsumerMessage() {
		super();
	}

	public ConsumerMessage(Message message) {
		super();
		this.topic = message.getTopic();
		this.reconsumerTimes = message.getReconsumeTimes();
		this.msgId = message.getMsgID();
		this.tags = message.getTag();
		this.key = message.getKey();
		this.body = new String(message.getBody());
	}

}
