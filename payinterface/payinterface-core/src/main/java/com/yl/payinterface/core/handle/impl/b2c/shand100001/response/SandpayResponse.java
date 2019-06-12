package com.yl.payinterface.core.handle.impl.b2c.shand100001.response;

import com.yl.payinterface.core.handle.impl.b2c.shand100001.utils.SandpayResponseHead;

public class SandpayResponse {

    public SandpayResponseHead head;

    public SandpayResponse() {
    }

    public SandpayResponseHead getHead() {
        return this.head;
    }

    public void setHead(SandpayResponseHead head) {
        this.head = head;
    }
}