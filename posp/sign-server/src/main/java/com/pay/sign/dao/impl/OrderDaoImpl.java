package com.pay.sign.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.stereotype.Component;

import com.pay.sign.dao.OrderDao;
import com.pay.sign.dao.util.EntityDao;
import com.pay.sign.dbentity.Order;

@Component("orderDao")
public class OrderDaoImpl implements OrderDao {

	@Resource
	private EntityDao entityDao;

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	@Override
	public Order getOrder(String orderNo,int limit) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Order.class).
				add(Property.forName("externalId").eq(orderNo));
		List<Order> list = entityDao.findByCriteria(criteria, 0, limit);
		if( list == null || list.size()==0){
			return null;
		}
		return list.get(0);
	}

	@Override
	public String getShopNO(Long shopId) {
		
		String hql = "select shopNo from Shop where id=?";
		List list = entityDao.find(hql, shopId);
		if(list == null || list.isEmpty()){
			return null;
		}else{
			return (String)list.get(0) ;
		}
		
	}
	

}