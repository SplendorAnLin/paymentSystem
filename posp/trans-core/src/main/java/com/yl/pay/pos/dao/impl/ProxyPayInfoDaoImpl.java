package com.yl.pay.pos.dao.impl;

import java.util.Date;
import java.util.List;

import com.yl.pay.pos.dao.ProxyPayInfoDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.ProxyPayInfo;
import com.yl.pay.pos.enums.ProxyPayStatus;


/**
 * Title: BankChannelDaoImpl
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class ProxyPayInfoDaoImpl implements ProxyPayInfoDao{

	private EntityDao entityDao;
	
	public ProxyPayInfo create(ProxyPayInfo proxyPayInfo) {
		entityDao.persist(proxyPayInfo);
		return proxyPayInfo;
	}

	public ProxyPayInfo findById(Long id) {
		return entityDao.findById(ProxyPayInfo.class, id);
	}

	public ProxyPayInfo update(ProxyPayInfo proxyPayInfo) {
		return entityDao.merge(proxyPayInfo);
	}
	
	@Override
	public List<ProxyPayInfo> findByOrderNo(String orderNo) {
		String hql="from ProxyPayInfo p where p.orderNo = ?  ";
		return entityDao.find(hql, orderNo);
	}
	

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	@Override
	public List<ProxyPayInfo> findListByStatusAndCreateTime(Date start , Date end) {
		
		String hql="from ProxyPayInfo p where p.status = ? and p.createTime >=? and p.createTime <= ?";
		return entityDao.find(hql, ProxyPayStatus.PAY_RESP_FAIL,start,end);
	}
	
}

