package com.yl.dpay.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yl.dpay.core.dao.ServiceConfigHistoryDao;
import com.yl.dpay.core.entity.ServiceConfigHistory;
import com.yl.dpay.core.service.ServiceConfigHistoryService;

/**
 * 代付配置历史服务信息业务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月13日
 * @version V1.0.0
 */
@Service
public class ServiceConfigHistoryServiceImpl implements ServiceConfigHistoryService {
	@Autowired
	private ServiceConfigHistoryDao	serviceConfigHistoryDao;
	@Override
	@Transactional
	public void insert(ServiceConfigHistory serviceConfigHistory) {
		serviceConfigHistoryDao.insert(serviceConfigHistory);
	}

}
