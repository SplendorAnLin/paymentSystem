package com.yl.client.front.service.impl;

import javax.annotation.Resource;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.boss.api.interfaces.OemInterface;
import com.yl.client.front.common.PushMSGUtils;
import com.yl.client.front.enums.MessageType;
import com.yl.client.front.enums.Status;
import com.yl.client.front.model.Message;
import com.yl.client.front.model.NoticeConfig;
import com.yl.client.front.service.NoticeConfigService;
import com.yl.client.front.service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yl.client.front.dao.MessageDao;
import com.yl.client.front.model.Notice;
import com.yl.client.front.service.MessageService;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Service("messageService")
public class MessageServiceImpl implements MessageService{
	private static Properties prop = new Properties();
	static {
		try {
			prop.load(MessageServiceImpl.class.getClassLoader().getResourceAsStream("serverHost.properties"));
		} catch (IOException e) {}
	}
	@Resource
	MessageDao messageDao;
	@Resource
	OemInterface oemInterface;
	@Resource
	UserService userService;
	@Resource
	NoticeConfigService noticeConfigService;
	protected static final Logger log = LoggerFactory.getLogger(PushMSGUtils.class);

	/**
	 * 获取相应OEM配置
	 * @param userName
	 * @return
	 */
	public JSONArray getConfig(String userName){
		String agentNo=oemInterface.findTopAgentNo(userName);
		JSONArray config=new JSONArray();
		if (agentNo!=null){
			//查询oem通道配置
			List<NoticeConfig> noticeConfigs=noticeConfigService.findNoticeConfigByAgentNo(agentNo);
			if(noticeConfigs!=null&&noticeConfigs.size()>0) {
				for (NoticeConfig temp : noticeConfigs) {
					config.add(JSONObject.fromObject(temp));
				}
			}else {
				config=getMainConfig();
			}
		}else {
			config=getMainConfig();
		}
		return config;
	}

	/**
	 * 获取主签名
	 * @return
	 */
	public JSONArray getMainConfig(){
		JSONArray config=new JSONArray();
		NoticeConfig noticeConfig=noticeConfigService.findNoticeConfigByOem("ylzf");
		if (noticeConfig!=null){
			config.add(JSONObject.fromObject(noticeConfig));
		}
		noticeConfig=noticeConfigService.findNoticeConfigByOem("ylzfStore");
		if (noticeConfig!=null){
			config.add(JSONObject.fromObject(noticeConfig));
		}
		return config;
	}

	public JSONArray getAllConfig(){
		JSONArray config=new JSONArray();
		List<NoticeConfig> noticeConfigs=noticeConfigService.findNoticeConfigs();
		if(noticeConfigs!=null&&noticeConfigs.size()>0) {
			for (NoticeConfig temp : noticeConfigs) {
				config.add(JSONObject.fromObject(temp));
			}
		}
		return config;
	}

	/**
	 * 单用户发送消息
	 * @param userName 用户
	 * @param msg 消息主题
	 */
	public void pushMSG(String userName, Message msg){
		int badge=1;
		if (MessageType.PAY_ORDER==msg.getType()){
			badge=0;
		}
		JSONArray configs=getConfig(userName);
		Notice notice=new Notice();
		notice.setContent(JsonUtils.toJsonString(msg));
		notice.setToForm(userName);
		notice.setCreateTime(new Date());
		insertMessage(notice);//插入消息到数据库
		for (Object config:configs) {
			JSONObject temp=JSONObject.fromObject(config).getJSONObject("config");
			String appKey=temp.getString("key");
			String masterSecret=temp.getString("secret");
			String titleTop=temp.getString("title");
			try {
				PushResult res=push("G_"+userName, msg,badge,appKey,masterSecret,titleTop);
				log.info("消息推送记录,{}",res);
			}catch (Exception e){
				userService.updateStauts("G_"+userName);
				log.error("消息推送错误",e.getMessage());
			}

		}
	}
	public void pushAllMSG(Message msg){
		int badge=1;
		JSONArray configs=getAllConfig();
		Notice notice=new Notice();
		notice.setContent(JsonUtils.toJsonString(msg));
		notice.setToForm("ALL");
		notice.setCreateTime(new Date());
		insertMessage(notice);//插入消息到数据库
		for (Object config:configs) {
			JSONObject temp=JSONObject.fromObject(config).getJSONObject("config");
			String appKey=temp.getString("key");
			String masterSecret=temp.getString("secret");
			String titleTop=temp.getString("title");
			try {
				PushResult res=buildPushObject_all_all_alert(JSONObject.fromObject(msg),masterSecret,appKey);
				log.info("消息推送记录,{}",res);
			}catch (Exception e){
				log.error("消息推送错误",e.getMessage());
			}

		}
	}


	/**
	 * 推送设备
	 * @param userName 用户ID
	 * @param msg	消息主体
	 * @param badge 消息标志 1|0
	 * @param masterSecret 通道密钥
	 * @param appKey 通道key
	 * @param titleTop 标题头部
	 * @return 发送结果
	 * @throws APIConnectionException
	 * @throws APIRequestException
	 */
	public PushResult push(String userName, Message msg, int badge,String appKey,String masterSecret,String titleTop) throws APIConnectionException, APIRequestException{
		JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, ClientConfig.getInstance());
		PushPayload payload;
		if (msg.getVoice()!=null) {
			payload = buildPushObject_all_alias_alert(userName,JSONObject.fromObject(msg),msg.getVoice(),badge,titleTop);
		}else {
			payload = buildPushObject_all_alias_alert(userName,JSONObject.fromObject(msg),badge,titleTop);
		}
		return jpushClient.sendPush(payload);
	}
	/**
	 *
	 * @param alias 推送设备
	 * @param strAlert 数据内容
	 * @return
	 */
		public PushPayload buildPushObject_all_alias_alert(String alias,JSONObject strAlert,int badge,String titleTop) {
		return buildPushObject_all_alias_alert(alias, strAlert, "",badge,titleTop);
	}
	/**
	 *
	 * @param alias 推送设备
	 * @param strAlert 数据内容
	 * @param title 下拉小标题提示
	 * @return
	 */
	public PushPayload buildPushObject_all_alias_alert(String alias,JSONObject strAlert,String title,int badge,String titleTop) {
		Notification.Builder info=Notification.newBuilder();
		if (title!=null) {
			info.setAlert(title);
		}
		info.addPlatformNotification(AndroidNotification.newBuilder()
				.setTitle(titleTop)
				.addExtra("data", strAlert.toString()).build())
				.addPlatformNotification(IosNotification.newBuilder()
						.incrBadge(badge)
						.setSound("coin_sound.wav").setContentAvailable(true)
						.addExtra("data", strAlert.toString()).build());
		if ("true".equals(prop.get("jiguang.test"))) {
			return PushPayload.newBuilder().setPlatform(Platform.all())
					.setAudience(Audience.alias(alias))
					.setNotification(info.build())
					.setOptions(Options.newBuilder().setApnsProduction(false).build())
					.build();
		}else {
			return PushPayload.newBuilder().setPlatform(Platform.all())
					.setAudience(Audience.alias(alias))
					.setNotification(info.build())
					.setOptions(Options.newBuilder().setApnsProduction(true).build())
					.build();
		}
	}
	/**
	 * 推送全平台所有设备
	 * @param strAlert
	 * @return
	 */
	public PushResult buildPushObject_all_all_alert(JSONObject strAlert, String masterSecret, String appKey) throws APIConnectionException, APIRequestException {
		JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, ClientConfig.getInstance());
		PushPayload payload=PushPayload.alertAll(strAlert.toString());
		if ("true".equals(prop.get("jiguang.test"))) {
			payload.resetOptionsApnsProduction(false);
		}else {
			payload.resetOptionsApnsProduction(true);
		}
		return jpushClient.sendPush(payload);
	}
	/**
	 * 按消息类别推送
	 * @param alias 设备别名
	 * @param strAlert 消息内容
	 * @param msgType 消息类型
	 * @param msgId 消息ID
	 * @return
	 */
	public PushPayload buildPushObject_all_alias_alert(String alias,String strAlert, String msgType,String msgId,String titleTop) {
		return PushPayload.newBuilder()
				.setPlatform(Platform.android())
				.setAudience(Audience.alias(alias))
				.setNotification(Notification.newBuilder()
						.setAlert(strAlert)
						.addPlatformNotification(AndroidNotification.newBuilder()
								.setTitle(titleTop)
								.addExtra("msgId", msgId)
								.addExtra("msgType", msgType).build())
						.addPlatformNotification(IosNotification.newBuilder()
								.incrBadge(1)
								.setSound("happy")
								.addExtra("msgId", msgId).addExtra("msgType", msgType).build())
						.build())
				.setOptions(Options.newBuilder()
						.setApnsProduction(true)
						.build())
				.build();
	}

	public void insertMessage(Notice notice){
		messageDao.createNotice(notice);
	}
	
}
