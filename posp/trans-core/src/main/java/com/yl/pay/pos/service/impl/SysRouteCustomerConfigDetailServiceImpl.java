package com.yl.pay.pos.service.impl;

import com.yl.pay.pos.dao.SysRouteCustomerConfigDetailDao;
import com.yl.pay.pos.entity.SysRouteCustomerConfigDetail;
import com.yl.pay.pos.enums.SysRouteCustConfDetailUseStatus;
import com.yl.pay.pos.service.ISysRouteCustomerConfigDetailService;

/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2014
 * Company: com.yl.pay
 * @author jun
 */
public class SysRouteCustomerConfigDetailServiceImpl implements
		ISysRouteCustomerConfigDetailService {
	private SysRouteCustomerConfigDetailDao sysRouteCustomerConfigDetailDao;

	@Override
	public void createRecover(SysRouteCustomerConfigDetail detail) {
		if(detail==null){
			return;
		}
		detail=sysRouteCustomerConfigDetailDao.findById(detail.getId());
		detail.setUseStatus(SysRouteCustConfDetailUseStatus.UN_CLAIM);
		sysRouteCustomerConfigDetailDao.update(detail);
	}

	public SysRouteCustomerConfigDetailDao getSysRouteCustomerConfigDetailDao() {
		return sysRouteCustomerConfigDetailDao;
	}

	public void setSysRouteCustomerConfigDetailDao(
			SysRouteCustomerConfigDetailDao sysRouteCustomerConfigDetailDao) {
		this.sysRouteCustomerConfigDetailDao = sysRouteCustomerConfigDetailDao;
	}

	
	
}
