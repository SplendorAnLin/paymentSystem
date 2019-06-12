package com.yl.online.trade.service;

import java.util.List;

import com.yl.online.model.model.AccountFailInfo;

/**
 * 入账服务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
public interface AccountFailInfoService {
	/**
	 * 查询入账失败的订单信息
	 * @param maxCount
	 * @param maxNums
	 * @return
	 */
	List<AccountFailInfo> queryAccountFacadeInfo(int maxCount, int maxNums);

	/**
	 * 更新入账状态
	 * @param accountFacadeInfo
	 */
	void modifyAccountInfo(AccountFailInfo accountFacadeInfo);

	/**
	 * 新增入账信息
	 * @param accountFacadeInfo
	 */
	void createAccountInfo(AccountFailInfo accountFacadeInfo);

}
