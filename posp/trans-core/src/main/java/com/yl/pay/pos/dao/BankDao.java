package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.Bank;

/**
 * Title: Bank Dao
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */
public interface BankDao {
	
	//根据银行编码查询
	public Bank findByCode(String code);	
	
}
