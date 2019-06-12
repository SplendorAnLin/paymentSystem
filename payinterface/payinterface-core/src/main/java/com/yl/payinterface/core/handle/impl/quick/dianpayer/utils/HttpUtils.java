package com.yl.payinterface.core.handle.impl.quick.dianpayer.utils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpUtils {
    static final String USER_AGENT = "Mozilla/5.0";
    static String POST_URL = "https://m.dianpayer.com/gateway.do";
//    static Logger logger = Logger.getLogger(HttpUtils.class);


    private static BasicHttpClientConnectionManager initHttpsPoolingMgnt() {
        SSLContextBuilder builder = SSLContexts.custom();
        try {
            builder.loadTrustMaterial(null, new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                    return true;
                }
            });
        } catch (NoSuchAlgorithmException ignore) {

        } catch (KeyStoreException ignore) {

        }
        SSLContext sslContext = null;
        try {
            sslContext = builder.build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();

        }
        assert sslContext != null;

        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslContext, new X509HostnameVerifier() {
            public void verify(String host, SSLSocket ssl)
                    throws IOException {
            }

            public void verify(String host, X509Certificate cert)
                    throws SSLException {
            }

            public void verify(String host, String[] cns,
                               String[] subjectAlts) throws SSLException {
            }

            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });

        Registry<ConnectionSocketFactory> socketFactoryRegistry
                = RegistryBuilder.<ConnectionSocketFactory>create().register("https", sslsf).register("http", new PlainConnectionSocketFactory()).build();

        return new BasicHttpClientConnectionManager(socketFactoryRegistry);
    }

    private HttpClient createClient() {
        return HttpClients.custom().setConnectionManager(initHttpsPoolingMgnt()).build();
    }

    /**
     * http 请求
     *
     * @param url     http/https 开头
     * @param request 请求参数Map 对象
     * @param outSb   输出参数
     * @return 200 返回正常
     */

    public static int sendRequest(String url, Map<String, String> request, StringBuilder outSb) {
        return sendRequest(url, request, outSb, "utf-8");

    }

    //默认15秒超时  最多 30 秒返回
    public static int sendRequest(String url, Map<String, String> request, StringBuilder outSb, String encoding) {
        return sendRequest(url, request, outSb, encoding, 15000);
    }

    /**
     * @param url
     * @param request
     * @param outSb
     * @param timeOut 毫秒
     * @return
     */
    public static int sendRequest(String url, Map<String, String> request, StringBuilder outSb, String encoding, int timeOut) {

        //设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeOut).setSocketTimeout(timeOut * 4).build();
        CloseableHttpClient hc = HttpClients.custom().setConnectionManager(initHttpsPoolingMgnt()).build();
        BufferedReader br = null;
        int ret = 0;
        try {
            HttpPost post = new HttpPost(url);
            post.setConfig(requestConfig);
            List<NameValuePair> urlParameters = new ArrayList<NameValuePair>(request.size());
            for (Map.Entry<String, String> v : request.entrySet()) {
                urlParameters.add(new BasicNameValuePair(v.getKey(), v.getValue()));
            }

            HttpEntity postParams = new UrlEncodedFormEntity(urlParameters, encoding);
            post.setEntity(postParams);
            CloseableHttpResponse httpResponse = hc.execute(post);

            br = new BufferedReader
                    (new InputStreamReader(httpResponse.getEntity().getContent(), encoding));
            String inputLine;

            while ((inputLine = br.readLine()) != null) {
                outSb.append(inputLine);
            }
            ret = httpResponse.getStatusLine().getStatusCode();
        } catch (IOException e) {
            //通讯异常
//            logger.debug("通讯异常:" + e.getMessage());
            ret = -1;
            // e.printStackTrace();
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException ignore) {
                }
            }
            try {
                hc.close();
            } catch (IOException ignore) {
            }
        }
        return ret;


    }


    /**
     * 直接向后台 post 字符串
     * 用在xml报文
     *
     * @param url
     * @param str
     * @param charset
     * @param outSb
     * @return
     */
    public static int sendRequest(String url, String str, String charset, StringBuilder outSb) {
        return sendRequest(url, str, charset, outSb, 15000);
    }


    /**
     * @param url
     * @param str     xml 报文  直接put到后台
     * @param charset
     * @param outSb   输出
     * @param timeout 超时时间
     * @return
     */
    public static int sendRequest(String url, String str, String charset, StringBuilder outSb, int timeout) {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(initHttpsPoolingMgnt()).build();
        BufferedReader reader = null;
        int ret = 0;
        try {
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(4 * timeout).setConnectTimeout(timeout).build();
            HttpPost httpPost = new HttpPost(url);
            StringEntity e = new StringEntity(str, charset);
            httpPost.setEntity(e);
            httpPost.setConfig(requestConfig);
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), charset));
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                outSb.append(inputLine);
            }
            ret = httpResponse.getStatusLine().getStatusCode();
        } catch (IOException e) {
            //通讯异常
            ret = -1;
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException ignore) {
                }
            }
            try {
                httpClient.close();
            } catch (IOException ignore) {
            }
        }
        return ret;

    }


    public static void main(String[] args) {

        String url = "http://www.godaddy.com";
        // url = "http://www.google.com";
        StringBuilder outSb = new StringBuilder();
        Map<String, String> request = new HashMap<String, String>();
        request.put("1", "2");
        long t1 = System.currentTimeMillis();
        System.out.println(sendRequest(url, request, outSb));

        System.out.println(System.currentTimeMillis() - t1);

        System.out.println(outSb.toString());
    }
}