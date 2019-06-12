package com.yl.pay.pos.task;

import com.pay.common.util.DateUtil;
import com.yl.pay.pos.dao.OrderDao;
import com.yl.pay.pos.dao.PosDao;
import com.yl.pay.pos.entity.Order;
import com.yl.pay.pos.entity.Pos;
import com.yl.pay.pos.enums.YesNo;
import com.yl.pay.pos.http.HttpClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Title: 订单同步管理
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author zz
 */
public class OrderSycnManage {
	
	private static final Log log = LogFactory.getLog(OrderSycnManage.class);
	
	private OrderDao orderDao;
	
	private PosDao posDao;
	
	public void sycnHandle(){
		log.info("--------------sycnHandle start on: " + DateUtil.formatDate(new Date()));
		
			String url ="http://115.29.163.163:8080/YangLianPay/ylBPosCallBackServlet";
			List<Order> orders = orderDao.findByUnSycn();
			for(Order order:orders){
				try{
					Pos pos = posDao.findByPosCati(order.getPosCati());
					Map<String, String> params = new HashMap<String, String>(); 
					params.put("returnCode", order.getResponCode());
					params.put("orderId", order.getExternalId());  
					params.put("type", pos.getPosType());
					params.put("CardNo", order.getPan());
					params.put("deviceId", pos.getPosSn());
					params.put("createTime", DateUtil.formatDate(order.getCreateTime()));
					params.put("amount", String.valueOf(order.getAmount()));
				} catch (Exception e) {
					log.info("------sycnHandle fail"+e);
					e.printStackTrace();
				}
			}
			
		
		log.info("--------------sycnHandle end on: " + DateUtil.formatDate(new Date()));
	}


	public OrderDao getOrderDao() {
		return orderDao;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public PosDao getPosDao() {
		return posDao;
	}

	public void setPosDao(PosDao posDao) {
		this.posDao = posDao;
	}

}
