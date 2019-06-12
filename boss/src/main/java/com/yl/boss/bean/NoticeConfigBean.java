package com.yl.boss.bean;

import java.util.Date;

import com.yl.boss.enums.Status;

public class NoticeConfigBean {
	
	private Long id;
	private int optimistic;
	private Date createDate; //创建时间
    private String config; //配置文件
    private String type;        //类型
    private String name;        //名称
    private String ownerId;     //所有人
    private String oem;         //oem标志
    private String oper;        //操作员
    private String source;      //来源
    private Status stauts;      //状态
    
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getOptimistic() {
		return optimistic;
	}

	public void setOptimistic(int optimistic) {
		this.optimistic = optimistic;
	}

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
