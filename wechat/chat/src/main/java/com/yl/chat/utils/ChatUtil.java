package com.yl.chat.utils;

import com.lefu.commons.cache.util.CacheUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.chat.Constant;
import com.yl.chat.popj.AccessToken;
import com.yl.chat.popj.Menu;
import com.yl.chat.service.impl.MessageServiceImpl;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zk
 */
public class ChatUtil {

    private static Logger log = LoggerFactory.getLogger(ChatUtil.class);

    public static final String URL_SEND_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

    // 菜单创建（POST） 限100（次/天）
    public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

    // 获取access_token的接口地址（GET） 限200（次/天）
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    //获取用户信息
    public final static String info_url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    // 获取openId
    public final static String access_token_url_openId = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=APPSECRET&code=CODE&grant_type=authorization_code";

    private static Properties prop = new Properties();

    static {
        try {
            prop.load(new InputStreamReader(ChatUtil.class.getClassLoader().getResourceAsStream("serverHost.properties")));
        } catch (IOException e) {
            log.error("ChatUtil load Properties error:", e);
        }
    }


    public static String getOpenId(String code) {
        String requestUrl = access_token_url_openId.replace("APPID", prop.getProperty("APPID")).replace("APPSECRET", prop.getProperty("APPSECRET")).replace("CODE", code);
        JSONObject jsonObject = HttpUtils.sendJsonReq(requestUrl); //code使用过一次
        String openId = "";
        // 如果请求成功
        if (null != jsonObject) {
            try {
                openId = jsonObject.getString("openid");
            } catch (JSONException e) {
                openId = null;
                log.error("获取openId失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return openId;
    }

    /**
     * 发送异常信息
     *
     * @param sys
     * @param type
     * @param remark
     */
    public static void sendEx(String sys, String level, String type, String oper, String first, String remark, String color) {
        Map<String, String> info = new HashMap<>();
        SimpleDateFormat time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        if (first == null) {
            info.put("first", "您好，系统发生异常信息：");
        } else {
            info.put("first", first);
        }
        info.put("keyword1", sys);
        info.put("keyword2", time.format(new Date()));
        info.put("keyword3", level);
        info.put("keyword4", type);
        info.put("keyword5", oper);
        info.put("remark", remark);
        MessageServiceImpl message = new MessageServiceImpl();
        for (Object openId : ChatUtil.getGroupUserByName("技术组")) {
            info.put("color", color);
            message.send(openId.toString(), Constant.templeEX, Constant.CLICK_URL, info, true);
        }
    }

    /**
     * 根据组名称获取组用户
     *
     * @param name
     * @return
     */
    public static List getGroupUserByName(String name) {
        JSONObject req = HttpUtils.sendJsonReq("https://api.weixin.qq.com/cgi-bin/tags/get?access_token=" + ChatUtil.getSysAccessToken());
        if (null != req) {
            try {
                List group = req.getJSONArray("tags");
                for (Object info : group) {
                    Map one = JsonUtils.toJsonToObject(info, Map.class);
                    if (one.get("name").equals(name)) {
                        return getGroupUserById(one.get("id").toString());
                    }
                }
            } catch (JSONException e) {
                log.error("获取用户组失败 errcode:{} errmsg:{}", req.getInt("errcode"), req.getString("errmsg"));
            }
        }
        return null;
    }

    /**
     * 根据组ID获取组用户
     *
     * @param id
     * @return
     */
    public static List getGroupUserById(String id) {
        Map<String, String> reqMap = new HashMap<>();
        reqMap.put("tagid", id);
        reqMap.put("next_openid", "");
        JSONObject req = HttpUtils.sendJsonReq("https://api.weixin.qq.com/cgi-bin/user/tag/get?access_token=" + ChatUtil.getSysAccessToken(), JsonUtils.toJsonString(reqMap), "POST");
        List group = null;
        if (null != req) {
            try {
                group = req.getJSONObject("data").getJSONArray("openid");
            } catch (JSONException e) {
                group = null;
                log.error("获取用户组失败 errcode:{} errmsg:{}", req.getInt("errcode"), req.getString("errmsg"));
            }
        }
        return group;
    }

    /**
     * 获取access_token
     *
     * @param appid     凭证
     * @param appsecret 密钥
     * @return
     */
    public static AccessToken getAccessToken(String appid, String appsecret) {
        AccessToken accessToken = null;
        String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
        JSONObject jsonObject = HttpUtils.sendJsonReq(requestUrl);
        // 如果请求成功
        if (null != jsonObject) {
            try {
                accessToken = new AccessToken();
                accessToken.setAccess_token(jsonObject.getString("access_token"));
                accessToken.setExpires_in(jsonObject.getInt("expires_in"));
            } catch (JSONException e) {
                accessToken = null;
                log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return accessToken;
    }

    public static Map findMenu() {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=" + ChatUtil.getSysAccessToken();
        JSONObject jsonObject = HttpUtils.sendJsonReq(url);
        if (null != jsonObject) {
            try {
                if (0 != jsonObject.getInt("errcode")) {
                    log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
                }
            } catch (Exception e) {
                log.info("获取菜单信息为：{}", JsonUtils.toJsonString(jsonObject));
            }
        }
        return JsonUtils.toJsonToObject(jsonObject.get("menu"), Map.class);
    }

    /**
     * 获取微信系统权限
     *
     * @return
     */
    public static String getSysAccessToken() {
        String accessToken = null;
        try {
            accessToken = CacheUtils.get(Constant.OPERATOR_RESOURCE_CACHE_DB, Constant.CHAT_ACCESS_TOKEN, String.class, true);
            if (accessToken != null && !"".equals(accessToken)) {
                return accessToken;
            } else {
                AccessToken token = getAccessToken(prop.getProperty("APPID"), prop.getProperty("APPSECRET"));
                CacheUtils.setEx(Constant.OPERATOR_RESOURCE_CACHE_DB, Constant.CHAT_ACCESS_TOKEN, token.getAccess_token(), token.getExpires_in() - 500, true);//防止网络延迟-500MS
                return token.getAccess_token();
            }
        } catch (Exception e) {
            AccessToken token = getAccessToken(prop.getProperty("APPID"), prop.getProperty("APPSECRET"));
            log.error("缓存token失败，临时token为:{}", token.getAccess_token());
            return token.getAccess_token();
        }
    }


    /**
     * 创建菜单
     *
     * @param menu 菜单实例
     * @return 0表示成功，其他值表示失败
     */
    public static int createMenu(Menu menu) {
        int result = 0;
        // 拼装创建菜单的url
        String url = menu_create_url.replace("ACCESS_TOKEN", getSysAccessToken());
        // 调用接口创建菜单
        JSONObject jsonObject = HttpUtils.sendJsonReq(url, JsonUtils.toJsonString(menu), "POST");
        if (null != jsonObject) {
            if (0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
                log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return result;
    }

    public static int createMenu(String menu) {
        int result = 0;
        // 拼装创建菜单的url
        String url = menu_create_url.replace("ACCESS_TOKEN", getSysAccessToken());
        // 调用接口创建菜单
        JSONObject jsonObject = HttpUtils.sendJsonReq(url, menu, "POST");
        if (null != jsonObject) {
            if (0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
                log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return result;
    }
}