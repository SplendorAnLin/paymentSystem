package com.yl.payinterface.core.handle.impl.remit.kingpass100001.utils;

import java.util.List;
import java.util.Map;

/**
 * Created by ruanxin on 2017/7/11.
 */
public class MrpException extends Exception {
    private Map<Integer, List<String>> messageMap;
    final static int MRPCODE = 001;//缺少必填参数
    final static int MPPCODE = 002;//多传了参数
    public MrpException(Map<Integer, List<String>> messageMap) {
        this.messageMap = messageMap;
    }

    public void setMessageMap(Map<Integer, List<String>> messageMap) {
        this.messageMap = messageMap;
    }

    public Map<Integer, List<String>> getMessageMap() {
        return messageMap;
    }
}
