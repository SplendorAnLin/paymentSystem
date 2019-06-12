package com.yl.payinterface.core.handle.impl.remit.guomei100001.utils;

import com.lefu.commons.cache.util.CacheUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
 * 国美代付签名工具类
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/6/14
 */
public class GuoMeiSignUtils {

    private static final Logger logger = LoggerFactory.getLogger(GuoMeiSignUtils.class);

    public static String sendPost(String url, String aid, String api_id, String access_token, String data){
        String res = "";
        try {
            res = HttpClientUtils.send(HttpClientUtils.Method.POST, create_access_token_url(url, aid, api_id, access_token), data, true, "UTF-8", 300000);
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
        return res;
    }

    public static String create_access_token_url(String url, String aid, String api_id, String access_token) {
        try {
            if (aid != null) {
                url = url.replace("AID", aid);
            }
            if (api_id != null) {
                url = url.replace("API_ID", api_id);
            }
            if (access_token != null) {
                url = url.replace("ACCESS_TOKEN", access_token);
            }
        } catch (Exception e) {
            logger.error("国美代付create_access_token_url异常!异常信息:{}", e);
        }
        return url;
    }

    public static String create_get_access_token_url(String url, String aid, String keys) {
        try {
            String api_id = "";
            String timestamp = getTimestamp();
            String nonce = getNonce();
            String signature = getSignature(aid, keys, timestamp, nonce);
            if (aid != null) {
                url = url.replace("AID", aid);
            }
            url = url.replace("SIGNATURE", signature).replace("TIMESTAMP", timestamp).replace("NONCE", nonce);
        } catch (Exception e) {
            logger.error("国美代付acc_token_url获取异常!异常信息:{}", e);
        }
        return url;
    }

    public static String getToken(String url, String aid, String keys){
        String access_token = CacheUtils.get("GM-TOKEN", String.class);
        try {
            if (StringUtils.isBlank(access_token)) {
                String res = HttpClientUtils.get(create_get_access_token_url(url, aid, keys));
                Map<String, Object> resMap = JsonUtils.toObject(res, new TypeReference<Map<String, Object>>() {
                });
                access_token = resMap.get("access_token").toString();
                CacheUtils.setEx("GM-TOKEN", access_token, 900);
            }
        } catch (Exception e) {
            logger.error("https异常!异常信息:{}", e);
        }
        logger.info("access_token:{}", access_token);
        return access_token;
    }

    public static String getSignature(String aid, String key, String timestamp, String nonce) {
        if (aid == null) {
            aid = "";
        }
        if (key == null) {
            key = "";
        }
        if (timestamp == null) {
            timestamp = "";
        }
        if (nonce == null) {
            nonce = "";
        }
        String[] args = new String[]{aid, key, timestamp, nonce};
        return getSignature(args);
    }

    public static String getSignature(String[] args) {
        if (args != null && args.length != 0) {
            Arrays.sort(args);
            StringBuilder content = new StringBuilder();
            for(int i = 0; i < args.length; ++i) {
                content.append(args[i]);
            }
            logger.info("签名原串：" + content.toString());
            MessageDigest md = null;
            String tmp_str = null;
            try {
                md = MessageDigest.getInstance("SHA-1");
                byte[] digest = md.digest(content.toString().getBytes());
                tmp_str = byteToStr(digest);
            } catch (NoSuchAlgorithmException var5) {
                var5.printStackTrace();
            }
            content = null;
            return tmp_str;
        } else {
            return null;
        }
    }

    private static String byteToStr(byte[] byte_array) {
        String str_digest = "";
        for(int i = 0; i < byte_array.length; ++i) {
            str_digest = str_digest + byteToHexStr(byte_array[i]);
        }
        return str_digest;
    }

    private static String byteToHexStr(byte m_byte) {
        char[] digit = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] temp_arr = new char[]{digit[m_byte >>> 4 & 15], digit[m_byte & 15]};
        String s = new String(temp_arr);
        return s;
    }

    public static String getTimestamp() {
        return Long.toString((new Date()).getTime());
    }

    public static String getNonce() {
        Random random = new Random();
        StringBuilder nonce = new StringBuilder();
        nonce.append(random.nextInt(10)).append(random.nextInt(10)).append(random.nextInt(10)).append(random.nextInt(10)).append(random.nextInt(10)).append(random.nextInt(10));
        return nonce.toString();
    }
}