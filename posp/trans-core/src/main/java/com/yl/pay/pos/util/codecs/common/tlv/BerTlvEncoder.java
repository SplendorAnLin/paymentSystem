package com.yl.pay.pos.util.codecs.common.tlv;


public class BerTlvEncoder implements TLVEncoder 
{
	@Override
	public byte[] encode(TLVObject[] data)
	{
		byte[] buffer = new byte[2048]; 
		int offset = 0;
		for(int i = 0; i<data.length; i++)
		{
			TLVObject tlv = data[i];
			byte[] tag = hexStringToByte(tlv.getTag());
			System.arraycopy(tag, 0, buffer, offset, tag.length);
			offset += tag.length;
			byte[] v = tlv.getByteValue();
			byte[] lengthByte = hexStringToByte(Integer.toHexString(v.length).toUpperCase());
			if(v.length < 128) {
				System.arraycopy(lengthByte, 0, buffer, offset, lengthByte.length);
				offset += lengthByte.length;
			} else {//当长度大于128时，需要有一个字节的长度域来表示后续还有几个字节表示长度，并且这个字节的最高位置1
				byte[] lengthByteSize = new byte[1];
				lengthByteSize[0] = (byte)(lengthByte.length | 0x80);
				System.arraycopy(lengthByteSize, 0, buffer, offset, 1);
				System.arraycopy(lengthByte, 0, buffer, offset+1, lengthByte.length);
				offset += lengthByte.length+1;
			}
			System.arraycopy(v, 0,buffer, offset, v.length);
			offset += v.length;
		}
		byte[] result = new byte[offset];
		System.arraycopy(buffer, 0, result, 0, offset);
		return result;
	}
	
	/*
	 * 把16进制字符串转换成字节数组 
	 * @param hex
	 * @return
	 */
	private byte[] hexStringToByte(String hex) {
		if(hex.length() % 2 != 0) hex = "0" + hex;
		int len = (hex.length() / 2);
		
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}
	
	private byte toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}
}
