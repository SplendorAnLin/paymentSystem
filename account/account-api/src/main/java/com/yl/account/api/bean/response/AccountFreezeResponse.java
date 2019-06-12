package com.yl.account.api.bean.response;

import com.yl.account.api.bean.BussinessResponse;

/**
 * 账户冻结响应
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月21日
 * @version V1.0.0
 */
public class AccountFreezeResponse extends BussinessResponse {

	private static final long serialVersionUID = -8988230766847949463L;

	/** 冻结编号 */
	private String freezeNo;

	public String getFreezeNo() {
		return freezeNo;
	}

	public void setFreezeNo(String freezeNo) {
		this.freezeNo = freezeNo;
	}

	@Override
	public String toString() {
		return "AccountFreezeResponse [freezeNo=" + freezeNo + "]";
	}

}
