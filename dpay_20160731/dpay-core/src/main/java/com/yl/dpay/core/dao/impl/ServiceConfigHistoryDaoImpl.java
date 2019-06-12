package com.yl.dpay.core.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yl.dpay.core.dao.ServiceConfigHistoryDao;
import com.yl.dpay.core.entity.ServiceConfigHistory;
import com.yl.dpay.core.mybatis.mapper.ServiceConfigHistoryMapper;

/**
 * 代付配置历史服务信息数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月13日
 * @version V1.0.0
 */
@Repository("serviceConfigHistoryDao")
public class ServiceConfigHistoryDaoImpl implements ServiceConfigHistoryDao {
	@Resource
	private ServiceConfigHistoryMapper serviceConfigHistoryMapper;
	@Override
	public void insert(ServiceConfigHistory t) {
		serviceConfigHistoryMapper.insert(t);
	}

	@Override
	public ServiceConfigHistory findById(Long id) {
		return serviceConfigHistoryMapper.findById(id);
	}

	@Override
	public void update(ServiceConfigHistory t) {
		serviceConfigHistoryMapper.update(t);
	}

}
