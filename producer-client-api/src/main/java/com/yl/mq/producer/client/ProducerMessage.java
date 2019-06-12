package com.yl.mq.producer.client;

import java.io.Serializable;

/**
 * 生产者消息
 *
 * @author AnLin
 * @since 2017年4月17日
 */
public class ProducerMessage implements Serializable {

	private static final long serialVersionUID = 7894336151185374454L;
	/**
	 * 主题
	 */
	private String topic;
	/**
	 * 标签
	 */
	private String tag;
	/**
	 * 消息体
	 */
	private byte[] body;
	/**
	 * 标识
	 */
	private String key;

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public ProducerMessage(String topic, String tag, byte[] body, String key) {
		super();
		this.topic = topic;
		this.tag = tag;
		this.body = body;
		this.key = key;
	}

	public ProducerMessage(String topic, String tag, byte[] body) {
		super();
		this.topic = topic;
		this.tag = tag;
		this.body = body;
	}

	public ProducerMessage() {
		super();
	}

	public ProducerMessage(String topic, byte[] body) {
		super();
		this.topic = topic;
		this.body = body;
	}

	public ProducerMessage(String topic, byte[] body, String key) {
		super();
		this.topic = topic;
		this.body = body;
		this.key = key;
	}

	@Override
	public String toString() {
		return "ProducerMessage [topic=" + topic + ", tag=" + tag + ", body=" + new String(body) + ", key=" + key
				+ "]";
	}

}
