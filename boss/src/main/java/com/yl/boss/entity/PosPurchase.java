package com.yl.boss.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import com.yl.boss.enums.PosType;
import com.yl.boss.enums.RequestRecord;

/**
 * POS申请
 *
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */

@Entity
@Table(name = "POS_PURCHASE")
public class PosPurchase extends AutoIDEntity{
	
	private static final long serialVersionUID = 1L;
	
	/** 商户编号 */
	private String customerNo;
	
	/** POS类型 */
	private PosType PosType;
	
	/** 联系电话 */
	private String phone;
	
	/** 联系地址 */
	private String addr;
	
	/** 备注 */
	private String remark;
	
	/** 请求类型 */
	private RequestRecord requestRecord;
	
	/** 创建时间 */
	private Date createTime;

	
	
	@Column(name = "CUSTOMER_NO", length = 30)
	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	@Enumerated(value = EnumType.STRING)
	@Column(name = "POS_TYPE", columnDefinition = "VARCHAR(30)")
	public PosType getPosType() {
		return PosType;
	}

	public void setPosType(PosType posType) {
		PosType = posType;
	}

	@Column(name = "PHONE", length = 30)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "ADDR", length = 50)
	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Column(name = "REMARK", length = 255)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Enumerated(value = EnumType.STRING)
	@Column(name = "REQUEST_RECORD", columnDefinition = "VARCHAR(30)")
	public RequestRecord getRequestRecord() {
		return requestRecord;
	}

	public void setRequestRecord(RequestRecord requestRecord) {
		this.requestRecord = requestRecord;
	}

	@Column(name = "CREATE_TIME", columnDefinition = "DATETIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}