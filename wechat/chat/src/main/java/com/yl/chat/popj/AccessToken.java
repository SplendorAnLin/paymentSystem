package com.yl.chat.popj;

/**
 * 微信通用接口凭证
 *
 * @author liufeng
 * @date 2013-08-08
 */
public class AccessToken {

    private String access_token; // 获取到的凭证

    private Integer expires_in; // 凭证有效时间，单位：秒

    private String refresh_token; // 用户刷新access_token

    private String openid; // 用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID

    private String scope; // 用户授权的作用域，使用逗号（,）分隔

    private String unionid; // 网页返回 unionid

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String accessToken) {
        access_token = accessToken;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refreshToken) {
        refresh_token = refreshToken;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}