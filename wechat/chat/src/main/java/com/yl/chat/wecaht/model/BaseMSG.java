package com.yl.chat.wecaht.model;

public class BaseMSG {
    /**
     * 头部
     */
    private String first;
    /**
     * 底部
     */
    private String remark;

    public String getFirst() {
        return first;
    }

    public String getRemark() {
        return remark;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}