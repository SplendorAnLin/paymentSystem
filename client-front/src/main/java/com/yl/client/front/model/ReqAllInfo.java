package com.yl.client.front.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 请求全部信息
 * 
 * @author 聚合支付有限公司
 * @since 2017年4月28日
 * @version V1.0.0
 */
public class ReqAllInfo implements Serializable{

	public ReqAllInfo(ReqAllInfo reqAllInfo){
		this.reqCode=reqAllInfo.getReqCode();
		this.reqSys=reqAllInfo.getReqSys();
		this.reqType=reqAllInfo.getReqType();
		this.reqAddress=reqAllInfo.getReqAddress();
		this.reqIp=reqAllInfo.getReqIp();
		this.clientInfo=reqAllInfo.getClientInfo();
		this.reqInfo=reqAllInfo.getReqInfo();
		this.info=reqAllInfo.getInfo();
		this.reqDate=reqAllInfo.getReqDate();
	}

	public ReqAllInfo(){}

	public ReqAllInfo(String reqCode,String reqSys,String reqType,String reqAddress,String reqIp,ClientInfo clientInfo,Object reqInfo,String info,Date reqDate){
		this.reqCode=reqCode;
		this.reqSys=reqSys;
		this.reqType=reqType;
		this.reqAddress=reqAddress;
		this.reqIp=reqIp;
		this.clientInfo=clientInfo;
		this.reqInfo=reqInfo;
		this.info=info;
		this.reqDate=reqDate;
	}

	private static final long serialVersionUID = 3921586093972698119L;
	private String reqCode;//请求编号
	private String reqSys;//请求系统
	private String reqType;//请求类型
	private String reqAddress;//请求地址
	private String reqIp;//请求者IP
	private ClientInfo clientInfo;//请求所带信息
	private Object reqInfo;//请求信息
	private String info;//原始请求信息
	private Date reqDate;//请求时间
	
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getReqAddress() {
		return reqAddress;
	}
	public String getReqIp() {
		return reqIp;
	}
	public void setReqAddress(String reqAddress) {
		this.reqAddress = reqAddress;
	}
	public void setReqIp(String reqIp) {
		this.reqIp = reqIp;
	}
	public Date getReqDate() {
		return reqDate;
	}
	public void setReqDate(Date reqDate) {
		this.reqDate = reqDate;
	}
	public String getReqSys() {
		return reqSys;
	}
	public String getReqType() {
		return reqType;
	}
	public void setReqSys(String reqSys) {
		this.reqSys = reqSys;
	}
	public void setReqType(String reqType) {
		this.reqType = reqType;
	}
	public ClientInfo getClientInfo() {
		return clientInfo;
	}
	public void setClientInfo(ClientInfo clientInfo) {
		this.clientInfo = clientInfo;
	}
	public Object getReqInfo() {
		return reqInfo;
	}
	public void setReqInfo(Object reqInfo) {
		this.reqInfo = reqInfo;
	}
	public String getReqCode() {
		return reqCode;
	}
	public void setReqCode(String reqCode) {
		this.reqCode = reqCode;
	}
	
}