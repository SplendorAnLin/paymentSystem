package com.yl.pay.pos.task;

import com.yl.pay.pos.dao.BankInterfaceDao;
import com.yl.pay.pos.dao.BankInterfaceTerminalDao;
import com.yl.pay.pos.entity.BankInterface;
import com.yl.pay.pos.entity.BankInterfaceTerminal;
import com.yl.pay.pos.enums.BankPosUseStatus;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.List;

/**
 * Title: 银行终端资源回收器
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class BankTerminalRecoverService {

	private static final Log log = LogFactory.getLog(BankTerminalRecoverService.class);	
	public BankInterfaceTerminalDao bankInterfaceTerminalDao;
	public BankInterfaceDao bankInterfaceDao;
	
	public void execute(){
		
		log.info("bankTerminal recover execute!");
		
		List<BankInterfaceTerminal> busyTerminalList = bankInterfaceTerminalDao.findByUseStatus(BankPosUseStatus.BUSY);
		for(BankInterfaceTerminal bankInterfaceTerminal :busyTerminalList){
			
			Long lockTime = bankInterfaceTerminal.getLockTime().getTime();
			Long nowTime = (new Date()).getTime();
			
			BankInterface bankInterface = bankInterfaceDao.findByCode(bankInterfaceTerminal.getBankInterface().getCode());			
			Integer averageRespTime = bankInterface.getAverageRespTime();
			if(averageRespTime==null){
				averageRespTime = 120000;//默认120秒
			}
			if ((nowTime - lockTime) > averageRespTime) {
				bankInterfaceTerminal.setBankPosUseStatus(BankPosUseStatus.IDLE);
				bankInterfaceTerminal.setLastUseTime(new Date());				
				bankInterfaceTerminalDao.update(bankInterfaceTerminal);
			}			
		}		
	}

	public BankInterfaceTerminalDao getBankInterfaceTerminalDao() {
		return bankInterfaceTerminalDao;
	}
	public void setBankInterfaceTerminalDao(
			BankInterfaceTerminalDao bankInterfaceTerminalDao) {
		this.bankInterfaceTerminalDao = bankInterfaceTerminalDao;
	}
	public BankInterfaceDao getBankInterfaceDao() {
		return bankInterfaceDao;
	}
	public void setBankInterfaceDao(BankInterfaceDao bankInterfaceDao) {
		this.bankInterfaceDao = bankInterfaceDao;
	}
}
