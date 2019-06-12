package com.yl.boss.action;


import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.chat.interfaces.WechatInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;



/**
 * 微信控制器
 * 
 * @author 聚合支付有限公司
 * @since 2017年5月25日
 * @version V1.0.0
 */
public class WeChatAction extends Struts2ActionSupport{
	private static final long serialVersionUID = 1L;
	private String msg;
	private String data;
	 private  WechatInterface wechatInterface;
	 private static final Logger logger = LoggerFactory.getLogger(WeChatAction.class);
	
	public String wxMenu(){
		try {
			Map<String, Object> info=wechatInterface.findMenu();
			msg=JsonUtils.toJsonString(info).replace(",\"sub_button\":[]", "");
		} catch (Exception e) {
			logger.error("微信菜单读取失败{}",e);
		}
		return "success";
    }
	
	public  String createMenu(){
		try {
			if (wechatInterface.createMenu(data)==0) {
				msg="TRUE";
			}else {
				msg="FALSE";
			}
		} catch (Exception e) {
			msg="FALSE";
			logger.error("微信菜单提交修改失败");
		}
		return "success";
	}
	

	public WechatInterface getWechatInterface() {
		return wechatInterface;
	}

	public void setWechatInterface(WechatInterface wechatInterface) {
		this.wechatInterface = wechatInterface;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
  }
