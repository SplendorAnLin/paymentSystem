package com.yl.boss.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.boss.dao.ShopDao;
import com.yl.boss.entity.Shop;
import com.yl.boss.enums.Status;
import com.yl.boss.enums.SyncType;
import com.yl.boss.service.ShopService;
import com.yl.boss.service.SyncInfoService;

/**
 * 网点业务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2017年6月15日
 * @version V1.0.0
 */
public class ShopServiceImpl implements ShopService {

	@Resource
	ShopDao shopDao;
	
	@Resource
	SyncInfoService syncInfoService;
	
	@Override
	public void shopAdd(Shop shop) {
		try {
			shop.setCreateTime(new Date());
			shopDao.shopAdd(shop);
			syncInfoService.syncInfoAddFormPosp(SyncType.SHOP_SYNC, JsonUtils.toJsonString(shop), Status.TRUE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Shop shopById(Long id) {
		return shopDao.shopById(id);
	}
	
	@Override
	public String queryMaxShopNo(String customerNo) {
		return shopDao.queryMaxShopNo(customerNo);
	}

	@Override
	public void ShopUpdate(Shop shop) {
		try {
			Shop s = shopDao.shopById(shop.getId());
			if(s != null){
				s.setShopName(shop.getShopName());
				s.setPrintName(shop.getPrintName());
				s.setBindPhoneNo(shop.getBindPhoneNo());
				s.setStatus(shop.getStatus());
				s.setCustomer(shop.getCustomer());
				shopDao.ShopUpdate(s);
				syncInfoService.syncInfoAddFormPosp(SyncType.SHOP_SYNC, JsonUtils.toJsonString(s), Status.FALSE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Shop> queryShopList(String customerNo) {
		return shopDao.queryShopList(customerNo);
	}

	@Override
	public Shop queryByShopNo(String shopNo) {
		return shopDao.queryByShopNo(shopNo);
	}

	@Override
	public String queryShopNameByShopNo(String shopNo) {
		return shopDao.queryShopNameByShopNo(shopNo);
	}

	@Override
	public List<String> queryShopNoByShopCustNo(String customerNo) {
		return shopDao.queryShopNoByShopCustNo(customerNo);
	}

	@Override
	public void shopDelByShopNo(String shopNo) {
		Shop shop = shopDao.queryByShopNo(shopNo);
		if(shop != null){
			shopDao.shopDelByShopNo(shop);
		}
	}

	@Override
	public Shop queryShopByCustNo(String customerNo) {
		return shopDao.queryShopByCustNo(customerNo);
	}

}
