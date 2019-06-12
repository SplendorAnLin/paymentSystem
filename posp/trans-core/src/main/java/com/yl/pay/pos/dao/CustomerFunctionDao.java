package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.*;

import java.util.List;

/**
 * Title: Order Dao
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */
public interface CustomerFunctionDao {
	public CustomerFunction create(CustomerFunction customerFunction);
	
	//根据商户ID查询
	public List<CustomerFunction> findByCustomerId(Long customerId);
	
	public CustomerFunction findBy(Long customerId, String trsanType, String cardType);
	
	public CustomerFunction update(CustomerFunction customerFunction);

	public void delete(Long id);
	
}
