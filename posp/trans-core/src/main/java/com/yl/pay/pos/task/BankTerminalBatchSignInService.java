package com.yl.pay.pos.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yl.pay.pos.interfaces.P500001.SignIn;

/**
 * 银行终端批量签到
 * @author Administrator
 *
 */
public class BankTerminalBatchSignInService {

	private static final Log log = LogFactory.getLog(BankTerminalRecoverService.class);	
	
	private SignIn signInP500001;
	
	public void execute(){
		
		log.info("bankTerminal batch sginIn execute!");
		long start = System.currentTimeMillis();
		try {
			//民生银行终端批次签到
			
			signInP500001.executeSignInBatchForBankTerminal();
			long end = System.currentTimeMillis();
			log.info("bankTerminal batch sginIn end! time:"+(end-start));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	public SignIn getSignInP500001() {
		return signInP500001;
	}

	public void setSignInP500001(SignIn signInP500001) {
		this.signInP500001 = signInP500001;
	}
	
	
}
