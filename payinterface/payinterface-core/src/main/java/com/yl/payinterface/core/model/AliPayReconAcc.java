package com.yl.payinterface.core.model;

import com.yl.payinterface.core.enums.Status;

/**
 * 支付宝对账账户
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/8/18
 */
public class AliPayReconAcc {

    /** ID **/
	private Long id;

	/** 版本号 **/
	private Long optimistic;

	/** 支付宝用户名 **/
	private String userName;

	/** token **/
	private String token;

	/** 业务ID **/
	private String billUserId;

	/** cookies **/
	private String cookies;

	/** 状态 **/
	private Status status;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getBillUserId() {
		return billUserId;
	}

	public void setBillUserId(String billUserId) {
		this.billUserId = billUserId;
	}

	public String getCookies() {
		return cookies;
	}

	public void setCookies(String cookies) {
		this.cookies = cookies;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOptimistic() {
        return optimistic;
    }

    public void setOptimistic(Long optimistic) {
        this.optimistic = optimistic;
    }
}