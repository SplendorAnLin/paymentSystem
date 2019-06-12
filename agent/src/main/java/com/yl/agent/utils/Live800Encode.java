/*
 * 文 件 名:  Live800Encode.java
 * 版    权:  乐富支付有限公司. Copyright 2011-2014,  All rights reserved
 * 描    述:  TODO(用一句话描述该文件做什么) 
 * 修 改 人:  jianwen.zhu
 * 创建时间:  2014-5-22
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.yl.agent.utils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Live800 加密类
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月13日
 * @version V1.0.0
 */
public class Live800Encode
{
    private final static String[] hexDigits = {
        "0", "1", "2", "3", "4", "5", "6", "7",
        "8", "9", "a", "b", "c", "d", "e", "f"};

    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * @param origin String
     * @return String
     * @throws Exception
     */
    public static String getMD5Encode(String origin) throws Exception {
        if (!inited) {
            throw new Exception("MD5 算法实例初始化错误！");
        }
        if (origin == null) {
            return null;
        }
        byte[] temp = null;
        synchronized (md) {
            temp = md.digest(origin.getBytes());
        }

        return byteArrayToHexString(temp);

    }

    private static MessageDigest md = null;
    private static boolean inited = false;
    static {
        try {
            md = MessageDigest.getInstance("MD5");
            inited = true;
        }
        catch (NoSuchAlgorithmException ex) {
            inited = false;
        }
    }
    public static void main(String[] args) {
        try {
            System.err.println(getMD5Encode("ff"));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
