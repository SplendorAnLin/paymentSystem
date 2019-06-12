package com.yl.online.trade.dao.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.yl.online.model.enums.AccountFailStatus;
import com.yl.online.model.model.AccountFailInfo;
import com.yl.online.trade.BaseTest;
import com.yl.online.trade.dao.AccountFailInfoDao;

/**
 * 入账结果信息数据实现测试
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月8日
 * @version V1.0.0
 */
public class AccountFailInfoDaoImplTest extends BaseTest {
	@Resource
	private AccountFailInfoDao accountDao;

	@Test
	public void testInsertAccountInfo() {
		AccountFailInfo accountFacadeInfo = new AccountFailInfo();
		accountFacadeInfo.setAccountStatus(AccountFailStatus.FAILED);
		accountFacadeInfo.setAmount(100.00);
		accountFacadeInfo.setOrderCode("TOSA20140418000003");
		accountDao.createAccountInfo(accountFacadeInfo);
	}

	@Test
	public void testUpdateAccountInfo() {
		// 查询需更新状态的交易数据
		List<AccountFailInfo> accountFacadeInfos = accountDao.queryAccountFacadeInfo(5, 200);
		for (AccountFailInfo accountFacadeInfo : accountFacadeInfos) {
			accountFacadeInfo.setAccountStatus(AccountFailStatus.SUCCESS);
			accountFacadeInfo.setAccountFacadeTime(new Date());
			accountFacadeInfo.setNextFireTime(new Date());
			accountDao.modifyAccountInfo(accountFacadeInfo);
		}
	}
}
