package com.yl.online.trade.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.online.model.enums.InterfacePolicyStatus;
import com.yl.online.model.enums.InterfaceType;
import com.yl.online.model.model.InterfacePolicy;
import com.yl.online.trade.dao.InterfacePolicyDao;
import com.yl.online.trade.hessian.bean.InterfacePolicyBean;
import com.yl.online.trade.hessian.bean.InterfacePolicyProfileBean;
import com.yl.online.trade.service.InterfacePolicyService;

/**
 * 路由模板服务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
@Service("interfacePolicyService")
public class InterfacePolicyServiceImpl implements InterfacePolicyService {
	private static final Logger logger = LoggerFactory.getLogger(InterfacePolicyServiceImpl.class);

	
	@Resource
	private InterfacePolicyDao interfacePolicyDao;
	
	@Override
	public void createInterfacePolicy(InterfacePolicyBean interfacePolicyBean) {
		InterfacePolicy interfacePolicy = new InterfacePolicy();
		interfacePolicy.setCode(interfacePolicyBean.getCode());
		interfacePolicy.setInterfaceType(InterfaceType.valueOf(interfacePolicyBean.getInterfaceType()));
		interfacePolicy.setName(interfacePolicyBean.getName());
		interfacePolicy.setProfiles(JsonUtils.toJsonString(interfacePolicyBean.getProfiles()));
		interfacePolicy.setStatus(InterfacePolicyStatus.valueOf(interfacePolicyBean.getStatus()));
		interfacePolicy.setCreateTime(new Date());
		interfacePolicyDao.createInterfacePolicy(interfacePolicy);
	}

	@Override
	public Page queryInterfacePolicyByPage(Page page) {
		Object interfacePolicyObj = interfacePolicyDao.queryInterfacePolicyByPage(page).getObject();
		if (interfacePolicyObj != null)
			interfacePolicyObj = JsonUtils.toJsonToObject(JsonUtils.toJsonString(interfacePolicyObj), List.class);
		page.setObject(interfacePolicyObj);
		return page;
	}

	@Override
	public InterfacePolicyBean queryInterfacePolicyByCode(String code) { 
		InterfacePolicy interfacePolicy = interfacePolicyDao.queryInterfacePolicyByCode(code);
		if (interfacePolicy == null) return null;
		InterfacePolicyBean interfacePolicyBean = new InterfacePolicyBean();
		interfacePolicyBean.setCode(interfacePolicy.getCode());
		interfacePolicyBean.setCreateTime(interfacePolicy.getCreateTime());
		interfacePolicyBean.setName(interfacePolicy.getName());
		interfacePolicyBean.setInterfaceType(interfacePolicy.getInterfaceType().toString());
		interfacePolicyBean.setProfiles(JsonUtils.toObject(interfacePolicy.getProfiles(), new TypeReference<List<InterfacePolicyProfileBean>>(){}));
		interfacePolicyBean.setStatus(interfacePolicy.getStatus().toString());
		return JsonUtils.toJsonToObject(interfacePolicyBean, InterfacePolicyBean.class);
	}

	@Override
	public void updateInterfacePolicy(InterfacePolicyBean interfacePolicyBean) {
		InterfacePolicy interfacePolicy = new InterfacePolicy();
		interfacePolicy.setCode(interfacePolicyBean.getCode());
		interfacePolicy.setInterfaceType(InterfaceType.valueOf(interfacePolicyBean.getInterfaceType()));
		interfacePolicy.setName(interfacePolicyBean.getName());
		interfacePolicy.setProfiles(JsonUtils.toJsonString(interfacePolicyBean.getProfiles()));
		interfacePolicy.setStatus(InterfacePolicyStatus.valueOf(interfacePolicyBean.getStatus()));
		
		// 根据编码查询原路由模板信息
		InterfacePolicy originalPolicy = interfacePolicyDao.queryInterfacePolicyByCode(interfacePolicy.getCode());
		interfacePolicy.setId(originalPolicy.getId());
		interfacePolicy.setCreateTime(originalPolicy.getCreateTime());
		interfacePolicyDao.updateInterfacePolicy(interfacePolicy);
	}

	@Override
	public List<InterfacePolicyBean> queryInterfacePolicyBy(Map<String, Object> params) {
		List<InterfacePolicy> interfaPolicys = interfacePolicyDao.queryInterfacePolicyBy(params);
		if (interfaPolicys == null) return null;
		return JsonUtils.toJsonToObject(JsonUtils.toJsonString(interfaPolicys), List.class);
	}
	
	@Override
	public List<InterfacePolicyBean> queryInterfacePolicyByInterfaceType(String interfaceType) {
		List<InterfacePolicy> interfaPolicys = interfacePolicyDao.queryInterfacePolicyByInterfaceType(interfaceType);
		if (interfaPolicys == null) return null;
		List<InterfacePolicyBean> beans = new ArrayList<InterfacePolicyBean>();
		for (InterfacePolicy interfacePolicy : interfaPolicys ) {
			InterfacePolicyBean bean = new InterfacePolicyBean();
			bean.setCode(interfacePolicy.getCode());
			bean.setInterfaceType(interfacePolicy.getInterfaceType().name());
			bean.setName(interfacePolicy.getName());
			bean.setProfiles(JsonUtils.toObject(interfacePolicy.getProfiles(), new TypeReference<List<InterfacePolicyProfileBean>>(){}));
			bean.setStatus(interfacePolicy.getStatus().toString());
			beans.add(bean);
		}
		return beans;
	}

	@Override
	public Object findAllInterfacePolicy(Page page, Map<String, Object> params) {
		String resultStr = "";
		List<InterfacePolicy> interfacePolicys = interfacePolicyDao.findAllInterfacePolicy(page,params);
		try {
			page.setObject(interfacePolicys);
			return page;
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return resultStr;
	
	}

}
