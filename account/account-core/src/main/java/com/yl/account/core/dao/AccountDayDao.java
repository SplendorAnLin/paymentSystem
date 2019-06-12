package com.yl.account.core.dao;

import com.yl.account.model.AccountDay;

/**
 * 商户余额表DAO接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月22日
 * @version V1.0.0
 */
public interface AccountDayDao {

	/**
	 * @Description 持久化商户余额收支信息
	 * @param accountDay 商户余额收支信息
	 * @see 需要参考的类或方法
	 */
	public void insert(AccountDay accountDay);

}
