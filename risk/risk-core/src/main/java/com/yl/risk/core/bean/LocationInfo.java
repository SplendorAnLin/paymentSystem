package com.yl.risk.core.bean;

import java.io.Serializable;

/**
 * IP 地址信息
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/11/17
 */
public class LocationInfo implements Serializable {

    /** 国家 */
    private String country;

    /** 省份 */
    private String state;

    /** 城市 */
    private String city;

    /** 服务提供商 */
    private String isp;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    @Override
    public String toString() {
        return "LocationInfo{" +
                "country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", isp='" + isp + '\'' +
                '}';
    }
}