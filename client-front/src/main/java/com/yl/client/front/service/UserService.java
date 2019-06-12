package com.yl.client.front.service;

import com.yl.client.front.model.User;

public interface UserService {
	/**
	 * 创建用户或者更新用户状态
	 * @param user
	 */
	void createUser(User user);
	/**
	 * 查找用户状态
	 * @param jgId
	 * @return
	 */
	boolean findUserStauts(String jgId);
	/**
	 * 更新用户状态
	 * @param jgId
	 */
	void updateStauts(String jgId);
	/**
	 * 上传用户头像
	 * @param customerImg
	 * @param customerNo
	 */
	void updateImg(String customerImg,String customerNo);
	/**
	 * 查找用户头像
	 * @param customerNo
	 * @return
	 */
	String findUserImg(String customerNo);
}
