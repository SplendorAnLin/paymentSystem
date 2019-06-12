package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.BankRequest;

/**
 * Title: BankRequest Dao
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */
public interface BankRequestDao {

	//创建
	public BankRequest create(BankRequest bankRequest);
	
	//更新
	public BankRequest update(BankRequest bankRequest);	
}
