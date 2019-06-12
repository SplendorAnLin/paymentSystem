package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.AccumulatedAmountLimit;
import com.yl.pay.pos.enums.LimitAmountControlRole;

import java.util.List;


/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun
 */

public interface AccumulatedAmountLimitDao {
	
	//创建
	public AccumulatedAmountLimit create(AccumulatedAmountLimit accumulatedAmountLimit);
	//更新
	public AccumulatedAmountLimit update(AccumulatedAmountLimit accumulatedAmountLimit);
	
	public AccumulatedAmountLimit findById(Long id);
	
	public AccumulatedAmountLimit findByCode(String code);
	
	//根据持有者ID、控制角色、状态、生效时间查找
	public List<AccumulatedAmountLimit> findByOwnerIdAndControlRoleAndStatus(String ownerId, LimitAmountControlRole controlRole);

	/**
	 * @param accumulatedAmountLimit
	 */
	public void delete(AccumulatedAmountLimit accumulatedAmountLimit);
	
}
