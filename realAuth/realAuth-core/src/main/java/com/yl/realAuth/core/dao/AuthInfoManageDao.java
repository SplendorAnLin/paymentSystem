package com.yl.realAuth.core.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yl.realAuth.model.BindCardInfo;
import com.yl.realAuth.model.IdentityInfo;

/**
 * 实名认证信息管理
 * @author Shark
 * @since 2015年7月15日
 */
public interface AuthInfoManageDao {

	/**
	 * 根据身份证号查询绑卡认证信息
	 * @param certNoEncrypt 身份证号加密
	 * @return
	 */
	IdentityInfo queryByCertNoEncrypt(@Param("certNoEncrypt")String certNoEncrypt);

	/**
	 * 身份认证成功记录存储
	 * @param identityInfo
	 */
	void insertAuthOrderByIdentity(IdentityInfo identityInfo);

	/**
	 * 绑卡认证成功记录存储
	 * @param bindCardInfo
	 */
	void insertAuthOrderByBind(BindCardInfo bindCardInfo);

	/**
	 * 绑卡认证信息更新
	 * @param bindCardInfo
	 */
	void updateBindCardInfo(BindCardInfo bindCardInfo);

	/**
	 * 身份认证信息更新
	 * @param identityInfo
	 */
	void updateIdentityInfo(IdentityInfo identityInfo);

	/**
	 * 根据卡号查询绑卡认证信息
	 * @param queryParams 查询参数
	 * @return
	 */
	BindCardInfo queryBy(Map<String, String> queryParams);

}
