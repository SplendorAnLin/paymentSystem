package com.yl.payinterface.core.handle.impl.remit.ylzf420101.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @ClassName YLZF420101RemitBean
 * @Description 发起代付Bean
 * @author Administrator
 * @date 2017年11月27日 下午3:30:19
 */
public class YLZF420101RemitBean implements Serializable {

	private static final long serialVersionUID = -3767061472390370355L;
	
	private String action = "QCash";
	private String txnamt;
	private String merid;
	private String orderid;
	/** 联行号 */
	private String bin;
	/** 银行卡号 */
	private String accno;
	/** 持卡人 */
	private String accname;
	private String backurl;
	private String code;
	/** 身份证号 */
	@JsonInclude(Include.NON_NULL) 
	private String cardno;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getTxnamt() {
		return txnamt;
	}

	public void setTxnamt(String txnamt) {
		this.txnamt = txnamt;
	}

	public String getMerid() {
		return merid;
	}

	public void setMerid(String merid) {
		this.merid = merid;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getBin() {
		return bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public String getAccno() {
		return accno;
	}

	public void setAccno(String accno) {
		this.accno = accno;
	}

	public String getAccname() {
		return accname;
	}

	public void setAccname(String accname) {
		this.accname = accname;
	}

	public String getBackurl() {
		return backurl;
	}

	public void setBackurl(String backurl) {
		this.backurl = backurl;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	@Override
	public String toString() {
		return "YLZF420101RemitBean [action=" + action + ", txnamt=" + txnamt + ", merid=" + merid + ", orderid=" + orderid + ", bin=" + bin + ", accno=" + accno + ", accname=" + accname + ", backurl=" + backurl + ", code=" + code + ", cardno=" + cardno + "]";
	}

}
