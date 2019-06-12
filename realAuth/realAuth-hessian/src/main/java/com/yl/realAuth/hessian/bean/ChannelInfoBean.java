package com.yl.realAuth.hessian.bean;

import java.io.Serializable;
import java.util.Properties;

import com.yl.realAuth.enums.ChannelStatus;
import com.yl.realAuth.enums.TrueFalse;
import com.yl.realAuth.hessian.enums.AuthBusiType;

public class ChannelInfoBean implements Serializable {

	private static final long serialVersionUID = -7910438111540196963L;

	private String code;
	/** 接口编号 */
	private String interfaceInfoCode;
	/** 渠道名称 */
	private String channelName;
	/** 渠道状态 */
	private ChannelStatus status;
	/** 交易配置 */
	private Properties tradeConfigs;
	/** 是否支持姓名 */
	private TrueFalse supportName;
	/** 姓名是否必须 */
	private TrueFalse mustName;
	/** 是否支持手机 */
	private TrueFalse supportMobNo;
	/** 手机是否必须 */
	private TrueFalse mustMobNo;
	/** 是否支持身份证号 */
	private TrueFalse supportCertNo;
	/** 身份证号是否必须 */
	private TrueFalse mustCertNo;

	private CardTypeInfo[] supportCardTypeInfos;
	/** 渠道类型 */
	private AuthBusiType channelType;

	public String getInterfaceInfoCode() {
		return interfaceInfoCode;
	}

	public void setInterfaceInfoCode(String interfaceInfoCode) {
		this.interfaceInfoCode = interfaceInfoCode;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public ChannelStatus getStatus() {
		return status;
	}

	public void setStatus(ChannelStatus status) {
		this.status = status;
	}

	public Properties getTradeConfigs() {
		return tradeConfigs;
	}

	public void setTradeConfigs(Properties tradeConfigs) {
		this.tradeConfigs = tradeConfigs;
	}

	public TrueFalse getSupportName() {
		return supportName;
	}

	public void setSupportName(TrueFalse supportName) {
		this.supportName = supportName;
	}

	public TrueFalse getMustName() {
		return mustName;
	}

	public void setMustName(TrueFalse mustName) {
		this.mustName = mustName;
	}

	public TrueFalse getSupportMobNo() {
		return supportMobNo;
	}

	public void setSupportMobNo(TrueFalse supportMobNo) {
		this.supportMobNo = supportMobNo;
	}

	public TrueFalse getMustMobNo() {
		return mustMobNo;
	}

	public void setMustMobNo(TrueFalse mustMobNo) {
		this.mustMobNo = mustMobNo;
	}

	public TrueFalse getSupportCertNo() {
		return supportCertNo;
	}

	public void setSupportCertNo(TrueFalse supportCertNo) {
		this.supportCertNo = supportCertNo;
	}

	public TrueFalse getMustCertNo() {
		return mustCertNo;
	}

	public void setMustCertNo(TrueFalse mustCertNo) {
		this.mustCertNo = mustCertNo;
	}

	public CardTypeInfo[] getSupportCardTypeInfos() {
		return supportCardTypeInfos;
	}

	public void setSupportCardTypeInfos(CardTypeInfo[] supportCardTypeInfos) {
		this.supportCardTypeInfos = supportCardTypeInfos;
	}

	public AuthBusiType getChannelType() {
		return channelType;
	}

	public void setChannelType(AuthBusiType channelType) {
		this.channelType = channelType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
