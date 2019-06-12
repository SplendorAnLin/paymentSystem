package com.yl.payinterface.core.handle.impl.wallet.yyg100001.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import javax.crypto.Cipher;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.HashMap;
import java.util.Map;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 签名工具
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/7/19
 */
public class yygSignUtils {

    public static final String KEY_ALGORITHM = "RSA";

    public static final String C_ALGORITHM = "BC";

    public static final String split = " ";

    public static final int max = 117;

    /**
     * 签名方法
     *
     * @param reqMsg 请求消息
     * @return
     * @throws UnsupportedEncodingException
     */
    public static Map<String, String> sign(String reqMsg, String privateKey, String proxyMerid)
            throws UnsupportedEncodingException {
        Map<String, String> reqHead = new HashMap<String, String>();
        String digestStr = getSha1(reqMsg);
        String signValue = encodeByPrivateKey(digestStr, new String(
                new Base64().decode(privateKey)));
        reqHead.put("mercId", proxyMerid);
        reqHead.put("sign", signValue);
        return reqHead;
    }

    public static String getSha1(String str){
        if(str==null||str.length()==0){
            return null;
        }
        char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9',
                'a','b','c','d','e','f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));
            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j*2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }

    public static String encodeByPrivateKey(String res, String key) {
        byte[] resBytes = res.getBytes();
        byte[] keyBytes = parseHexStr2Byte(key);
        StringBuffer result = new StringBuffer();
        // 如果超过了100个字节就分段
        if (keyBytes.length <= max) {// 不超过直接返回即可
            return encodePri(resBytes, keyBytes);
        } else {
            int size = resBytes.length / max + (resBytes.length % max > 0 ? 1 : 0);
            for (int i = 0; i < size; i++) {
                int len = i == size - 1 ? resBytes.length % max : max;
                byte[] bs = new byte[len];// 临时数组
                System.arraycopy(resBytes, i * max, bs, 0, len);
                result.append(encodePri(bs, keyBytes));
                if (i != size - 1)
                    result.append(split);
            }
            return result.toString();
        }
    }

    /** 加密-私钥-无分段 */
    private static String encodePri(byte[] res, byte[] keyBytes) {
        PKCS8EncodedKeySpec pk8 = new PKCS8EncodedKeySpec(keyBytes);
        try {
            KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
            Key priKey = kf.generatePrivate(pk8);
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            Cipher cp = Cipher.getInstance(kf.getAlgorithm(), C_ALGORITHM);
            cp.init(Cipher.ENCRYPT_MODE, priKey);
            return parseByte2HexStr(cp.doFinal(res));
        } catch (Exception e) {
            System.out.println("私钥加密失败");
            e.printStackTrace();
        }
        return null;
    }

    /** 将二进制转换成16进制 */
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

    /** 将16进制转换为二进制 */
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
}