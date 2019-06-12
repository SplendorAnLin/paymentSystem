package com.yl.payinterface.core.handle.impl.remit.kingpass100001;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.DateFormatUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.handle.impl.remit.kingpass100001.utils.DesUtils;
import com.yl.payinterface.core.handle.impl.remit.kingpass100001.utils.MerchantUtil;
import com.yl.payinterface.core.handle.impl.remit.kingpass100001.utils.RSASignUtil;
import com.yl.payinterface.core.handler.ChannelReverseHandler;
import com.yl.payinterface.core.handler.RemitHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.utils.CodeBuilder;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;

/**
 * 九派代付
 *
 * @author xiaojie.zhang
 * @version V1.0.0
 * @since 2017年7月6日
 */
@Service("kingPassHandler100001")
public class KingPass100001HandlerImpl implements RemitHandler, ChannelReverseHandler {

    private static Logger logger = LoggerFactory.getLogger(KingPass100001HandlerImpl.class);

    private static Properties cacheProp = new Properties();

    @Resource
    private InterfaceRequestService interfaceRequestService;

    static {
        try {
            cacheProp.load(KingPass100001HandlerImpl.class.getClassLoader().getResourceAsStream("serverHost.properties"));
        } catch (IOException e) {
            logger.error("系统异常:", e);
        }
    }


    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.setProperty("merchantId", "800001500020041");
        properties.setProperty("merchantCertPass", "ZMoRpB");
        properties.setProperty("merchantCertPath", "F:\\pay\\800001500020041.p12");
        properties.setProperty("rootCertPath", "F:\\pay\\rootca.cer");
        properties.setProperty("notifyUrl", "https://kd.alblog.cn/front/callback");
        properties.setProperty("payUrl", "https://jd.kingpass.cn/paygateway/mpsGate/mpsTransaction");
        Map<String, String> map = new HashMap<>();
        map.put("cardType", "DEBIT");
        map.put("accountType", "INDIVIDUAL");
        map.put("amount", "1");
        map.put("requestNo", CodeBuilder.build("TD", "yyyyMMdd"));
        map.put("accountNo", "6215583202002031321");
        map.put("accountName", "周林");
        map.put("interfaceInfoCode", "KingPass_100001_REMIT");
        map.put("tradeConfigs", JsonUtils.toJsonString(properties));
        map.put("orderTime", DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
        System.out.println(new KingPass100001HandlerImpl().query(map));
    }

    @Override
    public Map<String, String> remit(Map<String, String> map) {
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {
        });
        Map<String, String> respMap = new HashMap<>();
        String encoding = "UTF-8";
        String service = "capSingleTransfer";
        Map<String, String> dataMap = new LinkedHashMap<String, String>();
        dataMap.put("charset", "02");
        dataMap.put("version", "1.0");
        dataMap.put("service", service);
        dataMap.put("signType", "RSA256");
        dataMap.put("merchantId", properties.getProperty("merchantId"));
        dataMap.put("requestId", map.get("requestNo").replaceAll("-", "X"));
        dataMap.put("requestTime", DateFormatUtils.format(new Date(), "yyyyMMddhhmmss"));
        dataMap.put("mcSequenceNo", map.get("requestNo").replaceAll("-", "X"));
        dataMap.put("mcTransDateTime", map.get("orderTime"));
        dataMap.put("orderNo", map.get("requestNo").replaceAll("-", "X"));
        dataMap.put("amount", String.valueOf((int) AmountUtils.multiply(Double.valueOf(map.get("amount")), 100)));
        dataMap.put("cardNo", DesUtils.desEnCode(map.get("accountNo")));
        dataMap.put("accName", map.get("accountName"));
        if ("INDIVIDUAL".equals(map.get("accountType"))) {
            dataMap.put("accType", "0");
        } else {
            dataMap.put("accType", "2");
            try {
                Map<String, String> cnaps = getCnaps(map.get("bankName"));
                if (cnaps.size() == 0) {
                    respMap.put("resCode", "9997");
                    respMap.put("resMsg", "获取联行号失败");
                    respMap.put("tranStat", "FAILED");
                    respMap.put("requestNo", map.get("requestNo"));
                    respMap.put("amount", map.get("amount"));
                    return respMap;
                } else {
                    dataMap.put("lBnkNo", cnaps.get("cnapsCode"));
                    dataMap.put("lBnkNam", map.get("bankName"));
                }
            } catch (Exception e) {
                respMap.put("resCode", "9997");
                respMap.put("resMsg", "获取联行号失败");
                respMap.put("tranStat", "FAILED");
                respMap.put("requestNo", map.get("requestNo"));
                respMap.put("amount", map.get("amount"));
                return respMap;
            }
        }
        dataMap.put("crdType", "00");
        dataMap.put("remark", "代付");
        dataMap.put("bnkRsv", "代付");
        dataMap.put("capUse", "0");
        dataMap.put("callBackUrl", properties.getProperty("notifyUrl"));
        dataMap.put("remark1", "{\"mercUsage\":\"结算\"}");
        dataMap.put("remark2", "");
        dataMap.put("remark3", "");
        Map requestMap = new HashMap();
        requestMap.putAll(dataMap);
        Set set = dataMap.keySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            if ((dataMap.get(key) == null) || dataMap.get(key).toString().trim().length() == 0) {
                requestMap.remove(key);
            }
        }
        RSASignUtil util = new RSASignUtil(properties.getProperty("merchantCertPath"), properties.getProperty("merchantCertPass"));
        String reqData = util.coverMap2String(requestMap);
        util.setService(service);
        Map<String, String> resData = null;
        try {
            String merchantSign = util.sign(reqData, encoding);
            String merchantCert = util.getCertInfo();
            String buf = reqData + "&merchantSign=" + merchantSign + "&merchantCert=" + merchantCert;
            logger.info("九派代付下单 接口编号:[{}],订单号:[{}],请求报文:[{}]", map.get("interfaceInfoCode"), map.get("requestNo"), buf);
            String res = MerchantUtil.sendAndRecv(properties.getProperty("payUrl"), buf, "utf-8");
            logger.info("九派代付下单 接口编号:[{}],订单号:[{}],响应报文:[{}]", map.get("interfaceInfoCode"), map.get("requestNo"), res);
            resData = convertParams(res, util);
        } catch (Exception e) {
            logger.error("九派代付下单异常 接口编号:[{}],订单号:[{}],异常信息：[{}]", map.get("interfaceInfoCode"), map.get("requestNo"), e);
        }
        if (resData == null) {
            respMap.put("requestNo", map.get("requestNo"));
            respMap.put("amount", map.get("amount"));
            respMap.put("tranStat", "UNKNOWN");
            return respMap;
        }
        if (resData != null && !"IPS00000".equals(resData.get("rspCode"))) {
            respMap.put("resCode", "9999");
            respMap.put("resMsg", "请求通道异常");
            respMap.put("tranStat", "FAILED");
            respMap.put("requestNo", map.get("requestNo"));
            respMap.put("amount", map.get("amount"));
            if (resData != null) {
                respMap.put("resCode", String.valueOf(resData.get("rspCode")));
                respMap.put("resMsg", String.valueOf(resData.get("rspMessage")));
            }
            respMap.put("compType", BusinessCompleteType.NORMAL.name());
            return respMap;
        } else {
            if ("S".equals(resData.get("orderSts"))) {
                if (!verify(resData, properties.getProperty("rootCertPath"))) {
                    throw new RuntimeException("九派代付下单响应报文 验证签名失败 订单号 : " + map.get("requestNo"));
                }
                respMap.put("resCode", resData.get("orderSts"));
                respMap.put("resMsg", "付款成功");
                respMap.put("tranStat", "SUCCESS");
                respMap.put("requestNo", map.get("requestNo"));
                respMap.put("amount", map.get("amount"));
                respMap.put("compType", BusinessCompleteType.NORMAL.name());
                return respMap;
            } else if ("F".equals(resData.get("orderSts")) || "R".equals(resData.get("orderSts"))) {
                respMap.put("resCode", resData.get("orderSts"));
                respMap.put("resMsg", "付款失败");
                respMap.put("tranStat", "FAILED");
                respMap.put("requestNo", map.get("requestNo"));
                respMap.put("amount", map.get("amount"));
                respMap.put("compType", BusinessCompleteType.NORMAL.name());
                return respMap;
            } else {
                respMap.put("requestNo", map.get("requestNo"));
                respMap.put("amount", map.get("amount"));
                respMap.put("tranStat", "UNKNOWN");
            }
        }
        return respMap;
    }

    @Override
    public Map<String, String> query(Map<String, String> map) {
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {
        });
        Map<String, String> respMap = new HashMap<>();
        String encoding = "UTF-8";
        String service = "capOrderQuery";
        try {
            InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(map.get("requestNo"));
            Map<String, String> reqMap = new LinkedHashMap<>();
            reqMap.put("charset", "02");
            reqMap.put("version", "1.0");
            reqMap.put("service", service);
            reqMap.put("signType", "RSA256");
            reqMap.put("merchantId", properties.getProperty("merchantId"));
            reqMap.put("mcSequenceNo", CodeBuilder.getOrderIdByUUId());
            reqMap.put("requestId", map.get("requestNo").replaceAll("-", "X"));
            reqMap.put("mcTransDateTime", map.get("orderTime"));
            reqMap.put("orderNo", map.get("requestNo").replaceAll("-", "X"));
            reqMap.put("amount", String.valueOf((int) AmountUtils.round(AmountUtils.multiply(interfaceRequest.getAmount(), 100), 2, RoundingMode.HALF_UP)));
            Map requestMap = new HashMap();
            requestMap.putAll(reqMap);
            Set set = reqMap.keySet();
            Iterator iterator = set.iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                if ((reqMap.get(key) == null) || reqMap.get(key).toString().trim().length() == 0) {
                    requestMap.remove(key);
                }
            }
            RSASignUtil util = new RSASignUtil(properties.getProperty("merchantCertPath"), properties.getProperty("merchantCertPass"));
            util.setService(service);
            String reqData = util.coverMap2String(requestMap);
            String merchantSign = util.sign(reqData, encoding);
            String merchantCert = util.getCertInfo();
            String buf = reqData + "&merchantSign=" + merchantSign + "&merchantCert=" + merchantCert;
            logger.info("九派代付查询  接口编号:[{}],订单号:[{}], 请求报文:[{}]", map.get("interfaceInfoCode"), map.get("requestNo"), buf);
            String res = MerchantUtil.sendAndRecv(properties.getProperty("payUrl"), buf, "UTF-8");
            logger.info("九派代付查询  接口编号:[{}],订单号:[{}],响应报文:[{}]", map.get("interfaceInfoCode"), map.get("requestNo"), res);
            Map<String, String> retMap = new LinkedHashMap<String, String>();
            retMap.put("charset", util.getValue(res, "charset"));
            retMap.put("version", util.getValue(res, "version"));
            retMap.put("signType", util.getValue(res, "signType"));
            retMap.put("merchantId", util.getValue(res, "merchantId"));
            retMap.put("rspCode", util.getValue(res, "rspCode"));
            retMap.put("rspMessage", util.getValue(res, "rspMessage"));
            retMap.put("requestId", util.getValue(res, "requestId"));
            retMap.put("responseId", util.getValue(res, "responseId"));
            retMap.put("succQty", util.getValue(res, "succQty"));
            retMap.put("succAmt", util.getValue(res, "succAmt"));
            retMap.put("curPag", util.getValue(res, "curPag"));
            retMap.put("pageNum", util.getValue(res, "pageNum"));
            retMap.put("reqReserved", util.getValue(res, "reqReserved"));
            retMap.put("fileContent", util.getValue(res, "fileContent"));
            retMap.put("ordsts", util.getValue(res, "ordsts"));
            respMap.put("requestNo", map.get("requestNo"));
            respMap.put("amount", map.get("amount"));
            respMap.put("tranStat", "UNKNOWN");
            if ("IPS00000".equals(retMap.get("rspCode"))) {
                if ("S1".equals(retMap.get("ordsts")) || retMap.get("ordsts").substring(0, 1).equals("S")) {
                    respMap.put("resCode", retMap.get("ordsts"));
                    respMap.put("resMsg", "付款成功");
                    respMap.put("tranStat", "SUCCESS");
                    respMap.put("requestNo", map.get("requestNo"));
                    respMap.put("amount", retMap.get("amount"));
                    respMap.put("compType", BusinessCompleteType.AUTO_REPAIR.name());
                    respMap.put("interfaceOrderID", "");
                    return respMap;
                }
            }
        } catch (Exception e) {
            logger.error("九派代付查询异常   接口编号:[{}],订单号:[{}],异常信息：[{}]", map.get("interfaceInfoCode"), map.get("requestNo"), e);
            throw new RuntimeException(e.getMessage());
        }
        return respMap;
    }

    @Override
    public Map<String, String> reverse(Map<String, String> params) {
        return query(params);
    }

    private static Map<String, String> convertParams(String res, RSASignUtil util) {
        if (StringUtils.isBlank(res)) {
            return null;
        }
        Map<String, String> retMap = new HashMap<>();
        retMap.put("charset", (String) util.getValue(res, "charset"));
        retMap.put("version", (String) util.getValue(res, "version"));
        retMap.put("service", (String) util.getValue(res, "service"));
        retMap.put("requestId", (String) util.getValue(res, "requestId"));
        retMap.put("responseId", (String) util.getValue(res, "responseId"));
        retMap.put("responseTime", (String) util.getValue(res, "responseTime"));
        retMap.put("signType", (String) util.getValue(res, "signType"));
        retMap.put("merchantId", (String) util.getValue(res, "merchantId"));
        retMap.put("rspCode", (String) util.getValue(res, "rspCode"));
        retMap.put("rspMessage", (String) util.getValue(res, "rspMessage"));
        retMap.put("mcTransDateTime", (String) util.getValue(res, "mcTransDateTime"));
        retMap.put("orderNo", (String) util.getValue(res, "orderNo"));
        retMap.put("bfbSequenceNo", (String) util.getValue(res, "bfbSequenceNo"));
        retMap.put("mcSequenceNo", (String) util.getValue(res, "mcSequenceNo"));
        retMap.put("mcTransDateTime", (String) util.getValue(res, "mcTransDateTime"));
        retMap.put("cardNo", (String) util.getValue(res, "cardNo"));
        retMap.put("amount", (String) util.getValue(res, "amount"));
        retMap.put("remark1", (String) util.getValue(res, "remark1"));
        retMap.put("remark2", (String) util.getValue(res, "remark2"));
        retMap.put("remark3", (String) util.getValue(res, "remark3"));
        retMap.put("transDate", (String) util.getValue(res, "transDate"));
        retMap.put("transTime", (String) util.getValue(res, "transTime"));
        retMap.put("respMsg", (String) util.getValue(res, "respMsg"));
        retMap.put("orderSts", (String) util.getValue(res, "orderSts"));
        return retMap;
    }

    private static boolean verify(Map<String, String> params, String rootCertPath) {
        RSASignUtil rsautil = new RSASignUtil(rootCertPath);
        String serverSign = params.get("serverSign");
        String serverCert = params.get("serverCert");
        String sf = rsautil.coverMap2String(params);
        boolean flag = false;
        try {
            flag = rsautil.verify(sf, serverSign, serverCert, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("九派代付验证签名异常 : ", e);
        }
        return flag;
    }

    private static Map<String, String> getCnaps(String bankName) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            String words = "";
            words = "words=" + URLEncoder.encode(bankName, "UTF-8");
            words += "&1=1";
            String url = (String) cacheProp.get("cachecenter.service.url")
                    + "/cnaps/batchSearch.htm?fields=clearingBankCode&fields=code&fields=providerCode&fields=clearingBankLevel&fields=name";
            String resData = HttpClientUtils.send(HttpClientUtils.Method.POST, url, words, true, Charset.forName("UTF-8").name(), 40000);
            JsonNode cnapsNodes = JsonUtils.getInstance().readTree(resData);
            for (JsonNode cnapsNode : cnapsNodes) {
                map.put("cnapsCode", cnapsNode.get("code").getTextValue());
                map.put("bankName", cnapsNode.get("name").getTextValue());
                break;
            }
        } catch (Exception e) {
            logger.error("获取联行信息异常:", e);
        }
        return map;
    }
}