package com.lefu.online.gateway.service.impl;

import java.util.Set;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

import org.junit.Test;

import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.online.gateway.BaseTest;
import com.yl.online.gateway.Constants;
import com.yl.online.gateway.ExceptionMessages;
import com.yl.online.gateway.exception.BusinessException;
import com.yl.online.gateway.service.impl.SalesTradeHandler;
import com.yl.online.model.bean.SalesTradeRequest;
import com.yl.online.model.model.PartnerRequest;

/**
 * 网关交易测试
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月11日
 * @version V1.0.0
 */
public class SalesTradeHandlerTest extends BaseTest {

	@Resource
	private SalesTradeHandler salesTradeHandler;
	
	@Resource
	private Validator validator;
	
	@Test
	public void testExecute() throws BusinessException {
		Long l = new Long(2);
		PartnerRequest partnerRequest = new PartnerRequest();
		partnerRequest.setApiCode("YL-PAY");
		partnerRequest.setVersion(l);
		partnerRequest.setInputCharset("UTF-8");
		partnerRequest.setSignType("MD5");
		partnerRequest.setSign("328974378934789djhsadjkdfa");
		partnerRequest.setPartner("845673647");
		partnerRequest.setOutOrderId("3894357397");
		partnerRequest.setAmount("100");
		
		SalesTradeRequest partnerRequestInfo = new SalesTradeRequest();
		partnerRequestInfo.setBuyer("2323");
		// TODO
		partnerRequestInfo.setBuyerContactType("email");
		partnerRequestInfo.setBuyerContact("sfasf");
		partnerRequestInfo.setPaymentType("sales");
		// TODO
		partnerRequestInfo.setRetryFalg("TRUE");
		partnerRequestInfo.setSubmitTime("20131201");
		// TODO
		partnerRequestInfo.setTimeout("2DM");
		partnerRequestInfo.setRedirectURL("http://www.baidu.com");
		partnerRequestInfo.setNotifyURL("http://www.baidu.com");
		partnerRequestInfo.setProductURL("http://www.baidu.com");
		
		Set<ConstraintViolation<PartnerRequest>> violations = validator.validate(partnerRequest);
		if (violations.size() > 0) {
			String originalMsg = violations.iterator().next().getPropertyPath().toString();
			String errorMsg = originalMsg.substring(originalMsg.lastIndexOf(".") + 1, originalMsg.length());
			if (violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(NotNull.class))
				throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", errorMsg).toString());
			else 
				throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_ERROR, "-", errorMsg).toString());
		}
		// 检查页面支付结果通知和后台是否同时为空
		if (StringUtils.isBlank(Constants.PARAM_NAME_REDIRECTURL) && StringUtils.isBlank(Constants.PARAM_NAME_NOTIFYURL)) {
			throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", Constants.PARAM_NAME_REDIRECTURL, "-",
					Constants.PARAM_NAME_NOTIFYURL).toString());
		}
	}

}
