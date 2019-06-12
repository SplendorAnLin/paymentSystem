package com.yl.pay.pos.interfaces.P360001;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
/**
 * Title: 建行报文域定义
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: pay
 * @author huakui.zhang
 */

public class CcbParamMapping {
	//建行域<———>银联bean属性
	public static final Map<Integer,String> ccbUnionMap=new HashMap<Integer,String>();
	
	//银联bean属性<———>建行域
	public static final Map<String,Integer> unionCcbMap=new HashMap<String,Integer>();
	
	//存放建行报文域和银联Bean属性UnionPayParam的对应关系
	static{		
		ccbUnionMap.put(0 ,"mti");
		ccbUnionMap.put(2 ,"pan");
		ccbUnionMap.put(3 ,"processCode");
		ccbUnionMap.put(4 ,"amount");
		ccbUnionMap.put(5 ,"amountSettlement");
		ccbUnionMap.put(6 ,"amountCardholderBilling");
		ccbUnionMap.put(7 ,"transmissionDateAndTime");
		ccbUnionMap.put(8 ,"amountCardholderDocument");
		ccbUnionMap.put(9 ,"conversionRateSettlement");
		ccbUnionMap.put(10,"conversionRateCardholderBilling");
		ccbUnionMap.put(11,"systemsTraceAuditNumber");
		ccbUnionMap.put(12,"timeLocalTransaction");
		ccbUnionMap.put(13,"dateLocalTransaction");
		ccbUnionMap.put(14,"dateExpiration");
		ccbUnionMap.put(15,"dateSettlement");
		ccbUnionMap.put(16,"dateConversion");
		ccbUnionMap.put(17,"dateCapture");
		ccbUnionMap.put(18,"merchantType");
		ccbUnionMap.put(19,"aquiringInstitutionCountryCode");
		ccbUnionMap.put(20,"panExtendCountryCode");
		ccbUnionMap.put(21,"noUse21");
		ccbUnionMap.put(22,"posEntryModeCode");
		ccbUnionMap.put(23,"cardSequenceNumber");						//建行对应CVV2码
		ccbUnionMap.put(24,"noUse24");
		ccbUnionMap.put(25,"posConditionCode");
		ccbUnionMap.put(26,"posPinCaptureCode");
		ccbUnionMap.put(27,"noUse27");
		ccbUnionMap.put(28,"transactionFee");
		ccbUnionMap.put(29,"settlementFee");
		ccbUnionMap.put(30,"noUse30");
		ccbUnionMap.put(31,"settlementProcessFee");
		ccbUnionMap.put(32,"aquiringInstitutionId");
		ccbUnionMap.put(33,"forwardInstitutionId");
		ccbUnionMap.put(34,"panExtend");
		ccbUnionMap.put(35,"track2");
		ccbUnionMap.put(36,"track3");
		ccbUnionMap.put(37,"retrievalReferenceNumber");
		ccbUnionMap.put(38,"authorizationCode");
		ccbUnionMap.put(39,"responseCode");
		ccbUnionMap.put(40,"noUse40");
		ccbUnionMap.put(41,"cardAcceptorTerminalId");
		ccbUnionMap.put(42,"cardAcceptorId");
		ccbUnionMap.put(43,"cardAcceptorName");
		ccbUnionMap.put(44,"additionalResponseData");
		ccbUnionMap.put(45,"track1");
		ccbUnionMap.put(46,"noUse46");
		ccbUnionMap.put(47,"noUse47");
		ccbUnionMap.put(48,"additionalData48");
		ccbUnionMap.put(49,"currencyCode");
		ccbUnionMap.put(50,"currencyCodeSettle");
		ccbUnionMap.put(51,"currencyCodeCardholder");
		ccbUnionMap.put(52,"pin");
		ccbUnionMap.put(53,"securityControlInfo");
		ccbUnionMap.put(54,"additionalAmount");
		ccbUnionMap.put(55,"ICSystemRelated");
		ccbUnionMap.put(56,"noUse56");
		ccbUnionMap.put(57,"additionalData57");
		ccbUnionMap.put(58,"icpbocDate");
		ccbUnionMap.put(59,"detailInqrng");
		ccbUnionMap.put(60,"reserved");							//建行用来放交易批次号 交易类型+交易批次号+网络信息码 
		ccbUnionMap.put(61,"cardholderAuthInfo");
		ccbUnionMap.put(62,"switchingData");					//建行用来存放签到后的工作密钥
		ccbUnionMap.put(63,"finaclNetData");					//建行用来存放参考数据，也就是原交易数据
		ccbUnionMap.put(64,"mac");
	}	
	static{
		Set<Integer> keySet = ccbUnionMap.keySet();
		for(Integer key : keySet){
			String value = (String)ccbUnionMap.get(key);
			unionCcbMap.put(value,key);
		}
	}
	
	//返回报文正确消息类型
	static Map<String,String> mtpMap=new HashMap<String,String>();
	
	static{
		mtpMap.put("0210","1");
		mtpMap.put("0410","1");
		mtpMap.put("0110","1");
		mtpMap.put("0230","1");
		mtpMap.put("0810","1");
		mtpMap.put("0830","1");
	}

	public static boolean getMtpResult(String mtp){
		return mtpMap.get(mtp)==null?false:true;
	}
}
