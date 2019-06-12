package com.yl.pay.test;

import com.pay.common.util.DesUtil;
import com.pay.common.util.ISO8583.ISO8583Utile;
import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.bean.UnionPayBean;

import com.yl.pay.pos.interfaces.P500001.Constant;

import javax.crypto.KeyGenerator;
import java.security.SecureRandom;

public class Test {
	
	public static void main(String[] args) throws Exception {

//		KeyGenerator kg=KeyGenerator.getInstance("DES");
//		kg.init(56);
//		String mac=ISO8583Utile.bytesToHexString(kg.generateKey().getEncoded());
//		System.out.println(mac);
//		String macKey=DesUtil.desEncrypt(mac, "1111111111111111");
//		String macCheck=DesUtil.desEncrypt("0000000000000000",mac);
//		System.out.println("mac:"+mac+",macKey:"+macKey+",macCheck:"+macCheck);

//		CmbcISO8583 ccbRequest = new CmbcISO8583();
//
//		ccbRequest.putItem(0, Constant.TRANS_TYPE_PURCHASE); 				// 交易类型
//
//		ccbRequest.putItem(2, "6226186755888999"); 						// 主账号
//		ccbRequest.putItem(3, Constant.PROC_PURCHASE_VOID); 						// 处理码
//		ccbRequest.putItem(4, "000000000008"); 					// 交易金额
//		ccbRequest.putItem(11, "000041"); 	// 系统跟踪号
//		ccbRequest.putItem(14, "2112");
//		ccbRequest.putItem(22, "021"); 									// 输入方式,刷卡有密码【021】
//		//ccbRequest.putItem(23, "000");
//		ccbRequest.putItem(25, "00");										// 服务代码00
//		ccbRequest.putItem(26, "12");
//		ccbRequest.putItem(35, "6222300499932815=20051010090966399999"); 					// 2磁道
//		ccbRequest.putItem(37, "233651389787"); 					// 2磁道
//		ccbRequest.putItem(41, "10121640");	 						// 银行终端号
//		ccbRequest.putItem(42, "893440354990151");									// 银行商户号
//		ccbRequest.putItem(49, Constant.CURRCY_CODE_TRANS); 										// 币种 ,人民币【156】
//		ccbRequest.putItem(52, "68C5C2EDF8C1CE45");
//		ccbRequest.putItem(53, "2000000000000000");
//
//		//ccbRequest.putItem(55, "9F26083B135503A351A6BA9F2701809F101307010103A02002010A010000000000FE8643EA9F3704CDD0A5AC9F360200419505008004E0009A031709069C01009F02060000000000075F2A02015682027C009F1A0201569F03060000000000009F3303E0F9C89F34030203009F3501229F1E0837353532303534378408A0000003330101019F090200309F410400000038");
//		ccbRequest.putItem(601, "23");
//		ccbRequest.putItem(602, "000001");//签到返回批次号
//		ccbRequest.putItem(603, "000");
//		ccbRequest.putItem(604, "5");
//		ccbRequest.putItem(605, "0");
//		ccbRequest.putItem(611, "000001");
//		ccbRequest.putItem(612, "000040");
//		//ccbRequest.putItem(604, "5");
//		byte[] test = ccbRequest.Encoding();
//		NCCBTransUtil nccbTransUtil = new NCCBTransUtil();
//		nccbTransUtil.send2Bank(ccbRequest);
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
//		}kgen
//		KeyGenerator kgen = KeyGenerator.getInstance("AES");    //实例化一个用AES加密算法的密钥生成器
//		kgen.init(128);
//		String mac = ISO8583Utile.bytesToHexString(kgen.generateKey().getEncoded());
//		System.out.println(mac);

//		SignIn signIn = new SignIn();
//		ExtendPayBean extendPayBean = new ExtendPayBean();
//		UnionPayBean unionPayBean = new UnionPayBean();
//		unionPayBean.setSystemsTraceAuditNumber("000006");
//		unionPayBean.setCardAcceptorTerminalId("10000119"); 		// 银行终端号
//		unionPayBean.setCardAcceptorId("110345142152291"); 				// 银行商户号
//		extendPayBean.setUnionPayBean(unionPayBean);
//		NCCBTransUtil yyfTransUtil = new NCCBTransUtil();
//		signIn.setYyfTransUtil(yyfTransUtil);
//		signIn.execute(extendPayBean);

//		String xfor = ISO8583Utile.bytesToHexString(ISO8583Utile.getXorbyte(ISO8583Utile.getHAccno("6225880160777257"), ISO8583Utile.getHPin("548980")));
//
//		System.out.println(xfor);
		System.out.println(ISO8583Utile.bytesToHexString(getHString("00100011234567800000001001010000")));
		System.out.println(ISO8583Utile.bytesToHexString(getHString("11111111111111111111111111111111")));

		String xfor = ISO8583Utile.bytesToHexString(getXorbyte(getHString("11110411151103254769810325476981"), getHString("00001500040020160517140000000000")));
		System.out.println(xfor);
	}

	public static byte[] getHString(String accno) {
		byte[] arrAccno = accno.getBytes();

		byte[] encode = new byte[]{ISO8583Utile.uniteBytes(arrAccno[0], arrAccno[1]), ISO8583Utile.uniteBytes(arrAccno[2], arrAccno[3]),
				ISO8583Utile.uniteBytes(arrAccno[4], arrAccno[5]), ISO8583Utile.uniteBytes(arrAccno[6], arrAccno[7]),
				ISO8583Utile.uniteBytes(arrAccno[8], arrAccno[9]), ISO8583Utile.uniteBytes(arrAccno[10], arrAccno[11]),
				ISO8583Utile.uniteBytes(arrAccno[12], arrAccno[13]), ISO8583Utile.uniteBytes(arrAccno[14], arrAccno[15]),
				ISO8583Utile.uniteBytes(arrAccno[16], arrAccno[17]), ISO8583Utile.uniteBytes(arrAccno[18], arrAccno[19]),
				ISO8583Utile.uniteBytes(arrAccno[20], arrAccno[21]), ISO8583Utile.uniteBytes(arrAccno[22], arrAccno[23]),
				ISO8583Utile.uniteBytes(arrAccno[24], arrAccno[25]), ISO8583Utile.uniteBytes(arrAccno[26], arrAccno[27]),
				ISO8583Utile.uniteBytes(arrAccno[28], arrAccno[29]), ISO8583Utile.uniteBytes(arrAccno[30], arrAccno[31])};
		return encode;
	}

	public static byte[] getXorbyte(byte[] xorCycleA, byte[] xorCycleB) {
		byte[] resultCycle = new byte[16];

		for(int i = 0; i < xorCycleA.length; ++i) {
			resultCycle[i] = (byte)(xorCycleA[i] ^ xorCycleB[i]);
		}

		return resultCycle;
	}
}
