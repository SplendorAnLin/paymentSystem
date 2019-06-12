/******************************************************************************
 *                                                                             
 *                      Woodare PROPRIETARY INFORMATION                        
 *                                                                             
 *          The information contained herein is proprietary to Woodare         
 *           and shall not be reproduced or disclosed in whole or in part      
 *                    or used for any design or manufacture                    
 *              without direct written authorization from FengDa.              
 *                                                                             
 *            Copyright (c) 2013 by Woodare.  All rights reserved.             
 *                                                                             
 *****************************************************************************/
package com.yl.payinterface.core.handle.impl.auth.mipay.utils;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * ClassName: YiZhiFuApi
 * 
 * @description
 * @author Xinxing Jiang
 * @Date Feb 15, 2016
 * 
 */
public class HttpUtils {

	public static String post(String url, String body) throws Exception {
		CloseableHttpClient httpclient = HttpClients.custom().build();
		HttpPost post = null;
		String resData = null;
		CloseableHttpResponse result = null;
		try {
			post = new HttpPost(url);
			post.setConfig(RequestConfig.custom().setConnectTimeout(30000).setSocketTimeout(30000).build());
			post.setHeader("Content-Type", "application/json");
			if (body != null) {
				HttpEntity entity2 = new StringEntity(body, Consts.UTF_8);
				post.setEntity(entity2);
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

	public static String get(String url) throws Exception {
		CloseableHttpClient httpclient = HttpClients.custom().build();
		HttpGet get = null;
		String resData = null;
		CloseableHttpResponse result = null;
		try {
			get = new HttpGet(url);
			get.setConfig(RequestConfig.custom().setConnectTimeout(30000).setSocketTimeout(30000).build());
			result = httpclient.execute(get);
			if (HttpStatus.SC_OK == result.getStatusLine().getStatusCode()) {
				resData = new String(new String(EntityUtils.toByteArray(result.getEntity()), "GBK").getBytes(), "UTF-8");
			}
		} finally {
			if (result != null) {
				result.close();
			}
			if (get != null) {
				get.releaseConnection();
			}
			httpclient.close();
		}
		return resData;
	}
}
