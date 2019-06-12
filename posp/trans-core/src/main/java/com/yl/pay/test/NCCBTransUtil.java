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

public class NCCBTransUtil {

	private static final Log log = LogFactory.getLog(NCCBTransUtil.class);
	
//	private static int port =6018;
//	private static String ip= "111.205.207.17";
	private static int port =12581;
	private static String ip= "192.168.0.50";
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
	public UnionPayBean send2Bank(CmbcISO8583 ccbRequest)
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
//			String ss="016B60000000036031003110040210703C06C00EC28E11166226186755888999000000000000000002000008173735090421120510000000123130303030303032313930323132333435363030313030303031303433303532323030353036353030303200663330353131303035313131323933352C31303030393230372C3130333530372C3030303030312C3132333435363738393031322C323031373035323631343535333031353626000000000000000020313030323135364330303030303031303132343601455F2A02015682027C008408A0000003330101019505008004E0009A031709049C01009F02060000000000029F03060000000000009F090200309F101307010103A02002010A01000000000012B3B4299F1A0201569F1E0837353532303534379F2608792139204F8727069F2701809F3303E0F9C89F34030203009F3501229F3602002A9F3704B071A7039F4104000000170013220000010005004430454334373042";
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
