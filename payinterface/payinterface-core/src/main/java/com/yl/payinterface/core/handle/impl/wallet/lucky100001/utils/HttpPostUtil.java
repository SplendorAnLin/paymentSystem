package com.yl.payinterface.core.handle.impl.wallet.lucky100001.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * Http 工具类
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/3/10
 */
public class HttpPostUtil {

    public static String execute(String url, List<NameValuePair> req) throws Exception {
    	CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        String resp = null;
        try{
    		httpClient = HttpClients.createDefault();
            httpPost = new HttpPost(url);
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity((List<NameValuePair>)req, "UTF-8");
            httpPost.setEntity(urlEncodedFormEntity);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            if(responseEntity != null)
            {
                InputStream is = responseEntity.getContent();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int ch;
                while((ch = is.read()) != -1)
                {
                    baos.write(ch);
                }
                byte bytes[] = baos.toByteArray();
                resp = new String(bytes, "UTF-8");
            }
        }
        catch(Exception e){
        	throw e;
        }
        finally{
        	try{
                httpPost.releaseConnection();
            }
            catch(Exception e){}
            try{
            	httpClient.close();
            }
            catch(Exception e){}
        }
        
        return resp;
	}
}