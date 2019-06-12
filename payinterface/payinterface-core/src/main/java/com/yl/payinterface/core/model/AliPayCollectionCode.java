package com.yl.payinterface.core.model;

import com.yl.payinterface.core.enums.Status;
import java.util.Date;

/**
 * 支付宝个人收款码
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/8/15
 */
public class AliPayCollectionCode extends AutoStringIDModel{

    private static final long serialVersionUID = 5160963487425047877L;

    /** 二维码链接 **/
    private String url;

    /** 收款支付宝账号 **/
    private String aliPayAcc;

    /** 二维码金额 **/
    private double amount;

    /** 备注 **/
    private String remarks;

    /** 状态 **/
    private Status status;

    /** 使用次数 **/
    private int count;

    /** 最后一次使用时间 **/
    private Date lastTime;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAliPayAcc() {
        return aliPayAcc;
    }

    public void setAliPayAcc(String aliPayAcc) {
        this.aliPayAcc = aliPayAcc;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }
}