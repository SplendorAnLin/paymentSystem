package com.yl.chat.utils;

import com.lefu.commons.cache.util.CacheUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.security.DigestUtils;
import com.yl.chat.Constant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 连接工具类
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/11/23
 */
public class ConnectionUtils {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionUtils.class);

    private static Properties prop = new Properties();

    static {
        try {
            prop.load(ConnectionUtils.class.getClassLoader().getResourceAsStream("serverHost.properties"));
        } catch (IOException e) {
            logger.error("prop 加载失败!:{}", e);
        }
    }

    /**
     * 获取APP数据
     *
     * @param userName
     * @param info
     * @param code
     * @return
     * @throws Exception
     */
    public static String sendReq(String userName, Map<String, Object> info, String code) {
        long startTime = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String res = "";
        String checkSign = getSign(userName);
        if (StringUtils.isBlank(checkSign)){
            String sign = CodeBuilder.build("sign", "yyMMdd");
            Map<String,Object> signInfo = new HashMap<>();//登录身份信息记录
            signInfo.put("name", userName);
            signInfo.put("customerNo", info.get("customerNo"));
            signInfo.put("sign", sign);
            signInfo.put("jiguangId", "G_" + info.get("customerNo"));
            CacheUtils.setEx(Constant.OPERATOR_RESOURCE_CACHE_DB, Constant.OPERATOR_RESOUSE + "." + userName, signInfo, 172800, true);
            logger.info("补入APP用户授权信息：{}", JsonUtils.toJsonString(signInfo));
        }
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("ACTION_NAME", code);
            Map<String, String> msg = new HashMap<>();
            msg.put("netType", "wifi");
            msg.put("phone", userName);
            msg.put("address", info.get("ipAdd").toString());
            msg.put("lat", info.get("lat").toString());
            msg.put("lng", info.get("lng").toString());
            msg.put("clientVersion", getVersion("ios"));
            msg.put("oem", "ylzf");
            msg.put("clientType", "ios");
            msg.put("sysVersion", "11.1.2");
            msg.put("requestTime", sdf.format(new Date()));
            msg.put("msg", getSign(userName));
            params.put("ACTION_INFO", info);
            params.put("md5", md5(info, msg));
            params.put("ACTION_MSG", msg);
            res = HttpUtils.sendReq(prop.getProperty("appUrl"), JsonUtils.toJsonString(encryptApp(JsonUtils.toJsonString(params))), "POST");
            res = JsonUtils.toJsonString(decryptApp(JsonUtils.toObject(res, Map.class)));
            logger.info("接收返回数据：{}, 用时：{} MS!", res, System.currentTimeMillis() - startTime);
        } catch (Exception e) {
            logger.error("数据发送异常：", e);
        }
        return res;
    }

    /**
     * 获取sign信息
     *
     * @param phone
     * @return
     */
    public static String getSign(String phone) {
        String sign = "";
        Map loginInfo = CacheUtils.get(Constant.OPERATOR_RESOURCE_CACHE_DB, Constant.OPERATOR_RESOUSE + "." + phone, Map.class, true);
        if (loginInfo != null && loginInfo.get("sign") != null && StringUtils.isNotBlank(loginInfo.get("sign").toString())) {
            sign = loginInfo.get("sign").toString();
        }
        return sign;
    }

    /**
     * 获取最新版本号
     *
     * @param sysType
     * @return
     */
    public static String getVersion(String sysType) {
        String version = "";
        Map loginInfo = CacheUtils.get(Constant.OPERATOR_RESOURCE_CACHE_DB, Constant.SYSTEM_VERSION + "." + sysType, Map.class, true);
        if (loginInfo != null && loginInfo.get("version") != null && StringUtils.isNotBlank(loginInfo.get("version").toString())) {
            version = loginInfo.get("version").toString();
        }
        return version;
    }

    /**
     * App 请求解密
     *
     * @param info
     * @return
     * @throws Exception
     */
    public static Map<String, Object> decryptApp(Map<String, Object> info) throws Exception {
        String pwd = AESUtils.outKey(info.get("mark").toString(), info.get("timestamp").toString());
        String data = info.get("data").toString();
        String reqStr = AESUtils.decrypt(data, pwd);
        Map<String, Object> reqInfo = JsonUtils.toObject(reqStr, Map.class);
        return reqInfo;
    }

    /**
     * App 请求加密
     *
     * @param params
     * @return
     * @throws Exception
     */
    public static Map<String, String> encryptApp(String params) throws Exception {
        String time = Long.toString(System.currentTimeMillis());
        String uuid = CodeBuilder.build();
        String resStr = AESUtils.encrypt(params, AESUtils.outKey(time, uuid));
        Map<String, String> resMap = new HashMap<>();
        resMap.put("timestamp", time);
        resMap.put("mark", uuid);
        resMap.put("data", resStr);
        return resMap;
    }

    /**
     * MD5 加密工具
     *
     * @param info
     * @param msg
     * @return
     * @throws UnsupportedEncodingException
     */
    static String md5(Map<String, Object> info, Map<String, String> msg) throws UnsupportedEncodingException {
        Map<String, Object> temp = new HashMap<>();
        temp.putAll(info);
        temp.putAll(msg);
        ArrayList<String> paramNames = new ArrayList<>(temp.keySet());
        Collections.sort(paramNames);
        Iterator<String> iterator = paramNames.iterator();
        StringBuffer signSource = new StringBuffer();
        while (iterator.hasNext()) {
            String paramName = iterator.next();
            if (StringUtils.isNotBlank(String.valueOf(temp.get(paramName)))) {
                signSource.append(paramName).append("=").append(temp.get(paramName));
                if (iterator.hasNext()) signSource.append("&");
            }
        }
        return DigestUtils.md5DigestAsHex(signSource.toString().getBytes("UTF-8"));
    }
}