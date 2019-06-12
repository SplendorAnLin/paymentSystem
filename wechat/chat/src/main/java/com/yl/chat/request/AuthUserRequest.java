package com.yl.chat.request;

import java.util.Map;
import java.util.TreeMap;

/**
 * 获取用户信息请求
 *
 * @author Andy
 */
public class AuthUserRequest extends AbstractRequest implements
        java.io.Serializable {
	private static final long serialVersionUID = 1097614064395718617L;
	private String accessToken;
    private String openid;
    private String lang;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public Map<String, String> getParams() throws Exception {
        Map<String, String> params = new TreeMap<String, String>();
        params.put("access_token", this.accessToken);
        params.put("openid", this.openid);
        params.put("lang", this.lang);
        return params;
    }
}