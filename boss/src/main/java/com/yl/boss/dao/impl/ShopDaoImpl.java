package com.yl.boss.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.codehaus.jackson.type.TypeReference;
import org.hibernate.mapping.Map;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.dao.ShopDao;
import com.yl.boss.entity.Customer;
import com.yl.boss.entity.MccInfo;
import com.yl.boss.entity.Shop;
import com.yl.boss.enums.CustomerStatus;
import com.yl.boss.enums.Status;

/**
 * 网点数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2017年6月15日
 * @version V1.0.0
 */
public class ShopDaoImpl implements ShopDao {

	@Resource
	EntityDao entityDao;
	
	@Override
	public void shopAdd(Shop shop) {
		entityDao.persist(shop);
	}

	@Override
	public Shop shopById(Long id) {
		String hql = "from Shop s,Customer c where s.customer.customerNo = c.customerNo and s.id = ?";
		List list = entityDao.find(hql,id);
		if(list != null && list.size() > 0){
			Object[] obj = (Object[]) list.get(0);
			return (Shop) obj[0];
		}
		return null;
	}
	
	@Override
	public String queryMaxShopNo(String customerNo) {
		String hql = "select shopNo from Shop where CUSTOMER_NO = ? order by SHOP_NO DESC";
		List list = entityDao.find(hql,customerNo);
		if(list != null && list.size() > 0){
			return (String)list.get(0);
		}
		return null;
	}

	@Override
	public void ShopUpdate(Shop shop) {
		entityDao.update(shop);
	}

	@Override
	public List<Shop> queryShopList(String customerNo) {
		String hql = "from Shop s,Customer c where s.customer.customerNo = c.customerNo and c.customerNo = ?";
		List list = entityDao.find(hql,customerNo);
		
		if(list != null && list.size() > 0){
			
			List<Shop> shopList = new ArrayList<>();
			
			for (Object o : list) {
				
				shopList.add(JsonUtils.toJsonToObject(JsonUtils.toJsonToObject(o, Object[].class)[0], Shop.class));
				
			}
			return shopList;
		}
		return null;
	}

	@Override
	public Shop queryByShopNo(String shopNo) {
		String hql = "from Shop where shopNo = ? ";
		List<Shop> list = entityDao.find(hql,shopNo);
		
		if(list != null && list.size() > 0){
			
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Shop> findSyncShop() {
		String hql = "from Shop where issync=?";
		List<Shop> list = entityDao.find(hql,Status.FALSE);
		return list;
	}

	@Override
	public String queryShopNameByShopNo(String shopNo) {
		return (String) entityDao.find("select shopName from Shop where shopNo = ?",shopNo).get(0);
	}

	@Override
	public List<String> queryShopNoByShopCustNo(String customerNo) {
		return entityDao.find("select shopNo from Shop where customer.customerNo = ?", customerNo);
	}

	@Override
	public void shopDelByShopNo(Shop shop) {
		entityDao.delete(shop);
	}

	@Override
	public Shop queryShopByCustNo(String customerNo) {
		String hql = "from Shop where customer.customerNo = ?";
		List<Shop> list = entityDao.find(hql,customerNo);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

}
