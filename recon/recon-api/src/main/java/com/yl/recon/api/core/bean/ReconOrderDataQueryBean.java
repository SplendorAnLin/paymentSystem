package com.yl.recon.api.core.bean;

/**
 * 聚合支付有限公司
 *
 * @author 邱健
 * @version V1.0.0
 * @create 2018年01月20
 * @desc 对账数据查询bean
 **/

import com.yl.recon.api.core.bean.order.*;
import com.yl.recon.api.core.enums.ReconFileType;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2018年01月20
 */
public class ReconOrderDataQueryBean implements Serializable {

	/**
	 * 对账文件类型
	 */
	private ReconFileType reconFileType;
	private AccountOrder accountOrder;
	private BaseBankChannelOrder baseBankChannelOrder;
	private PayinterfaceOrder payinterfaceOrder;
	private TradeOrder tradeOrder;
	private RemitOrder remitOrder;
	private RealAuthOrder realAuthOrder;
	/**
	 * 创建时间开始
	 */
	private String createStartTime;
	/**
	 * 创建时间结束
	 */
	private String createEndTime;
	/**
	 * 对账日期
	 */
	private Date reconDate;


	public ReconOrderDataQueryBean() {
	}

	public AccountOrder getAccountOrder() {
		return accountOrder;
	}

	public void setAccountOrder(AccountOrder accountOrder) {
		this.accountOrder = accountOrder;
	}

	public BaseBankChannelOrder getBaseBankChannelOrder() {
		return baseBankChannelOrder;
	}

	public void setBaseBankChannelOrder(BaseBankChannelOrder baseBankChannelOrder) {
		this.baseBankChannelOrder = baseBankChannelOrder;
	}

	public PayinterfaceOrder getPayinterfaceOrder() {
		return payinterfaceOrder;
	}

	public void setPayinterfaceOrder(PayinterfaceOrder payinterfaceOrder) {
		this.payinterfaceOrder = payinterfaceOrder;
	}

	public TradeOrder getTradeOrder() {
		return tradeOrder;
	}

	public void setTradeOrder(TradeOrder tradeOrder) {
		this.tradeOrder = tradeOrder;
	}

	public ReconFileType getReconFileType() {
		return reconFileType;
	}

	public void setReconFileType(ReconFileType reconFileType) {
		this.reconFileType = reconFileType;
	}


	public String getCreateStartTime() {
		return createStartTime;
	}

	public void setCreateStartTime(String createStartTime) {
		this.createStartTime = createStartTime;
	}

	public String getCreateEndTime() {
		return createEndTime;
	}

	public void setCreateEndTime(String createEndTime) {
		this.createEndTime = createEndTime;
	}

	public Date getReconDate() {
		return reconDate;
	}

	public void setReconDate(Date reconDate) {
		this.reconDate = reconDate;
	}

	public RemitOrder getRemitOrder() {
		return remitOrder;
	}

	public void setRemitOrder(RemitOrder remitOrder) {
		this.remitOrder = remitOrder;
	}

	public RealAuthOrder getRealAuthOrder() {
		return realAuthOrder;
	}

	public void setRealAuthOrder(RealAuthOrder realAuthOrder) {
		this.realAuthOrder = realAuthOrder;
	}
}
