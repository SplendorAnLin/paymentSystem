package com.yl.realAuth.core.hessian.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.realAuth.core.ExceptionMessages;
import com.yl.realAuth.core.service.AuthConfigService;
import com.yl.realAuth.core.service.RoutingTemplateService;
import com.yl.realAuth.hessian.AuthConfigHessianService;
import com.yl.realAuth.hessian.bean.AuthConfigBean;
import com.yl.realAuth.model.AuthConfigInfo;

/**
 * 实名认证开通配置服务
 * @author congxiang.bai
 * @since 2015年6月3日
 */
@Service("authConfigHessianService")
public class AuthConfigHessianServiceImpl implements AuthConfigHessianService {
	private static final Logger logger=LoggerFactory.getLogger(AuthConfigHessianServiceImpl.class);
	@Resource
	private AuthConfigService authConfigService;
	@Resource
	private RoutingTemplateService routingTemplateService;

	public List<Map> findRoutingTemplate() {
		return routingTemplateService.findRoutingTemplate();
	}
	@Override
	public void createAuthConfig(AuthConfigBean authConfigBean) {
		if (authConfigBean == null) throw new RuntimeException(ExceptionMessages.PARAM_NOT_EXISTS);
		AuthConfigInfo authConfigInfo = JsonUtils.toJsonToObject(authConfigBean, AuthConfigInfo.class);
		authConfigService.createAuthConfig(authConfigInfo);
	}

	@Override
	public void updateAuthConfig(AuthConfigBean authConfigBean) {
		authConfigService.modifyAuthConfig(authConfigBean);
	}

	@Override
	public String queryAuthConfigByCustomerNoAndBusiType(String partnerCode, String busiType) {
		AuthConfigInfo authConfigInfo = authConfigService.queryAuthConfigByCustomerNo(partnerCode, busiType);
		String configStatus = "";
		if (authConfigInfo != null) {
			configStatus = authConfigInfo.getStatus().name();
		}
		return configStatus;
	}

	@Override
	public String queryAuthConfigByCustomerNo(String partnerCode) {
		AuthConfigInfo authConfigInfo = authConfigService.queryAuthConfigBycustomerNo(partnerCode);
		String configStatus = "";
		if (authConfigInfo != null) {
			configStatus = authConfigInfo.getStatus().name();
		}
		return configStatus;
	}

	@Override
	public Page findAlldynamicAuthConfigBean(Map<String, Object> params,Page page) {
		return authConfigService.findAlldynamicAuthConfigBean(params,page);
	}
	@Override
	public Page findAllAuthConfigBean(Map<String, Object> params, Page page) {
		return authConfigService.findAllAuthConfigBean(params, page);
	}

	@Override
	public AuthConfigBean findAuthConfigBeanByCustomerNoAndBusiType(String partnerCode, String busiType) {
		return JsonUtils.toJsonToObject(authConfigService.queryAuthConfigByCustomerNo(partnerCode, busiType), com.yl.realAuth.hessian.bean.AuthConfigBean.class);
	}

	@Override
	public Boolean queryAuthConfigBoolByCode(String code) {
		return authConfigService.queryAuthConfigBoolByCode(code);
	}
	
}
