package com.pay.sign;

import java.util.HashMap;
import java.util.Map;

import com.pay.sign.bean.ExtendPayBean;
import com.pay.sign.bean.UnionPayBean;
import com.pay.sign.enums.TransType;

/**
 * Title: 交易类型转换
 * Description:  
 * Copyright: Copyright (c)2015
 * Company: pay
 * @author zhongxiang.ren
 */

public class TransTypeMapping {
	
	public static final HashMap<TransType, String> transTypesReq = new HashMap<TransType, String>();
	public static final HashMap<TransType, String> transTypesRet = new HashMap<TransType, String>();
	
	public static final HashMap<String, TransType> transCodes = new HashMap<String, TransType>();
	public static final Map<TransType,TransType> orgTransMap=new HashMap<TransType,TransType>();
	
	static {
		
		//交易类型 -> 交易返回码
		transTypesRet.put(TransType.SIGNATURE, "0910");

		//交易请求码 -> 交易类型		
		transCodes.put("0900", TransType.SIGNATURE);
	}
	
	//获取交易类型
	public static ExtendPayBean getTransType(ExtendPayBean extendPayBean){	

		UnionPayBean unionPayBean = extendPayBean.getUnionPayBean();
		String transCode = unionPayBean.getMti();			
		
		//管理类无需处理	,手机图片签名0900无需处理
//		if("0800,0820,0320,0500,0900".indexOf(transCode)<0){	
//			StringBuffer sb = new StringBuffer();
//			sb.append(unionPayBean.getMti()).append("-");
//			sb.append(unionPayBean.getMsgTypeCode()).append("-");
//			sb.append(unionPayBean.getProcessCode()).append("-");
//			sb.append(unionPayBean.getPosConditionCode());			
//			transCode = sb.toString();	
//		}		
		
		TransType transType = TransTypeMapping.transCodes.get(transCode);
		extendPayBean.setTransType(transType);
		return extendPayBean;
	}
	
	//获取原交易类型
	public static TransType getOrgTransType(TransType trx){
		return orgTransMap.get(trx);
	}
	
	
}
