package com.yl.pay.pos.util.codecs.common.tlv;

public interface TLVDecoder 
{
	TLVObject[] decode(byte[] data);
}