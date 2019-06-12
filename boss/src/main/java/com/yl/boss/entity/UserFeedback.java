package com.yl.boss.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 意见反馈Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
@Entity
@Table(name = "user_feedback")
public class UserFeedback extends AutoIDEntity {

	private static final long serialVersionUID = 624295928770209738L;
	
	/**
	 * 会员编号
	 */
	private String customerNo;
	
	/**
	 * 意见内容
	 */
	private String content;
	
	/**
	 * 反馈日期
	 */
	private Timestamp createTime;
	
	/**
	 * 状态
	 */
	private String status;
	
	/** OEM */
	private String oem;

	
	@Column(name = "OME", length = 30)
	public String getOem() {
		return oem;
	}

	public void setOem(String oem) {
		this.oem = oem;
	}

	@Column(name = "CUSTOMER_NO", length = 30)
	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	@Column(name = "CONTENT", length = 300)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "CREATE_TIME", columnDefinition = "DATETIME")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "STATUS", length = 15)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}