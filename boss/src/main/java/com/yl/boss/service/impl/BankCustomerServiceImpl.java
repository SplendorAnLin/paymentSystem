package com.yl.boss.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.lefu.commons.utils.Page;
import com.yl.boss.dao.BankCustomerDao;
import com.yl.boss.entity.BankCustomer;
import com.yl.boss.service.BankCustomerService;

public class BankCustomerServiceImpl implements BankCustomerService {

	@Resource
	private BankCustomerDao bankCustomerDao;
	@Override
	public List<BankCustomer> findByOrg(Page<BankCustomer> page, String org, String category) {
		List<BankCustomer> list= bankCustomerDao.findByOrg(page, org, category);
		return list;
	}
	@Override
	public Integer findCountByOrg(String org, String category) {
		return bankCustomerDao.findCountByOrg(org, category);
	}
	@Override
	public BankCustomer findByCustomerNo(String bankCustomerNo) {
		// TODO Auto-generated method stub
		return bankCustomerDao.findByBankCustomerNo(bankCustomerNo);
	}

}
