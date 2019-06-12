package com.yl.pay.pos.interfaces.common.util.ISO8583;

import java.util.BitSet;
import java.util.Hashtable;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.common.util.ISO8583.DataType;
import com.pay.common.util.ISO8583.ISO8583Utile;
import com.pay.common.util.ISO8583.LengthType;
import com.yl.pay.pos.bean.UnionPayBean;


/**
 * Title: 通用报文基础类
 * Description:
 * Copyright: Copyright (c)2011
 * Company: pay
 * @author qiuzhl
 */

public abstract class ISO8583Message
{
	private static Log log = LogFactory.getLog(ISO8583Message.class);
	public final static int LEN_BITMAP_BASE = 8;			//主位图的长度
	public final static int LEN_BITMAP_EXTENDED = 8;		//扩展位图的长度
	Hashtable<Integer, ISO8583Field> fields = new Hashtable<Integer, ISO8583Field>();
	private String className = getClass().getSimpleName();
	
	public final UnionPayBean getUnionPayBean()
	{
		Iterator<Integer> it = fields.keySet().iterator();
		UnionPayBean resEntity = new UnionPayBean();
		while(it.hasNext())
		{
			int id = it.next();
			resEntity = putPropertyVaule(resEntity, id, fields.get(id).getStringValue());
		}
		
//		resEntity.setOrgIso8583Msg(ISO8583Utile.bytesToHexString(dataOfRaw));
		return resEntity;
	}
	
	public void putItem(int id, String data) {

		if(data == null) return ;
		
		checkFieldIdValidity(id);
		
		ISO8583Field itmdef = getFieldDefinition(id);
		if (id <= 128 && itmdef == null)
		{
			throw new RuntimeException("["+className+"]field not defined, fieldId="+id);
		}

		// 检查数据的长度是否合法
//		if (data.length() > itmdef.getMaxLength())
//			throw new IllegalArgumentException("["+className+"]item data larger than field definition,[id,datalength,itemlength,datavalue]=["+id+","+data.length()+","+itmdef.getMaxLength()+","+data+"]");

		ISO8583Field field8583 = new ISO8583Field(id, itmdef.getMaxLength(), itmdef.getLengthType(), itmdef.getDataType(), itmdef.getEncodeType());
		field8583.setStringValue(data);
		fields.put(id, field8583);
	}
	
	
	public ISO8583Field getItem(int fld)  {
		checkFieldIdValidity(fld);
		ISO8583Field itm = (ISO8583Field) fields.get(fld);
		return itm;
	}
	
	protected boolean hasExtendBitMap()
	{
		return false;
	}
	
	/**
	 * 对位图域以外的其他域数据进行编码，返回值为 消息类型 + 位图 + 域数据
	 * @return
	 */
	protected final byte[] encodeItems()
	{
		int bitmapLen = hasExtendBitMap() ? 16: 8;
		int maxFieldsLength = bitmapLen * 8;
		byte[] bitmap = new byte[bitmapLen];
		bitmap[0] = (byte) (hasExtendBitMap() ? 0x80 : 0x00);
		
		byte[] buffer = new byte[ISO8583Field.FLD_MAX_SIZE];// 用来存放从第2域开始的报文的整个字节数组
		int length = 0;
		
		// 注意，必须从第2域开始。否则，如果用户把第1域（bitmap）放入，会出现错误。	
		for (int i = 2; i <= maxFieldsLength; i++)
		{
			int newlength = encodingItem(i,buffer,length);
			if (newlength > 0)
			{
				bitmap[(i - 1) / 8] = (byte) (bitmap[(i - 1) / 8] | (0x80 >>> ((i - 1) % 8)));
				length += newlength;
			}
		}		
		
		ISO8583Field messageTypeField = getItem(0);
		String messagetype = ISO8583Utile.StringFillLeftZero(messageTypeField.getStringValue(),messageTypeField.getMaxLength());// 消息类型		
		int messageTypeLength = (messageTypeField.getMaxLength() + 1) / 2;
		
		//消息类型 + 位图 + 域数据
		byte[] data = new byte[messageTypeLength + bitmapLen + length];
		
		int i = 0;		
		
		System.arraycopy(ISO8583Utile.string2Bcd(messagetype), 0, data, i, messageTypeLength );//消息类型 
		i += messageTypeLength;

		if(log.isDebugEnabled())
		{
			log.debug("["+className+"]mti = " + messagetype);
			String bitmapHex = ISO8583Utile.Byte2Hex(bitmap, 0, bitmapLen);
			log.debug("["+className+"]bitmap[hex] = " + bitmapHex);
		}
				
		System.arraycopy(bitmap, 0, data, i, bitmapLen);
		i += bitmapLen;
		
		System.arraycopy(buffer, 0, data, i, length);
		
		return (data);
	}
	
	/**
	 * 解析8583报文内容，待解析的内容由三个参数确定，内容只能包含消息类型 + 位图 + 域数据
	 * @param data 存放8583报文内容的缓存
	 * @param offset 位图在8583报文中的开始位置
	 * @param length 8583报文位图+域数据的长度
	 * @return
	 */
	protected final void decode(byte[] data, int offset, int length)
	{
		if(log.isDebugEnabled())
		{
			log.debug("["+className+"]data[size,offset,length]=["+data.length + "," + offset + "," + length + "]");
		}
		ISO8583Field messageTypeField = getEmptyField(0);
		int messageTypeLength = (messageTypeField.getMaxLength() + 1) / 2;
		if(length < (messageTypeLength + LEN_BITMAP_BASE))
		{//每个8583报文肯定包含主位图，如果报文长度小于此值，说明报文一定有问题；
			throw new ISO8583RuntimeException("["+className+"]error.response.item.message.length:" + length);
		}
		
	    // 检查信息类型		
		int datalength = decodingItem(messageTypeField, 0, data, offset); 
		if(log.isDebugEnabled())
		{
			log.debug("["+className+"]mti=" + messageTypeField.getStringValue());
		}
		
		putItem(0, messageTypeField.getStringValue());
		
		length -= datalength;
		offset += datalength;
		
		int maxfields = LEN_BITMAP_BASE;
		byte[] bitmap= null;
		//此时的offset指向位图的开始位置，测试位图的第1位是否为1，如果为1，则表示有扩展位图，否则只有主位图
		if ((data[offset] & 0x80) != 0)
		{
			//检查是否够主位图+扩展位图的长度
			if (length < (LEN_BITMAP_BASE + LEN_BITMAP_EXTENDED))
			{
				log.error("["+className+"]data length error:" + length + ",data(Hex)=" + ISO8583Utile.bytesToHexString(data));
				throw new ISO8583RuntimeException("ata length error:" + length);
			}
			maxfields = LEN_BITMAP_BASE + LEN_BITMAP_EXTENDED;
		}

		bitmap = new byte[maxfields];
		System.arraycopy(data, offset, bitmap, 0, maxfields);	
		BitSet bs = ISO8583Utile.getBitMap(bitmap, maxfields);	
		
		if(log.isDebugEnabled())
		{
			log.debug("["+className+"]bitmap = " + bs);
		}
		
		length -= maxfields;
		offset += maxfields;
		
		ISO8583Field item = null;
		
		for (int fld = 2; fld <= maxfields * 8; fld++)
		{
			try
			{
				if(bs.get(fld) == false) continue;
				item = getEmptyField(fld);
				datalength = decodingItem(item, fld, data, offset);
				
				if(log.isDebugEnabled())
				{
			    	String s = String.format("[%03.0f][%03d]=[%s]", item.getId(),item.getStringValue().length(),item.getStringValue());
			    	log.debug(s);
				}

				putItem(fld, item.getStringValue());

				length -= datalength;
				offset += datalength;
			}
			catch(ISO8583RuntimeException e)
			{
				log.error("["+className+".decode] catch an exception when decode field " + fld + ",data(Hex)=" + ISO8583Utile.bytesToHexString(data) + ",error info : " + e.getMessage());
				throw e;
			}
		}//for (int fld = 1; fld < maxfields; fld++)
		
		if (length != 0)
		{
			throw new RuntimeException("["+className+"]error.response.item.message.length:"+length);
		}
	}
	
	protected int encodingItem(int fieldId, byte[] buf, int offset) 
	{
		ISO8583Field item = getItem(fieldId);
		int newlength = 0;
		if (item != null)
		{
			newlength = encodingItem(item,buf,offset);
		}
		return newlength;
	}
	
	private int encodingItem(ISO8583Field item, byte[] data, int offset) 
	{
		 String value = item.getStringValue(); //encoded item string
		
		 int  data_length = 0;
		     
		 byte[] lengthByte = null;
		 byte[] contenbyte = null;
		 
		 if (LengthType.LLVAR.equals(item.getLengthType())) 
		 {// LLVAR
			 if(EncodeType.HEX.equals(item.getEncodeType()))
			 {
				 lengthByte = ISO8583Utile.string2Bcd( ISO8583Utile.int2char2((value.length()+1)/2));// 域长度BCD码
			 }
			 else
			 {
				 lengthByte = ISO8583Utile.string2Bcd( ISO8583Utile.int2char2(value.length()) );// 域长度BCD码
			 }
		 } 
		 else if (LengthType.LLLVAR.equals(item.getLengthType())) 
		 {// LLLVAR
			 if(EncodeType.HEX.equals(item.getEncodeType()))
			 {
				 lengthByte = ISO8583Utile.string2Bcd( ISO8583Utile.int2char3((value.length()+1)/2));// 域长度BCD码
			 }
			 else
			 {
				 lengthByte = ISO8583Utile.string2Bcd( ISO8583Utile.int2char3(value.length()) );// 域长度BCD码
			 }
		 }
		 else if(LengthType.APPOINT.equals(item.getLengthType())) 
		 {
			 //do nothing
		 }
		 else
		 {
			 String msg = "["+className+"]Unsupported DataLengthType, fieldId= " + item.getId()+ ",dataLengthType=" + item.getDataType().name();
		     log.error(msg);
		     throw new RuntimeException(msg);
		 }
		 
		 if (EncodeType.BCD_LEFT_FILL_ZERO.equals(item.getEncodeType())) 
		 {
			 int vlength = LengthType.APPOINT.equals(item.getLengthType()) ? item.getMaxLength() : value.length();
			 value = ISO8583Utile.StringFillLeftZero(value,(vlength+1)/2*2);
			 contenbyte = ISO8583Utile.string2Bcd(value);
		 }
		 else if (EncodeType.BCD_RIGHT_FILL_ZERO.equals(item.getEncodeType())) 
		 {
			 int vlength = LengthType.APPOINT.equals(item.getLengthType()) ? item.getMaxLength() : value.length();
			 value = ISO8583Utile.StringFillRightZero(value,(vlength+1)/2*2);
			 contenbyte = ISO8583Utile.string2Bcd(value);
		 }
		 else if (EncodeType.ASCII.equals(item.getEncodeType()))
		 {
			 if(LengthType.APPOINT.equals(item.getLengthType()))
			 {
				 value = ISO8583Utile.StringFillRightBlank(value,item.getMaxLength());
			 }
			 contenbyte = value.getBytes();
		 }
		 else if (EncodeType.BINARY.equals(item.getEncodeType()))
		 {
			 contenbyte = value.getBytes();
		 }
		 else if (EncodeType.HEX.equals(item.getEncodeType()))
		 {
			 contenbyte = ISO8583Utile.hexStringToByte(value);
		 }
		 else
		 {
			 String msg = "["+className+"]Unsupported EncodeType, fieldId= " + item.getId()+ ",EncodeType=" + item.getEncodeType().name();
		     log.error(msg);
		     throw new RuntimeException(msg);
		 }
		 
	     if(lengthByte != null)
	     {
			System.arraycopy(lengthByte, 0, data, offset, lengthByte.length);
			data_length += lengthByte.length;
	     }
		 
	     System.arraycopy(contenbyte, 0, data, offset + data_length, contenbyte.length);
	     data_length += contenbyte.length;
		
	     if (item != null && log.isDebugEnabled())
	     {
	    	 
	    	 String s = String.format("[%03.0f][%03d]=[%s]", item.getId(),item.getStringValue().length(),item.getStringValue());
	    	 log.debug(s);
//			 log.debug("[]=[" +   + ","+ item.getMaxLength() + "," + item.getLengthType() + "," + item.getDataType() + "," + item.getEncodeType() +"," + offset +"," + data_length + "," + item.getStringValue()+ "] ");
		 }
	     log.info("field[" + item.getId() + "]: ["+ 
	    		 item.getStringValue() + "," + 
	    		 item.getEncodeType()  + "] ");
         return data_length;
    }
	
	protected int decodingItem(ISO8583Field field8583, int fld, byte data[], int offset)
	{
		ISO8583Field itmdef = getFieldDefinition(fld);
		int dataoffset = offset;
		int datalength = 0;
		String s = "";		
		if (LengthType.APPOINT.equals(itmdef.getLengthType()))
		{// 定长
			datalength = itmdef.getMaxLength();
		}
		else if(LengthType.LLVAR.equals(itmdef.getLengthType())) 
	    {// LLVAR
			byte[] itemValuelen = new byte[1];// 域长度BCD
			System.arraycopy(data, offset, itemValuelen, 0, 1);
			s = ISO8583Utile.bcd2String(itemValuelen, false);
			datalength = Integer.parseInt(s);// 域真实长度
			dataoffset += 1;
	    }
		else if (LengthType.LLLVAR.equals(itmdef.getLengthType()))
	    { // LLLVAR
			byte[] itemValuelen = new byte[2];// 域长度BCD
			System.arraycopy(data, offset, itemValuelen, 0, 2);
			s = ISO8583Utile.bcd2String(itemValuelen, false);
			datalength = Integer.parseInt(s);// 域真实长度
			dataoffset +=2;
	    }
		
		if (EncodeType.BCD_LEFT_FILL_ZERO.equals(itmdef.getEncodeType())) 
		{//BCD编码
			byte[] itemValue = new byte[(datalength + 1) / 2];
			System.arraycopy(data, dataoffset, itemValue, 0, itemValue.length);
			if(DataType.N.equals(itmdef.getDataType()))
			{
				s = ISO8583Utile.bcd2String(itemValue, false);
			}
			else
			{
				s = ISO8583Utile.bytesToHexString(itemValue);
			}
			
			if(fld == 35 || fld == 36)
			{
				s = s.replace("D", "=");
			}
			
			if (datalength % 2 != 0)
			{//处理左补零的情况
				s = s.substring(1);
			}
			datalength = itemValue.length;//为了返回时正确计算本次一共读取了多少字节的数据
		}
		else if (EncodeType.BCD_RIGHT_FILL_ZERO.equals(itmdef.getEncodeType())) 
		{//BCD编码
			byte[] itemValue = new byte[(datalength + 1) / 2];
			System.arraycopy(data, dataoffset, itemValue, 0, itemValue.length);
			if(DataType.N.equals(itmdef.getDataType()))
			{
				s = ISO8583Utile.bcd2String(itemValue, false);
			}
			else
			{
				s = ISO8583Utile.bytesToHexString(itemValue);
			}
			
			if(fld == 35 || fld == 36)
			{
				s = s.replace("D", "=");
			}			
			if (datalength % 2 != 0)
			{//处理右补零的情况
				s = s.substring(0,datalength);
			}
			datalength = itemValue.length;//为了返回时正确计算本次一共读取了多少字节的数据
		}
		else if(EncodeType.ASCII.equals(itmdef.getEncodeType()))
		{
			s = decodeItemValue(fld,data,dataoffset,datalength);
		}
		else if(EncodeType.BINARY.equals(itmdef.getEncodeType()))
		{
			byte[] itemValue = new byte[datalength];
			System.arraycopy(data, dataoffset, itemValue, 0, itemValue.length);
			s = new String(itemValue);
			datalength = itemValue.length;
		}
		else if(EncodeType.HEX.equals(itmdef.getEncodeType()))
		{
			byte[] itemValue = new byte[datalength];
			System.arraycopy(data, dataoffset, itemValue, 0, itemValue.length);
			s = ISO8583Utile.bytesToHexString(itemValue);
			datalength = itemValue.length;
		}
	    
		field8583.setMaxLength(itmdef.getMaxLength());
		field8583.setLengthType(itmdef.getLengthType());
		field8583.setDataType(itmdef.getDataType());
		field8583.setEncodeType(itmdef.getEncodeType());
		field8583.setStringValue(s);
		log.info("field[" + fld + "]: ["+ 
	    		 s + "," + 
	    		 field8583.getEncodeType()  + "] ");
	    return dataoffset - offset + datalength;
	}
	
	protected String decodeItemValue(int fieldId, byte[] rawvalue, int offset, int length)
	{
		try
		{
			return new String(rawvalue, offset, length);
		}
		catch(ISO8583RuntimeException e)
		{
			log.error("["+className+"]" + e.getMessage() + ",[id,size,offset,length]=["+fieldId+","+rawvalue.length+","+offset+","+length+"]");
			throw e;
		}		
	}
	
	protected UnionPayBean putPropertyVaule(UnionPayBean unionPayBean, int fieldId, String value) {
		String property = getUnionPayBeanProperty(fieldId);
		if(property == null)
		{
			log.warn("["+className+"]no property is mapping to field " + fieldId + ",value=" + value);
			return unionPayBean;
		}
		
		String method = "set" + property.substring(0, 1).toUpperCase() + property.substring(1);		
		try 
		{
			com.yl.pay.pos.bean.UnionPayBean.class.getMethod(method, String.class).invoke(unionPayBean, value);	
			return unionPayBean;
		} //NoSuchMethodException,IllegalAccessException,InvocationTargetException
		catch (Exception e)
		{
			throw new RuntimeException("["+className+"]convert.from.8583.to.unionpayparam.error,fieldId="+fieldId+",property="+property+",value="+value,e);
		}
	}	
	
	private ISO8583Field getEmptyField(int id)
	{
		ISO8583Field itmdef = getFieldDefinition(id);
		return new ISO8583Field(id, itmdef.getMaxLength(), itmdef.getLengthType(), itmdef.getDataType(), itmdef.getEncodeType()); 
	}
	
	abstract public byte[] getByteArrayData();
    /**
     * 验证消息的MAC是否正确，指定的消息类型不需要MAC，则直接返回TRUE；需要MAC单是位图中不含MAC，则返回FALSE ;有MAC且和我们自己计算的结果相同返回TRUE；其他情况返回FALSE
     * @return
     */
	abstract public boolean validateMAC();
	abstract protected void checkFieldIdValidity(int fieldId);
	abstract protected ISO8583Field getFieldDefinition(int fieldId);
	abstract protected String getUnionPayBeanProperty(int fieldId);
}
