package com.yl.realAuth.core.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.lefu.commons.utils.Page;
import com.yl.realAuth.core.dao.AuthConfigDao;
import com.yl.realAuth.core.dao.mapper.AuthConfigMapper;
import com.yl.realAuth.hessian.bean.AuthConfigBean;
import com.yl.realAuth.model.AuthConfigInfo;

@Repository("authConfigDao")
public class AuthConfigDaoImpl implements AuthConfigDao{

	@Resource
	private AuthConfigMapper authConfigMapper;

	@Override
	public void insertAuthConfig(AuthConfigInfo authConfigInfo) {
		authConfigMapper.insertAuthConfig(authConfigInfo);

	}

	@Override
	public void updateAuthConfig(AuthConfigBean authConfigBean) {
		authConfigMapper.updateAuthConfig(authConfigBean);

	}

	@Override
	public AuthConfigInfo findAuthConfigByCustomerNoAndType(String partnerCode, String busiType) {
		return authConfigMapper.findAuthConfigByCustomerNoAndType(partnerCode, busiType);
	}

	@Override
	public AuthConfigInfo findAuthConfigByCustomerNo(String partnerCode) {
		return authConfigMapper.findAuthConfigByCustomerNo(partnerCode);
	}

	@Override
	public List<AuthConfigInfo> findAlldynamicAuthConfigBean(Map<String, Object> params,Page page) {
		return authConfigMapper.findAlldynamicAuthConfigBean(params,page);
	}
	
	@Override
	public List<AuthConfigBean> findAllAuthConfigBean(Map<String, Object> params, Page page) {
		return authConfigMapper.findAllAuthConfigBean(params,page);
	}

	@Override
	public int queryAuthConfigBoolByCode(String code) {
		return authConfigMapper.queryAuthConfigBoolByCode(code);
	}

	@Override
	public List<AuthConfigInfo> queryAuthConfigListByCustomerNo(String partnerCode) {
		return authConfigMapper.queryAuthConfigListByCustomerNo(partnerCode);
	}

	
	
	
}
