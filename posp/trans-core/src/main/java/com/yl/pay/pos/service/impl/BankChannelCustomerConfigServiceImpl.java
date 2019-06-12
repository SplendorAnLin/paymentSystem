package com.yl.pay.pos.service.impl;

import com.yl.pay.pos.entity.BankChannelCustomerConfig;
import com.yl.pay.pos.entity.BankInterfaceTerminal;
import com.yl.pay.pos.enums.*;
import com.yl.pay.pos.service.IBankChannelCustomerConfigService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


/**
 * Title: 银行通道商户配置处理服务
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun
 */

public class BankChannelCustomerConfigServiceImpl extends BaseService implements
		IBankChannelCustomerConfigService {

	/**
	 * 获取银行通道商户配置
	 * 银行商户号分两个级别，商户级、系统级（大商户号），按条件查找银行商户号，每级取一个商户号
	 */
	@Override
	public List<BankChannelCustomerConfig> getBankChannelCustomerConfig(String bankInterfaceCode, String bankChannelCode,
			BankCustomerChooseType bankCustomerChooseType, String customerNo,String customerOrg) {
		List<BankChannelCustomerConfig> result=new ArrayList<BankChannelCustomerConfig>();
		
		if(BankCustomerChooseType.CUSTOMER.equals(bankCustomerChooseType)){
			List<BankChannelCustomerConfig> bankCustomers=bankChannelCustomerConfigDao.findByBankInterfaceAndBankChannelCodeAndCustomerNo(bankInterfaceCode, bankChannelCode, customerNo, BankCustomerLevel.CUSTOMER);
			BankChannelCustomerConfig bankChannelCustomer=getAvailableBankChannelCustomerConfig(bankInterfaceCode,bankCustomers,customerOrg);
			if(bankChannelCustomer!=null){
				result.add(bankChannelCustomer);
			}
		}else if(BankCustomerChooseType.SYSTEM.equals(bankCustomerChooseType)){
			List<BankChannelCustomerConfig> bankCustomers=bankChannelCustomerConfigDao.findByBankInterfaceAndBankChannelCodeAndCustomerNo(bankInterfaceCode, bankChannelCode, customerNo, BankCustomerLevel.SYSTEM);
			BankChannelCustomerConfig bankChannelCustomer=getAvailableBankChannelCustomerConfig(bankInterfaceCode,bankCustomers,customerOrg);
			if(bankChannelCustomer!=null){
				result.add(bankChannelCustomer);
			}
		}else if(BankCustomerChooseType.CUSTOMER_SYSTEM.equals(bankCustomerChooseType)){
			List<BankChannelCustomerConfig> customerLevels=bankChannelCustomerConfigDao.findByBankInterfaceAndBankChannelCodeAndCustomerNo(bankInterfaceCode, bankChannelCode, customerNo, BankCustomerLevel.CUSTOMER);
			BankChannelCustomerConfig bankChannelCustomer=getAvailableBankChannelCustomerConfig(bankInterfaceCode,customerLevels,customerOrg);
			if(bankChannelCustomer!=null){
				result.add(bankChannelCustomer);
			}
			List<BankChannelCustomerConfig> systemLevels=bankChannelCustomerConfigDao.findByBankInterfaceAndBankChannelCodeAndCustomerNo(bankInterfaceCode, bankChannelCode, customerNo, BankCustomerLevel.SYSTEM);
			BankChannelCustomerConfig sysCustomer=getAvailableBankChannelCustomerConfig(bankInterfaceCode,systemLevels,customerOrg);
			if(sysCustomer!=null){
				result.add(sysCustomer);
			}
		}else if(BankCustomerChooseType.SYSTEM_CUSTOMER.equals(bankCustomerChooseType)){
			List<BankChannelCustomerConfig> systemLevels=bankChannelCustomerConfigDao.findByBankInterfaceAndBankChannelCodeAndCustomerNo(bankInterfaceCode, bankChannelCode, customerNo, BankCustomerLevel.SYSTEM);
			BankChannelCustomerConfig sysCustomer=getAvailableBankChannelCustomerConfig(bankInterfaceCode,systemLevels,customerOrg);
			if(sysCustomer!=null){
				result.add(sysCustomer);
			}
			List<BankChannelCustomerConfig> customerLevels=bankChannelCustomerConfigDao.findByBankInterfaceAndBankChannelCodeAndCustomerNo(bankInterfaceCode, bankChannelCode, customerNo, BankCustomerLevel.CUSTOMER);
			BankChannelCustomerConfig bankChannelCustomer=getAvailableBankChannelCustomerConfig(bankInterfaceCode,customerLevels,customerOrg);
			if(bankChannelCustomer!=null){
				result.add(bankChannelCustomer);
			}
		}
		
		return result;
	}
	
	/**
	 * 	获取可用通道商户配置信息
	 * eg: !41|!3101|11|2101|ALL
	 */
	private BankChannelCustomerConfig getAvailableBankChannelCustomerConfig(String bankInterfaceCode,List<BankChannelCustomerConfig> bankCustomers,String customerOrg){
		if(bankCustomers!=null&&bankCustomers.size()>0){
			List<BankChannelCustomerConfig> cityResults=new LinkedList<BankChannelCustomerConfig>();
			List<BankChannelCustomerConfig> provinceResults=new LinkedList<BankChannelCustomerConfig>();
			List<BankChannelCustomerConfig> nationResults=new LinkedList<BankChannelCustomerConfig>();
			for(BankChannelCustomerConfig bankCustomer:bankCustomers){
				//判断是否有可用终端
				if(isAvailableBankChannelDetails(bankInterfaceCode,bankCustomer.getBankCustomerNo())){
					//校验地区属性
	    			String bankCustomerOrg=bankCustomer.getSupportOrganization();
	    			boolean orgCheckFlag=false;
	    			
	    					//全国、所有地区
	    			if(bankCustomerOrg==null){
	    				orgCheckFlag=true;
	    				nationResults.add(bankCustomer);
	    			}else{
	    				boolean cFlag=false;
	    				boolean pFlag=false;
	    				boolean nFlag=false;
	    				String[] bankCustomerOrgs=bankCustomerOrg.split("\\|");
	    				for(String bankOrg:bankCustomerOrgs){
	    					//省,直辖市
	    					if(customerOrg.length()==2){
	    						if(bankOrg.contains("!")){
	    							orgCheckFlag=true;
	    							nFlag=true;
	    							if(bankOrg.substring(1).equals(customerOrg)){
	    								orgCheckFlag=false;
	    								nFlag=false;
	    								break;
	    							}
	    						}else{
	    							if(bankOrg.equals(customerOrg)){
	    								orgCheckFlag=true;
	    								cFlag=true;
	    							}
	    						}
	    					//市	
	    					}else if(customerOrg.length()==4){
	    						String province=customerOrg.substring(0, 2);
	    						if(bankOrg.contains("!")){
	    							orgCheckFlag=true;
	    							nFlag=true;
	    							if(bankOrg.substring(1).equals(customerOrg)||bankOrg.substring(1).equals(province)){
	    								orgCheckFlag=false;
	    								nFlag=false;
	    								break;
	    							}
	    						}else{
	    							if(bankOrg.equals(customerOrg)){
	    								orgCheckFlag=true;
	    								cFlag=true;
	    							}else if(bankOrg.equals(province)){
	    								orgCheckFlag=true;
	    								pFlag=true;
	    							}
	    						}
	    					}
	    					
	    					if("ALL".equals(bankOrg)){
	    						orgCheckFlag=true;
	    						nFlag=true;
	    					}
	    				}
	    				
	    				if(orgCheckFlag){
	    					if(cFlag){
	    						cityResults.add(bankCustomer);
	    					}else if(pFlag){
	    						provinceResults.add(bankCustomer);
	    					}else if(nFlag){
	    						nationResults.add(bankCustomer);
	    					}
	    				}
	    				
	    			}
					
				}
			}
			
			//在结果中随机选取一个,按市、省、全国由高到低排序
			Random r=new Random();
			if(!cityResults.isEmpty()){
				return cityResults.get(r.nextInt(cityResults.size()));
			}else if(!provinceResults.isEmpty()){
				return provinceResults.get(r.nextInt(provinceResults.size()));
			}else if(!nationResults.isEmpty()){
				return nationResults.get(r.nextInt(nationResults.size()));
			}
			
		}
		return null;
	}
	
	/**
	 * 
	 */
	private boolean isAvailableBankChannelDetails(String bankInterfaceCode,String bankCustomerNo){
		List<BankInterfaceTerminal> details=bankInterfaceTerminalDao.findByBankInterfaceAndBankCustomerNoAndStatusAndBankPosRunStatus(bankInterfaceCode, bankCustomerNo, Status.TRUE, BankPosRunStatus.SIGNIN);
		if(details!=null&&details.size()>0){
			for(int j=0;j<details.size();j++){
				 if(!BankPosUseStatus.SIGNING.equals(details.get(j).getBankPosUseStatus())){
					 return true;
				 }
			}
		}
		return false;//没有可用银行终端
	}
	

}
