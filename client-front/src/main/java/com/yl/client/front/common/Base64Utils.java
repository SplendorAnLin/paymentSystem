package com.yl.client.front.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * BASE64加密工具类
 * 
 * @author 聚合支付有限公司
 * @since 2017年4月24日
 * @version V1.0.0
 */
@SuppressWarnings("restriction")
public class Base64Utils {
	private static Logger logger = LoggerFactory.getLogger(Base64Utils.class);
	/**
	 * Description: BASE64加密
	 * @param str
	 * @return
	 */
	public static String encode(byte[] str) {
		if (str == null || "".equals(str)) {
			return null;
		}
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(str);
	}

	/**
	 * Description: BASE64解密
	 * @param str
	 * @return
	 */
	public static byte[] decode(String str) {
		byte[] src = null;
		try {
			if (str == null || "".equals(str)) {
				return null;
			}
			BASE64Decoder decoder = new BASE64Decoder();
			src = decoder.decodeBuffer(str);
		} catch (IOException e) {
			return null;
		}
		return src;
	}
	
	public static boolean base64ToFile(String base64,String filePath) {
		byte[] buffer=decode(base64);  
    	File file = new File(filePath);
    	FileOutputStream out=null;
        try {
        	File fileParent = file.getParentFile();  
        	if(!fileParent.exists()){  
        	    fileParent.mkdirs();
        	}
        	if (!file.exists()) {
        		file.createNewFile();
			}
        	out= new FileOutputStream(file);  
 	        out.write(buffer);  
 	        out.flush();
            out.close(); 
        } catch (IOException e) {
        	return false;
        }finally {
			if (out!=null) {
	            try {
	            	out.close();
				} catch (IOException e) {
					logger.info("文件流异常{}",e);
				}
	            return true;
			}
		}
        return true;
    }
}
