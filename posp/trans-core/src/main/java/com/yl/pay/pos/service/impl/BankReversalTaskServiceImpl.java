package com.yl.pay.pos.service.impl;

import com.yl.pay.pos.dao.BankReversalTaskDao;
import com.yl.pay.pos.entity.BankReversalTask;
import com.yl.pay.pos.enums.BankReversalTaskStatus;
import com.yl.pay.pos.enums.YesNo;
import com.yl.pay.pos.service.IBankReversalTaskService;

import java.util.Date;
import java.util.List;

/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2012
 * Company: com.yl.pay
 * @author jun
 */

public class BankReversalTaskServiceImpl implements IBankReversalTaskService {
	private BankReversalTaskDao bankReversalTaskDao;
	
	@Override
	public BankReversalTask create(BankReversalTask bankReversalTask) {
		bankReversalTask.setClaim(YesNo.N);
		bankReversalTask.setCreateTime(new Date());
		bankReversalTask.setStatus(BankReversalTaskStatus.INIT);
		return bankReversalTaskDao.create(bankReversalTask);
	}

	@Override
	public BankReversalTask update(BankReversalTask bankReversalTask) {
		return bankReversalTaskDao.update(bankReversalTask);
	}

	@Override
	public BankReversalTask findById(Long id) {
		return bankReversalTaskDao.findById(id);
	}

	@Override
	public List<BankReversalTask> findByClaimAndStatus(YesNo claim,
			BankReversalTaskStatus status) {
		return bankReversalTaskDao.findByClaimAndStatus(claim, status);
	}

	public BankReversalTaskDao getBankReversalTaskDao() {
		return bankReversalTaskDao;
	}

	public void setBankReversalTaskDao(BankReversalTaskDao bankReversalTaskDao) {
		this.bankReversalTaskDao = bankReversalTaskDao;
	}

}
