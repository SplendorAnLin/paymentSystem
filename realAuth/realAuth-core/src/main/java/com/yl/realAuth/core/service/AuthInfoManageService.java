package com.yl.realAuth.core.service;

import com.yl.realAuth.model.RealNameAuthOrder;


/**
 * 实名认证信息存储管理
 * @author Shark
 * @since 2015年7月15日
 */
public interface AuthInfoManageService {
	/**
	 * 根据业务类型判断认证信息是否存在
	 * @param order
	 * @return
	 */
	boolean findAuthInfoBy(RealNameAuthOrder order);

	/**
	 * 验证信息存储
	 * @param order
	 */
	void saveAuthInfo(RealNameAuthOrder order);

}
