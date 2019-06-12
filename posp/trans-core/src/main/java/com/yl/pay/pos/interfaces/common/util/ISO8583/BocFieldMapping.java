package com.yl.pay.pos.interfaces.common.util.ISO8583;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Title: 中行报文与域的对应关系
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: pay
 * @author haitao.liu
 */

public class BocFieldMapping {
	
	public static final Map<Integer,String> fields = new HashMap<Integer,String>();
	public static final Map<String,Integer> reverse_fields = new HashMap<String,Integer>();
	
	//存放中行报文中的域和银联Bean属性的对应关系
	static{		
			
		fields.put(0,	"mti");
		fields.put(2,	"pan");
		fields.put(3,	"processCode");
		fields.put(4,	"amount");
		fields.put(5,	"amountSettlement");
		fields.put(6,	"amountCardholderBilling");
		fields.put(7,	"transmissionDateAndTime");
		fields.put(8,	"amountCardholderDocument");
		fields.put(9,	"conversionRateSettlement");
		fields.put(10,	"conversionRateCardholderBilling");
		fields.put(11,	"systemsTraceAuditNumber");
		fields.put(12,	"timeLocalTransaction");
		fields.put(13,	"dateLocalTransaction");
		fields.put(14,	"dateExpiration");
		fields.put(15,	"dateSettlement");
		fields.put(16,	"dateConversion");
		fields.put(17,	"dateCapture");
		fields.put(18,	"merchantType");
		fields.put(19,	"aquiringInstitutionCountryCode");
		fields.put(20,	"panExtendCountryCode");
		fields.put(21,	"noUse21");
		fields.put(22,	"posEntryModeCode");
		fields.put(23,	"cardSequenceNumber");
		fields.put(24,	"noUse24");
		fields.put(25,	"posConditionCode");
		fields.put(26,	"posPinCaptureCode");
		fields.put(27,	"noUse27");
		fields.put(28,	"transactionFee");
		fields.put(29,	"settlementFee");
		fields.put(30,	"noUse30");
		fields.put(31,	"settlementProcessFee");
		fields.put(32,	"aquiringInstitutionId");
		fields.put(33,	"forwardInstitutionId");
		fields.put(34,	"panExtend");
		fields.put(35,	"track2");
		fields.put(36,	"track3");
		fields.put(37,	"retrievalReferenceNumber");
		fields.put(38,	"authorizationCode");
		fields.put(39,	"responseCode");
		fields.put(40,	"noUse40");
		fields.put(41,	"cardAcceptorTerminalId");
		fields.put(42,	"cardAcceptorId");
		fields.put(43,	"cardAcceptorName");
		fields.put(44,	"additionalResponseData");
		fields.put(45,	"track1");
		fields.put(46,	"noUse46");
		fields.put(47,	"noUse47");
		fields.put(48,	"additionalData48");
		fields.put(49,	"currencyCode");
		fields.put(50,	"currencyCodeSettle");
		fields.put(51,	"currencyCodeCardholder");
		fields.put(52,	"pin");
		fields.put(53,	"securityControlInfo");
		fields.put(54,	"additionalAmount");
		
		fields.put(544,	"type");
		fields.put(545,	"amount");
		fields.put(55,	"ICSystemRelated");
		fields.put(56,	"noUse56");
		fields.put(57,	"additionalData57");
		fields.put(58,	"icpbocDate");
		fields.put(59,	"detailInqrng");
		fields.put(60,	"reserved");
		fields.put(601, "msgTypeCode");
		
		fields.put(61, 	"cardholderAuthInfo");		
		fields.put(611, "batchNo");
		fields.put(612, "noUseTemp");
		fields.put(613, "billNo");
		fields.put(614, "noUseTemp");
		fields.put(615, "noUseTemp");
		
		fields.put(64,	"mac");
		
		//fields.put(70, "netwk_mgmt_info_code");
		//fields.put(100, "rcvg_inst_id_code");		
	}	
	static{
		Set<Integer> keySet = fields.keySet();
		for(Integer key:keySet){
			String value = (String)fields.get(key);
			reverse_fields.put(value,key);
		}
	}
}
