package com.pay.sign.utils;

/**
 * Title: 根据交易类型，将ExtendPayBean组装POS报文
 * Description: 
 * Copyright: Copyright (c)2015
 * Company: pay
 * @author zhongxiang.ren
 */

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.common.util.ISO8583.FieldMapping;
import com.pay.sign.TransTypeMapping;
import com.pay.sign.bean.ExtendPayBean;
import com.pay.sign.enums.TransType;

public class PosMessageEncoder {
	
	private static Log log = LogFactory.getLog(PosMessageEncoder.class);
	private static Map<TransType, int[]> fieldsMap = new HashMap<TransType, int[]>();
	
	static {		
		
		//手机图片签名 返回域
		int[] signatureInFields = {4,11,12,13,39,41,42};
		
		//手机图片签名
		fieldsMap.put(TransType.SIGNATURE, signatureInFields);
	}
		
	public static byte[] encoding(ExtendPayBean extendPayBean) throws Exception {
		
		TransType transType = extendPayBean.getTransType();
		PosISO8583Common posISO8583Common = new PosISO8583Common();
		
		int[] fields = (int[])fieldsMap.get(transType);
		
		posISO8583Common.putField(0, TransTypeMapping.transTypesRet.get(transType));//消息类型
		
		for(int i=0;i<fields.length;i++){
			int fieldIndex = fields[i];
			String name = FieldMapping.fields.get(fieldIndex);
			String value = "";
			try{
		    	Field field = extendPayBean.getUnionPayBean().getClass().getField(name);
		    	value = (String)field.get(extendPayBean.getUnionPayBean());	
			}catch(Exception e){
				log.error("put field value err, name=" + name + " fieldIndex=" + fieldIndex);
			}	    	    	
	    	if(StringUtils.isNotBlank(value)){
	    		posISO8583Common.putField(fieldIndex, value);	
	    	}	
		}		
		return posISO8583Common.Encoding();
	}   

}
