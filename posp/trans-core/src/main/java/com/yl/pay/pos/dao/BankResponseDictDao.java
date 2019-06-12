package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.BankResponseDict;

/**
 * Title: BankResponseDictDao Dao
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */
public interface BankResponseDictDao {

	//根据银行编码，返回码查询
	public BankResponseDict findByCode(String interfaceCode, String responseCode);
}
