package com.yl.pay.pos.interfaces.P500001;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.common.util.StringUtil;
import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.bean.UnionPayBean;
import com.yl.pay.pos.dao.BankInterfaceTerminalDao;
import com.yl.pay.pos.entity.BankInterface;
import com.yl.pay.pos.entity.BankInterfaceTerminal;
import com.yl.pay.pos.enums.BankPosRunStatus;
import com.yl.pay.pos.enums.BankPosUseStatus;
import com.yl.pay.pos.enums.Status;
import com.yl.pay.pos.enums.TransType;
import com.yl.pay.pos.interfaces.BaseBankTransaction;
import com.yl.pay.pos.interfaces.IBankSignInAction;
import com.yl.pay.pos.interfaces.IBankTransaction;
import com.yl.pay.pos.service.IBankRequestService;

/**
 * Title: 银行交易处理 - 签到
 * Description: 
 * Copyright: Copyright (c)2011 
 * Company: lepay
 * @author huakui.zhang
 */

public class SignIn extends BaseBankTransaction implements IBankTransaction ,IBankSignInAction {
	private static final Log log = LogFactory.getLog(SignIn.class);

	private NCCBTransUtil cmbcTransUtil;
	private IBankRequestService bankRequestService;
	private BankInterfaceTerminalDao bankInterfaceTerminalDao;  

	public UnionPayBean execute(ExtendPayBean extendPayBean) throws Exception {
		log.info("SignIn : [interfaceCode=" + Constant.INTERFACE_CODE);

		UnionPayBean unionPayBean = extendPayBean.getUnionPayBean();

		CmbcISO8583 ccbRequest = new CmbcISO8583();
		ccbRequest.putItem(0,  Constant.TRANS_TYPE_SIGN_ON); 					// 交易类型
		ccbRequest.putItem(11, unionPayBean.getSystemsTraceAuditNumber()); 		// 系统跟踪号
		ccbRequest.putItem(41, unionPayBean.getCardAcceptorTerminalId()); 		// 银行终端号
		ccbRequest.putItem(42, unionPayBean.getCardAcceptorId()); 				// 银行商户号
		
		ccbRequest.putItem(601,"00");
		ccbRequest.putItem(602, "000001");
		ccbRequest.putItem(603, "003");
		ccbRequest.putItem(631, "001");
		UnionPayBean responseBean = new UnionPayBean();
		try {
			// 向建行接口发送报文
			responseBean = cmbcTransUtil.send2Bank(ccbRequest);

		} catch (Exception e) {
			log.info("SignIn send2Bank ",e);
		}

		// 更新密钥
		String mac = responseBean.getSwitchingData();
		NCCBTransUtil.updateMacAfterSignon(mac, ccbRequest.getItem(41).getValue());

		return responseBean;
	}
	
	//交易中重新签到
	public void transReSignIn(String bankCustomerNo, String bankPosCati) throws Exception{
		BankInterfaceTerminal bankInterfaceTerminal = bankInterfaceTerminalDao.findByInterfaceAndCustomerNoAndPosCati(
				Constant.INTERFACE_CODE, bankCustomerNo, bankPosCati);	
		if(bankInterfaceTerminal==null){
			return ;
		}
		UnionPayBean unionPayBean = new UnionPayBean();
		unionPayBean.setCardAcceptorTerminalId(bankInterfaceTerminal.getBankPosCati());
		unionPayBean.setCardAcceptorId(bankInterfaceTerminal.getBankCustomerNo());
		unionPayBean.setBatchNo(bankInterfaceTerminal.getBankBatch());
		unionPayBean.setSystemsTraceAuditNumber(generateSequence(Constant.SEQ_SYSTEM_TRACKE_NUMBER));
		
		ExtendPayBean extendPayBean = new ExtendPayBean();
		extendPayBean.setUnionPayBean(unionPayBean);
		extendPayBean.setTransType(TransType.SIGN_IN);
		BankInterface bankInterface = new BankInterface();
		bankInterface.setCode(Constant.INTERFACE_CODE);
		extendPayBean.setBankInterface(bankInterface);
		
		UnionPayBean responseBean = null;
		//记录银行请求
		extendPayBean = bankRequestService.create(extendPayBean);
		
		//银行接口调用
		responseBean = execute(extendPayBean);
		
		//更新银行终端
		if(Constant.BANK_RESP_CODE_00.equals(responseBean.getResponseCode())){
//			String batchNo=bankInterfaceTerminal.getBankBatch();
//			String batchNo2=ISO8583Utile.StringFillLeftZero(new Integer(Integer.parseInt(batchNo)).toString(),6);
//			if(batchNo2.length()>6){
//				batchNo2=batchNo2.substring(batchNo2.length()-6);
//			}
			String batchNo=responseBean.getReserved().trim().substring(2, 8);
			bankInterfaceTerminal.setBankBatch(batchNo);
			bankInterfaceTerminalDao.update(bankInterfaceTerminal);
		}					
		
		//更新银行请求
		extendPayBean.setUnionPayBean(responseBean);
		extendPayBean = bankRequestService.complete(extendPayBean);
	}
	
	public int executeSignInBatch() throws Exception{
		return executeSignIn(null, null);		
	}
	
	
	public int executeSignInBatchForBankTerminal() throws Exception{
		List<BankInterfaceTerminal> bankInterfaceTerminalList = new ArrayList<BankInterfaceTerminal>();
		
		bankInterfaceTerminalList = bankInterfaceTerminalDao.findByBankInterfaceAndStatusAndRunStatus(Constant.INTERFACE_CODE, Status.TRUE, BankPosRunStatus.SIGNOFF);
		
//		for(int i=0;i<3;i++){			
			bankInterfaceTerminalList = executeCallBank(bankInterfaceTerminalList);
//			if(bankInterfaceTerminalList.size()>0){
//				Thread.sleep(3000);
//			}						
//		}	
		
		if(bankInterfaceTerminalList.size()>0){
			//TODO;短信
			System.out.println("短信已发送");
		}	
		return bankInterfaceTerminalList.size();		
	}
	
	public int executeSignIn(String bankCustomerNo, String bankPosCati) throws Exception{

		List<BankInterfaceTerminal> bankInterfaceTerminalList = new ArrayList<BankInterfaceTerminal>();
		
		if( StringUtil.isNull(bankCustomerNo)&& StringUtil.isNull(bankPosCati)){
			bankInterfaceTerminalList = bankInterfaceTerminalDao.findByBankInterfaceAndStatus(Constant.INTERFACE_CODE, Status.TRUE);
		}else{
			BankInterfaceTerminal bankInterfaceTerminal = bankInterfaceTerminalDao.findByInterfaceAndCustomerNoAndPosCati(
					Constant.INTERFACE_CODE, bankCustomerNo, bankPosCati);	
			if(bankInterfaceTerminal!=null){
				bankInterfaceTerminalList.add(bankInterfaceTerminal);
			}		
		}		
		
		for(int i=0;i<3;i++){			
			bankInterfaceTerminalList = executeCallBank(bankInterfaceTerminalList);
			if(bankInterfaceTerminalList.size()>0){
				Thread.sleep(3000);
			}						
		}	
		
		if(bankInterfaceTerminalList.size()>0){
			//TODO;短信
			System.out.println("短信已发送");
		}	
		return bankInterfaceTerminalList.size();
	}
	
	public List<BankInterfaceTerminal> executeCallBank(List<BankInterfaceTerminal> bankInterfaceTerminalList) {
		
		UnionPayBean responseBean = null;			
		for(int i=0;i<bankInterfaceTerminalList.size();i++){
			
			BankInterfaceTerminal bankInterfaceTerminal = bankInterfaceTerminalList.get(i);			
			String bankPosUseStatus = bankInterfaceTerminal.getBankPosUseStatus().name();
			
			if(BankPosUseStatus.IDLE.name().equals(bankPosUseStatus)){
				
				log.info("SignIn bankCustomerNo:"+bankInterfaceTerminal.getBankCustomerNo()+",bankTerminal:"+bankInterfaceTerminal.getBankPosCati());
				
				bankInterfaceTerminal.setBankPosUseStatus(BankPosUseStatus.SIGNING);//加锁
				bankInterfaceTerminalDao.update(bankInterfaceTerminal);
				
				UnionPayBean unionPayBean = new UnionPayBean();
				unionPayBean.setCardAcceptorTerminalId(bankInterfaceTerminal.getBankPosCati());
				unionPayBean.setCardAcceptorId(bankInterfaceTerminal.getBankCustomerNo());
				unionPayBean.setBatchNo(bankInterfaceTerminal.getBankBatch());
				
				unionPayBean.setSystemsTraceAuditNumber(generateSequence(Constant.SEQ_SYSTEM_TRACKE_NUMBER));
				
				ExtendPayBean extendPayBean = new ExtendPayBean();
				extendPayBean.setUnionPayBean(unionPayBean);
				extendPayBean.setTransType(TransType.SIGN_IN);
				BankInterface bankInterface = new BankInterface();
				bankInterface.setCode(Constant.INTERFACE_CODE);
				extendPayBean.setBankInterface(bankInterface);
										
				try{				
					//记录银行请求
					extendPayBean = bankRequestService.create(extendPayBean);
					
					//银行接口调用
					responseBean = execute(extendPayBean);
					
					//更新银行终端
					if(Constant.BANK_RESP_CODE_00.equals(responseBean.getResponseCode())){
//						String batchNo=bankInterfaceTerminal.getBankBatch();
//						String batchNo2=ISO8583Utile.StringFillLeftZero(new Integer(Integer.parseInt(batchNo)+1).toString(),6);
//						if(batchNo2.length()>6){
//							batchNo2=batchNo2.substring(batchNo2.length()-6);
//						}
						String batchNo=responseBean.getReserved().trim().substring(2, 8);
						bankInterfaceTerminal.setBankBatch(batchNo);
						bankInterfaceTerminal.setBankPosRunStatus(BankPosRunStatus.SIGNIN);
						bankInterfaceTerminalDao.update(bankInterfaceTerminal);
					}					
					
					//更新银行请求
					extendPayBean.setUnionPayBean(responseBean);
					extendPayBean = bankRequestService.complete(extendPayBean);
					//bankInterfaceTerminalList.remove(i);			
					
				}catch(Exception e){
					e.printStackTrace();
					continue;
				}finally{
					bankInterfaceTerminal.setLastUseTime(new Date());
					bankInterfaceTerminal.setBankPosUseStatus(BankPosUseStatus.IDLE);//解锁
					bankInterfaceTerminalDao.update(bankInterfaceTerminal);
				}			
			}
		}			
		return bankInterfaceTerminalList;
	}

	 
	
	public NCCBTransUtil getCmbcTransUtil() {
		return cmbcTransUtil;
	}

	public void setCmbcTransUtil(NCCBTransUtil cmbcTransUtil) {
		this.cmbcTransUtil = cmbcTransUtil;
	}

	public IBankRequestService getBankRequestService() {
		return bankRequestService;
	}

	public void setBankRequestService(IBankRequestService bankRequestService) {
		this.bankRequestService = bankRequestService;
	}

	public BankInterfaceTerminalDao getBankInterfaceTerminalDao() {
		return bankInterfaceTerminalDao;
	}

	public void setBankInterfaceTerminalDao(
			BankInterfaceTerminalDao bankInterfaceTerminalDao) {
		this.bankInterfaceTerminalDao = bankInterfaceTerminalDao;
	}

	public void test(){
		
		ExtendPayBean extendPayBean = new ExtendPayBean();
		UnionPayBean unionPayBean = new UnionPayBean();
		unionPayBean.setCardAcceptorId(Constant.TEST_BANK_CUSTOMERNO);
		unionPayBean.setCardAcceptorTerminalId(Constant.TEST_BANK_POSCATI);
		unionPayBean.setSystemsTraceAuditNumber("000001");
		unionPayBean.setSystemsTraceAuditNumber(generateSequence(Constant.SEQ_SYSTEM_TRACKE_NUMBER));
		unionPayBean.setTimeLocalTransaction("223212");
		unionPayBean.setDateLocalTransaction("1102");
		extendPayBean.setUnionPayBean(unionPayBean);
		
		try {
			execute(extendPayBean);
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}
	
}
