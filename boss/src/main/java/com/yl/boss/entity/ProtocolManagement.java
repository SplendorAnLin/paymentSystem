package com.yl.boss.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import com.yl.boss.enums.Status;

/**
 * 协议管理Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
@Entity
@Table(name="protocol_management")
public class ProtocolManagement extends AutoIDEntity{

	private static final long serialVersionUID = 1L;

	/**
	 * 内容标题
	 */
	private String title;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 类型分类
	 */
	private String sort;
	/**
	 * 文档类型
	 */
	private String type;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 类型
	 */
	private Status status;
	/**
	 * 操作员
	 */
	private String operator;
	/**
	 * 系统公告备注
	 */
	private String msgTxt;
	
	@Column(name="TITLE")
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name="TYPE")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name="STATUS")	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}

	@Column(name="CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name="MSG_TXT")
	public String getMsgTxt() {
		return msgTxt;
	}

	public void setMsgTxt(String msgTxt) {
		this.msgTxt = msgTxt;
	}
	@Column(name="OPER")
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	@Column(name="UPDATE_TIME")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@Column(name="SORT")
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	@Column(name = "CONTENT", length = 50000)
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
}