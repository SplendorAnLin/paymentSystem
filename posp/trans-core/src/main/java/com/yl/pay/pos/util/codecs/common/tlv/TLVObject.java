package com.yl.pay.pos.util.codecs.common.tlv;

public class TLVObject
{
	private String tag;
	private byte[] bv ;
//	private int length;
//	private byte[] fullContent;
	public TLVObject(String t, byte[] v)
	{
		tag = t;
		bv = v;
	}
	
	public String getTag()
	{
		return tag;
	}
	
	public byte[] getByteValue()
	{
		return bv;
	}
}
