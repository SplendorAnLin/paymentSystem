package com.pay.sign.dbentity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.pay.sign.dao.util.BaseEntity;
import com.pay.sign.enums.CurrencyType;
import com.pay.sign.enums.TransType;

/**
 * Title: POS原始请求
 * Description: 
 * Copyright: Copyright (c)2015
 * Company: pay
 * @author zhongxiang.ren
 */

@Entity
@Table(name = "POS_REQUEST")
public class PosRequest extends BaseEntity{
	
	private static final long serialVersionUID = -2604449084017247873L;
	private TransType transType;			//交易类型
	private String pan;						//卡号
	private Double amount;					//交易金额	
	private String shopNo;					//网点编号
	private String posCati;					//终端号
	private String posBatch;				//POS批次号
	private String posRequestId;			//POS流水号
	private String operator;				//操作员
	private Date createTime;				//创建时间
	private Date completeTime;				//完成时间
	private String externalId;				//系统跟踪号/参考号
	private CurrencyType currencyType;		//货币类型
	private String responseCode;			//返回码
	private String exceptionCode;			//异常码
	
	//原交易信息类
	private String sourceBatchNo;			//原交易批次号
	private String sourcePosRequestId;		//原交易流水号
	private String sourceAuthorizationCode;	//原授权号
	private String sourceTransDate;			//原交易日期MMDD
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "TRANS_TYPE", columnDefinition = "VARCHAR(50)")
	public TransType getTransType() {
		return transType;
	}
	public void setTransType(TransType transType) {
		this.transType = transType;
	}
	
	@Column(name = "PAN", length = 50)
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	@Column(name = "SHOP_NO", length = 30)
	public String getShopNo() {
		return shopNo;
	}
	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}
	
	@Column(name = "POS_CATI", length = 20)
	public String getPosCati() {
		return posCati;
	}
	public void setPosCati(String posCati) {
		this.posCati = posCati;
	}
	
	@Column(name = "POS_BATCH", length = 20)
	public String getPosBatch() {
		return posBatch;
	}
	public void setPosBatch(String posBatch) {
		this.posBatch = posBatch;
	}
	
	@Column(name = "POS_REQUEST_ID", length = 50)
	public String getPosRequestId() {
		return posRequestId;
	}
	public void setPosRequestId(String posRequestId) {
		this.posRequestId = posRequestId;
	}
	
	@Column(name = "OPERATOR", length = 20)
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
		
	@Column(name = "CREATE_TIME", columnDefinition = "DATE")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "EXTERNAL_ID", length = 50)
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "CURRENCY_TYPE", columnDefinition = "VARCHAR(20)")
	public CurrencyType getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(CurrencyType currencyType) {
		this.currencyType = currencyType;
	}
	
	@Column(name = "S_BATCH_NO", length = 20)
	public String getSourceBatchNo() {
		return sourceBatchNo;
	}
	public void setSourceBatchNo(String sourceBatchNo) {
		this.sourceBatchNo = sourceBatchNo;
	}
	
	@Column(name = "S_POSREQUEST_ID", length = 20)
	public String getSourcePosRequestId() {
		return sourcePosRequestId;
	}
	public void setSourcePosRequestId(String sourcePosRequestId) {
		this.sourcePosRequestId = sourcePosRequestId;
	}
	
	@Column(name = "S_AUTHORIZATION_CODE", length = 20)
	public String getSourceAuthorizationCode() {
		return sourceAuthorizationCode;
	}
	public void setSourceAuthorizationCode(String sourceAuthorizationCode) {
		this.sourceAuthorizationCode = sourceAuthorizationCode;
	}
	
	@Column(name = "S_TRANS_DATE", length = 20)
	public String getSourceTransDate() {
		return sourceTransDate;
	}
	public void setSourceTransDate(String sourceTransDate) {
		this.sourceTransDate = sourceTransDate;
	}
	
	@Column(name = "RESPONSE_CODE", length = 20)
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	
	@Column(name = "COMPLETE_TIME", columnDefinition = "DATE")
	public Date getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}
	
	@Column(name = "EXCEPTION_CODE", length = 20)
	public String getExceptionCode() {
		return exceptionCode;
	}
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}	
}
