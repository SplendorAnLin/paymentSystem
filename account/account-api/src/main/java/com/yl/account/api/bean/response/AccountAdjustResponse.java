package com.yl.account.api.bean.response;

import com.yl.account.api.bean.BussinessResponse;
import com.yl.account.api.enums.AdjustStatus;

/**
 * 账户调账响应
 * 
 * @author 聚合支付有限公司
 * @since 2016年2月24日
 * @version V1.0.0
 */
public class AccountAdjustResponse extends BussinessResponse {

	private static final long serialVersionUID = -3940447924619799577L;

	/** 调账状态 */
	private AdjustStatus adjustStatus;
	/** 冻结编号 */
	private String preezeNo;

	public AdjustStatus getAdjustStatus() {
		return adjustStatus;
	}

	public void setAdjustStatus(AdjustStatus adjustStatus) {
		this.adjustStatus = adjustStatus;
	}

	public String getPreezeNo() {
		return preezeNo;
	}

	public void setPreezeNo(String preezeNo) {
		this.preezeNo = preezeNo;
	}

	@Override
	public String toString() {
		return "AccountAdjustResponse [adjustStatus=" + adjustStatus + ", preezeNo=" + preezeNo + "]";
	}

}
