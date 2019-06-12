package com.yl.customer.dao.impl;

import java.util.List;

import com.yl.customer.dao.BulletinReadDao;
import com.yl.customer.dao.EntityDao;
import com.yl.customer.entity.BulletinRead;

/**
 * 公告读取数据访问接口处理
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月3日
 * @version V1.0.0
 */
public class BulletinReadDaoImpl implements BulletinReadDao {
	private EntityDao entityDao;

	public void persist(BulletinRead bulletinRead) {
		entityDao.persist(bulletinRead);
	}

	public void remove(BulletinRead bulletinRead) {
		entityDao.remove(bulletinRead);
	}

	@SuppressWarnings("unchecked")
	public List<BulletinRead> getBySysCode(String sysCode,
			Long id) {
		String hql = "from BulletinRead where and sysCode = ? and bulletinId = ?";
		return entityDao.find(hql, sysCode, id);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
