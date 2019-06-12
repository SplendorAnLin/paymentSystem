package com.yl.payinterface.core.model;

import com.yl.payinterface.core.enums.FeeType;
import com.yl.payinterface.core.enums.InterfaceInfoStatus;
import com.yl.payinterface.core.enums.InterfaceType;

/**
 * 接口信息操作历史
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月27日
 * @version V1.0.0
 */
public class InterfaceInfoHistory extends AutoStringIDModel {

	private static final long serialVersionUID = -2421167469940051940L;
	/** 接口编号 */
	private String interfaceInfoCode;
	/** 接口提供方 */
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
	/** 交易配置 */
	private String tradeConfigs;
	/** 描述 */
	private String description;
	/** 操作来源 */
	private String invokeSystem;
	/** 操作人 */
	private String operator;
	/** 费率类型 **/
	private FeeType feeType;
	/** 费率 **/
	private double fee;
	/** 是否实时通道 */
	private String isReal;

	public InterfaceInfoHistory() {
		super();
	}

	public InterfaceInfoHistory(InterfaceInfo interfaceInfo, String invokeSystem, String operator) {
		super();
		this.interfaceInfoCode = interfaceInfo.getCode();
		this.provider = interfaceInfo.getProvider();
		this.name = interfaceInfo.getName();
		this.type = interfaceInfo.getType();
		this.singleAmountLimit = interfaceInfo.getSingleAmountLimit();
		this.singleAmountLimitSmall=interfaceInfo.getSingleAmountLimitSmall();
		this.startTime=interfaceInfo.getStartTime();
		this.endTime=interfaceInfo.getEndTime();
		this.status = interfaceInfo.getStatus();
		this.tradeConfigs = interfaceInfo.getTradeConfigs();
		this.description = interfaceInfo.getDescription();
		this.invokeSystem = invokeSystem;
		this.operator = operator;
		this.isReal = interfaceInfo.getIsReal();
		this.setVersion(interfaceInfo.getVersion());
		this.cardType = interfaceInfo.getCardType();
		this.fee = interfaceInfo.getFee();
		this.feeType = interfaceInfo.getFeeType();
	}
	
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

	public String getInterfaceInfoCode() {
		return interfaceInfoCode;
	}

	public void setInterfaceInfoCode(String interfaceInfoCode) {
		this.interfaceInfoCode = interfaceInfoCode;
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

	public String getInvokeSystem() {
		return invokeSystem;
	}

	public void setInvokeSystem(String invokeSystem) {
		this.invokeSystem = invokeSystem;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
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