package com.yl.pay.pos.api.bean;

import java.util.Date;

/**
 * Title: POS
 * Description:  POS终端
 * Copyright: Copyright (c)2011
 * Company: lepay
 * @author haitao.liu
 */
public class Pos extends BaseEntity{

	private String posCati;			//POS终端号
	private String customerNo;		//所属商户
	private String posBrand;		//品牌
	private String type;			//POS机型号
	private String shopNo;				//所属网点
	private String status;			//状态
	private String runStatus;	//运行状态
	private String batchNo;			//批次号
	private String operator;		//操作员
	private String posSn;			//POS机具序列号
	private String softVersion;		//当前软件版本号
	private String paramVersion;	//当前参数版本号
	private String mKey;			//主密钥(密文)
	private Date lastSigninTime;	//最后签到时间
	private Date createTime;		//创建时间
	private String posType;			//大pos，小pos
	private String routeType;	//路由类型


	public String getPosCati() {
		return posCati;
	}
	public void setPosCati(String posCati) {
		this.posCati = posCati;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getPosBrand() {
		return posBrand;
	}
	public void setPosBrand(String posBrand) {
		this.posBrand = posBrand;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getShopNo() {
		return shopNo;
	}
	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}
	public String getStatus() {
		return status;
	}
	public String getRunStatus() {
		return runStatus;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setRunStatus(String runStatus) {
		this.runStatus = runStatus;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getPosSn() {
		return posSn;
	}
	public void setPosSn(String posSn) {
		this.posSn = posSn;
	}
	public String getSoftVersion() {
		return softVersion;
	}
	public void setSoftVersion(String softVersion) {
		this.softVersion = softVersion;
	}
	public String getParamVersion() {
		return paramVersion;
	}
	public void setParamVersion(String paramVersion) {
		this.paramVersion = paramVersion;
	}
	public String getMKey() {
		return mKey;
	}
	public void setMKey(String mKey) {
		this.mKey = mKey;
	}
	public Date getLastSigninTime() {
		return lastSigninTime;
	}
	public void setLastSigninTime(Date lastSigninTime) {
		this.lastSigninTime = lastSigninTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getPosType() {
		return posType;
	}
	public void setPosType(String posType) {
		this.posType = posType;
	}
	public String getRouteType() {
		return routeType;
	}
	public void setRouteType(String routeType) {
		this.routeType = routeType;
	}
}
