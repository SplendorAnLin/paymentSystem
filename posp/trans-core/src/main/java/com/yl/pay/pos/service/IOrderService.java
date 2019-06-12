package com.yl.pay.pos.service;

import java.util.List;
import java.util.Map;

import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.entity.Order;

/**
 * Title: 订单 服务
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public interface IOrderService {
	/**
	 * 创建订单
	 * @param extendPayBean
	 * @return
	 */
	public ExtendPayBean create(ExtendPayBean extendPayBean) throws Exception ;
	/**
	 * 根据请求号获取订单
	 * @param externalId
	 * @return
	 */
	public Order findByCode(String externalId);
	/**
	 * 完成订单
	 * @param extendPayBean
	 * @return
	 */
	public ExtendPayBean complete(ExtendPayBean extendPayBean) ;	
	/**
	 * 更新订单
	 * @param order
	 * @return
	 */
	public Order update(Order order);
	/**
	 * 根据id查询订单
	 * @param id
	 * @return
	 */
	public Order findById(Long id);
	
	/**
	 * 查询POS订单合计
	 * @param type (boss || agent || customer)
	 * @param no (null || agentNo || customerNo)
	 * @param params (查询条件)
	 * @return
	 */
	public Map<String, Object> posOrderSum(String type,String no,Map<String,Object> params);
	
	/**
	 * 条件查询所有订单
	 * @param hql
	 * @return
	 */
	String orderSum(String orderTimeStart, String orderTimeEnd, String owner);
}