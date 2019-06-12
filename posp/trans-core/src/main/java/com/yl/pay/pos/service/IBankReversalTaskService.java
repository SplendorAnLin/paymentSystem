package com.yl.pay.pos.service;

import com.yl.pay.pos.entity.BankReversalTask;
import com.yl.pay.pos.enums.BankReversalTaskStatus;
import com.yl.pay.pos.enums.YesNo;

import java.util.List;

/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2012
 * Company: com.yl.pay
 * @author jun
 */

public interface IBankReversalTaskService {
	
	public BankReversalTask create(BankReversalTask bankReversalTask);
	
	public BankReversalTask update(BankReversalTask bankReversalTask);
	
	public BankReversalTask findById(Long id);
	
	public List<BankReversalTask> findByClaimAndStatus(YesNo claim, BankReversalTaskStatus status);
	
	
	
}
