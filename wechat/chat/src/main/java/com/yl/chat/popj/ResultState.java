package com.yl.chat.popj;

/**
 * 结果状态码
 *
 * @author Andy
 */
public class ResultState implements java.io.Serializable {

    private int errcode; // 状态

    private String errmsg; // 状态

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}