package com.yl.realAuth.hessian;

import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.realAuth.hessian.bean.AuthConfigBean;

/**
 * 实名认证开通Hessian服务
 * @author Shark
 * @since 2015年6月3日
 */
public interface AuthConfigHessianService {
	/**
	 * 实名认证服务开通
	 * @param authConfigBean 配置实体
	 */
	public void createAuthConfig(AuthConfigBean authConfigBean);

	/**
	 * 实名认证服务修改
	 * @param authConfigBean 配置实体
	 */
	public void updateAuthConfig(AuthConfigBean authConfigBean);

	/**
	 * 根据商户编号和业务类型查询实名认证是否开通
	 * @param partnerCode 商户编号
	 * @param busiType 业务类型
	 * @return "" 表示未开通,非空表示开通
	 */
	public String queryAuthConfigByCustomerNoAndBusiType(String partnerCode, String busiType);
	
	public String queryAuthConfigByCustomerNo(String partnerCode);
	/**
	 * 动态查询实名认证服务
	 */
	public Page findAlldynamicAuthConfigBean(Map<String, Object> params,Page page);
	public Page findAllAuthConfigBean(Map<String, Object> params, Page page);
	
	/**
	 * 根据商户编号和业务类型，查询实名认证信息
	 * @param partnerCode
	 * @param busiType
	 * @return
	 */
	public AuthConfigBean findAuthConfigBeanByCustomerNoAndBusiType(String partnerCode, String busiType);
	
	/**
	 * 根据编号查询是否存在
	 * @param code
	 * @return
	 */
	public Boolean queryAuthConfigBoolByCode(String code);
	/**
	 * 查询所有路由模版
	 * @return
	 */
	public List<Map> findRoutingTemplate();
}
