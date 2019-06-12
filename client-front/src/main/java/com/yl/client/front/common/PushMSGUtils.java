package com.yl.client.front.common;

import java.io.IOException;
import java.util.Properties;

import com.yl.client.front.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import cn.jpush.api.push.model.notification.Notification.Builder;
import net.sf.json.JSONObject;

public class PushMSGUtils {
	protected static final Logger LOG = LoggerFactory.getLogger(PushMSGUtils.class);

	private static Properties prop = new Properties();
	static {
		try {
			prop.load(PushMSGUtils.class.getClassLoader().getResourceAsStream("serverHost.properties"));
		} catch (IOException e) {}
	}
	
	private static final String appKey =prop.getProperty("jiguang.key");
	private static final String masterSecret = prop.getProperty("jiguang.masterSecret");
	
	private static final String TITLE= prop.getProperty("jiguang.title");

	public static PushResult push(String userName,Message msg,int badge) throws APIConnectionException, APIRequestException{
		JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, ClientConfig.getInstance());
		PushPayload payload;
		if (msg.getVoice()!=null) {
			payload = buildPushObject_all_alias_alert(userName,JSONObject.fromObject(msg),msg.getVoice(),badge);
		}else {
			payload = buildPushObject_all_alias_alert(userName,JSONObject.fromObject(msg),badge);
		} 
        return jpushClient.sendPush(payload);
	}
    /**
     * 推送全平台所有设备
     * @param strAlert
     * @return
     */
	public static PushPayload buildPushObject_all_all_alert(String strAlert) {
        return PushPayload.alertAll(strAlert);
    }
	
	 /**
     * 
     * @param alias 推送设备
     * @param strAlert 数据内容
     * @return
     */
    public static PushPayload buildPushObject_all_alias_alert(String alias,JSONObject strAlert,int badge) {
        return buildPushObject_all_alias_alert(alias, strAlert, "",badge);
    }
    /**
     * 
     * @param alias 推送设备
     * @param strAlert 数据内容
     * @param title 下拉小标题提示
     * @return
     */
    public static PushPayload buildPushObject_all_alias_alert(String alias,JSONObject strAlert,String title,int badge) {
    	Builder info=Notification.newBuilder();
    	if (title!=null) {
    		info.setAlert(title);
		}
    	info.addPlatformNotification(AndroidNotification.newBuilder()
        		.setTitle(TITLE)
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
     * 按消息类别推送
     * @param alias 设备别名
     * @param strAlert 消息内容
     * @param msgType 消息类型
     * @param msgId 消息ID
     * @return
     */
    public static PushPayload buildPushObject_all_alias_alert(String alias,String strAlert, String msgType,String msgId) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.newBuilder()
				        		.setAlert(strAlert)
				        		.addPlatformNotification(AndroidNotification.newBuilder()
								.setTitle(TITLE)
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
    
    
}
