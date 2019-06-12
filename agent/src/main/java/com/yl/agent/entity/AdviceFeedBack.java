package com.yl.agent.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 意见反馈
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月8日
 * @version V1.0.0
 */
@Entity
@Table(name = "ADVICE_FEEDBACK")
public class AdviceFeedBack extends AutoIDEntity {
	private static final long serialVersionUID = 8095255465629525275L;
	private String initiator;    //发起人 
	private String phoneNo;		 //手机号
	private String content;		 //内容
	private Date createTime;	 //创建时间
	private String level;		 //级别
	private Date processTime;  //处理时间
	private String status;		 //状态
	
	@Column(name = "INITIATOR", length = 50)
	public String getInitiator() {
		return initiator;
	}
	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}
	
	@Column(name = "LEVELS", length = 10)
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
	@Column(name = "CREATE_TIME", columnDefinition = "DATE")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "PROCESS_TIME", columnDefinition = "DATE")
	public Date getProcessTime() {
		return processTime;
	}
	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}
	
	@Column(name = "STATUS", length = 10)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "CONTENT", length = 1000)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(name = "PHONE_NO", length = 20)
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
}
