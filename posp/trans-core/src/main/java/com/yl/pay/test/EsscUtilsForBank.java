package com.yl.pay.test;


import com.pay.common.util.DesUtil;
import com.pay.common.util.ISO8583.ISO8583Utile;
import com.yl.pay.pos.entity.SecretKey;
import com.yl.pay.pos.service.SecretKeyService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.KeyGenerator;

public class EsscUtilsForBank {
	private static final Log log = LogFactory.getLog(EsscUtilsForBank.class);
	private static SecretKeyService secretKeyService;
	
	public static String generateCoverSecretPos(String posCati) throws Exception{
		StringBuffer switchingData = new StringBuffer();
		SecretKey secretKey=secretKeyService.findByKey("pos."+posCati+".zmk");
		KeyGenerator kg=KeyGenerator.getInstance("DES");
		kg.init(56);
		String mac=ISO8583Utile.bytesToHexString(kg.generateKey().getEncoded());
		String macKey=DesUtil.desEncrypt(mac, secretKey.getKey());
		String macCheck=DesUtil.desEncrypt("0000000000000000",mac);
		log.info("mac:"+mac+",macKey:"+macKey+",macCheck:"+macCheck);
		SecretKey zaksecretKey=secretKeyService.findByKey("pos."+posCati+".zak");
		zaksecretKey.setKey(mac);
		zaksecretKey.setCheckValue(macCheck);
		zaksecretKey=secretKeyService.update(zaksecretKey);
		String pin=ISO8583Utile.bytesToHexString(kg.generateKey().getEncoded());
		String pinKey=DesUtil.desEncrypt(pin, secretKey.getKey());
		String pinCheck=DesUtil.desEncrypt( "0000000000000000",pin);
		log.info("pin:"+pin+",pinKey:"+pinKey+",pinKey:"+pinCheck);
		SecretKey zpkSecretKey=secretKeyService.findByKey("pos."+posCati+".zpk");
		zpkSecretKey.setKey(pin);
		zpkSecretKey.setCheckValue(pinCheck);
		zpkSecretKey=secretKeyService.update(zpkSecretKey);
		return switchingData.append(pinKey+pinCheck.substring(0, 8)+macKey+macCheck.substring(0, 8)).toString();
	}
	
	public static String createPosMac(String fullName,String makeMacString){
		//SecretKey secretKey=secretKeyService.findByKey(fullName);
		SecretKey secretKey = new SecretKey();
		secretKey.setKeyName("bank.P500001_10000104.zak");
		secretKey.setKey("9DD54F1C790D10C4");
		secretKey.setCheckValue("F7771198");
		String before8=ISO8583Utile.toHexString(makeMacString.substring(0,8));
		String posMac=DesUtil.desEncrypt(before8,secretKey.getKey());
		String after8=ISO8583Utile.toHexString(makeMacString.substring(8,16));
		byte [] xfo=ISO8583Utile.string2Bcd(posMac+after8);
		String xforesult=ISO8583Utile.getXorString(xfo);
		String pospMac = DesUtil.desEncrypt(xforesult,secretKey.getKey());
		return pospMac;
	}

	public static void storeBankKey(String interfaceCode,String bankPosCati,String type,String key,String checkValue){
		SecretKey secretKey=secretKeyService.findByKey("bank."+interfaceCode+"_"+bankPosCati+".zmk");
		SecretKey zpkSecretKey=secretKeyService.findByKey("bank."+interfaceCode+"_"+bankPosCati+"."+type);
		zpkSecretKey.setKey(DesUtil.desDecrypt1(key, secretKey.getKey()));
		zpkSecretKey.setCheckValue(checkValue);
		secretKeyService.update(zpkSecretKey);
	}
	
	public static String translatePin(String posCati,String interfaceCode,String bankPosCati,String pin,String pan){
		SecretKey bankZpkKey=secretKeyService.findByKey("bank."+interfaceCode+"_"+bankPosCati+".zpk");
		SecretKey posZpkKey=secretKeyService.findByKey("pos."+posCati+".zpk");
		byte [] accBlock=ISO8583Utile.getHAccno(pan);
		String xfor1=DesUtil.desDecrypt1(pin, posZpkKey.getKey());
		String pass=ISO8583Utile.bytesToHexString(ISO8583Utile.getXorbyte(ISO8583Utile.hexStringToByte(xfor1),accBlock));

		String xfor=ISO8583Utile.bytesToHexString(ISO8583Utile.getXorbyte(accBlock, ISO8583Utile.getHPin(pass.substring(2,8))));
		String pik=DesUtil.desEncrypt(xfor, bankZpkKey.getKey());
		return pik;
	}
	public static SecretKeyService getSecretKeyService() {
		return secretKeyService;
	}

	public void setSecretKeyService(SecretKeyService secretKeyService) {
		EsscUtilsForBank.secretKeyService = secretKeyService;
	}



	public static void main(String[] args) {
		byte [] by=ISO8583Utile.getAsciiBytes("0210 166221639901374640 000000 000000000100 0217134042 500002 4816 00 0899991114 0899991128 011394 14 80030012 309610148168003");
		System.out.println(ISO8583Utile.bytesToHexString(by));
		String pospMac = DesUtil.desEncrypt(ISO8583Utile.bytesToHexString(by),"6E2A8F49019BBAF4");
//		String posMac=DesUtil.desEncrypt(ISO8583Utile.bytesToHexString(by),"6E2A8F49019BBAF4");
		System.out.println(pospMac);
	}

	
	
	
}
