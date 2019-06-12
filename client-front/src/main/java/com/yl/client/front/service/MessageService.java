package com.yl.client.front.service;

import com.yl.client.front.model.Message;
import com.yl.client.front.model.Notice;

public interface MessageService {
	/**
	 * 保存发送的信息
	 * @param notice
	 */
	public void insertMessage(Notice notice);
	/**
	 * 根据用户发送信息
	 * @param userName
	 * @param msg
	 */
	public void pushMSG(String userName, Message msg);
	/**
	 * 发送信息给所有人
	 * @param msg
	 */
	public void pushAllMSG(Message msg);
}
