package com.yl.pay.pos.service.impl;

import com.pay.common.util.StringUtil;
import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.entity.Bank;
import com.yl.pay.pos.entity.BankIdNumber;
import com.yl.pay.pos.entity.CardInfo;
import com.yl.pay.pos.enums.CardType;
import com.yl.pay.pos.enums.CurrencyType;
import com.yl.pay.pos.service.ICardInfoService;

import java.util.Date;

/**
 * Title: 卡片信息 服务
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class CardInfoServiceImpl extends BaseService implements ICardInfoService{
	
	public ExtendPayBean create(ExtendPayBean extendPayBean){
		
		String pan = extendPayBean.getUnionPayBean().getPan();
		
		if(StringUtil.isNull(pan)){	
			return extendPayBean;	//管理类及无卡号交易直接返回
		}
		
		BankIdNumber bankIdNumber = extendPayBean.getBankIdNumber();		
		CardInfo cardInfo = cardInfoDao.findByPan(pan);		
		
		if(cardInfo == null){	
			cardInfo = new CardInfo();
			cardInfo.setPan(pan);
//			cardInfo.setTrack2(extendPayBean.getUnionPayBean().getTrack2());	
			cardInfo.setCurrencyType(CurrencyType.CNY);
			cardInfo.setCreateTime(new Date());			
			
			if(bankIdNumber==null){				
				Bank unknownBank = bankDao.findByCode("UNKNOWNBANK");			
				cardInfo.setIssuer(unknownBank);
				cardInfo.setCardType(CardType.DEBIT_CARD);
			}else{
				cardInfo.setCardType(bankIdNumber.getCardType());
				cardInfo.setIssuer(bankIdNumber.getBank());
				cardInfo.setCardVerifyCode(bankIdNumber.getVerifyCode());
			}	
			cardInfo = cardInfoDao.create(cardInfo);			
		}else{
//			cardInfo.setTrack2(extendPayBean.getUnionPayBean().getTrack2());	
			if(bankIdNumber!=null){
				cardInfo.setCardType(bankIdNumber.getCardType());
				cardInfo.setIssuer(bankIdNumber.getBank());
				cardInfo.setCardVerifyCode(bankIdNumber.getVerifyCode());
			}
			cardInfo=cardInfoDao.update(cardInfo);
		}
				
		extendPayBean.setCardInfo(cardInfo);
		return extendPayBean;
	}
}

