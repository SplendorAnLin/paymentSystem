package com.pay.sign.dao;

import com.pay.sign.dbentity.TransResponseDict;

/**
 * Title: 
 * Description: 
 * Copyright: (c)2015
 * Company: pay
 * @author zhongxiang.ren
 */

public interface TransResponseDictDao {
	
	public TransResponseDict create(TransResponseDict transResponseDict);
	
	/**
	 * 根据异常码查找
	 */
	public TransResponseDict findByExceptionCode(String exceptionCode);
	
}

