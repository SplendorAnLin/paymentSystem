package com.yl.chat.wecaht.model;


/**
 * 获取微信用户授权的信息
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017年6月13日
 */
public class User {

    private String openid;//用户的唯一标识

    private String customer;

    private String phone;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}