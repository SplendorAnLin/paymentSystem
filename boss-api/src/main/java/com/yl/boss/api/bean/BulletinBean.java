package com.yl.boss.api.bean;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import com.yl.boss.api.enums.BulletinSysType;
import com.yl.boss.api.enums.BulletinType;

/**
 * 公告Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月16日
 * @version V1.0.0
 */
public class BulletinBean extends BaseBean implements Serializable {
	
	private static final long serialVersionUID = -3596317933866548645L;
	private String content;//��������
	private BulletinType status;//״̬
	private Date createTime;//����ʱ��
	private Date effTime;//��Чʱ��
	private Date extTime;//ʧЧʱ��
	private String operator;//����Ա
	private String title;//����
	private BulletinSysType sysCode;//ϵͳ����
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public BulletinType getStatus() {
		return status;
	}
	public void setStatus(BulletinType status) {
		this.status = status;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getEffTime() {
		return effTime;
	}
	public void setEffTime(Date effTime) {
		this.effTime = effTime;
	}
	public Date getExtTime() {
		return extTime;
	}
	public void setExtTime(Date extTime) {
		this.extTime = extTime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public BulletinSysType getSysCode() {
		return sysCode;
	}
	public void setSysCode(BulletinSysType sysCode) {
		this.sysCode = sysCode;
	}
}
