package com.yl.payinterface.core.handle.impl.remit.futeng100001;


import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.security.DigestUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.lefu.commons.utils.web.HttpClientUtils.Method;
import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.enums.InterfaceTradeStatus;
import com.yl.payinterface.core.handle.impl.remit.cmbc584002.Cmbc584002HandlerImpl;
import com.yl.payinterface.core.handler.RemitHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.utils.CodeBuilder;
import com.yl.payinterface.core.utils.CommonUtils;
import com.yl.payinterface.core.utils.HttpUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;

/**
 * 付腾代付（畅捷）
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017年08月29日
 */
@Service("futengHandler100001")
public class futeng100001HandlerImpl implements RemitHandler {

    private static Logger logger = LoggerFactory.getLogger(futeng100001HandlerImpl.class);
    private static Properties prop = new Properties();
    static {
        try {
            prop.load(Cmbc584002HandlerImpl.class.getClassLoader().getResourceAsStream("serverHost.properties"));
        } catch (IOException e) {
            logger.error("系统异常:", e);
        }
    }

    public static void main(String[] args) {
        Properties prop = new Properties();
        prop.setProperty("mercNo", "896231119452693");
        prop.setProperty("key", "7E623AFD60F48E30FBFC29E938480192");
        prop.setProperty("payUrl", "http://120.27.138.219:8080/CrpsInterface/payG10002");
        prop.setProperty("queryUrl", "http://120.27.138.219:8080/CrpsInterface/findPay");
//        prop.setProperty("notifyUrl", "http://127.0.0.1/");
        Map<String, String> params = new HashMap<>();
        params.put("tradeConfigs", JsonUtils.toJsonString(prop));
        params.put("accountType", "123");
        params.put("accountNo", "6212260200041743341");
        params.put("accountName", "张晓杰");
        params.put("bankName", "中国工商银行九棵树分行");
        params.put("requestNo", CodeBuilder.build("TD", "yyyyMMdd"));
        params.put("requestNo", "TD-20170831-100825060597");
        params.put("amount", "1");
        System.out.println(new futeng100001HandlerImpl().query(params));
    }

    @Resource
    private InterfaceRequestService interfaceRequestService;

    @Override
    public Map<String, String> remit(Map<String, String> map) {

        try {
            Map<String, String> reqMap = new HashMap();
            Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {
            });
            reqMap.put("mercNo", properties.getProperty("mercNo")); // 商户号
            // 结算卡类型 0:对私 1:对公
            if (map.get("accountType").toString().equals("OPEN")) {
                reqMap.put("bankSign", "1");
            } else {
                reqMap.put("bankSign", "0");
            }
            reqMap.put("bankCardNo", map.get("accountNo")); // 结算卡号
            reqMap.put("baknPerson", map.get("accountName")); // 结算姓名
            reqMap.put("provName", "北京"); // 开户行所在省
            reqMap.put("cityName", "北京"); // 开户行所在市
            reqMap.put("settleMoney", String.valueOf((int) AmountUtils.round(AmountUtils.multiply(Double.valueOf(map.get("amount")), 100d), 2, RoundingMode.HALF_UP))); // 结算金额
            reqMap.put("orderId", map.get("requestNo").toString()); // 结算金额
//            reqMap.put("notifyUrl", properties.getProperty("notifyUrl")); // 通知地址

            try {
                Map<String, String> cnaps = getCnaps(map.get("bankName")).get(0);
                reqMap.put("bankName", map.get("bankName")); // 总行名称
                reqMap.put("bankCode", cnaps.get("clearingBankCode")); // 总行联行号
                reqMap.put("bankNameZhiHang", map.get("bankName")); // 开户行支行名称
                reqMap.put("bankCodeZhiHang", cnaps.get("code")); // 开户行支行行号
            } catch (Exception e) {
                logger.error("futeng remit getCnaps error:{}", e);
                Map<String, String> respMap = new HashMap<>();
                respMap.put("tranStat", InterfaceTradeStatus.FAILED.name());
                respMap.put("requestNo", map.get("requestNo"));
                respMap.put("compType", BusinessCompleteType.NORMAL.name());
                respMap.put("amount", map.get("amount"));
                respMap.put("resCode", "9998");
                respMap.put("resMsg", "获取清分行号失败");
                return respMap;
            }
            reqMap.put("sign", DigestUtils.md5DigestAsHex((properties.getProperty("mercNo") + properties.getProperty("key")).getBytes("UTF-8"))); // 签名

            Map<String, String> resData;
            logger.info("付腾代付 请求明文：" + CommonUtils.convertMap2Str(reqMap));
            try {
                String result = HttpUtils.sendReq(properties.getProperty("payUrl") + "?" + CommonUtils.convertMap2Str(reqMap), "", "POST");
                logger.info("付腾代付 响应报文：[{}]", result);
                resData = JsonUtils.toObject(result, new TypeReference<Map<String, String>>() {
                });
            } catch (Exception e) {
                logger.error("futeng remit request error:{}", e);
                Map<String, String> respMaps = new HashMap<>();
                respMaps.put("tranStat", InterfaceTradeStatus.FAILED.name());
                respMaps.put("requestNo", map.get("requestNo").toString());
                respMaps.put("compType", BusinessCompleteType.NORMAL.name());
                respMaps.put("amount", map.get("amount").toString());
                respMaps.put("resCode", "9999");
                respMaps.put("resMsg", "发往通道方失败");
                return respMaps;
            }

            if (resData.get("resultCode").toString().equals("0000")) {//交易成功
                //等待异步通知
                resData.put("tranStat", InterfaceTradeStatus.UNKNOWN.name());
            } else {
                resData.put("tranStat", InterfaceTradeStatus.FAILED.name());
            }
            resData.put("requestNo", map.get("requestNo").toString());
            resData.put("compType", BusinessCompleteType.NORMAL.name());
            resData.put("amount", map.get("amount").toString());
            resData.put("interfaceOrderID", "");
            resData.put("resCode", resData.get("resultCode").toString());
            resData.put("resMsg", resData.get("message").toString());

            return resData;
        } catch (Exception e) {
            logger.error("cmbc remit error:{}", e);
        }

        Map<String, String> resMap = new HashMap<String, String>();
        resMap.put("tranStat", "UNKNOW");
        resMap.put("resCode", "9997");
        resMap.put("resMsg", "未知异常");
        return resMap;
    }

    @Override
    public Map<String, String> query(Map<String, String> map) {
        Map<String, String> reqMap;
        Map<String, String> resData;
        Map<String, String> respMap = new HashMap<String, String>();
        try {
            reqMap = new HashMap();
            Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {
            });

            reqMap.put("mercNo", properties.getProperty("mercNo")); // 商户号
            reqMap.put("orderId", map.get("requestNo")); // 订单号
            reqMap.put("sign", DigestUtils.md5DigestAsHex((properties.getProperty("mercNo") + properties.getProperty("key")).getBytes("UTF-8"))); // 签名

            logger.info("付腾代付 查询请求报文：[{}]", CommonUtils.convertMap2Str(reqMap));
            String result = HttpUtils.sendReq(properties.getProperty("queryUrl") + "?" + CommonUtils.convertMap2Str(reqMap), "", "POST");
            logger.info("付腾代付 查询响应报文：[{}]", result);
            resData = JsonUtils.toObject(result, new TypeReference<Map<String, String>>() {
            });
            logger.info("付腾代付 查询响应参数：[{}]", resData);
            if ("0000".equals(resData.get("resultCode"))) {
                InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(map.get("requestNo"));
                respMap.put("resCode", resData.get("resultCode"));
                respMap.put("resMsg", "付款成功");
                respMap.put("tranStat", "SUCCESS");
                respMap.put("requestNo", map.get("requestNo"));
                respMap.put("amount", String.valueOf(interfaceRequest.getAmount()));
                respMap.put("compType", BusinessCompleteType.AUTO_REPAIR.name());
                respMap.put("interfaceOrderID", "");
            } else if ("0008".equals(resData.get("resultCode"))) {
                InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(map.get("requestNo"));
                respMap.put("resCode", resData.get("resultCode"));
                respMap.put("resMsg", "付款失败");
                respMap.put("tranStat", "FAILED");
                respMap.put("requestNo", map.get("requestNo"));
                respMap.put("amount", String.valueOf(interfaceRequest.getAmount()));
                respMap.put("compType", BusinessCompleteType.AUTO_REPAIR.name());
                respMap.put("interfaceOrderID", "");
            } else {
                respMap.put("tranStat", "UNKNOW");
                respMap.put("resCode", "9997");
                respMap.put("resMsg", "未知异常");
            }
            logger.info("付腾代付 查询结果：[{}]", result);
            return respMap;
        } catch (Exception e) {
            logger.error("futeng query error:{}", e);
        }
        return respMap;
    }

    public List<Map<String, String>> getCnaps(String bankNames) throws Exception {
        if (bankNames == null || "".equals(bankNames)) {
            return new ArrayList<Map<String, String>>();
        }
        String words = "";
        if (bankNames.indexOf(",") > -1) {
            String[] bankName = bankNames.split(",");
            for (int i = 0; i < bankName.length; i++) {
                if (i == 0) {
                    words += "words=" + URLEncoder.encode(bankName[i], "UTF-8");
                    continue;
                }
                words += "&words=" + URLEncoder.encode(bankName[i], "UTF-8");
            }
        } else {
            words = "words=" + URLEncoder.encode(bankNames, "UTF-8");
            words += "&1=1";
        }
        List<Map<String, String>> list = null;
        try {
            String url = (String) prop.get("cachecenter.service.url")
                    + "/cnaps/batchSearch.htm?fields=clearingBankCode&fields=code&fields=providerCode&fields=clearingBankLevel&fields=name";
            String resData = HttpClientUtils.send(Method.POST, url, words, true, Charset.forName("UTF-8").name(), 40000);
            JsonNode cnapsNodes = JsonUtils.getInstance().readTree(resData);
            list = new ArrayList<>();
            for (JsonNode cnapsNode : cnapsNodes) {
                if (cnapsNode.size() == 0) {
                    Map<String, String> map = new HashMap<String, String>();
                    list.add(map);
                    continue;
                }
                Map<String, String> map = new HashMap<String, String>();
                map.put("code", cnapsNode.get("code").getTextValue());
                map.put("clearingBankCode", cnapsNode.get("clearingBankCode").getTextValue());
                map.put("providerCode", cnapsNode.get("providerCode").getTextValue());
                map.put("clearingBankLevel", cnapsNode.get("clearingBankLevel").getTextValue());
                map.put("name", cnapsNode.get("name").toString());
                if ("null".equals(map.get("code"))) {
                    map.put("code", "");
                }
                list.add(map);
            }
        } catch (Exception e) {
            logger.error("系统异常获取银行编码payeePartyId时出错:", e);
        }
        return list;
    }

}
