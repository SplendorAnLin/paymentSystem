package com.yl.dpay.core.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yl.dpay.core.dao.ServiceConfigDao;
import com.yl.dpay.core.entity.ServiceConfig;
import com.yl.dpay.core.enums.FireType;
import com.yl.dpay.core.mybatis.mapper.ServiceConfigMapper;
import com.yl.dpay.hessian.beans.ServiceConfigBean;

/**
 * 代付配置服务数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月13日
 * @version V1.0.0
 */
@Repository("serviceConfigDao")
public class ServiceConfigDaoImpl implements ServiceConfigDao {

	@Resource
	private ServiceConfigMapper serviceConfigMapper;
	
	@Override
	public void insert(ServiceConfig t) {
		serviceConfigMapper.insert(t);
	}

	@Override
	public ServiceConfig findById(Long id) {
		return serviceConfigMapper.findById(id);
	}

	@Override
	public ServiceConfig find(String ownerId, String valid) {
		return serviceConfigMapper.find(ownerId, valid);
	}

	@Override
	public void update(ServiceConfig ServiceConfig) {
		serviceConfigMapper.update(ServiceConfig);
	}

	@Override
	public ServiceConfig findByOwnerId(String ownerId) {
		return serviceConfigMapper.findByOwnerId(ownerId);
	}

	@Override
	public boolean updateComplexPwd(ServiceConfig ServiceConfig) {
		return serviceConfigMapper.updateComplexPwd(ServiceConfig);
	}

	@Override
	public void updateServiceConfig(ServiceConfigBean serviceConfigBean) {
		
	}

	@Override
	public ServiceConfigBean findByPhone(String phone) {
		return serviceConfigMapper.findByPhone(phone);
	}

	@Override
	public List<ServiceConfig> findByFireType(FireType fireType, String valid) {
		return serviceConfigMapper.findByFireType(fireType, valid);
	}
}
