package com.yl.rate.interfaces.beans;

import java.io.Serializable;

/**
 * @author Shark
 * @Description
 * @Date 2017/12/3 17:43
 */
public class RateResponseBean implements Serializable {
    /**
     * 手续费
     */
    private Double fee;
    /**
     * 响应编号
     */
    private String responseCode;
    /**
     * 响应信息
     */
    private String responseMessage;
    /**
     * 状态
     */
    private String status;

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RateResponseBean() {
        this.fee = -1d;
        this.responseCode = "100001";
        this.responseMessage = "未匹配到规则";
        this.status = "FAILED";
    }
}
