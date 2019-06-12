package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.SingleAmountLimit;
import com.yl.pay.pos.enums.LimitAmountControlRole;

import java.util.List;


/**
 * Title:  
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun
 */

public interface SingleAmountLimitDao {
	//创建
	public SingleAmountLimit create(SingleAmountLimit singleAmountLimit);
	
	//更新
	public SingleAmountLimit update(SingleAmountLimit singleAmountLimit);
	
	//根据ID查找
	public SingleAmountLimit findById(Long id);
	
	//根据编号查找
	public SingleAmountLimit findByCode(String code);
	
	//根据持有者ID、控制角色、状态、生效时间查找
	public List<SingleAmountLimit> findByOwnerIdAndControlRoleAndStatus(String ownerId, LimitAmountControlRole controlRole);

	/**
	 * @param singleAmountLimit
	 */
	public void delete(SingleAmountLimit singleAmountLimit);
	
}
