/**
 * 
 */
package com.yl.account.core.service;

import com.yl.account.model.Account;
import com.yl.account.model.AccountBehavior;

/**
 * 账户行为逻辑处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月14日
 * @version V1.0.0
 */
public interface AccountBehaviorService {

	/**
	 * @Description 账号获取账户属性信息
	 * @param accountNo 账号
	 * @return AccountBehavior 账户属性
	 * @see 需要参考的类或方法
	 */
	AccountBehavior findAccountBehavior(String accountNo);

	/**
	 * @Description 账户预冻次数递减
	 * @param accountBehavior 账户属性实体
	 * @see 需要参考的类或方法
	 */
	void addOrSubtractPreFreezeCount(AccountBehavior accountBehavior);

	/**
	 * @Description 持久化账户属性
	 * @param account 账户实体
	 * @see 需要参考的类或方法
	 */
	void insert(Account account);

}
