package com.yl.payinterface.core.handle.impl.auth.hxt100001.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HXTCommonUtils {
	
	public static String sendReq(String url, String xml, String menthod) throws Exception {

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
		urlConnection.setRequestProperty("Content-Type", "text/plain;charset=UTF-8");
		

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
