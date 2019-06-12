package com.yl.account.core.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yl.account.core.dao.AccountDayDetailDao;
import com.yl.account.core.dao.mapper.AccountDayDetailMapper;
import com.yl.account.model.AccountDayDetail;

/**
 * 商户余额收支明细表接口实现类
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
@Repository("accountDayDetailDao")
public class AccountDayDetailDaoImpl implements AccountDayDetailDao {

	@Resource
	AccountDayDetailMapper accountDayDetailMapper;

	@Override
	public void insert(List<AccountDayDetail> listAccountDayDetail) {
		accountDayDetailMapper.insert(listAccountDayDetail);
	}
}
