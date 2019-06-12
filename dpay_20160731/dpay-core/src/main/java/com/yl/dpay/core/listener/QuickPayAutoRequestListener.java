package com.yl.dpay.core.listener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.boss.api.bean.TransCardBean;
import com.yl.dpay.core.entity.QuickPayAutoRequest;
import com.yl.dpay.core.enums.AccountType;
import com.yl.dpay.core.enums.OwnerRole;
import com.yl.dpay.core.enums.QuickPayRemitType;
import com.yl.dpay.core.remit.handle.impl.RequestRemitHandleImpl;
import com.yl.dpay.core.service.QuickPayAutoRequestService;
import com.yl.mq.consumer.client.ConsumerMessage;
import com.yl.mq.consumer.client.listener.ConsumerMessageListener;

/**
 * 快捷支付完成后自动代付监听
 *
 * @author Shark
 * @since 2017年9月9日
 */
@Service("quickPayAutoRequestListener")
public class QuickPayAutoRequestListener implements ConsumerMessageListener{
	
	private static Logger logger = LoggerFactory.getLogger(RequestRemitHandleImpl.class);
	
	@Resource
	private QuickPayAutoRequestService quickPayAutoRequestService;

	@Override
	public void consumerListener(ConsumerMessage consumerMessage) {
		if(consumerMessage.getBody() == null || "".equals(consumerMessage.getBody())){
			return;
		}
		Map<String, String> params = null;
		QuickPayAutoRequest autoRequest = new QuickPayAutoRequest();
		try {
			params = JsonUtils.toObject(consumerMessage.getBody(), new TypeReference<Map<String, String>>(){});
			autoRequest.setAccountDate(new Date());
			autoRequest.setOrderCode(params.get("orderCode"));
			autoRequest.setOwnerId(params.get("ownerId"));
			autoRequest.setOwnerRole(OwnerRole.valueOf(params.get("ownerRole")));
			autoRequest.setRemitAmount(Double.valueOf(params.get("dapyAmount")));
			autoRequest.setRemitFee(Double.valueOf(params.get("dapyFee")));
			autoRequest.setOperator(params.get("operator"));
			autoRequest.setAccountName(params.get("accountName"));
			autoRequest.setAccountNo(params.get("accountNo"));
			autoRequest.setAccountType(AccountType.valueOf(params.get("accountType")));
			autoRequest.setBankCode(params.get("bankCode"));
			autoRequest.setBankName(params.get("openBankName"));
			autoRequest.setFreezeNo(params.get("freezeNo"));
			autoRequest.setApplyDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(params.get("applyDate")));
			autoRequest.setRemitInterfaceCode(params.get("remitInterfaceInfoCode"));
			autoRequest.setQuickPayRemitType(QuickPayRemitType.valueOf(params.get("quickPayRemitType")));
		} catch (Exception e1) {
			logger.error("QuickPay autoRequestListener messageId:[{}], handle error:[{}]", consumerMessage.getMsgId(), e1);
			throw new RuntimeException(e1);
		}
		
		logger.info("QuickPay autoRequestListener autoRequest : {}", autoRequest);
		try {
			quickPayAutoRequestService.handler(autoRequest);
		} catch (Exception e) {
			logger.error("QuickPay autoRequestListener orderCode:[{}], handle error:[{}]", params.get("orderCode"), e);
		}
	}
	
}
