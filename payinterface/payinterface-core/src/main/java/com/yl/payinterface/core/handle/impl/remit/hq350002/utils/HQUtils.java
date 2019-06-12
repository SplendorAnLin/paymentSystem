package com.yl.payinterface.core.handle.impl.remit.hq350002.utils;

import com.lefu.commons.utils.lang.StringUtils;
import com.yl.payinterface.core.utils.MD5Util;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 环球支付工具类
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/24
 */
public class HQUtils {

    public static final Pattern PATTERN = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");

    public static void main(String[] args) {
//        System.out.println(MD5Util.md5("bank_no=6215583202002031321&money=123&order_no=DF20180424115599&user_pid=399071026189840&user_seller=199108&name=周林123456"));
        System.out.println(httpPost("http://www.globalspay.com/User/apply_cash", "bank_no=6215583202002031321&money=2.0&order_no=TD20180425101466237153&user_pid=399071026189840&user_seller=199108&name=周林&sign=382dc0ab449817f54bcdd087359e69e6"));
//        System.out.println(httpPost("http://www.globalspay.com/PayOrder/queryDaifuOrderStatus", "order_no=C2018042452018525701&user_pid=399071026189840&user_seller=199108&sign=e3840f04b69336a3c1fd55fb09eeb8fd"));
    }

    /**
     * unicode 转中文
     *
     * @param str
     * @return
     */
    public static String unicodeToString(String str) {
        Matcher matcher = PATTERN.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch + "");
        }
        return str;
    }

    /**
     * Map转Url
     * @param map
     * @return
     */
    public static String getUrlParamsByMap(Map<String, String> map) {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = StringUtils.substringBeforeLast(s, "&");
        }
        return s;
    }

    public static String getQuerySign(Map<String, String> transMap, String key) {
        StringBuffer arg = new StringBuffer();
        arg.append("order_no=" + transMap.get("order_no") + "&");
        arg.append("user_pid=" + transMap.get("user_pid") + "&");
        arg.append("user_seller=" + transMap.get("user_seller"));
        return MD5Util.md5(arg.toString() + key);
    }

    public static String getSign(Map<String, String> transMap, String key) {
        StringBuffer arg = new StringBuffer();
        arg.append("bank_no=" + transMap.get("bank_no") + "&");
        arg.append("money=" + transMap.get("money") + "&");
        arg.append("order_no=" + transMap.get("order_no") + "&");
        arg.append("user_pid=" + transMap.get("user_pid") + "&");
        arg.append("user_seller=" + transMap.get("user_seller") + "&");
        arg.append("name=" + URLEncoder.encode(transMap.get("name")).toUpperCase());
        return MD5Util.md5(arg.toString() + key);
    }

    public static String httpPost(String url, String req){
        String responseMessage = "";
        StringBuffer response = new StringBuffer();
        HttpURLConnection httpConnection = null;
        OutputStreamWriter out = null;
        BufferedReader reader = null;
        try {
            URL urlPost = new URL(url);
            httpConnection = (HttpURLConnection) urlPost.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setDoInput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setUseCaches(false);
            httpConnection.setInstanceFollowRedirects(true);
            httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConnection.connect();
            out = new OutputStreamWriter(httpConnection.getOutputStream(), "UTF-8");
            out.write(req);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.close();
                }
                if (null != reader) {
                    reader.close();
                }
                if (null != httpConnection) {
                    httpConnection.disconnect();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        try {
            reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(),"UTF-8"));
            while ((responseMessage = reader.readLine()) != null) {
                response.append(responseMessage);
                response.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }
}