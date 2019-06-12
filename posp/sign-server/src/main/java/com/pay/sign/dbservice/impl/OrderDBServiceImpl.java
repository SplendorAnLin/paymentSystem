package com.pay.sign.dbservice.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pay.sign.dao.OrderDao;
import com.pay.sign.dbentity.Order;
import com.pay.sign.dbservice.IOrderDBService;
@Component("orderDBService")
public class OrderDBServiceImpl implements IOrderDBService {
	@Resource
	private OrderDao orderDao;

	@Override
	public Order getOrder(String orderNo) {
		
		return orderDao.getOrder(orderNo,2);
	}

	@Override
	public String getShopNO(Long shopId) {

		return orderDao.getShopNO(shopId);
	}

}
