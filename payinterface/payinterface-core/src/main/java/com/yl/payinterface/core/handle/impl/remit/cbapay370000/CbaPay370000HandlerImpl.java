package com.yl.payinterface.core.handle.impl.remit.cbapay370000;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.handler.ChannelReverseHandler;
import com.yl.payinterface.core.handler.RemitHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import net.sf.json.JSONObject;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 网上有名代付
 *
 * @author 聚合支付有限公司
 * @since 2017年2月23日
 * @version V1.0.0
 */
@Service("cbapayHandler370000")
public class CbaPay370000HandlerImpl implements RemitHandler, ChannelReverseHandler {

    @Resource
    private InterfaceRequestService interfaceRequestService;

    private static Logger logger = LoggerFactory.getLogger(CbaPay370000HandlerImpl.class);
    public static String CHARSET = "UTF-8";

    public static void main(String[] args) {
        Properties prop = new Properties();
        prop.setProperty("notifyUrl", "http://v.hank-pay.com/payinterface-front/cbaPayRemitNotify/completePay");
        prop.setProperty("payUrl", "http://www.cbapay.com/merchant/servlet/HttpPaymentServlet");
        prop.setProperty("queryUrl", "http://www.cbapay.com/merchant/servlet/HttpPaymentQueryServlet");
        prop.setProperty("channelMerchantId", "");
//        prop.setProperty("merchantId", "00000000150589.11");
//        prop.setProperty("merchantPwd", "af3a73149f090e07");
//        prop.setProperty("key", "qX0iYNIRrj76iMtwXxX");
        Map<String, String> params = new HashMap<>();
        params.put("bankName", "工商银行");
        params.put("accountNo", "6212260200041743341");
        params.put("accountName", "张晓杰");
        params.put("amount", "1");
        params.put("requestNo", "TD-1499923210267");
        params.put("accountType", "INDIVIDUAL");
        params.put("tradeConfigs", JsonUtils.toJsonString(prop));
        params.put("interfaceInfoCode", "CBAPAY_370000_REMIT");
        System.out.println(new CbaPay370000HandlerImpl().query(params));
    }

    @Override
    public Map<String, String> reverse(Map<String, String> params) {
        return query(params);
    }

    @Override
    public Map<String, String> remit(Map<String, String> map) {
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, String> respMap = new HashMap<>();
        try {
            paramMap.put("bankName", URLEncoder.encode(checkBank(map.get("bankName")), CHARSET));
            paramMap.put("cardNo", map.get("accountNo"));
            paramMap.put("accName", URLEncoder.encode(map.get("accountName"), CHARSET));
            map.put("amount", String.valueOf((int)AmountUtils.multiply(Double.valueOf(map.get("amount")), 100)));
            paramMap.put("amount", map.get("amount"));
            paramMap.put("merchantId", properties.getProperty("merchantId"));
            paramMap.put("merchantPwd", properties.getProperty("merchantPwd"));
            paramMap.put("lowerPayOrderNo", map.get("requestNo"));
            paramMap.put("accTel", "");
            if(StringUtils.isBlank(map.get("remark"))){
                paramMap.put("payDesc", URLEncoder.encode("代付", CHARSET));
            }else{
                paramMap.put("payDesc", URLEncoder.encode(map.get("remark"), CHARSET));
            }
            if(map.get("accountType").equals("INDIVIDUAL")){
                paramMap.put("isB2b", "");
            }else{
                paramMap.put("isB2b", "1");
            }
            paramMap.put("tranCode", "rtSinglePay");
            paramMap.put("noticeUrl", properties.getProperty("notifyUrl"));
            paramMap.put("channelMerchantId", properties.getProperty("channelMerchantId"));

            String sourceData = "accName=" + map.get("accountName") + "&accTel=" + paramMap.get("accTel") + "&amount=" + map.get("amount") +
                    "&bankName=" + checkBank(map.get("bankName")) + "&cardNo=" + map.get("accountNo") + "&isB2b=" + paramMap.get("accTel")
                    + "&lowerPayOrderNo=" + map.get("requestNo") + "&merchantId=" + properties.getProperty("merchantId") + "&merchantPwd=" +
                    properties.getProperty("merchantPwd") + "&noticeUrl=" + paramMap.get("noticeUrl") + "&payDesc=" + URLDecoder.decode(paramMap.get("payDesc"), CHARSET);
            System.out.println(sourceData);
            String signature = sign(sourceData, properties.getProperty("key"));
            paramMap.put("signature", URLEncoder.encode(signature, CHARSET));

            logger.info("网上有名代付下单 接口编号:[{}],订单号:[{}],请求报文:[{}]", map.get("interfaceInfoCode"),map.get("requestNo"),paramMap);
            String result = HttpClientUtils.send(HttpClientUtils.Method.POST, properties.getProperty("payUrl"),paramMap);
            logger.info("网上有名代付下单 接口编号:[{}],订单号:[{}],响应报文:[{}]",  map.get("interfaceInfoCode"),map.get("requestNo"), result);
            if(StringUtils.isBlank(result)){
                respMap.put("resCode", "9999");
                respMap.put("resMsg","请求通道异常");
                respMap.put("tranStat", "FAILED");
                respMap.put("requestNo", map.get("requestNo"));
                respMap.put("amount", map.get("amount"));
                return respMap;
            }
            JSONObject jsonObject = JSONObject.fromObject(result);
            String rspMsg = jsonObject.getString("rspMsg");
            String rspType = jsonObject.getString("rspType");
            String rspLowerPayOrderNo = jsonObject.getString("lowerPayOrderNo");
            String payTranId = jsonObject.getString("payTranId");
            String singleFee = jsonObject.getString("singleFee");
            String rspAmount = jsonObject.getString("amount");
            String rspSignature = jsonObject.getString("signature");
            String payDesc = jsonObject.getString("payDesc");
            sourceData = "amount=" + rspAmount + "&lowerPayOrderNo=" + rspLowerPayOrderNo + "&payDesc=" + payDesc + "&payTranId=" + payTranId + "&rspMsg=" + rspMsg + "&rspType=" + rspType
                    + "&singleFee=" + singleFee;
            if(verSign(sourceData, rspSignature, properties.getProperty("key"))){
                if("S".equals(rspType)){
                    respMap.put("requestNo", map.get("requestNo"));
                    respMap.put("amount", map.get("amount"));
                    respMap.put("tranStat", "UNKNOWN");
                    respMap.put("resCode",  rspType);
                    respMap.put("resMsg", rspMsg);
                }else{
                    respMap.put("tranStat", "FAILED");
                    respMap.put("requestNo", map.get("requestNo"));
                    respMap.put("amount", map.get("amount"));
                    respMap.put("resCode",  rspType);
                    respMap.put("resMsg", rspMsg);
                }
            }
        }catch (Exception e){
            logger.error("网上有名代付下单异常 接口编号:[{}],订单号:[{}],异常信息：[{}]", map.get("interfaceInfoCode"),map.get("requestNo"), e);
        }
        return respMap;
    }

    @Override
    public Map<String, String> query(Map<String, String> map) {
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});
        Map<String, String> respMap = new HashMap<>();
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("merchantId", properties.getProperty("merchantId"));
        paramMap.put("payTranId", "");//
        paramMap.put("lowerPayOrderNo", map.get("requestNo"));
        paramMap.put("tranCode", "qySinglePay");
        paramMap.put("channelMerchantId", properties.getProperty("channelMerchantId"));

        String sourceData = "lowerPayOrderNo=" + map.get("requestNo") + "&merchantId=" + properties.getProperty("merchantId") + "&payTranId=" + paramMap.get("payTranId");
        String signature = sign(sourceData, properties.getProperty("key"));
        try {
            paramMap.put("signature", URLEncoder.encode(signature, CHARSET));
            logger.info("网上有名代付查询  接口编号:[{}],订单号:[{}], 请求报文:[{}]", map.get("interfaceInfoCode"),map.get("requestNo"), paramMap);
            String result = HttpClientUtils.send(HttpClientUtils.Method.POST, properties.getProperty("queryUrl"),paramMap);
            logger.info("网上有名代付查询  接口编号:[{}],订单号:[{}],响应报文:[{}]",  map.get("interfaceInfoCode"),map.get("requestNo"), result);

            JSONObject jsonObject = JSONObject.fromObject(result);
            String rspType = jsonObject.getString("rspType");
            String rspLowerPayOrderNo = jsonObject.getString("lowerPayOrderNo");
            String payTranId = jsonObject.getString("payTranId");
            String singleFee = jsonObject.getString("singleFee");
            String rspAmount = jsonObject.getString("amount");
            String payResult = jsonObject.getString("payResult");
            String rspSignature = jsonObject.getString("signature");
            sourceData = "amount=" + rspAmount + "&lowerPayOrderNo=" + rspLowerPayOrderNo + "&payResult=" + payResult + "&payTranId=" + payTranId + "&rspType=" + rspType
                    + "&singleFee=" + singleFee;
            if(verSign(sourceData, rspSignature, properties.getProperty("key"))){
                if("S".equals(rspType)){
                    if("S".equals(payResult)){
                        respMap.put("requestNo", map.get("requestNo"));
                        respMap.put("compType", BusinessCompleteType.AUTO_REPAIR.name());
                        respMap.put("amount", new BigDecimal(rspAmount).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP).toString());
                        respMap.put("interfaceOrderID", payTranId);
                        respMap.put("resCode", payResult);
                        respMap.put("resMsg", "付款成功");
                        respMap.put("tranStat", "SUCCESS");
                    }else if("E".equals(payResult)){
                        respMap.put("requestNo", map.get("requestNo"));
                        respMap.put("compType", BusinessCompleteType.AUTO_REPAIR.name());
                        respMap.put("amount", new BigDecimal(rspAmount).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP).toString());
                        respMap.put("interfaceOrderID", payTranId);
                        respMap.put("resCode", payResult);
                        respMap.put("resMsg", "付款失败");
                        respMap.put("tranStat", "FAILED");
                    }else{
                        respMap.put("tranStat", "UNKNOWN");
                    }
                }else if("E".equals(rspType)){
                    InterfaceRequest interfaceRequest =  interfaceRequestService.queryByInterfaceRequestID(map.get("requestNo"));
                    respMap.put("requestNo", map.get("requestNo"));
                    respMap.put("compType", BusinessCompleteType.AUTO_REPAIR.name());
                    respMap.put("amount", String.valueOf(interfaceRequest.getAmount()));
                    respMap.put("interfaceOrderID", payTranId);
                    respMap.put("resCode", rspType);
                    respMap.put("resMsg", "付款失败");
                    respMap.put("tranStat", "FAILED");
                }else{
                    respMap.put("requestNo", map.get("requestNo"));
                    respMap.put("tranStat", "UNKNOWN");
                }
            }
        }catch (Exception e){
            logger.error("网上有名代付查询异常 接口编号:[{}],订单号:[{}],异常信息：[{}]", map.get("interfaceInfoCode"),map.get("requestNo"),e);
            throw new RuntimeException(e.getMessage());
        }
        return respMap;
    }

    /**
     * 加签
     *
     * @param sourceData 待加签串
     * @return
     */
    private static String sign(String sourceData, String key) {
        return MD5Util.getMD5(sourceData + key);
    }

    /**
     * 验签
     *
     * @param sourceData   待验签串
     * @param rspSignature 返回的加签串
     * @return
     */
    private static boolean verSign(String sourceData, String rspSignature, String key) {
        return rspSignature.equals(MD5Util.getMD5(sourceData + key));
    }

    private static String checkBank(String bankName){
        String bank = null;
        if(bankName.indexOf("算行")>-1){
            bank = bankName.substring(0, bankName.indexOf("算行"))+"算行";
        }
        if(bankName.indexOf("合社")>-1){
            bank = bankName.substring(0, bankName.indexOf("合社"))+"合社";
        }
        if(bankName.indexOf("联社")>-1){
            bank = bankName.substring(0, bankName.indexOf("联社"))+"联社";
        }
        if(bankName.indexOf("用社")>-1){
            bank = bankName.substring(0, bankName.indexOf("用社"))+"用社";
        }
        if(bankName.indexOf("中心")>-1){
            bank = bankName.substring(0, bankName.indexOf("中心"))+"中心";
        }
        if(bankName.indexOf("作室")>-1){
            bank = bankName.substring(0, bankName.indexOf("作室"))+"作室";
        }
        if(bankName.indexOf("金融")>-1){
            bank = bankName.substring(0, bankName.indexOf("金融"))+"金融";
        }
        if(bankName.indexOf("算所")>-1){
            bank = bankName.substring(0, bankName.indexOf("算所"))+"算所";
        }
        if(bankName.indexOf("银行")>-1){
            bank = bankName.substring(0, bankName.indexOf("银行"))+"银行";
        }
        return bank==null?bankName:bank;
    }
}
