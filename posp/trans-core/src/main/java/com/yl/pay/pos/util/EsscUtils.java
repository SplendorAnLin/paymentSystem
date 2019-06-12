package com.yl.pay.pos.util;


import com.pay.common.util.DesUtil;
import com.pay.common.util.ISO8583.ISO8583Utile;
import com.yl.pay.pos.entity.SecretKey;
import com.yl.pay.pos.service.SecretKeyService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;

public class EsscUtils {
	private static final Log log = LogFactory.getLog(EsscUtils.class);
	private static SecretKeyService secretKeyService;
	
	public static String generateCoverSecretPos(String posCati) throws Exception{

		SecretKey secretKey=secretKeyService.findByKey("pos."+posCati+".zmk");
		String zmk = secretKey.getKey();
		if(zmk.length() ==16){
			return generateHaploidCoverSecretPos(zmk,posCati);
		}if(zmk.length() ==32){
			return  generateDoubeCoverSecretPos(zmk,posCati);
		} else {
			return  null;
		}
	}
	public static String generateMainSecretPos(String posCati,String tsk) throws Exception{
		StringBuffer switchingData = new StringBuffer();
		KeyGenerator kg=KeyGenerator.getInstance("DES");
		kg.init(56);
		String zmk=ISO8583Utile.bytesToHexString(kg.generateKey().getEncoded())+ISO8583Utile.bytesToHexString(kg.generateKey().getEncoded());
		String zmkKey=DesUtil.desEncrypt(zmk, tsk);
		String zmkCheck=DesUtil.desEncrypt( "0000000000000000",zmk);
		log.info("zmk:"+zmk+",zmkKey:"+zmkKey+",zmkCheck:"+zmkCheck);
		SecretKey zmkSecretKey=secretKeyService.findByKey("pos."+posCati+".zmk");
		zmkSecretKey.setKey(zmk);
		zmkSecretKey.setCheckValue(zmkCheck);
		zmkSecretKey=secretKeyService.update(zmkSecretKey);
		return switchingData.append(zmkKey+zmkCheck.substring(0, 8)).toString();
	}

	public static  String generateHaploidCoverSecretPos(String zmkKey,String posCati) throws Exception {
		StringBuffer switchingData = new StringBuffer();
		KeyGenerator kg=KeyGenerator.getInstance("DES");
		kg.init(56);
		String mac=ISO8583Utile.bytesToHexString(kg.generateKey().getEncoded());
		String macKey=DesUtil.desEncrypt(mac, zmkKey);
		String macCheck=DesUtil.desEncrypt("0000000000000000",mac);
		log.info("mac:"+mac+",macKey:"+macKey+",macCheck:"+macCheck);
		SecretKey zaksecretKey=secretKeyService.findByKey("pos."+posCati+".zak");
		zaksecretKey.setKey(mac);
		zaksecretKey.setCheckValue(macCheck);
		zaksecretKey=secretKeyService.update(zaksecretKey);
		String pin=ISO8583Utile.bytesToHexString(kg.generateKey().getEncoded());
		String pinKey=DesUtil.desEncrypt(pin, zmkKey);
		String pinCheck=DesUtil.desEncrypt( "0000000000000000",pin);
		log.info("pin:"+pin+",pinKey:"+pinKey+",pinKey:"+pinCheck);
		SecretKey zpkSecretKey=secretKeyService.findByKey("pos."+posCati+".zpk");
		zpkSecretKey.setKey(pin);
		zpkSecretKey.setCheckValue(pinCheck);
		zpkSecretKey=secretKeyService.update(zpkSecretKey);
		return switchingData.append(pinKey+pinCheck.substring(0, 8)+macKey+macCheck.substring(0, 8)).toString();
	}

	public static String generateDoubeCoverSecretPos(String zmkKey,String posCati) throws Exception{
		StringBuffer switchingData = new StringBuffer();
		KeyGenerator kg=KeyGenerator.getInstance("DES");
		kg.init(56);
		String mac=ISO8583Utile.bytesToHexString(kg.generateKey().getEncoded());
		String macKey=DesUtil.desEncrypt(mac, zmkKey);
		String macCheck=DesUtil.desEncrypt("0000000000000000",mac);
		log.info("mac:"+mac+",macKey:"+macKey+",macCheck:"+macCheck);
		SecretKey zaksecretKey=secretKeyService.findByKey("pos."+posCati+".zak");
		zaksecretKey.setKey(mac);
		zaksecretKey.setCheckValue(macCheck);
		zaksecretKey=secretKeyService.update(zaksecretKey);
		String pin=ISO8583Utile.bytesToHexString(kg.generateKey().getEncoded())+ISO8583Utile.bytesToHexString(kg.generateKey().getEncoded());

		String pinKey=DesUtil.desEncrypt(pin, zmkKey);
		String pinCheck=DesUtil.desEncrypt( "0000000000000000",pin);
		log.info("pin:"+pin+",pinKey:"+pinKey+",pinKey:"+pinCheck);
		SecretKey zpkSecretKey=secretKeyService.findByKey("pos."+posCati+".zpk");
		zpkSecretKey.setKey(pin);
		zpkSecretKey.setCheckValue(pinCheck);
		zpkSecretKey=secretKeyService.update(zpkSecretKey);
		return switchingData.append(pinKey+pinCheck.substring(0, 8)+macKey+"0000000000000000"+macCheck.substring(0, 8)).toString();
	}
	
	public static String createPosMac(String fullName,String makeMacString){
		SecretKey secretKey=secretKeyService.findByKey(fullName);
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
		EsscUtils.secretKeyService = secretKeyService;
	}




	public static void main(String[] args) {
		String makeMacString = "BCC679E3C75A09D8";
		System.out.println(makeMacString.substring(0,8));
		String before8=ISO8583Utile.toHexString(makeMacString.substring(0,8));
		System.out.println(before8);
		String posMac=DesUtil.desEncrypt(before8,"7683162A2F0B232C");
		System.out.println(posMac);
		String after8=ISO8583Utile.toHexString(makeMacString.substring(8,16));
		byte [] xfo=ISO8583Utile.string2Bcd(posMac+after8);
		String xforesult=ISO8583Utile.getXorString(xfo);
		String pospMac = DesUtil.desEncrypt(xforesult,"7683162A2F0B232C");
		System.out.println(pospMac);
	}

	
	
	
}
