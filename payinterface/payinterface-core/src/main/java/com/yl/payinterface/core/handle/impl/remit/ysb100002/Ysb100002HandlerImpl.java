package com.yl.payinterface.core.handle.impl.remit.ysb100002;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.handle.impl.remit.ysb100002.utils.signUtils;
import com.yl.payinterface.core.handler.RemitHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 银生宝代付
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/7/9
 */
@Service("remitYsb100002Handler")
public class Ysb100002HandlerImpl implements RemitHandler {

    private static final Logger logger = LoggerFactory.getLogger(Ysb100002HandlerImpl.class);

    public static void main(String[] args) {
        Map<String, String> req = new TreeMap<>();
        try {
//            req.put("service", "query_paytobank_order");
            req.put("service", "single_pay_to_bank");
            req.put("version", "1.0");
            req.put("request_time", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            req.put("partner_id", "241028");
            req.put("_input_charset", "UTF-8");


//            req.put("order_no", "DF-" + System.currentTimeMillis());


            req.put("notify_url", "https://kd.alblog.cn/front/callback");
            req.put("out_order_no", "DF-" + System.currentTimeMillis());
            req.put("merchant_no", "241028");
            req.put("payee_name", "周林");
            req.put("payee_mobile", "17607111111");
            req.put("bank_card_no", "6215583202002031321");
            req.put("amount", "2.0");
            req.put("id_no", "370305198804056957");
            req.put("payee_type", "C");
            req.put("summary", "测试");
            req.put("purpose", "测试");
            req.put("pay_pwd", "bf.123456");


            req.put("sign", signUtils.getUrlParamsByMap(req, "3ad0517e465a329cebc3c9a67fd3dbe4"));
            req.put("sign_type", "MD5");
            req.put("summary", URLEncoder.encode("测试", "UTF-8"));
            req.put("purpose", URLEncoder.encode("测试", "UTF-8"));
            req.put("payee_name", URLEncoder.encode("周林", "UTF-8"));
            System.out.println(req);
            String res = HttpClientUtils.send(HttpClientUtils.Method.POST, "http://47.52.56.25:8025/apigw/service.do", req, false, "UTF-8", 300000);
            System.out.println(res);
//            JSONArray resList = JSONObject.fromObject(res).getJSONArray("resList");
//            System.out.println(resList.size());
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
    }

    @Override
    public Map<String, String> remit(Map<String, String> map) {
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});
        String interfaceRequestId = map.get("requestNo");
        String accountNo = map.get("accountNo");
        String accountName = map.get("accountName");
        String amount = String.valueOf(map.get("amount"));
        String cerNo = map.get("cerNo");
        Map<String, String> req = new TreeMap<>();
        Map<String, String> resMap = new HashMap<>();
        try {
            req.put("service", "single_pay_to_bank");
            req.put("version", "1.0");
            req.put("request_time", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            req.put("partner_id", properties.getProperty("customerNo"));
            req.put("_input_charset", "UTF-8");
            req.put("notify_url", properties.getProperty("notifyUrl"));
            req.put("out_order_no", interfaceRequestId);
            req.put("merchant_no", properties.getProperty("customerNo"));
            req.put("payee_name", accountName);
            req.put("payee_mobile", "17607111111");
            req.put("bank_card_no", accountNo);
            req.put("amount", amount);
            req.put("id_no", cerNo);
            req.put("payee_type", "C");
            req.put("summary", "供货商资金结算");
            req.put("purpose", "供货商资金结算");
            req.put("pay_pwd", properties.getProperty("payPwd"));
            req.put("sign", signUtils.getUrlParamsByMap(req, properties.getProperty("key")));
            req.put("sign_type", "MD5");
            req.put("payee_name", URLEncoder.encode(accountName, "UTF-8"));
            req.put("summary", URLEncoder.encode("供货商资金结算", "UTF-8"));
            req.put("purpose", URLEncoder.encode("供货商资金结算", "UTF-8"));
            logger.info("银生宝代付下单报文：{}", req);
            String resPar = HttpClientUtils.send(HttpClientUtils.Method.POST, properties.getProperty("url"), req, false, "UTF-8", 300000);
            logger.info("银生宝代付下单返回报文：{}", resPar);
            resMap.put("tranStat", "UNKNOWN");
            resMap.put("compType", BusinessCompleteType.NORMAL.name());
            resMap.put("amount", amount);
            resMap.put("requestNo", interfaceRequestId);
            resMap.put("resCode", "");
            resMap.put("resMsg", "");
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
        return resMap;
    }

    @Override
    public Map<String, String> query(Map<String, String> map) {
        Map<String, String> req = new TreeMap<>();
        Map<String,String> remitPayResponseBean = new HashMap<>();
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {
        });
        String requestNo = map.get("requestNo");
        try {
            req.put("service", "query_paytobank_order");
            req.put("version", "1.0");
            req.put("request_time", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            req.put("partner_id", properties.getProperty("customerNo"));
            req.put("_input_charset", "UTF-8");
            req.put("order_no", requestNo);
            req.put("sign", signUtils.getUrlParamsByMap(req, properties.getProperty("key")));
            req.put("sign_type", "MD5");
            logger.info("银生宝代付查询报文：{}", req);
            String resPar = HttpClientUtils.send(HttpClientUtils.Method.POST, properties.getProperty("url"), req, false, "UTF-8", 300000);
            logger.info("银生宝代付查询返回报文：{}", resPar);
            JSONArray resList = JSONObject.fromObject(resPar).getJSONArray("resList");
            if (resList.size() > 0) {
                JSONObject res = resList.getJSONObject(0);
                if ("4".equals(res.getString("trans_status"))) {
                    remitPayResponseBean.put("resCode", "0000");
                    remitPayResponseBean.put("tranStat", "SUCCESS");
                    remitPayResponseBean.put("resMsg", "成功");
                    remitPayResponseBean.put("requestNo", requestNo);
                    remitPayResponseBean.put("interfaceOrderID", requestNo);
                    remitPayResponseBean.put("interfaceCode", "YSB_100002_REMIT");
                    remitPayResponseBean.put("amount", res.getString("amount"));
                    remitPayResponseBean.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                    remitPayResponseBean.put("completeTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                    remitPayResponseBean.put("compType", BusinessCompleteType.NORMAL.toString());
                    logger.info("千付宝代付 交易完成 通知接口核心原文 [{}]", remitPayResponseBean);
                }
            }
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
        return remitPayResponseBean;
    }
}