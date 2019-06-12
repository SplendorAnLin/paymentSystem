package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.BankInterface;
import com.yl.pay.pos.enums.Status;

/**
 * Title: BankInterface Dao
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */
public interface BankInterfaceDao {

	//根据ID查询
	public BankInterface findById(Long id);
	
	public BankInterface findByCode(String code);
	
	//根据编号查询
	public BankInterface findByCodeAndStatus(String code, Status status);
	
	//创建
	public BankInterface create(BankInterface bankInterface);
	
	//更新
	public BankInterface update(BankInterface bankInterface);	
	
	
}
