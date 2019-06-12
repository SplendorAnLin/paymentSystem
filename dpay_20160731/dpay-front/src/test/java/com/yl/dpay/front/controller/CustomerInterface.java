package com.yl.dpay.front.controller;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.yl.dpay.front.utils.RSAUtil;
import sun.swing.plaf.synth.DefaultSynthStyle;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author AnLin
 * @since 2017/7/10
 */
public class CustomerInterface {

    enum Status {
        I,//状态未明
        RUN,//处理中
        OK,//成功
        FL;//失败
    }

    private static final String CUST_MANAGE_URL = "http://221.239.93.142:9085/front/Transfer.do";
    private static final String CUST_MANAGE = "payecm.MCMerchantsOperateTrs";
    private static final String CUST_QUERY = "payecm.MCMerchantsQry";
    private static final String SINGLE_DPAY = "payecm.MCSinglePaymentTrs";
    private static final String SINGLE_QUERY = "payecm.MCSinglePaymentStateQry";
    private static final String ORDER_NOTIFY = "payecm.MCFileImportNoticeTrs";

    //10080 10065 10081 10082 10083 10084
    public static void main(String[] args) throws Exception{
//        singleDpay();
        singleQuery();
//        custManage();
//        custQuery();
//        orderNotify();
    }

    private static void orderNotify() throws Exception{
        Map<String, String> params = new LinkedHashMap<>();
        params.put("_TransactionId", ORDER_NOTIFY);
        params.put("_MCHTimestamp", String.valueOf(System.currentTimeMillis()));
        params.put("_MCHJnlNo", "CI" + System.currentTimeMillis());
        params.put("NoticeType", "2");
        params.put("FileName", "RQ_PT000002_batchpay_20170815_0001.csv");

        String dataStr = JsonUtils.toJsonString(params);

        System.out.println("请求报文明文");
        System.out.println(dataStr);
        String sign = RSAUtil.sign(dataStr, SIGN_PRIVATEKEY);
        String plainData = RSAUtil.RSAencryptByPublicKey(dataStr.getBytes(), RSA_PUBLICKEY);
        String data = "PlainData=" + URLEncoder.encode(plainData, "utf-8") + "&Signatures=" + URLEncoder.encode(sign, "utf-8");
        System.out.println("请求报文");
        System.out.println(data);
        System.out.println("响应报文");
        System.out.println(sendReq("", CUST_MANAGE_URL+"?"+data));
    }

    private static void singleDpay() throws Exception{
        Map<String, String> params = new LinkedHashMap<>();
        params.put("_TransactionId", SINGLE_DPAY);
        params.put("_MCHTimestamp", String.valueOf(System.currentTimeMillis()));
        params.put("_MCHJnlNo", "CI" + System.currentTimeMillis());
        params.put("PlatformId", "PT000002");
        params.put("MerchantId", "10065");
        params.put("PayeeAcctNo", "6212260200041743341");
        params.put("PayeeAcctName", "小杰");
        params.put("PayeeExNo", "102100004986");
        params.put("PayeeBankName", "中国工商银行股份有限公司北京九棵树支行");
        params.put("Amt", "3");
        params.put("ChannelJnlNo", "TD-"+System.currentTimeMillis());


        String dataStr = JsonUtils.toJsonString(params);

        System.out.println("请求报文明文");
        System.out.println(dataStr);
        String sign = RSAUtil.sign(dataStr, SIGN_PRIVATEKEY);
        String plainData = RSAUtil.RSAencryptByPublicKey(dataStr.getBytes(), RSA_PUBLICKEY);
        String data = "PlainData=" + URLEncoder.encode(plainData, "utf-8") + "&Signatures=" + URLEncoder.encode(sign, "utf-8");
        System.out.println("请求报文");
        System.out.println(data);
        System.out.println("响应报文");
        System.out.println(sendReq("", CUST_MANAGE_URL+"?"+data));
    }

    private static void singleQuery() throws Exception{
        Map<String, String> params = new LinkedHashMap<>();
        params.put("_TransactionId", SINGLE_QUERY);
        params.put("_MCHTimestamp", String.valueOf(System.currentTimeMillis()));
        params.put("_MCHJnlNo", "CI" + System.currentTimeMillis());
        params.put("ChannelJnlNo", "TD-1500876300139");
        params.put("PlatformId", "PT000002");

        String dataStr = JsonUtils.toJsonString(params);

        System.out.println("请求报文明文");
        System.out.println(dataStr);
        String sign = RSAUtil.sign(dataStr, SIGN_PRIVATEKEY);
        String plainData = RSAUtil.RSAencryptByPublicKey(dataStr.getBytes(), RSA_PUBLICKEY);
        String data = "PlainData=" + URLEncoder.encode(plainData, "utf-8") + "&Signatures=" + URLEncoder.encode(sign, "utf-8");
        System.out.println("请求报文");
        System.out.println(data);
        System.out.println("响应报文");
        System.out.println(sendReq("", CUST_MANAGE_URL+"?"+data));
    }

    private static void custManage() throws Exception{
        Map<String, String> params = new LinkedHashMap<>();
        params.put("_TransactionId", CUST_MANAGE);
        params.put("_MCHTimestamp", String.valueOf(System.currentTimeMillis()));
        params.put("_MCHJnlNo", "CI" + System.currentTimeMillis());
        params.put("PlatformId", "PT000002");
        params.put("MerchantId", "10065");
        params.put("MerchantName", "聚合代理商测试112");
        params.put("ClearingType", "T1");
        params.put("OperateType", "3");

        String dataStr = JsonUtils.toJsonString(params);

        System.out.println("请求报文明文");
        System.out.println(dataStr);
        String sign = RSAUtil.sign(dataStr, SIGN_PRIVATEKEY);
        String plainData = RSAUtil.RSAencryptByPublicKey(dataStr.getBytes(), RSA_PUBLICKEY);
        String data = "PlainData=" + URLEncoder.encode(plainData, "utf-8") + "&Signatures=" + URLEncoder.encode(sign, "utf-8");
        System.out.println("请求报文");
        System.out.println(data);
        System.out.println("响应报文");
        System.out.println(sendReq("", CUST_MANAGE_URL+"?"+data));
    }

    private static void custQuery() throws Exception{
        Map<String, String> params = new LinkedHashMap<>();
        params.put("_TransactionId", CUST_QUERY);
        params.put("_MCHTimestamp", String.valueOf(System.currentTimeMillis()));
        params.put("_MCHJnlNo", "CI" + System.currentTimeMillis());
        params.put("PlatformId", "PT000002");
        params.put("MerchantId", "10065");

        String dataStr = JsonUtils.toJsonString(params);

        System.out.println("请求报文明文");
        System.out.println(dataStr);
        String sign = RSAUtil.sign(dataStr, SIGN_PRIVATEKEY);
        String plainData = RSAUtil.RSAencryptByPublicKey(dataStr.getBytes(), RSA_PUBLICKEY);
        String data = "PlainData=" + URLEncoder.encode(plainData, "utf-8") + "&Signatures=" + URLEncoder.encode(sign, "utf-8");
        System.out.println("请求报文");
        System.out.println(data);
        System.out.println("响应报文");
        System.out.println(sendReq(data, CUST_MANAGE_URL+"?"+data));
    }

    //RSA加密公钥 银行方提供
    private static final String RSA_PUBLICKEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCJDM3YWjCKKBEFY4ZgOJ5aant3JacgeyxfHTWBKCFkZqK36F3N/gJ21ee1YkiKD/TMBZLZ0XjphPSkQ4kwO47r6TbKqWqYAAqpJ0QCa8cbfjnNb4gkQK20xtVMChfjTtNdnaEEov1lx79OyOitFdUm7hCEH67BJJArdiPoOdC+xwIDAQAB";
    //签名私钥 商户方自己保存
    private static final String SIGN_PRIVATEKEY="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJC/yqNqyvMrwHgp/E5HMrNnAmFq\n" +
            "5VXHui2QA7CI29ihtO31nOfNCTC857fwZztuzmUTUVZke8M40NSLtUv9YjIb1O60EKGlwQzglxY8\n" +
            "ggvCqMOQx+G897Rlg1D/kcudrcbL/iLMdVpOibvLi29/H4CMZU5qJ4mL2xUx8n5gKStjAgMBAAEC\n" +
            "gYEAh7T4g5uE6NT1HvOmE7GWrDIAPlsc5f5Z44uomeLF0uVQnuRuFbjaS/JfgMkHz+XD5WlmEYwl\n" +
            "qFLlHtBYygSmAQxTdcKdsB74bqekeQlEsDelEVuD6kJRYBUMLEGIt+b/j6LnM1jFmYrGNqVCpWpB\n" +
            "SI06F6mGivEJ+GBKDJOFWakCQQDkybOzKLmeb4VHUVJRI+ZIHfgNzcI6ON4fHksykQrYTXhgzAUA\n" +
            "YzSXIFYxl4rGkeq435qdClV98pjA+jH1jhDVAkEAofc39jI6lISCByEOUi44wnqogsrmvd+N+PsN\n" +
            "M59/Y0u0ZjnRs3OUKeYX3I1qnWKDXvEjpKgoOBovpNpld40nVwJAJi8r2MkBQdonCmIeNQCi3IJz\n" +
            "9gnTUthO6i6qKkRe5P75Cl7Cru/fxSFWgWxjcwTDght/uJoS7rRgnkSjtfICCQJBAJm7zHR1TME3\n" +
            "3SvjJnK+yMVgI56x9L54+YtA0FEVrZaUfxEhBHiu1g3HBxMjb/UfUs7FWC2sJzDJOjvhyLnnU1UC\n" +
            "QDWq8mIqNYW6KpRdl0f2CEMXLsSV9blOsZlxne+lzprV5ZoxCo1Y9kUiqul35xrqZo3DSo+fjf1A\n" +
            "3NCN8DZUaAI=";
    //验签公钥 提供给银行方
    private static final String VERIFY_PUBLIC="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCQv8qjasrzK8B4KfxORzKzZwJhauVVx7otkAOw\n" +
            "iNvYobTt9ZznzQkwvOe38Gc7bs5lE1FWZHvDONDUi7VL/WIyG9TutBChpcEM4JcWPIILwqjDkMfh\n" +
            "vPe0ZYNQ/5HLna3Gy/4izHVaTom7y4tvfx+AjGVOaieJi9sVMfJ+YCkrYwIDAQAB";

    public static String sendReq(String data, String url) throws Exception {

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
        urlConnection.setRequestProperty("Content-Length", String.valueOf(data.getBytes("UTF-8").length));
        urlConnection.setRequestProperty("Content-Type", "text/html");

        urlConnection.setConnectTimeout(60000);
        urlConnection.setReadTimeout(60000);

        out = new BufferedOutputStream(urlConnection.getOutputStream());

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

        urlConnection.disconnect();
        return respContent.toString();
    }
}
