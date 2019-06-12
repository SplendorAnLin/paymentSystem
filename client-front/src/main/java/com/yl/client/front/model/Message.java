package com.yl.client.front.model;

import java.util.Date;

import com.yl.client.front.enums.MessageType;

import net.sf.json.JSONObject;

/**
 * 推送消息
 * @author 聚合支付有限公司
 * @since 2017年4月28日
 * @version V1.0.0
 */
public class Message extends BaseEntity {
    private String customerNo;      		//商户号
    private String phone;				//登陆号
    private String icon;					//消息图标
    private String title;      			//消息标题
    private JSONObject content;      		//内容
    private MessageType type;			//消息类型
    private String voice;				//语音
    private Date extTime;    			//失效时间
    private String systemCode;			//操作人或者系统
    private String msgTxt;				//备注
	private String createDate;
    
	public String getCustomerNo() {
		return customerNo;
	}
	public String getPhone() {
		return phone;
	}
	public String getIcon() {
		return icon;
	}
	public String getTitle() {
		return title;
	}
	public JSONObject getContent() {
		return content;
	}
	public MessageType getType() {
		return type;
	}
	public Date getExtTime() {
		return extTime;
	}
	public String getSystemCode() {
		return systemCode;
	}
	public String getMsgTxt() {
		return msgTxt;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setContent(JSONObject content) {
		this.content = content;
	}
	public void setType(MessageType type) {
		this.type = type;
	}
	public void setExtTime(Date extTime) {
		this.extTime = extTime;
	}
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
	public void setMsgTxt(String msgTxt) {
		this.msgTxt = msgTxt;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getVoice() {
		return voice;
	}
	public void setVoice(String voice) {
		this.voice = voice;
	}
    
}
