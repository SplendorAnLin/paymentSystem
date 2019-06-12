package com.yl.rate.core;

/**
 * @author Shark
 * @Description
 * @Date 2017/12/7 11:39
 */
public enum ExceptionMessages {

    SUCCESS("000000", "成功"),
    RULE_NOT_MATCH("100001", "未匹配到规则"),
    FEE_TYPE_ERROR("100002", "费率类型错误"),
    AMOUNT_FEE_ERROR("100003","订单金额小于手续费"),
    PARAMETER_ERROR("100004","参数错误");

    private final String code;
    private final String message;

    ExceptionMessages(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getMessage(String code) {
        for (ExceptionMessages t : ExceptionMessages.values()) {
            if (code.equals(t.getCode())) {
                return t.getMessage();
            }
        }
        return null;
    }

    public static String getErrMsg(String code) {
        return code + ":" + getMessage(code);
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
