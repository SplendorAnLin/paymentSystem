package com.yl.risk.api.bean;

/**
 * 响应信息
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/11/14
 */
public class RiskResponseMessage {

    /** 主键 */
    private Integer id;

    /** 编号 */
    private String code;

    /** 信息 */
    private String message;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}