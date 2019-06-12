package com.yl.boss.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yl.boss.dao.AdDao;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.entity.Ad;

/**
 * 广告管理业务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月11日
 * @version V1.0.0
 */
public class AdDaoImpl implements AdDao {

	
	private EntityDao entityDao;
	
	@Override
	public List<Ad> query(String hql) {
		List<Ad> list=new ArrayList<Ad>();
		try {
			list=entityDao.find(hql);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public List<Object> queryByadminId(String hql) {
		List<Object> list=new ArrayList<Object>();
		try {
			list=entityDao.find(hql);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public void save(Ad ad) {
		try {
			entityDao.save(ad);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public List<Ad> queryAdCodeByAdminId(String hql) {
		List<Ad> list=new ArrayList<Ad>();
		try {
			list=entityDao.find(hql);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public void update(Ad ad) {
		try {
			entityDao.update(ad);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Ad ad) {
		try {
			entityDao.delete(ad);
			} catch (Exception e) {
				e.printStackTrace();
		}
	}
	@Override
	public List<Ad> queryById(String hql) {
		List<Ad> list=new ArrayList<Ad>();
		try {
			list=entityDao.find(hql);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}


	@Override
	public int selectCount() {
		String hql="from Ad ";		
		List<Ad> list=new ArrayList<Ad>();
		try {
			list=entityDao.find(hql);
			return list.size();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}


	

}
