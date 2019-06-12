package com.yl.payinterface.core.handle.impl.wallet.cib330000;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 公告工具类
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月18日
 * @version V1.0.0
 */
public class CommonsUtil {

	@SuppressWarnings("rawtypes")
	public static SortedMap getValue(Object thisObj) {
		SortedMap<String, String> map = new TreeMap<String, String>();
		Class c;
		try {
			c = Class.forName(thisObj.getClass().getName());
			Method[] m = c.getMethods();
			for (int i = 0; i < m.length; i++) {
				String method = m[i].getName();
				if (method.equals("getClass")) {
					continue;
				}
				if (method.startsWith("get")) {
					try {
						Object value = m[i].invoke(thisObj);
						if (value != null) {
							String key = method.substring(3);
							key = key.substring(0, 1).toUpperCase() + key.substring(1);
							String tmp = method.toString().substring(3, method.length()).toLowerCase();
							map.put(tmp, value != null ? value.toString() : "");
						}
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return map;
	}

	public static String getNonceStr() {
		Random random = new Random();
		return MD5Util.MD5Encode(String.valueOf(random.nextInt(10000)), "UTF-8");
	}

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
		urlConnection.setRequestProperty("Content-Type", "text/html");

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
