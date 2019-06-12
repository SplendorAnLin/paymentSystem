package com.yl.payinterface.core.handle.impl.quick.dianpayer.utils;

import java.security.MessageDigest;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

public class DianPayerMD5 {
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7",
            "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 转换字节数组为16进制字串
     *
     * @param b 字节数组
     * @return 16进制字串
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();
        for (byte aB : b) {
            resultSb.append(byteToHexString(aB));
        }
        return resultSb.toString();
    }

    /**
     * 转换byte到16进制
     *
     * @param b 要转换的byte
     * @return 16进制格式
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * MD5编码
     *
     * @param origin 原始字符串
     * @return 经过MD5加密之后的结果
     */
    public static String md5Encode(String origin) {
        return md5Encode(origin, "utf-8");
    }

    public static String md5Encode(String origin, String encode) {
        String resultString = null;
        try {
            resultString = origin;
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(resultString.getBytes(encode)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }
    
    public static String getSignData(TreeMap<String, String> treeMap,
			String signKey) {
		Set<String> keySet = treeMap.keySet();
		StringBuilder sb = new StringBuilder();
		for (String key : keySet) {
			if (!key.equalsIgnoreCase("sign")) {
				String value = treeMap.get(key);
				if (StringUtils.isNotBlank(value))
					sb.append(key).append("=").append(value).append("&");
			}
		}
		sb.append(signKey);
		String str = sb.toString();
		String ret = DianPayerMD5.md5Encode(str, "utf-8");
		return ret;
	}

}