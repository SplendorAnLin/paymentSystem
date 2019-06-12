

package com.yl.payinterface.core.handle.impl.remit.kingpass100001.utils;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class DesUtils {
    public DesUtils() {
    }

    public static String desEnCode(String srcStr) {
        try {
            Key localException = jdMethod_super("cputest".getBytes());
            Cipher localCipher = Cipher.getInstance("DES");
            localCipher.init(1, localException);
            return byteArr2HexStr(localCipher.doFinal(srcStr.getBytes()));
        } catch (InvalidKeyException |NoSuchAlgorithmException | NoSuchPaddingException var3) {
            var3.printStackTrace();
        }  catch (Exception var6) {
            var6.printStackTrace();
        }

        return "0";
    }

    public static String desDeCode(String desStr) {
        try {
            Key localException = jdMethod_super("cputest".getBytes());
            Cipher localCipher = Cipher.getInstance("DES");
            localCipher.init(2, localException);
            return new String(localCipher.doFinal(hexStr2ByteArr(desStr)));
        } catch (InvalidKeyException |NoSuchAlgorithmException | NoSuchPaddingException var3) {
            var3.printStackTrace();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return "0";
    }

    private static Key jdMethod_super(byte[] paramArrayOfByte) throws Exception {
        byte[] arrayOfByte = new byte[8];

        for(int localSecretKeySpec = 0; localSecretKeySpec < paramArrayOfByte.length && localSecretKeySpec < arrayOfByte.length; ++localSecretKeySpec) {
            arrayOfByte[localSecretKeySpec] = paramArrayOfByte[localSecretKeySpec];
        }

        SecretKeySpec var3 = new SecretKeySpec(arrayOfByte, "DES");
        return var3;
    }

    public static String byteArr2HexStr(byte[] paramArrayOfByte) throws Exception {
        int i = paramArrayOfByte.length;
        StringBuffer localStringBuffer = new StringBuffer(i * 2);

        for(int j = 0; j < i; ++j) {
            int k;
            for(k = paramArrayOfByte[j]; k < 0; k += 256) {
                ;
            }

            if(k < 16) {
                localStringBuffer.append("0");
            }

            localStringBuffer.append(Integer.toString(k, 16));
        }

        return localStringBuffer.toString();
    }

    public static byte[] hexStr2ByteArr(String paramString) throws Exception {
        byte[] arrayOfByte1 = paramString.getBytes();
        int i = arrayOfByte1.length;
        byte[] arrayOfByte2 = new byte[i / 2];

        for(int j = 0; j < i; j += 2) {
            String str = new String(arrayOfByte1, j, 2);
            arrayOfByte2[j / 2] = (byte)Integer.parseInt(str, 16);
        }

        return arrayOfByte2;
    }

}
