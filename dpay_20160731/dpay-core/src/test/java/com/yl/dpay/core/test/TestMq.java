package com.yl.dpay.core.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.dpay.core.entity.QuickPayAutoRequest;
import com.yl.dpay.core.enums.AccountType;
import com.yl.dpay.core.enums.OwnerRole;
import com.yl.dpay.core.enums.QuickPayRemitType;
import com.yl.dpay.core.service.QuickPayAutoRequestService;
import com.yl.dpay.core.service.impl.QuickPayAutoRequestServiceImpl;

public class TestMq extends BaseTest{
	private static Logger logger = LoggerFactory.getLogger(QuickPayAutoRequestServiceImpl.class);
	
	@Resource
	public QuickPayAutoRequestService quickPayAutoRequestService;

	@Test
	public void aa() {// TD-20171104-101525366616 TD-20171104-101050579512
		//TD-20171103-102020853419

		String cc = "{\"orderCode\":\"TD-20171104-101050579512\",\"ownerId\":\"C100000\",\"ownerRole\":\"CUSTOMER\",\"interfaceInfoCode\":\"QUICKPAY_ZhongMao-100001\",\"accountNo\":\"6217230200003271469\",\"quickPayRemitType\":\"ORDER_PAY\",\"openBankName\":\"中国工商银行\",\"operator\":\"SYSTEM\",\"applyDate\":\"2017-11-04 12:07:09\",\"bankCode\":\"ICBC\",\"dapyAmount\":\"9.94\",\"accountName\":\"孙勃洋\",\"freezeNo\":\"1c1-260caf9896d5\",\"accountType\":\"INDIVIDUAL\",\"dapyFee\":\"2.0\",\"remitInterfaceInfoCode\":\"ZhongMao_100003_REMIT\"}";
		Map<String, String> params = null;
		QuickPayAutoRequest autoRequest = new QuickPayAutoRequest();
		try {
			params = JsonUtils.toObject(cc, new TypeReference<Map<String, String>>(){});
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
			logger.error("QuickPay autoRequestListener messageId:[{}], handle error:[{}]", "12", e1);
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
