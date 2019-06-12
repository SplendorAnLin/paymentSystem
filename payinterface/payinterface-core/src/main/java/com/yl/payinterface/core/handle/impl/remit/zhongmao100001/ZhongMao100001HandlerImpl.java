package com.yl.payinterface.core.handle.impl.remit.zhongmao100001;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.DateFormatUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.exception.BusinessRuntimeException;
import com.yl.payinterface.core.handle.impl.remit.zhongmao100001.utils.SSLClient;
import com.yl.payinterface.core.handle.impl.remit.zhongmao100001.utils.SignUtils;
import com.yl.payinterface.core.handler.ChannelReverseHandler;
import com.yl.payinterface.core.handler.RemitHandler;
import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.utils.CodeBuilder;
import com.yl.payinterface.core.utils.CommonUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 中茂代付
 *
 * @author xiaojie.zhang
 * @version V1.0.0
 * @since 2017年7月6日
 */
@Service("zhongMaoHandler100001")
public class ZhongMao100001HandlerImpl implements RemitHandler, ChannelReverseHandler {

    private static Logger logger = LoggerFactory.getLogger(ZhongMao100001HandlerImpl.class);
    private static Properties prop = new Properties();

    static {
        try {
            prop.load(ZhongMao100001HandlerImpl.class.getClassLoader().getResourceAsStream("serverHost.properties"));
        } catch (IOException e) {
            logger.error("系统异常:", e);
        }
    }

    @Resource
    private InterfaceRequestService interfaceRequestService;
    @Resource
    private CustomerInterface customerInterface;


    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.setProperty("merNo", "305420054110001");
        properties.setProperty("productId", "0203");
        properties.setProperty("queryProductId", "04");
        properties.setProperty("privateKeyPath", "C:\\Users\\Administrator\\Desktop\\888440058111002\\305420054110001_prv.pem");
        properties.setProperty("publicKeyPath", "C:\\Users\\Administrator\\Desktop\\888440058111002\\305420054110001_pub.pem");
        properties.setProperty("notifyUrl", "http://127.0.0.1/");
        properties.setProperty("payUrl", "https://cqlsy.cmbc.com.cn:8412/payment-gate-web/gateway/api/backTransReq");
        properties.setProperty("queryUrl", "https://cqlsy.cmbc.com.cn:8412/payment-gate-web/gateway/api/backTransReq");
        Map<String, String> map = new HashMap<>();
        map.put("cardType", "DEBIT");
        map.put("accountType", "INDIVIDUAL");
        map.put("amount", "1");
        map.put("requestNo", CodeBuilder.build("TD", "yyyyMMdd"));
        map.put("accountNo", "6212260200041743341");
        map.put("accountName", "张晓杰");
//        map.put("requestNo", "TD-20170904-100192459399"); //1499316452417
        map.put("interfaceInfoCode", "ZhongMao_100001_REMIT");
        map.put("tradeConfigs", JsonUtils.toJsonString(properties));
        map.put("orderTime", DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
        map.put("orderTime", "20170904111442");
        System.out.println(new ZhongMao100001HandlerImpl().remit(map));
    }

    @Override
    public Map<String, String> remit(Map<String, String> map) {

        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {
        });
        DefaultHttpClient httpClient = null;
        try {
            httpClient = new SSLClient();
        } catch (Exception e) {
            throw new BusinessRuntimeException("中茂代付 初始化http 失败", e);
        }
        HttpPost postMethod = new HttpPost(properties.getProperty("payUrl"));
        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
        nvps.add(new BasicNameValuePair("requestNo", map.get("requestNo")));
        nvps.add(new BasicNameValuePair("version", "V1.0"));
        nvps.add(new BasicNameValuePair("productId", properties.getProperty("productId")));
        nvps.add(new BasicNameValuePair("transId", "07"));//07
        nvps.add(new BasicNameValuePair("merNo", properties.getProperty("merNo")));
        nvps.add(new BasicNameValuePair("orderDate", new SimpleDateFormat("yyyyMMdd").format(new Date())));
        nvps.add(new BasicNameValuePair("orderNo", map.get("requestNo")));
        nvps.add(new BasicNameValuePair("notifyUrl", properties.getProperty("notifyUrl")));
        nvps.add(new BasicNameValuePair("transAmt", String.valueOf((int) AmountUtils.multiply(Double.valueOf(map.get("amount")), 100d))));
        if ("OPEN".equals(map.get("accountType"))) {
            nvps.add(new BasicNameValuePair("isCompay", "1"));
        } else {
            nvps.add(new BasicNameValuePair("isCompay", "0"));
        }
        nvps.add(new BasicNameValuePair("phoneNo", getPhone(map.get("customerNo"))));
        nvps.add(new BasicNameValuePair("customerName", map.get("accountName")));
        nvps.add(new BasicNameValuePair("acctNo", map.get("accountNo")));
        nvps.add(new BasicNameValuePair("note", "代付"));
        Map<String, String> resData = null;
        Map<String, String> respMap = new HashMap<>();
        try {
            nvps.add(new BasicNameValuePair("signature", SignUtils.signData(nvps, properties.getProperty("privateKeyPath"))));
            postMethod.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            logger.info("中茂代付下单 接口编号:[{}],订单号:[{}],请求报文:{}", map.get("interfaceInfoCode"), map.get("requestNo"), nvps);
            HttpResponse resp = httpClient.execute(postMethod);
            String str = EntityUtils.toString(resp.getEntity(), "UTF-8");
            logger.info("中茂代付下单 接口编号:[{}],订单号:[{}],响应报文:[{}]", map.get("interfaceInfoCode"), map.get("requestNo"), str);
            int statusCode = resp.getStatusLine().getStatusCode();
            if (200 == statusCode) {
                boolean signFlag = SignUtils.verferSignData(str, properties.getProperty("publicKeyPath"));
                if (signFlag) {
                    resData = CommonUtils.kvStr2Map(str);
                    if ("0000".equals(resData.get("respCode"))) {
                        respMap.put("resCode", resData.get("respCode"));
                        respMap.put("resMsg", "付款成功");
                        respMap.put("tranStat", "SUCCESS");
                        respMap.put("requestNo", map.get("requestNo"));
                        respMap.put("amount", map.get("amount"));
                        respMap.put("compType", BusinessCompleteType.NORMAL.name());
                        return respMap;
                    } else if ("9997".equals(resData.get("respCode")) || "P000".equals(resData.get("respCode")) || "9999".equals(resData.get("respCode"))) {
                        respMap.put("requestNo", map.get("requestNo"));
                        respMap.put("amount", map.get("amount"));
                        respMap.put("tranStat", "UNKNOWN");
                        return respMap;
                    } else {
                        respMap.put("resCode", resData.get("respCode"));
                        respMap.put("resMsg", resData.get("respDesc"));
                        respMap.put("tranStat", "FAILED");
                        respMap.put("requestNo", map.get("requestNo"));
                        respMap.put("amount", map.get("amount"));
                        respMap.put("compType", BusinessCompleteType.NORMAL.name());
                        return respMap;
                    }
                }
            } else {
                respMap.put("resCode", "9999");
                respMap.put("resMsg", "请求通道异常");
                respMap.put("tranStat", "FAILED");
                respMap.put("requestNo", map.get("requestNo"));
                respMap.put("amount", map.get("amount"));
                respMap.put("compType", BusinessCompleteType.NORMAL.name());
                return respMap;
            }
        } catch (Exception e) {
            logger.error("中茂代付下单异常 接口编号:[{}],订单号:[{}],异常信息：[{}]", map.get("interfaceInfoCode"), map.get("requestNo"), e);
        }
        return respMap;
    }

    @Override
    public Map<String, String> query(Map<String, String> map) {
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {
        });
        Map<String, String> respMap = new HashMap<>();
        DefaultHttpClient httpClient = null;
        try {
            httpClient = new SSLClient();
        } catch (Exception e) {
            throw new BusinessRuntimeException("中茂代付查询 初始化http 失败", e);
        }
        HttpPost postMethod = new HttpPost(properties.getProperty("queryUrl"));
        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
        nvps.add(new BasicNameValuePair("requestNo", map.get("requestNo")));
        nvps.add(new BasicNameValuePair("version", "V1.0"));
        nvps.add(new BasicNameValuePair("transId", "04"));//07
        nvps.add(new BasicNameValuePair("merNo", properties.getProperty("merNo")));
        nvps.add(new BasicNameValuePair("orderDate", map.get("orderTime").substring(0, 8)));
        nvps.add(new BasicNameValuePair("orderNo", map.get("requestNo")));
        Map<String, String> retMap = null;
        try {
            nvps.add(new BasicNameValuePair("signature", SignUtils.signData(nvps, properties.getProperty("privateKeyPath"))));
            postMethod.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            logger.info("中茂代付查询  接口编号:[{}],订单号:[{}], 请求报文:[{}]", map.get("interfaceInfoCode"), map.get("requestNo"), nvps);
            HttpResponse resp = httpClient.execute(postMethod);
            String str = EntityUtils.toString(resp.getEntity(), "UTF-8");

            logger.info("中茂代付查询  接口编号:[{}],订单号:[{}],响应报文:[{}]", map.get("interfaceInfoCode"), map.get("requestNo"), str);
            int statusCode = resp.getStatusLine().getStatusCode();
            if (200 == statusCode) {
                boolean signFlag = SignUtils.verferSignData(str, properties.getProperty("publicKeyPath"));
                if (signFlag) {
                    retMap = CommonUtils.kvStr2Map(str);
                    if ("0000".equals(retMap.get("respCode"))) {
                        if ("0000".equals(retMap.get("origRespCode"))) {
                            respMap.put("resCode", retMap.get("origRespCode"));
                            respMap.put("resMsg", "付款成功");
                            respMap.put("tranStat", "SUCCESS");
                            respMap.put("requestNo", map.get("requestNo"));
                            respMap.put("amount", String.valueOf(AmountUtils.divide(Double.valueOf(retMap.get("transAmt")), 100d, 2, RoundingMode.HALF_UP)));
                            respMap.put("compType", BusinessCompleteType.AUTO_REPAIR.name());
                            respMap.put("interfaceOrderID", "");
                            return respMap;
                        } else if ("9998".equals(retMap.get("origRespCode")) || "99".equals(retMap.get("origRespCode"))) {
                            respMap.put("resCode", retMap.get("origRespCode"));
                            respMap.put("resMsg", retMap.get("origRespDesc"));
                            respMap.put("tranStat", "FAILED");
                            respMap.put("requestNo", map.get("requestNo"));
                            respMap.put("amount", retMap.get("amount"));
                            respMap.put("compType", BusinessCompleteType.AUTO_REPAIR.name());
                            respMap.put("interfaceOrderID", "");
                            return respMap;
                        } else {
                            respMap.put("requestNo", map.get("requestNo"));
                            respMap.put("amount", map.get("amount"));
                            respMap.put("tranStat", "UNKNOWN");
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("中茂代付查询异常   接口编号:[{}],订单号:[{}],异常信息：[{}]", map.get("interfaceInfoCode"), map.get("requestNo"), e);
            throw new RuntimeException(e.getMessage());
        }
        return respMap;
    }

    @Override
    public Map<String, String> reverse(Map<String, String> params) {
        return query(params);
    }

    private String getPhone(String customerNo) {
        // todo
//        Customer customer = customerInterface.getCustomer(customerNo);
//        if (customer != null) {
//            return customer.getPhoneNo();
//        }
        return "17600126400";
    }
}
