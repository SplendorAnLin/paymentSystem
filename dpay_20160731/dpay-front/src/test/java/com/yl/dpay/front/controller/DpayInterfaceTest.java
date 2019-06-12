package com.yl.dpay.front.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.dpay.front.common.Base64Utils;
import com.yl.dpay.front.common.RSAUtils;
import com.yl.dpay.front.model.DpayInfoBean;
import com.yl.dpay.front.model.DpayQueryReqBean;
import com.yl.dpay.front.model.DpayTradeReqBean;

/**
 * 代付接口测试
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月22日
 * @version V1.0.0
 */
public class DpayInterfaceTest {

	public final static String password = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCEy79cRTBuL/YJo4ZZrkJbzroVqhN4RpoBliBT\r\npubFccppVol/pgf6BQbf8dbve0zreL4exfT9OpsD840xUB5nPBFc+a60U2rtqWJLbyAoWaUZTV4w\r\n57I7uKbMZtD1UdTvNhUgxQ+z6PSWkV+rSt+wrLRAyOl0oCw1mwz1+IP4NQIDAQAB";
	//public final static String password = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDBxAlzWzffrEfzNCSnxbpdIaUa4CsZw4vY9YJG\r\nzyRo9CZV34IGctt5J2BctfCT1nIc15zFgji14shWw8RmEk+bku5clBk6wBudXrs0yJmBku37EoPj\r\nk4f/L/N4rNgrv695yaZQ2et2ngEkbnw8Uux8o9MQAziiaJ6OrZbQdBBsCQIDAQAB";
	
	
	public static void main(String[] args) throws Exception {

		dpay();
		//query();

	}

	@SuppressWarnings("unchecked")
	public static void dpay() throws Exception {
		DpayInfoBean bean = new DpayInfoBean();
		bean.setAccountName("張大");
		bean.setAccountNo("6212260200041743344");
		//bean.setAccountType("INDIVIDUAL");
		bean.setAccountType("OPEN");
		bean.setBankCode("ICBC");
		bean.setAmount("0.25");
		bean.setCutomerOrderCode(Long.toString(System.currentTimeMillis()));
		bean.setDescription("代付");
		bean.setNotifyUrl("http://127.0.0.1:80/dpay-front/callback");
		bean.setCardType("DEBIT");
		bean.setCerType("NAME");
		bean.setValidity("0816");
		bean.setCvv("422");
		bean.setCerNo("130728199008250012");

		String cipherText = null;

		System.out.println(bean.toJsonString());
		cipherText = Base64Utils.encode(RSAUtils.encryptByPublicKey(String.valueOf(JSONObject.fromObject(bean)).getBytes(), password));
		// cipherText = new String(Base64Utils.encode(encryptResult));

		DpayTradeReqBean dpayTradeReqBean = new DpayTradeReqBean();
		dpayTradeReqBean.setCipherText(cipherText);
		//dpayTradeReqBean.setCustomerNo("CUST14661513092691");
		dpayTradeReqBean.setCustomerNo("CUST1466151309269");
		dpayTradeReqBean.setCustomerParam("test");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHMMss");
		dpayTradeReqBean.setCustomerRequestTime(sdf.format(new Date()));
		dpayTradeReqBean.setVersionCode("1.0");
		String url = "http://183.3.223.28:8002/dpay-front/dpayTrade";
		//String url = "http://127.0.0.1:80/dpay-front/dpayTrade";
		System.out.println(JSONObject.fromObject(dpayTradeReqBean).toString());
		String res = sendReq(JSONObject.fromObject(dpayTradeReqBean).toString(), url);
		System.out.println(res);
		Map<String,String> resMap = JsonUtils.toObject(res, Map.class);
		System.out.println(new String(RSAUtils.decryptByPublicKey(Base64Utils.decode(resMap.get("cipherText")), password)));
	}

	@SuppressWarnings("unchecked")
	public static void query() throws Exception {
		DpayQueryReqBean dpayQueryReqBean = new DpayQueryReqBean();
		Map<String,String> queryMap = new HashMap<String, String>();
		queryMap.put("customerOrderCode", "1466434537936");

		//String signData = AESUtils.encryptBase64(JSONObject.fromObject(dpayQueryInfo).toString(), password);
		//String signData = new String(Base64Utils.encode(encryptResult));

		System.out.println(queryMap);
		String signData = Base64Utils.encode(RSAUtils.encryptByPublicKey(String.valueOf(JSONObject.fromObject(queryMap)).getBytes(), password));
		//dpayQueryReqBean.setCustomerNo("CUST1466151309269");
		dpayQueryReqBean.setCustomerNo("CUST1466151309269");
		dpayQueryReqBean.setCipherText(signData);

		String url = "http://183.3.223.28:8002/dpay-front/dpayQuery";
		//String url = "http://127.0.0.1:80/dpay-front/dpayQuery";
		System.out.println(JSONObject.fromObject(dpayQueryReqBean).toString());
		
		String res = sendReq(JSONObject.fromObject(dpayQueryReqBean).toString(), url);
		System.out.println(res);
		Map<String,String> resMap = JsonUtils.toObject(res, Map.class);
		String sss = new String(RSAUtils.decryptByPublicKey(Base64Utils.decode(resMap.get("cipherText")), password));
		System.out.println(sss);
	}

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
			throw new Exception("请求失败");
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
