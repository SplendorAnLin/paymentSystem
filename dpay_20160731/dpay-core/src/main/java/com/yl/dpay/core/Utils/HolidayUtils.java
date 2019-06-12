package com.yl.dpay.core.Utils;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * 假日工具类
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月15日
 * @version V1.0.0
 */
public class HolidayUtils {
    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
    
    public static Map<String,Boolean> holidayMap = new HashMap<>();
    
    //配置您申请的KEY
    public static final String APPKEY ="16d3398fbffa0a4fb0347692ddfc137c";

    public static void main(String[] args) {
    	new ArrayList<>();
//    	for(int i=0;i<10;i++){
    		System.out.println(isHoliday());
//    		System.out.println(holidayMap);
//    	}
    }
    
    public static boolean isHoliday(){
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-M-d");
    	String now = simpleDateFormat.format(new Date());
    	if(holidayMap.get(now) != null){
    		return holidayMap.get(now);
    	}
    	
    	holidayMap.clear();
        JSONObject res = getRequest1(now);
        JSONObject result = JSONObject.fromObject(JSONObject.fromObject(JSONObject.fromObject(res.get("result"))).get("data"));
        if("星期日".equals(result.get("weekday")) || "星期六".equals(result.get("weekday")) || res.get("holiday") != null){
        	holidayMap.put(now, true);
        	return true;
        }
        
        holidayMap.put(now, false);
    	return false;
    }
    
    public static boolean isHoliday(Date date){
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-M-d");
    	String now = simpleDateFormat.format(date);
    	if(holidayMap.get(now) != null){
    		return holidayMap.get(now);
    	}
    	
        JSONObject res = getRequest1(now);
        JSONObject result = JSONObject.fromObject(JSONObject.fromObject(JSONObject.fromObject(res.get("result"))).get("data"));
        if("星期日".equals(result.get("weekday")) || "星期六".equals(result.get("weekday")) || res.get("holiday") != null){
        	holidayMap.put(now, true);
        	return true;
        }
        
        holidayMap.put(now, false);
    	return false;
    }

    //1.获取当天的详细信息
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static JSONObject getRequest1(String date){
        String result =null;
        String url ="http://v.juhe.cn/calendar/day";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("key",APPKEY);//您申请的appKey
        params.put("date",date);//指定日期,格式为YYYY-MM-DD,如月份和日期小于10,则取个位,如:2012-1-1

        try {
            result =net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if(object.getInt("error_code")==0){
                return object;
            }else{
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //2.获取当月近期假期
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static void getRequest2(){
        String result =null;
        String url ="http://japi.juhe.cn/calendar/month";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("key",APPKEY);//您申请的appKey
        params.put("year-month","");//指定月份,格式为YYYY-MM,如月份和日期小于10,则取个位,如:2012-1

        try {
            result =net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if(object.getInt("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //3.获取当年的假期列表
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static void getRequest3(){
        String result =null;
        String url ="http://japi.juhe.cn/calendar/year";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("key",APPKEY);//您申请的appKey
        params.put("year","");//指定年份,格式为YYYY,如:2015

        try {
            result =net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if(object.getInt("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return  网络请求字符串
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static String net(String strUrl, Map params,String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if(method==null || method.equals("GET")){
                strUrl = strUrl+"?"+urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if(method==null || method.equals("GET")){
                conn.setRequestMethod("GET");
            }else{
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params!= null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }

    //将map型转为请求参数型
    @SuppressWarnings("rawtypes")
	public static String urlencode(Map<String,Object>data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
