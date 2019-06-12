package com.yl.pay.pos.service.impl;

import com.yl.pay.pos.dao.ShopDao;
import com.yl.pay.pos.entity.Shop;
import com.yl.pay.pos.service.ShopService;

public class ShopServiceImpl implements ShopService{
	
	private ShopDao shopDao;
	
	@Override
	public Shop findById(Long id) {
		return shopDao.findById(id);
	}

	@Override
	public Shop findByShopNo(String shopNo) {
		return shopDao.findByShopNo(shopNo);
	}

	@Override
	public Shop create(Shop shop) {
		return shopDao.create(shop);
	}

	@Override
	public Shop update(Shop shop) {
		return shopDao.update(shop);
	}

	public ShopDao getShopDao() {
		return shopDao;
	}

	public void setShopDao(ShopDao shopDao) {
		this.shopDao = shopDao;
	}
}
