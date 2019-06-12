package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.CustomerTransFunction;

/**
 * Title: 
 * Description:   
 * Copyright: 2015年7月6日上午10:18:10
 * Company: SDJ
 * @author zhongxiang.ren
 */
public interface CustomerTransFunctionDao {
	/**
	 * 根据商户号查询商户级权限
	 * @param customerNo
	 * @return
	 */
	public CustomerTransFunction findByCustomerNo(String customerNo);
}
