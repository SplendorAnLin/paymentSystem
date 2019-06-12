package com.yl.payinterface.core.handle.impl.wallet.kingpass100001.utils;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.*;
import java.security.cert.*;

public class RSASignUtil {
    private static final Logger log = LoggerFactory.getLogger(RSASignUtil.class);
    private String service = null;
    private String certFilePath = null;
    private String password = null;
    private String hexCert = null;
    private String rootCertPath = null;

    public RSASignUtil(String certFilePath, String password) {
        this.certFilePath = certFilePath;
        this.password = password;
    }

    public static byte[] checkPEM(byte[] paramArrayOfByte) {
        String str1 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789/+= \r\n-";

        for (int localStringBuffer = 0; localStringBuffer < paramArrayOfByte.length; ++localStringBuffer) {
            if (str1.indexOf(paramArrayOfByte[localStringBuffer]) == -1) {
                return null;
            }
        }

        StringBuffer var5 = new StringBuffer(paramArrayOfByte.length);
        String str2 = new String(paramArrayOfByte);

        for (int j = 0; j < str2.length(); ++j) {
            if (str2.charAt(j) != ' ' && str2.charAt(j) != '\r' && str2.charAt(j) != '\n') {
                var5.append(str2.charAt(j));
            }
        }

        return var5.toString().getBytes();
    }

    public void setRootCertPath(String rootCertPath) {
        this.rootCertPath = rootCertPath;
    }

    public String sign(String indata, String encoding) throws Exception {
        String serverSign = null;
        if (StringUtils.isBlank(encoding)) {
            encoding = "GBK";
        }

        log.info("[{}]", indata);

        try {
            CAP12CertTool singData = new CAP12CertTool(this.certFilePath, this.password);
            X509Certificate cert = singData.getCert();
            byte[] si = singData.getSignData(indata.getBytes(encoding));
            byte[] cr = cert.getEncoded();
            this.hexCert = HexStringByte.byteToHex(cr);
            serverSign = HexStringByte.byteToHex(si);
        } catch (CertificateEncodingException var8) {
            log.error("Certificate encoding exception!", var8);
        } catch (FileNotFoundException var9) {
            log.error("File not found exception", var9);
        } catch (SecurityException var10) {
            log.error("Security exception", var10);
        }

        return serverSign;
    }

    public String getCertInfo() {
        return this.hexCert;
    }

    public boolean verify(String oridata, String signData, String svrCert, String encoding) throws Exception {
        boolean res = false;
        if (StringUtils.isBlank(encoding)) {
            encoding = "GBK";
        }

        try {
            byte[] e = HexStringByte.hexToByte(signData.getBytes());
            byte[] inDataBytes = oridata.getBytes(encoding);
            byte[] signaturepem = checkPEM(e);
            if (signaturepem != null) {
                e = Base64.decode(signaturepem);
            }

            X509Certificate cert = this.getCertFromHexString(svrCert);
            if (cert != null) {
                PublicKey pubKey = cert.getPublicKey();
                Signature signet = Signature.getInstance("SHA256WITHRSA");
                signet.initVerify(pubKey);
                signet.update(inDataBytes);
                res = signet.verify(e);
            }

            if (!res) {
                log.warn("verify signature failed!");
                return false;
            }

            X509Certificate root = this.getCertFromPath(this.rootCertPath);
            res = this.verifyCert(cert, root);
            return res;
        } catch (InvalidKeyException var12) {
            log.error("Invalid key exception", var12);
        } catch (NoSuchAlgorithmException var13) {
            log.error("No such algorithm exception", var13);
        } catch (SignatureException var14) {
            log.error("Signature exception", var14);
        } catch (SecurityException var15) {
            log.error("Security exception", var15);
        }

        return res;
    }

    public boolean verifyCert(X509Certificate userCert, X509Certificate rootCert) throws SecurityException {
        boolean res = false;

        try {
            PublicKey e = rootCert.getPublicKey();
            userCert.checkValidity();
            userCert.verify(e);
            res = true;
            if (!userCert.getIssuerDN().equals(rootCert.getSubjectDN())) {
                res = false;
            }

            return res;
        } catch (CertificateNotYetValidException | InvalidKeyException | NoSuchAlgorithmException | NoSuchProviderException | SignatureException | CertificateExpiredException var5) {
            throw new SecurityException(var5.getMessage());
        } catch (CertificateException var6) {
            throw new SecurityException(var6.getMessage());
        }
    }

    private X509Certificate getCertFromPath(String crt_path) throws Exception {
        try {
            FileInputStream fis = new FileInputStream(new File(crt_path));
            CertificateFactory e = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) e.generateCertificate(fis);
            return cert;
        } catch (Exception var5) {
            throw new SecurityException(var5.getMessage());
        }
    }

    private X509Certificate getCertFromHexString(String hexCert) throws SecurityException {
        ByteArrayInputStream bIn = null;
        X509Certificate certobj = null;

        try {
            byte[] e = HexStringByte.hexToByte(hexCert.getBytes());
            CertificateFactory fact = CertificateFactory.getInstance("X.509");
            bIn = new ByteArrayInputStream(e);
            certobj = (X509Certificate) fact.generateCertificate(bIn);
            bIn.close();
            bIn = null;
        } catch (CertificateException var16) {
            log.error("Certificate exception", var16);
        } catch (IOException var17) {
            log.error("IO exception", var17);
        } finally {
            try {
                if (bIn != null) {
                    bIn.close();
                }
            } catch (IOException var15) {
                ;
            }

        }

        return certobj;
    }

    public String getService() {
        return this.service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
