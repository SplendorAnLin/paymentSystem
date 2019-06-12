package com.yl.payinterface.core.handle.impl.b2c.shand100001;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.DateUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.hessian.exception.BusinessException;
import com.yl.payinterface.core.bean.InternetbankSalesResponseBean;
import com.yl.payinterface.core.bean.TradeContext;
import com.yl.payinterface.core.handle.impl.b2c.shand100001.request.GatewayOrderPayRequest;
import com.yl.payinterface.core.handle.impl.b2c.shand100001.response.GatewayOrderPayResponse;
import com.yl.payinterface.core.handle.impl.b2c.shand100001.utils.CertUtil;
import com.yl.payinterface.core.handle.impl.b2c.shand100001.utils.SandpayClient;
import com.yl.payinterface.core.handle.impl.b2c.shand100001.utils.SandpayRequestHead;
import com.yl.payinterface.core.handle.impl.b2c.shand100001.utils.SandpayResponseHead;
import com.yl.payinterface.core.handler.InternetbankHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.utils.CodeBuilder;
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

/**
 * 杉德B2C
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2016年11月16日
 */
@Service("b2cShand100001Handler")
public class Shand100001Handler implements InternetbankHandler {

    private static Logger logger = LoggerFactory.getLogger(Shand100001Handler.class);

    static {
        try {
            CertUtil.init("/home/cer/sand/sand.cer", "/home/cer/sand/sand.pfx", "123456");
        } catch (Exception e) {
            logger.error("衫德扫码支付加载证书异常:{}", e);
        }
    }

    public static void main(String[] args) {
        String url = "https://cashier.sandpay.com.cn/gateway/api/order/pay";
        String mid = "14066132";
        String notifyUrl = "https://kd.alblog.cn/front/callback";
        String frontUrl = "https://kd.alblog.cn/front/callback";
        Properties properties = new Properties();
        properties.setProperty("url", url);
        properties.setProperty("mid", mid);
        properties.setProperty("notifyUrl", notifyUrl);
        properties.setProperty("frontUrl", frontUrl);
        Map<String, String> params = new HashMap<>();
        params.put("transConfig", JsonUtils.toJsonString(properties));
        params.put("interfaceRequestID", CodeBuilder.build("TD", "yyyyMMdd"));
        params.put("amount", "0.01");
        params.put("goodsName", "测试");
        params.put("interfaceInfoCode", "SD_100001-B2C");
        params.put("clientIp", "127.0.0.1");
        params.put("providerCode", "ICBC");
        new Shand100001Handler().pay(params);
    }

    public Map<String, String> pay(Map<String, String> map) {
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("transConfig")), new TypeReference<Properties>() {
        });
        SandpayRequestHead head = new SandpayRequestHead();
        GatewayOrderPayRequest.GatewayOrderPayRequestBody body = new GatewayOrderPayRequest.GatewayOrderPayRequestBody();
        GatewayOrderPayRequest gwOrderPayReq = new GatewayOrderPayRequest();
        gwOrderPayReq.setHead(head);
        gwOrderPayReq.setBody(body);
        head.setVersion("1.0");
        head.setMethod("sandpay.trade.pay");
        head.setProductId("00000007");
        head.setAccessType("1");
        head.setMid(properties.getProperty("mid"));
        head.setSubMid("");
        head.setSubMidAddr("");
        head.setSubMidName("");
        head.setChannelType("07");
        head.setReqTime((new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()));
        body.setOrderCode(map.get("interfaceRequestID"));
        if (map.get("amount").length() != 12) {
            String tmpAmt = String.valueOf((int) AmountUtils.multiply(Double.valueOf(map.get("amount")), 100));
            for (; ; ) {
                tmpAmt = "0" + tmpAmt;
                if (tmpAmt.length() == 12) {
                    body.setTotalAmount(tmpAmt);
                    break;
                }
            }
        } else {
            body.setTotalAmount(map.get("amount"));
        }
        body.setSubject(map.get("goodsName"));
        body.setBody(map.get("goodsName"));
        body.setTxnTimeOut(new SimpleDateFormat("yyyyMMddHHmmss").format(DateUtils.addDays(new Date(), 1)));
        String payExtra = "{\"bankCode\":\"" + convertBankCode(map.get("providerCode")) + "\",\"payType\":\"3\"}";
        body.setPayMode("bank_pc");
        body.setPayExtra(payExtra);
        body.setClientIp(map.get("clientIp"));
        body.setNotifyUrl(properties.getProperty("notifyUrl"));
        body.setFrontUrl(properties.getProperty("frontUrl"));
        body.setStoreId(map.get("storeId"));
        body.setTerminalId(map.get("terminalId"));
        body.setOperatorId(map.get("operatorId"));
        body.setClearCycle("0");
        body.setBizExtendParams(map.get("bizExtendParams"));
        body.setMerchExtendParams(map.get("merchExtendParams"));
        body.setExtend(map.get("extend"));
        logger.info("b2cShand100001 下单请求明文  接口编号:[{}],订单号:[{}],请求报文:[{}]", map.get("interfaceInfoCode"), map.get("interfaceRequestID"), JsonUtils.toJsonString(body));
        JSONObject credential = null;
        try {
            GatewayOrderPayResponse gwPayResponse = SandpayClient.execute(gwOrderPayReq, properties.getProperty("url"));
            logger.info("b2cShand100001 下单响应明文  接口编号:[{}],订单号:[{}],响应报文:[{}]", map.get("interfaceInfoCode"), map.get("interfaceRequestID"), JsonUtils.toJsonString(gwPayResponse));
            SandpayResponseHead respHead = gwPayResponse.getHead();
            if ("000000".equals(respHead.getRespCode())) {
                GatewayOrderPayResponse.GatewayOrderPayResponseBody respBody = gwPayResponse.getBody();
                credential = JSONObject.fromObject(respBody.getCredential());
            } else {
                logger.info("b2cShand100001 下单响应明文  接口编号:[{}],订单号:[{}],下单异常", map.get("interfaceInfoCode"));
                throw new RuntimeException("");
            }
        } catch (Exception e) {
            logger.info("b2cShand100001 下单响应明文  接口编号:[{}],订单号:[{}],下单异常", map.get("interfaceInfoCode"), e);
            throw new RuntimeException(e.getMessage());
        }
        Map<String, String> resParams = JsonUtils.toObject(credential.getString("params"), new TypeReference<Map<String, String>>() {
        });
        resParams.put("gateway", "submit");
        resParams.put("url", credential.getString("submitUrl"));
        resParams.put("interfaceRequestID", map.get("interfaceRequestID"));
        return resParams;
    }

    @Override
    public InterfaceRequest complete(Map<String, Object> map) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public Object[] trade(TradeContext tradeContext) throws BusinessException {
        Map<String, String> map = tradeContext.getRequestParameters();
        map.put("amount", String.valueOf(tradeContext.getInterfaceRequest().getAmount()));
        map.put("interfaceRequestID", tradeContext.getInterfaceRequest().getInterfaceRequestID());
        map = pay(map);
        return new Object[]{"", map};
    }

    @Override
    public Map<String, Object> signatureVerify(
            InternetbankSalesResponseBean internetbankSalesResponseBean,
            Properties tradeConfigs) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object[] query(TradeContext tradeContext) throws BusinessException {
        return null;
    }

    private static Map<String, String> bankCodes = new HashMap<>();

    static {
        bankCodes.put("ICBC", "01020000");
        bankCodes.put("CCB", "01050000");
        bankCodes.put("ABC", "01030000");
        bankCodes.put("CMB", "03080000");
        bankCodes.put("BCM", "03010000");
        bankCodes.put("BOC", "01040000");
        bankCodes.put("CEB", "03030000");
        bankCodes.put("CMBC", "03050000");
        bankCodes.put("CIB", "03090000");
        bankCodes.put("CNCB", "03020000");
        bankCodes.put("CGB", "03060000");
        bankCodes.put("SPDB", "03100000");
        bankCodes.put("PAB", "03070000");
        bankCodes.put("HXB", "03040000");
        bankCodes.put("NBCB", "04083320");
        bankCodes.put("BEA", "03200000");
        bankCodes.put("BOS", "04012900");
        bankCodes.put("PSBC", "01000000");
        bankCodes.put("NJCB", "04243010");
        bankCodes.put("SRCB", "65012900");
        bankCodes.put("CBHB", "03170000");
        bankCodes.put("BOCD", "64296510");
        bankCodes.put("BOB", "04031000");
        bankCodes.put("HSB", "64296511");
        bankCodes.put("BOTJ", "04341101");
    }

    private String convertBankCode(String bankCode) {
        if (bankCodes.get(bankCode) != null) {
            return bankCodes.get(bankCode);
        } else {
            throw new RuntimeException("衫德网银,银行编号匹配失败:{" + bankCode + "}");
        }
    }
}