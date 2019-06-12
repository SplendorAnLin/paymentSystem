package com.yl.pay.pos.access;

/**
 * Title: 根据交易类型，将ExtendPayBean组装POS报文
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

import com.pay.common.util.ISO8583.ISO8583Utile;
import com.yl.pay.pos.TransTypeMapping;
import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.enums.TransType;
import com.yl.pay.pos.util.FieldMapping;
import com.yl.pay.pos.util.codecs.common.tlv.BerTlvEncoder;
import com.yl.pay.pos.util.codecs.common.tlv.TLVObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PosMessageEncoder {
	
	private static Log log = LogFactory.getLog(PosMessageEncoder.class);
	private static Map<TransType, int[]> fieldsMap = new HashMap<TransType, int[]>();
	
	static {		
		
		//管理类
		int[] signInFields = {11,12,13,32,37,39,41,42,51,571,601,602,603,62};
		int[] signOffFields = {11,12,13,32,37,39,41,42,47,601,602,603};
		int[] settleFields = {11,12,13,15,32,41,42,47,48,49,601,602,603,631};
		int[] batchUpFields = {11,12,13,32,37,39,41,42,48,601,602,603};
		int[] mainKeyFields = {11,12,13,32,37,39,41,42,571,572,573,601,602,603,62};
		
		//交易类
		int[] balanceInquiryFields = {2,3,11,12,13,25,32,37,39,41,42,49,54,55,56,571,601,602};	
		int[] purchaseFields = {2,3,4,11,12,13,14,15,25,32,37,38,39,41,42,44,47,49,50,54,55,56,571,601,602,631};			
		int[] purchaseReversalFields = {2,3,4,11,12,13,14,15,23,25,32,37,39,41,42,44,49,53,54,55,56,571,601,602};
		int[] purchaseVoidFields = {2,3,4,11,12,13,15,25,32,37,38,39,41,42,44,47,49,55,56,571,601,602,631};
		int[] purchaseVoidReversalFields = {2,3,4,11,12,13,14,15,23,25,32,37,39,41,42,44,49,53,55,56,571,601,602};
		int[] preAuthFields = {2,3,4,11,12,13,14,15,23,25,32,37,38,39,41,42,44,47,49,55,56,571,601,602,631};
		int[] preAuthReversalFields = {2,3,4,11,12,13,14,15,23,25,32,37,39,41,42,44,49,55,56,571,601,602,631};
		int[] preAuthVoidFields = {2,3,4,11,12,13,14,15,23,25,32,37,38,39,41,42,44,47,49,55,56,571,601,602,631};
		int[] preAuthVoidReversalFields = {2,3,4,11,12,13,14,15,23,25,32,37,39,41,42,49,55,56,571,601,602};
		int[] preAuthCompFields = {2,3,4,11,12,13,14,15,23,25,32,37,38,39,41,42,44,47,49,55,56,571,601,602,631};
		int[] preAuthCompReversalFields = {2,3,4,11,12,13,14,15,23,25,32,37,39,41,42,49,55,56,571,601,602};
		int[] preAuthCompVoidFields = {2,3,4,11,12,13,14,15,23,25,32,37,38,39,41,42,44,47,49,55,56,571,601,602,631};
		int[] preAuthCompVoidReversalFields = {2,3,4,11,12,13,14,15,23,25,32,37,39,41,42,55,56,49,571,601,602};
		
		int[] specifyQuancunFields = {2,3,4,11,12,13,14,15,23,25,32,37,39,41,42,44,53,55,49,571,601,602,603,604,605,631};
		int[] notSpecifyQuancunFields = {2,3,4,11,12,13,14,15,23,25,32,37,39,41,42,44,48,53,55,49,571,601,602,603,604,605,631};
		int[] offlinePurchaseFields= {2,3,4,11,12,13,14,15,25,32,37,38,39,41,42,44,49,50,54,55,56,571,601,602,631};	
		int[] scriptNoticeFields= {2,3,4,11,12,13,15,22,23,32,37,38,39,41,42,48,49,55,601,602,603,604,605,61,611,612,613};	
		//交易类
		fieldsMap.put(TransType.BALANCE_INQUIRY, balanceInquiryFields);
		fieldsMap.put(TransType.PURCHASE, purchaseFields);
		fieldsMap.put(TransType.PURCHASE_REVERSAL, purchaseReversalFields);
		fieldsMap.put(TransType.PURCHASE_VOID, purchaseVoidFields);
		fieldsMap.put(TransType.PURCHASE_VOID_REVERSAL, purchaseVoidReversalFields);
		fieldsMap.put(TransType.PRE_AUTH, preAuthFields);
		fieldsMap.put(TransType.PRE_AUTH_REVERSAL, preAuthReversalFields);
		fieldsMap.put(TransType.PRE_AUTH_VOID, preAuthVoidFields);
		fieldsMap.put(TransType.PRE_AUTH_VOID_REVERSAL, preAuthVoidReversalFields);
		fieldsMap.put(TransType.PRE_AUTH_COMP, preAuthCompFields);
		fieldsMap.put(TransType.PRE_AUTH_COMP_REVERSAL, preAuthCompReversalFields);
		fieldsMap.put(TransType.PRE_AUTH_COMP_VOID, preAuthCompVoidFields);
		fieldsMap.put(TransType.PRE_AUTH_COMP_VOID_REVERSAL, preAuthCompVoidReversalFields);
		fieldsMap.put(TransType.SPECIFY_QUANCUN, specifyQuancunFields);
		fieldsMap.put(TransType.SPECIFY_QUANCUN_REVERSAL, specifyQuancunFields);
		fieldsMap.put(TransType.NOT_SPECIFY_QUANCUN, specifyQuancunFields);
		fieldsMap.put(TransType.NOT_SPECIFY_QUANCUN_REVERSAL, notSpecifyQuancunFields);
		fieldsMap.put(TransType.CASH_RECHARGE_QUNCUN, specifyQuancunFields);
		fieldsMap.put(TransType.CASH_RECHARGE_QUNCUN_REVERSAL, specifyQuancunFields);
		fieldsMap.put(TransType.OFFLINE_PURCHASE, offlinePurchaseFields);
		fieldsMap.put(TransType.PURCHASE_SCRIPT_NOTICE, scriptNoticeFields);
		fieldsMap.put(TransType.SPECIFY_QUANCUN_NOTICE, scriptNoticeFields);
		fieldsMap.put(TransType.PREAUTH_SCRIPT_NOTICE, scriptNoticeFields);
		fieldsMap.put(TransType.BALANCE_INQUIRY_NOTICE, scriptNoticeFields);
		fieldsMap.put(TransType.NOT_SPECIFY_QUANCUN_NOTICE, scriptNoticeFields);
		fieldsMap.put(TransType.CASH_RECHARGE_QUNCUN_NOTICE, scriptNoticeFields);
		//管理类
		fieldsMap.put(TransType.SIGN_IN, signInFields);
		fieldsMap.put(TransType.SIGN_OFF, signOffFields);
		fieldsMap.put(TransType.SETTLE, settleFields);
		fieldsMap.put(TransType.BATCH_UP, batchUpFields);

		fieldsMap.put(TransType.DOWNLOAD_MAIN_KEY, mainKeyFields);
		fieldsMap.put(TransType.DOWNLOAD_IC_REQUEST, signInFields);
		fieldsMap.put(TransType.DOWNLOAD_IC_TRANSFER, signInFields);
		fieldsMap.put(TransType.DOWNLOAD_IC_END, signInFields);
		
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
		    	if(fieldIndex == 51){ //TLV MAP  	
		    		Map<String,String> map = extendPayBean.getUnionPayBean().getCurrencyCodeCardholderMap();
		    		if(map != null && map.size() > 0){
		    			TLVObject[] tlvs = new TLVObject[map.size()];
		    			Iterator<String> it = map.keySet().iterator();
		    			int k = 0;
		    			while(it.hasNext()) {
		    				String key = it.next();
		    				tlvs[k] = new TLVObject(key,map.get(key).getBytes("GBK"));
		    				k++;
		    			}
		    			byte[] v = (new BerTlvEncoder()).encode(tlvs);
		    			value = ISO8583Utile.bytesToHexString(v);
		    		}
		    	}else{
		    		value = (String)field.get(extendPayBean.getUnionPayBean());	
		    	}
		    	
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
