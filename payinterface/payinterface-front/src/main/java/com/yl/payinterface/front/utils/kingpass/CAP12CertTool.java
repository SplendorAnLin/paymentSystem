package com.yl.payinterface.front.utils.kingpass;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

/**
 * @author Shark
 * @Description
 * @Date 2018/5/30 17:31
 */
public class CAP12CertTool {

    private static SignedPack signedPack;

    public CAP12CertTool(InputStream fileInputStream, String keyPass) throws SecurityException {
        signedPack = this.getP12(fileInputStream, keyPass);
    }

    public CAP12CertTool(String path, String keyPass) throws SecurityException, FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(new File(path));
        signedPack = this.getP12(fileInputStream, keyPass);
    }

    private SignedPack getP12(InputStream fileInputStream, String keyPass) throws SecurityException {
        SignedPack sp = new SignedPack();

        try {
            KeyStore e = KeyStore.getInstance("PKCS12");
            char[] nPassword = (char[])null;
            if (keyPass != null && !keyPass.trim().equals("")) {
                nPassword = keyPass.toCharArray();
            } else {
                nPassword = (char[])null;
            }

            e.load(fileInputStream, nPassword);
            Enumeration enum2 = e.aliases();
            String keyAlias = null;
            if (enum2.hasMoreElements()) {
                keyAlias = (String)enum2.nextElement();
            }

            PrivateKey priKey = (PrivateKey)e.getKey(keyAlias, nPassword);
            Certificate cert = e.getCertificate(keyAlias);
            PublicKey pubKey = cert.getPublicKey();
            sp.setCert((X509Certificate)cert);
            sp.setPubKey(pubKey);
            sp.setPriKey(priKey);
        } catch (Exception var18) {
            var18.printStackTrace();
            throw new SecurityException(var18.getMessage());
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException var17) {
                    ;
                }
            }

        }

        return sp;
    }

    public X509Certificate getCert() {
        return signedPack.getCert();
    }

    public PublicKey getPublicKey() {
        return signedPack.getPubKey();
    }

    public PrivateKey getPrivateKey() {
        return signedPack.getPriKey();
    }

    public byte[] getSignData(byte[] indata) throws SecurityException {
        byte[] res = (byte[])null;

        try {
            Signature e = Signature.getInstance("SHA256WITHRSA");
            e.initSign(this.getPrivateKey());
            e.update(indata);
            res = e.sign();
            return res;
        } catch (InvalidKeyException var4) {
            throw new SecurityException(var4.getMessage());
        } catch (NoSuchAlgorithmException var5) {
            throw new SecurityException(var5.getMessage());
        } catch (SignatureException var6) {
            throw new SecurityException(var6.getMessage());
        }
    }

}
