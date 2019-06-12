package com.yl.client.front.common;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * http工具类
 * 
 * @author 聚合支付有限公司
 * @since 2017年4月24日
 * @version V1.0.0
 */
public class HttpUtils {
	
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

}
