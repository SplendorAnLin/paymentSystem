package com.yl.payinterface.core.handle.impl.wallet.shand100001.request;

public class SandpayRequestHead {
    public String version;
    public String method;
    public String productId;
    public String accessType;
    public String mid;
    public String subMid;
    public String subMidName;
    public String subMidAddr;
    public String channelType;
    public String reqTime;

    public SandpayRequestHead() {
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getAccessType() {
        return this.accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getMid() {
        return this.mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getSubMid() {
        return this.subMid;
    }

    public void setSubMid(String subMid) {
        this.subMid = subMid;
    }

    public String getSubMidName() {
        return this.subMidName;
    }

    public void setSubMidName(String subMidName) {
        this.subMidName = subMidName;
    }

    public String getSubMidAddr() {
        return this.subMidAddr;
    }

    public void setSubMidAddr(String subMidAddr) {
        this.subMidAddr = subMidAddr;
    }

    public String getChannelType() {
        return this.channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getReqTime() {
        return this.reqTime;
    }

    public void setReqTime(String reqTime) {
        this.reqTime = reqTime;
    }
}
