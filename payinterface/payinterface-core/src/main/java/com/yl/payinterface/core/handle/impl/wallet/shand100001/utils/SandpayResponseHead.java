package com.yl.payinterface.core.handle.impl.wallet.shand100001.utils;

public class SandpayResponseHead {

    public String version;
    public String respTime;
    public String respCode;
    public String respMsg;

    public SandpayResponseHead() {
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRespTime() {
        return this.respTime;
    }

    public void setRespTime(String respTime) {
        this.respTime = respTime;
    }

    public String getRespCode() {
        return this.respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return this.respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }
}
