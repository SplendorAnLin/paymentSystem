package com.yl.chat.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public class AESUtils {

    /**
     * 加密解决算法
     */
    private static String ivParameter = "1234567890123456";//默认偏移

    private static String WAYS = "AES";
    private static String MODE = "";
    private static boolean isPwd = false;
    private static String ModeCode = "PKCS5Padding";

    private static int pwdLenght = 16;
    private static String val = "0";

    public static String selectMod(int type) {
        switch (type) {
            case 0:
                isPwd = false;
                MODE = WAYS + "/ECB/" + ModeCode;
                break;
            case 1:
                isPwd = true;
                MODE = WAYS + "/CBC/" + ModeCode;
                break;
            case 2:
                isPwd = true;
                MODE = WAYS + "/CFB/" + ModeCode;
                break;
            case 3:
                isPwd = true;
                MODE = WAYS + "/OFB/" + ModeCode;
                break;
        }
        return MODE;
    }

    public static String encrypt(String sSrc, String sKey) throws Exception {
        return encrypt(sSrc, sKey, 0);
    }

    // 加密
    public static String encrypt(String sSrc, String sKey, int type)
            throws Exception {
        Cipher cipher = Cipher.getInstance(selectMod(type));
        byte[] raw = toMakekey(sKey, pwdLenght, val).getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, WAYS);
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        if (isPwd == false) {// ECB 不用密码
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        }

        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        return Base64Utils.encode(encrypted);// 此处使用BASE64做转码。
    }

    public static String decrypt(String sSrc, String sKey) throws Exception {
        return decrypt(sSrc, sKey, 0);
    }

    // 解密
    public static String decrypt(String sSrc, String sKey, int type)
            throws Exception {
        try {
            byte[] raw = toMakekey(sKey, pwdLenght, val).getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, WAYS);
            Cipher cipher = Cipher.getInstance(selectMod(type));
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            if (isPwd == false) {// ECB 不用密码
                cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            }
            byte[] encrypted1 = Base64Utils.decode(sSrc);// 先用base64解密
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * byte数组转化为16进制字符串
     *
     * @param bytes
     * @return
     */
    public static String byteToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String strHex = Integer.toHexString(bytes[i]);
            if (strHex.length() > 3) {
                sb.append(strHex.substring(6));
            } else {
                if (strHex.length() < 2) {
                    sb.append("0" + strHex);
                } else {
                    sb.append(strHex);
                }
            }
        }
        return sb.toString();
    }

    //key
    public static String toMakekey(String str, int strLength, String val) {
        int strLen = str.length();
        int lenDiff = strLen - strLength;
        if (lenDiff == 0) {
            return str;
        } else if (lenDiff < 0) {
            while (strLen < strLength) {
                StringBuffer buffer = new StringBuffer();
                buffer.append(str).append(val);
                str = buffer.toString();
                strLen = str.length();
            }
            return str;
        } else {
            String baseKey = str.substring(0, 16);
            if (lenDiff >= 16) {
                int idx = lenDiff / 16;
                String tmp = baseKey;
                for (int i = 0; i < idx; i++) {
                    tmp = xor(baseKey, str.substring((i + 1) * 16, 16 * (i + 2)));
                    idx--;
                }
                str = tmp;
            } else {
                str = xor(baseKey, str.substring(16, str.length()));
            }
            return str;
        }
    }

    public static String xor(String part1, String part2) {
        char[] charArray1 = part1.toCharArray();
        char[] charArray2 = part2.toCharArray();
        char[] charArray;
        int tmpIndex = 0;
        if (charArray1.length > charArray2.length) {
            charArray = new char[charArray1.length];
            for (int i = 0; i < charArray1.length; i++) {
                tmpIndex = i;
                if (tmpIndex >= charArray2.length - 1) {
                    tmpIndex = 0;
                }
                charArray[i] = (char) (charArray1[i] ^ charArray2[tmpIndex]);
            }
        } else {
            charArray = new char[charArray2.length];
            for (int i = 0; i < charArray2.length; i++) {
                tmpIndex = i;
                if (tmpIndex >= charArray1.length - 1) {
                    tmpIndex = 0;
                }
                charArray[i] = (char) (charArray2[i] ^ charArray1[tmpIndex]);
            }
        }
        return new String(charArray);
    }

    public static byte[] newencrypt(String content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建AES加密编码器
            byte[] byteContent = content.getBytes("UTF-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化AES加密
            byte[] result = cipher.doFinal(byteContent);
            return result; // AES加密结果
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
      * @param content  待解密内容,格式为byte数组
      * @param password AES解密使用的密钥
      * @return
      */
    public static byte[] newdecrypt(byte[] content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建AES加密编码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化AES加密
            byte[] result = cipher.doFinal(content);
            return result; // 得到AES解密结果
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * java将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static String outKey(String part1, String part2) {
        String encryption = "";
//         try {  
//         	part1 =  new String(part1.getBytes("UTF-8"),"iso-8859-1");
//         	part2 =  new String(part2.getBytes("UTF-8"),"iso-8859-1");
//            } catch (Exception e) {  
//            }
        int tmpIndex = 0;
        if (part1.length() > part2.length()) {
            char[] cipher = new char[part1.length()];
            for (int i = 0; i < part1.length(); i++) {
                tmpIndex = i;
                if (tmpIndex >= part2.length() - 1) tmpIndex = 0;

                cipher[i] = (char) (part1.charAt(i) ^ part2.charAt(tmpIndex));
                String strCipher = Integer.toHexString(cipher[i]);
                if (strCipher.length() == 1) {
                    encryption += "0" + strCipher;
                } else {
                    encryption += strCipher;
                }
            }

        } else {
            char[] cipher = new char[part2.length()];
            for (int i = 0; i < part2.length(); i++) {
                tmpIndex = i;
                if (tmpIndex >= part1.length() - 1) tmpIndex = 0;
                cipher[i] = (char) (part2.charAt(i) ^ part1.charAt(tmpIndex));
                String strCipher = Integer.toHexString(cipher[i]);
                if (strCipher.length() == 1) {
                    encryption += "0" + strCipher;
                } else {
                    encryption += strCipher;
                }

            }
        }
        return MD5.MD5Encode16(encryption);
    }

    public static void main(String[] args) {
        String content = "{\"responseData\":\"{\"sign\"}";
        String key = AESUtils.outKey("ace9fea0-2f1d-46b7-8699-c93a8a33eceb", "1502023341023");
        System.out.println(key);
        try {
            String s = new String(AESUtils.encrypt(content, key, 0));
            System.out.println("方法-加密后：" + s);
            s = new String(AESUtils.decrypt(s, key, 0));
            System.out.println("方法-解密后：" + s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}