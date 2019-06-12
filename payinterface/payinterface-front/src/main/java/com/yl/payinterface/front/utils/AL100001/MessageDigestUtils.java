package com.yl.payinterface.front.utils.AL100001;

/**
 * MD5签名工具
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/5/8
 */
public class MessageDigestUtils {

    public MessageDigestUtils(String algorithm, String provider) {
        this.algorithm = algorithm;
        this.provider = provider;
    }

    public MessageDigestUtils(String algorithm) {
        this(algorithm, null);
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public byte[] sign(byte bytes[]){
       try {
           java.security.MessageDigest digit = provider != null ? java.security.MessageDigest.getInstance(algorithm, provider) : java.security.MessageDigest.getInstance(algorithm);
           digit.update(bytes);
           return digit.digest();
       } catch (Exception e) {

       }
       return null;
    }

    public boolean verify(byte bytes[], byte signedArray[]) {
        try {
            return signedArray.equals(sign(bytes));
        } catch (Exception e) {

        }
        return false;
    }

    private String algorithm;
    private String provider;
}