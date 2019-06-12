package com.yl.chat.request;

import java.util.Map;
import java.util.TreeMap;

/**
 * 获取授权请求token的请求参数
 *
 * @author Andy
 */
public class AuthTokenRequest extends AbstractRequest implements
        java.io.Serializable {

	private static final long serialVersionUID = -2006821270935001106L;

	private String appid;

    private String secret;

    private String code;

    private String grant_type = "authorization_code";

    public AuthTokenRequest() {
        super();
    }

    public AuthTokenRequest(String appid, String secret, String code,
                            String grant_type) {
        super();
        this.appid = appid;
        this.secret = secret;
        this.code = code;
        this.grant_type = grant_type;
    }

    /**
     * 请求参数
     *
     * @return
     */
    public Map<String, String> getParams() {
        Map<String, String> params = new TreeMap<String, String>();
        params.put("appid", this.appid);
        params.put("secret", this.secret);
        params.put("code", this.code);
        params.put("grant_type", this.grant_type);
        return params;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGrant_type() {
        return grant_type;
    }
}