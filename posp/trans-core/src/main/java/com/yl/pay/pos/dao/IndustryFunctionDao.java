package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.IndustryFunction;

import java.util.List;

/**
 * Title: IndustyFunction Dao
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */
public interface IndustryFunctionDao {
	
	//根据MCC查询
	public List<IndustryFunction> findByMcc(String mcc);
	
}
