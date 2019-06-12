package com.pay.test;

import java.util.ArrayList;
import java.util.List;

import com.pay.common.util.ISO8583.ISO8583Utile;
import com.pay.sign.ByteArrayUtil;
import com.pay.sign.Constant;
import com.pay.sign.TransTypeMapping;
import com.pay.sign.bean.ExtendPayBean;
import com.pay.sign.bean.UnionPayBean;
import com.pay.sign.utils.ByteUtil;
import com.pay.sign.utils.PosMessageDecoder;


public class SignReturnTest {

	public static final String SOCKET_IP = "10.10.110.34";
	public static final int SOCKET_PORT = 28001;
	
	public static void main(String[] args) {

		UnionPayBean unionPayBean = new UnionPayBean();
		
		try {
			//unionPayBean = PosMessageDecoder.Decoding(inputData, unionPayBean);				//原始报文解析
			//消息类型
			String _mesagetype = "0910" ;
			byte[] mesagetype = new byte[Constant.LEN_POS_MESSAGETYPE];
			mesagetype = ISO8583Utile.string2Bcd(_mesagetype);
			//位图BitMap10 38 00 00 02 C0 00 00
			String _bitMap = "1038000002C00000";
			byte[] bitMap = ISO8583Utile.hexStringToByte(_bitMap);
			//000000001000（4交易金额）
			String _tradeAmount = "000000001000" ;
			byte[] tradeAmount = ISO8583Utile.string2Bcd(_tradeAmount);
			//000002（11 系统跟踪号）
			String _sysTraceNO = "000002" ;
			byte[] sysTraceNO = ISO8583Utile.string2Bcd(_sysTraceNO);
			//170315（12受卡方所在地时间，hhmmss）
			String _receiceTime = "170315" ;
			byte[] receiceTime = ISO8583Utile.string2Bcd(_receiceTime);
			//0819（13受卡方所在地日期，MMDD）
			String _receiveDate = "0819" ;
			byte [] receiveDate = ISO8583Utile.string2Bcd(_receiveDate);
			// 00（39 交易应答码）
			byte[] responseCode = "00".getBytes() ;
			//12345678（41终端号）
			String _terminalNO = "12345678" ;
			byte[] terminalNO = _terminalNO.getBytes();
			//123456789012345（42 商户号）
			byte[] merchantCode = "123456789012345".getBytes();
			
			//TPDU
			String _tpdu = "6000040000" ;
			byte[] tpdu = ISO8583Utile.hexStringToByte(_tpdu);
			//报文头
			String _head = "602200000000" ;
			byte[] head = ISO8583Utile.hexStringToByte(_head);
			//报文长度
			int _messageLength = 
					mesagetype.length + 
					tradeAmount.length + 
					sysTraceNO.length + 
					receiceTime.length + 
					receiveDate.length + 
					responseCode.length +
					terminalNO.length + 
					merchantCode.length +
					bitMap.length + 
					tpdu.length +
					head.length + 2 ;
			byte[] messageLength = ByteUtil.int2bcd(_messageLength, 2);
			
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
			srcArrays.add(tradeAmount);
			srcArrays.add(sysTraceNO);
			srcArrays.add(receiceTime);
			srcArrays.add(receiveDate);
			srcArrays.add(responseCode);
			srcArrays.add(terminalNO);
			srcArrays.add(merchantCode);
			
			byte[] result = ByteArrayUtil.streamCopy(srcArrays );
			
			System.out.println("组装的返回报文：");
			System.out.println(ISO8583Utile.Byte2Hex(result,result.length));
			
			//2.解析报文
			UnionPayBean unionPayBean2 = new UnionPayBean();		
			try {
				ExtendPayBean extendPayBean2 = new ExtendPayBean();
				unionPayBean2 = PosMessageDecoder.Decoding(result, unionPayBean2);
				extendPayBean2.setUnionPayBean(unionPayBean2);
				extendPayBean2 = TransTypeMapping.getTransType(extendPayBean2);	
				System.out.println("response code: "+unionPayBean.getResponseCode());
				
				System.out.println("extendPayBean.getTransType().name():" + extendPayBean2.getTransType().name());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}catch (Throwable e) {
			e.printStackTrace();
		}finally{			
			
		}		

		
		
	}
	
}