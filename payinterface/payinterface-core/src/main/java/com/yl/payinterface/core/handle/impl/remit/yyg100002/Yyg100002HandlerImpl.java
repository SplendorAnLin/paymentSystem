package com.yl.payinterface.core.handle.impl.remit.yyg100002;


import com.alibaba.fastjson.JSONObject;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.security.DigestUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.lefu.commons.utils.web.HttpClientUtils.Method;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.enums.InterfaceTradeStatus;
import com.yl.payinterface.core.handle.impl.remit.yyg100002.bean.DF0001ReqBean;
import com.yl.payinterface.core.handle.impl.remit.yyg100002.bean.DF0003ReqBean;
import com.yl.payinterface.core.handle.impl.remit.yyg100002.bean.DF0004ReqBean;
import com.yl.payinterface.core.handle.impl.remit.yyg100002.util.Base64;
import com.yl.payinterface.core.handle.impl.remit.yyg100002.util.BeanUtil;
import com.yl.payinterface.core.handle.impl.remit.yyg100002.util.DateUtil;
import com.yl.payinterface.core.handle.impl.remit.yyg100002.util.RSAUtil;
import com.yl.payinterface.core.handle.impl.wallet.yyg100001.utils.HttpClientUtil;
import com.yl.payinterface.core.handler.RemitHandler;
import com.yl.payinterface.core.utils.CodeBuilder;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 丫丫谷代付
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017年08月29日
 */
@Service("remitYyg100002Handler")
public class Yyg100002HandlerImpl implements RemitHandler {

    private static Logger logger = LoggerFactory.getLogger(Yyg100002HandlerImpl.class);

    private static Properties prop = new Properties();
    static {
        try {
            prop.load(Yyg100002HandlerImpl.class.getClassLoader().getResourceAsStream("serverHost.properties"));
        } catch (IOException e) {
            logger.error("系统异常:", e);
        }
    }

    public static void main(String[] args) {
        Properties prop = new Properties();
        prop.setProperty("key", "MzA4MjAyNzYwMjAxMDAzMDBEMDYwOTJBODY0ODg2RjcwRDAxMDEwMTA1MDAwNDgyMDI2MDMwODIwMjVDMDIwMTAwMDI4MTgxMDBBNUUwQjVGOUU0QTk3REU2OEJEQTlFMUJGQjFBMDIwNzEzOURDRTE5RUVEQThFOTUyMzc1QjY4MTU3QjA5NjlDMTI4NEMwQUUzOTQ1QzBBQjMyMDFENUQwNTNGQjgwMkEzMEEzMjk0RjEyMzdBMUEzOUFDNDRGMEUzQTlDNDhCNjRERDNFNEMzQUVGNDY4OEJFMzRGMjk1ODVGNDFDMDcwNTU0MzEzNDc4ODAwNEVCRUZFRENDOENCQkVCREUwQUQ2NkMzNEQzNzYzOTM5OTkzQzgzNkY2NDU5NTgxRkRDMTQ5NEU1M0JCRkY1NUVBMDBDN0JBRDU0QkVDNjY4MjlEMDIwMzAxMDAwMTAyODE4MDJBNjVEMDNEOEEyQkE0NDk3QzJGQTlCRkZGMjM3QkE3MzE2NTYxNUI5MTg4N0Q2RjMzM0MxMDI0RTkzM0YyOUFGQkM0QzBCNzA3NUU4M0NCN0UyMUE3RjNGMkIzNTJFM0Y1QzA4RTdCNkU2RDk3QkMwODdGRUFCMEY5NUMxRURENDY3QkU0QkY5MkI3QzcxNjNBRTJEN0JCNTZEMTU5Qjg2QTkyMzZFNzRDM0RCN0M1QzlGQUQ5ODU1MzY0ODQ0MTAwNzY4RDg4MjI1NTY0RDMzMTZEM0E5Njc5MjQ3REVDMUMyMkY1MUZCNEU5REI3MDE0NjgyMDUzMDE4ODYxMjUwMjQxMDBGQkI3MkVEQkFGRDE4RjlBMDE1RkEyMDQzOTg5M0FBQTFFMzZDRTU0MTQ2MDE2REE2MTM5NzEwMEQyRjY4MDA0QjEzNjE1MjExMEZEN0FGMTJDMTA4QkU3NTIyNDQ0N0M0RjZCRjMwNjRFNEMzODEzNEIzNTNGM0M3QzhFMkIxNzAyNDEwMEE4QjM4MDQ5OTk5M0QxRTY0MEJCQTI2RkY3RkMwODdEOTI3MUE1QjY4ODYxMjU4M0Y4RDFFQUI3QjI1RDNFQUQ3RjNBODI2NTc1NTRDNEJGQjQ0RTM2MUIyNTUyMjc1QjQ5QjQ2MzYzQUIzQ0I0MDVCQTIzNkUwMDlENjk4MDZCMDI0MDI0RjNENjcwMkZENjZFRTM2N0YyMzcyMUIxQTRBMEI1MUFBQzY3MEJENkQ1RTg2NEY0QzJFRjAwRjRGNzc1MDFCQjU2M0EzMUQ5ODFBQ0NCQkNGMTRDRTg4Rjk5N0Q2ODU2NkM3RDg3REU3NEI4MDJCNTE2QzMwQUM5MkE0MUNGMDI0MDExQTJBQTFGODc4OTc3NjBDRDk1OEZENjhBQTJGMzM0NDU1MUQyMTNGMUNGQ0RGRjJDQ0NBQ0VGQzUxQTkwNDlDQ0NBMEUwNTkzMkQ2ODVGRURGNjVCMUI5RDVDMjgzNzE3Q0U1RUIxNzU5RTIzRTc5MTVDRDBDQzA5QTg5NEJGMDI0MTAwQUE5RDQ4QTg5Nzg4Qzk0OTJCQzVERTZBNjBGMTlCMDBDNTRFMzQyQjU5RDEzQjg3NUI0NUE2MTgzRkI2QjUxNEMxQzc2OUY0QjAyOTM2RjAzMTZGREVDNTRCRTYwNjE1REUwQkM5ODVDNTQ1NThGRDRBOUYxMTNFQTQwQTQyQzQ=");
        prop.setProperty("dpayUrl", "http://47.93.80.210:8011/api/polypay/batchPay");
        prop.setProperty("proxyMerid", "020001");
//        prop.setProperty("notifyUrl", "http://127.0.0.1/");
        Map<String, String> params = new HashMap<>();
        params.put("tradeConfigs", JsonUtils.toJsonString(prop));
        params.put("accountType", "123");
        params.put("accountNo", "6212260200041743341");
        params.put("accountName", "张晓杰");
        params.put("bankName", "中国工商银行九棵树分行");
        params.put("requestNo", CodeBuilder.build("TD", "yyyyMMdd"));
//        params.put("requestNo", "TD-20170831-100825060597");
        params.put("amount", "1");
        params.put("orderTime", DateUtil.getTodayAndTime());

        System.out.println(new Yyg100002HandlerImpl().query(params));
    }

    @Override
    public Map<String, String> remit(Map<String, String> map) {
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {
        });
        DF0003ReqBean df0003ReqBean = new DF0003ReqBean();
        DF0001ReqBean.BatchPayHeadReq batchPayHeadReq = new DF0001ReqBean.BatchPayHeadReq();
        batchPayHeadReq.setTranCode("DF0003");
        batchPayHeadReq.setChannelVersion("1.0");
        batchPayHeadReq.setApiVersion("1.0");
        batchPayHeadReq.setChannelDate(DateUtil.getDateStrCompact(new Date()));
        batchPayHeadReq.setChannelTime(DateUtil.getTimeStrCompact(new Date()));
        batchPayHeadReq.setChannelSerial(map.get("requestNo"));
        batchPayHeadReq.setProxyID(properties.getProperty("proxyMerid"));
        df0003ReqBean.setHead(batchPayHeadReq);
        df0003ReqBean.setCardByName(map.get("accountName"));
        df0003ReqBean.setCardByNo(map.get("accountNo"));
        try {
            Map<String, String> cnaps = getCnaps(map.get("bankName")).get(0);
            String providerCode=cnaps.get("providerCode");
            if (StringUtils.notBlank(providerCode)&&isProviderCode(providerCode,properties)){
                df0003ReqBean.setBankCode(providerCode);
            }else {
                Map<String, String> respMap = new HashMap<>();
                respMap.put("tranStat", InterfaceTradeStatus.FAILED.name());
                respMap.put("requestNo", map.get("requestNo"));
                respMap.put("compType", BusinessCompleteType.NORMAL.name());
                respMap.put("amount", map.get("amount"));
                respMap.put("resCode", "9998");
                respMap.put("resMsg", "银行类型不支持");
                return respMap;
            }
        } catch (Exception e) {
            logger.error("YY谷 remit getCnaps error:{}", e);
            Map<String, String> respMap = new HashMap<>();
            respMap.put("tranStat", InterfaceTradeStatus.FAILED.name());
            respMap.put("requestNo", map.get("requestNo"));
            respMap.put("compType", BusinessCompleteType.NORMAL.name());
            respMap.put("amount", map.get("amount"));
            respMap.put("resCode", "9998");
            respMap.put("resMsg", "清分行获取失败");
            return respMap;
        }

        df0003ReqBean.setTradeTime(DateUtil.getDateStrCompact2(new Date()));
        df0003ReqBean.setBankMobile("");
        df0003ReqBean.setOrderId(map.get("requestNo"));
        df0003ReqBean.setAmount(map.get("amount"));
        df0003ReqBean.setAccType("00");
        df0003ReqBean.setBankCity("北京市");
        df0003ReqBean.setBankOpenName(map.get("bankName"));
        df0003ReqBean.setBankProvcince("北京市");

        try {
            df0003ReqBean.getHead().setSign(sign(JSONObject.toJSONString(df0003ReqBean), properties.getProperty("key")));
            logger.info("发送数据为:[" + JSONObject.toJSONString(df0003ReqBean) + "]");
            String rsp = HttpClientUtil.doPost(properties.getProperty("dpayUrl"), null, JSONObject.toJSONString(df0003ReqBean), "utf-8");
            logger.info("响应数据为:[" + rsp + "]");
            Map<String, Object> resMap=JsonUtils.toObject(rsp,Map.class);
            Map<String, String> resData= JsonUtils.toObject(JsonUtils.toJsonString(resMap.get("head")),Map.class);
            if ("000000".equals(resData.get("resCode"))) {
                resData.put("tranStat", InterfaceTradeStatus.UNKNOWN.name());
            } else {
                resData.put("tranStat", InterfaceTradeStatus.FAILED.name());
            }
            resData.put("requestNo", map.get("requestNo"));
            resData.put("compType", BusinessCompleteType.NORMAL.name());
            resData.put("amount", map.get("amount"));
            resData.put("interfaceOrderID", resData.get("tranSerial"));
            resData.put("resCode", resData.get("resCode"));
            resData.put("resMsg", resData.get("resMessage"));

            return resData;
        } catch (UnsupportedEncodingException e) {
            logger.error("丫丫谷代付异常,{}",e);
        }
        Map<String, String> resMap = new HashMap<String, String>();
        resMap.put("tranStat", "UNKNOW");
        resMap.put("resCode", "9997");
        resMap.put("resMsg", "未知异常");
        return resMap;
    }

    private boolean isProviderCode(String providerCode,Properties properties){
        String [] bankCodes=properties.getProperty("bankList").split(",");
        for (String bankCode:bankCodes){
            if (bankCode.equals(providerCode)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<String, String> query(Map<String, String> map) {
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {
        });
        String interfaceRequestId = map.get("requestNo");
        String interfaceInfoCode = map.get("interfaceInfoCode");
        Map<String, String> respMap = new HashMap<String, String>();

        DF0004ReqBean df0004ReqBean = new DF0004ReqBean();
        DF0001ReqBean.BatchPayHeadReq batchPayHeadReq = new DF0001ReqBean.BatchPayHeadReq();
        batchPayHeadReq.setTranCode("DF0004");
        batchPayHeadReq.setChannelVersion("1.0");
        batchPayHeadReq.setApiVersion("1.0");
        batchPayHeadReq.setChannelDate(DateUtil.getTodayDate());
        batchPayHeadReq.setChannelTime(DateUtil.getTimeStrCompact(new Date()));
        batchPayHeadReq.setChannelSerial(UUID.randomUUID().toString().substring(0, 20));
        batchPayHeadReq.setProxyID(properties.getProperty("proxyMerid"));
        df0004ReqBean.setHead(batchPayHeadReq);
        df0004ReqBean.setOrderId(interfaceRequestId);
        try {
            df0004ReqBean.setTradeDate(DateUtil.getDateStrCompact(DateUtil.parseDateShort(map.get("orderTime"))));
            df0004ReqBean.getHead().setSign(sign(JSONObject.toJSONString(df0004ReqBean), properties.getProperty("key")));
            logger.info("YY谷 代付查询发送数据为:[" + JSONObject.toJSONString(df0004ReqBean) + "]");
            String rsp = HttpClientUtil.doPost(properties.getProperty("dpayUrl"), null, JSONObject.toJSONString(df0004ReqBean), "utf-8");
            logger.info("YY谷 代付查询响应数据为:[" + rsp + "]");
            Map<String, Object> resMap=JsonUtils.toObject(rsp,Map.class);
            if ("4".equals(resMap.get("status"))) {
                respMap.put("resCode", "0000");
                respMap.put("resMsg", "付款成功");
                respMap.put("tranStat", "SUCCESS");
                respMap.put("amount", resMap.get("amount").toString());
                respMap.put("compType", BusinessCompleteType.AUTO_REPAIR.name());
                respMap.put("requestNo", interfaceRequestId);
            } else if ("3".equals(resMap.get("status"))) {
                respMap.put("resCode", "9999");
                respMap.put("resMsg", "付款失败");
                respMap.put("tranStat", "FAILED");
                respMap.put("amount", resMap.get("amount").toString());
                respMap.put("requestNo", interfaceRequestId);
                respMap.put("compType", BusinessCompleteType.AUTO_REPAIR.name());
            }
            logger.info("丫丫谷代付 查询结果：[{}]", respMap);
            return respMap;
        } catch (Exception e) {
            logger.error("丫丫谷代付异常,{}",e);
        }
        respMap.put("requestNo", interfaceRequestId);
        respMap.put("interfaceOrderID", interfaceRequestId);
        respMap.put("interfaceCode", interfaceInfoCode);
        respMap.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        respMap.put("completeTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        respMap.put("compType", BusinessCompleteType.NORMAL.toString());
        respMap.put("tranStat", "UNKNOWN");
        return respMap;
    }


    public static String sign(String reqStr, String privateKey) throws UnsupportedEncodingException {
        logger.info("丫丫谷 加密：" + reqStr);
        String digestStr = DigestUtils.sha1DigestAsHex(reqStr.getBytes("UTF-8"));
        String signValue = RSAUtil.encodeByPrivateKey(digestStr, new String(Base64.decode(privateKey)));
        return signValue;
    }

    private static String createSign(Object o) {
        //将reqMap排序
        SortedMap<String, Object> sm = new TreeMap<String, Object>(BeanUtil.bean2Map(o));
        //按序拼接
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> sme : sm.entrySet()) {
            String v = (String) sme.getValue();
            //空字段不参加签名
            if (null == v || v.length() == 0)
                continue;
            sb.append("&").append(sme.getKey()).append("=").append(v);
        }
        return sb.substring(1);

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