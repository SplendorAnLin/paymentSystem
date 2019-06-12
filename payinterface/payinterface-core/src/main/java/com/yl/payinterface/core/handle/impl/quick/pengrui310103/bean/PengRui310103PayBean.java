package com.yl.payinterface.core.handle.impl.quick.pengrui310103.bean;

import net.sf.json.JSONObject;

/**
 * 全时优服 消费Bean
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/3/1
 */
public class PengRui310103PayBean {

    /** 请求头 */
    private JSONObject head;

    /** 交易流水 */
    private String workId;

    /** 验证码 */
    private String smsCode;

    public JSONObject getHead() {
        return head;
    }

    public void setHead(JSONObject head) {
        this.head = head;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
}