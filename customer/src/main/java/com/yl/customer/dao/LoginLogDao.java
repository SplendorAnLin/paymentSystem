package com.yl.customer.dao;

import java.util.List;

import com.yl.customer.entity.LoginLog;
import com.yl.customer.enums.LoginStatus;

/**
 * 操作员登录日志
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
public interface LoginLogDao {
	
	
	/**
	 * 根据用户名查找上次登录信息
	 * @param username
	 * @return
	 */
	public LoginLog findLastByUsername(String username);
	/**
	 * 创建操作员登录日志
	 * @param loginLog
	 *  @return   LoginLog对象
	 */
	public LoginLog create(LoginLog loginLog);

	/**
	 * 根据操作员id查询操作员登录日志
	 * @param id         
	 * @return LoginLog对象
	 */

	public LoginLog findById(Long id);
	
	/**
	 * 修改操作员登录日志
	 * @param loginLog
	 * @return
	 */
	public LoginLog update(LoginLog loginLog);
	
	/**
	 * 修改操作员登录日志
	 * @param loginLogs
	 * @return
	 */
	public void update(List<LoginLog> loginLogs);
	/**
	 * 根据用户名查找登录日志
	 * @param username
	 * @return
	 */
	public List<LoginLog> findByUsername(String username);
	
	/**
	 * 查找该用户名以不用于参数sessionId登录成功并且LogoutTime属性为空的登录日志，结果按照LoginTime倒叙排列
	 * 
	 * @return
	 */
	public List<LoginLog> findByUsernameAndStatusAndLogoutTimeNull(String username,LoginStatus loginStatus);
	
	/**
	 * 根据sessionId查找登录日志
	 * @param sessionId
	 * @return
	 */
	public List<LoginLog> findBySessionIdAndLoginStatus(String username,String sessionId,LoginStatus loginStatus);
	
}
