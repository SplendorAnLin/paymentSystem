package com.yl.pay.pos.interfaces.common.util.ISO8583;

import java.io.UnsupportedEncodingException;
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
import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.bean.UnionPayBean;

/**
 * Title: 中行报文域定义
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: pay
 * @author haitao.liu
 */

public class BocISO8583 {
	
	private static Log log = LogFactory.getLog(BocISO8583.class);
	
	static Hashtable<FieldId, Field8583> ItemTbl = null;
	  
	public final static int FIELD_ID_START = 0;				//用于标识报文的域开始最小数	  
	public final static int FIELD_ID_END = 64;				//用于标识报文的域结束最大数	  	
	public final static int LEN_BITMAP_BASE = 8;			//主位图的长度	 
	public final static int LEN_BITMAP_EXTENDED = 8;		//扩展位图的长度	  
	public final static int LEN_BOC_TPUD = 5;				//TPUD的长度	 
	public final static int LEN_BOC_MESSAGETYPE = 2; 		//信息类型的长度	  
	public final static int LEN_CERTIFIED_VERSION = 2;		//认证版本号长度
	public final static int LEN_BOC_LEN = 2;	  			//报文长度的长度	  
	  	  
	Hashtable<FieldId, Field8583> fields = null;	  

	static {
		ItemTbl = new Hashtable<FieldId, Field8583>();
		
		ItemTbl.put(new FieldId( 0), new Field8583(0,4,LengthType.APPOINT,DataType.N,EncodeType.BCD));		//信息类型
		ItemTbl.put(new FieldId( 1), new Field8583(1,8, LengthType.APPOINT,DataType.B,EncodeType.BINARY));	//位图
		ItemTbl.put(new FieldId( 2), new Field8583(2,19, LengthType.LLVAR,DataType.N,EncodeType.BCD));		//主账号
		  
		ItemTbl.put(new FieldId( 3), new Field8583(3,6,LengthType.APPOINT,DataType.N,EncodeType.BCD));		//处理码
		ItemTbl.put(new FieldId( 4), new Field8583(4,12,LengthType.APPOINT,DataType.N,EncodeType.BCD));		//交易金额
		ItemTbl.put(new FieldId( 7), new Field8583(7,12,LengthType.APPOINT,DataType.N,EncodeType.BCD));		//交易金额
		ItemTbl.put(new FieldId( 11), new Field8583(11,6,LengthType.APPOINT,DataType.N,EncodeType.BCD));	//系统跟踪号
		
		ItemTbl.put(new FieldId( 12), new Field8583(12,6,LengthType.APPOINT,DataType.N,EncodeType.BCD));	//本地交易时间
		ItemTbl.put(new FieldId( 13), new Field8583(13,4,LengthType.APPOINT,DataType.N,EncodeType.BCD));	//本地交易日期
		ItemTbl.put(new FieldId( 14), new Field8583(14,4,LengthType.APPOINT,DataType.N,EncodeType.BCD));	//卡有效期
		
		ItemTbl.put(new FieldId( 22), new Field8583(22,3,LengthType.APPOINT,DataType.N,EncodeType.BCD));	//POS输入方式
		ItemTbl.put(new FieldId( 23), new Field8583(23,3,LengthType.APPOINT,DataType.N,EncodeType.BCD));	//卡片序列号
		ItemTbl.put(new FieldId( 24), new Field8583(24,3,LengthType.APPOINT,DataType.N,EncodeType.BCD));	//NII
		ItemTbl.put(new FieldId( 25), new Field8583(25,2,LengthType.APPOINT,DataType.N,EncodeType.BCD));	//服务点条件码
		
		ItemTbl.put(new FieldId( 35), new Field8583(35,37,LengthType.LLVAR,DataType.S,EncodeType.BCD));		//第二磁道数据
		ItemTbl.put(new FieldId( 36), new Field8583(36,104,LengthType.LLLVAR,DataType.S,EncodeType.BCD));	//第三磁道数据
		ItemTbl.put(new FieldId( 37), new Field8583(37,12,LengthType.APPOINT,DataType.S,EncodeType.ASCII));	//检索参考号
		ItemTbl.put(new FieldId( 38), new Field8583(38,6,LengthType.APPOINT,DataType.S,EncodeType.ASCII));	//授权标识应答码
		ItemTbl.put(new FieldId( 39), new Field8583(39,2,LengthType.APPOINT,DataType.S,EncodeType.ASCII));	//应答码
		
		ItemTbl.put(new FieldId( 41), new Field8583(41,8,LengthType.APPOINT,DataType.S,EncodeType.ASCII));	//终端编号
		ItemTbl.put(new FieldId( 42), new Field8583(42,15,LengthType.APPOINT,DataType.S,EncodeType.ASCII));	//商户号
		ItemTbl.put(new FieldId( 44), new Field8583(44,25,LengthType.LLVAR,DataType.S,EncodeType.ASCII));	//附加响应数据
		ItemTbl.put(new FieldId( 48), new Field8583(48,999,LengthType.LLLVAR,DataType.S,EncodeType.ASCII));	//附加数据——私有
		ItemTbl.put(new FieldId( 49), new Field8583(49,3,LengthType.APPOINT,DataType.N,EncodeType.BCD));	//*交易货币代码 改类型
		
		ItemTbl.put(new FieldId( 52), new Field8583(52,16,LengthType.APPOINT,DataType.B,EncodeType.BINARY));//个人标识码数据
		
		ItemTbl.put(new FieldId( 54), new Field8583(54,120,LengthType.LLLVAR,DataType.S,EncodeType.ASCII));	//附加金额
		ItemTbl.put(new FieldId( 55), new Field8583(55,999,LengthType.LLLVAR,DataType.S,EncodeType.ASCII));	//IC卡数据域
		ItemTbl.put(new FieldId( 56), new Field8583(56,999,LengthType.LLLVAR,DataType.S,EncodeType.ASCII));	//附加金额    
		
		ItemTbl.put(new FieldId( 61), new Field8583(61,999,LengthType.LLLVAR,DataType.S,EncodeType.ASCII));	//自定义域
		ItemTbl.put(new FieldId( 611), new Field8583(611,6,LengthType.APPOINT,DataType.N,EncodeType.ASCII));//批次号
		ItemTbl.put(new FieldId( 612), new Field8583(612,3,LengthType.APPOINT,DataType.N,EncodeType.ASCII));//操作员号
		ItemTbl.put(new FieldId( 613), new Field8583(613,6,LengthType.APPOINT,DataType.N,EncodeType.ASCII));//票据号
		ItemTbl.put(new FieldId( 614), new Field8583(614,2,LengthType.APPOINT,DataType.N,EncodeType.ASCII));//卡类型
		ItemTbl.put(new FieldId( 615), new Field8583(615,10,LengthType.APPOINT,DataType.S,EncodeType.ASCII));//发卡银行简称
		ItemTbl.put(new FieldId( 62), new Field8583(62,20,LengthType.LLLVAR,DataType.S,EncodeType.ASCII));	//原始交易信息
		ItemTbl.put(new FieldId( 621), new Field8583(621,4,LengthType.APPOINT,DataType.N,EncodeType.ASCII));//信息类型码
		ItemTbl.put(new FieldId( 622), new Field8583(622,6,LengthType.APPOINT,DataType.N,EncodeType.ASCII));//系统跟踪号
		ItemTbl.put(new FieldId( 623), new Field8583(623,10,LengthType.APPOINT,DataType.N,EncodeType.ASCII));//交易日期和时间
		ItemTbl.put(new FieldId( 63), new Field8583(63,36,LengthType.LLLVAR,DataType.S,EncodeType.ASCII));	//交易总计数据
		ItemTbl.put(new FieldId( 64), new Field8583(64,16,LengthType.APPOINT,DataType.B,EncodeType.ASCII));	//*报文鉴别码
		
		log.debug( "ISO8583 init 8583 defination table, size=" + ItemTbl.size());
	};

	public BocISO8583() {
		fields = new Hashtable<FieldId, Field8583>();
	}

	private static Field8583 getItemDef(int index)
			throws ArrayIndexOutOfBoundsException {
		Field8583 itm = (Field8583) ItemTbl.get(new FieldId(index));
		if (itm == null) {
			throw new ArrayIndexOutOfBoundsException("8583 field not define, invalid 8583 field id=" + index);
		}
		return itm;
	}
	  
	public void putItem(int d, String data) throws Exception {
		if (d < 600) {
			// 61，62，63这几个有子域的域直接跳过，不判断
			if (d < FIELD_ID_START || d > FIELD_ID_END)
				throw new Exception("item.out.of.range.invalid.8583.fiel");
		}
		Field8583 itmdef = getItemDef(d);
		if (itmdef == null)
			throw new Exception("the.field.not.define.invalid.8583.field.id");

		// 检查数据的长度是否合法
		if (data.length() > itmdef.getLength())
			throw new Exception("item.data.longer.than.defined.in.8583");

		Field8583 field8583 = new Field8583(d, itmdef.getLength(), itmdef
				.getLengthType(), itmdef.getDataType(), itmdef.getEncodeType());
		field8583.setValue(data);
		fields.put(new FieldId(d), field8583);
		}
	   
	public Field8583 getItem(int fld) throws ArrayIndexOutOfBoundsException {
		if (fld < 600) {// 61，62，63这几个有子域的域直接跳过，不判断
			if (fld < FIELD_ID_START || fld > FIELD_ID_END)
				throw new ArrayIndexOutOfBoundsException(
						"invalid 8583 field id=" + fld);
		}
		Field8583 itm = (Field8583) fields.get(new FieldId(fld));

		return itm;
	}
	   
   /**
    * 把定义的8583字段转换成8583表示的字符串
    */
   public byte[] Encoding(String tpdu, String version) throws Exception {
     
		boolean flag128 = false;
		Field8583 itm = null;
		byte[] bitmap = new byte[16];
		byte[] buf = new byte[Field8583.FLD_MAX_SIZE];// 用来存放从第2域开始的报文的整个字节数组
		byte[] itembytes = new byte[Field8583.FLD_MAX_SIZE];
		byte[] bytes = new byte[30];
		byte[] itembyte = null;
		int len = 0;
		
		// processing from bitmap 2
		// 注意，必须从第2域开始。否则，如果用户把第1域（bitmap）放入，会出现错误。
		
		log.info("[Bank] Messagetype = " + getItem(0).getValue());
		//log.info("field[index]: [data,maxLength,lengthType,dataType]" );			
		
		int bitmapLen = 8;
		for (int i = 2; i <= FIELD_ID_END; i++) {
			if (i == 61 || i == 62) {// 特殊的域单独做处理
				int itemlen = 0;
				boolean key = false;
				for (int j = i * 10 + 1; j <= i * 10 + 5; j++) {
					itm = getItem(j);
					if (itm != null) {
						key = true;
						itembyte = EncodingItem(itm);
						System.arraycopy(itembyte, 0, bytes, itemlen,
								itembyte.length);
						itemlen += itembyte.length;
						log.info("field[" + j + "]: ["+ 
								 itm.getValue() + "," + 
								 itm.getLength() + "," +
								 itm.getLengthType() + "," + 
								 itm.getDataType() + "] ");
					}
				}
				if (key) {
					bitmap[(i - 1) / 8] = (byte) (bitmap[(i - 1) / 8] | (0x80 >>> ((i - 1) % 8)));

					byte[] byteslen = new byte[2];
					String strLen = ISO8583Utile.int2char3(itemlen);
					byteslen = ISO8583Utile.string2Bcd(strLen);// 域长度BCD码
					System.arraycopy(byteslen, 0, buf, len, 2);
					len += 2;
					System.arraycopy(bytes, 0, buf, len, itemlen);
					len += itemlen;
				}
			} else if (i == 52 || i == 64) {
				itm = getItem(i);
				if (itm != null) {
					bitmap[(i - 1) / 8] = (byte) (bitmap[(i - 1) / 8] | (0x80 >>> ((i - 1) % 8)));
					byte[] s = new byte[8];
					s = ISO8583Utile.hexStringToByte(itm.getValue());
					System.arraycopy(s, 0, buf, len, 8);
					len += 8;
				}
			} else {
				itm = getItem(i);
				if (itm != null) {
					if (flag128 == false && i > 64) { // testing extend bitmap
														// flag
						flag128 = true;
						bitmapLen = 16;
						bitmap[0] = (byte) (bitmap[0] | 0x80);
					}
					bitmap[(i - 1) / 8] = (byte) (bitmap[(i - 1) / 8] | (0x80 >>> ((i - 1) % 8)));
					itembytes = EncodingItem(itm);
					System.arraycopy(itembytes, 0, buf, len, itembytes.length);
					len += itembytes.length;
				}
			}
			
			if (itm != null) {
				if(i!=35&&i!=36){
					log.info("field[" + i + "]: ["+ 
							itm.getValue() + "," + 
							itm.getLength() + "," +
							itm.getLengthType() + "," + 
							itm.getDataType() + "] ");
				}
			}
			
		}

		String messagetype = getItem(0).getValue();// 消息类型
		// 转换成8583内容格式
		// 转换成8583内容格式
		int i = 0;
		int messagelen = messagetype.length();
		int mesLen = len + messagelen / 2 + bitmapLen + 7;
		byte[] data = new byte[LEN_BOC_LEN + mesLen];
		byte[] lenByte = new byte[LEN_BOC_LEN];
		lenByte[0] = (byte) (mesLen / 256);
		lenByte[1] = (byte) (mesLen % 256);
			
		if(tpdu==null||"".equals(tpdu)){
			log.info("[Bank] TPDU is null!");
			throw new Exception("error.response.item.tpud");
		}
		
		if(version==null||"".equals(version)){
			log.info("[Bank] Version is null!");
			throw new Exception("error.response.item.version");
		}
		
		System.arraycopy(lenByte, 0, data, i, LEN_BOC_LEN);//
		i += LEN_BOC_LEN;
		System.arraycopy(ISO8583Utile.string2Bcd(tpdu), 0, data, i, 5);// 报文头
		i += 5;
		System.arraycopy(ISO8583Utile.string2Bcd(version), 0, data, i, 2);// 认证
		i += 2;
		System.arraycopy(ISO8583Utile.string2Bcd(messagetype), 0, data, i,	messagelen / 2);
		i += messagelen / 2;
		System.arraycopy(bitmap, 0, data, i, bitmapLen);
		i += bitmapLen;
		System.arraycopy(buf, 0, data, i, len);
		i += len;

//		log.info("bank request before add mti & bitmap data(Hex)="+ISO8583Utile.bytesToHexString(data));
	
		return (data);
   }
   	   
   /**
	 * Encode an item according to coding rule
	 * 
	 * @param itm
	 * @return: The item's encoded string length
	 */
   private byte[] EncodingItem(Field8583 itm)  {
	     int iMaxLen; // item content max length
	     int iSrcLen; //item content actual length
	     int iDestLen; //encoded string length
	     String sEncoded = ""; //encoded item string
	     String strLen;

	     int  data_len = 0;
	     byte[] data  = new byte[Field8583.FLD_MAX_SIZE];

	     iMaxLen = itm.getLength();//域数据是大长度
	     if(itm.getId()==55||itm.getId()==48){
	    	 iSrcLen=ISO8583Utile.hexStringToByte(itm.getValue()).length;
	     }else{
	    	 iSrcLen = itm.getValue().length();//域中内容的实际长度
	     }    
		 byte[] len=null;
		 byte[] contenbyte=null;
		 if (LengthType.LLVAR.name().equals(itm.getLengthType().name())) { // LLVAR
				strLen = ISO8583Utile.int2char2(iSrcLen);
				len = ISO8583Utile.string2Bcd(strLen);// 域长度BCD码
				if (DataType.N.equals(itm.getDataType())) {// 数字型
					contenbyte = ISO8583Utile.string2Bcd(itm.getValue());// 域值
				} else {// 字符型（目前Binary没有变长，所以不考虑）
					if (EncodeType.BCD.equals(itm.getEncodeType())) {// 针对35，36域
						contenbyte = ISO8583Utile.string2Bcd(itm.getValue());
					} else {
						contenbyte = itm.getValue().getBytes();
					}
				}
				System.arraycopy(len, 0, data, data_len, 1);
				data_len += 1;
				System.arraycopy(contenbyte, 0, data, data_len, contenbyte.length);
				data_len += contenbyte.length;

			} else if (LengthType.LLLVAR.name().equals(itm.getLengthType().name())) { // LLVAR
				strLen = ISO8583Utile.int2char3(iSrcLen);
				len = ISO8583Utile.string2Bcd(strLen);// 域长度BCD码
				if (DataType.N.equals(itm.getDataType())) {// 数字型
					contenbyte = ISO8583Utile.string2Bcd(itm.getValue());// 域值
				} else {// 字符型（目前Binary没有变长，所以不考虑）
					if (EncodeType.BCD.equals(itm.getEncodeType())) {// 针对35，36域
						contenbyte = ISO8583Utile.string2Bcd(itm.getValue());
					} else {
						if(itm.getId()==55){
							contenbyte=ISO8583Utile.hexStringToByte(itm.getValue());
						}else {
							contenbyte = itm.getValue().getBytes();
						}
					}
				}
				System.arraycopy(len, 0, data, data_len, 2);
				data_len += 2;
				System.arraycopy(contenbyte, 0, data, data_len, contenbyte.length);
				data_len += contenbyte.length;

			} else if (LengthType.APPOINT.name().equals(itm.getLengthType().name())) { // fix
				iDestLen = iMaxLen;
				if (DataType.N.name().equals(itm.getDataType().name())) { // data
					sEncoded = ISO8583Utile.StringFillLeftZero(itm.getValue(),iDestLen);
					if (EncodeType.BCD.equals(itm.getEncodeType())) {// BCD
						data = ISO8583Utile.string2Bcd(sEncoded);
						data_len += (iMaxLen + 1) / 2;
					} else { // ascii
						data = sEncoded.getBytes();
						data_len += iMaxLen;
					}
				} else { // data type S,B(binary在PUT之前就要转换成8个字节的数组)
					sEncoded = ISO8583Utile.StringFillRightBlank(itm.getValue(),iDestLen);
					data = sEncoded.getBytes();
					data_len += iMaxLen;
				}
	    	    }else{
			         System.err.println("item " + itm.getId() + " variable flag invalid! data=" + itm.getValue() + ", datetype=" +
			                            itm.getDataType().name() );
	    	    }

	   byte[] returnbyte=new byte[data_len];
	   System.arraycopy(data, 0, returnbyte, 0, data_len);
   
     return returnbyte;
   }
   
   /**
    * 把8583报文解析成Bccb8583类
    * 注意：一定要按照中行TPUD+认证号+ISO8583报文的格式来进行解析
    * @param data
    * @param len
    * @return
    * @throws Exception
    */
   public UnionPayBean Decoding(byte data[], int len) throws Exception {
	 
		int bitmapLen = 0;
		int maxfields = 0;
		int offset = 0;
		
		UnionPayBean resEntity = new UnionPayBean();
		resEntity.setOrgIso8583Msg(data.toString());
										
		// 检查TPUD
		if(len<LEN_BOC_TPUD){
			throw new Exception("error.response.item.tpud");
		} 
		byte[] tpudtype=new byte[LEN_BOC_TPUD];
		System.arraycopy(data, offset, tpudtype, 0,LEN_BOC_TPUD);
					 
		len -= LEN_BOC_TPUD;
		offset += LEN_BOC_TPUD;
     
		// 检查认证版本号
		if(len<LEN_CERTIFIED_VERSION){
			throw new Exception("error.response.item.version");
		}	 
		byte[] versiontype=new byte[LEN_CERTIFIED_VERSION];
		System.arraycopy(data, offset, versiontype, 0,LEN_CERTIFIED_VERSION);
		 
		len -= LEN_CERTIFIED_VERSION;
		offset += LEN_CERTIFIED_VERSION;
     
     
	     // 检查信息类型
		if (len < LEN_BOC_MESSAGETYPE)
			throw new Exception("error.response.item.messagetypelen");
		byte[] mesagetype = new byte[LEN_BOC_MESSAGETYPE];
		System.arraycopy(data, offset, mesagetype, 0, LEN_BOC_MESSAGETYPE);
		String mti = ISO8583Utile.bcd2String(mesagetype, false);

		
		if("0210,0410,0230,0110,0810,0510".indexOf(mti)<0){
			throw new Exception("error.response.item.messagetypelen");
		}					
		
		resEntity = putPropertyVaule(resEntity, 0, mti);
		len -= LEN_BOC_MESSAGETYPE;
		offset += LEN_BOC_MESSAGETYPE;

		byte bitmapbase[] = new byte[LEN_BITMAP_BASE];
		System.arraycopy(data, offset, bitmapbase, 0, LEN_BITMAP_BASE);
		
		
		log.info("[Bank] Messagetype = " + mti);
		//log.info("field[index]: [data,maxLength,lengthType,dataType]" );
		
		maxfields = LEN_BITMAP_BASE;
		len -= LEN_BITMAP_BASE;
		offset += LEN_BITMAP_BASE;

		if ((bitmapbase[0] & 0x80) != 0) {
			if (len < LEN_BITMAP_EXTENDED){
				throw new Exception("error.parse.item.length");
			}
			maxfields = LEN_BITMAP_BASE + LEN_BITMAP_EXTENDED;
			bitmapLen = LEN_BITMAP_EXTENDED;	
		}
     	     
		byte bitmapextend[] = new byte[bitmapLen];
		byte bitmapextendtype[] = new byte[bitmapLen];
		byte bitmap[] = new byte[maxfields];
		
		System.arraycopy(bitmapbase, 0, bitmap, 0, LEN_BITMAP_BASE);
		if (bitmapLen > 0) {
			System.arraycopy(data, offset, bitmapextend, 0, bitmapLen);	
			
			log.info("BocISO8583 Request: offset = " + offset + ", bitmap = " + ISO8583Utile.Byte2Hex(bitmapextend, 0, bitmapLen));
			
			len -= LEN_BITMAP_EXTENDED;
			offset += LEN_BITMAP_EXTENDED;
			System.arraycopy(bitmapextendtype, 0, bitmap, LEN_BITMAP_BASE,LEN_BITMAP_EXTENDED);
			
			System.arraycopy(data, offset, bitmapextend, 0, bitmapLen);			
		}
			
		bitmap = ISO8583Utile.string2Bcd(ISO8583Utile.Byte2Hex(bitmap,maxfields));
		BitSet bs = ISO8583Utile.getBitMap(bitmap, maxfields);		
		log.info("bs = " + bs);
		
		BocISO8583 bankRresponse = new BocISO8583();			
		Field8583 itm = null;
		
		for (int i = 0; i < maxfields; i++) {
			int flag = 0x100;
			for (int j = 0; j < 8; j++) {
				flag >>>= 1;
				if ((bitmap[i] & flag) == 0)
					continue;	
				int fld = i * 8 + j + 1;// 有值的域的ID号
				if (fld == 1) {
					continue;
				}
        
				itm = DecodingItem(bankRresponse,fld, data, offset, len);
				  
				log.info("field[" + fld + "]: ["+ 
						itm.getValue() + "," + 
						itm.getLength() + "," +
						itm.getLengthType() + "," + 
						itm.getDataType() + "] ");
				  
				bankRresponse.putItem(fld, itm.getValue());
		         
	         	int propertyLen = 0;
				String propertyValue = "";
	
				// 61和62有子域的要特殊的去进行处理,因为这两个域的子域都是定长的ASCII编码，因此直接去进行截取
	
				if (fld == 61 || fld == 62) {
					String returnString = itm.getValue();// 该域返回的值
					Field8583 newItm = null;
					int bytelen = 0;
					int newFildLen = 0;
					int charlen = 0;// 字符串中的汉字数量
					if (returnString != null) {
						for (int k = 1; k < 6; k++) {// 先暂时把中文返回的给关掉
							propertyLen = fld * 10 + k;
							newItm = getItemDef(propertyLen);
							newFildLen = newItm.getLength();
							if (propertyLen == 615) {
								charlen = ISO8583Utile.getChinCharNumber(returnString);
								
								log.info("field[" + fld + "]: value=" + returnString + " charlen=" + charlen);
	
								if (charlen > 0) {
									newFildLen = newFildLen - charlen;
								}
							}
							propertyValue = returnString.substring(bytelen,	newFildLen);
							resEntity = putPropertyVaule(resEntity,	propertyLen, propertyValue);
							returnString = returnString.substring(newFildLen);
							if (returnString == null || "".equals(returnString)) {
								break;
							}
						}
					}
				} else {
					propertyLen = fld;
					propertyValue = itm.getValue();
					resEntity = putPropertyVaule(resEntity, propertyLen, propertyValue);
				}
	
				if (fld == 64) {//
					len -= 8;
					offset += 8;
					continue;
				}
				if(fld==55){
					len -= itm.getValue().getBytes().length/2 + 2;
					offset += itm.getValue().getBytes().length/2 + 2;
					continue;
				}
				if (LengthType.APPOINT.equals(itm.getLengthType())) {// 定长
					if (DataType.N.equals(itm.getDataType()) && EncodeType.BCD.equals(itm.getEncodeType())) {// 数字,BCD编码
						len -= (itm.getLength() + 1) / 2;
						offset += (itm.getLength() + 1) / 2;
					} else {
						len -= itm.getLength();
						offset += itm.getLength();
					}
				} else if (LengthType.LLVAR.equals(itm.getLengthType())) {// LLVAR
					if (DataType.S.equals(itm.getDataType())&& EncodeType.ASCII.equals(itm.getEncodeType())) {
						if(fld==44){//44域特殊处理
							len -= itm.getValue().length()/2 + 1;
							offset += itm.getValue().length()/2 + 1;
						}else {
							len -= itm.getValue().length() + 1;
							offset += itm.getValue().length() + 1;	
						}
					} else {// 取的是BCD码
						len -= (itm.getValue().length() + 1) / 2 + 1;
						offset += (itm.getValue().length() + 1) / 2 + 1;
					}
				} else if (LengthType.LLLVAR.equals(itm.getLengthType())) { // LLLVAR
					if (DataType.S.equals(itm.getDataType()) && EncodeType.ASCII.equals(itm.getEncodeType())) {
						int charlen = 0;
						charlen = ISO8583Utile
								.getChinCharNumber(itm.getValue());
						if (charlen != 0) {// 针对银行返回的62域的第5子域有中文的情况
							len -= itm.getValue().length() + 2 + charlen;
							offset += itm.getValue().length() + 2 + charlen;
						} else {
							len -= itm.getValue().length() + 2;
							offset += itm.getValue().length() + 2;
						}
					} else {// 取的是BCD码
						len -= (itm.getValue().length() + 1) / 2 + 2;
						offset += (itm.getValue().length() + 1) / 2 + 2;
					}
				}

			}

		}
		if (len != 0) {
			if (StringUtil.notNull(resEntity.getResponseCode()) && !"00".equals(resEntity.getResponseCode())) {
				throw new Exception("error.parse.item.length");
			} else {
				throw new Exception("success.order.error.parse.item.length");
			}
		}

		return resEntity;
   }

   private static UnionPayBean putPropertyVaule(UnionPayBean unionPayBean, int len, String value) throws Exception {
		Map<Integer, String> fields = BocFieldMapping.fields;
		String property, pro, pro2, method;
		property = fields.get(len);
		
		pro = property.substring(0, 1).toUpperCase();
		pro2 = pro + property.substring(1);
		
		method = "set" + pro2;
		
		try {
			Class.forName("com.pay.pos.bean.UnionPayBean").getMethod(method, String.class).invoke(unionPayBean, value);				
		} catch (Exception e) {
			throw new Exception("convert.from.8583.to.unionpayparam.error");
		}
		return unionPayBean;
	}

	private static Field8583 DecodingItem(BocISO8583 bocISO8583, int fld, byte data[], int offset, int len) throws Exception {
		
		Field8583 itmdef = bocISO8583.getItemDef(fld);
		int maxlen = itmdef.getLength();
		int bytelen = 0;
		String str = "";
	     
	     if (LengthType.APPOINT.equals(itmdef.getLengthType())) {// 定长
				if (DataType.N.equals(itmdef.getDataType())
						&& EncodeType.BCD.equals(itmdef.getEncodeType())) {// 数字,BCD编码
					byte[] itemValue = new byte[(maxlen + 1) / 2];
					System.arraycopy(data, offset, itemValue, 0, (maxlen + 1) / 2);
					str = ISO8583Utile.bcd2String(itemValue, false);
					if (maxlen % 2 != 0) {
						str = str.substring(1);						
					}
				} else {
					bytelen = maxlen;
					if (fld == 64) {
						byte[] macbyte = new byte[8];
						System.arraycopy(data, offset, macbyte, 0, 8);
						str = ISO8583Utile.bytesToHexString(macbyte);
					} else {
						str = new String(data, offset, bytelen);
					}
				}				
			} else if (LengthType.LLVAR.equals(itmdef.getLengthType())) {// LLVAR
				byte[] itemValuelen = new byte[1];// 域长度BCD
				System.arraycopy(data, offset, itemValuelen, 0, 1);
				str = ISO8583Utile.bcd2String(itemValuelen, false);
				bytelen = Integer.parseInt(str);// 域真实长度
				if (DataType.S.equals(itmdef.getDataType())
						&& EncodeType.ASCII.equals(itmdef.getEncodeType())) {
					if(fld == 44){
						byte[] byte44 = new byte[bytelen];
						System.arraycopy(data, offset + 1, byte44, 0, bytelen);
						str = ISO8583Utile.bytesToHexString(byte44);
					}else {
						str = new String(data, offset + 1, bytelen);
					}
				} else {// 取的是BCD码
					byte[] itemValue = new byte[(bytelen + 1) / 2];
					System.arraycopy(data, offset + 1, itemValue, 0,
							(bytelen + 1) / 2);
					str = ISO8583Utile.bytesToHexString(itemValue);
					str = str.replace("D", "=");
					if (bytelen % 2 != 0) {
						str = str.substring(1, bytelen+1);
					}
				}
				
			} else if (LengthType.LLLVAR.equals(itmdef.getLengthType())) { // LLLVAR
				byte[] itemValuelen = new byte[2];// 域长度BCD
				System.arraycopy(data, offset, itemValuelen, 0, 2);
				str = ISO8583Utile.bcd2String(itemValuelen, false);
				bytelen = Integer.parseInt(str);// 域真实长度
				if (DataType.S.equals(itmdef.getDataType())
						&& EncodeType.ASCII.equals(itmdef.getEncodeType())) {
					byte[] itemValue = new byte[bytelen];
					System.arraycopy(data, offset + 2, itemValue, 0, bytelen);
					try {
						if(fld==56){
							str = new String(itemValue, "UTF-8");
						}else if(fld==55){
							str = ISO8583Utile.bytesToHexString(itemValue);
						}else{	
							str = new String(itemValue, "GBK");
						}	
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					byte[] bytereturn=new byte[bytelen];
					System.arraycopy(data, offset + 2, bytereturn, 0, bytelen);
					
				} else {// 取的是BCD码
					byte[] itemValue = new byte[(bytelen + 1) / 2];
					System.arraycopy(data, offset + 2, itemValue, 0,
							(bytelen + 1) / 2);
					str = ISO8583Utile.bytesToHexString(itemValue);
					str = str.replace("D", "=");
					if (bytelen % 2 != 0) {
						str = str.substring(1, bytelen+1);
					}
				}				
	     }else{
	    	 throw new Exception("error.parse.item.length");
	     }
	    	
	     Field8583 field8583 = new Field8583(fld,itmdef.getLength(), itmdef.getLengthType(),  itmdef.getDataType(),itmdef.getEncodeType());
		 field8583.setValue(str);
	     return field8583;
	}

	public Hashtable<FieldId, Field8583> getFieldDefines() {
		return ItemTbl;
	}
}
