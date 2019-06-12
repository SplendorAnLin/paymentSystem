package com.yl.online.trade.dao.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yl.online.model.model.AccountFailInfo;
import com.yl.online.trade.dao.AccountFailInfoDao;
import com.yl.online.trade.dao.mapper.AccountFailInfoMapper;
import com.yl.online.trade.utils.CodeBuilder;

/**
 * 交易系统入账DAO层服务实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
@Repository("accountDao")
public class AccountFailInfoDaoImpl implements AccountFailInfoDao {
	@Resource
	private AccountFailInfoMapper accountFailInfoMapper;

	@Override
	public List<AccountFailInfo> queryAccountFacadeInfo(int maxCount, int maxNums) {
		return accountFailInfoMapper.queryAccountFacadeInfo(maxCount, maxNums);
	}

	@Override
	public void modifyAccountInfo(AccountFailInfo accountFacadeInfo) {
		accountFailInfoMapper.modifyAccountInfo(accountFacadeInfo);
	}

	@Override
	public void createAccountInfo(AccountFailInfo accountFacadeInfo) {
		accountFacadeInfo.setAccountCount(0);
		accountFacadeInfo.setCode(CodeBuilder.build("ACF", "yyyyMMdd", 6));
		accountFacadeInfo.setCreateTime(new Date());
		accountFacadeInfo.setVersion(System.currentTimeMillis());
		accountFailInfoMapper.createAccountInfo(accountFacadeInfo);
	}

}
