package com.yl.payinterface.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * @author Shark
 * @Description
 * @Date 2018/4/2 16:25
 */
public class HttpUtil {
    public static final String GET = "GET";
    public static final String POST = "POST";
    private static final int TIME_OUT = 3000;
    private static Logger log = LoggerFactory.getLogger(HttpUtil.class);

    public HttpUtil() {
    }

    public static String sendPost(String url, Map<String, String> param) {
        StringBuffer paramStr=new StringBuffer();
        for (Map.Entry<String, String> entry : param.entrySet()) {
            paramStr.append(entry.getKey()+"="+entry.getValue()+"&");
        }
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(paramStr.toString());
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.error("POST请求异常",e);
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static JSONObject getJsonObject(String url) {
        try {
            URL u = new URL(url);

            try {
                HttpURLConnection uConnection = (HttpURLConnection) u.openConnection();

                try {
                    uConnection.connect();
                    InputStream is = uConnection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }

                    String content = new String(sb);
                    br.close();
                    return JSONObject.parseObject(content);
                } catch (Exception var8) {
                    log.error(var8.getMessage());
                }
            } catch (IOException var9) {
                log.error(var9.getMessage());
            }
        } catch (Exception var10) {
            log.error(var10.getMessage());
        }

        return null;
    }

    public static JSONArray getJsonArray(String url) {
        try {
            URL u = new URL(url);

            try {
                HttpURLConnection uConnection = (HttpURLConnection) u.openConnection();

                try {
                    uConnection.connect();
                    InputStream is = uConnection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }

                    String content = new String(sb);
                    br.close();
                    return (JSONArray) JSON.parseObject(content, JSONArray.class);
                } catch (Exception var8) {
                    log.error(var8.getMessage());
                }
            } catch (IOException var9) {
                log.error(var9.getMessage());
            }
        } catch (Exception var10) {
            log.error(var10.getMessage());
        }

        return null;
    }

    public static String sendReq(String url, String data, String menthod) {
        return sendReq(url, data, menthod, 3000);
    }

    public static String sendReq(String url, String data, String menthod, int timeOut) {
        HttpURLConnection urlConnection = null;
        StringBuffer respContent = new StringBuffer();

        try {
            URL aURL = new URL(url);
            urlConnection = (HttpURLConnection) aURL.openConnection();
            urlConnection.setRequestMethod(menthod);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestProperty("Connection", "close");
            urlConnection.setRequestProperty("Content-Length", String.valueOf(data.getBytes("UTF-8").length));
            urlConnection.setRequestProperty("Content-Type", "text/html");
            urlConnection.setConnectTimeout(timeOut);
            urlConnection.setReadTimeout(timeOut);
            BufferedOutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
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
        } catch (Exception var11) {
            log.error(var11.getMessage());
        }

        urlConnection.disconnect();
        return respContent.toString();
    }

    public static String sendReq(String url, String menthod) {
        return sendReq(url, menthod, 3000);
    }

    public static String sendReq(String url, String menthod, int timeOut) {
        HttpURLConnection urlConnection = null;
        StringBuffer respContent = new StringBuffer();

        try {
            URL aURL = new URL(url);
            urlConnection = (HttpURLConnection) aURL.openConnection();
            urlConnection.setRequestMethod(menthod);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestProperty("Connection", "close");
            urlConnection.setRequestProperty("Content-Type", "text/html");
            urlConnection.setConnectTimeout(timeOut);
            urlConnection.setReadTimeout(timeOut);
            BufferedOutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
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
        } catch (Exception var10) {
            log.error(var10.getMessage());
        }

        urlConnection.disconnect();
        return respContent.toString();
    }

    public static String sendReq(String url) {
        return sendReq(url, "GET");
    }

    public static JSONObject sendJsonReq(String url) {
        return JSONObject.parseObject(sendReq(url));
    }

    public static JSONObject sendAsciiJsonReq(String url) {
        String cn = ascii2native(sendReq(url));
        return cn != null && !"".equals(cn) ? JSONObject.parseObject(cn) : null;
    }

    public static JSONObject sendJsonReq(String url, String menthod) {
        return JSONObject.parseObject(sendReq(url, menthod));
    }

    public static JSONObject sendJsonReq(String url, String data, String menthod) {
        return JSONObject.parseObject(sendReq(url, data, menthod));
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (null != ip && ip.length() > 15 && ip.indexOf(",") > 0) {
            ip = ip.substring(0, ip.indexOf(","));
        }

        return ip;
    }

    public static String toJsonStr(String str) {
        return str.substring(str.indexOf("[") + 1, str.lastIndexOf("]"));
    }

    public static String ascii2native(String asciicode) {
        String[] asciis = asciicode.split("\\\\u");
        String nativeValue = asciis[0];

        try {
            for (int i = 1; i < asciis.length; ++i) {
                String code = asciis[i];
                nativeValue = nativeValue + (char) Integer.parseInt(code.substring(0, 4), 16);
                if (code.length() > 4) {
                    nativeValue = nativeValue + code.substring(4, code.length());
                }
            }

            return nativeValue;
        } catch (NumberFormatException var5) {
            return asciicode;
        }
    }
}

