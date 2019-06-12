package com.yl.payinterface.core.handle.impl.wallet.yyg100001.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 报文请求头Bean类
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/7/19
 */
public class HeadReq {

	private DataReq dataReq;

	public DataReq getDataReq() {
		return dataReq;
	}

	public void setDataReq(DataReq dataReq) {
		this.dataReq = dataReq;
	}

	/**
	 * 交易码
	 */
	private String tranCode;
	/**
	 * 渠道版本号-1.0
	 */
	private String channelVersion;
	/**
	 * 接口版本号-1.0
	 */
	private String apiVersion;
	/**
	 * 渠道编号-99
	 */
	private String channelNo;
	/**
	 * 渠道日期
	 */
	private String channelDate;
	/**
	 * 渠道时间
	 */
	private String channelTime;
	/**
	 * 渠道流水号
	 */
	private String channelSerial;
	/**
	 * 子商户号
	 */
	private String userId;
	/**
	 * 设备指纹
	 */
	private String deviceFingerPrint;
	/**
	 * 登录Token
	 */
	private String loginToken;

	/**
	 * 报文请求头
	 * 
	 * @param tranCode
	 *            交易码
	 * @param userId
	 *            子商户号
	 */
	public HeadReq(String tranCode, String userId, String orderId) {
		this.setApiVersion("1.0");//接口版本号-固定传1.0
		this.setChannelDate(new SimpleDateFormat("yyyyMMdd").format(new Date()));//渠道日期
		this.setChannelNo("99");//渠道号-固定传99-渠道接入
		this.setChannelSerial(orderId);//渠道流水号-需唯一
		this.setChannelTime(new SimpleDateFormat("HHmmss").format(new Date()));//渠道时间
		this.setChannelVersion("1.0");//渠道版本号-固定传1.0
		this.setDeviceFingerPrint("");//设备指纹
		this.setLoginToken("");//登录token
		this.setTranCode(tranCode);//交易码
		this.setUserId(userId);//子商户号
	}

	public String getTranCode() {
		return tranCode;
	}

	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}

	public String getChannelVersion() {
		return channelVersion;
	}

	public void setChannelVersion(String channelVersion) {
		this.channelVersion = channelVersion;
	}

	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

	public String getChannelDate() {
		return channelDate;
	}

	public void setChannelDate(String channelDate) {
		this.channelDate = channelDate;
	}

	public String getChannelTime() {
		return channelTime;
	}

	public void setChannelTime(String channelTime) {
		this.channelTime = channelTime;
	}

	public String getChannelSerial() {
		return channelSerial;
	}

	public void setChannelSerial(String channelSerial) {
		this.channelSerial = channelSerial;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDeviceFingerPrint() {
		return deviceFingerPrint;
	}

	public void setDeviceFingerPrint(String deviceFingerPrint) {
		this.deviceFingerPrint = deviceFingerPrint;
	}

	public String getLoginToken() {
		return loginToken;
	}

	public void setLoginToken(String loginToken) {
		this.loginToken = loginToken;
	}
}