package com.yl.payinterface.core.handle.impl.wallet.yyg100001.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AUTH;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

/*
 * 利用HttpClient进行post请求的工具类
 */
public class HttpClientUtilProxy {

	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtilProxy.class);

	/**
	 * 默认的http连接超时时间
	 */
	private final static int DEFAULT_CONN_TIMEOUT = 30000; // 30s
	/**
	 * 默认的http read超时时间
	 */
	private final static int DEFAULT_READ_TIMEOUT = 30000; // 30s
	/**
	 * 报文编码格式
	 */
	private final static String CHARSET = "UTF-8";

	/**
	 * 
	 * FunctionName:post请求 默认utf-8
	 *
	 * @since JDK 1.7
	 * @param params
	 * @return
	 */
	public static String doPost(String url, String params) {
		return doPost(url, null, params, CHARSET);
	}

	/**
	 * 
	 * FunctionName:发送接收请求
	 *
	 * @since JDK 1.7
	 * @param url
	 * @param
	 * @return
	 */
	public static String doPost(String url, Map<String, String> header, String param, String charset) {
		String headerJson = "";
		long start = -1;
		long end = -1;
		CloseableHttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			start = System.currentTimeMillis();
			httpClient = createSSLClientDefault();
			httpPost = getPostWithTimeOut(url);
			HttpHost proxy = new HttpHost("60.205.142.169", 19999, "http");
			
			BasicScheme proxyAuth = new BasicScheme();
			// Make client believe the challenge came form a proxy  
			proxyAuth.processChallenge(new BasicHeader(AUTH.PROXY_AUTH, "BASIC realm=default"));
			BasicAuthCache authCache = new BasicAuthCache();
			authCache.put(proxy, proxyAuth);  
			  
			CredentialsProvider credsProvider = new BasicCredentialsProvider();
			credsProvider.setCredentials(  
			        new AuthScope(proxy),
			        new UsernamePasswordCredentials("mixell", "mixell123"));
			  
			HttpClientContext context = HttpClientContext.create();
			context.setAuthCache(authCache);  
			context.setCredentialsProvider(credsProvider);  
			
			RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
			httpPost.setConfig(config);
			StringEntity stringEntity = new StringEntity(param, charset);// 解决中文乱码问题
			stringEntity.setContentEncoding(charset);
			stringEntity.setContentType("application/json");
			if (header != null && !header.isEmpty()) {
				headerJson = JSON.toJSONString(header, SerializerFeature.WriteMapNullValue);
				for (String key : header.keySet()) {
					httpPost.setHeader(key, header.get(key));
				}
			}
			httpPost.setEntity(stringEntity);
			HttpResponse response = httpClient.execute(httpPost,context);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				end = System.currentTimeMillis();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charset);
					printLog(url, headerJson, param, result, start, end, null);
				}
			}
		} catch (Exception ex) {
			end = System.currentTimeMillis();
			printLog(url, headerJson, param, result, start, end, ex);
		} finally {
			if (httpPost != null) {
				httpPost.releaseConnection();
			}
			try {
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				end = System.currentTimeMillis();
				printLog(url, headerJson, param, result, start, end, e);
			}
		}
		return result;
	}

	/**
	 * 打印Log
	 */
	private static void printLog(String url, String header, String param, String result, long start, long end,
                                 Exception e) {
		if (logger.isInfoEnabled()) {
			logger.info("调用业务平台:url:[{" + url + "}],header:[{" + header + "}],params:[{" + param + "}],result:[{"
					+ result + "}],start:[{" + start + "}],end:[{" + end + "}],useTime:[{" + (end - start)
					+ "}ms],exception:[{" + e + "}]");
		}
	}

	public static CloseableHttpClient createSSLClientDefault() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				// 信任所有
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
			// return HttpClients.custom().setSSLSocketFactory(sslsf).build();
			// return HttpClients.custom().setRedirectStrategy(new
			// DefaultRedirectStrategy()).setSSLSocketFactory(sslsf).build();
			return HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategyCustom()).setSSLSocketFactory(sslsf)
					.build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return HttpClients.createDefault();
	}

	public static HttpPost getPostWithTimeOut(String url) {
		int timeOut = DEFAULT_CONN_TIMEOUT;
		HttpPost httpPost = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeOut).setConnectTimeout(timeOut)
				.build();// 设置请求和传输超时时间
		httpPost.setConfig(requestConfig);
		return httpPost;
	}

	public static String postReq(String requrl, String req) {
		return postReq(requrl, req, DEFAULT_CONN_TIMEOUT, DEFAULT_READ_TIMEOUT);
	}

	/**
	 * http post,有返回String
	 * 
	 * @param requrl
	 * @param req
	 * @param connTimeOut
	 * @param readTimeOut
	 * @return
	 */
	public static String postReq(String requrl, String req, int connTimeOut, int readTimeOut) {
		try {
			HttpURLConnection conn = null;
			try {
				URL url = new URL(requrl);
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoInput(true);
				conn.setDoOutput(true); // POST
				conn.setRequestMethod("POST");
				conn.setUseCaches(false);
				conn.setConnectTimeout(connTimeOut);
				conn.setReadTimeout(readTimeOut);
				conn.connect();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), CHARSET);
			out.write(req);
			out.flush();
			out.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), CHARSET));
			StringBuilder sb = new StringBuilder();
			char[] buff = new char[2048];
			int cnt = 0;
			while ((cnt = in.read(buff)) != -1)
				sb.append(buff, 0, cnt);
			in.close();
			String rtStr = sb.toString();
			logger.info("http post ,\nurl:[{" + requrl + "}],\nsend date:[{" + req + "}],\nresult:[{" + rtStr + "}]");
			return rtStr;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据给定的链接获取所有的重定向位置
	 * 
	 * @param link
	 *            给定的链接
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static List<URI> getAllRedirectLocations(CloseableHttpClient httpClient, String link)
			throws ClientProtocolException, IOException {
		List<URI> redirectLocations = null;
		CloseableHttpResponse response = null;
		try {
			HttpClientContext context = HttpClientContext.create();
			HttpGet httpGet = new HttpGet(link);
			response = httpClient.execute(httpGet, context);
			// 获取所有的重定向位置
			redirectLocations = context.getRedirectLocations();
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return redirectLocations;
	}
}