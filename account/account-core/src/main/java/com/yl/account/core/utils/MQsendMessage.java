package com.yl.account.core.utils;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.account.api.bean.request.AccountBalanceQuery;
import com.yl.account.api.bean.response.AccountBalanceQueryResponse;
import com.yl.account.api.dubbo.AccountQueryInterface;
import com.yl.account.bean.TradeVoucher;
import com.yl.account.core.C;
import com.yl.account.enums.MessageType;
import com.yl.mq.producer.client.ProducerClient;
import com.yl.mq.producer.client.ProducerMessage;

/**
 * 资金变化MQ消息发送
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月9日
 * @version V1.0.0
 */
@Component("mqSendMessage")
public class MQsendMessage {
	
	private static Logger logger = LoggerFactory.getLogger(MQsendMessage.class);
	
	@Resource
	private ProducerClient producerClient;
	@Resource
	private AccountQueryInterface accountQueryInterface;
	
	public  void send(TradeVoucher tradeVoucher,String userNo,String type){
		try {
			Map<String, Object> info=new HashMap<>();
			info.put("type", type);
			info.put("info", tradeVoucher);
			info.put("userNo", userNo);
			Map<String, Object> map = new HashMap<>();
			map.put("userNo", userNo);
			AccountBalanceQuery accountBalanceQuery=new AccountBalanceQuery();
			accountBalanceQuery.setUserNo(userNo);
			AccountBalanceQueryResponse accountBalanceQueryResponse=accountQueryInterface._findAccountBalance(accountBalanceQuery);
			info.put("balance", accountBalanceQueryResponse.getAvailavleBalance());
			producerClient.sendMessage(new ProducerMessage(C.NOTIFY_TOPIC,MessageType.CHANGES_FUNDS.toString(),JsonUtils.toJsonString(info).getBytes()));
		} catch (Exception e) {
			logger.error("{}", e);
		}
	}
}
