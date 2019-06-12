package com.yl.boss.dao.impl;

import java.util.List;

import com.yl.boss.dao.EntityDao;
import com.yl.boss.dao.QRCodeDao;
import com.yl.boss.entity.License;

/**
 * QRCode数据接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public class QRCodeDaoImpl implements QRCodeDao {
	
	private EntityDao entityDao ;

	@Override
	public void sweepTheNetwork(License license) {
		entityDao.persist(license);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public License getInfo(int Id) {
		String hql = "FROM License WHERE id = ?";
		List<License> list = entityDao.find(hql, Id);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void updateNetwork(License license) {
		entityDao.update(license);
	}
}