package com.yl.customer.service;

import com.yl.customer.entity.Authorization;
import com.yl.customer.entity.LoginLog;
import com.yl.customer.exception.LoginException;

/**
 * 操作员
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月20日
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
	 * 上次登录信息
	 * @param username
	 * @return
	 */
	public LoginLog lastLogiLog(String username);
	
}
