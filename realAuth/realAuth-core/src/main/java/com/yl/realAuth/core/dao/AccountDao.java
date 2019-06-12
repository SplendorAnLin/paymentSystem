package com.yl.realAuth.core.dao;

import java.util.List;

import com.lefu.commons.utils.Page;
import com.yl.realAuth.model.AccountPlusFailInfo;

/**
 * 实名认证账户信息管理
 * @author Shark
 * @since 2015年7月22日
 */
public interface AccountDao {
	/**
	 * 查询入账信息
	 * @param maxCount 最大次数
	 * @param page 分页实体
	 * @return
	 */
	List<AccountPlusFailInfo> queryAccountFacadeInfo(int maxCount, Page page);

	/**
	 * 更新入账状态
	 * @param accountFacadeInfo
	 */
	void modifyAccountInfo(AccountPlusFailInfo accountFacadeInfo);

	/**
	 * 新增入账信息
	 * @param accountFacadeInfo
	 */
	void createAccountInfo(AccountPlusFailInfo accountFacadeInfo);

	/**
	 * 根据订单号查询手续费退回失败信息
	 * @param orderCode 订单号
	 */
	AccountPlusFailInfo findAccountInfoByCode(String orderCode);
}
