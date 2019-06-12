package com.yl.realAuth.core.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.realAuth.core.dao.AuthConfigDao;
import com.yl.realAuth.core.service.AuthConfigService;
import com.yl.realAuth.enums.AuthBusiType;
import com.yl.realAuth.enums.AuthConfigStatus;
import com.yl.realAuth.hessian.bean.AuthConfigBean;
import com.yl.realAuth.model.AuthConfigInfo;

@Service("authConfigService")
public class AuthConfigServiceImpl implements AuthConfigService {

	@Resource
	private AuthConfigDao authConfigDao;

	@Override
	public void createAuthConfig(AuthConfigInfo authConfigInfo) {
		authConfigDao.insertAuthConfig(authConfigInfo);
	}

	@Override
	public void modifyAuthConfig(AuthConfigBean authConfigBean) {
		AuthConfigInfo result = null;
		List<AuthConfigInfo> list = this.queryAuthConfigListByCustomerNo(authConfigBean.getCustomerNo());
		if(list != null && list.size() > 0){
			
			if(list.size() > 1){
			
				for (AuthConfigInfo authConfig : list) {
					
					if(authConfig.getBusiType() == com.yl.realAuth.enums.AuthBusiType.valueOf(authConfigBean.getBusiType().name())){
						
						result = authConfig;
						break;
						
					}
					
				}
				
			}else {
				
				result = list.get(0);
				
			}
			
			result.setVersion(authConfigBean.getVersion());
			result.setBusiType(AuthBusiType.valueOf(authConfigBean.getBusiType().name()));
			result.setStatus(AuthConfigStatus.valueOf(authConfigBean.getStatus().name()));
			result.setLastUpdateTime(new Date());
			result.setIsActual(authConfigBean.getIsActual());
			result.setRoutingTemplateCode(authConfigBean.getRoutingTemplateCode());
			result.setRemark(authConfigBean.getRemark());
			authConfigDao.updateAuthConfig(JsonUtils.toJsonToObject(result, AuthConfigBean.class));
			
		}

	}

	@Override
	public AuthConfigInfo queryAuthConfigByCustomerNo(String partnerCode, String busiType) {
		return authConfigDao.findAuthConfigByCustomerNoAndType(partnerCode, busiType);
	}

	@Override
	public AuthConfigInfo queryAuthConfigBycustomerNo(String partnerCode) {
		return authConfigDao.findAuthConfigByCustomerNo(partnerCode);
	}

	@Override
	public Page findAlldynamicAuthConfigBean(Map<String, Object> params,Page page) {
		List<AuthConfigInfo> list = authConfigDao.findAlldynamicAuthConfigBean(params,page);
		page.setObject(list);
		return page;
	}
	
	@Override
	public Page findAllAuthConfigBean(Map<String, Object> params, Page page) {
		List<AuthConfigBean> list = authConfigDao.findAllAuthConfigBean(params,page);
		page.setObject(list);
		return page;
	}

	@Override
	public Boolean queryAuthConfigBoolByCode(String code) {
		int result = authConfigDao.queryAuthConfigBoolByCode(code);
		if(result == 1){
			return true;
		}else {
			return false;
		}
	}

	@Override
	public List<AuthConfigInfo> queryAuthConfigListByCustomerNo(String partnerCode) {
		return authConfigDao.queryAuthConfigListByCustomerNo(partnerCode);
	}

	

}
