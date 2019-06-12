package com.yl.dpay.core.entity;

import java.util.Date;

/**
 * 代付配置历史记录
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月12日
 * @version V1.0.0
 */
public class DpayConfigHistory extends DpayConfig{

	private static final long serialVersionUID = -2947689980369979699L;
	
	/**
	 * 操作人
	 */
	private String oper;

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public DpayConfigHistory(DpayConfig dpayConfig,String oper) {
		this.oper = oper;
		super.setStatus(dpayConfig.getStatus());
		super.setCreateDate(new Date());
		super.setAuditAmount(dpayConfig.getAuditAmount());
		super.setCreateDate(new Date());
		super.setEndTime(dpayConfig.getEndTime());
		super.setHolidayStatus(dpayConfig.getHolidayStatus());
		super.setMaxAmount(dpayConfig.getMaxAmount());
		super.setMinAmount(dpayConfig.getMinAmount());
		super.setRemitType(dpayConfig.getRemitType());
		super.setStartTime(dpayConfig.getStartTime());
		super.setReRemitFlag(dpayConfig.getReRemitFlag());
	}
	
}
