package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.TransRoute;

/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2012
 * Company: com.yl.pay
 * @author jun
 */

public interface TransRouteDao {
	//根据编号查找，默认获取可用状态的结果 
	public TransRoute findByCode(String code);
	
	void save(TransRoute transRoute);
}