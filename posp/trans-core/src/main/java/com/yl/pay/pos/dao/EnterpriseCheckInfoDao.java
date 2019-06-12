/**
 * 
 */
package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.EnterpriseCheckInfo;

/**
 * Title: 商户企业信息验证dao
 * Description: 
 * Copyright: Copyright (c)2014 
 * Company: com.yl.pay
 * 
 * @author zhendong.wu
 */
public interface EnterpriseCheckInfoDao {
	public EnterpriseCheckInfo findByCustomerNo(String customerNo);
}
