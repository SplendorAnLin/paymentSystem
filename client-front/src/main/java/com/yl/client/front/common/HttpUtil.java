package com.yl.client.front.common;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

public class HttpUtil {
	private static Logger log = LoggerFactory.getLogger(HttpUtil.class);

	public static String sendReq(String url, String data, String menthod) {

		java.net.HttpURLConnection urlConnection = null;
		BufferedOutputStream out;
		StringBuffer respContent = new StringBuffer();
		try {
			java.net.URL aURL = new java.net.URL(url);
			urlConnection = (java.net.HttpURLConnection) aURL.openConnection();

			urlConnection.setRequestMethod(menthod);

			urlConnection.setDoInput(true);
			urlConnection.setDoOutput(true);
			urlConnection.setUseCaches(false);

			urlConnection.setRequestProperty("Connection", "close");
			urlConnection.setRequestProperty("Content-Length", String.valueOf(data.getBytes("UTF-8").length));
			urlConnection.setRequestProperty("Content-Type", "text/html");

			urlConnection.setConnectTimeout(5000);
			urlConnection.setReadTimeout(5000);

			out = new BufferedOutputStream(urlConnection.getOutputStream());

			out.write(data.getBytes("UTF-8"));
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
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		urlConnection.disconnect();
		return respContent.toString();
	}

	public static String sendReq(String url, String menthod) {
		java.net.HttpURLConnection urlConnection = null;
		BufferedOutputStream out;
		StringBuffer respContent = new StringBuffer();
		try {
			java.net.URL aURL = new java.net.URL(url);
			urlConnection = (java.net.HttpURLConnection) aURL.openConnection();

			urlConnection.setRequestMethod(menthod);

			urlConnection.setDoInput(true);
			urlConnection.setDoOutput(true);
			urlConnection.setUseCaches(false);

			urlConnection.setRequestProperty("Connection", "close");
			urlConnection.setRequestProperty("Content-Type", "text/html");

			urlConnection.setConnectTimeout(5000);
			urlConnection.setReadTimeout(5000);

			out = new BufferedOutputStream(urlConnection.getOutputStream());
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
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		urlConnection.disconnect();
		return respContent.toString();
	}

	public static String sendReq(String url) {
		return sendReq(url, "GET");
	}

	public static JSONObject sendJsonReq(String url) {
		return JSONObject.fromObject(sendReq(url));
	}
	
	public static JSONObject sendAsciiJsonReq(String url) {
		String cn=ascii2native(sendReq(url));
		if (cn!=null&&!"".equals(cn)) {
			return JSONObject.fromObject(cn);
		}
		return null;
	}

	public static JSONObject sendJsonReq(String url, String menthod) {
		return JSONObject.fromObject(sendReq(url, menthod));
	}

	public static JSONObject sendJsonReq(String url, String data, String menthod) {
		return JSONObject.fromObject(sendReq(url, data, menthod));
	}
	
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static String toJsonStr(String str) {
		return str.substring(str.indexOf("[") + 1, str.lastIndexOf("]"));
	}
	
	public static String ascii2native ( String asciicode )
    {
        String[] asciis = asciicode.split ("\\\\u");
        String nativeValue = asciis[0];
        try
        {
            for ( int i = 1; i < asciis.length; i++ )
            {
                String code = asciis[i];
                nativeValue += (char) Integer.parseInt (code.substring (0, 4), 16);
                if (code.length () > 4)
                {
                    nativeValue += code.substring (4, code.length ());
                }
            }
        }
        catch (NumberFormatException e)
        {
            return asciicode;
        }
        return nativeValue;
    }

}
