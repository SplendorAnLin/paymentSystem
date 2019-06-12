package com.yl.dpay.core.listener;

import java.text.SimpleDateFormat;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yl.dpay.core.entity.AutoRequest;
import com.yl.dpay.core.enums.OwnerRole;
import com.yl.dpay.core.remit.handle.impl.RequestRemitHandleImpl;
import com.yl.dpay.core.service.AutoRequestService;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.mq.consumer.client.ConsumerMessage;
import com.yl.mq.consumer.client.listener.ConsumerMessageListener;

/**
 * 自动代付请求监听
 *
 * @author AnLin
 * @since 2017年4月24日
 */
@Service("autoRequestListener")
public class AutoRequestListener implements ConsumerMessageListener{
	
	private static Logger logger = LoggerFactory.getLogger(RequestRemitHandleImpl.class);
	
	@Resource
	private AutoRequestService autoRequestService;

	@Override
	public void consumerListener(ConsumerMessage consumerMessage) {
		if(consumerMessage.getBody() == null || "".equals(consumerMessage.getBody())){
			return;
		}
		Map<String, String> params = null;
		AutoRequest autoRequest = new AutoRequest();
		try {
			params = JsonUtils.toObject(consumerMessage.getBody(), new TypeReference<Map<String, String>>(){});
			autoRequest.setAccountDate(new SimpleDateFormat("yyyyMMddhhmmss").parse(params.get("accountDate")));
			autoRequest.setOrderCode(params.get("orderCode"));
			autoRequest.setOwnerId(params.get("ownerId"));
			autoRequest.setOwnerRole(OwnerRole.valueOf(params.get("ownerRole")));
			autoRequest.setAmount(Double.valueOf(params.get("amount")));
			autoRequest.setOperator(params.get("operator"));
		} catch (Exception e1) {
			logger.error("autoRequestListener messageId:[{}], handle error:[{}]", consumerMessage.getMsgId(), e1);
			throw new RuntimeException(e1);
		}
		
		logger.info("autoRequestListener autoRequest : {}", autoRequest);
		try {
			autoRequestService.handle(autoRequest);
		} catch (Exception e) {
			logger.error("autoRequestListener orderCode:[{}], handle error:[{}]", params.get("orderCode"), e);
		}
	}
	
}
