package com.yl.risk.core.bean;

import java.io.Serializable;

/**
 * 风控响应参数
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/11/22
 */
public class RiskResponseBean implements Serializable {

    /** 结果编码 */
    private String resultCode;

    /** 处理备注 */
    private String resultMsg;

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public RiskResponseBean(String code, String msg){
        this.resultCode = code;
        this.resultMsg = msg;
    }

    public RiskResponseBean() {
    }
}