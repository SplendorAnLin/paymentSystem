package com.yl.recon.api.core.bean;


import com.yl.recon.api.core.enums.ReconStatus;
import com.yl.recon.api.core.enums.ReconType;
import com.yl.utils.excel.ExcelField;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2018年01月05
 */
public class ReconOrder implements Serializable {
	@ExcelField(name = "对账单编号", column = "A")
	private String code;
	@ExcelField(name = "对账日期", column = "B", columnWidth = "15", formatDate = "yyyy-MM-dd")
	private Date reconDate;
	@ExcelField(name = "对账类型", column = "C", dict = "RECON_TYPE", columnWidth = "15")
	private ReconType reconType;
	@ExcelField(name = "A总笔数", column = "D")
	private Integer numsA;
	@ExcelField(name = "B总笔数", column = "E")
	private Integer numsB;
	@ExcelField(name = "A总金额", column = "F", round = "2")
	private Double amountA;
	@ExcelField(name = "B总金额", column = "G", round = "2")
	private Double amountB;
	@ExcelField(name = "A单边笔数", column = "H")
	private Integer failNumsA;
	@ExcelField(name = "B单边笔数", column = "I")
	private Integer failNumsB;
	@ExcelField(name = "A单边金额", column = "J", round = "2")
	private Double failAmountA;
	@ExcelField(name = "B单边金额", column = "K", round = "2")
	private Double failAmountB;
	@ExcelField(name = "金额错误笔数", column = "L")
	private Integer amountErrNum;
	@ExcelField(name = "对账状态", column = "M", dict = "RECON_STATUS")
	private ReconStatus reconStatus;
	private long version;
	@ExcelField(name = "创建时间", column = "N", columnWidth = "20", formatDate = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	@ExcelField(name = "对账描述", column = "O")
	private String msg;
	@ExcelField(name = "失败原因", column = "P")
	private String failureReason;
	@ExcelField(name = "匹配元素A", column = "Q")
	private String matchA;
	@ExcelField(name = "匹配元素B", column = "R")
	private String matchB;
	private Integer repeatNumA;
	private Integer repeatNumB;


	//	  	金 	创建时间	对账描述

	public ReconOrder() {
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getReconDate() {
		return reconDate;
	}

	public void setReconDate(Date reconDate) {
		this.reconDate = reconDate;
	}

	public ReconType getReconType() {
		return reconType;
	}

	public void setReconType(ReconType reconType) {
		this.reconType = reconType;
	}

	public ReconStatus getReconStatus() {
		return reconStatus;
	}

	public void setReconStatus(ReconStatus reconStatus) {
		this.reconStatus = reconStatus;
	}

	public String getMatchA() {
		return matchA;
	}

	public void setMatchA(String matchA) {
		this.matchA = matchA;
	}

	public Integer getRepeatNumA() {
		return repeatNumA;
	}

	public void setRepeatNumA(Integer repeatNumA) {
		this.repeatNumA = repeatNumA;
	}

	public Integer getNumsA() {
		return numsA;
	}

	public void setNumsA(Integer numsA) {
		this.numsA = numsA;
	}

	public Double getAmountA() {
		return amountA;
	}

	public void setAmountA(Double amountA) {
		this.amountA = amountA;
	}

	public Integer getFailNumsA() {
		return failNumsA;
	}

	public void setFailNumsA(Integer failNumsA) {
		this.failNumsA = failNumsA;
	}

	public Double getFailAmountA() {
		return failAmountA;
	}

	public void setFailAmountA(Double failAmountA) {
		this.failAmountA = failAmountA;
	}

	public String getMatchB() {
		return matchB;
	}

	public void setMatchB(String matchB) {
		this.matchB = matchB;
	}

	public Integer getRepeatNumB() {
		return repeatNumB;
	}

	public void setRepeatNumB(Integer repeatNumB) {
		this.repeatNumB = repeatNumB;
	}

	public Integer getNumsB() {
		return numsB;
	}

	public void setNumsB(Integer numsB) {
		this.numsB = numsB;
	}

	public Double getAmountB() {
		return amountB;
	}

	public void setAmountB(Double amountB) {
		this.amountB = amountB;
	}

	public Integer getFailNumsB() {
		return failNumsB;
	}

	public void setFailNumsB(Integer failNumsB) {
		this.failNumsB = failNumsB;
	}

	public Double getFailAmountB() {
		return failAmountB;
	}

	public void setFailAmountB(Double failAmountB) {
		this.failAmountB = failAmountB;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	public Integer getAmountErrNum() {
		return amountErrNum;
	}

	public void setAmountErrNum(Integer amountErrNum) {
		this.amountErrNum = amountErrNum;
	}
}