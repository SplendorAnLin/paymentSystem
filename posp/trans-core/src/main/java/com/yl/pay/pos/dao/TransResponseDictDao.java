package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.TransResponseDict;

/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public interface TransResponseDictDao {
	
	public TransResponseDict create(TransResponseDict transResponseDict);
	
	/**
	 * 根据异常码查找
	 */
	public TransResponseDict findByExceptionCode(String exceptionCode);
	
}

