package com.yl.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 公告读取Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月15日
 * @version V1.0.0
 */
@Entity
@Table(name="BULLETIN_READ")
public class BulletinRead extends AutoIDEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4703747270674642139L;
    private String sysType;
    private String sysCode;
    private Long bulletinId;
    private Date readTime;
    
    @Column(name = "SYS_TYPE", length = 100)
    public String getSysType()
    {
        return sysType;
    }
    public void setSysType(String sysType)
    {
        this.sysType = sysType;
    }
    @Column(name = "SYS_CODE", length = 100)
    public String getSysCode()
    {
        return sysCode;
    }
    public void setSysCode(String sysCode)
    {
        this.sysCode = sysCode;
    }
    @Column(name = "BULLETIN_ID", length = 20)
    public Long getBulletinId()
    {
        return bulletinId;
    }
    public void setBulletinId(Long bulletinId)
    {
        this.bulletinId = bulletinId;
    }
    @Column(name = "READ_TIME",columnDefinition = "DATE")
    public Date getReadTime()
    {
        return readTime;
    }
    public void setReadTime(Date readTime)
    {
        this.readTime = readTime;
    }
}
