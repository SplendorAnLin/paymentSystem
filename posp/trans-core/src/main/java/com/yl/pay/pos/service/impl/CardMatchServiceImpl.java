package com.yl.pay.pos.service.impl;

import com.pay.common.util.StringUtil;
import com.yl.pay.pos.Constant;
import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.dao.BankIdNumberDao;
import com.yl.pay.pos.entity.Bank;
import com.yl.pay.pos.entity.BankIdNumber;
import com.yl.pay.pos.service.ICardMatchService;

import java.util.List;

/**
 * Title: 卡识别 服务
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class CardMatchServiceImpl extends BaseService implements ICardMatchService{
	
	public BankIdNumberDao bankIdNumberDao;
	
	public ExtendPayBean execute(ExtendPayBean extendPayBean){
		
		String pan = extendPayBean.getUnionPayBean().getPan();	
		
		if(StringUtil.isNull(pan)){
			return extendPayBean;		//管理类及无卡号交易直接返回
		}
		List<BankIdNumber> bankIdNumberList = bankIdNumberDao.findByPan(pan, pan.length(), pan.substring(0, 3));		
		
		for (BankIdNumber bankIdNumber : bankIdNumberList) {
			
			int verifyLength = bankIdNumber.getVerifyLength();
			String verifyCode = bankIdNumber.getVerifyCode();					
			String panStr = pan.substring(0, verifyLength);			
			if (!panStr.equals(verifyCode)){
				continue;
			}
			extendPayBean.setBankIdNumber(bankIdNumber);
			
			Bank issuer = bankDao.findByCode(bankIdNumber.getBank().getCode());
			extendPayBean.setIssuer(issuer);
		}
		if(extendPayBean.getBankIdNumber()==null){	//TODO;未知银行,记录告警信息
			Bank issuer = bankDao.findByCode(Constant.UN_BANK);
			extendPayBean.setIssuer(issuer);				
		}		
		return extendPayBean;
	}
	public BankIdNumberDao getBankIdNumberDao() {
		return bankIdNumberDao;
	}
	public void setBankIdNumberDao(BankIdNumberDao bankIdNumberDao) {
		this.bankIdNumberDao = bankIdNumberDao;
	}	
}

