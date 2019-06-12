package com.yl.online.gateway.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * http工具类
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月17日
 * @version V1.0.0
 */
public class HttpUtils {
	
	private HttpUtils(){}
	
	private static HttpUtils httpUtils;
	
	public static HttpUtils instance(){
		if(httpUtils == null){
			httpUtils = new HttpUtils();
		}
		return httpUtils;
	}

	@SuppressWarnings("rawtypes")
	public String getPostResponse(String url, Map parmMap) {
		String response = null;
		PostMethod post = new PostMethod(url);
		HttpClient client = new HttpClient();

		Iterator it = parmMap.entrySet().iterator();
		NameValuePair[] param = new NameValuePair[parmMap.size()];
		int i = 0;
		while (it.hasNext()) {
			Entry parmEntry = (Entry) it.next();
			param[i++] = new NameValuePair((String) parmEntry.getKey(),
					(String) parmEntry.getValue());
		}

		post.setRequestBody(param);

		try {
			int statusCode = client.executeMethod(post);
			if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
					|| statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
				Header locationHeader = post.getResponseHeader("location");
				String location = null;
				if (locationHeader != null) {
					location = locationHeader.getValue();
					response = this.getPostResponse(location, param);// 用跳转后的页面重新请求。
				}
			} else if (statusCode == HttpStatus.SC_OK) {
				response = post.getResponseBodyAsString();
			}
		} catch (IOException ex) {
		} finally {
			post.releaseConnection();
		}
		return response;
	}

	public String getPostResponse(String url, NameValuePair[] param) {
		String response = null;
		PostMethod post = new PostMethod(url);
		HttpClient client = new HttpClient();

		post.setRequestBody(param);

		try {
			int statusCode = client.executeMethod(post);
			if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
					|| statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
				Header locationHeader = post.getResponseHeader("location");
				String location = null;
				if (locationHeader != null) {
					location = locationHeader.getValue();
					response = this.getPostResponse(location, param);// 用跳转后的页面重新请求。
				}
			} else if (statusCode == HttpStatus.SC_OK) {
				response = post.getResponseBodyAsString();
			}
		} catch (IOException ex) {
		} finally {
			post.releaseConnection();
		}
		return response;
	}
	
	public static JSONObject sendReqJson(String url, String xml ,String menthod) throws Exception {

		java.net.HttpURLConnection urlConnection = null;
		BufferedOutputStream out;
		StringBuffer respContent = new StringBuffer();

		java.net.URL aURL = new java.net.URL(url);
		urlConnection = (java.net.HttpURLConnection) aURL.openConnection();

		urlConnection.setRequestMethod(menthod);

		urlConnection.setDoInput(true);
		urlConnection.setDoOutput(true);
		urlConnection.setUseCaches(false);

		urlConnection.setRequestProperty("Connection", "close");
		urlConnection.setRequestProperty("Content-Length", String.valueOf(xml.getBytes("UTF-8").length));
		urlConnection.setRequestProperty("Content-Type", "application/json");

		urlConnection.setConnectTimeout(60000);
		urlConnection.setReadTimeout(60000);

		out = new BufferedOutputStream(urlConnection.getOutputStream());

		out.write(xml.getBytes("UTF-8"));
		out.flush();
		out.close();
		int responseCode = urlConnection.getResponseCode();

		if (responseCode != 200) {
			throw new Exception("connection failed!");
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
		String line;

		while ((line = reader.readLine()) != null) {
			respContent.append(line);
		}

		urlConnection.disconnect();
		return JSONObject.fromObject(respContent.toString());
	}
	
	public static String sendReq(String paramsStr, String url, String charset) throws Exception {

		java.net.HttpURLConnection urlConnection = null;
		BufferedOutputStream out;
		StringBuffer respContent = new StringBuffer();

		java.net.URL aURL = new java.net.URL(url);
		urlConnection = (java.net.HttpURLConnection) aURL.openConnection();

		urlConnection.setRequestMethod("POST");

		urlConnection.setDoInput(true);
		urlConnection.setDoOutput(true);
		urlConnection.setUseCaches(false);

		urlConnection.setRequestProperty("Connection", "close");
		urlConnection.setRequestProperty("Content-Length", String.valueOf(paramsStr.getBytes(charset).length));
		urlConnection.setRequestProperty("Content-Type", "text/html");

		urlConnection.setConnectTimeout(60000);
		urlConnection.setReadTimeout(60000);

		out = new BufferedOutputStream(urlConnection.getOutputStream());

		out.write(paramsStr.getBytes(charset));
		out.flush();
		out.close();
		int responseCode = urlConnection.getResponseCode();

		if (responseCode != 200) {
			throw new Exception("请求失败");
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
		String line;

		while ((line = reader.readLine()) != null) {
			respContent.append(line);
		}

		urlConnection.disconnect();
		return respContent.toString();
	}

	public static String sendReq2(String url, String xml, String menthod) throws Exception {

		java.net.HttpURLConnection urlConnection = null;
		BufferedOutputStream out;
		StringBuffer respContent = new StringBuffer();

		java.net.URL aURL = new java.net.URL(url);
		urlConnection = (java.net.HttpURLConnection) aURL.openConnection();

		urlConnection.setRequestMethod(menthod);

		urlConnection.setDoInput(true);
		urlConnection.setDoOutput(true);
		urlConnection.setUseCaches(false);

		urlConnection.setRequestProperty("Connection", "close");
		urlConnection.setRequestProperty("Content-Length", String.valueOf(xml.getBytes("UTF-8").length));
		// urlConnection.setRequestProperty("Content-Type", "text/html");


		urlConnection.setConnectTimeout(60000);
		urlConnection.setReadTimeout(60000);

		out = new BufferedOutputStream(urlConnection.getOutputStream());

		out.write(xml.getBytes("UTF-8"));
		out.flush();
		out.close();
		int responseCode = urlConnection.getResponseCode();

		if (responseCode != 200) {
			throw new Exception("请求失败");
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
		String line;

		while ((line = reader.readLine()) != null) {
			respContent.append(line);
			respContent.append("\r\n");
		}

		urlConnection.disconnect();
		return respContent.toString();
	}
}