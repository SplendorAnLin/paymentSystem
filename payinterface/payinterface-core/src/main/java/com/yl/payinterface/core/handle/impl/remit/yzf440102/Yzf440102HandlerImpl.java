package com.yl.payinterface.core.handle.impl.remit.yzf440102;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.handle.impl.wallet.yzf440101.utils.HttpUtil;
import com.yl.payinterface.core.handler.RemitHandler;
import com.yl.payinterface.core.utils.MD5Util;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 云支付  代付
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/7/9
 */
@Service("remitYzf440102Handler")
public class Yzf440102HandlerImpl implements RemitHandler {

    private static final Logger logger = LoggerFactory.getLogger(Yzf440102HandlerImpl.class);

    public static final Pattern PATTERN = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");

    public static void main(String[] args) {
        try {
            Map<String, String> req = new HashMap<>();
            String[][] a = {{"fxddh", "DF-" + System.currentTimeMillis()}, {"fxdate", new SimpleDateFormat("YYYYMMDDhhmmss").format(new Date())} ,
                    {"fxfee","2.00"} , {"fxbody","6215583202002031321"} , {"fxname","周林"} , {"fxaddress","中国工商银行"}};
            req.put("fxid", "2018114");
            req.put("fxaction", "repay");
            req.put("fxbody", JsonUtils.toJsonString(a));
            req.put("fxsign", getSign(req, "SACUiCByqsHivexWjBYnafueWvJkIcHR"));
            String res = unicodeToString(HttpUtil.httpRequest("http://juhe.juhegame.com/Pay", req, "POST", "UTF-8"));
            System.out.println(res);
            JSONObject dfRes = JSONObject.fromObject(res);
            JSONArray result = JSONArray.fromObject(dfRes.getString("fxbody"));
            dfRes = result.getJSONObject(0);
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
    }

    @Override
    public Map<String, String> remit(Map<String, String> map) {
        Map<String, String> req = new HashMap<>();
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});
        String interfaceRequestId = map.get("requestNo");
        String accountNo = map.get("accountNo");
        String accountName = map.get("accountName");
        String bankName = map.get("bankName");
        String amount = String.valueOf(map.get("amount"));
        Map<String, String> respMap = new HashMap<>();
        try {
            String[][] a = {{"fxddh", interfaceRequestId}, {"fxdate", new SimpleDateFormat("YYYYMMDDhhmmss").format(new Date())} ,
                    {"fxfee", amount} , {"fxbody", accountNo} , {"fxname", accountName} , {"fxaddress", bankName}};
            req.put("fxid", properties.getProperty("userId"));
            req.put("fxaction", "repay");
            req.put("fxbody", JsonUtils.toJsonString(a));
            req.put("fxsign", getSign(req, properties.getProperty("key")));
            logger.info("云支付 代付下单报文：{}", req);
            String res = unicodeToString(HttpUtil.httpRequest(properties.getProperty("url"), req, "POST", "UTF-8"));
            logger.info("云支付 代付下单返回报文：{}", res);
            Map<String, String> resPar = JsonUtils.toObject(res, new TypeReference<Map<String, String>>() {
            });
            if ("0".equals(resPar.get("fxstatus"))) {
                respMap.put("resCode", "");
                respMap.put("resMsg", "");
                respMap.put("tranStat", "FAILED");
                respMap.put("requestNo", interfaceRequestId);
                respMap.put("amount", amount);
                respMap.put("compType", BusinessCompleteType.NORMAL.name());
            } else {
                respMap.put("requestNo", interfaceRequestId);
                respMap.put("amount", amount);
                respMap.put("tranStat", "UNKNOWN");
            }
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
        return respMap;
    }

    @Override
    public Map<String, String> query(Map<String, String> map) {
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {});
        String requestNo = map.get("requestNo");
        Map<String, String> req = new HashMap<>();
        Map<String, String> respMap = new HashMap<>();
        try {
            String[][] a = {{"fxddh", requestNo}};
            req.put("fxid", properties.getProperty("userId"));
            req.put("fxaction", "repayquery");
            req.put("fxbody", JsonUtils.toJsonString(a));
            req.put("fxsign", getSign(req, properties.getProperty("key")));
            logger.info("云支付 代付查询报文：{}", req);
            String res = HttpUtil.httpRequest(properties.getProperty("url"), req, "POST", "UTF-8");
            logger.info("云支付 代付查询返回报文：{}", res);
            Map<String, String> resPar = JsonUtils.toObject(res, new TypeReference<Map<String, String>>() {
            });
            if ("1".equals(resPar.get("fxstatus"))) {
                resPar = JsonUtils.toObject(resPar.get("fxbody"), new TypeReference<Map<String, String>>() {
                });
                if ("1".equals(resPar.get("fxstatus"))) {
                    respMap.put("resCode", "0000");
                    respMap.put("resMsg", "付款成功");
                    respMap.put("tranStat", "SUCCESS");
                    respMap.put("requestNo", requestNo);
                    respMap.put("amount", resPar.get("fxfee"));
                } else if ("3".equals(resPar.get("fxstatus")) || "2".equals(resPar.get("fxstatus")) || "-1".equals(resPar.get("fxstatus"))) {
                    respMap.put("resCode", "9999");
                    respMap.put("resMsg", resPar.get("fxcode"));
                    respMap.put("tranStat", "FAILED");
                    respMap.put("requestNo", requestNo);
                    respMap.put("amount", resPar.get("fxfee"));
                } else {
                    respMap.put("requestNo", requestNo);
                    respMap.put("amount", map.get("amount"));
                    respMap.put("tranStat", "UNKNOWN");
                }
            }
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
        return respMap;
    }

    private static String getSign(Map<String, String> req, String key){
        StringBuffer sb = new StringBuffer();
        sb.append(req.get("fxid"));
        sb.append(req.get("fxaction"));
        sb.append(req.get("fxbody"));
        sb.append(key);
        return MD5Util.md5(sb.toString());
    }

    /**
     * unicode 转中文
     *
     * @param str
     * @return
     */
    private static String unicodeToString(String str) {
        try {
            Matcher matcher = PATTERN.matcher(str);
            char ch;
            while (matcher.find()) {
                ch = (char) Integer.parseInt(matcher.group(2), 16);
                str = str.replace(matcher.group(1), ch + "");
            }
            return str;
        } catch (Exception e) {
            logger.error("unicode 转中文异常", e);
        }
        return null;
    }
}