package com.yl.pay.test;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.BitSet;
import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.common.util.StringUtil;
import com.pay.common.util.ISO8583.DataType;
import com.pay.common.util.ISO8583.EncodeType;
import com.pay.common.util.ISO8583.Field8583;
import com.pay.common.util.ISO8583.FieldId;
import com.pay.common.util.ISO8583.ISO8583Utile;
import com.pay.common.util.ISO8583.LengthType;
import com.yl.pay.pos.bean.UnionPayBean;
import com.yl.pay.pos.interfaces.P500001.CmbcParamMapping;
import com.yl.pay.pos.interfaces.P500001.CmbcTransException;
import com.yl.pay.pos.interfaces.P500001.Constant;

 

@SuppressWarnings("unchecked")
public class CmbcISO8583ForBank {
	private static final Log log = LogFactory.getLog(CmbcISO8583ForBank.class);
	
	public static Hashtable<FieldId,Field8583> domainDefPool = null;		// 存放IOS8583域定义对象
	public Hashtable<FieldId,Field8583> domainInsPool = null;				// 存放ISO8583各域实例对象
	
	public final static int FIELD_ID_START = 0;								// 用于标识报文的域开始最小数	
	public final static int FIELD_ID_END = 64;								// 用于标识报文的域结束最大数	
	
	public final static int LEN_CCB_LEN = 2;								// 报文长度的长度
	
	public final static int LEN_CCB_TPUD = 5;								// TPUD的长度	
	public final static String CONTENT_CCB_TPUD = "6000030000";				// 生产	
	
	public final static int LEN_CCB_MESSAGE = 6;							// 报文头长度
	public final static String CONTENT_CCB_MESSAGE = "603100311004";	
	
	public final static int LEN_CCB_MESSAGETYPE = 2;						// 信息类型的长度	
	
	public final static int LEN_BITMAP_BASE = 8;							// 主位图的长度	
	public final static int LEN_BITMAP_EXTENDED = 8;						// 扩展位图的长度	

	static {
		domainDefPool = new Hashtable();
		
		/** define iso8583 domain , put in pool */
		
		domainDefPool.put(new FieldId(0 ), new Field8583(0 , 4 , LengthType.APPOINT, DataType.N, EncodeType.BCD));		// 信息类型
		domainDefPool.put(new FieldId(1 ), new Field8583(1 , 8 , LengthType.APPOINT, DataType.B, EncodeType.BINARY));	// 位图
		domainDefPool.put(new FieldId(2 ), new Field8583(2 , 19, LengthType.LLVAR,   DataType.N, EncodeType.BCD));		// 主账号
		domainDefPool.put(new FieldId(3 ), new Field8583(3 , 6 , LengthType.APPOINT, DataType.N, EncodeType.BCD));		// 处理码
		domainDefPool.put(new FieldId(4 ), new Field8583(4 , 12, LengthType.APPOINT, DataType.N, EncodeType.BCD));		// 交易金额
		domainDefPool.put(new FieldId(11), new Field8583(11, 6 , LengthType.APPOINT, DataType.N, EncodeType.BCD));		// 受卡方系统跟踪号

		domainDefPool.put(new FieldId(12), new Field8583(12, 6 , LengthType.APPOINT, DataType.N, EncodeType.BCD));		// 受卡方所在地时间
		domainDefPool.put(new FieldId(13), new Field8583(13, 4 , LengthType.APPOINT, DataType.N, EncodeType.BCD));		// 受卡方所在地日期
		domainDefPool.put(new FieldId(14), new Field8583(14, 4 , LengthType.APPOINT, DataType.N, EncodeType.BCD));		// 卡有效期
		domainDefPool.put(new FieldId(15), new Field8583(15, 4 , LengthType.APPOINT, DataType.N, EncodeType.BCD));		// 清算日期
		
		domainDefPool.put(new FieldId(18), new Field8583(18, 4 , LengthType.APPOINT, DataType.N, EncodeType.BCD));		// 商户类型

		domainDefPool.put(new FieldId(22), new Field8583(22, 3 , LengthType.APPOINT, DataType.N, EncodeType.BCD));		// 服务点输入方式码
		domainDefPool.put(new FieldId(23), new Field8583(23, 4 , LengthType.APPOINT, DataType.N, EncodeType.BCD));		// CVV2
		domainDefPool.put(new FieldId(25), new Field8583(25, 2 , LengthType.APPOINT, DataType.N, EncodeType.BCD));		// 服务点条件码
		domainDefPool.put(new FieldId(26), new Field8583(26, 2 , LengthType.APPOINT, DataType.N, EncodeType.BCD));		//服务点PIN获取码
		
		domainDefPool.put(new FieldId(31), new Field8583(31, 10 , LengthType.APPOINT, DataType.S, EncodeType.BCD));	
		domainDefPool.put(new FieldId(32), new Field8583(32, 11 , LengthType.LLVAR, DataType.N, EncodeType.BCD));		//服务点PIN获取码
		domainDefPool.put(new FieldId(35), new Field8583(35, 37 , LengthType.LLVAR,  DataType.S, EncodeType.BCD));		// 第二磁道数据
		domainDefPool.put(new FieldId(36), new Field8583(36, 104, LengthType.LLLVAR, DataType.S, EncodeType.BCD));		// 第三磁道数据
		domainDefPool.put(new FieldId(37), new Field8583(37, 12 , LengthType.APPOINT,DataType.S, EncodeType.ASCII));	// 检索参考号
		domainDefPool.put(new FieldId(38), new Field8583(38, 6  , LengthType.APPOINT,DataType.S, EncodeType.ASCII));	// 授权标识应答码
		domainDefPool.put(new FieldId(39), new Field8583(39, 2  , LengthType.APPOINT,DataType.S, EncodeType.ASCII));	// 应答码

		domainDefPool.put(new FieldId(41), new Field8583(41, 8 ,  LengthType.APPOINT,DataType.S, EncodeType.ASCII));	// 终端编号
		domainDefPool.put(new FieldId(42), new Field8583(42, 15,  LengthType.APPOINT,DataType.S, EncodeType.ASCII));	// 商户号
		domainDefPool.put(new FieldId(43), new Field8583(43, 40,  LengthType.APPOINT, DataType.S, EncodeType.ASCII));	// 受卡方的名称地址，即商户的名称和所在地
		domainDefPool.put(new FieldId(44), new Field8583(44, 99,  LengthType.LLVAR,  DataType.S, EncodeType.ASCII));	// 附加响应数据
		domainDefPool.put(new FieldId(47), new Field8583(47, 999,  LengthType.LLLVAR,  DataType.S, EncodeType.ASCII));	// 附加响应数据
		domainDefPool.put(new FieldId(48), new Field8583(48, 2 ,  LengthType.LLLVAR, DataType.S, EncodeType.ASCII));	// 附加数据——私有
		domainDefPool.put(new FieldId(49), new Field8583(49, 3 ,  LengthType.APPOINT,DataType.S, EncodeType.ASCII));	// *交易货币代码
		domainDefPool.put(new FieldId(51), new Field8583(51,500, LengthType.LLLVAR, DataType.S,EncodeType.ASCII));	//个人标识码数据
		domainDefPool.put(new FieldId(52), new Field8583(52, 16,  LengthType.APPOINT,DataType.B, EncodeType.BINARY));	// 个人标识码数据
		domainDefPool.put(new FieldId(53), new Field8583(53, 16,  LengthType.APPOINT,DataType.N, EncodeType.BCD));		// 安全控制信息
		domainDefPool.put(new FieldId(54), new Field8583(54, 40,  LengthType.LLLVAR, DataType.S, EncodeType.ASCII));	// 附加金额
		domainDefPool.put(new FieldId(55), new Field8583(55, 999, LengthType.LLLVAR, DataType.S, EncodeType.ASCII));
		domainDefPool.put(new FieldId(56), new Field8583(56, 999, LengthType.LLLVAR, DataType.S, EncodeType.ASCII));
		
		domainDefPool.put(new FieldId(57), new Field8583(57,999,LengthType.LLLVAR, DataType.S,EncodeType.ASCII));		//自定义域
		domainDefPool.put(new FieldId(571),new Field8583(571,8, LengthType.APPOINT,DataType.S,EncodeType.ASCII));		//更新标志(下载主密钥为厂商编号)
		domainDefPool.put(new FieldId(572),new Field8583(572,20,LengthType.APPOINT, DataType.S,EncodeType.ASCII));	//软件版本号(下载主密钥为机型编号)
		domainDefPool.put(new FieldId(573),new Field8583(573,20,LengthType.APPOINT, DataType.S,EncodeType.ASCII));	//参数版本号(下载主密钥为SN)

		domainDefPool.put(new FieldId(59), new Field8583(59,999,LengthType.LLLVAR, DataType.S,EncodeType.ASCII));
		domainDefPool.put(new FieldId(60), new Field8583(60,  14, LengthType.LLLVAR, DataType.N, EncodeType.BCD));		// 自定义域
		domainDefPool.put(new FieldId(601),new Field8583(601, 2 , LengthType.APPOINT,DataType.N, EncodeType.BCD));		// 交易类型码
		domainDefPool.put(new FieldId(602),new Field8583(602, 6 , LengthType.APPOINT,DataType.N, EncodeType.BCD));		// 批次号
		domainDefPool.put(new FieldId(603),new Field8583(603, 3 , LengthType.APPOINT,DataType.N, EncodeType.BCD));		// 网络管理码
		domainDefPool.put(new FieldId(604),new Field8583(604, 1 , LengthType.APPOINT,DataType.N, EncodeType.BCD));		// 网络管理码
		domainDefPool.put(new FieldId(605),new Field8583(605, 1 , LengthType.APPOINT,DataType.N, EncodeType.BCD));		// 网络管理码
		domainDefPool.put(new FieldId(606),new Field8583(606, 1 , LengthType.APPOINT,DataType.N, EncodeType.BCD));		// 网络管理码

		domainDefPool.put(new FieldId(61), new Field8583(61,  29, LengthType.LLLVAR, DataType.N, EncodeType.BCD));	// 分期付款消费标志
		domainDefPool.put(new FieldId(611),new Field8583(611, 6 , LengthType.APPOINT,DataType.N, EncodeType.BCD));		// 交易类型码
		domainDefPool.put(new FieldId(612),new Field8583(612, 6 , LengthType.APPOINT,DataType.N, EncodeType.BCD));		// 批次号
		domainDefPool.put(new FieldId(613), new Field8583(613,4, LengthType.APPOINT, DataType.N, EncodeType.BCD));	// 分期付款返回
		domainDefPool.put(new FieldId(62), new Field8583(62,96, LengthType.LLLVAR, DataType.S, EncodeType.ASCII));	// 分期付款返回

		domainDefPool.put(new FieldId(63), new Field8583(63, 999, LengthType.LLLVAR,  DataType.S, EncodeType.ASCII));		// 参数数据
		domainDefPool.put(new FieldId(631), new Field8583(631, 3, LengthType.APPOINT,  DataType.S, EncodeType.ASCII));		// 参数数据
		domainDefPool.put(new FieldId(64), new Field8583(64, 8,  LengthType.APPOINT, DataType.B, EncodeType.ASCII));	// *报文鉴别码
	};

	public CmbcISO8583ForBank() {
		domainInsPool = new Hashtable();
	}

	private Field8583 getItemDef(int d) throws ArrayIndexOutOfBoundsException {
		Field8583 itm = (Field8583) domainDefPool.get(new FieldId(d));
		if (itm == null) {
			throw new ArrayIndexOutOfBoundsException(
					"8583 field not define, invalid 8583 field id=" + d);
		}
		return itm;
	}

	/**
	 * put a 8583 item data to hash table
	 * 
	 * @param d
	 * @param data
	 * @throws java.lang.ArrayIndexOutOfBoundsException
	 */
	public void putItem(int d, String data) throws CmbcTransException {
		// >=600为扩展域,不判断 
		if (d<570) {
			if (d < FIELD_ID_START || d > FIELD_ID_END)
				throw new CmbcTransException(
						CmbcTransException.ERROR_DATA_LENGTH);
		}
		//获取8583第${d}域定义
		Field8583 itmdef = getItemDef(d);
		if (itmdef == null)
			throw new CmbcTransException(
					CmbcTransException.FIELD_NOT_DEFINED_IN_8583);

		// 检查数据的长度是否合法
		if (data.length() > itmdef.getLength())
			throw new CmbcTransException(
					CmbcTransException.ERROR_DATA_TYPE);
		
		// 检查数据是否为有效的数字串
		if (itmdef.getDataType() == DataType.N) { 
			if (!ISO8583Utile.isValidDecString(data))
				throw new CmbcTransException(
						CmbcTransException.ERROR_DATA_TYPE);
		}

		//生成8583域定义实例对象
		Field8583 field8583=new Field8583(d,itmdef.getLength(), itmdef.getLengthType(),  itmdef.getDataType(),itmdef.getEncodeType());
		field8583.setValue(data);
		
		domainInsPool.put(new FieldId(d), field8583);
	}

	public Field8583 getItem(int fld) throws ArrayIndexOutOfBoundsException {
		if (fld < 570) {// >=600为扩展域,不判断
			if (fld < FIELD_ID_START || fld > FIELD_ID_END)
				throw new ArrayIndexOutOfBoundsException(
						"invalid 8583 field id=" + fld);
		}
		Field8583 itm = (Field8583) domainInsPool.get(new FieldId(fld));

 		return itm;
	}

	public Field8583 putValue(int i) throws CmbcTransException {// 对于有子域的域，把子域的值拼接好后放入到对应的域中
		Field8583 itm;
		Field8583 itmdef = getItemDef(i);
		StringBuffer str = new StringBuffer();
		boolean key = false;
		for (int j = i * 10 + 1; j <= i * 10 + 5; j++) {
			itm = getItem(j);
			if (itm != null) {
				key = true;
				str.append(itm.getValue());
			}
		}
		if (!key) {
			return null;
		}
		Field8583 field8583=new Field8583(i,itmdef.getLength(), itmdef.getLengthType(),  itmdef.getDataType(),itmdef.getEncodeType());
		field8583.setValue(str.toString());
		domainInsPool.put(new FieldId(i), field8583);
		return getItem(i);
	}

	/**
	 * 把定义的8583字段转换成8583表示的字符串
	 * @throws Exception
	 */
	public byte[] Encoding() throws  Exception {
		log.info("field[head]: [data,maxLength,length,dataType,encodeType,lengthType,hexString]");	
		
		boolean flag128 = false;							// 位图128域开关
		int bitmapLen = 8;									// 默认使用64域,8*8位
		byte[] bitmap = new byte[16];						// 位图
		
		byte[] bodyBuf = new byte[Field8583.FLD_MAX_SIZE];  // 从第2域开始的报文体数据
		int bodyLen = 0; 									// 从第2域开始的报文体长度
	
		Field8583 itm;
		byte[] itembytes ;
		
		for (int i = 2; i <= FIELD_ID_END; i++) {			// 第一域为位图,从第二域开始循环,每处理一个域,变更该域对应位图标识
			itm = getItem(i);
			if (i == 60||i==63||i==61) {									// 特殊的域单独做处理
				itm = putValue(i);
			}
			if (itm != null) { 
				if (flag128 == false && i > 64) { 			// 如果超过64个域,则扩展位图长度为16
					flag128 = true;
					bitmapLen = 16;
					bitmap[0] = (byte) (bitmap[0] | 0x80);
				}
				bitmap[(i - 1) / 8] = (byte) (bitmap[(i - 1) / 8] | (0x80 >>> ((i - 1) % 8))); //处理位图标志位
				if (i == 52) {
					byte[] s = new byte[8];
					s = ISO8583Utile.hexStringToByte(itm.getValue());
					System.arraycopy(s, 0, bodyBuf, bodyLen, 8);
					bodyLen += 8;
				} if (i == 62 || i == 55 || i == 51) {
					// 62域全部进行压缩
					byte[] lenbyte = new byte[2];
					byte[] keybyte = new byte[itm.getValue().length() / 2];
					byte[] bytevalue = new byte[itm.getValue().length() / 2 + 2];

					String keylen = null;

					keybyte = ISO8583Utile.hexStringToByte(itm.getValue());
					keylen = ISO8583Utile.int2char3(keybyte.length);
					lenbyte = ISO8583Utile.string2Bcd(keylen);// 域长度BCD码
					System.arraycopy(lenbyte, 0, bytevalue, 0, 2);
					System.arraycopy(keybyte, 0, bytevalue, 2, keybyte.length);
					itembytes = bytevalue;
					System.arraycopy(itembytes, 0, bodyBuf, bodyLen, itembytes.length);
					bodyLen += itembytes.length;
				} else {
					itembytes = EncodingItem(itm);					
					System.arraycopy(itembytes, 0, bodyBuf, bodyLen, itembytes.length);
					bodyLen += itembytes.length;
				}
			}
		}
		String messagetype = getItem(0).getValue();// 消息类型
		log.info("message type ="+messagetype);
		int maclen = 0;
		int messagelen = messagetype.length();
		
		// 根据信息类型确认是否产生MAC
		if (!Constant.NOMACTYPE_SIGNIN.equals(messagetype)
				&& !Constant.NOMACTYPE_SIGNOFF.equals(messagetype) && !Constant.NOMACTYPE_DOWNLOAD_MAIN_KEY.equals(messagetype)) {
			int domainMac = 64;   //64【mac域】
			bitmap[(domainMac - 1) / 8] = (byte) (bitmap[(domainMac - 1) / 8] | (0x80 >>> ((domainMac - 1) % 8)));	// 变更mac的位图信息
			
			byte[] macdata = new byte[ messagelen / 2 + bitmapLen + bodyLen ];
			System.arraycopy(ISO8583Utile.string2Bcd(messagetype), 0 , macdata , maclen, messagelen / 2);			// copy消息类型
			maclen += messagelen / 2;
			System.arraycopy(bitmap, 0, macdata, maclen, bitmapLen);												// copy位图信息
			maclen += bitmapLen;
			System.arraycopy(bodyBuf, 0, macdata, maclen, bodyLen);													// copy报文体
			maclen += bodyLen;
			
			String makeMacString=ISO8583Utile.getXorString(macdata);
			
//			String before8=ISO8583Utile.toHexString(makeMacString.substring(0,8));
//			String posMac=DesUtil.desEncrypt(before8,"C45DA4CD1007CEBC");
//			String after8=ISO8583Utile.toHexString(makeMacString.substring(8,16));
//			byte [] xfo=ISO8583Utile.string2Bcd(posMac+after8);
//			String xforesult=ISO8583Utile.getXorString(xfo);
//			String macResult = DesUtil.desEncrypt(xforesult,"C45DA4CD1007CEBC").substring(0, 8);
//			String macResult =EsscUtils.createBankMac("bank."+Constant.INTERFACE_CODE+"_"+getItem(41).getValue()+".zak", makeMacString);
			String macResult =EsscUtilsForBank.createPosMac("bank."+Constant.INTERFACE_CODE+"_"+getItem(41).getValue()+".zak", makeMacString);
			System.arraycopy(macResult.substring(0,8).getBytes(), 0, bodyBuf, bodyLen, 8);
			bodyLen += 8;
		}
		
		int messageLen =  LEN_CCB_TPUD + LEN_CCB_MESSAGE + ( messagelen / 2 + bitmapLen + bodyLen );    // 报文体【 TPDU + 报文头 + iso8583】
		
		/**
		 * 建行报文长度计算方式
		 * */
		// 转换成8583内容格式
		byte[] headLen = new byte[2];
		String hexStr = Integer.toHexString(messageLen);
		String lenString = ISO8583Utile.StringFillLeftZero(hexStr, 4);
		headLen = ISO8583Utile.string2Bcd(lenString);
		byte[] data = new byte[LEN_CCB_LEN + messageLen];												// 消息报文data,长度为【2位数据长度 + 报文体】
		int step = 0; 																					// 2位长度头
		System.arraycopy(headLen, 0, data, step, LEN_CCB_LEN);	
		step += LEN_CCB_LEN;
		System.arraycopy(ISO8583Utile.string2Bcd(CONTENT_CCB_TPUD), 0, data, step,	LEN_CCB_TPUD);		// TPDU
		step += LEN_CCB_TPUD;
		System.arraycopy(ISO8583Utile.string2Bcd(CONTENT_CCB_MESSAGE), 0, data,	step, LEN_CCB_MESSAGE);	// 报文头
		step += LEN_CCB_MESSAGE;
		System.arraycopy(ISO8583Utile.string2Bcd(messagetype), 0, data, step,	messagelen / 2);  		// 消息类型【0域】
		step += messagelen / 2;
		System.arraycopy(bitmap, 0, data, step, bitmapLen); 											// 位图【1域】
		step += bitmapLen;
		System.arraycopy(bodyBuf, 0, data, step, bodyLen);
		step += bodyLen;
		log.info("len=[" + messageLen + "] data=[" + ISO8583Utile.bytesToHexString(data) + "]");

		return data;
	}
	
	/**
	 * Encode an item according to coding rule
	 * @param ins
	 * @return: The item's encoded string length
	 */
	private byte[] EncodingItem (Field8583 ins)  {
		
		byte[] data = new byte[Field8583.FLD_MAX_SIZE]; //按最大可用长度创建报文数组
		int dataLen = 0; 								//报文数组中数据长度

		int maxLen = ins.getLength();					// 域实例可用最大长度
		String insValue = ins.getValue();				// 域实例.value
		int realLen = 0;								// 域实例.value的实际长度
		if(ins.getId()==55){
			 realLen=ISO8583Utile.hexStringToByte(insValue).length;
	     }else{
	    	 realLen = insValue.length();//域中内容的实际长度
	     }
		byte[] bcdLen = null;
		byte[] contenbyte = null;
		String realLenStr = null;
		if (LengthType.LLVAR.name().equals(ins.getLengthType().name())) { 			// LLVAR.双字节变长
			realLenStr = ISO8583Utile.int2char2(realLen); 
			bcdLen = ISO8583Utile.string2Bcd(realLenStr); 
			
			if (EncodeType.BCD.equals(ins.getEncodeType())) {						// BCD编码方式[左靠],value.len为奇数位,右补1位0
				if (realLen % 2 != 0) {
					insValue = ISO8583Utile.StringFillRightBlank(insValue, realLen + 1);
				}
				contenbyte = ISO8583Utile.string2Bcd(insValue);// 域值
			} else {																// 字符型,直接转化byte[],如有异常可使用getBytes("gb2312")调试
				contenbyte = insValue.getBytes();
			}
			
			System.arraycopy(bcdLen, 0, data, dataLen, 1);
			dataLen += 1;
			System.arraycopy(contenbyte, 0, data, dataLen, contenbyte.length);
			dataLen += contenbyte.length;

		} else if (LengthType.LLLVAR.name().equals(ins.getLengthType().name())) { 	// LLLVAR.三字节变长
			realLenStr = ISO8583Utile.int2char3(realLen);
			bcdLen = ISO8583Utile.string2Bcd(realLenStr); 
			
			if (EncodeType.BCD.equals(ins.getEncodeType())) {						// BCD编码方式[左靠],value.len为奇数位,右补1位0
				if (realLen % 2 != 0) {
					insValue = ISO8583Utile.StringFillRightBlank(insValue,
							realLen + 1);
				}
				contenbyte = ISO8583Utile.string2Bcd(insValue); 
			} else { 																// 字符型,直接转化byte[],如有异常可使用getBytes("gb2312")调试
				if(ins.getId()==55){
					contenbyte=ISO8583Utile.hexStringToByte(insValue);
				}else {
					contenbyte = insValue.getBytes();
				}
			}
			System.arraycopy(bcdLen, 0, data, dataLen, 2);
			dataLen += 2;
			System.arraycopy(contenbyte, 0, data, dataLen, contenbyte.length);
			dataLen += contenbyte.length;

		} else if (LengthType.APPOINT.name().equals(ins.getLengthType().name())) { 
			String filledValue = "";  														//填充后的ins.value
			if (DataType.N.name().equals(ins.getDataType().name())) { 
																		
				if (maxLen % 2 != 0) {
//					if(ins.getId()==22){
//						filledValue = ISO8583Utile.StringFillLeftZero(insValue, maxLen + 1);
//					}else{
						filledValue = ISO8583Utile.StringFillRightBlank(insValue, maxLen + 1);  //ins.value,奇位数,[左靠,右补0]
//					}
					} else {
					filledValue = ISO8583Utile.StringFillLeftZero(insValue, maxLen);		//ins.value,偶位数,[左补0,凑位数]
				}

				if (EncodeType.BCD.equals(ins.getEncodeType())) { 
					data = ISO8583Utile.string2Bcd(filledValue);
					dataLen += (maxLen + 1) / 2;
				}
			} else { // data type S,B(binary在PUT之前就要转换成8个字节的数组)
				if(ins.getId()==43){
					Charset defaultCharset = Charset.defaultCharset();
					byte[] rawBytes = ins.getValue().getBytes(defaultCharset);
					Charset targetCharset = Charset.forName("GBK");
					ByteBuffer inputBuffer = ByteBuffer.wrap(rawBytes);
					CharBuffer dataCharBuffer = defaultCharset.decode(inputBuffer);
					ByteBuffer outBuffer = targetCharset.encode(dataCharBuffer);

					byte[] encodeBtyes = outBuffer.array();
					for (int i = 0; i < maxLen; i++) {
						if (i < encodeBtyes.length && encodeBtyes[i] != 0) {
							data[i] = encodeBtyes[i];
						} else {
							data[i] = (byte) ' ';
						}
					}
					filledValue = new String(data, targetCharset);
					try {
						data = filledValue.getBytes("GBK");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}else{	
					filledValue = ISO8583Utile.StringFillRightBlank(insValue, maxLen);
					data = filledValue.getBytes();
				}
				dataLen += maxLen;
			}
		} else {
			System.err.println(" 8583 instance " + ins.getId()
					+ " length type invalid! data=" + ins.getValue()
					+ ", lengthtype=" + ins.getLengthType().name());
		}

		byte[] returnbyte = new byte[dataLen];					//按实际报文长度创建返回数组,去掉冗余部分
		System.arraycopy(data, 0, returnbyte, 0, dataLen);
				
		
		log.info("field[" + ins.getId() + "]: ["+ 
				ins.getValue() + "," + 
				ins.getLength() + "," +
				dataLen + "," +
				ins.getDataType().name() + "," + 
				ins.getEncodeType()+"," + 
				ins.getLengthType() + "," + 
				ISO8583Utile.bytesToHexString(returnbyte) +"] ");

		return returnbyte;
	}

	/**
	 * 把建行的报文域转化为iso8583标准银联bean字段
	 * @param resBean 银联标准bean
	 * @param key 建行8583报文域
	 * @param value 域值
	 * */
	private static UnionPayBean putPropertyVaule(UnionPayBean resBean,
			int key, String value) throws Exception {
		Map<Integer, String> fields = CmbcParamMapping.ccbUnionMap;
		String property, setMethod ;
		property = fields.get(key);
		setMethod = "set" + property.substring(0, 1).toUpperCase() + property.substring(1); 
		try {
			UnionPayBean.class.getMethod(setMethod, String.class).invoke(resBean, value);
		} catch (Exception e) {
			throw new Exception();
		}
		return resBean;
	}

	/**
	 * 把建行8583报文解析成银联UnionPayParam 
	 * 建行返回报文：TPUD+报文头+ISO8583报文
	 * @param data 		     不带2位报文长度的建行返回信息
	 * @param dataLen 	     待读取数据长度
	 * @return UnionPayParam 银联报文对象
	 */
	public UnionPayBean Decoding(byte data[], int dataLen)
			throws Exception {
		int offset = 0;															//报文读取偏移量
		UnionPayBean resBean = new UnionPayBean();
		resBean.setOrgIso8583Msg(data.toString());
		
		if (dataLen < LEN_CCB_TPUD) throw new Exception();   					// 检查TPDU
		offset+=LEN_CCB_MESSAGETYPE;
		byte[] tpduByte = new byte[LEN_CCB_TPUD];
		System.arraycopy(data, offset, tpduByte, 0, LEN_CCB_TPUD);
		String tpud = ISO8583Utile.bcd2String(tpduByte, false);
		log.info("Decoding tpud = " + tpud);
		dataLen -= LEN_CCB_TPUD;
		offset += LEN_CCB_TPUD;

		
		if (dataLen < LEN_CCB_MESSAGE) throw new Exception(); 					// 检查报文头
		byte[] messageByte = new byte[LEN_CCB_MESSAGE];
		System.arraycopy(data, offset, messageByte, 0, LEN_CCB_MESSAGE);
		String messageHead = ISO8583Utile.bcd2String(messageByte, false);
		log.info("Decoding message head = "+ messageHead);
		dataLen -= LEN_CCB_MESSAGE;
		offset += LEN_CCB_MESSAGE;

		if (dataLen < LEN_CCB_MESSAGETYPE) throw new Exception();				// 检查信息类型
		byte[] mesagetype = new byte[LEN_CCB_MESSAGETYPE];
		System.arraycopy(data, offset, mesagetype, 0, LEN_CCB_MESSAGETYPE);
		String message = ISO8583Utile.bcd2String(mesagetype, false);
		log.info("Decoding message type = " + message);
		dataLen -= LEN_CCB_MESSAGETYPE;
		offset += LEN_CCB_MESSAGETYPE;
		
		if (!CmbcParamMapping.getMtpResult(message)) throw new Exception(); 	// 判断返回消息类型是否正确
		
		resBean = putPropertyVaule(resBean, 0, message);					// 8583.0

		int bigMapLen = 0;
		byte bitmapbase[] = new byte[LEN_BITMAP_BASE];							// 基本位图
		System.arraycopy(data, offset, bitmapbase, 0, LEN_BITMAP_BASE);
		log.info("Decoding base bigmap "+ISO8583Utile.bytesToHexString(bitmapbase)); 
		bigMapLen = LEN_BITMAP_BASE;
		dataLen -= LEN_BITMAP_BASE;
		offset += LEN_BITMAP_BASE;
		
		int bitmapExtendLen = 0;
		if ((bitmapbase[0] & 0x80) != 0) {										// 位图bitmap[0]=0为基础位图,=1则为扩展位图
			if (dataLen < LEN_BITMAP_EXTENDED) throw new Exception();
			bigMapLen += LEN_BITMAP_EXTENDED;
			bitmapExtendLen = LEN_BITMAP_EXTENDED;
		} 
		
		byte bitmap[] = new byte[bigMapLen];									// 实际位图		
		System.arraycopy(bitmapbase, 0, bitmap, 0, LEN_BITMAP_BASE);			// 填充基础位图到实际位图
		
		if (bitmapExtendLen > 0) {		
			byte bitmapextend[] = new byte[bitmapExtendLen];										// 扩展位图
			System.arraycopy(data, offset, bitmapextend, 0, bitmapExtendLen);						// 读取扩展位图信息
			log.info("Decoding base bigmap "+ISO8583Utile.bytesToHexString(bitmapextend)); 
			dataLen -= LEN_BITMAP_EXTENDED;
			offset += LEN_BITMAP_EXTENDED;
			System.arraycopy(bitmapextend, 0, bitmap, LEN_BITMAP_BASE,LEN_BITMAP_EXTENDED);			// 填充扩展位图到实际位图
			log.info("Decoding real bigmap "+ISO8583Utile.bytesToHexString(bitmap)); 
		}

		bitmap = ISO8583Utile.string2Bcd(ISO8583Utile.Byte2Hex(bitmap,
				bigMapLen));
		log.info("Decoding real bigmap "+ISO8583Utile.bytesToHexString(bitmap)); 

		/**
		 * 解析位图中记录的返回字段域ID
		 */
		BitSet bs = ISO8583Utile.getBitMap(bitmap, bigMapLen);
		log.info("Decoding bs = " + bs);

		log.info("field[index]: [data,maxLength,dataType,encodeType,lengthType,offset]");		
		
		int charlen = 0;
		CmbcISO8583ForBank ccbResponse = new CmbcISO8583ForBank(); 
		Field8583 ins = null;
		for (int i = 0; i < bigMapLen; i++) {
			int flag = 0x100;
			for (int j = 0; j < 8; j++) {
				flag >>>= 1;
				if ((bitmap[i] & flag) == 0)
					continue;

				int FieldId = i * 8 + j + 1;// 有值的域的ID号
				if (FieldId == 1) {
					continue;
				}
				
				Field8583 itmdef = ccbResponse.getItemDef(FieldId);
				ins = DecodingItem(itmdef, FieldId, data, offset, dataLen);
				ccbResponse.putItem(FieldId, ins.getValue());
				resBean = putPropertyVaule(resBean, FieldId, ins.getValue());
				if (FieldId == 52) {//
					dataLen -= 8;
					offset += 8;
					continue;
				}

				
				log.info("field[" + FieldId + "]: ["+ 
						ins.getValue() + "," + 
						ins.getLength()+ "," +
						ins.getDataType().name() + "," + 
						ins.getEncodeType()+ "," + 
						ins.getLengthType()+ "," + 
						offset + "] ");

				if (LengthType.APPOINT.equals(ins.getLengthType())) {				// 定长
					if (DataType.N.equals(ins.getDataType())
							&& EncodeType.BCD.equals(ins.getEncodeType())) {		// 数字,BCD编码
						dataLen -= (ins.getLength() + 1) / 2;
						offset += (ins.getLength() + 1) / 2;
					} else {
						dataLen -= ins.getLength();
						offset += ins.getLength();
					}
				} else if (LengthType.LLVAR.equals(ins.getLengthType())) {			// LLVAR
					if (DataType.S.equals(ins.getDataType())
							&& EncodeType.ASCII.equals(ins.getEncodeType())) {
						if (FieldId == 44&&null!=ins.getValue()&&!"".equals(ins.getValue())) {// 附加数据域，带中文
							String value = ins.getValue();
							charlen = ISO8583Utile.getChinCharNumber(value);
							 log.info("field[" + FieldId + "]: value=" + value + " valuelen=" + value.length()+" charlen="+ charlen);
							if (charlen > 0) {
								dataLen -= ins.getValue().length() + 1;
								offset += ins.getValue().length() + 1;
							}
						}
							dataLen -= ins.getValue().length() + 1;
							offset += ins.getValue().length() + 1;
						
					} else {// 取的是BCD码
						dataLen -= (ins.getValue().length() + 1) / 2 + 1;
						offset += (ins.getValue().length() + 1) / 2 + 1;
					}
				} else if (LengthType.LLLVAR.equals(ins.getLengthType())) { 		// LLLVAR
					if (DataType.S.equals(ins.getDataType())
							&& EncodeType.ASCII.equals(ins.getEncodeType())) {
						if (FieldId == 62||FieldId == 56) {// 工作密钥
							dataLen -= (ins.getValue().length() + 1) / 2 + 2;
							offset += (ins.getValue().length() + 1) / 2 + 2;
						}else if(FieldId==55||FieldId==57||FieldId==59){
							dataLen -= ins.getValue().getBytes().length/2 + 2;
							offset += ins.getValue().getBytes().length/2 + 2;
						} else if(FieldId==47){
							dataLen -=ISO8583Utile.bytesToHexString(ins.getValue().getBytes()).length()/2 + 2;
							offset += ISO8583Utile.bytesToHexString(ins.getValue().getBytes()).length()/2 + 2;
						} else {
							dataLen -= ins.getValue().length() + 2;
							offset += ins.getValue().length() + 2;
						}
					} else {// 取的是BCD码
						dataLen -= (ins.getValue().length() + 1) / 2 + 2;
						offset += (ins.getValue().length() + 1) / 2 + 2;
					}
				}

			}

		}
		if (dataLen != 0) {
			if (StringUtil.notNull(resBean.getResponseCode()) && !Constant.BANK_RESP_CODE_00.equals(resBean.getResponseCode())) {
				throw new CmbcTransException(CmbcTransException.ERROR_PARSE_ITEM_LENGTH);
			} else {
				throw new Exception();
			}
		}

		return resBean;
	}

	private static Field8583 DecodingItem(Field8583 itmdef, int FieldId,
			byte data[], int offset, int leftLen) throws  Exception {
		
		int domainLen = 0; 
		String value = ""; 


		if (LengthType.APPOINT.equals(itmdef.getLengthType())) {					// 定长
			int maxlen = itmdef.getLength();
			if (DataType.N.equals(itmdef.getDataType())
					&& EncodeType.BCD.equals(itmdef.getEncodeType())) {				// 数字,BCD编码
				byte[] itemValue = new byte[(maxlen + 1) / 2];
				System.arraycopy(data, offset, itemValue, 0, (maxlen + 1) / 2);
				value = ISO8583Utile.bcd2String(itemValue, false);
				if (maxlen % 2 != 0) {												// 如果是奇数
					value = value.substring(0, maxlen);
				}
			} else {
				domainLen = maxlen;

				domainLen = maxlen;
				if (FieldId == 52) {// 52密码码单独处理
					byte[] pinbyte = new byte[8];
					System.arraycopy(data, offset, pinbyte, 0, 8);
					value = ISO8583Utile.bytesToHexString(pinbyte);
				} else {
					value = new String(data, offset, domainLen);
				}
				// str=new String(data, offset, bytelen);
//				value = new String(data, offset, domainLen);
			}
		} else if (LengthType.LLVAR.equals(itmdef.getLengthType())) {				// LLVAR
			byte[] itemValuelen = new byte[1];										// 域长度BCD
			System.arraycopy(data, offset, itemValuelen, 0, 1);
			value = ISO8583Utile.bcd2String(itemValuelen, false);
			domainLen = Integer.parseInt(value);										// 域真实长度
			if (DataType.S.equals(itmdef.getDataType())
					&& EncodeType.ASCII.equals(itmdef.getEncodeType())) {
				byte[] domainData = new byte[domainLen]; 
				System.arraycopy(data, offset + 1, domainData, 0, domainLen);
				try {
					value = new String(domainData, "gb2312");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} else {// 取的是BCD码(左靠)
				byte[] domainData = new byte[(domainLen + 1) / 2];
				System.arraycopy(data, offset + 1, domainData, 0,
						(domainLen + 1) / 2);
				value = ISO8583Utile.bytesToHexString( domainData );
				// value=ISO8583Utile.bcd2String(domainData, false);
				value = value.replace("D", "=");
				if (domainLen % 2 != 0) {
					value = value.substring(0, domainLen);
				}
			}
		} else if (LengthType.LLLVAR.equals(itmdef.getLengthType())) {				// LLLVAR
			byte[] itemValuelen = new byte[2];										// 域长度BCD
			System.arraycopy(data, offset, itemValuelen, 0, 2);
			value = ISO8583Utile.bcd2String(itemValuelen, false);
			domainLen = Integer.parseInt(value);									// 域真实长度
			if (DataType.S.equals(itmdef.getDataType())
					&& EncodeType.ASCII.equals(itmdef.getEncodeType())) {
				if (FieldId == 62 || FieldId==55||FieldId==56||FieldId==57||FieldId==59) {												// 工作密钥
					byte[] itemValue = new byte[domainLen];
					System.arraycopy(data, offset + 2, itemValue, 0, domainLen);
					value = ISO8583Utile.bytesToHexString(itemValue);
				} else {
					value = new String(data, offset + 2, domainLen);
				}
			} else {																// 取的是BCD码
				byte[] itemValue = new byte[(domainLen + 1) / 2];
				System.arraycopy(data, offset + 2, itemValue, 0,
						(domainLen + 1) / 2);
				value = ISO8583Utile.bytesToHexString(itemValue);
				// str=ISO8583Utile.bcd2String(itemValue, false);
				value = value.replace("D", "=");
				if (domainLen % 2 != 0) {
					value = value.substring(0, domainLen);
				}
			}
		} else {
			throw new Exception();
		}

		// 判断取到的域的值是否与定义的数据类型一致
		if (itmdef.getDataType() == DataType.N) { // 检查数据是否为有效的数字串,MAC码和位图的暂不作判断
			if (!ISO8583Utile.isValidDecString(value))
				throw new Exception();
		}
         
		 Field8583 field8583=new Field8583(FieldId,itmdef.getLength(), itmdef.getLengthType(),  itmdef.getDataType(),itmdef.getEncodeType());
	     field8583.setValue(value);
		return field8583;
	}

	public Hashtable getFieldDefines() {
		return domainDefPool;
	}

	public static void main(String[] args) throws Exception {

		// 查询
		
		CmbcISO8583ForBank ccb = new CmbcISO8583ForBank();
		try{
			byte [] res_byte=ISO8583Utile.hexStringToByte("011660000000036031003110040200702406C022C09A111662261867558889990000000000000000020000012112051000000012376226186755888999D2112220009756510000003330313030303031303233303534343030353831333130343031353612B03E4B289EBD78260000000000000001459F26083D6C7EBC47F92D4D9F2701809F101307010103A02002010A010000000000D327F59C9F370421DCE2B19F360200269505008004E0009A031709049C01009F02060000000000025F2A02015682027C009F1A0201569F03060000000000009F3303E0F9C89F34030203009F3501229F1E0837353532303534378408A0000003330101019F090200309F4104000000130013220000010005003031353742384335");
		log.info("bank response HexString : "+ISO8583Utile.bytesToHexString(res_byte));
		
		// 验证数据头长度和报文长度是否一致
		byte[] len = new byte[2];		
		System.arraycopy(res_byte, 0, len, 0, 2);
		int messagelen = Integer.parseInt(ISO8583Utile.bytesToHexString(len), 16);	
		if (messagelen != res_byte.length - 2) {
			log.info("------CCBTransUtil messageLength error!");
		}
	
		// 解析银行返回报文
		UnionPayBean res_bean = ccb.Decoding(res_byte, messagelen);
		} catch (CmbcTransException e) {
			e.printStackTrace();
		}


	
	}
}
