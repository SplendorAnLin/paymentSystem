package com.yl.realAuth.core.service;

import java.util.List;

import com.lefu.commons.utils.Page;
import com.yl.realAuth.model.AccountPlusFailInfo;

/**
 * 账户调增操作服务管理
 * @author Shark
 * @since 2015年7月22日
 */
public interface AccountPlusOperateService {
	/**
	 * 查询手续费退回失败的订单信息
	 * @param maxCount 最大退回次数
	 * @param page 分页信息
	 * @return
	 */
	List<AccountPlusFailInfo> queryAccountFacadeInfo(int maxCount, Page page);

	/**
	 * 更新入账状态
	 * @param accountPlusFailInfo
	 */
	void modifyAccountInfo(AccountPlusFailInfo accountPlusFailInfo);

	/**
	 * 新增入账信息
	 * @param accountPlusFailInfo
	 */
	void createAccountInfo(AccountPlusFailInfo accountPlusFailInfo);
}
