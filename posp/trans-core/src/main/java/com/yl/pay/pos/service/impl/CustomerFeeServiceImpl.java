package com.yl.pay.pos.service.impl;

import com.pay.common.util.AmountUtil;
import com.yl.pay.pos.Constant;
import com.yl.pay.pos.bean.CustomerFeeBean;
import com.yl.pay.pos.bean.CustomerFeeReturnBean;
import com.yl.pay.pos.dao.CustomerFeeDao;
import com.yl.pay.pos.entity.CustomerFee;
import com.yl.pay.pos.enums.ComputeFeeMode;
import com.yl.pay.pos.exception.TransExceptionConstant;
import com.yl.pay.pos.exception.TransRunTimeException;
import com.yl.pay.pos.service.ICustomerFeeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.List;


/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class CustomerFeeServiceImpl implements ICustomerFeeService {
	private static final Log log = LogFactory.getLog(CustomerFeeServiceImpl.class);
	private CustomerFeeDao customerFeeDao;
	

	@Override
	public boolean addCustomerFee(CustomerFee customerFee) {
		try {
			customerFeeDao.create(customerFee);
			return true;
		} catch (Exception e) {
			log.error(e);
			return false;
		}
	}
	
	@Override
	public boolean updateCustomerFee(CustomerFee customerFee) {
		try {
			customerFeeDao.update(customerFee);
			return true;
		} catch (Exception e) {
			log.error(e);
			return false;
		}
	}

	@Override
	public CustomerFee findByCustNo(String custNo) {
		return customerFeeDao.findBycustNo(custNo);
	}
	
	@Override
	public CustomerFeeReturnBean getCustomerFee(CustomerFeeBean param) {
		if(AmountUtil.equal(0.01, param.getTrxAmount())){//TODO 一分钱特殊处理
			return new CustomerFeeReturnBean(null, null, 0.00);
		}
		
		List<CustomerFee> feeRules=customerFeeDao.findByCustomerNoAndMccCode(param.getCustomerNo(), param.getMcc());
		if(feeRules==null||feeRules.size()==0){
			throw new TransRunTimeException(TransExceptionConstant.CUSTOMERFEE_IS_NULL);
		}
		
		//根据规则匹配商户手续费规则记录    1、发卡行条件优先级高于卡种
		CustomerFee feeRule1=null;
		CustomerFee feeRule2=null;
		CustomerFee feeRule3=null;
		CustomerFee feeRule4=null;
		CustomerFee finalFeeRule=null;
		for(CustomerFee rule:feeRules){
			if(param.getIssuer().equals(rule.getIssuer())&&param.getCardType().name().equals(rule.getCardType())){
				feeRule1=rule;
			}else if(param.getIssuer().equals(rule.getIssuer())&&Constant.SYSTEM_DEFAULT_FLAG.equals(rule.getCardType())){
				feeRule2=rule;
			}else if(Constant.SYSTEM_DEFAULT_FLAG.equals(rule.getIssuer())&&param.getCardType().name().equals(rule.getCardType())){
				feeRule3=rule;
			}else if(Constant.SYSTEM_DEFAULT_FLAG.equals(rule.getIssuer())&&Constant.SYSTEM_DEFAULT_FLAG.equals(rule.getCardType())){
				feeRule4=rule;
			}
		}
		Date now=new Date();
		if(feeRule1!=null){
			//校验费率过期时间
			if(feeRule1.getExpireTime()==null||now.compareTo(feeRule1.getExpireTime())<=0){
				finalFeeRule=feeRule1;
			}
		}else if(finalFeeRule==null&&feeRule2!=null){
			if(feeRule2.getExpireTime()==null||now.compareTo(feeRule2.getExpireTime())<=0){
				finalFeeRule=feeRule2;
			}
		}else if(finalFeeRule==null&&feeRule3!=null){
			if(feeRule3.getExpireTime()==null||now.compareTo(feeRule3.getExpireTime())<=0){
				finalFeeRule=feeRule3;
			}
		}else if(finalFeeRule==null&&feeRule4!=null){
			if(feeRule4.getExpireTime()==null||now.compareTo(feeRule4.getExpireTime())<=0){
				finalFeeRule=feeRule4;
			}
		}
		if(finalFeeRule==null){
			//查找系统默认行业费率规则
			List<CustomerFee> systemFeeRules=customerFeeDao.findByCustomerNoAndMccCode(Constant.SYSTEM, param.getMcc());
			if(systemFeeRules==null||systemFeeRules.size()==0||systemFeeRules.size()>1){
				//没有匹配的费率规则 
				throw new TransRunTimeException(TransExceptionConstant.CUSTOMERFEE_IS_NULL);
			}
			finalFeeRule=systemFeeRules.get(0);
		}
		//手续费初算
		double customerFee=0.00;
		if(ComputeFeeMode.RATIO.equals(finalFeeRule.getComputeMode())){
			double rate=Double.parseDouble(finalFeeRule.getRate());
			customerFee=AmountUtil.mul(param.getTrxAmount(), rate);
		}else if(ComputeFeeMode.FIXED.equals(finalFeeRule.getComputeMode())){
			customerFee=finalFeeRule.getFixedFee();
		}else{
			double rate=Double.parseDouble(finalFeeRule.getRate());
			double rateFee=AmountUtil.mul(param.getTrxAmount(), rate);
			customerFee=AmountUtil.add(rateFee, finalFeeRule.getFixedFee());
		}
		//精算
		if(finalFeeRule.getUpperLimit()!=null&&finalFeeRule.getUpperLimit()&&finalFeeRule.getLowerLimit()!=null&&finalFeeRule.getLowerLimit()){
			if(AmountUtil.bigger(finalFeeRule.getLowerLimitFee(), finalFeeRule.getUpperLimitFee())){
				throw new TransRunTimeException(TransExceptionConstant.AMOUNT_INVALID);
			}
			if(AmountUtil.bigger(finalFeeRule.getLowerLimitFee(), customerFee)){
				customerFee=finalFeeRule.getLowerLimitFee();
			}else if(AmountUtil.bigger(customerFee, finalFeeRule.getUpperLimitFee())){
				customerFee=finalFeeRule.getUpperLimitFee();
			}
		}else if(finalFeeRule.getLowerLimit()!=null&&finalFeeRule.getLowerLimit()&&AmountUtil.bigger(finalFeeRule.getLowerLimitFee(), customerFee)){
			customerFee=finalFeeRule.getLowerLimitFee();
		}else if(finalFeeRule.getUpperLimit()!=null&&finalFeeRule.getUpperLimit()&&AmountUtil.bigger(customerFee, finalFeeRule.getUpperLimitFee())){
			customerFee=finalFeeRule.getUpperLimitFee();
		}
		
		log.info("#-#-#-#-#-#-#-# finalFeeRule code="+finalFeeRule.getCode());
		if(AmountUtil.bigger(0.00, customerFee)){
			//手续费金额无效 
			throw new TransRunTimeException(TransExceptionConstant.AMOUNT_INVALID);
		}
		//进一法取值
		customerFee=AmountUtil.ceiling(customerFee,2);
		if(AmountUtil.compare(customerFee, param.getTrxAmount())){
			throw new TransRunTimeException(TransExceptionConstant.AMOUNT_INVALID);
		}
		
		return new CustomerFeeReturnBean(finalFeeRule.getCode(),finalFeeRule.getRate(),customerFee);
	}

	public void setCustomerFeeDao(CustomerFeeDao customerFeeDao) {
		this.customerFeeDao = customerFeeDao;
	}

	
	

}
