package com.yl.client.front.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yl.client.front.service.MessageService;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.client.front.common.PushMSGUtils;
import com.yl.client.front.enums.MessageType;
import com.yl.client.front.model.Message;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import net.sf.json.JSONObject;

@Controller
public class OrderCompleteController {
	private Logger logger = LoggerFactory.getLogger(OrderCompleteController.class);

	@Resource
	MessageService messageService;

	@RequestMapping("completeOrder")
	public void index(HttpServletRequest request, HttpServletResponse response){
		logger.info("*************** App收款通知START ****************");
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			logger.info("接收订单状态消息:{}",sb.toString());
			Map<String,String> resMap = JsonUtils.toObject(sb.toString(), new TypeReference<Map<String,String>>() {});
			String pushId=resMap.get("partner");
			if (resMap.get("returnParam")==null){
				resMap.remove("returnParam");
			}
			Message msg=new Message();
			msg.setType(MessageType.PAY_ORDER);
			msg.setContent(JSONObject.fromObject(resMap));
			msg.setCustomerNo(resMap.get("partner"));
			msg.setTitle("交易订单状态推送");
			messageService.pushMSG(pushId, msg);
			response.getWriter().write("SUCCESS");
			response.getWriter().close();
		} catch (Exception e) {
			logger.error("发送消息异常：{}",e);
			try {
				response.getWriter().write("SUCCESS");
				response.getWriter().close();
			} catch (Exception e2) {
				logger.error("订单处理失败：{}",e);
			}
		}
		logger.info("*************** App收款通知END ****************");
	}
}
