package com.pay.sign.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.BitSet;
import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.common.util.EsscUtil;
import com.pay.common.util.ISO8583.DataType;
import com.pay.common.util.ISO8583.EncodeType;
import com.pay.common.util.ISO8583.Field8583;
import com.pay.common.util.ISO8583.FieldId;
import com.pay.common.util.ISO8583.FieldMapping;
import com.pay.common.util.ISO8583.ICNotSupportException;
import com.pay.common.util.ISO8583.ISO8583Utile;
import com.pay.common.util.ISO8583.LengthType;
import com.pay.common.util.ISO8583.POSISO8583Exception;
import com.pay.sign.Constant;
import com.pay.sign.bean.UnionPayBean;

/**
 * Title: ISO8583  工具类
 * Description: 
 * Copyright: Copyright (c)2015
 * Company: pay
 * @author zhongxiang.ren
 */

public class PosISO8583Common {
	
	private static final Log log=LogFactory.getLog(PosISO8583Common.class);
    static Hashtable<FieldId, Field8583> ItemTbl = null;
	Hashtable<FieldId, Field8583> fields = new Hashtable<FieldId, Field8583>();

	static {
		
		ItemTbl = new Hashtable<FieldId, Field8583>();
		
		/** initialize 8583's 128 field defination */
		
		ItemTbl.put(new FieldId(0),  new Field8583(0,4,  LengthType.APPOINT, DataType.N,EncodeType.BCD));		//信息类型
		ItemTbl.put(new FieldId(1),  new Field8583(1,8,  LengthType.APPOINT, DataType.B,EncodeType.BINARY));	//位图
		ItemTbl.put(new FieldId(2),  new Field8583(2,19, LengthType.LLVAR,   DataType.N,EncodeType.BCD));		//主账号
		  
		ItemTbl.put(new FieldId(3),  new Field8583(3,6,  LengthType.APPOINT, DataType.N,EncodeType.BCD));		//处理码
		ItemTbl.put(new FieldId(4),  new Field8583(4,12, LengthType.APPOINT, DataType.N,EncodeType.BCD));		//交易金额
		ItemTbl.put(new FieldId(11), new Field8583(11,6, LengthType.APPOINT, DataType.N,EncodeType.BCD));		//受卡方系统跟踪号
		
		ItemTbl.put(new FieldId(12), new Field8583(12,6, LengthType.APPOINT, DataType.N,EncodeType.BCD));		//受卡方所在地时间
		ItemTbl.put(new FieldId(13), new Field8583(13,4, LengthType.APPOINT, DataType.N,EncodeType.BCD));		//受卡方所在地日期
		ItemTbl.put(new FieldId(14), new Field8583(14,4, LengthType.APPOINT, DataType.N,EncodeType.BCD));		//卡有效期
		ItemTbl.put(new FieldId(15), new Field8583(15,4, LengthType.APPOINT, DataType.N,EncodeType.BCD));		//清算日期
		
		ItemTbl.put(new FieldId(22), new Field8583(22,3, LengthType.APPOINT, DataType.N,EncodeType.BCD));		//服务点输入方式码
		ItemTbl.put(new FieldId(23), new Field8583(23,3, LengthType.APPOINT, DataType.N,EncodeType.BCD));		//卡片序列号
		//ItemTbl.put(new FieldId( 24), new Field8583(24,3,LengthType.APPOINT,DataType.N,EncodeType.BCD));		//NII
		ItemTbl.put(new FieldId(25), new Field8583(25,2, LengthType.APPOINT, DataType.N,EncodeType.BCD));		//服务点条件码
		ItemTbl.put(new FieldId(26), new Field8583(26,2, LengthType.APPOINT, DataType.N,EncodeType.BCD));		//服务点PIN 获取码
		
		ItemTbl.put(new FieldId(32), new Field8583(32,11, LengthType.LLVAR,  DataType.N,EncodeType.BCD));		//受理方标识码
		ItemTbl.put(new FieldId(35), new Field8583(35,37, LengthType.LLVAR,  DataType.S,EncodeType.BCD));		//第二磁道数据
		ItemTbl.put(new FieldId(36), new Field8583(36,104,LengthType.LLLVAR, DataType.S,EncodeType.BCD));		//第三磁道数据
		ItemTbl.put(new FieldId(37), new Field8583(37,12, LengthType.APPOINT,DataType.S,EncodeType.ASCII));		//检索参考号
		ItemTbl.put(new FieldId(38), new Field8583(38,6,  LengthType.APPOINT,DataType.S,EncodeType.ASCII));		//授权标识应答码
		ItemTbl.put(new FieldId(39), new Field8583(39,2,  LengthType.APPOINT,DataType.S,EncodeType.ASCII));		//应答码
		
		ItemTbl.put(new FieldId(41), new Field8583(41,8,  LengthType.APPOINT,DataType.S,EncodeType.ASCII));		//终端编号
		ItemTbl.put(new FieldId(42), new Field8583(42,15, LengthType.APPOINT,DataType.S,EncodeType.ASCII));		//商户号
		ItemTbl.put(new FieldId(44), new Field8583(44,25, LengthType.LLVAR,  DataType.S,EncodeType.ASCII));		//附加响应数据
		ItemTbl.put(new FieldId(45), new Field8583(45,79, LengthType.LLVAR,  DataType.S,EncodeType.BCD));		//第一磁道数据
		ItemTbl.put(new FieldId(48), new Field8583(48,322,LengthType.LLLVAR, DataType.N,EncodeType.BCD));		//附加数据——私有
		ItemTbl.put(new FieldId(49), new Field8583(49,3,  LengthType.APPOINT,DataType.S,EncodeType.ASCII));		//*交易货币代码 
		ItemTbl.put(new FieldId(50), new Field8583(50,500,LengthType.LLLVAR, DataType.S,EncodeType.ASCII));		//附加数据
		
		ItemTbl.put(new FieldId(52), new Field8583(52,8, LengthType.APPOINT, DataType.B,EncodeType.BINARY));	//个人标识码数据
		ItemTbl.put(new FieldId(53), new Field8583(53,16,LengthType.APPOINT, DataType.N,EncodeType.BCD));		//安全控制信息		
		ItemTbl.put(new FieldId(54), new Field8583(54,20,LengthType.LLLVAR,  DataType.S,EncodeType.ASCII));		//附加金额
		ItemTbl.put(new FieldId(55), new Field8583(55,255,LengthType.LLLVAR,  DataType.S,EncodeType.ASCII));	//附加金额
		ItemTbl.put(new FieldId(56), new Field8583(56,100,LengthType.LLLVAR,  DataType.S,EncodeType.ASCII));	//错误信息
		
		ItemTbl.put(new FieldId(57), new Field8583(57,42,LengthType.LLLVAR, DataType.S,EncodeType.ASCII));		//自定义域
		ItemTbl.put(new FieldId(571),new Field8583(571,2, LengthType.APPOINT,DataType.S,EncodeType.ASCII));		//更新标志
		ItemTbl.put(new FieldId(572),new Field8583(572,20,LengthType.APPOINT, DataType.S,EncodeType.ASCII));	//软件版本号
		ItemTbl.put(new FieldId(573),new Field8583(573,20,LengthType.APPOINT, DataType.S,EncodeType.ASCII));	//参数版本号
		
		ItemTbl.put(new FieldId(59), new Field8583(59,160,LengthType.LLLVAR, DataType.S,EncodeType.ASCII));		//自定义域
		ItemTbl.put(new FieldId(591),new Field8583(591,3, LengthType.APPOINT,DataType.S,EncodeType.ASCII));		//类型标识码
		ItemTbl.put(new FieldId(592),new Field8583(592,50,LengthType.APPOINT, DataType.S,EncodeType.ASCII));	//商户订单号
		ItemTbl.put(new FieldId(593),new Field8583(593,100,LengthType.APPOINT, DataType.S,EncodeType.ASCII));	//商户名称
		
		
		ItemTbl.put(new FieldId(60), new Field8583(60,13,LengthType.LLLVAR, DataType.N,EncodeType.BCD));		//自定义域
		ItemTbl.put(new FieldId(601),new Field8583(601,2,LengthType.APPOINT, DataType.N,EncodeType.BCD));		//交易类型码
		ItemTbl.put(new FieldId(602),new Field8583(602,6,LengthType.APPOINT, DataType.N,EncodeType.BCD));		//批次号
		ItemTbl.put(new FieldId(603),new Field8583(603,3,LengthType.APPOINT, DataType.N,EncodeType.BCD));		//网络管理码
		ItemTbl.put(new FieldId(604),new Field8583(604,1,LengthType.APPOINT, DataType.N,EncodeType.BCD));		//终端读取能力
		ItemTbl.put(new FieldId(605),new Field8583(605,1,LengthType.APPOINT, DataType.N,EncodeType.BCD));		//基于PBOC 借/贷记标准的IC 卡条件代码
		ItemTbl.put(new FieldId(61), new Field8583(61,29,LengthType.LLLVAR,  DataType.N,EncodeType.BCD));		//原始信息域
		ItemTbl.put(new FieldId(611),new Field8583(611,6,LengthType.APPOINT, DataType.N,EncodeType.BCD));		//原批号
		ItemTbl.put(new FieldId(612),new Field8583(612,6,LengthType.APPOINT, DataType.N,EncodeType.BCD));		//原POS 流水号
		ItemTbl.put(new FieldId(613),new Field8583(613,4,LengthType.APPOINT, DataType.N,EncodeType.BCD));		//原交易日期
		ItemTbl.put(new FieldId(614),new Field8583(614,2,LengthType.APPOINT, DataType.N,EncodeType.BCD));		//原交易授权方式
		ItemTbl.put(new FieldId(615),new Field8583(615,11,LengthType.APPOINT,DataType.N,EncodeType.BCD));		//原交易授权机构代码
		ItemTbl.put(new FieldId(62), new Field8583(62,3000,LengthType.LLLVAR, DataType.S,EncodeType.ASCII));		//自定义域
		ItemTbl.put(new FieldId(63), new Field8583(63,163,LengthType.LLLVAR, DataType.S,EncodeType.ASCII));		//自定义域
		ItemTbl.put(new FieldId(631),new Field8583(631,3, LengthType.APPOINT,DataType.S,EncodeType.ASCII));		//国际信用卡公司代码
		ItemTbl.put(new FieldId(632),new Field8583(632,120,LengthType.LLVAR, DataType.S,EncodeType.ASCII));		//自定义域2
		ItemTbl.put(new FieldId(64), new Field8583(64,8,LengthType.APPOINT,  DataType.B,EncodeType.ASCII));		//报文鉴别码 MAC
		
		log.info("CommonPosiso8583 init 8583 defination table, size = " + ItemTbl.size());
	};
	
	public Hashtable<FieldId, Field8583> getFieldDefines() {
		return ItemTbl;
	}	
	
    public Field8583 getField(int index) throws ArrayIndexOutOfBoundsException {
		
		// 60，61，63这几个有子域的域直接跳过，不判断		
		if (index < 570) {
			if (index < Constant.FIELD_ID_START || index > Constant.FIELD_ID_END){
				throw new ArrayIndexOutOfBoundsException("invalid 8583 field id=" + index);
			}	
		}
		Field8583 field = (Field8583) fields.get(new FieldId(index));
		return field;
	}
	
	public Object Decoding(byte[] data, int len, UnionPayBean unionPayBean) {

		int bitmapLen = 0;
		int maxfields = 0;
		int offset = 0;

		// 检查信息类型
		if (len < Constant.LEN_POS_MESSAGETYPE) {
			log.info("CommonPosiso8583 Decoding parse message fail, data len less than MESSAGETYPE's length");
			throw new POSISO8583Exception(
					POSISO8583Exception.ERROR_PARSE_ITEM_LENGTH);
		}

		byte[] mesagetype = new byte[Constant.LEN_POS_MESSAGETYPE];
		System.arraycopy(data, offset, mesagetype, 0, Constant.LEN_POS_MESSAGETYPE);
		String message = ISO8583Utile.bcd2String(mesagetype, false);
		log.info("[Pos] Messagetype = " + message);

		if (!PosRetuMessType.getMtpResult(message)) {
			log.info("CommonPosiso8583 Decoding parse message fail, data MESSAGETYPE content error!");
			throw new POSISO8583Exception(
					POSISO8583Exception.ERROR_RESPONSE_ITEM_TPUD);
		}

		len -= Constant.LEN_POS_MESSAGETYPE;
		offset += Constant.LEN_POS_MESSAGETYPE;
		// macString.append(message);

		byte bitmapbase[] = new byte[Constant.LEN_BITMAP_BASE];
		System.arraycopy(data, offset, bitmapbase, 0, Constant.LEN_BITMAP_BASE);
		
		maxfields = Constant.LEN_BITMAP_BASE;
		len -= Constant.LEN_BITMAP_BASE;
		offset += Constant.LEN_BITMAP_BASE;

		if ((bitmapbase[0] & 0x80) != 0) {
			if (len < Constant.LEN_BITMAP_EXTENDED) {
				log.info("CommonPosiso8583 Decoding parse message fail, data len less than  bitmap's length");
				throw new POSISO8583Exception(
						POSISO8583Exception.ERROR_PARSE_ITEM_LENGTH);
			}
			maxfields = Constant.LEN_BITMAP_BASE + Constant.LEN_BITMAP_EXTENDED;
			bitmapLen = Constant.LEN_BITMAP_EXTENDED;
		}
		
		byte bitmapextend[] = new byte[bitmapLen];
		byte bitmapextendtype[] = new byte[bitmapLen];
		byte bitmap[] = new byte[maxfields];
		
		System.arraycopy(bitmapbase, 0, bitmap, 0, Constant.LEN_BITMAP_BASE);
		
		if (bitmapLen > 0) {
			System.arraycopy(data, offset, bitmapextend, 0, bitmapLen);
			
			//log.info("CommonPosiso8583 Request: offset = " + offset + ", bitmap = " + ISO8583Utile.Byte2Hex(bitmapextend, 0, bitmapLen));
			
			len -= Constant.LEN_BITMAP_EXTENDED;
			offset += Constant.LEN_BITMAP_EXTENDED;
			
			System.arraycopy(bitmapextendtype, 0, bitmap, Constant.LEN_BITMAP_BASE, Constant.LEN_BITMAP_EXTENDED);
		}

		bitmap = ISO8583Utile.string2Bcd(ISO8583Utile.Byte2Hex(bitmap, maxfields));
		
		//log.info("CommonPosiso8583 Request: offset=" + offset + ", bitmap=" + ISO8583Utile.Byte2Hex(bitmap, 0, maxfields));

		/**
		 * 解析所有的字段
		 */

		BitSet bs = ISO8583Utile.getBitMap(bitmap, maxfields);
		
		log.info("bs = " + bs);
		unionPayBean.setMti(message);// 信息类型
		Object copyObject = unionPayBean;

		Field8583 field = null;

		//log.info("field[index]: [data,maxLength,lengthType,dataType]" );
					
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

				field = DecodingItem(fld, data, offset, len);
												
				
				// 直接把解析后的值放入到银联对应的BIN中
				copyObject = putBeanbySpecialItem(fld, field.getValue(), copyObject);

				log.info("field[" + fld + "]: ["+ 
						field.getValue() + "," + 
						field.getLength() + "," +
						field.getLengthType() + "," + 
						field.getDataType() + "] ");
				
				if (fld == 52) {//
					len -= 8;
					offset += 8;
					continue;
				}
				if(fld == 62){
					len -= ISO8583Utile.hexStringToByte(field.getValue()).length + 2;
					offset += ISO8583Utile.hexStringToByte(field.getValue()).length + 2;
					continue;
				}

				if (LengthType.APPOINT.equals(field.getLengthType())) {// 定长
					if (DataType.N.equals(field.getDataType())
							&& EncodeType.BCD.equals(field.getEncodeType())) {// 数字,BCD编码
						len -= (field.getLength() + 1) / 2;
						offset += (field.getLength() + 1) / 2;
					} else {
						len -= field.getLength();
						offset += field.getLength();
					}
				} else if (LengthType.LLVAR.equals(field.getLengthType())) {// LLVAR
					if (DataType.S.equals(field.getDataType())
							&& EncodeType.ASCII.equals(field.getEncodeType())) {
						len -= field.getValue().length() + 1;
						offset += field.getValue().length() + 1;
					} else {// 取的是BCD码
						len -= (field.getValue().length() + 1) / 2 + 1;
						offset += (field.getValue().length() + 1) / 2 + 1;
					}
				} else if (LengthType.LLLVAR.equals(field.getLengthType())) { // LLLVAR
					if (DataType.S.equals(field.getDataType())
							&& EncodeType.ASCII.equals(field.getEncodeType())) {
						if(fld==55){
							len -= (field.getValue().length()+1 )/ 2 + 2;
							offset += (field.getValue().length()+1) / 2 + 2;	
						}else{
							len -= field.getValue().length() + 2;
							offset += field.getValue().length() + 2;
						}
					
					} else {// 取的是BCD码
						len -= (field.getValue().length() + 1) / 2 + 2;
						offset += (field.getValue().length() + 1) / 2 + 2;
					}
				}
			}
		}
		
		if (len != 0) {			
			log.info("CommonPosiso8583 Decoding parse message fail, decoding's length not equal package's length");			
			throw new POSISO8583Exception( POSISO8583Exception.ERROR_PARSE_ITEM_LENGTH);
		}

		log.info("CommonPosiso8583 Decoding Message Success!");
		
		return copyObject;
	}

	public byte[] Encoding() {
		boolean flag128 = false;
		Field8583 itm;
		byte[] bitmap = new byte[16];
		byte[] buf = new byte[Field8583.FLD_MAX_SIZE];// 用来存放从第2域开始的报文的整个字节数组
		byte[] itembytes = new byte[Field8583.FLD_MAX_SIZE];
		int len = 0;
		
		// processing from bitmap 2
		// 注意，必须从第2域开始。否则，如果用户把第1域（bitmap）放入，会出现错误。
		log.info("[Pos] Messagetype = " + getField(0).getValue());
		//log.info("field[index]: [data,maxLength,lengthType,dataType]" );
		
		int bitmapLen = 8;
		
		for (int i = 2; i <= Constant.FIELD_ID_END; i++) {
			itm = getField(i);
									
			if (i == 57 || i == 59 ||i == 60 || i == 61 || i == 63) {// 特殊的域单独做处理
				itm = putValue(i);
			}
			if (itm != null) {
				if (flag128 == false && i > 64) { // testing extend bitmap
					flag128 = true;
					bitmapLen = 16;
					bitmap[0] = (byte) (bitmap[0] | 0x80);
				}
				
				bitmap[(i - 1) / 8] = (byte) (bitmap[(i - 1) / 8] | (0x80 >>> ((i - 1) % 8)));
				
				if (i == 62) {
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
					
					//log.info("bitmap[" + i + "] = " + ISO8583Utile.bytesToHexString(itembytes));
					
				} else {
					itembytes = EncodingItem(itm);
				}
				System.arraycopy(itembytes, 0, buf, len, itembytes.length);
				len += itembytes.length;
				
				log.info("field[" + i + "]: ["+ 
						 itm.getValue() + "," + 
						 itm.getLength() + "," +
						 itm.getLengthType() + "," + 
						 itm.getDataType() + "] ");
			}			
		}

		String messageType = getField(0).getValue();// 消息类型
		int maclen = 0;
		int messagelen = messageType.length();
		int j = 64;
		
		//管理类交易不需要生成MAC
		if ("0810,0830,0510,0330".indexOf(messageType)<0) {
			
			byte[] macdata = new byte[len + messagelen / 2 + bitmapLen];
			bitmap[(j - 1) / 8] = (byte) (bitmap[(j - 1) / 8] | (0x80 >>> ((j - 1) % 8)));// 新的位图，说明64域存在
			System.arraycopy(ISO8583Utile.string2Bcd(messageType), 0, macdata, maclen, messagelen / 2);
			
			maclen += messagelen / 2;
			System.arraycopy(bitmap, 0, macdata, maclen, bitmapLen);
			
			maclen += bitmapLen;
			
			System.arraycopy(buf, 0, macdata, maclen, len);
			maclen += len;
						
			String makeMacString = ISO8583Utile.getXorString(macdata);// 异或后的十六进制字符串			
			String mac = null;
			
			try {
				mac = EsscUtils.createPosMac("pos."+getField(41).getValue()+".zak",makeMacString).substring(0, 8);
//				mac = "AAAAAAAA" ;
			} catch (Exception e) {
				try {
					mac = EsscUtil.createPosMac(getField(41).getValue(), makeMacString);
				} catch (ArrayIndexOutOfBoundsException e1) {
					mac = "AAAAAAAA";
				} catch (Exception e1) {
					mac = "AAAAAAAA";
				}
			}
			log.info("field[" + 64 + "]: ["+  mac.substring(0, 8) + ",8,APPOINT,B" + "] ");
			System.arraycopy(mac.substring(0, 8).getBytes(), 0, buf, len, 8);
			len += 8;
		}

		// 转换成8583内容格式
		// 转换成8583内容格式
		int datalen=len + messagelen / 2 + bitmapLen + Constant.LEN_POS_TPUD + Constant.LEN_POS_MESSAGE;
		int i = 0;
		byte[] data = new byte[datalen+Constant.LEN_CONTENT];

		byte[] lentype = new byte[2];
		String hexStr = Integer.toHexString(datalen);
		String lenString = ISO8583Utile.StringFillLeftZero(hexStr, 4);
		lentype = ISO8583Utile.string2Bcd(lenString);
		
		System.arraycopy(lentype, 0, data, i,	Constant.LEN_CONTENT);// 两位长度位
		i += Constant.LEN_CONTENT;
		
		System.arraycopy(ISO8583Utile.string2Bcd(Constant.CONTENT_POS_TPUD), 0, data, i,	Constant.LEN_POS_TPUD);// 报文头
		i += Constant.LEN_POS_TPUD;
		
		System.arraycopy(ISO8583Utile.string2Bcd(Constant.CONTENT_POS_Message), 0, data,	i, Constant.LEN_POS_MESSAGE);// 认证
		i += Constant.LEN_POS_MESSAGE;
		
		System.arraycopy(ISO8583Utile.string2Bcd(messageType), 0, data, i,	messagelen / 2);
		i += messagelen / 2;
		
		System.arraycopy(bitmap, 0, data, i, bitmapLen);
		i += bitmapLen;
		
		System.arraycopy(buf, 0, data, i, len);
		i += len;
		
		
		//log.info("data:" + ISO8583Utile.bytesToHexString(data) + "]");

		return (data);
	}		   
	
	private byte[] EncodingItem(Field8583 itm) throws POSISO8583Exception {

		int iMaxLen; 			// item content max length
		int iSrcLen=0; 			// item content actual length
		int iDestLen; 			// encoded string length
		String sEncoded = ""; 	// encoded item string
		String strLen;
		String sValue = itm.getValue();

		int data_len = 0;
		byte[] data = new byte[Field8583.FLD_MAX_SIZE];

			try {
				iSrcLen = itm.getValue().getBytes("gb2312").length;
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		iMaxLen = itm.getLength();			// 域数据是大长度

		/**
		 * Variable length items Coding rule: LLVAR: two bytes in ascii as
		 * length, maximal value is 99 = 0x39 0x39, LLLVAR, maximal value is 999 =
		 * 0x39 0x39 0x39
		 */
		
		byte[] len = null;
		byte[] contenbyte = null;
		if (LengthType.LLVAR.name().equals(itm.getLengthType().name())) { // LLVAR
			
			strLen = ISO8583Utile.int2char2(iSrcLen);
			len = ISO8583Utile.string2Bcd(strLen);// 域长度BCD码
			
			if (EncodeType.BCD.equals(itm.getEncodeType())) {// BCD编码方式（左靠）
				if (iSrcLen % 2 != 0) {// 右补0
					sValue = ISO8583Utile.StringFillRightBlank(sValue,
							iSrcLen + 1);
				}
				contenbyte = ISO8583Utile.string2Bcd(sValue);// 域值
			} else {// 字符型（目前Binary没有变长，所以不考虑）
				try {
					contenbyte = sValue.getBytes("gb2312");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			System.arraycopy(len, 0, data, data_len, 1);
			data_len += 1;
			System.arraycopy(contenbyte, 0, data, data_len, contenbyte.length);
			data_len += contenbyte.length;

		} else if (LengthType.LLLVAR.name().equals(itm.getLengthType().name())) { // LLVAR
			
			strLen = ISO8583Utile.int2char3(iSrcLen);
			len = ISO8583Utile.string2Bcd(strLen);// 域长度BCD码
			
			if (EncodeType.BCD.equals(itm.getEncodeType())) {// BCD编码方式（左靠）
				if (iSrcLen % 2 != 0) {// 右补0
					sValue = ISO8583Utile.StringFillRightBlank(sValue,
							iSrcLen + 1);
				}
				contenbyte = ISO8583Utile.string2Bcd(sValue);// 域值
			} else {// 字符型（目前Binary没有变长，所以不考虑）
				try {
					contenbyte = sValue.getBytes("gb2312");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			System.arraycopy(len, 0, data, data_len, 2);
			data_len += 2;
			System.arraycopy(contenbyte, 0, data, data_len, contenbyte.length);
			data_len += contenbyte.length;

		} else if (LengthType.APPOINT.name().equals(itm.getLengthType().name())) { // fix
			
			iDestLen = iMaxLen;
			
			if (DataType.N.name().equals(itm.getDataType().name())) { // data
																		
				if (iDestLen % 2 != 0) {
					sEncoded = ISO8583Utile.StringFillRightBlank(sValue,
							iDestLen + 1);
				} else {
					sEncoded = ISO8583Utile.StringFillLeftZero(sValue, iDestLen);
				}

				if (EncodeType.BCD.equals(itm.getEncodeType())) {// BCD
					data = ISO8583Utile.string2Bcd(sEncoded);
					data_len += (iMaxLen + 1) / 2;
				}
			} else { // data type S,B(binary在PUT之前就要转换成8个字节的数组)
				sEncoded = ISO8583Utile.StringFillRightBlank(sValue, iDestLen);
				try {
					data = sEncoded.getBytes("gb2312");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				data_len += iMaxLen;
			}
		} else {
			log.info("item " + itm.getId() + " variable flag invalid! " + 
					"data=" + itm.getValue() + 
					", datetype=" + itm.getDataType().name());
		}

		byte[] returnbyte = new byte[data_len];
		System.arraycopy(data, 0, returnbyte, 0, data_len);

		return returnbyte;
	}
	
	private static Field8583 DecodingItem(int fld,
			byte data[], int offset, int len) throws POSISO8583Exception,ICNotSupportException{
		
		Field8583 itmdef = null;
		try{
			itmdef = getItemDefine(fld);
		}catch(ICNotSupportException e){
			throw new ICNotSupportException("CommonPosiso8583 DecodingItem NOT SUPPORT IC!");
		}

		int maxlen = itmdef.getLength();
		int bytelen = 0;
		String str = "";

		if (LengthType.APPOINT.equals(itmdef.getLengthType())) {// 定长
			if (DataType.N.equals(itmdef.getDataType())
					&& EncodeType.BCD.equals(itmdef.getEncodeType())) {// 数字,BCD编码
				byte[] itemValue = new byte[(maxlen + 1) / 2];
				System.arraycopy(data, offset, itemValue, 0, (maxlen + 1) / 2);
				str = ISO8583Utile.bcd2String(itemValue, false);
				if (maxlen % 2 != 0) {// 右补0的情况
					str = str.substring(0, maxlen);
				}
			} else {
				bytelen = maxlen;
				if (fld == 52) {// 52密码码单独处理
					byte[] pinbyte = new byte[8];
					System.arraycopy(data, offset, pinbyte, 0, 8);
					str = ISO8583Utile.bytesToHexString(pinbyte);
				} else {
					str = new String(data, offset, bytelen);
				}
				// str=new String(data, offset, bytelen);
			}
		} else if (LengthType.LLVAR.equals(itmdef.getLengthType())) {// LLVAR
			
			byte[] itemValuelen = new byte[1];// 域长度BCD
			System.arraycopy(data, offset, itemValuelen, 0, 1);
			str = ISO8583Utile.bcd2String(itemValuelen, false);
			bytelen = Integer.parseInt(str);// 域真实长度
			
			if (DataType.S.equals(itmdef.getDataType())
					&& EncodeType.ASCII.equals(itmdef.getEncodeType())) {
				str = new String(data, offset + 1, bytelen);
			} else {// 取的是BCD码(左靠)
				byte[] itemValue = new byte[(bytelen + 1) / 2];
				System.arraycopy(data, offset + 1, itemValue, 0,(bytelen + 1) / 2);
				str = ISO8583Utile.bytesToHexString(itemValue);
				str = str.replace("D", "=");
				if (bytelen % 2 != 0) {
					str = str.substring(0, bytelen);
				}
			}
		} else if (LengthType.LLLVAR.equals(itmdef.getLengthType())) { // LLLVAR
			
			byte[] itemValuelen = new byte[2];// 域长度BCD
			System.arraycopy(data, offset, itemValuelen, 0, 2);
			str = ISO8583Utile.bcd2String(itemValuelen, false);
			bytelen = Integer.parseInt(str);// 域真实长度
			
			if(fld == 62|| fld==55){
				
				byte[] xxxByte = new byte[bytelen];
				System.arraycopy(data, offset + 2, xxxByte, 0, bytelen);
				str = ISO8583Utile.bytesToHexString(xxxByte);
			}
			else if (DataType.S.equals(itmdef.getDataType())
					&& EncodeType.ASCII.equals(itmdef.getEncodeType())) {
				str = new String(data, offset + 2, bytelen);
			} 
			else {// 取的是BCD码(左靠)
				byte[] itemValue = new byte[(bytelen + 1) / 2];
				System.arraycopy(data, offset + 2, itemValue, 0,(bytelen + 1) / 2);
				str = ISO8583Utile.bytesToHexString(itemValue);
				str = str.replace("D", "=");
				
				if (bytelen % 2 != 0) {
					str = str.substring(0, bytelen);
				}
			}
		} else {
			log.info("CommonPosiso8583 DecodingItem ENCODE field " + itmdef.getId()
					+ " isn't defined in defination table");
			throw new POSISO8583Exception(
					POSISO8583Exception.ERROR_PARSE_ITEM_LENGTH);
		}

		// 判断取到的域的值是否与定义的数据类型一致
		if (itmdef.getDataType() == DataType.N) { // 检查数据是否为有效的数字串,MAC码和位图的暂不作判断
			if (!ISO8583Utile.isValidDecString(str)) {
				log.info("CommonPosiso8583 DecodingItem ENCODE Item " + fld
						+ "'s datatype[" + itmdef.getDataType() + "]is error");
				throw new POSISO8583Exception(
						POSISO8583Exception.ERROR_ITEM_DATA_TYPE_ERROR);
			}
		}

	/*	if (itmdef.getDataType() == DataType.S) {// 检查字符串域值中是不是包含有汉字，MAC码和位图的暂不作判断
			if (str.getBytes().length != str.length()) {
				log.info("POSISO8583 DecodingItem ENCODE Item " + fld
						+ "'s datatype[" + itmdef.getDataType() + "] is error");
				throw new POSISO8583Exception(
						POSISO8583Exception.ERROR_ITEM_DATA_TYPE_ERROR);
			}
		}*/

		Field8583 field8583=new Field8583(fld,itmdef.getLength(), itmdef.getLengthType(),  itmdef.getDataType(),itmdef.getEncodeType());
	     field8583.setValue(str);
		return field8583;
	}
	
	public Field8583 putValue(int index) throws POSISO8583Exception {// 对于有子域的域，把子域的值拼接好后放入到对应的域中
		Field8583 field;
		Field8583 fieldDefine = getItemDefine(index);
		StringBuffer str = new StringBuffer();
		boolean key = false;
		for (int j = index * 10 + 1; j <= index * 10 + 5; j++) {
			field = getField(j);
			if (field != null) {
				key = true;
				if (DataType.S.equals(field.getDataType())&& EncodeType.ASCII.equals(field.getEncodeType())) {//针对57和59需右补空格的情况
					str.append(ISO8583Utile.StringFillRightBlankReal(field.getValue(),field.getLength()));
				} else {// 取的是BCD码(左靠)
					str.append(field.getValue());
				}
			}
		}
		if (!key) {
			return null;
		}
		
		Field8583 field8583=new Field8583(index,fieldDefine.getLength(), fieldDefine.getLengthType(),  fieldDefine.getDataType(),fieldDefine.getEncodeType());
		field8583.setValue(str.toString());
	
		fields.put(new FieldId(index), field8583);
		return getField(index);
	}
		
	public static Object putBeanbySpecialItem(int fld, String value,
			Object object) {// 目前这三个域比较特殊
		Object copyObject = object;
		int newfld = 0;
		int startlen = 0;

		Field8583 field8583 = null;
		String realValue = value;// 域的值

		if (fld == 57 || fld == 59 ||fld == 60 || fld == 61 || fld == 63) {// 目前只考虑主域和子域编码一样的情况****
			for (int i = fld * 10 + 1; i < fld * 10 + 6; i++) {
				field8583 = getItemDefine(i);
				if (field8583 != null
						&& field8583.getLength() <= realValue.length()) {// 域的长度要大于子域的长度
					if (LengthType.APPOINT.equals(field8583.getLengthType())) {// 定长
						String objectvalue=realValue.substring(startlen, startlen + field8583.getLength());
						objectvalue=ISO8583Utile.RemoveRightBlank(objectvalue);//针对57和59右补空格的情况
						copyObject = putBeanbyItem(i,objectvalue ,copyObject);
						startlen += field8583.getLength();
					} else {// 变长(目前只有63.2)
						if (LengthType.LLVAR.equals(field8583.getLengthType())) {// 两位变长
							newfld = Integer.parseInt(realValue.substring(
									startlen, startlen + 2));
							copyObject = putBeanbyItem(i, realValue.substring(
									startlen + 2, startlen + 2 + newfld),
									copyObject);
							startlen += newfld + 2;
						} else {// 三位变长
							newfld = Integer.parseInt(realValue.substring(
									startlen, startlen + 3));
							copyObject = putBeanbyItem(i, realValue.substring(
									startlen + 3, startlen + 3 + newfld),
									copyObject);
							startlen += newfld + 3;
						}
					}
					if (realValue.length() == startlen) {
						break;
					}
				}
			}
		} else {
			copyObject = putBeanbyItem(fld, realValue, copyObject);
		}
		return copyObject;
	}
		   
	public static Object putBeanbyItem(int fld, String value, Object object) {
		Object copyObject = object;
		// 获得对象的类型
		Class<?> classType = copyObject.getClass();
		// 获得对象的所有属性
		Field[] fields = classType.getFields();
		for (int k = 0; k < fields.length; k++) {
			String fieldName = fields[k].getName();
			if (FieldMapping.fields.get(fld) == fieldName) {
				// 获得和属性对应的setXXX()方法的名字
				String setMethodName = "set"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);
				// 获得和属性对应的setXXX()方法
				Method setMethod = null;
				try {
					setMethod = classType.getMethod(setMethodName,
							new Class[] { fields[k].getType() });
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 调用拷贝对象的setXXX()方法
				try {
					setMethod.invoke(copyObject, new Object[] { value });
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return copyObject;
	}
	
	private static Field8583 getItemDefine(int index) throws ArrayIndexOutOfBoundsException,ICNotSupportException{
		Field8583 field = (Field8583) ItemTbl.get(new FieldId(index));
//		if(index==55){
//			throw new ICNotSupportException("CommonPosiso8583 not support IC!");
//		}
		if (field == null) {
			throw new ArrayIndexOutOfBoundsException("CommonPosiso8583 field not define, invalid 8583 field id = " + index);
		}
		return field;
	}
	
	public void putField(int index, String data) throws POSISO8583Exception {
		
		// 60，61，63这几个有子域的域直接跳过，不判断
		if (index < 570) {
			if (index < Constant.FIELD_ID_START || index > Constant.FIELD_ID_END) {				
				log.info("CommonPosiso8583 putItem invalid 8583 field id=" + index);
				throw new POSISO8583Exception( POSISO8583Exception.ERROR_ITEM_OUT_OF_RANGE);
			}
		}
		
		Field8583 field = getItemDefine(index);
		
		if (field == null) {
			log.info("CommonPosiso8583 putItem Item " + index	+ ", isn't defined in 8583 table");
			throw new POSISO8583Exception( POSISO8583Exception.ERROR_ITEM_NOT_DEFINED);
		}	
		
		// 检查数据的长度是否合法
		int databyteLen=0;
		try {
			databyteLen = data.getBytes("gb2312").length;
		} catch (UnsupportedEncodingException e) {
			log.error("Field index=" + index, e);
		}
		if (databyteLen> field.getLength()) {
			log.info("CommonPosiso8583 putItem Item " + index	+ ", this data length is longer than defined in 8583 table");
			throw new POSISO8583Exception( POSISO8583Exception.ERROR_ITEM_DATA_TOO_LONG);
		}

		if (field.getDataType() == DataType.N) { // 检查数据是否为有效的数字串, MAC码和位图的暂不作判断
			if (!ISO8583Utile.isValidDecString(data)) {
				log.info("CommonPosiso8583 putItem Item " + index + "'s datatype[" + field.getDataType() + "]is error");
				throw new POSISO8583Exception( POSISO8583Exception.ERROR_ITEM_DATA_TYPE_ERROR);
			}
		}

		/*if (field.getDataType() == DataType.S) {// 检查字符串域值中是不是包含有汉字，MAC码和位图的暂不作判断
			if (data.getBytes().length != data.length()) {
				log.info("POSISO8583 putItem Item " + index + "'s datatype[" + field.getDataType() + "] is error");
				throw new POSISO8583Exception( POSISO8583Exception.ERROR_ITEM_DATA_TYPE_ERROR);
			}
		}*/

		Field8583 field8583=new Field8583(index,field.getLength(), field.getLengthType(),  field.getDataType(),field.getEncodeType());
		field8583.setValue(data);
		fields.put(new FieldId(index), field8583);
	}

	public byte[] Encoding(byte[] date) {
		return null;
	}

			
}
