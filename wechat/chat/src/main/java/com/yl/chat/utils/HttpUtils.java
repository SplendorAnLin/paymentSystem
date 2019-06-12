package com.yl.chat.utils;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * http工具类
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017年4月21日
 */
public class HttpUtils {

    private static Logger log = LoggerFactory.getLogger(HttpUtils.class);

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
            urlConnection.setRequestProperty("Content-Length",
                    String.valueOf(data.getBytes("UTF-8").length));
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
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream(), "utf-8"));
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
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream(), "utf-8"));
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

    public static JSONObject sendJsonReq(String url, String menthod) {
        return JSONObject.fromObject(sendReq(url, menthod));
    }

    public static JSONObject sendJsonReq(String url, String data, String menthod) {
        return JSONObject.fromObject(sendReq(url, data, menthod));
    }

    public static String getWebIp() {
        try {
            URL url = new URL("http://iframe.ip138.com/ip2city.asp");
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    url.openStream()));
            String s = "";
            StringBuffer sb = new StringBuffer("");
            String webContent = "";
            while ((s = br.readLine()) != null) {
                sb.append(s + "\r\n");
            }
            br.close();
            webContent = sb.toString();
            int start = webContent.indexOf("[") + 1;
            int end = webContent.indexOf("]");
            webContent = webContent.substring(start, end);
            return webContent;
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public static String toJsonStr(String str) {
        return str.substring(str.indexOf("[") + 1, str.lastIndexOf("]"));
    }
}