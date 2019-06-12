package com.yl.payinterface.core.handle.impl.quick.ldz42010103;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.yl.payinterface.core.handler.QuickPayFilingHandler;
import com.yl.payinterface.core.model.QuickPayFilingInfo;
import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.service.QuickPayFilingInfoService;
import com.yl.payinterface.core.utils.Base64Utils;
import com.yl.payinterface.core.utils.CodeBuilder;
import com.yl.payinterface.core.utils.HttpUtils;
import com.yl.payinterface.core.utils.RSAUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;


@Service("lDZ42010103FilingHandler")
public class LDZ42010103FilingHandlerImpl implements QuickPayFilingHandler {

    private static Logger logger = LoggerFactory.getLogger(LDZ42010103FilingHandlerImpl.class);

    private final static String interfaceInfoCode = "QUICKPAY_LDZ-42010103";

    @Resource
    private QuickPayFilingInfoService quickPayFilingInfoService;
    @Resource
    private InterfaceRequestService interfaceRequestService;



    @Override
    public Map<String, String> filing(Map<String, String> map) {

        Map<String, String> rtMap = new HashMap<String, String>();
        rtMap.put("status", "SUCCESS");

        try {

            Map<String, String> reqMap = new HashMap<String, String>();

            Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {
            });

            //接口信息拼装
            reqMap.put("orderId", CodeBuilder.getOrderIdByUUId());
            reqMap.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            reqMap.put("toAccNo", map.get("bankCardNo"));
            reqMap.put("phoneNo", map.get("phone"));
            reqMap.put("toBankName", map.get("bankName"));
            reqMap.put("toAccType", "PERSONNEL");
            reqMap.put("realName", map.get("name"));
            reqMap.put("identityNo", map.get("idCardNo"));
            reqMap.put("t0DrawRate", map.get("quickPayFee"));
            reqMap.put("t0DrawFee", String.valueOf((int) AmountUtils.multiply(Double.valueOf(map.get("remitFee")), 100)));
            reqMap.put("version", properties.getProperty("version"));


            //保存OR更新数据库
            QuickPayFilingInfo quickPayFilingInfo = new QuickPayFilingInfo();
            quickPayFilingInfo.setBankCardNo(map.get("bankCardNo"));
            quickPayFilingInfo.setBankName(map.get("bankName"));
            quickPayFilingInfo.setClearBankCode(map.get("clearBankCode"));
            quickPayFilingInfo.setIdCardNo(map.get("idCardNo"));
            quickPayFilingInfo.setName(map.get("name"));
            quickPayFilingInfo.setPhone(map.get("phone"));
            quickPayFilingInfo.setQuickPayFee(map.get("quickPayFee"));
            quickPayFilingInfo.setRemitFee(map.get("remitFee"));
            quickPayFilingInfo.setTransCardNo(map.get("cardNo"));
            quickPayFilingInfo.setInterfaceInfoCode(interfaceInfoCode);
            quickPayFilingInfo.setCustomerCode(map.get("customerCode") == null ? CodeBuilder.getOrderIdByUUId() : map.get("customerCode"));

            //添加OR更改报备
            if(map.get("updateFlag").equals("0")) {
                logger.info("来逗阵 快捷支付D0-积分报备 接口编号：[{}]，请求原参数：[{}]", interfaceInfoCode, JsonUtils.toJsonString(reqMap));

                Map<String, String> data = new LinkedHashMap<String, String>();
                data.put("data", Base64Utils.encode(RSAUtils.encryptByPublicKey(JsonUtils.toJsonString(reqMap).getBytes("UTF-8"), properties.getProperty("publicKey"))));
                data.put("url", properties.getProperty("filingUrl") + properties.getProperty("channelType") + "/" + properties.getProperty("appId"));

                logger.info("来逗阵 快捷支付D0-积分报备 接口编号：[{}]，请求报文：[{}]", interfaceInfoCode, data);

                String respInfo = HttpClientUtils.send(HttpClientUtils.Method.POST, "http://util.bank-pay.com/postForWard", data, false, "UTF-8", 10000);

                logger.info("来逗阵 快捷支付D0-积分报备 接口编号：[{}]，响应报文：[{}]", interfaceInfoCode, respInfo);

                Map<String, Object> respMap = JsonUtils.toObject(respInfo, new TypeReference<Map>() {});

                if(Boolean.valueOf(respMap.get("success").toString())){
                    quickPayFilingInfo.setChannelCustomerCode(JsonUtils.toObject(JsonUtils.toJsonString(respMap.get("attributes")), new TypeReference<Map>() {
                    }).get("insCode").toString());

                    //保存到数据库
                    quickPayFilingInfoService.save(quickPayFilingInfo);

                }else {
                    rtMap.put("status", "FAILED");
                }

            }else {
                reqMap.put("insCode", map.get("customerCode"));

                logger.info("来逗阵 银联无跳转D0修改报备 接口编号：[{}]，请求原参数：[{}]", interfaceInfoCode, JsonUtils.toJsonString(reqMap));

                Map<String, String> data = new LinkedHashMap<String, String>();
                data.put("data", Base64Utils.encode(RSAUtils.encryptByPublicKey(JsonUtils.toJsonString(reqMap).getBytes("UTF-8"), properties.getProperty("publicKey"))));
                data.put("url", properties.getProperty("filingUpdateUrl") + properties.getProperty("channelType") + "/" + properties.getProperty("appId"));

                logger.info("来逗阵 银联无跳转D0修改报备 接口编号：[{}]，请求报文：[{}]", interfaceInfoCode, data);

                String respInfo = HttpClientUtils.send(HttpClientUtils.Method.POST, "http://util.bank-pay.com/postForWard", data, false, "UTF-8", 10000);

                logger.info("来逗阵 银联无跳转D0修改报备 接口编号：[{}]，响应报文：[{}]", interfaceInfoCode, respInfo);

                Map<String, Object> respMap = JsonUtils.toObject(respInfo, new TypeReference<Map>() {});

                if(Boolean.valueOf(respMap.get("success").toString())){

                    //更新到数据库
                    quickPayFilingInfo.setChannelCustomerCode(map.get("channelCustomerCode"));
                    quickPayFilingInfo.setCode(map.get("filingInfoCode"));
                    quickPayFilingInfoService.update(quickPayFilingInfo);

                }else {
                    rtMap.put("status", "FAILED");
                }

            }

        } catch (Exception e) {
            logger.info("交易卡号：{}上送商户报错：{}", map.get("cardNo"), e);
            rtMap.put("status", "FAILED");
        }

        return rtMap;
    }

}
