package com.yl.client.front.model;

import java.io.Serializable;
import java.util.Date;

public class Notice implements Serializable{
	private static final long serialVersionUID = -477131732669356016L;
	
	private Long id;
	private String toForm;
	private String content;
	private Date createTime;
	
	public Long getId() {
		return id;
	}
	public String getToForm() {
		return toForm;
	}
	public String getContent() {
		return content;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setToForm(String toForm) {
		this.toForm = toForm;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
