package com.yl.client.front.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yl.client.front.dao.UserDao;
import com.yl.client.front.enums.Status;
import com.yl.client.front.model.User;
import com.yl.client.front.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{
	@Resource
	UserDao userDao;
	
	@Override
	public void createUser(User user) {
		if (findUserExist(user.getJgId())) {
			User userOld=userDao.findUserByJg(user.getJgId());
			userOld.setError(0);
			userOld.setStauts(Status.TRUE);
			userDao.updateStauts(userOld);
		}else {
			user.setError(0);
			user.setCreateTime(new Date());
			user.setStauts(Status.TRUE);
			userDao.createUser(user);
		}
	}
	
	@Override
	public boolean findUserStauts(String jgId){
		User user=userDao.findUserByJg(jgId);
		if (user!=null&&user.getStauts()==Status.TRUE) {
			return true;
		}
		return false;
	}

	public boolean findUserExist(String jgId) {
		try {
			User user=userDao.findUserByJg(jgId);
			if (user!=null) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public User findUserByGj(String jgId){
		return userDao.findUserByJg(jgId);
	}

	@Override
	public void updateStauts(String jgId) {
		User userOld=userDao.findUserByJg(jgId);
		if (userOld!=null){
			if (userOld.getError()>3) {
				userOld.setStauts(Status.FALSE);
			}else {
				userOld.setError(userOld.getError()+1);
			}
			userDao.updateStauts(userOld);
		}
	}

	@Override
	public void updateImg(String customerImg, String customerNo) {
		userDao.updateImg(customerImg, customerNo);
	}
	
	public String findUserImg(String customerNo){
		return userDao.findUserImg(customerNo);
	}

}
