package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.PosResponseDict;

/**
 * Title: BankResponseDictDao Dao
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */
public interface PosResponseDictDao {

	//根据返回码查询
	public PosResponseDict findByResponseCode(String responseCode);

}
