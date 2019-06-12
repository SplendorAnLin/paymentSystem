package com.yl.pay.pos.util.codecs.common.tlv;

import java.util.ArrayList;
import java.util.List;

public class BerTlvDecoder implements TLVDecoder
{
	@Override
	public TLVObject[] decode(byte[] data) 
	{//TODO:目前只考虑了所有TLV中的V都是基本类型的情况
		List<TLVObject> list = new ArrayList<TLVObject>();
		int offset = 0;
		int length = data.length;
		String tag = null;
		ValuePosition vp = null;
		byte[] v = null;
		while(offset < length)
		{
			int size = getTagSize(data, offset);
			tag = bytesToHexString(data, offset, size);
			vp = getValuePosition( data, offset + size);
			v = new byte[vp.getLength()];
			System.arraycopy(data, vp.getOffset(), v, 0, vp.getLength());
			list.add(new TLVObject(tag, v));
			offset = vp.getOffset() + vp.getLength();
		}
		
		return list.toArray(new TLVObject[0]);
	}
	
	private int getTagSize(byte[] data, int offset)
	{//TODO:目前只考虑了最多 2个字节的情况 ，即TAG由2位或4位字符组成
		return (data[offset] & 0x1f) == 0x1f ? 2 : 1;
	}
	
	private ValuePosition getValuePosition(byte[] data, int offset)
	{
		//长度域的第1个字节的最高位如果为0，则本字节的值就表示v的长度
		//否则本字节b7-b1位的整数值表示后续有几个字节表示v的长度
		if( ( data[offset] & 0x80 ) == 0x00 )
		{
			return new ValuePosition(offset + 1, (int)data[offset]);
		}
		else
		{
			int lengthByte = data[offset] & 0x7F;//接着这么多个字节表示长度
			int length = 0;
			for(int i = 1; i<=lengthByte;i++) {//注意i是从1开始，可以等于lengthByte
				length = length << 8;
				length = length |(0xFF & data[offset+i]);
			}
			return new ValuePosition(offset + 1 + lengthByte, length);
		}
		
	}
	
	/**
	 * 把字节数组转换成16进制字符串
	 * 
	 * @param bArray
	 * @return
	 */
	private String bytesToHexString(byte[] bArray, int offset, int length)
	{
		StringBuffer sb = new StringBuffer(length * 2);
		String sTemp;
		for (int i = 0; i < length; i++)
		{
			sTemp = Integer.toHexString(0xFF & bArray[offset + i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}
	
	class ValuePosition
	{
		private int offset;
		private int length;
		public ValuePosition(int offset, int length)
		{
			this.offset = offset;
			this.length = length;
		}
		
		public int getOffset() {
			return offset;
		}
		
		public int getLength() {
			return length;
		}
		
		
	}
}