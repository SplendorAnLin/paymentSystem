package com.yl.chat.wecaht.model;

import com.yl.chat.enums.MessageStatus;

/**
 * 待发送信息
 */
public class WaitInfo {

    Long id;

    String openId;

    String templeId;

    String url;

    String info;

    String sendTime;

    boolean isDiyColor;

    MessageStatus status;

    String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public String getTempleId() {
        return templeId;
    }

    public String getUrl() {
        return url;
    }

    public String getInfo() {
        return info;
    }

    public boolean isDiyColor() {
        return isDiyColor;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public String getRemark() {
        return remark;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public void setTempleId(String templeId) {
        this.templeId = templeId;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public void setDiyColor(boolean isDiyColor) {
        this.isDiyColor = isDiyColor;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}