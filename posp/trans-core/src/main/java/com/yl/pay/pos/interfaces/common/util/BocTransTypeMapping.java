package com.yl.pay.pos.interfaces.common.util;

import java.util.HashMap;

import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.enums.TransType;

/**
 * Title: 交易类型转换
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: pay
 * @author haitao.liu
 */

public class BocTransTypeMapping {
	
	public static final HashMap<TransType, String> transTypesReq = new HashMap<TransType, String>();
	public static final HashMap<TransType, String> transTypesRet = new HashMap<TransType, String>();
	
	public static final HashMap<String, TransType> transCodes = new HashMap<String, TransType>();
	
	static {
		
		//交易类型 - 交易码
		
		transTypesReq.put(TransType.SIGN_IN, "0800");
		transTypesReq.put(TransType.SIGN_OFF, "0820");
		transTypesReq.put(TransType.AVAILABLE_FUNDSINQUIRY, "0200");
		transTypesReq.put(TransType.BALANCE_INQUIRY, "0200");
		transTypesReq.put(TransType.BATCH_UP, "0320");
		transTypesReq.put(TransType.PURCHASE, "0200");
		transTypesReq.put(TransType.PURCHASE_REFUND, "0220");
		transTypesReq.put(TransType.PURCHASE_REVERSAL, "0400");
		transTypesReq.put(TransType.PURCHASE_VOID, "0200");
		transTypesReq.put(TransType.PURCHASE_VOID_REVERSAL, "0400");
		transTypesReq.put(TransType.SETTLE, "0500");
	}	
}
