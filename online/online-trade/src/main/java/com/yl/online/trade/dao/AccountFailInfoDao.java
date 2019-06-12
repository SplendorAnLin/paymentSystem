package com.yl.online.trade.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yl.online.model.model.AccountFailInfo;

/**
 * 交易系统入账DAO层服务
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
public interface AccountFailInfoDao {
	/**
	 * 查询入账信息
	 * @param maxCount
	 * @param maxNums
	 * @return
	 */
	List<AccountFailInfo> queryAccountFacadeInfo(@Param("maxCount")int maxCount, @Param("maxNums")int maxNums);

	/**
	 * 更新入账状态
	 * @param accountFacadeInfo
	 */
	void modifyAccountInfo(@Param("accountFacadeInfo")AccountFailInfo accountFacadeInfo);

	/**
	 * 新增入账信息
	 * @param accountFacadeInfo
	 */
	void createAccountInfo(AccountFailInfo accountFacadeInfo);

}
