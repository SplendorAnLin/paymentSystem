package com.yl.pay.pos.util.codecs.common.tlv;

public interface TLVEncoder
{
	byte[] encode(TLVObject[] data);
}
