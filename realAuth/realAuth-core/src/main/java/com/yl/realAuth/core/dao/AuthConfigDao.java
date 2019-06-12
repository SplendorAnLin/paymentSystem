package com.yl.realAuth.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lefu.commons.utils.Page;
import com.yl.realAuth.hessian.bean.AuthConfigBean;
import com.yl.realAuth.model.AuthConfigInfo;

/**
 * 实名认证开通服务
 * @author Shark
 * @since 2015年6月5日
 */
public interface AuthConfigDao {
	/**
	 * 身份认证开通
	 * @param authConfigBean 开通业务bean
	 */
	void insertAuthConfig(AuthConfigInfo authConfigInfo);

	/**
	 * 更改商户信息
	 * @param authConfigBean 业务请求bean
	 */
	void updateAuthConfig(AuthConfigBean authConfigBean);

	/**
	 * 根据商户编号和业务类型查询实名认证是否开通
	 * @param partnerCode 商户编号
	 * @param busiType 业务类型
	 * @return
	 */
	AuthConfigInfo findAuthConfigByCustomerNoAndType(@Param("partnerCode")String partnerCode, @Param("busiType")String busiType);

	/**
	 * 根据商户编号查询实名认证是否开通
	 * @param partnerCode 商户编号
	 * @return
	 */
	AuthConfigInfo findAuthConfigByCustomerNo(String partnerCode);
	/**
	 * 动态查询
	 */
	public List<AuthConfigInfo> findAlldynamicAuthConfigBean(@Param("params")Map<String, Object> params , @Param("page")Page page);
	
	public List<AuthConfigBean> findAllAuthConfigBean(@Param("params")Map<String, Object> params , @Param("page")Page page);
	
	/**
	 * 根据编号查询是否存在
	 * @param code
	 * @return
	 */
	public int queryAuthConfigBoolByCode(@Param("code")String code);
	
	/**
	 * 根据商户编号查询集合
	 * @param partnerCode
	 * @return
	 */
	public List<AuthConfigInfo> queryAuthConfigListByCustomerNo(@Param("partnerCode")String partnerCode);
	
}
