package com.pay.sign.dbentity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.pay.sign.dao.util.BaseEntity;

@Entity
@Table(name = "POS_SIGNPIC")
public class SignPic extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	private String signFilePath;				//图片路径
	private Long orderId;						//订单主键
	private String externalId;				    //系统跟踪号/参考号
	private Date  creditTime;					//创建时间
	private String ICSystemRelated ;            //*IC卡数据域 Integrated Circuit Card System Related
	private String synMark;  					//N代表未同步 ，Y代表已同步
	@Column(name="SYN_MARK",length =10)
	public String getSynMark() {
		return synMark;
	}
	public void setSynMark(String synMark) {
		this.synMark = synMark;
	}
	@Column(name = "SIGN_FILEPATH", length = 100)
	public String getSignFilePath() {
		return signFilePath;
	}
	public void setSignFilePath(String signFilePath) {
		this.signFilePath = signFilePath;
	}
	@Column(name = "ORDER_ID", columnDefinition = "NUMBER")
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	@Column(name = "EXTERNAL_ID", length = 50)
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	@Column(name = "CREDIT_TIME", columnDefinition = "DATE")
	public Date getCreditTime() {
		return creditTime;
	}
	public void setCreditTime(Date creditTime) {
		this.creditTime = creditTime;
	}
	@Column(name = "IC_SYSTEM_RELATED", length = 500)
	public String getICSystemRelated() {
		return ICSystemRelated;
	}
	public void setICSystemRelated(String iCSystemRelated) {
		ICSystemRelated = iCSystemRelated;
	}
	
}
