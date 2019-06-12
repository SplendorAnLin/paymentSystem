package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.TransRouteProfile;

import java.util.List;

/**
 * Title: 	
 * Description: 
 * Copyright: Copyright (c)2012
 * Company: com.yl.pay
 * @author jun
 */

public interface TransRouteProfileDao {
	
	//根据路由模版编号查询，默认获取状态为可用的
	public List<TransRouteProfile> findByTransRouteCode(String transRouteCode);
	
}
