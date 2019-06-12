package com.yl.pay.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.common.util.EsscUtil;
import com.pay.common.util.PropertyUtil;
import com.pay.common.util.StringUtil;
import com.pay.common.util.ISO8583.ISO8583Utile;
import com.yl.pay.pos.bean.UnionPayBean;
import com.yl.pay.pos.exception.trans.BankNeedReSignInException;
import com.yl.pay.pos.exception.trans.BankNeedReversalException;
import com.yl.pay.pos.interfaces.P500001.CmbcTransException;
import com.yl.pay.pos.interfaces.P500001.Constant;

public class NCCBTransUtilForBank {

	private static final Log log = LogFactory.getLog(NCCBTransUtilForBank.class);
	
	private static int port =12581;
	private static String ip= "192.168.0.22";
//	private static int port =12581;
//	private static String ip= "192.168.0.50";
	private static PropertyUtil propertyUtil =null;
	private static int MAXLENGTH = 1024;
//	static{
//		propertyUtil = PropertyUtil.getInstance("transConfig");
//		ip = propertyUtil.getProperty("zm.pos.trans.ip");
//		port = Integer.parseInt(propertyUtil.getProperty("zm.pos.trans.port"));
//	}
	
	/**
	 * 用于向建行发送请求
	 * @param bocReq
	 * @param mccCode
	 * @return
	 * @throws CCBNeedReversalException
	 * @throws ConnectSecretMachException 
	 * @throws CcbSzNeedReSignonException 
	 */
	public UnionPayBean send2Bank(CmbcISO8583ForBank ccbRequest)
			throws   Exception {
		byte[] res_byte=null;
		byte[] send_byte=null;
		//报文组装
		try {
			send_byte = ccbRequest.Encoding();
		} catch(Exception e) {
			log.info("-----CCBTransUtil encoding ",e);
			throw new  Exception(CmbcTransException.ERROR_ENCODING_CCB8583);
		}	
		//发送至银行
		try {
			log.info("bank request HexString : "+ISO8583Utile.bytesToHexString(send_byte));
//			String ss="011A60000000036031003110040210603C06C10AD28E111862290841300555271531000000000300021209140000051000010012080009690031303030303135353233353130303130303535363830333035313130303534313130303032223033303930303030202020303330353639303020202001013330353530323235343131393231312C30303030303031302C3231383038322C3030303030312C3233353732383533373837392C3233353732383533373837392C32303137303931333233353732382CE9878DE5AEA2E99A86E4B883E6989FE5B297E5BA973135362600000000000000002031303032313536433030303030303030323430370012910A0C0E2A5E8648965230300013010000010005003037393935373133";
//			res_byte = ISO8583Utile.hexStringToByte(ss);
			res_byte = sendToBank(send_byte,ip,port);
			
		}catch( Exception e){
			log.info("-----CCBTransUtil sendToBank",e);
			throw new BankNeedReversalException();
		}
	 
		log.info("bank response HexString : "+ISO8583Utile.bytesToHexString(res_byte));
		
		// 验证数据头长度和报文长度是否一致
		byte[] len = new byte[2];		
		System.arraycopy(res_byte, 0, len, 0, 2);
		int messagelen = Integer.parseInt(ISO8583Utile.bytesToHexString(len), 16);	
		if (messagelen != res_byte.length - 2) {
			log.info("------CCBTransUtil messageLength error!");
			throw new BankNeedReversalException();
		}
	
		// 解析银行返回报文
		UnionPayBean res_bean = ccbRequest.Decoding(res_byte, messagelen);
		
		res_bean.setOrgIso8583Msg(ISO8583Utile.bytesToHexString(res_byte));//存放原始报文信息
		
		if (StringUtil.notNull(res_bean.getResponseCode()) && !Constant.BANK_RESP_CODE_00.equals(res_bean.getResponseCode())) {
			if(Constant.BANK_RESP_CODE_63.equals(res_bean.getResponseCode())||Constant.BANK_RESP_CODE_A0.equals(res_bean.getResponseCode())){
				throw new BankNeedReSignInException();
			}
		}
		
		
		return res_bean;
	}
	
	
	public byte[] sendToBank(byte[] data,String ip,int port) throws IOException {
		
		Socket client = new Socket(); 
		OutputStream ops = null;
		InputStream ins = null;
		
		int timeoutConect = 50000;			// 设置连接超时时间50秒
		int timeoutRevice = 60000;  		// 接收数据超时时间60秒
		long begintime = 0, endtime = 0;  
		
		log.info("** Send date to " + ip +":" + port );
		
		try {
			begintime = System.currentTimeMillis(); 
			client.connect(new InetSocketAddress(InetAddress.getByName(ip), port), timeoutConect); 
			client.setSoTimeout(timeoutRevice);   
			
			ops = client.getOutputStream();
			ops.write(data);
			ops.flush();
			ins = client.getInputStream();
			byte[] _resultByte = new byte[MAXLENGTH];
			int len = ins.read(_resultByte);
			if(len==-1){//
				throw new SocketTimeoutException();
			}
			byte[] resultByte = new byte[len];
			System.arraycopy(_resultByte, 0, resultByte, 0, len);
			
			return resultByte;			
		} catch (SocketTimeoutException e) { 
            throw e;
        }  catch (IOException e) {
			throw e;
		} finally {
			
			endtime = System.currentTimeMillis();  
			log.info("** use time: "+(endtime - begintime)+" ms");  
			
			if (ins != null)
				ins.close();
			if (ops != null)
				ops.close();
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					log.info("close socket fail",e);
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 校验MAC
	 * @param bean
	 * @return
	 * @throws ConnectSecretMachException 
	 */
	public boolean checkMAC(String bankCati,byte[] macbyte,String retrunMac) throws  Exception {
		String macString="";
		try {
			byte[] temp =new byte[macbyte.length-21];
			System.arraycopy(macbyte, 13 , temp , 0 , temp.length);
			macString = EsscUtil.createMacForBank(Constant.INTERFACE_CODE,bankCati,temp); 		// 根据8583报文生成mac
		} catch (Exception e) {
			throw new  Exception(" create ccb mac error");
		}
		if(retrunMac.equals(macString.substring(0, 8)))
			return true;
		else 
			return false;
	}
	
	public static int getResLen(String resInfo){
		return 	Integer.valueOf(resInfo.substring(0, 2))*100 +Integer.valueOf(resInfo.substring(2));
	}
	
	/**
	 * 用于在签到后更新MAC PIN
	 * @param field62Str 62域
	 * @param bankCati	银行终端号
	 * @return
	 * @throws UpdateMacAndPinException
	 */
	public static boolean updateMacAfterSignon(String field62Str,String bankCati ) throws Exception{
		log.info("updateMacAfterSignon frpCode="+Constant.INTERFACE_CODE +" bankCati="+bankCati+" MACPINSTRING="+field62Str);
		String pinKey=field62Str.substring(0, 32);				//PINKEY
		String pinChkval=field62Str.substring(32, 40);			//CHKVAL
		String macKey=field62Str.substring(40, 56);				//MACKEY
		String macChkval=field62Str.substring(72, 80);			//CHKVAL
		try {
			EsscUtils.storeBankKey(Constant.INTERFACE_CODE, bankCati, Constant.ESSC_PIN, pinKey, pinChkval);
			EsscUtils.storeBankKey(Constant.INTERFACE_CODE, bankCati, Constant.ESSC_MAC, macKey, macChkval);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("update mac and pin error");
		}
	}
	
	//PIN转加密
	public static String translatePin(String posCati,String bankPosCati, String pin, String pan) throws Exception {
		
		try {
			return EsscUtils.translatePin(posCati, Constant.INTERFACE_CODE, bankPosCati, pin, pan);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}		
	}

	/**
	 * 验证主卡号
	 */
	public static boolean checkPrimary_acct_num(String primary_acct_num)
			throws CmbcTransException {
		if (primary_acct_num.length() > 19)
			throw new CmbcTransException(
					CmbcTransException.ERROR_DATA_LENGTH);
		if (!isLong(primary_acct_num))
			throw new CmbcTransException(CmbcTransException.ERROR_DATA_TYPE);
		else
			return true;
	}
	
	/**
	 * 检验字符串中是否全是数字
	 * @param str
	 * @return
	 */
	private static boolean isLong(String str) {
		return str.matches("^[\\+-]?[\\d]+$");
	}


	

	
}
