package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.ShopDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.Shop;

/**
 * Title: ShopDaoImpl
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class ShopDaoImpl implements ShopDao{

	public EntityDao entityDao;
	
	public Shop create(Shop shop) {
		entityDao.persist(shop);
		return shop;
	}

	public Shop findById(Long id) {		
		return entityDao.findById(Shop.class, id);
	}

	public Shop update(Shop shop) {
		return entityDao.merge(shop);
	}

	public Shop findByShopNo(String shopNo) {
		String hql="from Shop where shopNo=?";
		return entityDao.findUnique(Shop.class,hql, shopNo);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}
}
