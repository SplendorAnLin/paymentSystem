package com.yl.pay.pos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Title: 行业交易权限
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

@Entity
@Table(name = "INDUSTRY_FUNCTION")
public class IndustryFunction extends BaseEntity{

	private String mcc;						//MCC	
	private String trsanType;				//交易类型
	private Date lastUpdateTime;			//最后更新时间
	
	@Column(name = "MCC", length = 10)
	public String getMcc() {
		return mcc;
	}
	public void setMcc(String mcc) {
		this.mcc = mcc;
	}
	
	@Column(name = "TRANS_TYPE", columnDefinition = "VARCHAR(50)")
	public String getTrsanType() {
		return trsanType;
	}
	public void setTrsanType(String trsanType) {
		this.trsanType = trsanType;
	}
	
	@Column(name = "LAST_UPDATE_TIME", columnDefinition = "DATETIME")
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
}
