package com.yl.pay.pos.service;

import com.yl.pay.pos.entity.BankInterfaceTerminal;

/**
 * Title: 银行接口商户及终端信息服务
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun
 */

public interface IBankInterfaceTerminalService {
	
	//根据银行接口编号、银行商户号获取可用银行终端
	public BankInterfaceTerminal loadUseableBankInterfaceTerminal(String bankInterfaceCode, String bankCustomerNo);

	//根据银行接口编号、银行商户号、银行终端号获取可用银行终端
	public BankInterfaceTerminal loadUseableBankInterfaceTerminal(String bankInterfaceCode, String bankCustomerNo, String bankPosCati);

	//更新银行终端
	public BankInterfaceTerminal update(BankInterfaceTerminal obj);

	//根据银行接口编号,银行商户号,银行终端号查找指定银行终端
	public BankInterfaceTerminal findTerminal(String interfaceCode, String customerNo, String terminalId);
	
	//终端回收
	public void terminalRecover(String bankInterfaceCode, String bankCustomerNo, String bankPosCati);
	
	//终端回收
	public void terminalRecover(BankInterfaceTerminal bankInterfaceTerminal);
	
	
}
