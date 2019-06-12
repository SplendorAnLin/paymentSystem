package com.yl.boss.service;

import com.yl.boss.entity.Authorization;
import com.yl.boss.entity.LoginLog;
import com.yl.boss.exception.LoginException;

/**
 * 操作员业务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public interface OperatorLoginService {

	/**
	 * 操作员登录
	 * @param name
	 * @param password
	 */
	public Authorization login(String username, String password,String ipAddress,String sessionId) throws LoginException;
	
	/**
	 * 根据登录名查询上一次登录信息
	 * @param username
	 */
	public LoginLog lastLogiLog(String username);
}
