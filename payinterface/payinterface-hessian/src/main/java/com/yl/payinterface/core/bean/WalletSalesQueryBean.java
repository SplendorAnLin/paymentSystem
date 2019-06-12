package com.yl.payinterface.core.bean;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * 钱包支付查询业务处理Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月24日
 * @version V1.0.0
 */
public class WalletSalesQueryBean implements Serializable {

	private static final long serialVersionUID = -3758549242410013166L;

	/** 业务订单编号 */
	@NotNull
	private String businessOrderID;

	public String getBusinessOrderID() {
		return businessOrderID;
	}

	public void setBusinessOrderID(String businessOrderID) {
		this.businessOrderID = businessOrderID;
	}

	@Override
	public String toString() {
		return "WalletSalesQueryBean [businessOrderID=" + businessOrderID + "]";
	}
	
}
