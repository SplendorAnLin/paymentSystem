package com.yl.pay.pos.entity;


import com.yl.pay.pos.enums.YesNo;

import javax.persistence.*;
import java.util.Date;

/**
 * Title: 银行接口mcc分类配置（乐富类商户专用）
 * Description: 
 * Company: com.yl.pay
 * @author haitao.liu
 */
@Entity
@Table(name = "BANK_INTERFACE_MCC_CONFIG")
public class BankInterfaceMccConfig extends BaseEntity{
	
	private static final long serialVersionUID = -3203612556582750372L;

	private String bankInterfaceCode;	//银行接口编号	
	
	private YesNo isOpenConfig;		
	
	private String selectParams;
	
	private Date createTime;

	@Column(name = "bank_Interface_Code", length = 20)
	public String getBankInterfaceCode() {
		return bankInterfaceCode;
	}

	public void setBankInterfaceCode(String bankInterfaceCode) {
		this.bankInterfaceCode = bankInterfaceCode;
	}

	@Enumerated(value = EnumType.STRING)
	@Column(name = "IS_OPEN_CONFIG", columnDefinition = "VARCHAR(50)")
	public YesNo getIsOpenConfig() {
		return isOpenConfig;
	}

	public void setIsOpenConfig(YesNo isOpenConfig) {
		this.isOpenConfig = isOpenConfig;
	}
	
	@Column(name = "SELECT_PARAMS", length = 20)
	public String getSelectParams() {
		return selectParams;
	}

	public void setSelectParams(String selectParams) {
		this.selectParams = selectParams;
	}

	@Column(name = "CREATE_TIME",columnDefinition = "DATETIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
