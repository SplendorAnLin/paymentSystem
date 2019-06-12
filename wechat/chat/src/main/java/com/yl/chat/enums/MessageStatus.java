package com.yl.chat.enums;

/**
 * 基本状态
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年8月11日
 */
public enum MessageStatus {
    /**
     * 成功
     */
    SUCCESS,
    /**
     * 待发送
     */
    WAIT_SEND,
    /**
     * 拒收
     */
    NO_ACCEPT,
    /**
     * 其他失败
     */
    FAIL
}