package com.yl.realAuth.core.service;

import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.realAuth.hessian.bean.AuthConfigBean;
import com.yl.realAuth.model.AuthConfigInfo;

/**
 * 实名认证管理服务
 * @author Shark
 * @since 2015年6月5日
 */
public interface AuthConfigService {

	/**
	 * 身份认证开通
	 * @param authConfigBean 开通业务参数bean
	 */
	void createAuthConfig(AuthConfigInfo authConfigInfo);

	/**
	 * 身份认证修改
	 * @param authConfigBean
	 */
	void modifyAuthConfig(AuthConfigBean authConfigBean);
	
	/**
	 * 根据商户编号和业务类型查询实名认证服务是否开通
	 * @param partnerCode 商户编号
	 * @param busiType 业务类型
	 * @return 实名认证开通状态
	 */
	AuthConfigInfo queryAuthConfigByCustomerNo(String partnerCode, String busiType);

	/**
	 * 根据商户编号查询实名认证服务是否开通
	 * @param partnerCode
	 * @return
	 */
	AuthConfigInfo queryAuthConfigBycustomerNo(String partnerCode);

	/**
	 * 动态查询
	 */
	public Page findAlldynamicAuthConfigBean(Map<String, Object> params,Page page);
	public Page findAllAuthConfigBean(Map<String, Object> params, Page page);
	
	/**
	 * 根据编号查询是否存在
	 * @param code
	 * @return
	 */
	public Boolean queryAuthConfigBoolByCode(String code);
	
	/**
	 * 根据商户编号查询集合
	 * @param partnerCode
	 * @return
	 */
	public List<AuthConfigInfo> queryAuthConfigListByCustomerNo(String partnerCode);
}
