package com.yl.payinterface.core.handle.impl.remit.kingpass100001.utils;


import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class  SimpleHttpsSocketFactory implements X509TrustManager {
        SimpleHttpsSocketFactory() {
    }

    public void checkClientTrusted(X509Certificate[] ax509certificate, String s) throws CertificateException {
    }

    public void checkServerTrusted(X509Certificate[] ax509certificate, String s) throws CertificateException {
    }

    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}
