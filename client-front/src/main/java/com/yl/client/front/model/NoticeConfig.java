package com.yl.client.front.model;

import com.yl.client.front.enums.Status;
import net.sf.json.JSONObject;

import java.util.Date;

public class NoticeConfig extends BaseEntity  {
    private Date createDate; //创建时间
    private String config; //配置文件
    private String type;        //类型
    private String name;        //名称
    private String ownerId;     //所有人
    private String oem;         //oem标志
    private String oper;        //操作员
    private String source;      //来源
    private Status stauts;      //状态

    public Status getStauts() {
        return stauts;
    }

    public void setStauts(Status stauts) {
        this.stauts = stauts;
    }

    public Date getCreateDate() {

        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOem() {
        return oem;
    }

    public void setOem(String oem) {
        this.oem = oem;
    }

    public String getOper() {
        return oper;
    }

    public void setOper(String oper) {
        this.oper = oper;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
