package com.yl.pay.pos.service.impl;

import com.yl.pay.pos.entity.BankInterfaceTerminal;
import com.yl.pay.pos.enums.BankPosRunStatus;
import com.yl.pay.pos.enums.BankPosUseStatus;
import com.yl.pay.pos.enums.Status;
import com.yl.pay.pos.service.IBankInterfaceTerminalService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: BankInterfaceTerminalService 实现
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun.yu
 */

public class BankInterfaceTerminalServiceImpl extends BaseService implements IBankInterfaceTerminalService {
	
	private Map<String, Object> actLocks = new HashMap<String, Object>();
		
	public synchronized Object getActLocks(String detailId){
		Object lock = actLocks.get(detailId);
		if(lock==null){
			lock = new Object();
			actLocks.put(detailId, lock);
		}
		return lock;
	}
	
	//按bankInterfaceCode和bankCustomerNo获取并发锁
	@Override	
	public BankInterfaceTerminal loadUseableBankInterfaceTerminal(
			String bankInterfaceCode, String bankCustomerNo) {
		//获取并发锁
		Object lock = getActLocks( bankInterfaceCode + bankCustomerNo);
		synchronized (lock) {
			List<BankInterfaceTerminal>  bankTerminals=bankInterfaceTerminalDao.findUseableBankInterfaceTerminal(bankInterfaceCode, bankCustomerNo, BankPosUseStatus.IDLE, BankPosRunStatus.SIGNIN, Status.TRUE);
			if(bankTerminals!=null&&!bankTerminals.isEmpty()){
				BankInterfaceTerminal terminal=bankTerminals.get(0);
				terminal.setBankPosUseStatus(BankPosUseStatus.BUSY);
				terminal.setLockTime(new Date());
				bankInterfaceTerminalDao.updateByTrans(terminal);
				return terminal;
			}else{
				return null;
			}			
		}
	}	
	
	@Override
	public BankInterfaceTerminal loadUseableBankInterfaceTerminal(String bankInterfaceCode, String bankCustomerNo, String bankPosCati) {
		//获取并发锁
		Object lock = getActLocks( bankInterfaceCode + bankCustomerNo);
		synchronized (lock) {
			BankInterfaceTerminal  bankTerminal=bankInterfaceTerminalDao.findUseableBankInterfaceTerminal(bankInterfaceCode, bankCustomerNo, bankPosCati,BankPosUseStatus.IDLE, BankPosRunStatus.SIGNIN, Status.TRUE);
			if(bankTerminal!=null){
				bankTerminal.setBankPosUseStatus(BankPosUseStatus.BUSY);
				bankTerminal.setLockTime(new Date());
				bankInterfaceTerminalDao.updateByTrans(bankTerminal);
				return bankTerminal;
			}else{
				return null;
			}			
		}
	}	

	//终端回收
	public void terminalRecover(String bankInterfaceCode, String bankCustomerNo, String bankPosCati){	
		
		BankInterfaceTerminal bankInterfaceTerminal = bankInterfaceTerminalDao.findByInterfaceAndCustomerNoAndPosCatiByJdbc(
				bankInterfaceCode, bankCustomerNo, bankPosCati);	
		if(bankInterfaceTerminal!=null){
			bankInterfaceTerminal.setBankPosUseStatus(BankPosUseStatus.IDLE);
			bankInterfaceTerminal.setLastUseTime(new Date());				
			bankInterfaceTerminalDao.updateByTrans(bankInterfaceTerminal);				
		}
	}
	
	//终端回收
	public void terminalRecover(BankInterfaceTerminal bankInterfaceTerminal){
		if(bankInterfaceTerminal != null && bankInterfaceTerminal.getBankInterface() != null){
			terminalRecover(bankInterfaceTerminal.getBankInterface().getCode(), 
					bankInterfaceTerminal.getBankCustomerNo(), bankInterfaceTerminal.getBankPosCati());
		}
	}

	@Override
	public BankInterfaceTerminal update(BankInterfaceTerminal bankInterfaceTerminal) {
		return bankInterfaceTerminalDao.update(bankInterfaceTerminal);
	}

	@Override
	public BankInterfaceTerminal findTerminal(String interfaceCode,
			String customerNo, String terminalId) {
		return bankInterfaceTerminalDao.findByInterfaceAndCustomerNoAndPosCati(interfaceCode, customerNo, terminalId);
	}
}
