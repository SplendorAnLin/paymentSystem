package com.yl.online.gateway.utils;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.security.DigestUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.yl.online.gateway.AuthExceptionMessages;
import com.yl.online.gateway.ExceptionMessages;
import com.yl.online.gateway.context.RequestProxy;
import com.yl.online.gateway.enums.MerchantResponseCode;
import com.yl.online.gateway.exception.BusinessException;
import org.apache.commons.lang3.math.NumberUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.util.*;

/**
 * 通用工具类
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017年08月29日
 */
public class CommonUtils {

    private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);
    private static Properties prop = new Properties();
    private static String INPUT_CHARSET = "UTF-8";

    static {
        try {
            prop.load(CommonUtils.class.getClassLoader().getResourceAsStream("serverHost.properties"));
        } catch (IOException e) {
            logger.error("初始化 缓存中心地址 配置失败:", e);
        }
    }

    /**
     * Map 转化 键值对
     *
     * @param params
     * @return
     */
    public static String convertMap2Str(Map params) {
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
        return signSource.toString();
    }

    /**
     * 键值对 转换 Map
     *
     * @param str
     * @return
     */
    public static Map<String, String> kvStr2Map(String str) {
        String[] tmp;
        String[] strTmp;
        Map<String, String> params = new HashMap<>();
        if (StringUtils.notBlank(str)) {
            if (str.indexOf("&") > -1) {
                tmp = str.split("&");
                for (String s : tmp) {
                    if (s.indexOf("=") > -1) {
                        strTmp = s.split("=");
                        params.put(strTmp[0], strTmp.length == 1 ? "" : strTmp[1]);
                    }
                }
            } else if (str.indexOf("=") > -1) {
                tmp = str.split("=");
                params.put(tmp[0], tmp.length == 1 ? "" : tmp[1]);
            }
        }
        return params;
    }

    /**
     * http Base64请求参数转换 Map
     *
     * @param request
     * @return
     */
    public static Map<String, String> pareseParams(HttpServletRequest request) {
        Map reqParams = null;
        String line;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            if (sb.length() == 0) {
                return reqParams;
            }
            logger.error("请求报文 : {}", sb.toString());
            reqParams = JsonUtils.toObject(new String(Base64Utils.decode(sb.toString()), INPUT_CHARSET), new TypeReference<Map<String, String>>() {
            });
        } catch (Exception e) {
            logger.error("请求报文接收异常 : ", e);
        }
        return reqParams;
    }

    /**
     * http 通用请求参数转换 Map
     *
     * @param request
     * @return
     */
    public static Map<String, String> commonPareseParams(HttpServletRequest request) throws Exception{
        String  line;
        Map<String, String> reqParams;
        // 设置编码
        request = new RequestProxy(request);
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuffer sb = new StringBuffer();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        if (sb.length() == 0) {
            Map<String, String[]> OriginalParams = request.getParameterMap();
            reqParams = new HashMap<>();
            for (String key : OriginalParams.keySet()) {
                reqParams.put(key, OriginalParams.get(key)[0]);
            }
        } else {
            reqParams = JsonUtils.toObject(sb.toString(), new TypeReference<Map<String, String>>() {
            });
        }
        if (reqParams.size() == 0) {
            throw new BusinessException(ExceptionMessages.PARAM_NOT_EXISTS);
        }
        return reqParams;
    }

    /**
     * 校验卡信息（卢恩算法）
     *
     * @param cardNo
     * @return
     */
    public static boolean checkCard(String cardNo) {
        if (!NumberUtils.isNumber(cardNo) || cardNo.length() < 14)
            return false;

        String[] nums = cardNo.split("");
        int sum = 0;
        int index = 1;
        for (int i = 0; i < nums.length; i++) {
            if ((i + 1) % 2 == 0) {
                if ("".equals(nums[nums.length - index])) {
                    continue;
                }
                int tmp = Integer.parseInt(nums[nums.length - index]) * 2;
                if (tmp >= 10) {
                    String[] t = String.valueOf(tmp).split("");
                    tmp = Integer.parseInt(t[1]) + Integer.parseInt(t[2]);
                }
                sum += tmp;
            } else {
                if ("".equals(nums[nums.length - index]))
                    continue;
                sum += Integer.parseInt(nums[nums.length - index]);
            }
            index++;
        }
        if (sum % 10 != 0) {
            return false;
        }
        return true;
    }

    /**
     * 获取卡信息
     *
     * @param panNos
     * @return
     */
    public static List<Map<String, String>> getCardInfos(String panNos) {
        if (panNos == null || "".equals(panNos)) {
            return new ArrayList();
        }
        String cardsNo = "";
        if (panNos.indexOf(",") > -1) {
            String[] panNoArr = panNos.split(",");
            for (int i = 0; i < panNoArr.length; i++) {
                if (i == 0) {
                    cardsNo = "cardNos=" + panNoArr[i];
                    continue;
                }
                cardsNo += "&" + "cardNos=" + panNoArr[i];
            }
        } else {
            cardsNo = "cardNos=" + panNos;
        }
        List<Map<String, String>> list = null;
        Map<String, String> map;
        String url = prop.getProperty("cachecenter.service.url") + "/iin/batchRecognition.htm?fields=providerCode&fields=cardType&fields=cardName&" +
                "&fields=agencyName&fields=cardType&" + cardsNo;
        try {
            String resData = HttpClientUtils.send(HttpClientUtils.Method.POST, url, "", false, "");
            JsonNode iinNode = JsonUtils.getInstance().readTree(resData);

            list = new ArrayList<>();
            for (JsonNode iins : iinNode) {
                if (iins.size() == 0) {
                    map = new HashMap<>();
                    map.put("providerCode", "");
                    list.add(map);
                    continue;
                }
                map = new HashMap<>();
                map.put("providerCode", iins.get("providerCode").getValueAsText());
                map.put("cardType", iins.get("cardType").getValueAsText());
                map.put("cardName", iins.get("cardName").getValueAsText());
                map.put("agencyName", iins.get("agencyName").getValueAsText());
                list.add(map);
            }
        } catch (Exception e) {
            logger.error("系统异常:", e);
        }
        return list;
    }

    /**
     * 获取卡信息
     * @param cardNo
     * @return
     */
    public static Map getCardInfo(String cardNo) {
        if (!checkCard(cardNo)) {
            return null;
        }
        List<Map<String, String>> list = getCardInfos(cardNo);
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 卡校验
     *
     * @param cardNo
     */
    public static void checkCardNo(String cardNo) throws BusinessException {
        try {
            if (!checkCard(cardNo)) {
                throw new BusinessException(AuthExceptionMessages.TRNS_CARD_CHK_ERR.getCode());
            }
            String words = "&cardNo=" + cardNo;
            String url = prop.getProperty("cachecenter.service.url") + "/iin/recognition.htm?fields=agencyName" + words;
            if (HttpClientUtils.send(HttpClientUtils.Method.POST, url, "", true, Charset.forName("UTF-8").name(), 5000) == null) {
                throw new BusinessException(AuthExceptionMessages.TRNS_CARD_CHK_ERR.getCode());
            }
        } catch (Exception e) {
            logger.error("卡信息校验异常 : ", e);
            throw new BusinessException(AuthExceptionMessages.TRNS_CARD_CHK_ERR.getCode());
        }
    }

    /**
     * 错误码转义
     *
     * @param request 请求实体
     * @param e       异常实体
     */
    private void transferredErrorMsg(HttpServletRequest request, Exception e) {
        String errorMsg = "";
        MerchantResponseCode merchantResponseCode = MerchantResponseCode.UNKOWN_ERROR;
        if (e.getMessage() != null) {
            if (e.getClass().isAssignableFrom(RuntimeException.class)) {
                errorMsg = e.getMessage().substring(e.getMessage().lastIndexOf(":") + 2, e.getMessage().length());
            } else if (e.getClass().isAssignableFrom(BusinessException.class)) {
                errorMsg = e.getMessage();
            }
            merchantResponseCode = MerchantResponseCode.getMerchantCode(errorMsg);
        }
        request.setAttribute("responseCode", merchantResponseCode.getMerchantCode());
        request.setAttribute("responseMsg", merchantResponseCode.getResponseMessage());
    }

    /**
     * 金额校验
     *
     * @param amount
     * @throws BusinessException
     */
    private static void checkAmount(String amount) throws BusinessException {
        if (StringUtils.isBlank(amount)) {
            throw new BusinessException(ExceptionMessages.AMOUNT_NULL);
        }
        try {
            Double.valueOf(amount);
        } catch (Exception e) {
            throw new BusinessException(ExceptionMessages.PARAM_ERROR);
        }

        if (amount.indexOf(".") > 0) {
            if (amount.split("\\.")[1].length() > 2) {
                throw new BusinessException(ExceptionMessages.PARAM_ERROR);
            }
        }
    }

    /**
     * 金额校验
     * @param amount
     * @throws BusinessException
     */
    public static void amountCheck(String amount) throws BusinessException{
        // 金额校验
        if (StringUtils.notBlank(amount)) {
            try {
                if (!AmountUtils.eq(AmountUtils.round(Double.valueOf(amount), 2, RoundingMode.HALF_UP), Double.valueOf(amount))) {
                    throw new BusinessException(ExceptionMessages.AMOUNT_ERROR);
                }
            } catch (Exception e) {
                throw new BusinessException(ExceptionMessages.AMOUNT_ERROR);
            }
        } else {
            throw new BusinessException(ExceptionMessages.AMOUNT_ERROR);
        }
    }

    /**
     * md5签名校验
     *
     * @param params
     * @param key
     * @param charset
     * @return
     */
    public static boolean md5CheckSign(Map<String, String> params, String key, String charset) {
        ArrayList<String> paramNames;
        String serverSign = params.get("sign");
        params.remove("sign");
        paramNames = new ArrayList<>(params.keySet());
        Collections.sort(paramNames);
        // 组织待签名信息
        StringBuffer signSource = new StringBuffer();
        Iterator<String> iterator = paramNames.iterator();
        while (iterator.hasNext()) {
            String paramName = iterator.next();
            if (!"sign".equals(paramName) && StringUtils.notBlank(params.get(paramName))) {
                signSource.append(paramName).append("=").append(params.get(paramName));
                signSource.append("&");
            }
        }
        // 计算签名
        String calSign;
        try {
            logger.info("签名前 参数 : {}", signSource.substring(0, signSource.length() - 1) + key);
            calSign = DigestUtils.md5DigestAsHex((signSource.substring(0, signSource.length() - 1) + key).getBytes(StringUtils.notBlank(charset) ? charset : INPUT_CHARSET));
            if (serverSign.equals(calSign)) {
                return true;
            }
            logger.info("服务端签名 : {}", calSign);
            logger.info("客户端签名 : {}", serverSign);
        } catch (UnsupportedEncodingException e) {
            logger.error("签名校验异常 :", e);
        }
        return false;
    }

    /**
     * md5签名校验
     *
     * @param params
     * @param key
     * @return
     */
    public static boolean md5CheckSign(Map<String, String> params, String key) {
        return md5CheckSign(params, key, null);
    }

    /**
     * md5签名
     *
     * @param params
     * @param key
     * @param charset
     * @return
     */
    public static String md5Sign(Map<String, String> params, String key, String charset) {
        String sign = null;
        ArrayList<String> paramNames = new ArrayList<>(params.keySet());
        Collections.sort(paramNames);
        Iterator<String> iterator = paramNames.iterator();
        StringBuffer signSource = new StringBuffer(50);
        while (iterator.hasNext()) {
            String paramName = iterator.next();
            if (StringUtils.notBlank(String.valueOf(params.get(paramName)))) {
                signSource.append(paramName).append("=").append(params.get(paramName));
                signSource.append("&");
            }
        }
        try {
            logger.info("签名前 参数 : {}", signSource.substring(0, signSource.length() - 1) + key);
            sign = DigestUtils.md5DigestAsHex((signSource.substring(0, signSource.length() - 1) + key).getBytes(StringUtils.notBlank(charset) ? charset : INPUT_CHARSET));
        } catch (Exception e) {
            logger.error("签名异常 :", e);
        }
        return sign;
    }

    /**
     * md5签名
     *
     * @param params
     * @param key
     * @return
     */
    public static String md5Sign(Map<String, String> params, String key) {
        return md5Sign(params, key, null);
    }

}
