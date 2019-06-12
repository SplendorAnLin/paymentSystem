package com.yl.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.yl.customer.enums.BulletinType;

/**
 * 公告Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月15日
 * @version V1.0.0
 */
@Entity
@Table(name="BULLETIN")
public class Bulletin extends AutoIDEntity {
	
	private static final long serialVersionUID = -3596317933866548645L;
	private String content;//公告内容
	private BulletinType status;//状态
	private Date createTime;//创建时间
	private Date effTime;//生效时间
	private Date extTime;//失效时间
	private String operator;//操作员
	private String title;//标题
	private String flag;//NEW
	
	
	@Column(name = "CONTENT", columnDefinition = "CLOB")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Enumerated(value = EnumType.STRING)
	@Column(name = "STATUS", length = 10)
	public BulletinType getStatus() {
		return status;
	}
	public void setStatus(BulletinType status) {
		this.status = status;
	}
	
	@Column(name = "CREATE_TIME",columnDefinition = "DATE")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name = "EFF_TIME",columnDefinition = "DATE")
	public Date getEffTime() {
		return effTime;
	}
	public void setEffTime(Date effTime) {
		this.effTime = effTime;
	}
	@Column(name = "EXT_TIME",columnDefinition = "DATE")
	public Date getExtTime() {
		return extTime;
	}
	public void setExtTime(Date extTime) {
		this.extTime = extTime;
	}
	@Column(name = "OPERATOR", length = 10)
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	@Column(name = "TITLE", length = 30)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name = "FLAG", length = 10)
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
}
