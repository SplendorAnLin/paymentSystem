package com.yl.payinterface.core.handle.impl.b2c.hfb100002.utils;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;

/**
 * Https 相关工具
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/3/13
 */
class HttpsHandler extends HttpFacade {

    /**
     * 发送 https post 请求
     * @param url 访问的url地址
     * @param params 请求参数
     * @return 返回结果
     * @throws Exception
     */
    @Override
    public String post(String url, Map<Object, Object> params) throws Exception {
        StringBuilder sb = new StringBuilder();
        if(params != null && !params.isEmpty()){
            String temp;
            for(Iterator<Object> it = params.keySet().iterator(); it.hasNext();){
                temp = it.next().toString();
                sb.append(temp).append("=").append(params.get(temp)).append("&");
            }
            sb.deleteCharAt(sb.length() - 1 );
        }
        return handler(url, sb.toString().getBytes(defaultCharset), DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT, METHOD_POST);
    }

    /**
     * 发送 https get 请求
     * @param url 访问的url地址
     * @return 返回结果
     * @throws Exception
     */
    @Override
    public String get(String url) throws Exception {
        return handler(url,null, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT, METHOD_GET);
    }

    /**
     * 处理实际业务员
     * @param url
     * @param content
     * @param connectTimeout
     * @param readTimeout
     * @param method
     * @return
     * @throws Exception
     */
    private String handler(String url, byte[] content, int connectTimeout, int readTimeout, String method) throws Exception {
        HttpsURLConnection conn = null;
        OutputStream out = null;
        String rsp = null;
        try {
            try{
                SSLContext ctx = SSLContext.getInstance("TLS");
                ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());
                SSLContext.setDefault(ctx);
                conn = getConnection(new URL(url), method);
                conn.setHostnameVerifier(new HostnameVerifier() {
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
                conn.setConnectTimeout(connectTimeout);
                conn.setReadTimeout(readTimeout);
            }catch(Exception e){
                e.printStackTrace();
                throw e;
            }
            try{
                //如果是post交易，写入请求参数
                if(METHOD_POST.equals(method)) {
                    out = conn.getOutputStream();
                    out.write(content);
                }
                rsp = getResponseAsString(conn);
            }catch(IOException e){
                e.printStackTrace();
                throw e;
            }

        }finally {
            if (out != null) {
                out.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }

        return rsp;
    }


    private HttpsURLConnection getConnection(URL url, String method)
            throws IOException {
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setDoInput(true);
        if(METHOD_POST.equals(method))
            conn.setDoOutput(true);
        conn.setRequestProperty("Accept", "*/*");
        return conn;
    }

    private static class DefaultTrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

    }




}
