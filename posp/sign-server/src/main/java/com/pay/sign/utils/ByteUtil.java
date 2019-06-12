package com.pay.sign.utils;

import java.util.BitSet;

public class ByteUtil {

	/**
	 * 将int的num转换为数量为byteNum的bcd码byte数组
	 * 
	 * @param num
	 *            需要被转换的数字
	 * @param byteNum
	 *            转换为几byte的数组
	 * @return
	 */
	public static byte[] int2bcd(int num, int byteNum) {

		String numStr = String.valueOf(num);
		int numCount = numStr.length();

		if (byteNum * 2 < numCount) {
			throw new java.lang.ArithmeticException(byteNum
					+ " byte can't host int " + num);
		}
		byte[] b = new byte[byteNum];
		if (numCount % 2 == 0) {
			int needNum = numCount / 2;
			int zeroByteCount = byteNum - needNum;
			for (int i = 0; i < zeroByteCount; i++) {
				b[i] = 0;
			}
			for (int i = 0; i < numCount; i = i + 2) {

				byte high = (byte) ((byte) (numStr.charAt(i) - '1' + 1) << 4);
				byte low = (byte) (numStr.charAt(i + 1) - '1' + 1);
				b[zeroByteCount + i / 2] = (byte) (high | low);
			}
		} else {
			int needNum = numCount / 2 + 1;
			int zeroByteCount = byteNum - needNum;
			for (int i = 0; i < zeroByteCount; i++) {
				b[i] = 0;
			}
			b[zeroByteCount] = (byte) (numStr.charAt(0) - '1' + 1);
			for (int i = 1; i < numCount; i = i + 2) {
				byte high = (byte) ((byte) (numStr.charAt(i) - '1' + 1) << 4);
				byte low = (byte) (numStr.charAt(i + 1) - '1' + 1);
				b[zeroByteCount + 1 + i / 2] = (byte) (high | low);
			}
		}
		return b;
	}
	
	/**
	 * short转为byte数组
	 * @param s
	 * @return
	 */
	public static byte[] shortToByteArray(short s) {
		byte[] shortBuf = new byte[2];
		for (int i = 0; i < 2; i++) {
			int offset = (shortBuf.length - 1 - i) * 8;
			shortBuf[i] = (byte) ((s >>> offset) & 0xff);
		}
		return shortBuf;
	}

	/**
	 * byte数组转为short
	 * @param b
	 * @return
	 */
	public static int byteArrayToShort(byte[] b) {
		return (b[0] << 8) + (b[1] & 0xFF);
	}

	/**
	 * int转为byte数组
	 * @param value
	 * @return
	 */
	public static byte[] intToByteArray(int value) {
		byte[] b = new byte[4];
		for (int i = 0; i < 4; i++) {
			int offset = (b.length - 1 - i) * 8;
			b[i] = (byte) ((value >>> offset) & 0xFF);
		}
		return b;
	}

	/**
	 * byte数组转为int
	 * @param b
	 * @return
	 */
	public static int byteArrayToInt(byte[] b) {
		return (b[0] << 24) + ((b[1] & 0xFF) << 16) + ((b[2] & 0xFF) << 8)
				+ (b[3] & 0xFF);
	}
	
	/**
	 * 根据byte，及指定的位图长度取位图
	 * bitmap:位图数据
	 * len:位图byte数
	 */
	public static BitSet getBitMap(byte[] bitmap, int len) {
		if (bitmap == null)
			return null;
		BitSet bs = new BitSet(len + 1);
		for (int i = 0; i < len * 8; i++) {
			if ((bitmap[i / 8] & (0x80 >>> (i % 8))) != 0)
				bs.set(i + 1, true);
		}
		return bs;
	}
}
