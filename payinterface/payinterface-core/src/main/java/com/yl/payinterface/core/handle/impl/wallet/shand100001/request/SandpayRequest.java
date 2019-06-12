package com.yl.payinterface.core.handle.impl.wallet.shand100001.request;

import com.yl.payinterface.core.handle.impl.wallet.shand100001.response.SandpayResponse;

public abstract class SandpayRequest<T extends SandpayResponse> {

    private SandpayRequestHead head;

    public SandpayRequest() {
    }

    public SandpayRequestHead getHead() {
        return this.head;
    }

    public void setHead(SandpayRequestHead head) {
        this.head = head;
    }

    public abstract Class<T> getResponseClass();

    public abstract String getTxnDesc();
}