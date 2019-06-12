package com.yl.payinterface.core.handle.impl.realAuth.alipay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.lefu.commons.utils.lang.StringUtils;

/**
 * http工具类
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月22日
 * @version V1.0.0
 */
public class HttpUtils {

	/**
	 * post form
	 * 
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @param bodys
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String host, String path, String method, 
			Map<String, String> headers, 
			Map<String, String> querys, 
			Map<String, String> bodys)
            throws Exception {
		CloseableHttpClient httpclient = HttpClients.custom().build();
		CloseableHttpResponse result = null;
		HttpPost post = null;
		String resData = null;
		try {

	    	post = new HttpPost(buildUrl(host, path, querys));
	        for (Map.Entry<String, String> e : headers.entrySet()) {
	        	post.addHeader(e.getKey(), e.getValue());
	        }

	        if (bodys != null) {
	            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();

	            for (String key : bodys.keySet()) {
	                nameValuePairList.add(new BasicNameValuePair(key, bodys.get(key)));
	            }
	            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairList, "utf-8");
	            formEntity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
	            post.setEntity(formEntity);
	        }
	        
	        result = httpclient.execute(post);
	        
	        if (HttpStatus.SC_OK == result.getStatusLine().getStatusCode()) {
				resData = EntityUtils.toString(result.getEntity());
			}
		} finally {
			if (result != null) {
				result.close();
			}
			if (post != null) {
				post.releaseConnection();
			}
			httpclient.close();
		}

        return resData;
    }
	
	/**
	 * get
	 * 
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @return
	 * @throws Exception
	 */
	public static String doGet(String host, String path, String method, 
			Map<String, String> headers, 
			Map<String, String> querys)
            throws Exception {    	
		CloseableHttpClient httpClient = HttpClients.custom().build();
		CloseableHttpResponse result = null;
		String resData = null;
    	HttpGet get = null;
    	try {
    		get = new HttpGet(buildUrl(host, path, querys));
    		
    		for (Map.Entry<String, String> e : headers.entrySet()) {
            	get.addHeader(e.getKey(), e.getValue());
            }
    		
    		result = httpClient.execute(get);
    		
    		if (HttpStatus.SC_OK == result.getStatusLine().getStatusCode()) {
				resData = EntityUtils.toString(result.getEntity());
			}
		} finally {
			if (result != null) {
				result.close();
			}
			if (get != null) {
				get.releaseConnection();
			}
			httpClient.close();
		}
        
        return resData;
    }
	
	private static String buildUrl(String host, String path, Map<String, String> querys) throws UnsupportedEncodingException {
    	StringBuilder sbUrl = new StringBuilder();
    	sbUrl.append(host);
    	if (!StringUtils.isBlank(path)) {
    		sbUrl.append(path);
        }
    	if (null != querys) {
    		StringBuilder sbQuery = new StringBuilder();
        	for (Map.Entry<String, String> query : querys.entrySet()) {
        		if (0 < sbQuery.length()) {
        			sbQuery.append("&");
        		}
        		if (StringUtils.isBlank(query.getKey()) && !StringUtils.isBlank(query.getValue())) {
        			sbQuery.append(query.getValue());
                }
        		if (!StringUtils.isBlank(query.getKey())) {
        			sbQuery.append(query.getKey());
        			if (!StringUtils.isBlank(query.getValue())) {
        				sbQuery.append("=");
        				sbQuery.append(URLEncoder.encode(query.getValue(), "utf-8"));
        			}        			
                }
        	}
        	if (0 < sbQuery.length()) {
        		sbUrl.append("?").append(sbQuery);
        	}
        }
    	
    	return sbUrl.toString();
    }
	
}
