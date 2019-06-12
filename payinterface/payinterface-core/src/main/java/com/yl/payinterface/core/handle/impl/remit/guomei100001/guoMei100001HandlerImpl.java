package com.yl.payinterface.core.handle.impl.remit.guomei100001;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.handle.impl.remit.guomei100001.utils.GuoMeiSignUtils;
import com.yl.payinterface.core.handler.RemitHandler;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 国美代付
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/6/14
 */
@Service("guoMei100001Handler")
public class guoMei100001HandlerImpl implements RemitHandler {

    private static final Logger logger = LoggerFactory.getLogger(guoMei100001HandlerImpl.class);

    private static final String tokenUrl = "https://api.gomepay.com/access_token?aid=AID&signature=SIGNATURE&timestamp=TIMESTAMP&nonce=NONCE";

    private static final String url = "https://api.gomepay.com/CoreServlet?aid=AID&api_id=API_ID&access_token=ACCESS_TOKEN";

    public static void main(String[] args) {
        String cfg = "{\"service_code\":\"sne_00000000002\",\"merchant_number\":\"SHID20180613029\",\"aid\":\"8a179b8c63d8c63b0163fc2e11b7031c\",\"notifyUrl\":\"http://pay.feiyijj.com/payinterface-front/gm100001Notify/completeRemit\",\"wallet_id\":\"0100851953871183\",\"app_code\":\"apc_02000001524\",\"pwd\":\"4607e782c4d86fd5364d7e4508bb10d9\",\"appid\":\"epay_api_deal@agent_for_paying\",\"asset_id\":\"251865a0c51a4cce89d0affb079542ee\",\"keys\":\"rongmei|m2|20180614\"}";
        Map<String, String> params = new HashMap<>();
        params.put("tradeConfigs", cfg);
        params.put("accountNo", "6215583202002031321");
        params.put("accountName", "周林");
        params.put("requestNo", "DF-" + System.currentTimeMillis());
        params.put("amount", "10");
        System.out.println(new guoMei100001HandlerImpl().remit(params));
    }

    @Override
    public Map<String, String> remit(Map<String, String> map) {
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {
        });
        Map<String, String> retMap = new HashMap<>();
        String accountNo = map.get("accountNo");
        String accountName = map.get("accountName");
        String interfaceRequestId = map.get("requestNo");
        String amount = map.get("amount");
        try {
            Map<String, String> desa = new HashMap<>();
            desa.put("login_token", "");
            desa.put("req_no", "RQ-" + System.currentTimeMillis());
            desa.put("app_code", properties.getProperty("app_code"));
            desa.put("app_version", "1.0.0");
            desa.put("service_code", properties.getProperty("service_code"));
            desa.put("plat_form", "03");
            desa.put("merchant_number", properties.getProperty("merchant_number"));
            desa.put("order_number", interfaceRequestId);
            desa.put("wallet_id", properties.getProperty("wallet_id"));
            desa.put("asset_id", properties.getProperty("asset_id"));
            desa.put("password_type", "02");
            desa.put("encrypt_type", "02");
            desa.put("pay_password", properties.getProperty("pwd"));
            desa.put("customer_type", "01");
            desa.put("customer_name", accountName);
            desa.put("currency", "CNY");
            desa.put("amount", amount);
            desa.put("async_notification_addr", properties.getProperty("notifyUrl"));
            desa.put("asset_type_code", "000002");
            desa.put("account_type_code", "01");
            desa.put("account_number", accountNo);
            logger.info("国美代付下单报文:{}", desa);
            String access_token = GuoMeiSignUtils.getToken(tokenUrl, properties.getProperty("aid"), properties.getProperty("keys"));
            String res = GuoMeiSignUtils.sendPost(url, properties.getProperty("aid"), properties.getProperty("appid"), access_token, JsonUtils.toJsonString(desa));
            logger.info("国美代付下单返回报文：{}", res);
            Map<String, Object> resMap = JsonUtils.toObject(res, new TypeReference<Map<String, Object>>() {
            });
            if (resMap.get("err_code") != null && StringUtils.notBlank(resMap.get("err_code").toString())) {
                retMap.put("tranStat", "FAILED");
                retMap.put("resMsg", resMap.get("err_msg").toString());
            } else {
                if ("000".equals(resMap.get("op_ret_code"))) {
                    retMap.put("tranStat", "UNKNOWN");
                } else {
                    retMap.put("tranStat", "FAILED");
                    retMap.put("resMsg", resMap.get("op_err_msg").toString());
                }
            }
            retMap.put("compType", BusinessCompleteType.NORMAL.name());
            retMap.put("amount", amount);
            retMap.put("requestNo", interfaceRequestId);
            retMap.put("resCode", resMap.get("op_ret_code").toString());
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
        return retMap;
    }

    @Override
    public Map<String, String> query(Map<String, String> map) {
        return null;
    }
}