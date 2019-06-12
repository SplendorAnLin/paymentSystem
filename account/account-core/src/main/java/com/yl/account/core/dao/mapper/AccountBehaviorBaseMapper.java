/**
 * 
 */
package com.yl.account.core.dao.mapper;

import org.apache.ibatis.annotations.Param;

import com.yl.account.model.AccountBehavior;

/**
 * 账户行为数据映射
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public interface AccountBehaviorBaseMapper {

	/**
	 * @Description 账号获取账户属性
	 * @param accountNo 账号
	 * @return AccountBehavior 账户属性
	 * @see 需要参考的类或方法
	 */
	AccountBehavior findAccountBehaviorBy(String accountNo);

	/**
	 * @Description 持久化账户属性
	 * @param accountBehavior 账户属性实体
	 * @see 需要参考的类或方法
	 */
	void insert(AccountBehavior accountBehavior);

	/**
	 * @Description 账户预冻次数递增或递减
	 * @param accountBehavior 账户属性实体
	 * @param newVersion 新版本号
	 * @return int 更新结果
	 * @see 需要参考的类或方法
	 */
	int addOrSubtractPreFreezeCount(@Param("accountBehavior") AccountBehavior accountBehavior, @Param("newVersion") long newVersion);

}
