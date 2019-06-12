package com.yl.pay.test;

import com.pay.common.util.ISO8583.ISO8583Utile;
import com.yl.pay.pos.interfaces.P500001.Constant;
import com.yl.pay.pos.util.Tlv;
import com.yl.pay.pos.util.TlvUtils;
import com.yl.pay.pos.util.codecs.common.tlv.BerTlvEncoder;
import com.yl.pay.pos.util.codecs.common.tlv.TLVEncoder;
import com.yl.pay.pos.util.codecs.common.tlv.TLVObject;

public class TestForBank {
	
	public static void main(String[] args) throws Exception {
		
//		KeyGenerator kg=KeyGenerator.getInstance("DES");
//		kg.init(56);
//		String mac=ISO8583Utile.bytesToHexString(kg.generateKey().getEncoded());
//		System.out.println(mac);
//		String macKey=DesUtil.desEncrypt(mac, "1111111111111111");
//		String macCheck=DesUtil.desEncrypt("0000000000000000",mac);
//		System.out.println("mac:"+mac+",macKey:"+macKey+",macCheck:"+macCheck);
		
		CmbcISO8583ForBank ccbRequest = new CmbcISO8583ForBank();
		
//		ccbRequest.putItem(0, Constant.TRANS_TYPE_PURCHASE); 				// 交易类型
//
//		ccbRequest.putItem(2, "6226186755888999"); 						// 主账号
//		ccbRequest.putItem(3, Constant.PROC_PURCHASE_VOID); 						// 处理码
//		ccbRequest.putItem(4, "000000000002"); 					// 交易金额
//		ccbRequest.putItem(11, "000004"); 	// 系统跟踪号
//		ccbRequest.putItem(14, "2112");
//		ccbRequest.putItem(22, "051"); 									// 输入方式,刷卡有密码【021】
//		ccbRequest.putItem(23, "000");
//		ccbRequest.putItem(25, "00");										// 服务代码00
//		ccbRequest.putItem(26, "12");
//		ccbRequest.putItem(35, "6226186755888999=21122200097565100000"); 					// 2磁道
//		ccbRequest.putItem(37, "100000021902"); 					// 2磁道
//		ccbRequest.putItem(41, "10000104");	 						// 银行终端号
//		ccbRequest.putItem(42, "305220050650002");									// 银行商户号
//		ccbRequest.putItem(49, Constant.CURRCY_CODE_TRANS); 										// 币种 ,人民币【156】
//		ccbRequest.putItem(52, "353BF35F6420393D");
//		ccbRequest.putItem(53, "2600000000000000");
//
//		ccbRequest.putItem(55, "9F26083CC5BDFB9D1B9ED59F2701809F101307010103A02002010A0100000000006A54E0B69F37045F84A1F09F3602003E9505008004E0009A031709059C01009F02060000000000025F2A02015682027C009F1A0201569F03060000000000009F3303E0F9C89F34030203009F3501229F1E0837353532303534378408A0000003330101019F090200309F410400000031");
//		ccbRequest.putItem(601, "23");
//		ccbRequest.putItem(602, "000001");//签到返回批次号
//		ccbRequest.putItem(603, "000");
//		ccbRequest.putItem(604, "5");
//		ccbRequest.putItem(605, "0");
//		ccbRequest.putItem(611, "000001");
//		ccbRequest.putItem(612, "000003");
		//ccbRequest.putItem(604, "5");

		ccbRequest.putItem(0,"0700");
		ccbRequest.putItem(11,"000123");
		ccbRequest.putItem(41,"10121640");

		TLVObject[] TLV51Array = new TLVObject[3];
		TLV51Array[0] = new TLVObject("A1",
				"000015".getBytes());
		TLV51Array[1] = new TLVObject("A2",
				"04".getBytes());
		TLV51Array[2] = new TLVObject("A3",
				"75520547".getBytes());
		byte[] byteData = new BerTlvEncoder().encode(TLV51Array);
		String field51 = ISO8583Utile.bytesToHexString(byteData);
		ccbRequest.putItem(42,"333333333333333");
		ccbRequest.putItem(51,field51);
		ccbRequest.putItem(601,"01");
		ccbRequest.putItem(602,"000000");
		ccbRequest.putItem(603,"003");

		byte[] test = ccbRequest.Encoding();
		NCCBTransUtilForBank nccbTransUtil = new NCCBTransUtilForBank();
		nccbTransUtil.send2Bank(ccbRequest);
//		Socket socket = null;
//		PrintWriter out = null;
//		InputStream ins = null;
//		try {
//			//tring a = "029B600004000060220000000009005020000008C00215166222530914767996000000001500000028323433363531333839363537313031323136343038393334343033353439393031353100749F2608AF325C172167184D9505080004E0004F10413030303030303333333031303130325F340200019B02F8009F3602000882027C009F37047505CFE8500B50424F4320435245444954000807000001050100000100000001400000008A000000030800031CFF02F5359E06B8B340414A7D5653853690FF025F006722D83028A9BEE1E421C8C84BFEF3F8FF020793D724ABCBF7FF00679B022680FF0252FA724257A53316FA8A682F04FF02246EABA45150D0092A2E4220FF022CA5CA4B854C3D67360880FF02868DE898E6B6CDCF38FF02805BFC82D5D040FF029103032950C2FF0259C9C0AEB2C3E5FF0239660FE243E94480FF02043F032F4CDFB8FF024270C36216D30580FF022E4E09368C3B051236413160FF02A71C1D4D5FE2727AE2D9FE090BFF02CC7DA9397D9D80FF023D48941DCFEFFEA0FF0204C820D8021832B1FF02702196B93E335B6F1CE7EB23CACA034B8762C8D08090FF021E1C005E9328E3274408789ABD01F4688B87A4105E0BF3D4701877672271FF023A3E19B269A520235F932E51CBD853EDFF025E595B65EB937F299977FE09CE1E9DAA6A3F15D7F836FF024210568C8BB9E2212589F5BF138C45D906071C73C0D48FD57EEEB0FF0248B9AA2F94810B601DFDA1D620FF02A60FDE24FE17E54D4BF71260FF020C1D2028D3BE0366DE4460FF029C80F099D7A3305CD6A8869C90FF0277E7E37249DFE56A24FF0276347A6A4880FF02E938ACC1143CFF024D781CDE53148FC0FF02945605AC4C1180FF023F43C0FF02FCFF02FF02FF02FF02FF02FF02FF02FF02FF02FF02FF02FF024534414533304546";
//			//String a = "00C7600004000060220000000008000020000000C0209200002931303132313634303839333434303335343939303135310028D2083735353230353437DF130F38363839383630323135393232303000423030563230313530383037303120202020202020202056323031343036313030312020202020202020200011000000010010006830312043323839333334323232347C323836393731313334327C323839363635353439357C32383933333131393233203839383630324234303731373730353732343234";
//			//byte[] request = ISO8583Utile.hexStringToByte(a);
//			//socket = new Socket("192.168.0.35",28001);
//			byte[] request = test;
//			
//			socket = new Socket("111.205.207.17",6018);
//			OutputStream  os = socket.getOutputStream();
//			os.write(request);
//			os.flush();
//			ins = socket.getInputStream();
//			byte[] _resultByte = new byte[2048];
//			int len = ins.read(_resultByte);
//			System.out.println("+++++len"+len);
//			byte[] resultByte = new byte[len];
//			System.arraycopy(_resultByte, 0, resultByte, 0, len);
//			System.out.println(new String(resultByte));
//			Thread.sleep(10000);
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}  finally {
//			try {
//				if(null != out){
//					out.close();
//				}
//				if(null != ins){
//					ins.close();
//				}
//				if(null != socket){
//					socket.close();
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
		
	}
}
