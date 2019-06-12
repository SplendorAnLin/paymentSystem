package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.CustomerTransSort;

/**
 * Title: 
 * Description:   
 * Copyright: 2015年7月6日上午10:17:51
 * Company: SDJ
 * @author zhongxiang.ren
 */
public interface CustomerTransSortDao {
	/**
	 * 根据序列和交易类型查询
	 * @param sort
	 * @param transType
	 * @return
	 */
	public CustomerTransSort findBySortAndTransType(String sort, String transType);
}
