package com.yl.customer.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.yl.boss.api.enums.BulletinSysType;
import com.yl.customer.dao.BulletinDao;
import com.yl.customer.dao.EntityDao;
import com.yl.customer.entity.Bulletin;
import com.yl.customer.enums.BulletinType;

/**
 * 公告数据访问接口处理
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月3日
 * @version V1.0.0
 */
public class BulletinDaoImpl extends HibernateDaoSupport implements BulletinDao {
	private EntityDao entityDao;

	public Bulletin create(Bulletin bulletin) {
		entityDao.persist(bulletin);
		return bulletin;
	}

	public Bulletin update(Bulletin bulletin) {
		entityDao.update(bulletin);
		return bulletin;
	}

	public Bulletin findById(Long id) {
		return entityDao.findById(Bulletin.class, id);
	}

	@SuppressWarnings("rawtypes")
	public int bulletinCount() {
		String hql = "from Bulletin b where b.status = ? and b.effTime <=? and b.extTime >=? and b.publishSystem = ?";
		List list = entityDao.find(hql, BulletinType.TRUE, new Date(),
				new Date(), "AGENT");
		return list.size();
	}
	
	@SuppressWarnings("unchecked")
	public List<Bulletin> findBulletinBy(BulletinType bulletinType, BulletinSysType bulletinSysType, Date date) {
		String hql = "from Bulletin b where b.status = ? and b.sysCode=? and b.effTime <=? and b.extTime >=? order by b.id desc";
		List<Bulletin> list = entityDao.find(hql, bulletinType, bulletinSysType, date, date);
		return list;
	}
	
	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}
}
