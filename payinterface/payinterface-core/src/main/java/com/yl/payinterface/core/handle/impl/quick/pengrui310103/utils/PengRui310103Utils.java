package com.yl.payinterface.core.handle.impl.quick.pengrui310103.utils;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 全时优服 综合工具
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/2/28
 */
public class PengRui310103Utils {

    private static Logger logger = LoggerFactory.getLogger(PengRui310103Utils.class);

    private static String[] bankInfo = {"102,ICBC", "103,ABC", "104,BOC", "105,CCB", "301,BOCOM", "302,CITIC", "303,CEB", "304,HXB", "305,CMBC", "306,CGB", "307,PAB", "308,CMB", "309,CIB", "310,SPDB", "403,PSBC", "313100000013,BOB", "325290000012,BOS", "313110000017,TCCB"};

    public static JSONObject execute(String key, String url, String api, String dataJson, String partnerNo, String orderId) {
        logger.info("订单号：{}, api:{}, 请求报文:{}", orderId, api, dataJson);
        String signKey = key.substring(16);
        String dataKey = key.substring(0, 16);
        String sign = DigestUtils.shaHex(dataJson + signKey);
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("encryptData", encode(PengRui310103AESUtils.encode(dataJson, dataKey)));
            params.put("signData", sign);
            params.put("orderId", orderId);
            params.put("partnerNo", partnerNo);
            params.put("ext", "EZPZ");
            logger.info("请求报文:{}", JsonUtils.toJsonString(params));
            String respInfo = HttpClientUtils.send(HttpClientUtils.Method.POST, url + api, params, false, "UTF-8", 300000);
            logger.info("返回报文密文:{}", respInfo);
            JSONObject res = JSONObject.fromObject(respInfo);
            String result = PengRui310103AESUtils.decode(decode(res.getString("encryptData")), dataKey);
            logger.info("返回报文明文:{}", result);
            String signature = res.getString("signature");
            String reSign = DigestUtils.shaHex(result + signKey);
            if (signature != null && signature.equals(reSign)) {
                return JSONObject.fromObject(result);
            }
        } catch (Exception e) {
            logger.info("订单号：{},api:{},下单报错:{}", orderId, api, e);
        }
        return null;
    }

    /**
     * 银行代码匹配
     */
    public static String[] getBankInfo(String bankCode, String bankName){
        String[] info = {"905", "UNIONPAY", "中国银联"};
        String[] res = null;
        for (int i = 0; i < bankInfo.length ; i++) {
            res = bankInfo[i].split(",");
            if (bankCode.equals(res[1])){
                info = new String[]{res[0], res[1], bankName};
            }
        }
        return info;
    }


    /**
     * 报文头组装
     *
     * @param now
     * @param orderId
     * @param txnCode
     * @param partnerNo
     * @return
     */
    public static JSONObject head(Date now, String orderId, String txnCode, String partnerNo) {
        JSONObject head = new JSONObject();
        head.put("version", "1.0.0");
        head.put("charset", "UTF-8");
        head.put("partnerNo", partnerNo);
        head.put("txnCode", txnCode);
        head.put("orderId", orderId);
        head.put("reqDate", formatDate(now, "yyyyMMdd"));
        head.put("reqTime", formatDate(now, "yyyyMMddHHmmss"));
        return head;
    }

    /**
     * 格式化时间
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * UUID
     *
     * @return
     */
    public static String getOrderIdByUUId() {
        int machineId = 6;
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {
            hashCodeV = -hashCodeV;
        }
        return machineId + String.format("%011d", hashCodeV);
    }

    /**
     * 查询订单号获取
     *
     * @return
     */
    public static String getOrderIdByUUIdTwo() {
        String order = "";
        order = getOrderIdByUUId();
        order += getOrderIdByUUId();
        return order;
    }

    /**
     * 使用Base64加密算法加密字符串 return
     */
    public static String encode(byte[] plainBytes) {
        byte[] b = plainBytes;
        org.apache.commons.codec.binary.Base64 base64 = new org.apache.commons.codec.binary.Base64();
        b = base64.encode(b);
        String s = new String(b, StandardCharsets.UTF_8);
        return s;
    }

    /**
     * 使用Base64解密算法解密字符串 return
     */
    public static byte[] decode(String encodeStr) {
        byte[] b = encodeStr.getBytes(StandardCharsets.UTF_8);
        org.apache.commons.codec.binary.Base64 base64 = new org.apache.commons.codec.binary.Base64();
        b = base64.decode(b);
        return b;
    }

    /**
     * 订单号处理
     */
    public static String getInterfaceRequestId(String requestId) {
        requestId = requestId.replaceAll("TD", "");
        return requestId.replaceAll("-", "");
    }

    /**
     * 订单号还原
     */
    public static String resInterfaceRequestId(String requestId) {
        String q = requestId.substring(8);
        String h = requestId.substring(0, 8);
        return "TD-" + h + "-" + q;
    }
}