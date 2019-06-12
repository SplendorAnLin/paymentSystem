package com.yl.client.front.dao;

import org.apache.ibatis.annotations.Param;

import com.yl.client.front.model.User;

public interface UserDao {
	void createUser(User user);
	User findUserByJg(@Param("jgId")String jgId);
	void updateStauts(User user);
	void updateImg(@Param("customerImg")String customerImg,@Param("customerNo")String customerNo);
	String findUserImg(@Param("customerNo")String customerNo);
}
