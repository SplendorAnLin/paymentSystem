package com.yl.boss.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.yl.boss.api.enums.BulletinSysType;
import com.yl.boss.api.enums.BulletinType;
import com.yl.boss.api.utils.Page;
import com.yl.boss.dao.BulletinDao;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.entity.Bulletin;

/**
 * 公告数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月11日
 * @version V1.0.0
 */
public class BulletinDaoImpl implements BulletinDao {
	private EntityDao entityDao;

	public Bulletin create(Bulletin bulletin) {
		entityDao.persist(bulletin);
		return bulletin;
	}
	

	public void update(Bulletin bulletin) {
		entityDao.update(bulletin);
	}

	public Bulletin findById(Long id) {
		return entityDao.findById(Bulletin.class, id);
	}
	
	@SuppressWarnings("rawtypes")
	public int findBulletinCount(BulletinType bulletinType, BulletinSysType bulletinSysType, Date date) {
		String hql = "from Bulletin b where b.status = ? and b.sysCode=? and b.effTime <=? and b.extTime >=?";
		List list = entityDao.find(hql, bulletinType, bulletinSysType, date, date);
		return list.size();
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	@SuppressWarnings("unchecked")
	public List<Bulletin> findBulletinBy(BulletinType bulletinType, BulletinSysType bulletinSysType, Date date,Page page) {
		//String hql = "from Bulletin b where b.status = ? and b.sysCode=? and b.effTime <=? and b.extTime >=? order by b.id desc";
		//entityDao.queryListBySqlQuery(hql, bulletinType, bulletinSysType, date, date);
		try{
		List<Bulletin> list =new ArrayList<Bulletin>();
		DetachedCriteria dc=DetachedCriteria.forClass(Bulletin.class);
		if(bulletinType!=null){
			dc.add(Restrictions.eq("status", bulletinType));
		}
		if(bulletinSysType!=null){
			dc.add(Restrictions.eq("sysCode",bulletinSysType));
		}
		if(date!=null){
			dc.add(Restrictions.le("effTime", date))
			.add(Restrictions.ge("extTime",date));
		}
		if(page!=null){
			dc.addOrder(Order.desc("id"));
			list =entityDao.findByCriteria(dc,(page.getCurrentPage()-1)*page.getShowCount(),page.getCurrentPage()*page.getShowCount());
		}else{
			list =entityDao.findByCriteria(dc,false);
		}
		return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}
}
