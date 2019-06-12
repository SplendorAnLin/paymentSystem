package com.yl.pay.pos.service.impl;

import com.lefu.commons.cache.util.CacheUtils;
import com.yl.pay.pos.dao.*;
import com.yl.pay.pos.entity.BankChannel;
import com.yl.pay.pos.entity.BankCustomer;
import com.yl.pay.pos.entity.BankCustomerConfig;
import com.yl.pay.pos.entity.BankInterfaceTerminal;
import com.yl.pay.pos.enums.BankBillType;
import com.yl.pay.pos.enums.BankPosRunStatus;
import com.yl.pay.pos.enums.BankPosUseStatus;
import com.yl.pay.pos.enums.Status;
import com.yl.pay.pos.interfaces.P500001.NCCBTransUtil;
import com.yl.pay.pos.service.IBankCustomerConfigService;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2012
 * Company: com.yl.pay
 * @author jun
 */

public class BankCustomerConfigServiceImpl implements IBankCustomerConfigService {
	
	private static final Log log = LogFactory.getLog(BankCustomerConfigServiceImpl.class);
	
	private BankCustomerConfigDao bankCustomerConfigDao;
	private BankInterfaceTerminalDao bankInterfaceTerminalDao;
	private BankCustomerDao bankCustomerDao;
	
	@Override
	public List<BankCustomerConfig> findByBankInterfaceAndBankChannelAndBankCustomerNo(
			String bankInterface, BankChannel bankChannel, String bankCustomerNo) {
		List<BankCustomerConfig> bankCustomerConfigs2=new LinkedList<BankCustomerConfig>();
		List<BankCustomerConfig> bankCustomerConfigs=bankCustomerConfigDao.findByBankInterfaceAndBankChannelAndBankCustomerNo(bankInterface, bankChannel, bankCustomerNo);
		if(bankCustomerConfigs!=null){
			for(BankCustomerConfig customerConfig:bankCustomerConfigs){
				if(isAvailableBankChannelDetails(customerConfig)){
					bankCustomerConfigs2.add(customerConfig);
				}
			}
		}
		return bankCustomerConfigs2;
	}
	
	@Override
	public List<BankCustomerConfig> findByBankInterfaceAndBankChannelAndCustomerNo(
			String bankInterface, BankChannel bankChannel, String customerNo) {
		List<BankCustomerConfig> bankCustomerConfigs2=new LinkedList<BankCustomerConfig>();
		List<BankCustomerConfig> bankCustomerConfigs=null;
		if(!BankBillType.LEFU.equals(bankChannel.getBankBillType())){
			bankCustomerConfigs=bankCustomerConfigDao.findByBankInterfaceAndBankChannelAndCustomerNo(bankInterface, bankChannel, customerNo);
		}else{
			String bankCustomerNo=null;
			List<BankCustomer> bankCustomers =bankCustomerDao.findLefuBy(bankChannel.getBankInterface().getCode(), bankChannel.getMccCategory(),null,Status.TRUE,null);
			if (bankCustomers != null && bankCustomers.size() > 0) {
				bankCustomerNo = bankCustomers.get(RandomUtils.nextInt(bankCustomers.size())).getBankCustomerNo();
			}
			if(bankCustomerNo!=null&&!"".equals(bankCustomerNo)){
				bankCustomerConfigs=new ArrayList<BankCustomerConfig>();
				BankCustomerConfig bankCustomerConfig=new BankCustomerConfig();
				bankCustomerConfig.setBank(bankChannel.getBank());
				bankCustomerConfig.setBankChannelCode(bankChannel.getCode());
				bankCustomerConfig.setBankCustomerNo(bankCustomerNo);
				bankCustomerConfig.setBankInterface(bankChannel.getBankInterface().getCode());
				bankCustomerConfig.setCustomerNo(customerNo);
				bankCustomerConfig.setStatus(Status.TRUE);
				bankCustomerConfigs.add(bankCustomerConfig);
			}
		}
		if(bankCustomerConfigs!=null){
			for(BankCustomerConfig customerConfig:bankCustomerConfigs){
				if(isAvailableBankChannelDetails(customerConfig)){
					bankCustomerConfigs2.add(customerConfig);
				}
			}
		}
		return bankCustomerConfigs2;
	}
	
	@Override
	public List<BankCustomerConfig> findByBankInterfaceAndBankChannelAndCustomerNoAndPosCati(
			String bankInterface, BankChannel bankChannel, String customerNo,String posCati) {
		List<BankCustomerConfig> bankCustomerConfigs2=new LinkedList<BankCustomerConfig>();
		List<BankCustomerConfig> bankCustomerConfigs=null;
		if(!BankBillType.LEFU.equals(bankChannel.getBankBillType())){
			bankCustomerConfigs=bankCustomerConfigDao.findByBankInterfaceAndBankChannelAndCustomerNoAndPosCati(bankInterface, bankChannel, customerNo,posCati);
		}else{
//			String bankCustomerNo = null;
			String bankCustomerNo = CacheUtils.get("ONE_CUSTOMER_MULTI_CODE_"+customerNo+"_"+posCati,String.class);
			log.info("customerNo:"+customerNo+",posCati:"+posCati+",bankCustomerNo:"+bankCustomerNo);
			//查询是否一户多码
			if (null != bankCustomerNo && !bankCustomerNo.isEmpty()){

				BankCustomerConfig bccf = bankCustomerConfigDao.findByBankCustomerNo(bankCustomerNo);
				
				if(null != bccf){
					if(bccf.getBankInterface().equals(bankChannel.getBankInterface().getCode())){						
						bankCustomerConfigs=new ArrayList<BankCustomerConfig>();
						BankCustomerConfig bankCustomerConfig=new BankCustomerConfig();
						bankCustomerConfig.setBank(bankChannel.getBank());
						bankCustomerConfig.setBankChannelCode(bankChannel.getCode());
						bankCustomerConfig.setBankCustomerNo(bccf.getBankCustomerNo());
						bankCustomerConfig.setBankInterface(bankChannel.getBankInterface().getCode());
						bankCustomerConfig.setCustomerNo(customerNo);
						bankCustomerConfig.setStatus(Status.TRUE);
						bankCustomerConfigs.add(bankCustomerConfig);
					}
				}
			} else {

				List<BankCustomer> bankCustomers =bankCustomerDao.findLefuBy(bankChannel.getBankInterface().getCode(), bankChannel.getMccCategory(),null,Status.TRUE,null);
				if (bankCustomers != null && bankCustomers.size() > 0) {
					bankCustomerNo = bankCustomers.get(RandomUtils.nextInt(bankCustomers.size())).getBankCustomerNo();
				}
				if(bankCustomerNo!=null&&!"".equals(bankCustomerNo)){
					bankCustomerConfigs=new ArrayList<BankCustomerConfig>();
					BankCustomerConfig bankCustomerConfig=new BankCustomerConfig();
					bankCustomerConfig.setBank(bankChannel.getBank());
					bankCustomerConfig.setBankChannelCode(bankChannel.getCode());
					bankCustomerConfig.setBankCustomerNo(bankCustomerNo);
					bankCustomerConfig.setBankInterface(bankChannel.getBankInterface().getCode());
					bankCustomerConfig.setCustomerNo(customerNo);
					bankCustomerConfig.setStatus(Status.TRUE);
					bankCustomerConfigs.add(bankCustomerConfig);
				}
			}
		}
		if(bankCustomerConfigs!=null){
			for(BankCustomerConfig customerConfig:bankCustomerConfigs){
				if(isAvailableBankChannelDetails(customerConfig)){
					bankCustomerConfigs2.add(customerConfig);
				}
			}
		}
		return bankCustomerConfigs2;
	}


	private boolean isAvailableBankChannelDetails(BankCustomerConfig customerConfig){
		Date effectTime=customerConfig.getEffectTime();
		if (effectTime!=null&&new Date().compareTo(effectTime) < 0) {
			return false;
		}
		List<BankInterfaceTerminal> details=bankInterfaceTerminalDao.findByBankInterfaceAndBankCustomerNoAndStatusAndBankPosRunStatus(customerConfig.getBankInterface(), 
				customerConfig.getBankCustomerNo(), Status.TRUE, BankPosRunStatus.SIGNIN);
		if(details!=null&&details.size()>0){
			for(int j=0;j<details.size();j++){
				 if(!BankPosUseStatus.SIGNING.equals(details.get(j).getBankPosUseStatus())){
					 return true;
				 }
			}
		}
		return false;//没有可用银行终端
	}

	public BankInterfaceTerminalDao getBankInterfaceTerminalDao() {
		return bankInterfaceTerminalDao;
	}

	public void setBankInterfaceTerminalDao(
			BankInterfaceTerminalDao bankInterfaceTerminalDao) {
		this.bankInterfaceTerminalDao = bankInterfaceTerminalDao;
	}

	public BankCustomerConfigDao getBankCustomerConfigDao() {
		return bankCustomerConfigDao;
	}

	public void setBankCustomerConfigDao(BankCustomerConfigDao bankCustomerConfigDao) {
		this.bankCustomerConfigDao = bankCustomerConfigDao;
	}



	public void setBankCustomerDao(BankCustomerDao bankCustomerDao) {
		this.bankCustomerDao = bankCustomerDao;
	}

	
}
