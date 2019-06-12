package com.yl.pay.pos.access;

import com.pay.common.util.ISO8583.ISO8583Utile;
import com.pay.common.util.ISO8583.POSISO8583Exception;
import com.pay.common.util.DesUtil;
import com.pay.common.util.StringUtil;
import com.yl.pay.pos.Constant;
import com.yl.pay.pos.bean.UnionPayBean;
import com.yl.pay.pos.exception.TransExceptionConstant;
import com.yl.pay.pos.exception.TransRunTimeException;
import com.yl.pay.pos.util.EsscUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
		
		byte[] lri = new byte[Constant.LEN_POS_LRI];//拨号POS标识 或者 ip标识
		System.arraycopy(inputDataCut, offset, lri, 0, Constant.LEN_POS_LRI);
		String lriTemp  = new String(lri);
		log.info("request POS message head  flag ["+lriTemp+"] length ["+contenlen+"]");
		
		boolean lriFlag = false;
		boolean lriIpFlag = false;
		if(Constant.CONTENT_POS_LRI.equals(lriTemp)){
			lriFlag = true;
			int byteStart = offset;
			byte[] lrilength = new byte[2];
			System.arraycopy(inputDataCut, byteStart+3, lrilength , 0, 2);//长度
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
		int IPContentLength=0;
		if(Constant.CONTENT_IP_LRI.equals(lriTemp)){
			lriIpFlag = true;
			int byteStart = offset;
			byteStart += Constant.LEN_POS_LRI;
			offset += Constant.LEN_POS_LRI;
			contenlen -= Constant.LEN_POS_LRI;
			byte[] ipLength = new byte[1];
			ipLength[0] = inputDataCut[byteStart];
			byteStart += Constant.LEN_POS_IP;
			offset += Constant.LEN_POS_IP;
			contenlen -= Constant.LEN_POS_IP;
			//处理ip数据
			String ipData = ISO8583Utile.bcd2String(ipLength, false);
			int ipDataLength = Integer.parseInt(ipData);
			byte [] ip = new byte[ipDataLength];
			System.arraycopy(inputDataCut, byteStart, ip , 0, ipDataLength);
			byteStart +=ipDataLength;
			offset +=ipDataLength;
			contenlen -=ipDataLength;
			IPContentLength=4+ipDataLength;
			log.info("-+-+-+-+-+ request ip = "+ new String(ip));
			unionPayBean.setRequestIp(new String(ip));
		}
			
		// 检查报文头
		if (contenlen < Constant.LEN_POS_MESSAGE) {
			log.info("POSISO8583 Decoding parse message fail, data len less than CERTIFIED VERSION's length");
			throw new POSISO8583Exception(POSISO8583Exception.ERROR_RESPONSE_ITEM_TPUD);
		}
		
		// 解析厂商
		byte[] versiontype = new byte[Constant.LEN_POS_MESSAGE];
		System.arraycopy(inputDataCut, offset, versiontype, 0, Constant.LEN_POS_MESSAGE);	
//		String version = ISO8583Utile.bcd2String(versiontype, false);
		
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
			}else if(lriIpFlag){
				macInput = new byte[messageLen-(IPContentLength+21)];//需要做异或的MAC数据
				System.arraycopy(inputData, IPContentLength+13, macInput, 0, messageLen-(IPContentLength+21));
			}else{
				macInput = new byte[messageLen-21];					//需要做异或的MAC数据
				System.arraycopy(inputData, 13, macInput, 0, messageLen-21);		
			}
			
			String makeMacString=ISO8583Utile.getXorString(macInput);
			String pospMac = null;
			try {
				pospMac = EsscUtils.createPosMac("pos."+unionPayBean.getCardAcceptorTerminalId()+".zak",makeMacString).substring(0, 8);
//				String before8=ISO8583Utile.toHexString(makeMacString.substring(0,8));
//				String posMac=DesUtil.desEncrypt(before8,"04DCCB1FEC5B8C67");
//				String after8=ISO8583Utile.toHexString(makeMacString.substring(8,16));
//				byte [] xfo=ISO8583Utile.string2Bcd(posMac+after8);
//				String xforesult=ISO8583Utile.getXorString(xfo);
//				pospMac = DesUtil.desEncrypt(xforesult,"04DCCB1FEC5B8C67").substring(0, 8);
//				return pospMac;
			} catch (Exception e) {
				log.info("pos create MAC error!",e);
				throw new TransRunTimeException(TransExceptionConstant.POS_MAC_GENERATEERR);
			}
			
			if(!macString.equals(pospMac)){//MAC校验不通过
				throw new TransRunTimeException(TransExceptionConstant.POS_MAC_CHECKERR);
			}
		}
		unionPayBean.setOrgIso8583Msg(ISO8583Utile.bytesToHexString(decodeingByte));
		return unionPayBean;
	}
	
	public static void main(String[] args) {
		String a = "1662261867558889990000000000000000110000652112051000000012376226186755888999D21122200097565100000031303132313634303839333434303335343939303135313135360010DF110746552042494E4774103E950686D1C9200000000000000001459F2608C0535CED777565A89F2701809F101307010103A02002010A0100000000007E6D74059F370471202C239F360200539505008004E0009A031709079C01009F02060000000000115F2A02015682027C009F1A0201569F03060000000000009F3303E0F9C89F34030203009F3501229F1E0837353532303534378408A0000003330101019F090200309F4104000000650042303056323031353038303730312020202020202020205632303134303631303031202020202020202020001422000001000600009030312065323839333334323232347C323839333334383831327C323836393731313334327C323839333331313932317C323839333331323035327C32383936363535343935203839383630324234303731373730353732343234";
		String makeMacString=ISO8583Utile.getXorString(ISO8583Utile.hexStringToByte(a));
		System.out.println(makeMacString);
		
		String before8=ISO8583Utile.toHexString(makeMacString.substring(0,8));
		String posMac=DesUtil.desEncrypt(before8,"F8F2A8D0EF6B7F26");
		String after8=ISO8583Utile.toHexString(makeMacString.substring(8,16));
		byte [] xfo=ISO8583Utile.string2Bcd(posMac+after8);
		String xforesult=ISO8583Utile.getXorString(xfo);
		String pospMac = DesUtil.desEncrypt(xforesult,"F8F2A8D0EF6B7F26");
		System.out.println(pospMac);
		
	}
}
