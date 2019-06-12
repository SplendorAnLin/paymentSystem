package com.pay.sign.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.common.util.StringUtil;
import com.pay.common.util.ISO8583.ISO8583Utile;
import com.pay.common.util.ISO8583.POSISO8583Exception;
import com.pay.sign.Constant;
import com.pay.sign.bean.UnionPayBean;
import com.pay.sign.exception.TransExceptionConstant;
import com.pay.sign.exception.TransRunTimeException;
/**
 * Title: 
 * Description:   
 * Copyright: 2015年6月12日下午2:54:13
 * Company: SDJ
 * @author zhongxiang.ren
 */
public class PosMessageDecoder {
	
	private static Log log = LogFactory.getLog(PosMessageDecoder.class);
	
	public static UnionPayBean Decoding(byte[] inputData, UnionPayBean unionPayBeanRet) throws Exception {
				
		//log.info("pos input data(Hex):\n" + ISO8583Utile.bytesToHexString(inputData));
		
		byte[] inputDataCut = new byte[inputData.length-2];	//去掉两位报文长度
		int contenlen = inputData.length-2;
		System.arraycopy(inputData, 2, inputDataCut, 0, contenlen);
		int messageLen = inputData.length;
		int offset=0;
		
		UnionPayBean unionPayBean = new UnionPayBean();
		
		// 检查TPUD
		if (contenlen < 5) {			
			throw new Exception("data len less than TPUD length");
		}

		byte[] tpudtype = new byte[5];
		System.arraycopy(inputDataCut, offset, tpudtype, 0, Constant.LEN_POS_TPUD);
		
		contenlen -= Constant.LEN_POS_TPUD;
		offset += Constant.LEN_POS_TPUD;
		
		byte[] lri = new byte[Constant.LEN_POS_LRI];//拨号POS标识
		System.arraycopy(inputDataCut, offset, lri, 0, Constant.LEN_POS_LRI);
		
		String lriTemp  = new String(lri);
		log.info("the POS message head  lri ["+lriTemp+"] ");
		boolean lriFlag = false;
		if(Constant.CONTENT_POS_LRI.equals(lriTemp)){
			lriFlag = true;
			log.info("go into pos_lri success! contenlen["+contenlen+"]");
			int byteStart = offset;
			byte[] lrilength = new byte[2];
			System.arraycopy(inputDataCut, byteStart+3, lrilength , 0, 2);//长度
			String length = ISO8583Utile.bcd2String(lrilength, false);
			byteStart += Constant.LEN_POS_LRI;
			byteStart += 2;  //略过2字节的长度域
			byte[] sourcePhonebyte = new byte[Constant.LEN_POS_PHONE];
			System.arraycopy(inputDataCut, byteStart, sourcePhonebyte , 0, Constant.LEN_POS_PHONE);
			byteStart += Constant.LEN_POS_PHONE;
			byte[] destPhonebyte = new byte[Constant.LEN_POS_PHONE];
			System.arraycopy(inputDataCut, byteStart, destPhonebyte , 0, Constant.LEN_POS_PHONE);
			byteStart += Constant.LEN_POS_PHONE;
			byteStart += 4;  //略过4个字节的分隔符和长度域
			byte[] nodebyte = new byte[Constant.LEN_POS_NODE];
			System.arraycopy(inputDataCut, byteStart, nodebyte, 0, Constant.LEN_POS_NODE);
			byteStart += Constant.LEN_POS_NODE;
			offset += 33;  //略过33个字节的拨号POS特殊报文域  
			contenlen -= 33;  
			//处理来电电话和被叫电话
			String callPhoneNo = ISO8583Utile.bcd2String(sourcePhonebyte, false);
			String servicePhoneNo = ISO8583Utile.bcd2String(destPhonebyte, false);
			
			log.info("-+-+-+-+-+ callPhoneNo="+callPhoneNo+"--servicePhoneNo="+servicePhoneNo);
			
			unionPayBean.setCallPhoneNo(callPhoneNo);
			unionPayBean.setServicePhoneNo(servicePhoneNo);
			
		}

		// 检查报文头
		if (contenlen < Constant.LEN_POS_MESSAGE) {
			log.info("POSISO8583 Decoding parse message fail, data len less than CERTIFIED VERSION's length");
			throw new POSISO8583Exception(POSISO8583Exception.ERROR_RESPONSE_ITEM_TPUD);
		}		
		
		// 解析厂商
		byte[] versiontype = new byte[Constant.LEN_POS_MESSAGE];
		System.arraycopy(inputDataCut, offset, versiontype, 0, Constant.LEN_POS_MESSAGE);	
		String version = ISO8583Utile.bcd2String(versiontype, false);
		
		contenlen -= Constant.LEN_POS_MESSAGE;
		offset += Constant.LEN_POS_MESSAGE;
				
		byte[] decodeingByte = new byte[contenlen];
		System.arraycopy(inputDataCut, offset, decodeingByte, 0,contenlen);
		
		PosISO8583Common posiso8583 = new PosISO8583Common();		
		
		try {
			unionPayBean = (UnionPayBean)posiso8583.Decoding(decodeingByte, contenlen, unionPayBean);
			BeanUtils.copyProperties(unionPayBeanRet, unionPayBean);
		} catch (POSISO8583Exception e) {		
			log.info("pos decoding error!",e);
		}
		
		// 解析卡号
		String track2 = unionPayBean.getTrack2();		
	    if(StringUtil.notNull(track2)){
	    	boolean flag = track2.contains("=");
			if(flag){
				int i = track2.indexOf("=");
				unionPayBean.setPan(track2.substring(0, i));
			}else{
				unionPayBean.setPan(track2);
			}
	    }	
	    		
	    //验证MAC
		String macString = unionPayBean.getMac();				
		if(macString!=null){
			byte[] macInput=null;
			if(lriFlag){
				macInput = new byte[messageLen-54];//需要做异或的MAC数据
				System.arraycopy(inputData, 46, macInput, 0, messageLen-54);
			}else{
				macInput = new byte[messageLen-21];						//需要做异或的MAC数据
				System.arraycopy(inputData, 13, macInput, 0, messageLen-21);		
			}
			
			String makeMacString=ISO8583Utile.getXorString(macInput);
			String pospMac = null;
			try {
				pospMac =  EsscUtils.createPosMac("pos."+unionPayBean.getCardAcceptorTerminalId()+".zak",makeMacString).substring(0, 8);
			} catch (Exception e) {
				log.info("pos create MAC error!",e);
				throw new TransRunTimeException(TransExceptionConstant.POS_MAC_GENERATEERR);
			}
			log.info("macString: "+macString+",pospMac:"+pospMac);
			if(!macString.equals(pospMac)){//MAC校验不通过
				throw new TransRunTimeException(TransExceptionConstant.POS_MAC_CHECKERR);
			}
		}		
		return unionPayBean;
	}
}
