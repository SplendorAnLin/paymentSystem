package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.BankInterfaceTerminal;
import com.yl.pay.pos.enums.BankPosRunStatus;
import com.yl.pay.pos.enums.BankPosUseStatus;
import com.yl.pay.pos.enums.Status;

import java.util.List;


/**
 * Title: 银行接口商户终端DAO
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun
 */

public interface BankInterfaceTerminalDao {
	
	//创建
	public BankInterfaceTerminal create(BankInterfaceTerminal bankInterfaceTerminal);
	
	//更新
	public BankInterfaceTerminal update(BankInterfaceTerminal bankInterfaceTerminal);
	
	public BankInterfaceTerminal updateByTrans(BankInterfaceTerminal bankInterfaceTerminal);
		
	//根据银行接口、银行商户号、状态、银行终端运行状态查找
	public List<BankInterfaceTerminal> findByBankInterfaceAndBankCustomerNoAndStatusAndBankPosRunStatus(String bankInterfaceCode, String bankCustomerNo, Status status, BankPosRunStatus bankPosRunStatus);

	public List<BankInterfaceTerminal> findUseableBankInterfaceTerminal(String bankInterfaceCode, String bankCustomerNo, BankPosUseStatus bankPosUseStatus, BankPosRunStatus bankPosRunStatus, Status status);

	public BankInterfaceTerminal findUseableBankInterfaceTerminal(String bankInterfaceCode, String bankCustomerNo, String bankPosCati, BankPosUseStatus bankPosUseStatus, BankPosRunStatus bankPosRunStatus, Status status);
	
	//根据使用状态查询
	public List<BankInterfaceTerminal> findByUseStatus(BankPosUseStatus bankPosUseStatus);
	
	//根据银行接口编码和状态查询
	public List<BankInterfaceTerminal> findByBankInterfaceAndStatus(String bankInterfaceCode, Status status);
	
	//根据银行接口编码和状态查询
	public List<BankInterfaceTerminal> findByBankInterfaceAndStatusAndRunStatus(String bankInterfaceCode, Status status,BankPosRunStatus runStatus);
	
	//根据银行接口,商户号,终端号查询
	public BankInterfaceTerminal findByInterfaceAndCustomerNoAndPosCati(String bankInterfaceCode, String bankCustomerNo, String bankPosCati);
	
	//根据银行接口，核心商户号，核心终端号
	
	public BankInterfaceTerminal findByInterfaceAndEncryCustomerAndEncryPosCati(String bankInterfaceCode, String bankCustomerNo, String bankPosCati);

	public BankInterfaceTerminal findByInterfaceAndCustomerNoAndPosCatiByJdbc(String bankInterfaceCode, String bankCustomerNo, String bankPosCati);
}


