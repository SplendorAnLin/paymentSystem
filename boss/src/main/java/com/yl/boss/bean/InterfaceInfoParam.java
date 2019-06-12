package com.yl.boss.bean;

import java.io.Serializable;

import com.yl.payinterface.core.enums.BillType;
import com.yl.payinterface.core.enums.ConnectType;
import com.yl.payinterface.core.enums.InterfaceHandlerType;
import com.yl.payinterface.core.enums.InterfaceInfoStatus;
import com.yl.payinterface.core.enums.InterfaceType;
import com.yl.payinterface.core.enums.TradeType;

/**
 * 接口信息参数Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月8日
 * @version V1.0.0
 */
public class InterfaceInfoParam implements Serializable {
	private static final long serialVersionUID = -6686996571648081545L;
	/** 编号 */
	private String code;
	/** 名称 */
	private String name;
	/** 接口类型 */
	private InterfaceType type;
	/** 状态 */
	private InterfaceInfoStatus status;
	/** 卡类型 */
	private String[] cardType;
	/** 支持的提供方列表 */
	private String[] supProvider;
	/** 接口连接方式 */
	private ConnectType connectType;
	/** 支持的交易类型 */
	private TradeType[] supportTradeTypes;
	/** 接口账单类型 */
	private BillType billType;
	/** 支持的币种 */
	private String[] supportCurrencies;
	/** 创建开始时间 */
	private String createTimeStart;
	/** 创建截止时间 */
	private String createTimeEnd;
	/** 接口处理类型 add 20140916 wcy */
	private InterfaceHandlerType handlerType;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public InterfaceInfoStatus getStatus() {
		return status;
	}

	public void setStatus(InterfaceInfoStatus status) {
		this.status = status;
	}

	public String[] getCardType() {
		return cardType;
	}

	public void setCardType(String[] cardType) {
		this.cardType = cardType;
	}

	public ConnectType getConnectType() {
		return connectType;
	}

	public void setConnectType(ConnectType connectType) {
		this.connectType = connectType;
	}

	public TradeType[] getSupportTradeTypes() {
		return supportTradeTypes;
	}

	public void setSupportTradeTypes(TradeType[] supportTradeTypes) {
		this.supportTradeTypes = supportTradeTypes;
	}

	public BillType getBillType() {
		return billType;
	}

	public void setBillType(BillType billType) {
		this.billType = billType;
	}

	public String[] getSupportCurrencies() {
		return supportCurrencies;
	}

	public void setSupportCurrencies(String[] supportCurrencies) {
		this.supportCurrencies = supportCurrencies;
	}

	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public String[] getSupProvider() {
		return supProvider;
	}

	public void setSupProvider(String[] supProvider) {
		this.supProvider = supProvider;
	}

	public InterfaceHandlerType getHandlerType() {
		return handlerType;
	}

	public void setHandlerType(InterfaceHandlerType handlerType) {
		this.handlerType = handlerType;
	}
}
