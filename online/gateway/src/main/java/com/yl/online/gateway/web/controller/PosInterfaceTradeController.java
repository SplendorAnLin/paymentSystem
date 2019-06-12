package com.yl.online.gateway.web.controller;

import com.lefu.commons.cache.util.CacheUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.security.DigestUtils;
import com.yl.boss.api.bean.CustomerKey;
import com.yl.boss.api.bean.Pos;
import com.yl.boss.api.enums.KeyType;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.boss.api.interfaces.PosInterface;
import com.yl.online.gateway.exception.BusinessException;
import com.yl.online.gateway.utils.CodeBuilder;
import com.yl.online.gateway.utils.CommonUtils;
import com.yl.online.gateway.utils.HttpUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 交易控制器
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017年9月10日
 */
@Controller
@RequestMapping("/pos")
public class PosInterfaceTradeController {
    private static final Logger logger = LoggerFactory.getLogger(PosInterfaceTradeController.class);
    @Resource
    private CustomerInterface customerInterface;
    @Resource
    private PosInterface posInterface;
    private static Properties prop = new Properties();
    private static Map<String, String> respMap = new HashMap<>();

    static {
        respMap.put("01", "设备不存在");
        respMap.put("02", "商户未开通此业务");
        respMap.put("03", "订单类型错误");
        respMap.put("04", "下单失败");
        respMap.put("05", "验签失败");
        respMap.put("06", "金额错误");
        respMap.put("07", "订单时间错误");
        respMap.put("08", "授权码错误");
    }

    private static Properties interfaceCodesProp = new Properties();

    static {
        try {
            interfaceCodesProp.load(GatewayTradeController.class.getClassLoader().getResourceAsStream("interfaceConf.properties"));
        } catch (IOException e) {
            logger.error("初始化 加载POS秘钥 失败", e);
        }
    }

    static {
        try {
            prop.load(PosInterfaceTradeController.class.getClassLoader().getResourceAsStream("serverHost.properties"));
        } catch (IOException e) {
            logger.error("初始化 交易地址 配置失败:", e);
        }
    }

    /**
     * Pos接口支付
     *
     * @param request  请求信息
     * @param response 响应信息
     * @return
     */
    @RequestMapping("/wallet/pay")
    public void pay(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> resParams;
        Map<String, String> resData = new HashMap<>();
        String deviceId = request.getParameter("deviceId");
        String amount = request.getParameter("amount");
        String applyTime = request.getParameter("applyTime");
        String type = request.getParameter("type");
        String authCode = request.getParameter("authCode");
        String sign = request.getParameter("sign");

        StringBuffer sb = new StringBuffer();
        sb.append("deviceId=>").append(deviceId).append("&amount=>").append(amount).append("&applyTime=>").append(applyTime)
                .append("&type=>").append(type).append("&key=>").append(interfaceCodesProp.getProperty("pos.trans.key"));
        logger.info("pos 扫码支付 下单请求报文 : {}" , sb.toString());

        String outOrderId = CodeBuilder.build("POL");
        try {
            CommonUtils.amountCheck(amount);
            if (!sign.equalsIgnoreCase(DigestUtils.md5DigestAsHex(sb.toString().getBytes("UTF-8")))) {
                throw new BusinessException("05");
            }
            resParams = payHandle(deviceId, amount, type, outOrderId, authCode);
            resData.put("code", "00");
            resData.put("msg", "成功");
            resData.put("wxPayUrl", resParams.get("qrCodeUrl"));
            resData.put("orderId", outOrderId);
            resData.put("applyTime", applyTime);
            sb = new StringBuffer();
            sb.append("wxPayUrl=>").append(resData.get("wxPayUrl") == null ? "" : (String)resData.get("wxPayUrl")).append("&orderId=>").append((String)resData.get("orderId")).append("&applyTime=>").append(applyTime).append("&key=>").append(interfaceCodesProp.getProperty("pos.trans.key"));
            resData.put("sign", DigestUtils.md5DigestAsHex(sb.toString().getBytes("UTF-8")));
            CacheUtils.setEx("POS_ORDER_QUERY:" + outOrderId, (String)resParams.get("partner") + "_" + applyTime, 7200);
        } catch (Exception e) {
            logger.error("pos 扫码支付 下单失败", e);
            if (e instanceof BusinessException) {
                resData.put("code", e.getMessage());
                resData.put("msg", getRespMsg(e.getMessage()));
            } else {
                resData.put("code", "04");
                resData.put("msg", "下单失败");
            }
            if ("0005".equals(e.getMessage())) {
                resData.put("code", "06");
                resData.put("msg", getRespMsg(e.getMessage()));
            }
        }
        try {
            logger.info("pos 扫码支付 下单响应报文 : {}" , JsonUtils.toJsonString(resData));
            response.getWriter().write(JsonUtils.toJsonString(resData));
        } catch (Exception e) {
            logger.error("pos 二维码交易 下单响应失败 : ", e);
        }
    }

    /**
     * Pos接口查询
     *
     * @param request  请求信息
     * @param response 响应信息
     * @return
     */
    @RequestMapping("/wallet/query")
    public void query(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> resData = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        String partnerNo;
        String applyTime = "";
        String deviceId = "";
        String orderId = "";
        try {
            deviceId = request.getParameter("deviceId");
            orderId = request.getParameter("orderId");
            String sign = request.getParameter("sign");

            StringBuffer sb = new StringBuffer();
            sb.append("deviceId=>").append(deviceId).append("&orderId=>").append(orderId).append("&key=>").append(interfaceCodesProp.getProperty("pos.trans.key"));
            logger.info("pos 扫码支付 查询请求报文 : {}" , sb.toString());
            if (!sign.equalsIgnoreCase(DigestUtils.md5DigestAsHex(sb.toString().getBytes("UTF-8")))) {
                throw new BusinessException("05");
            }

            String queryParams = CacheUtils.get("POS_ORDER_QUERY:" + orderId, String.class);
            partnerNo = queryParams.split("_")[0];
            applyTime = queryParams.split("_")[1];
            params.put("queryCode", "YL-QUERY");
            params.put("inputCharset", "UTF-8");
            params.put("partner", partnerNo);
            params.put("outOrderId", orderId);
            params.put("signType", "MD5");
            Pos pos = posInterface.findPosByPosSn(deviceId);
            if (pos == null) {
                throw new BusinessException("01");
            }
            CustomerKey customerKey = customerInterface.getCustomerKey(partnerNo, KeyType.MD5);
            params = request(params, prop.getProperty("tradeQueryUrl"), customerKey.getKey());
            params.put("amount", new DecimalFormat("#0.00").format(Double.valueOf(params.get("amount"))));
            params.put("partner", partnerNo);
            if (params == null || params.size() == 0) {
                throw new BusinessException("04");
            }
        } catch (Exception e) {
            logger.error("pos二维码查询订单失败", e);
            if (e instanceof BusinessException) {
                resData.put("code", e.getMessage());
                resData.put("msg", getRespMsg(e.getMessage()));
            } else {
                resData.put("code", "04");
                resData.put("msg", "查询失败");
            }
        }
        try {
            if (!"-1".equals(resData.get("status"))) {
                if ("SUCCESS".equals(params.get("partnerOrderStatus"))) {
                    resData.put("status", "2");
                } else {
                    resData.put("status", "0");
                }
                resData.put("code", "00");
                resData.put("msg", "请求成功");
            }
            resData.put("deviceId", deviceId);
            resData.put("orderId", orderId);
            resData.put("applyTime", StringUtils.isBlank(applyTime) ? "" : applyTime);
            resData.put("amount", StringUtils.isBlank(params.get("amount")) ? "" : params.get("amount"));

            StringBuffer sb = new StringBuffer();
            sb.append("status=>").append(resData.get("status")).append("&deviceId=>").append(deviceId).append("&orderId=>").append(orderId).
                    append("&applyTime=>").append(applyTime).append("&amount=>").append(resData.get("amount")).append("&key=>").append(interfaceCodesProp.getProperty("pos.trans.key"));

            resData.put("sign", DigestUtils.md5DigestAsHex(sb.toString().getBytes("UTF-8")));
            logger.info("pos 扫码支付 查询响应报文 : {}" , JsonUtils.toJsonString(resData));
            response.getWriter().write(JsonUtils.toJsonString(resData));
        } catch (Exception e) {
            logger.error("pos 二维码交易 查询响应失败 : ", e);
        }

    }

    private Map<String, String> payHandle(String deviceId, String amount, String type, String outOrderId, String authCode) throws Exception {
        Pos pos = posInterface.findPosByPosSn(deviceId);
        if (pos == null) {
            throw new BusinessException("01");
        }

        CustomerKey customerKey = customerInterface.getCustomerKey(pos.getCustomerNo(), KeyType.MD5);
        if (customerKey == null) {
            throw new BusinessException("02");
        }

        Map<String, String> params = new HashMap<>();
        params.put("apiCode", "YL-PAY");
        params.put("inputCharset", "UTF-8");
        params.put("partner", pos.getCustomerNo());
        params.put("outOrderId", outOrderId);
        params.put("amount", amount);
        if ("0".equals(type)) {
            params.put("payType", "WXNATIVE");
        } else if ("1".equals(type)) {
            params.put("payType", "ALIPAY");
        }else if ("2".equals(type)) {
            params.put("payType", "WXMICROPAY");
            if(StringUtils.isBlank(authCode)){
                throw new BusinessException("08");
            }
            params.put("authCode", authCode);
        } else if ("3".equals(type)) {
            if(StringUtils.isBlank(authCode)){
                throw new BusinessException("08");
            }
            params.put("payType", "ALIPAYMICROPAY");
            params.put("authCode", authCode);
        } else {
            throw new BusinessException("03");
        }
        params.put("notifyUrl", "http://www.baidu.com");
        Map<String, String> oemParams = new HashMap<>();
        oemParams.put("OEM_CUST_CATI", pos.getPosCati());
        params.put("extParam", JsonUtils.toJsonString(oemParams));
        params.put("signType", "MD5");
        params = request(params, prop.getProperty("posPayUrl"), customerKey.getKey());
        params.put("partner", pos.getCustomerNo());
        if (params == null || params.size() == 0) {
            throw new BusinessException("04");
        }
        return params;
    }

    private Map<String, String> request(Map<String, String> params, String url, String key) throws Exception {
        String sign;
        ArrayList<String> paramNames = new ArrayList<>(params.keySet());
        Collections.sort(paramNames);
        Iterator<String> iterator = paramNames.iterator();
        StringBuffer signSource = new StringBuffer(50);
        while (iterator.hasNext()) {
            String paramName = iterator.next();
            if (StringUtils.notBlank(String.valueOf(params.get(paramName)))) {
                signSource.append(paramName).append("=").append(params.get(paramName));
                if (iterator.hasNext()) signSource.append("&");
            }
        }
        signSource.append(key);
        sign = DigestUtils.md5DigestAsHex(signSource.toString().getBytes("UTF-8"));
        params.put("sign", sign);
        String resStr = HttpUtils.sendReq(JsonUtils.toJsonString(params), url, "UTF-8");
        params = JsonUtils.toObject(resStr, new TypeReference<Map<String, String>>() {
        });
        String serverSign = params.get("sign");
        params.remove("sign");
        paramNames = new ArrayList<>(params.keySet());
        Collections.sort(paramNames);
        // 组织待签名信息
        signSource = new StringBuffer();
        iterator = paramNames.iterator();
        while (iterator.hasNext()) {
            String paramName = iterator.next();
            if (!"sign".equals(paramName) && StringUtils.notBlank(params.get(paramName))) {
                signSource.append(paramName).append("=").append(params.get(paramName));
                if (iterator.hasNext()) signSource.append("&");
            }
        }

        signSource.append(key);
        // 计算签名
        String calSign = DigestUtils.md5DigestAsHex(signSource.toString().getBytes("UTF-8"));
        if (serverSign.equals(calSign)) {
            return params;
        }
        return params;
    }

    /**
     * 响应码 转换
     *
     * @param respCode
     * @return
     */
    private static String getRespMsg(String respCode) {
        return StringUtils.notBlank(respMap.get(respCode)) ? respMap.get(respCode) : "04";
    }
}