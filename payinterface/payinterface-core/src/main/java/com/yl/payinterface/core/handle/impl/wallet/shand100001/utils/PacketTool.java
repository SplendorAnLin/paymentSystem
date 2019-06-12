package com.yl.payinterface.core.handle.impl.wallet.shand100001.utils;

import com.yl.payinterface.core.handle.impl.wallet.shand100001.SandpayConstants;
import com.yl.payinterface.core.handle.impl.wallet.shand100001.request.SandpayRequestHead;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PacketTool {
    public PacketTool() {
    }

    public static void setDefaultRequestHead(SandpayRequestHead head, String method, String productId, String mid) {
        head.setVersion("1.0");
        head.setMethod(method);
        head.setProductId(productId);
        head.setAccessType(SandpayConstants.AccessType.merchant.code);
        head.setMid(mid);
        head.setSubMid("");
        head.setSubMidName("");
        head.setSubMidAddr("");
        head.setChannelType(SandpayConstants.ChannelType.INTERNET.getCode());
        head.setReqTime((new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()));
    }
}