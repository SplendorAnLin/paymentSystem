package com.yl.chat.request;

import java.util.Map;

/**
 * 抽象请求
 *
 * @author Andy
 */
public abstract class AbstractRequest {

    /**
     * 返回请求参数
     *
     * @return
     */
    public abstract Map<String, String> getParams() throws Exception;
}