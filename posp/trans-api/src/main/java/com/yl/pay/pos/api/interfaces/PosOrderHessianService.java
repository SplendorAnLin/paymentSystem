package com.yl.pay.pos.api.interfaces;

import java.util.Date;
import java.util.Map;

public interface PosOrderHessianService{
	Map<String, Object> findOrderById(Long id,String currentPage);
	
	public Map<String, Object> posOrderSum(String type,String no,Map<String,Object> params);
	
	void posRoute(String customerNo, String transType);
	
	public Map<String, Object> findOrderByCode(String externalId);
	
	public String orderSum(Date orderTimeStart, Date orderTimeEnd, String owner);
}