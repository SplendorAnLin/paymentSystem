package com.yl.pay.pos.service.impl;

import com.yl.pay.pos.entity.BankChannel;
import com.yl.pay.pos.entity.BankChannelFunction;
import com.yl.pay.pos.entity.BankInterface;
import com.yl.pay.pos.enums.*;
import com.yl.pay.pos.service.IBankChannelService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.LinkedList;
import java.util.List;


/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class BankChannelServiceImpl extends BaseService implements IBankChannelService {
	private static final Log log = LogFactory.getLog(BankChannelServiceImpl.class);

	@Override
	public List<BankChannel> findAvailableBankChannel(String issuer,CardType cardType, TransType transType,YesNo isIC) {
		List<BankChannel> bankChannels=bankChannelDao.findAllActive();
		if(bankChannels==null||bankChannels.isEmpty()){
			return null;
		}
		List<BankChannel> useableChannels=new LinkedList<BankChannel>();
		for(BankChannel channel:bankChannels){
			if(checkFunction(channel, transType, issuer, cardType,isIC)){
				useableChannels.add(channel);
			}
		}
		return useableChannels;
	}
	
	@Override
	public List<BankChannel> findAvailableBankChannel(String issuer,
			CardType cardType, TransType transType, String bankConnectType,	String bankBillType,YesNo isIc) {
		List<BankChannel> bankChannels=null;
		if(!StringUtils.isBlank(bankConnectType)&&!StringUtils.isBlank(bankBillType)){
			bankChannels=bankChannelDao.findAllActiveByConnectTypeAndBillType(BankConnectType.valueOf(bankConnectType), BankBillType.valueOf(bankBillType));
		}else if(!StringUtils.isBlank(bankConnectType)){
			bankChannels=bankChannelDao.findAllActiveByConnectType(BankConnectType.valueOf(bankConnectType));
		}else{
			bankChannels=bankChannelDao.findAllActiveByBillType(BankBillType.valueOf(bankBillType));
		}
		if(bankChannels==null||bankChannels.isEmpty()){
			return null;
		}
		
		List<BankChannel> useableChannels=new LinkedList<BankChannel>();
		for(BankChannel channel:bankChannels){
			if(checkFunction(channel, transType, issuer, cardType,isIc)){
				useableChannels.add(channel);
			}
		}
		return useableChannels;
	}
	
	@Override
	public List<BankChannel> findAvailableBankChannel(String bankInterface,
			BankBillType bankBillType, String mccCategory, String issuer,
			CardType cardType, TransType transType,YesNo isIC) {
		List<BankChannel> bankChannels=bankChannelDao.findActiveByBankInterfaceAndBillTypeAndMccCategory(bankInterface, bankBillType, mccCategory);
		if(bankChannels==null||bankChannels.isEmpty()){
			return null;
		}
		List<BankChannel> useableChannels=new LinkedList<BankChannel>();
		for(BankChannel channel:bankChannels){
			if(checkFunction(channel, transType, issuer, cardType,isIC)){
				useableChannels.add(channel);
			}
		}
		return useableChannels;
	}

	@Override
	public BankChannel isBankChannelUseable(String issuer, String bankChannelCode,
			CardType cardType, TransType transType,YesNo isIC) {
		if(StringUtils.isBlank(bankChannelCode)){
			return null;
		}
		
		List<BankChannel> bankChannelList=bankChannelDao.findBankChannel(bankChannelCode);
		if(bankChannelList==null||bankChannelList.isEmpty()){
			return null;
		}
		if(checkFunction(bankChannelList.get(0),transType,issuer,cardType,isIC)){
			return bankChannelList.get(0);
		}
		
		return null;
	}


	private boolean checkFunction(BankChannel bankChannel,TransType transType,String issuer,CardType cardType,YesNo isIC){
		//判断银行接口状态
		BankInterface bankInterface=bankInterfaceDao.findByCodeAndStatus(bankChannel.getBankInterface().getCode(), Status.TRUE);
		if(bankInterface==null){
			return false;
		}
		//银行通道交易类型权限校验
		BankChannelFunction function=bankChannelFunctionDao.findBySortCodeAndTransTypeAndStatus(bankChannel.getTransFunction(), transType, Status.TRUE);
		if(function==null||(isIC.name().equals(YesNo.Y.name())&&YesNo.N.name().equals(bankChannel.getIsSupportIc().name()))){
			return false;
		}
//		//时间校验
//		Date effectTime=bankChannel.getEffectTime();//生效时间
//		Date expireTime=bankChannel.getExpireTime();//失效时间
//		if (effectTime!=null&&trxTime.compareTo(effectTime) < 0) {
//			return false;
//		}
//		if (expireTime!=null&&trxTime.compareTo(expireTime) > 0) {
//			return false;
//		}
		
		//校验支持的发卡行与卡种	如：!,BOC,ICBC
		if(bankChannel.getSupportIssuer().contains("!")){
			String[] supportIssuers=bankChannel.getSupportIssuer().substring(1).split("\\,");
			for(String supportIssuer:supportIssuers){
				if(issuer.equals(supportIssuer)){
					return false;
				}
			}
		}else{
			String[] supportIssuers=bankChannel.getSupportIssuer().split("\\,");
			boolean flag=false;
			for(String supportIssuer:supportIssuers){
				if(issuer.equals(supportIssuer)){
					flag=true;
				}
			}
			if(!flag){
				return flag;
			}
		}
		
		String[] supportCardTypes=bankChannel.getSupportCardType().split("\\,");
		boolean flag1=false;
		for(String supportCardType:supportCardTypes){
			if(cardType.name().equals(supportCardType)){
				flag1=true;
			}
		}
		if(!flag1){
			return flag1;
		}
		
		return true;
	}

	@Override
	public BankChannel findByBankChannelCode(String channelCode) {
		if(StringUtils.isBlank(channelCode)){
			return null;
		}
		
		List<BankChannel> bankChannelList=bankChannelDao.findBankChannel(channelCode);
		if(bankChannelList==null||bankChannelList.isEmpty()){
			return null;
		}
		return bankChannelList.get(0);
	}

}


