package com.yl.dpay.core.Utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HttpUtils {

    /**
     * 通用请求
     * @param reqJson
     * @param url
     * @return
     * @throws Exception
     */
    public static String sendReq(String reqJson, String url) throws Exception {

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
        urlConnection.setRequestProperty("Content-Length", String.valueOf(reqJson.getBytes("UTF-8").length));
        urlConnection.setRequestProperty("Content-Type", "text/html");

        urlConnection.setConnectTimeout(60000);
        urlConnection.setReadTimeout(60000);

        out = new BufferedOutputStream(urlConnection.getOutputStream());

        out.write(reqJson.getBytes("UTF-8"));
        out.flush();
        out.close();
        int responseCode = urlConnection.getResponseCode();

        if (responseCode != 200) {
            throw new Exception("请求失败 : " + responseCode);
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
