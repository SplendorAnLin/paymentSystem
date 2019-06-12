package com.yl.pay.pos.http;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;


public class HttpClient {  
    private static Logger log = Logger.getLogger(HttpClient.class);  
      
//    public static String post(String url, Map<String, String> params) {
//    	log.info(params.toString());
//        DefaultHttpClient httpclient = new DefaultHttpClient();
//        String body = null;
//        httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
//        httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
//        log.info("create httppost:" + url);
//        HttpPost post = postForm(url, params);
//
//        body = invoke(httpclient, post);
//
//        httpclient.getConnectionManager().shutdown();
//
//        return body;
//    }
//
//    public static String get(String url) {
//        DefaultHttpClient httpclient = new DefaultHttpClient();
//        String body = null;
//
//        log.info("create httppost:" + url);
//        HttpGet get = new HttpGet(url);
//        body = invoke(httpclient, get);
//
//        httpclient.getConnectionManager().shutdown();
//
//        return body;
//    }
//
//
//    private static String invoke(DefaultHttpClient httpclient,
//            HttpUriRequest httpost) {
//
//        HttpResponse response = sendRequest(httpclient, httpost);
//        String body = paseResponse(response);
//
//        return body;
//    }
//
//    private static String paseResponse(HttpResponse response) {
//        log.info("get response from http server..");
//        HttpEntity entity = response.getEntity();
//
//        log.info("response status: " + response.getStatusLine());
//        String charset = EntityUtils.getContentCharSet(entity);
//        log.info(charset);
//
//        String body = null;
//        try {
//            body = EntityUtils.toString(entity);
//            log.info(body);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return body;
//    }
//
//    private static HttpResponse sendRequest(DefaultHttpClient httpclient,
//            HttpUriRequest httpost) {
//        log.info("execute post...");
//        HttpResponse response = null;
//
//        try {
//            response = httpclient.execute(httpost);
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return response;
//    }
//
//    private static HttpPost postForm(String url, Map<String, String> params){
//
//        HttpPost httpost = new HttpPost(url);
//        List<NameValuePair> nvps = new ArrayList <NameValuePair>();
//
//        Set<String> keySet = params.keySet();
//        for(String key : keySet) {
//            nvps.add(new BasicNameValuePair(key, params.get(key)));
//        }
//
//        try {
//            log.info("set utf-8 form entity to httppost");
//            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        return httpost;
//    }
//
//
//    public static void main(String[] args) throws InterruptedException {
//
//    	boolean flag = true;
//    	while(flag){
//    		String url ="http://211.149.208.184:8080/YangLianPay/ylBPosCallBackServlet";
//        	Map<String, String> params = new HashMap<String, String>();
//        	params.put("returnCode", "00");
//        	params.put("orderId", "BP201511110953123");
//        	params.put("deviceId", "111111");
//        	params.put("amount", "111111");
//        	params.put("type", "0");
//        	params.put("CardNo", "622");
//        	String resp = HttpClient.post(url, params);
//        	log.info(resp);
//        	if("SUCCESS".equals(resp)){
//        		flag=false;
//        	}else{
//        		Thread.sleep(30000);
//        	}
//
//    	}
//	}
}  