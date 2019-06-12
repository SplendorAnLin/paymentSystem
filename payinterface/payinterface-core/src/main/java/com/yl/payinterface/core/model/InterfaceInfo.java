package com.yl.payinterface.core.model;

import java.util.Date;

import com.yl.payinterface.core.enums.FeeType;
import com.yl.payinterface.core.enums.InterfaceHandlerType;
import com.yl.payinterface.core.enums.InterfaceInfoStatus;
import com.yl.payinterface.core.enums.InterfaceType;
import com.yl.payinterface.core.enums.ReverseSwitch;

/**
 * 接口信息（渠道）
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月27日
 * @version V1.0.0
 */
public class InterfaceInfo extends AutoStringIDModel {

	private static final long serialVersionUID = 8089842196046180049L;
	/** 接口提供方信息 */
	private String provider;
	/** 名称 */
	private String name;
	/** 接口类型 */
	private InterfaceType type;
	/** 支持的卡类型相关信息 */
	private String cardType;
	/** 单笔最大交易额 */
	private Double singleAmountLimit;
	/** 单笔最小交易额 */
	private Double singleAmountLimitSmall;
	/** 开始时间 */
	private String startTime;
	/** 结束时间 */
	private String endTime;
	/** 状态 */
	private InterfaceInfoStatus status;
	/** 接口补单开关 */
	private ReverseSwitch reverseSwitch;
	/** 交易配置 */
	private String tradeConfigs;
	/** 描述 */
	private String description;
	/** 最后更新时间 */
	private Date lastModifyTime;
	/** 接口处理类型 */
	private InterfaceHandlerType handlerType;
	/** 费率类型 **/
	private FeeType feeType;
	/** 费率 **/
	private double fee;
	/** 是否实时通道 */
	private String isReal;

	public Double getSingleAmountLimitSmall() {
		return singleAmountLimitSmall;
	}

	public void setSingleAmountLimitSmall(Double singleAmountLimitSmall) {
		this.singleAmountLimitSmall = singleAmountLimitSmall;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public InterfaceType getType() {
		return type;
	}

	public void setType(InterfaceType type) {
		this.type = type;
	}

	public Double getSingleAmountLimit() {
		return singleAmountLimit;
	}

	public void setSingleAmountLimit(Double singleAmountLimit) {
		this.singleAmountLimit = singleAmountLimit;
	}

	public InterfaceInfoStatus getStatus() {
		return status;
	}

	public void setStatus(InterfaceInfoStatus status) {
		this.status = status;
	}

	public ReverseSwitch getReverseSwitch() {
		return reverseSwitch;
	}

	public void setReverseSwitch(ReverseSwitch reverseSwitch) {
		this.reverseSwitch = reverseSwitch;
	}

	public String getTradeConfigs() {
		return tradeConfigs;
	}

	public void setTradeConfigs(String tradeConfigs) {
		this.tradeConfigs = tradeConfigs;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public InterfaceHandlerType getHandlerType() {
		return handlerType;
	}

	public void setHandlerType(InterfaceHandlerType handlerType) {
		this.handlerType = handlerType;
	}

	public FeeType getFeeType() {
		return feeType;
	}

	public void setFeeType(FeeType feeType) {
		this.feeType = feeType;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getIsReal() {
		return isReal;
	}

	public void setIsReal(String isReal) {
		this.isReal = isReal;
	}
	
}