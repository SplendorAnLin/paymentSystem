package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.TransRouteConfig;
import com.yl.pay.pos.enums.RouteType;

/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2012
 * Company: com.yl.pay
 * @author jun
 */

public interface TransRouteConfigDao {

	//根据商户编号查找 
	public TransRouteConfig findByCustomerNo(String customerNo);
	
	/**
	 * 根据商户号和路由类型查找
	 * @param customerNo
	 * @param routeType
	 * @return
	 */
	public TransRouteConfig findByCustomerNoAndRouteType(String customerNo,RouteType routeType);
	
	public TransRouteConfig create(TransRouteConfig config);
	
}
