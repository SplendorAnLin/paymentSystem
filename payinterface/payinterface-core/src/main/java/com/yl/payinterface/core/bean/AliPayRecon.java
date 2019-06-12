package com.yl.payinterface.core.bean;

public class AliPayRecon {

    /** 支付宝业务ID **/
    private String billUserId;

    /** 查询token **/
    private String token;

    /** cookies **/
    private String cookies;

    /** 支付宝账号 **/
    private String partnerNo;

    /** 页数 **/
    private String pageNum;

    public String getBillUserId() {
        return billUserId;
    }

    public void setBillUserId(String billUserId) {
        this.billUserId = billUserId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }

    public String getPartnerNo() {
        return partnerNo;
    }

    public void setPartnerNo(String partnerNo) {
        this.partnerNo = partnerNo;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }
}
