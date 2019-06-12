package com.yl.pay.pos.service;

import com.yl.pay.pos.entity.Shop;

/**
 * Title: Shop Dao
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */
public interface ShopService {

	//根据ID查询
	public Shop findById(Long id);
	
	//根据网点编号查询
	public Shop findByShopNo(String shopNo);

	//创建
	public Shop create(Shop shop);
	
	//更新
	public Shop update(Shop shop);	
	
}
