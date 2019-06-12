package com.yl.payinterface.core.bean;

import com.yl.payinterface.core.enums.Status;
import java.io.Serializable;
import java.util.Date;

/**
 * 支付宝个人收款码
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/8/15
 */
public class AliPayCollectionCodeBean implements Serializable {

    private static final long serialVersionUID = -14983199385657558L;

    /** 主键 **/
    private String id;

    /** 编号 **/
    private String code;

    /** 版本号 **/
    private Long version;

    /** 创建时间 **/
    private Date createTime;

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

    /** 接口请求编号 **/
    private String interfaceRequestID;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

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

    public String getInterfaceRequestID() {
        return interfaceRequestID;
    }

    public void setInterfaceRequestID(String interfaceRequestID) {
        this.interfaceRequestID = interfaceRequestID;
    }
}