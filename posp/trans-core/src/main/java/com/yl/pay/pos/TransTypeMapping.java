package com.yl.pay.pos;

import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.bean.UnionPayBean;
import com.yl.pay.pos.enums.TransType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: 交易类型转换
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class TransTypeMapping {
	
	public static final HashMap<TransType, String> transTypesReq = new HashMap<TransType, String>();
	public static final HashMap<TransType, String> transTypesRet = new HashMap<TransType, String>();
	
	public static final HashMap<String, TransType> transCodes = new HashMap<String, TransType>();
	public static final Map<TransType,TransType> orgTransMap=new HashMap<TransType,TransType>();
	
	public static final List<TransType> transTypeMAC=new ArrayList<TransType>();
	
	static {
		
		//交易类型 -> 交易返回码
		
		transTypesRet.put(TransType.SIGN_IN, "0810");
		transTypesRet.put(TransType.SIGN_OFF, "0830");
		transTypesRet.put(TransType.AVAILABLE_FUNDSINQUIRY, "0210");
		transTypesRet.put(TransType.BALANCE_INQUIRY, "0210");
		transTypesRet.put(TransType.BATCH_UP, "0330");
		transTypesRet.put(TransType.PURCHASE, "0210");
		transTypesRet.put(TransType.PURCHASE_REFUND, "0230");
		transTypesRet.put(TransType.PURCHASE_REVERSAL, "0410");
		transTypesRet.put(TransType.PURCHASE_VOID, "0210");
		transTypesRet.put(TransType.PURCHASE_VOID_REVERSAL, "0410");
		transTypesRet.put(TransType.PRE_AUTH, "0110");
		transTypesRet.put(TransType.PRE_AUTH_REVERSAL, "0410");
		transTypesRet.put(TransType.PRE_AUTH_VOID, "0110");
		transTypesRet.put(TransType.PRE_AUTH_VOID_REVERSAL, "0410");
		transTypesRet.put(TransType.PRE_AUTH_COMP, "0210");
		transTypesRet.put(TransType.PRE_AUTH_COMP_REVERSAL, "0410");
		transTypesRet.put(TransType.PRE_AUTH_COMP_VOID, "0210");
		transTypesRet.put(TransType.PRE_AUTH_COMP_VOID_REVERSAL, "0410");
		transTypesRet.put(TransType.SETTLE, "0510");


		transTypesRet.put(TransType.DOWNLOAD_MAIN_KEY, "0710");
		transTypesRet.put(TransType.DOWNLOAD_IC_END, "0810");
		transTypesRet.put(TransType.DOWNLOAD_IC_REQUEST, "0830");
		transTypesRet.put(TransType.DOWNLOAD_IC_TRANSFER, "0810");
		transTypesRet.put(TransType.SPECIFY_QUANCUN, "0210");
		transTypesRet.put(TransType.SPECIFY_QUANCUN_REVERSAL, "0410");
		transTypesRet.put(TransType.NOT_SPECIFY_QUANCUN, "0210");
		transTypesRet.put(TransType.NOT_SPECIFY_QUANCUN_REVERSAL, "0410");
		transTypesRet.put(TransType.CASH_RECHARGE_QUNCUN, "0210");
		transTypesRet.put(TransType.CASH_RECHARGE_QUNCUN_REVERSAL, "0410");
		transTypesRet.put(TransType.OFFLINE_PURCHASE, "0210");
		transTypesRet.put(TransType.PURCHASE_SCRIPT_NOTICE, "0630");
		transTypesRet.put(TransType.SPECIFY_QUANCUN_NOTICE, "0630");
		transTypesRet.put(TransType.PREAUTH_SCRIPT_NOTICE, "0630");
		transTypesRet.put(TransType.BALANCE_INQUIRY_NOTICE, "0630");
		transTypesRet.put(TransType.NOT_SPECIFY_QUANCUN_NOTICE, "0630");
		transTypesRet.put(TransType.CASH_RECHARGE_QUNCUN_NOTICE, "0630");

		//交易请求码 -> 交易类型
		
		transCodes.put("0320", TransType.BATCH_UP);
		transCodes.put("0500", TransType.SETTLE);
		
		// key = mti-msgTypeCode-processCode-posConditionCode
		
		transCodes.put("0200-36-000000-00", TransType.OFFLINE_PURCHASE);		
		transCodes.put("0200-22-000000-00", TransType.PURCHASE);			
		transCodes.put("0200-23-200000-00", TransType.PURCHASE_VOID);	
		transCodes.put("0200-45-600000-91", TransType.SPECIFY_QUANCUN);	
		transCodes.put("0400-45-600000-91", TransType.SPECIFY_QUANCUN_REVERSAL);
		transCodes.put("0200-47-620000-91", TransType.NOT_SPECIFY_QUANCUN);	
		transCodes.put("0400-47-620000-91", TransType.NOT_SPECIFY_QUANCUN_REVERSAL);
		transCodes.put("0200-46-630000-91", TransType.CASH_RECHARGE_QUNCUN);
		transCodes.put("0400-46-630000-91", TransType.CASH_RECHARGE_QUNCUN_REVERSAL);
		
		
		
		transCodes.put("0400-22-000000-00", TransType.PURCHASE_REVERSAL);				
		transCodes.put("0400-23-200000-00", TransType.PURCHASE_VOID_REVERSAL);
		transCodes.put("0220-25-200000-00", TransType.PURCHASE_REFUND);					
		transCodes.put("0200-01-310000-00", TransType.BALANCE_INQUIRY);			
		transCodes.put("0100-10-030000-06", TransType.PRE_AUTH);				
		transCodes.put("0100-11-200000-06", TransType.PRE_AUTH_VOID);				
		transCodes.put("0400-10-030000-06", TransType.PRE_AUTH_REVERSAL);			
		transCodes.put("0400-11-200000-06", TransType.PRE_AUTH_VOID_REVERSAL);			
		transCodes.put("0200-20-000000-06", TransType.PRE_AUTH_COMP);			
		transCodes.put("0200-21-200000-06", TransType.PRE_AUTH_COMP_VOID);		
		transCodes.put("0400-20-000000-06", TransType.PRE_AUTH_COMP_REVERSAL);		
		transCodes.put("0400-21-200000-06", TransType.PRE_AUTH_COMP_VOID_REVERSAL);			
		transCodes.put("0220-30-000000-00", TransType.SETTLE);		
		
		transCodes.put("0820-002", TransType.SIGN_OFF);
		transCodes.put("0820-372", TransType.DOWNLOAD_IC_REQUEST);
		transCodes.put("0820-382", TransType.DOWNLOAD_IC_REQUEST);
		
		transCodes.put("0800-001", TransType.SIGN_IN);
		transCodes.put("0800-003", TransType.SIGN_IN);
		transCodes.put("0700-003", TransType.DOWNLOAD_MAIN_KEY);
		transCodes.put("0800-370", TransType.DOWNLOAD_IC_TRANSFER);
		transCodes.put("0800-380", TransType.DOWNLOAD_IC_TRANSFER);
		
		transCodes.put("0800-371", TransType.DOWNLOAD_IC_END);
		transCodes.put("0800-381", TransType.DOWNLOAD_IC_END);
		transCodes.put("0620-00-000000", TransType.PURCHASE_SCRIPT_NOTICE);
		transCodes.put("0620-00-600000", TransType.SPECIFY_QUANCUN_NOTICE);
		transCodes.put("0620-00-030000", TransType.PREAUTH_SCRIPT_NOTICE);
		transCodes.put("0620-00-310000", TransType.BALANCE_INQUIRY_NOTICE);
		transCodes.put("0620-00-620000", TransType.NOT_SPECIFY_QUANCUN_NOTICE);
		transCodes.put("0620-00-630000", TransType.CASH_RECHARGE_QUNCUN_NOTICE);
		
		//交易类型-原交易类型
		orgTransMap.put(TransType.NOT_SPECIFY_QUANCUN_REVERSAL, TransType.NOT_SPECIFY_QUANCUN);
		orgTransMap.put(TransType.SPECIFY_QUANCUN_REVERSAL, TransType.SPECIFY_QUANCUN);
		orgTransMap.put(TransType.PURCHASE_VOID, TransType.PURCHASE);
		orgTransMap.put(TransType.PURCHASE_REVERSAL, TransType.PURCHASE);
		orgTransMap.put(TransType.PURCHASE_VOID_REVERSAL, TransType.PURCHASE);
		orgTransMap.put(TransType.PURCHASE_REFUND, TransType.PURCHASE);
		orgTransMap.put(TransType.PRE_AUTH_VOID, TransType.PRE_AUTH);
		orgTransMap.put(TransType.PRE_AUTH_VOID_REVERSAL, TransType.PRE_AUTH);
		orgTransMap.put(TransType.PRE_AUTH_COMP, TransType.PRE_AUTH);
		orgTransMap.put(TransType.PRE_AUTH_COMP_VOID, TransType.PRE_AUTH);
		orgTransMap.put(TransType.PRE_AUTH_COMP_VOID_REVERSAL, TransType.PRE_AUTH);
		orgTransMap.put(TransType.PRE_AUTH_COMP_REVERSAL, TransType.PRE_AUTH);
		orgTransMap.put(TransType.PRE_AUTH_REVERSAL, TransType.PRE_AUTH);
		orgTransMap.put(TransType.PURCHASE_SCRIPT_NOTICE, TransType.PURCHASE);
		orgTransMap.put(TransType.SPECIFY_QUANCUN_NOTICE, TransType.SPECIFY_QUANCUN);
		orgTransMap.put(TransType.PREAUTH_SCRIPT_NOTICE, TransType.PRE_AUTH);
		orgTransMap.put(TransType.BALANCE_INQUIRY_NOTICE, TransType.BALANCE_INQUIRY);
		orgTransMap.put(TransType.NOT_SPECIFY_QUANCUN_NOTICE, TransType.NOT_SPECIFY_QUANCUN);
		orgTransMap.put(TransType.CASH_RECHARGE_QUNCUN_NOTICE, TransType.CASH_RECHARGE_QUNCUN);
		orgTransMap.put(TransType.CASH_RECHARGE_QUNCUN_REVERSAL, TransType.CASH_RECHARGE_QUNCUN);
		
		transTypeMAC.add(TransType.AVAILABLE_FUNDSINQUIRY);
		transTypeMAC.add(TransType.BALANCE_INQUIRY);
		transTypeMAC.add(TransType.PURCHASE);
		transTypeMAC.add(TransType.PURCHASE_VOID);
		transTypeMAC.add(TransType.PURCHASE_REVERSAL);
		transTypeMAC.add(TransType.PURCHASE_VOID_REVERSAL);
		transTypeMAC.add(TransType.PURCHASE_REFUND);
		transTypeMAC.add(TransType.SPECIFY_QUANCUN);
		transTypeMAC.add(TransType.SPECIFY_QUANCUN_REVERSAL);
		transTypeMAC.add(TransType.NOT_SPECIFY_QUANCUN);
		transTypeMAC.add(TransType.NOT_SPECIFY_QUANCUN_REVERSAL);
		transTypeMAC.add(TransType.CASH_RECHARGE_QUNCUN);
		transTypeMAC.add(TransType.CASH_RECHARGE_QUNCUN_REVERSAL);
		transTypeMAC.add(TransType.OFFLINE_PURCHASE);
		transTypeMAC.add(TransType.PRE_AUTH);
		transTypeMAC.add(TransType.PRE_AUTH_VOID);
		transTypeMAC.add(TransType.PRE_AUTH_REVERSAL);
		transTypeMAC.add(TransType.PRE_AUTH_VOID_REVERSAL);
		transTypeMAC.add(TransType.PRE_AUTH_COMP);
		transTypeMAC.add(TransType.PRE_AUTH_COMP_VOID);
		transTypeMAC.add(TransType.PRE_AUTH_COMP_REVERSAL);
		transTypeMAC.add(TransType.PRE_AUTH_COMP_VOID_REVERSAL);
		
	}
	
	//获取交易类型
	public static ExtendPayBean getTransType(ExtendPayBean extendPayBean){	

		UnionPayBean unionPayBean=extendPayBean.getUnionPayBean();
		String transCode = unionPayBean.getMti();			
		if("0320,0500".indexOf(transCode)<0){	//管理类无需处理	
			if ("0820".equals(transCode)||"0800".equals(transCode) || "0700".equals(transCode)){
				StringBuffer sb = new StringBuffer();
				sb.append(unionPayBean.getMti()).append("-");
				sb.append(unionPayBean.getNetMngInfoCode());
				transCode = sb.toString();
			}else if("0620".equals(transCode)){
				StringBuffer sb = new StringBuffer();
				sb.append(unionPayBean.getMti()).append("-");
				sb.append(unionPayBean.getMsgTypeCode()).append("-");
				sb.append(unionPayBean.getProcessCode());
				transCode = sb.toString();		
			}else{	
			StringBuffer sb = new StringBuffer();
			sb.append(unionPayBean.getMti()).append("-");
			sb.append(unionPayBean.getMsgTypeCode()).append("-");
			sb.append(unionPayBean.getProcessCode()).append("-");
			sb.append(unionPayBean.getPosConditionCode());			
			transCode = sb.toString();	
			}
		}	
		TransType transType = TransTypeMapping.transCodes.get(transCode);
		extendPayBean.setTransType(transType);
		return extendPayBean;
	}
	
	//获取原交易类型
	public static TransType getOrgTransType(TransType trx){
		return orgTransMap.get(trx);
	}
	
	
}
