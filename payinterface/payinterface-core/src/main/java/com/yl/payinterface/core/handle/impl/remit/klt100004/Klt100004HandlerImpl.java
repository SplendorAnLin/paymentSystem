package com.yl.payinterface.core.handle.impl.remit.klt100004;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.yl.payinterface.core.bean.Cnaps;
import com.yl.payinterface.core.bean.Recognition;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.handle.impl.remit.klt100004.common.dto.impl.Head;
import com.yl.payinterface.core.handle.impl.remit.klt100004.common.dto.impl.Sign;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.SingleAgentpayService;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.impl.request.Request;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.impl.request.RequestEnvelope;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.impl.request.agentPay.SingleAgentpayRequestBody;
import com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.impl.response.Response;
import com.yl.payinterface.core.handle.impl.remit.klt100004.util.getEvn;
import com.yl.payinterface.core.handler.RemitHandler;
import com.yl.payinterface.core.utils.Base64Utils;
import com.yl.payinterface.core.utils.CommonUtils;
import com.yl.payinterface.core.utils.HttpUtil;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 开联通 实时代付
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/3
 */
@Service("remitKlt100004Handler")
public class Klt100004HandlerImpl implements RemitHandler {

    private static final Logger logger = LoggerFactory.getLogger(Klt100004HandlerImpl.class);

    public static void main(String[] args) {
//        Klt100004HandlerImpl klt100004Handler = new Klt100004HandlerImpl();
//        Map<String, String> map = new HashMap<>();
//        Properties properties = new Properties();
//        properties.put("merchantId", "105840180510003");
//        properties.put("notifyUrl", "https://kd.alblog.cn/front/callback");
//        properties.put("url", "https://pg.openepay.com/gateway/singleagentpay");
//        properties.put("cer", "F:\\pay\\多游\\ops-prod.cer");
//        properties.put("pfx", "F:\\pay\\多游\\test-rsa.pfx");
//        properties.put("passWord", "123456");
//        map.put("tradeConfigs", JsonUtils.toJsonString(properties));
//        map.put("requestNo", "DF-20180601" + System.currentTimeMillis());
//        map.put("bankName", "中国工商银行武汉市东西湖开发区支行");
//        map.put("accountNo", "6215583202002031321");
//        map.put("accountName", "周林");
//        map.put("amount", "2");
//        klt100004Handler.remit(map);
//        XMLSerializer xmlSerializer = new XMLSerializer();
//        String xml = "<response><envelope><head><version>v1.0.7.6</version><charset>UTF-8</charset></head><body><mchtId>105840180510003</mchtId><mchtOrderNo>DF-201806011527833715746</mchtOrderNo><responseCode>E0000</responseCode><responseMsg>正常</responseMsg><status>TX_BEGIN</status></body></envelope><sign><signType>1</signType><certificate></certificate><signContent>gUMug6CX1dSF3W7VJk+NhVo1yg1YQ3n7Ez3ighz8DBJWUPdaF4C4mHV3C4mWK4muInewYPMhwxj42deAPy1oVe9WG6y6Jgc6IPFBgX8JAQ4yoSBRLa8mh8uCo4PAJuHdyFlvQn2fQjg2g0+USABWoebsxWISi/2lOdNFUqbzMGE48r5cFD3LVzsZiUsPUUXpUTBFaTZPkccIHaCWSOh7Zdi+m9C389lMax2FxeOZfQ7/VeHDpnJeoGry2eSQ1jTYVDM+TMpeU6a9/6LZ2MOdZ8YatG8kBI5x/399vbbqJ58YbA1C8ymPrtddQrzgdmB3Mw+oClSKK+glpbE/THisxw==</signContent></sign></response>";
//        String result = xmlSerializer.read(xml).toString();
//        System.out.println(result);
//        JSONObject res = JSONObject.fromObject(result);
//        if ("E0000".equals(res.getJSONObject("envelope").getJSONObject("body").getString("responseCode"))) {
//            System.out.println(res.getJSONObject("envelope").getJSONObject("body").getString("responseMsg"));
//        }
//        String res = new String(Base64Utils.decode("PHJlc3BvbnNlPjxlbnZlbG9wZT48aGVhZD48dmVyc2lvbj52MS4wLjcuNjwvdmVyc2lvbj48Y2hhcnNldD5VVEYtODwvY2hhcnNldD48L2hlYWQ+PGJvZHk+PHJlc3BvbnNlQ29kZT5FMDAwMDwvcmVzcG9uc2VDb2RlPjxvcmdPcmRlck5vPjIwMTgwNjAxMTE1ODM4NzUwMDwvb3JnT3JkZXJObz48b3JkZXJObz5ERi0yMDE4MDYwMTE1Mjc4MjU1NDc2MjI8L29yZGVyTm8+PHN0YXR1cz5UWF9TVUNDRVNTPC9zdGF0dXM+PG9yZGVyQW1vdW50PjUwMDwvb3JkZXJBbW91bnQ+PGFjY291bnRUeXBlPlBFUlNPTkFMPC9hY2NvdW50VHlwZT48bWVyY2hhbnRJZD4xMDU4NDAxODA1MTAwMDM8L21lcmNoYW50SWQ+PG9yZGVyRGF0ZXRpbWU+MjAxODA2MDExMTU4Mzg8L29yZGVyRGF0ZXRpbWU+PHN0YXR1c0Rlc2M+5Lqk5piT5oiQ5YqfPC9zdGF0dXNEZXNjPjwvYm9keT48L2VudmVsb3BlPjxzaWduPjxzaWduVHlwZT4xPC9zaWduVHlwZT48Y2VydGlmaWNhdGU+PC9jZXJ0aWZpY2F0ZT48c2lnbkNvbnRlbnQ+ck9mZE9zU1o0aGxOMGUySHp5d09kMGhaTXZEdDF6OWJIcDVDTVlZamtMV2JlblVFQlo3WU1CTHBoc3pYL3BTMjN2UXZFRVA1K09VbUUzN2Y3RFNLQUNxbFo4WVlEcWx2cHQzcHR1SDZoOXZHdEdmUDBUa1l5WGFTUTlLM3BqcnBzQUhEc2NyODlXSGJPcmlQbXZFaFgzTm5UYnNidURSaHRPWTErK2ZrU1RGQXVCbERHV2J5T1hjM3ZlTnJSSWtaLytkd0Z4VFF6U0c0OGxyRGREOTVWd0VpRjJLQVpOb3llcytHclhSVnJpTWhRQ3VwY3J6K2prMWE2c1FRMnlGRFpYVWZxaFc0bmNRTHdNc3ZyVUpoZVRNVUNEYW1aaUJqWUdTazFPVGk2UHZhSTMvVWJPcjJnazRTcWJjTm5RMjJYK3hBT1RvTWJZeTJjNk5wak03UFlBPT08L3NpZ25Db250ZW50Pjwvc2lnbj48L3Jlc3BvbnNlPg=="));
//        XMLSerializer xmlSerializer = new XMLSerializer();
//        String result = xmlSerializer.read(res).toString();
//        System.out.println(result);
        try {
            String res = Base64Utils.encode("<response><envelope><head><version>v1.0.7.6</version><charset>UTF-8</charset></head><body><responseCode>E0000</responseCode><orgOrderNo>201806011540415315</orgOrderNo><orderNo>TD20180601100689706169</orderNo><status>TX_SUCCESS</status><orderAmount>125000</orderAmount><accountType>PERSONAL</accountType><merchantId>105840180510003</merchantId><orderDatetime>20180601154041</orderDatetime><statusDesc>交易成功</statusDesc></body></envelope><sign><signType>1</signType><certificate></certificate><signContent>gAnIRy3EXK+Kq2mjjskvaTwWX/QayBS2Jb+53Xiy5ggqYHhdP95ZT1thqdBnpEtU1w1bAVEo8HFZ/0iU1qd1cNwT4rhri8LWEpEHNRvU5qGCyWL8elMa/ALTxx2F4Foq43AcQ1SVGfEfebmnX6sLtARzFjU4ONcFdr9H4yT4qaauJ3ZuZmfuNpRnWw4AIQYuqUjQ0+62voPUqiVywsqgJ4WjBmCUSTlAQIQqeCB71T4v06SU6KdQrpBj7F/dFBrURoUwMa0qCwDAmk5J0bTLHWVW18OVQQD5AEIn2dPoX/h7SW0DDei2p3eSLxMz9qPap1j9ZEKtU/Fl8FIpIEAPgA==</signContent></sign></response>".getBytes("UTF-8"));
//            HttpClientUtils.send(HttpClientUtils.Method.GET, "http://pay.feiyijj.com/payinterface-front/klt100004Notify/completeRemit?respMsg=", res, true, "UTF-8");

            SingleAgentpayService.send("http://pay.feiyijj.com/payinterface-front/klt100004Notify/completeRemit", res, "UTF-8");
//            HttpUtil.sendReq("http://pay.feiyijj.com/payinterface-front/klt100004Notify/completeRemit?respMsg=" + res, "POST");
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
    }

    @Override
    public Map<String, String> remit(Map<String, String> map) {
        Map<String, String> resMap = new HashMap<>();
        Request agentpayRequest = new Request();
        RequestEnvelope envelope = new RequestEnvelope();
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});
        String interfaceRequestId = map.get("requestNo");
        String bankName = map.get("bankName");
        String accountNo = map.get("accountNo");
        String accountName = map.get("accountName");
        String amount = map.get("amount");
        String cnapsCode = "";
        Recognition recognition = CommonUtils.recognition(accountNo);
        List<Cnaps> cnaps = CommonUtils.cnapsCode(recognition.getProviderCode());
        if (cnaps != null && cnaps.size() > 0) {
            cnapsCode = cnaps.get(0).getClearingBankCode();
        }
        if (StringUtils.isBlank(cnapsCode)) {
            resMap.put("resCode", "9999");
            resMap.put("resMsg", "该银行卡不支持");
            resMap.put("tranStat", "FAILED");
            resMap.put("requestNo", interfaceRequestId);
            resMap.put("amount", amount);
            resMap.put("compType", BusinessCompleteType.NORMAL.name());
        }
        try {
            Head head = new Head();
            head.setCharset("UTF-8");
            head.setVersion("v1.0.7.6");
            envelope.setHead(head);
            SingleAgentpayRequestBody body = new SingleAgentpayRequestBody();
            String dateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            body.setMchtId(properties.getProperty("merchantId"));
            body.setMchtOrderNo(interfaceRequestId);
            body.setOrderDateTime(dateTime);
            body.setAccountNo(accountNo);
            body.setAccountName(accountName);
            body.setAccountType("PERSONAL");
            body.setBankNo(cnapsCode);
            body.setBankName(bankName);
            body.setAmt(String.valueOf((int) AmountUtils.multiply(Double.valueOf(amount), 100d)));
            body.setPurpose("提现");
            body.setNotifyUrl(properties.getProperty("notifyUrl"));
            envelope.setBody(body);
            agentpayRequest.setEnvelope(envelope);
            Sign sign = new Sign();
            sign.setSignType("1");
            agentpayRequest.setSign(sign);
            Response payResponse = (Response) SingleAgentpayService.request(agentpayRequest, getEvn.getEnv(properties.getProperty("url"), properties.getProperty("cer"), properties.getProperty("pfx"), properties.getProperty("passWord")));
            String xml = payResponse.toXML();
            logger.info("代付返回报文：{}", xml);
            XMLSerializer xmlSerializer = new XMLSerializer();
            JSONObject result = JSONObject.fromObject(xmlSerializer.read(xml).toString());
            if ("E0000".equals(result.getJSONObject("envelope").getJSONObject("body").getString("responseCode"))) {
                resMap.put("tranStat", "UNKNOWN");
            } else {
                resMap.put("tranStat", "FAILED");
            }
            resMap.put("compType", BusinessCompleteType.NORMAL.name());
            resMap.put("amount", amount);
            resMap.put("requestNo", interfaceRequestId);
            resMap.put("resCode", result.getJSONObject("envelope").getJSONObject("body").getString("responseCode"));
            resMap.put("resMsg", result.getJSONObject("envelope").getJSONObject("body").getString("responseMsg"));
        } catch (Exception e) {
            logger.error("异常信息:{}", e);
        }
        return resMap;
    }

    @Override
    public Map<String, String> query(Map<String, String> map) {
        return null;
    }
}