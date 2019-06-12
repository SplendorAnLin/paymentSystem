package com.pay.test;

import java.util.List;

import com.pay.pos.dao.BankChannelCustomerConfigDao;
import com.pay.pos.dao.BankDao;
import com.pay.pos.dao.BankInterfaceDao;
import com.pay.pos.dao.BankInterfaceTerminalDao;
import com.pay.pos.entity.BankInterfaceTerminal;
import com.pay.pos.enums.Status;


public class BankTerminalTest extends BaseServiceTest {

	private BankDao bankDao;
	private BankInterfaceDao bankInterfaceDao;
	private BankInterfaceTerminalDao bankInterfaceTerminalDao;
	private BankChannelCustomerConfigDao bankChannelCustomerConfigDao;
	
	
	protected void setUp() throws Exception {
		super.setUp();
		bankDao=(BankDao)context.getBean("bankDao");
		bankInterfaceDao=(BankInterfaceDao)context.getBean("bankInterfaceDao");
		bankInterfaceTerminalDao=(BankInterfaceTerminalDao)context.getBean("bankInterfaceTerminalDao");
		bankChannelCustomerConfigDao=(BankChannelCustomerConfigDao)context.getBean("bankChannelCustomerConfigDao");
		
		
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

		
	public void testCreate(){
		
		List<BankInterfaceTerminal> ss=bankInterfaceTerminalDao.findByBankInterfaceAndStatus("P110005", Status.TRUE);
		
		System.out.println("-----------------ter size="+ss.size());
		
	}
	
}


	
