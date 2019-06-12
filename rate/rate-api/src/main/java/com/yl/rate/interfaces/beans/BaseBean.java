package com.yl.rate.interfaces.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础信息
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/12/12
 */
public abstract class BaseBean implements Serializable {

    /** id */
    private Long id;

    /** 编号 */
    private String code;

    /** 版本号 */
    private int version;

    /** 创建时间 */
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", version=" + version +
                ", createTime=" + createTime +
                '}';
    }
}