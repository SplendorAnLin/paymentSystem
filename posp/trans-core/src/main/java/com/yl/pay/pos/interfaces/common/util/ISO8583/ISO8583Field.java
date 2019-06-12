package com.yl.pay.pos.interfaces.common.util.ISO8583;

import com.pay.common.util.ISO8583.DataType;
import com.yl.pay.pos.interfaces.common.util.ISO8583.EncodeType;
import com.pay.common.util.ISO8583.LengthType;

/**
 * Title: 域定义
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: pay
 * @author qiuzhl
 */
public class ISO8583Field implements Cloneable
{
	public final static int FLD_MAX_SIZE = 999 + 3; // 字段最大长度
	
	/**
	 * 域名称
	 */
	private double id;

	/**
	 * 这个域的最大的长度
	 */
	private int maxLength;

	/**
	 * 长度类型
	 */
	private LengthType lengthType;
	
	/**
	 * 每个域可放的数据的类型
	 */
	private DataType dataType;
	
	/**
	 * 编码方式
	 */
	private EncodeType encodeType;// 编码方式
	
//	/**
//	 * 本字段所有数据的字节长度，如果是定长数据，则为数据的字节长度，如果是边长数据，还要加上1或2个字节的长度位
//	 */
//	private int byteDataLength = -1;
//	
//	private byte[] bv = null;
	
	private String sv = null;

	public ISO8583Field(double id, int maxLength, LengthType lengthType,DataType dataType, EncodeType encodeType)
	{
		this.id = id;
		this.lengthType = lengthType;
		this.dataType = dataType;
		this.maxLength = maxLength;
		this.encodeType = encodeType;
	}

	public double getId()
	{
		return id;
	}

	public void setId(double id) {
		this.id = id;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int length) {
		this.maxLength = length;
	}

	public LengthType getLengthType() {
		return lengthType;
	}

	public void setLengthType(LengthType lengthType) {
		this.lengthType = lengthType;
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	public EncodeType getEncodeType() {
		return encodeType;
	}

	public void setEncodeType(EncodeType encodeType) {
		this.encodeType = encodeType;
	}

	public void setStringValue(String value)
	{
		sv = value;
//		bv = null;
	}

	public String getStringValue() {
		return sv;
	}
	
//	public void setByteValue(byte[] value)
//	{
//		bv = value;
//		sv = null;
//	}
	
	public byte[] getByteValue()
	{
		return null;
	}
	
	public byte[] getFieldData()
	{
		return null;
	}
	
	public void setFieldData(byte[] data)
	{
		
	}
	
	public int getByteValueLength()
	{
		//目前仅支持APPOINT+HEX的类型，如果其他地方调用此方法，需要进行相应的修改
		if(!LengthType.APPOINT.equals(lengthType)) throw new RuntimeException("this method is not support LengthType " + lengthType.name() + ",EncodeType " + encodeType.name());
		
		if(EncodeType.HEX.equals(encodeType) || EncodeType.BCD_LEFT_FILL_ZERO.equals(encodeType) || EncodeType.BCD_RIGHT_FILL_ZERO.equals(encodeType))
		{
			return (maxLength + 1 ) / 2;
		}
		else
		{
			return maxLength;
		}
	}
	
	public int getFiledDataLength()
	{
		return 0;
	}
}
