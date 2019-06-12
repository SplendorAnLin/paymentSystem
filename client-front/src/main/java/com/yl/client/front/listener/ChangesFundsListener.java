package com.yl.client.front.listener;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.client.front.common.ConverMap;
import com.yl.client.front.common.DictUtils;
import com.yl.client.front.common.PushMSGUtils;
import com.yl.client.front.enums.MessageType;
import com.yl.client.front.model.Message;
import com.yl.client.front.model.Notice;
import com.yl.client.front.service.MessageService;
import com.yl.client.front.service.UserService;
import com.yl.mq.consumer.client.ConsumerMessage;
import com.yl.mq.consumer.client.listener.ConsumerMessageListener;
import net.sf.json.JSONObject;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 资金变动监听
 *
 * @author AnLin
 * @since 2017年4月24日
 */
@Service("changesFundsListener")
public class ChangesFundsListener implements ConsumerMessageListener{
	
	private static Logger logger = LoggerFactory.getLogger(ChangesFundsListener.class);
	
	@Resource
	MessageService messageService;
	@Resource
	UserService userService;

	@Override
	public void consumerListener(ConsumerMessage consumerMessage) {
		if(consumerMessage.getBody() == null || "".equals(consumerMessage.getBody())){
			return;
		}
		if ("CHANGES_FUNDS".equals(consumerMessage.getTags())) {
			Map<String, Object> params = null;
			try {
				params = JsonUtils.toObject(consumerMessage.getBody(), new TypeReference<Map<String, Object>>(){});
			} catch (Exception e1) {
				logger.error("MQ消息ID:[{}],错误信息:[{}]", consumerMessage.getMsgId(), e1);
				throw new RuntimeException(e1);
			}	
			String pushId=params.get("userNo").toString();
			if (userService.findUserStauts("G_"+pushId)) {
					String type=params.get("type").toString();
					params=JsonUtils.toObject(JsonUtils.toJsonString(params.get("info")), new TypeReference<Map<String, Object>>(){});
					params.put("type", type);
					if ("C".equals(pushId.substring(0,1))) {
						String voice = null;
						params=DictUtils.mapOfDict(params,"BUSINESS_TYPE","type");
						if ("PLUS".equals(params.get("symbol"))) {
							voice="您有一笔入账，金额为:"+params.get("amount").toString()+"元";
							if (params.get("fee")!=null&&Double.valueOf(params.get("fee").toString())>0) {
								Double fee=Double.valueOf(params.get("fee").toString());
								voice+=",手续费为:"+fee.toString()+"元";
							}
						}
						if ("SUBTRACT".equals(params.get("symbol"))) {
							voice="您有一笔出账，金额为:"+params.get("amount")+"元";
							if (params.get("fee")!=null&&Double.valueOf(params.get("fee").toString())>0) {
								Double fee=Double.valueOf(params.get("fee").toString());
								voice+=",手续费为:"+fee.toString()+"元";
							}
						}
						params=ConverMap.convertMapDateToStr(params);
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Message msg=new Message();
						msg.setType(MessageType.CHANGES_FUNDS);
						if ("PLUS".equals(params.get("symbol"))) {
							msg.setIcon("http://pay.feiyijj.com/files/icon/income.png");
						}
						if ("SUBTRACT".equals(params.get("symbol"))) {
							msg.setIcon("http://pay.feiyijj.com/files/icon/pay.png");
						}
						msg.setCustomerNo(pushId);
						msg.setTitle(params.get("type_CN").toString());
						msg.setContent(JSONObject.fromObject(params));
						msg.setCreateDate(formatter.format(new Date()));
						msg.setVoice(voice);
						logger.info("发送资金变动记录消息:{}",JsonUtils.toJsonString(msg));
						messageService.pushMSG(pushId, msg);
					}
			}
		}
		
	}
	
}
