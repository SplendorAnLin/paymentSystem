package com.pay.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.common.util.ISO8583.ISO8583Utile;
import com.pay.sign.ByteArrayUtil;
import com.pay.sign.Constant;
import com.pay.sign.TransTypeMapping;
import com.pay.sign.bean.ExtendPayBean;
import com.pay.sign.bean.UnionPayBean;
import com.pay.sign.business.SignBusiness;
import com.pay.sign.utils.ByteUtil;
import com.pay.sign.utils.PosMessageDecoder;


public class SignTest {
	private static Log log = LogFactory.getLog(SignTest.class);
	private static int MAXLENGTH = 2048;
	
	public static void main(String[] args) throws IOException {
		
		
		//解析
//		byte[] result = packageISO();
//		parse(result);
		
		//请求服务
		//send(result);
		
		//测试
//		test();
		test();
		
		//System.out.println(Integer.toHexString(219));
	}
	
	public static void test() throws IOException{
		 String ss="0572600004000060220000000009005020000008C000151962270033245600341790000000001000000002323433363531333839383837383038393534333838373431313031343732323238333000080700000104870000010000000180000000B4000000050800031CFF02FF02FF02FF02FF02FF02FF02FF02FF02FF02B0A6B6C0FF0238B783DD3F8CB2ECE4FF02A8FC4B0A9438E17657613613E74B26AC2AF85FFF0293A8601EBED1BCB623AEE4AAF9C5DCFEDF6E65FF024DB163DCFDD3747D7DE6FF0070807DC77F48FF029D98F3697010EFE5DAC25DD6FF02D8D32B0D73D19979E30DED59ACD8FF0200632EFB542D4FF23C54BA06B8930650491A7E6F1934C27FB840A9729DCF90FF025807E6BCBDA9166B094943C15887094B845250AB69856BE86C90D0FEFB244A9817F36D5EAAE230FF026C1769172B2248B82098EDD1A7A2479B10298B1A39C1E97E9510CD6CABC9B3E1AF227EBD9C495A05339D547AE85D3B18F0FF024D6E80C0463E2FF09A2E20CC7D7D2C59CE715C5AC975EB4A6E6A153072DE4680FF0235B3E90F8E36700F476A754B83AE5E36A1419E367FE3868EFF022172C9E1A16D2687578EEE5C3EAA2FB6B7B8615B2B9CFF020F0F05F0B98D10E99CF8DADC359CC73506DEDB90FF024D955F0EEA2300DA1E4CBE77E3B65735CFDDFF00D9D22FA0FF024D6A89D26AA10CDADAC8800996C572AA6E206F122EFF02750FB703620CC74999D9249B479B3F3FDAFF028AA2D4B25718FF02FF02FF02FF02FF02FF02FF02FF02FF02FF02228FB9D61FD111600F6750FF024533394331314137";
		 sendToBank(ISO8583Utile.hexStringToByte(ss),"123.57.78.28",28001);
	}
	
	public static  byte[] sendToBank(byte[] data,String ip,int port) throws IOException {
		
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
	private static void test1(){
		/*
		 *
		 */
		//组装的报文:0572600004000060220000000009005020000008C000151962270033245600341790000000001000000002323433363531333839383837353031303133363138373431313031343732323238333000080700000104870000010000000180000000B4000000050800031CFF02FF02FF02FF02FF02FF02FF02FF02FF02FF02B0A6B6C0FF0238B783DD3F8CB2ECE4FF02A8FC4B0A9438E17657613613E74B26AC2AF85FFF0293A8601EBED1BCB623AEE4AAF9C5DCFEDF6E65FF024DB163DCFDD3747D7DE6FF0070807DC77F48FF029D98F3697010EFE5DAC25DD6FF02D8D32B0D73D19979E30DED59ACD8FF0200632EFB542D4FF23C54BA06B8930650491A7E6F1934C27FB840A9729DCF90FF025807E6BCBDA9166B094943C15887094B845250AB69856BE86C90D0FEFB244A9817F36D5EAAE230FF026C1769172B2248B82098EDD1A7A2479B10298B1A39C1E97E9510CD6CABC9B3E1AF227EBD9C495A05339D547AE85D3B18F0FF024D6E80C0463E2FF09A2E20CC7D7D2C59CE715C5AC975EB4A6E6A153072DE4680FF0235B3E90F8E36700F476A754B83AE5E36A1419E367FE3868EFF022172C9E1A16D2687578EEE5C3EAA2FB6B7B8615B2B9CFF020F0F05F0B98D10E99CF8DADC359CC73506DEDB90FF024D955F0EEA2300DA1E4CBE77E3B65735CFDDFF00D9D22FA0FF024D6A89D26AA10CDADAC8800996C572AA6E206F122EFF02750FB703620CC74999D9249B479B3F3FDAFF028AA2D4B25718FF02FF02FF02FF02FF02FF02FF02FF02FF02FF02228FB9D61FD111600F6750FF024533394331314137
		//测试的报文:
		//
		//
		//
		String sss = "00000000001401400000000000000000000000000000000000000000000000" ;
		//3800000075
		System.out.println(new String(ISO8583Utile.hexStringToByte("33383030303030303735")));
		System.out.println("600004000060220000000009005020000008C0001516622228010149349900000000170000000830323031343430303030303036303839353434383532373631363038313139333530380008220000040292000001000000007B0000003B000000020800031CA853CE80FF0227A820FF024C11FF021980FF0207C0FF02E1D436B99A20FF02F3C4251B5EB5E04450FF029E441B433AF0FE50FF0253D25CB560D0FF026BDB3CFE8923B386FF02C8FB381ABE9568AE7AD7E0FF02343D3F7777B5FE4020EEED80FF02CE9FB9CC0919CC6C7E22ACFF02E549A7610866124B80FF02F51612DA6183BDB064FF0259908C25CCFABACADDFF0225A86149B3CB26D0FF02005D93694536FF008CFF02557057E8947DFAF11840FF0246540E55136CC48314FF0234B9C952C1592E57CBA29EFF0250B17C2793F39304A958FF0254F4C375A8972812CEFF029292B9044F9D6330FF0248AA9E6898FF021C7B813C7EFF022D1991D739EEC0FF0252D79257FF02503ACDB280FF0203FF024442414539344331".equals("600004000060220000000009005020000008C0001516622228010149349900000000170000000830323031343430303030303036303839353434383532373631363038313139333530380008220000040292000001000000007B0000003B000000020800031CA853CE80FF0227A820FF024C11FF021980FF0207C0FF02E1D436B99A20FF02F3C4251B5EB5E04450FF029E441B433AF0FE50FF0253D25CB560D0FF026BDB3CFE8923B386FF02C8FB381ABE9568AE7AD7E0FF02343D3F7777B5FE4020EEED80FF02CE9FB9CC0919CC6C7E22ACFF02E549A7610866124B80FF02F51612DA6183BDB064FF0259908C25CCFABACADDFF0225A86149B3CB26D0FF02005D93694536FF008CFF02557057E8947DFAF11840FF0246540E55136CC48314FF0234B9C952C1592E57CBA29EFF0250B17C2793F39304A958FF0254F4C375A8972812CEFF029292B9044F9D6330FF0248AA9E6898FF021C7B813C7EFF022D1991D739EEC0FF0252D79257FF02503ACDB280FF0203FF024442414539344331"));
		
		
	}
	
	public static final String SOCKET_IP = "10.10.110.34";
	public static final int SOCKET_PORT = 28004;
	
	public static void send(byte[] dataBytes){
		InputStream ins = null;
		OutputStream ops = null;
		Socket socket = null;
		
		try {
			//请求服务
			socket = new Socket(SOCKET_IP, SOCKET_PORT);
			ops = socket.getOutputStream();
			ops.write(dataBytes);
			ops.flush();
			ins = socket.getInputStream();
			byte[] _resultByte = new byte[MAXLENGTH];
			int len = ins.read(_resultByte);
			byte[] resultByte = new byte[len];
			System.arraycopy(_resultByte, 0, resultByte, 0, len);
			System.out.println("服务端返回:---------");
			//解析返回
			parse( resultByte);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ins != null)
					ins.close();
				if (ops != null)
					ops.close();
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void parse(byte[] resultByte){
		
		System.out.println(ISO8583Utile.Byte2Hex(resultByte,resultByte.length));
		//2.解析报文
		UnionPayBean unionPayBean2 = new UnionPayBean();		
		try {
			ExtendPayBean extendPayBean2 = new ExtendPayBean();
			unionPayBean2 = PosMessageDecoder.Decoding(resultByte, unionPayBean2);
			extendPayBean2.setUnionPayBean(unionPayBean2);
			extendPayBean2 = TransTypeMapping.getTransType(extendPayBean2);	
			System.out.println("ResponseCode:"+unionPayBean2.getResponseCode());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static byte[] packageISO(){
		/**1.组装报文*/
		
		//消息类型
		String _mesagetype = "0900" ;
		byte[] mesagetype = new byte[Constant.LEN_POS_MESSAGETYPE];
		mesagetype = ISO8583Utile.string2Bcd(_mesagetype);
		//(19)6227003324560034179（2主帐号）
		String _mainAccount = "19" ;
		byte[] mainAccount1 = ISO8583Utile.string2Bcd(_mainAccount);
		_mainAccount = "6227003324560034179";//左靠BCD码
		if(_mainAccount.length() % 2 != 0){ 
			_mainAccount = ISO8583Utile.StringFillRightZero(_mainAccount, _mainAccount.length() +1);
		}else{
			_mainAccount = ISO8583Utile.StringFillRightZero(_mainAccount, _mainAccount.length() );
		}
		byte[] mainAccount2 = ISO8583Utile.string2Bcd(_mainAccount);
		//000000001000（4交易金额）
		String _tradeAmount = "000000001000" ;
		byte[] tradeAmount = ISO8583Utile.string2Bcd(_tradeAmount);
		//000002（11 系统跟踪号）
		String _sysTraceNO = "000002" ;
		byte[] sysTraceNO = ISO8583Utile.string2Bcd(_sysTraceNO);
		//020037020037（37检索参考号，订单号）
		//243651389887
		String _orderNO = "243651389887" ;
		byte[] orderNO = _orderNO.getBytes();
		//12345678（41终端号）
		//50101361
		String _terminalNO = "50101361" ;
		byte[] terminalNO = _terminalNO.getBytes();
		//123456789012345（42店铺号）
		//874110147222830
		String _shopNO = "874110147222830" ;
		byte[] shopNO = _shopNO.getBytes();
		//07000001（60 (8)消息类型码07，批次号000001）
		String _field60 = "07000001" ;
		byte[] field60_1 = ISO8583Utile.string2Bcd("0");
		byte[] field60_2 = ISO8583Utile.string2Bcd("8") ;
		byte[] field60_3 = ISO8583Utile.string2Bcd(_field60) ;
		//62 = (487)签名图片数据
		String _sign = "0000010000000180000000B4000000050800031CFF02FF02FF02FF02FF02FF02FF02FF02FF02FF02B0A6B6C0FF0238B783DD3F8CB2ECE4FF02A8FC4B0A9438E17657613613E74B26AC2AF85FFF0293A8601EBED1BCB623AEE4AAF9C5DCFEDF6E65FF024DB163DCFDD3747D7DE6FF0070807DC77F48FF029D98F3697010EFE5DAC25DD6FF02D8D32B0D73D19979E30DED59ACD8FF0200632EFB542D4FF23C54BA06B8930650491A7E6F1934C27FB840A9729DCF90FF025807E6BCBDA9166B094943C15887094B845250AB69856BE86C90D0FEFB244A9817F36D5EAAE230FF026C1769172B2248B82098EDD1A7A2479B10298B1A39C1E97E9510CD6CABC9B3E1AF227EBD9C495A05339D547AE85D3B18F0FF024D6E80C0463E2FF09A2E20CC7D7D2C59CE715C5AC975EB4A6E6A153072DE4680FF0235B3E90F8E36700F476A754B83AE5E36A1419E367FE3868EFF022172C9E1A16D2687578EEE5C3EAA2FB6B7B8615B2B9CFF020F0F05F0B98D10E99CF8DADC359CC73506DEDB90FF024D955F0EEA2300DA1E4CBE77E3B65735CFDDFF00D9D22FA0FF024D6A89D26AA10CDADAC8800996C572AA6E206F122EFF02750FB703620CC74999D9249B479B3F3FDAFF028AA2D4B25718FF02FF02FF02FF02FF02FF02FF02FF02FF02FF02228FB9D61FD111600F6750FF02" ;
		byte [] sign1 = ISO8583Utile.string2Bcd("4");
		byte [] sign2 = ISO8583Utile.string2Bcd("87");
		byte [] sign3 = ISO8583Utile.hexStringToByte(_sign);
		//64报文签名mac
		String _mac = "4533394331314137" ;
		byte[] mac = ISO8583Utile.hexStringToByte(_mac);
		
		//位图BitMap50 20 00 00 08 C0 00 15
		String _bitMap = "5020000008C00015";
		byte[] bitMap = ISO8583Utile.hexStringToByte(_bitMap);
		
		//TPDU
		String _tpdu = "6000040000" ;
		byte[] tpdu = ISO8583Utile.hexStringToByte(_tpdu);
		//报文头
		String _head = "602200000000" ;
		byte[] head = ISO8583Utile.hexStringToByte(_head);
		//报文长度
		int _messageLength = 
				mesagetype.length + 
				mainAccount1.length + 
				mainAccount1.length + 
				tradeAmount.length + 
				sysTraceNO.length + 
				orderNO.length +
				terminalNO.length + 
				shopNO.length +
				field60_1.length + 
				field60_2.length + 
				field60_3.length +
				sign1.length +
				sign2.length +
				sign3.length + 
				mac.length + 
				bitMap.length + 
				tpdu.length +
				head.length + 2 ;
		System.out.println("报文长度:"+_messageLength);
		byte[] messageLength = ByteUtil.int2bcd(_messageLength, 2);
		//byte[] messageLength = ISO8583Utile.hexStringToByte("0572");
		//byte[] messageLength = Integer.toHexString(_messageLength).getBytes();
		
		List<byte[]> srcArrays = new ArrayList<byte[]>();
		
		//报文长度
		srcArrays.add(messageLength);
		//TPDU
		srcArrays.add(tpdu);
		//报文头
		srcArrays.add(head);
		//信息类型
		srcArrays.add(mesagetype);
		//位图
		srcArrays.add(bitMap);
		srcArrays.add(mainAccount1);
		srcArrays.add(mainAccount2);
		srcArrays.add(tradeAmount);
		srcArrays.add(sysTraceNO);
		srcArrays.add(orderNO);
		srcArrays.add(terminalNO);
		srcArrays.add(shopNO);
		srcArrays.add(field60_1);
		srcArrays.add(field60_2);
		srcArrays.add(field60_3);
		srcArrays.add(sign1);
		srcArrays.add(sign2);
		srcArrays.add(sign3);
		srcArrays.add(mac);
		
		byte[] result = ByteArrayUtil.streamCopy(srcArrays );
		
		System.out.println("组装的报文：");
		System.out.println(ISO8583Utile.Byte2Hex(result,result.length));
		
		return result;
	}
}