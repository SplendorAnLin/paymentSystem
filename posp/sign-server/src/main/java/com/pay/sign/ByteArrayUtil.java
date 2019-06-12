package com.pay.sign;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
/**
 * Title: 1.实现多个字节数组的合并;2.转成二进制字符串 
 * Description:   
 * Copyright: 2015年6月12日下午2:54:59
 * Company: SDJ
 * @author zhongxiang.ren
 */
public class ByteArrayUtil {
	/**
	 * 系统提供的数组拷贝方法arraycopy
	 * */
	public static byte[] sysCopy(List<byte[]> srcArrays) {
		int len = 0;
		for (byte[] srcArray : srcArrays) {
			len += srcArray.length;
		}
		byte[] destArray = new byte[len];
		int destLen = 0;
		for (byte[] srcArray : srcArrays) {
			System.arraycopy(srcArray, 0, destArray, destLen, srcArray.length);
			destLen += srcArray.length;
		}
		return destArray;
	}

	/**
	 * 借助字节输出流ByteArrayOutputStream来实现字节数组的合并
	 * */
	public static byte[] streamCopy(List<byte[]> srcArrays) {
		byte[] destAray = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			for (byte[] srcArray : srcArrays) {
				bos.write(srcArray);
			}
			bos.flush();
			destAray = bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bos.close();
			} catch (IOException e) {
			}
		}
		return destAray;
	}

	 /**
	  * 将字符串转换成二进制字符串
	  * 
    private static String strToBinstr(String str) {
        char[] strChar=str.toCharArray();
        String result="";
        for(int i=0;i<strChar.length;i++){
            result +=Integer.toBinaryString(strChar[i]);
        }
        return result;
    }
	*/
	public static void main(String[] args) {
//		List<byte[]> bytes = new ArrayList<byte[]>();
//		byte[] byte1 = "asdf23346039546809734kjlsdasdf".getBytes();
//		bytes.add(byte1);
//		byte[] byte2 = "2354983754729979878osdf0987e57osadf9sa8923osadsdgf".getBytes();
//		bytes.add(byte2);
//		byte[] byte3 = "qwer093465u0g9u093450923ufw".getBytes();
//		bytes.add(byte3);
//		//测试效率高低
//		System.out.println("方法一:");
//		long start = System.currentTimeMillis();
//		byte[] newByte = sysCopy(bytes);
//		long end = System.currentTimeMillis();
//		System.out.println("time:" + (end - start));
//		
//		for (int i = 0; i < newByte.length; i++) {
//			System.out.print(newByte[i] + " ");
//		}
//
//		System.out.println("\n方法二:");
//		
//		start = System.currentTimeMillis();
//		newByte = streamCopy(bytes);
//		end = System.currentTimeMillis();
//		System.out.println("time:" + (end - start));
//		
//		for (int i = 0; i < newByte.length; i++) {
//			System.out.print(newByte[i] + " ");
//		}
		
	}
}