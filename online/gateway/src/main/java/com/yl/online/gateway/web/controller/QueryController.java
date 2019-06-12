package com.yl.online.gateway.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.type.TypeReference;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.security.DigestUtils;
import com.yl.boss.api.bean.Customer;
import com.yl.boss.api.bean.CustomerKey;
import com.yl.boss.api.enums.CustomerStatus;
import com.yl.boss.api.enums.KeyType;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.online.gateway.Constants;
import com.yl.online.gateway.ExceptionMessages;
import com.yl.online.gateway.bean.PartnerQueryResponse;
import com.yl.online.gateway.context.RequestProxy;
import com.yl.online.gateway.enums.MerchantResponseCode;
import com.yl.online.gateway.enums.SignType;
import com.yl.online.gateway.exception.BusinessException;
import com.yl.online.gateway.service.PartnerQueryRequestService;
import com.yl.online.gateway.service.QueryHandler;
import com.yl.online.model.model.PartnerQueryRequest;

/**
 * 合作方单笔订单查询接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月23日
 * @version V1.0.0
 */
@Controller
public class QueryController {
	private static final Logger logger = LoggerFactory.getLogger(QueryController.class);
	@Resource
	private Validator validator;
	@Resource
	private Map<String, QueryHandler> queryHandlers;
	@Resource
	private PartnerQueryRequestService PartnerQueryRequestService;
	@Resource
	private CustomerInterface customerInterface;
	
	@RequestMapping("/query")
	public void query(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter writer = null;
		PartnerQueryResponse partnerQueryResponse = null;
		String cipher = "";
		try {
			RequestProxy requestProxy = new RequestProxy(request);
			request = requestProxy;
			PartnerQueryRequest partnerQueryRequest = initialQueryRequest(request);
			cipher = validateQueryParams(partnerQueryRequest, request);
			QueryHandler queryHandler = queryHandlers.get(partnerQueryRequest.getQueryCode());
			partnerQueryResponse = queryHandler.query(partnerQueryRequest, cipher);
			writer = response.getWriter();
			writer.print(JsonUtils.toJsonString(partnerQueryResponse));
		} catch (Exception e) {
			logger.error("", e);
			try {
				if (partnerQueryResponse == null) partnerQueryResponse = new PartnerQueryResponse();
				this.transferredErrorMsg(partnerQueryResponse, e);
				if (writer == null) writer = response.getWriter();
				writer.print(URLEncoder.encode(JsonUtils.toJsonString(
						partnerQueryResponse), StringUtils.isBlank(request.getParameter(
								Constants.PARAM_NAME_INPUT_CHARSET)) ? "UTF-8" : request.getParameter(Constants.PARAM_NAME_INPUT_CHARSET)));
			} catch (IOException e1) {
				logger.error("", e1);
			}
		} finally {
			if (writer != null) writer.close();
		}
	}
	
	private String validateQueryParams(PartnerQueryRequest partnerQueryRequest, HttpServletRequest request) throws BusinessException {
		Map<String, String> params = new HashMap<>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			
			if(sb.toString().equals("")){
				Map<String, String[]> OriginalParams = JsonUtils.toObject(partnerQueryRequest.getOriginalRequest(), new TypeReference<Map<String, String[]>>() {});
				for (String key : OriginalParams.keySet()) {
					params.put(key, OriginalParams.get(key)[0]);
				}
			}
			
			if(sb.toString().equals("") && params.size() == 0){
				throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", Constants.PARAM_NAME_API_CODE).toString());
			}
			if(params.size() == 0){
				params = JsonUtils.toObject(sb.toString(), new TypeReference<Map<String, String>>(){});
			}
		} catch (IOException e) {
			logger.error("", e);
			throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", Constants.PARAM_NAME_API_CODE).toString());
		}
		// 查询标示符
		partnerQueryRequest.setQueryCode(params.get(Constants.PARAM_NAME_QUERY_CODE));
		// 字符集
		partnerQueryRequest.setInputCharset(params.get(Constants.PARAM_NAME_INPUT_CHARSET));
		// 合作方编码
		partnerQueryRequest.setPartner(params.get(Constants.PARAM_NAME_PARTNER));
		// 商户订单号
		partnerQueryRequest.setOutOrderId(params.get(Constants.PARAM_NAME_OUT_ORDER_ID));
		// 签名方式
		partnerQueryRequest.setSignType(params.get(Constants.PARAM_NAME_SIGN_TYPE));
		// 签名
		partnerQueryRequest.setSign(params.get(Constants.PARAM_NAME_SIGN));
		
		Set<ConstraintViolation<PartnerQueryRequest>> violations = validator.validate(partnerQueryRequest);
		if (violations.size() > 0) {
			ConstraintViolation<PartnerQueryRequest> violation = violations.iterator().next();
			Class<? extends Annotation> annoationType = violation.getConstraintDescriptor().getAnnotation().annotationType();
			if (annoationType.equals(NotNull.class) || annoationType.equals(NotBlank.class)) throw new BusinessException(ExceptionMessages.PARAM_NOT_EXISTS);
		}
		
		// 合作方编码
		final String partnerCode = partnerQueryRequest.getPartner();
		
		// 查询卖方信息
		Customer customer = customerInterface.getCustomer(partnerCode);
		
		if (customer == null) throw new BusinessException(ExceptionMessages.RECEIVER_NOT_EXISTS);

		if (customer.getStatus() == null || !customer.getStatus().equals(CustomerStatus.TRUE)) {
			throw new BusinessException(ExceptionMessages.RECEIVER_STATUS_ERROR);
		}
		
		CustomerKey customerKey = customerInterface.getCustomerKey(partnerCode, KeyType.MD5);
		
		// 开通相关业务
//		if (!partner.getInterfaceType().contains("ONLINE")) throw new BusinessException(ExceptionMessages.RECEIVER_NOT_OPEN_ONLINE_SERVICE);
		
		// 按参数名排序
		ArrayList<String> paramNames = new ArrayList<>(params.keySet());
		Collections.sort(paramNames);
		// 组织待签名信息
		StringBuilder signSource = new StringBuilder();
		Iterator<String> iterator = paramNames.iterator();
		while (iterator.hasNext()) {
			String paramName = iterator.next();
			if (!Constants.PARAM_NAME_SIGN.equals(paramName) && StringUtils.notBlank(params.get(paramName))) {
				signSource.append(paramName).append("=").append(params.get(paramName));
				if (iterator.hasNext()) signSource.append("&");
			}
		}

		// 计算签名
		String calSign = null;
		if (SignType.MD5.name().equals(partnerQueryRequest.getSignType())) {
			signSource.append(customerKey.getKey());
			try {
				logger.info("current inputCharset is {}, signSource is {}", partnerQueryRequest.getInputCharset(), signSource);
				calSign = DigestUtils.md5DigestAsHex(signSource.toString().getBytes(partnerQueryRequest.getInputCharset()));
			} catch (UnsupportedEncodingException e) {
				throw new BusinessException(ExceptionMessages.PARAM_ERROR);
			}
		}

		if (!partnerQueryRequest.getSign().equals(calSign)) {
			logger.info("sign check error\r\nseller={}\r\nsign={}\r\ncalSign={}", partnerCode, partnerQueryRequest.getSign(), calSign);
			throw new BusinessException(ExceptionMessages.SIGN_ERROR);
		}
		// 记录合作方查询请求信息
		PartnerQueryRequestService.save(partnerQueryRequest);
		return customerKey.getKey();
	}

	/**
	 * 错误码转义
	 * @param partnerQueryResponse 响应合作方查询信息
	 * @param e 异常实体
	 */
	private void transferredErrorMsg(PartnerQueryResponse partnerQueryResponse, Exception e) {
		String errorMsg = "";
		MerchantResponseCode merchantResponseCode = MerchantResponseCode.UNKOWN_ERROR;
		if (e.getMessage() != null) {
			if (e.getClass().isAssignableFrom(RuntimeException.class)) errorMsg = e.getMessage().substring(e.getMessage().lastIndexOf(":") + 2, e.getMessage().length());
			else if (e.getClass().isAssignableFrom(BusinessException.class)) errorMsg = e.getMessage();
			merchantResponseCode = MerchantResponseCode.getMerchantCode(errorMsg);
		}
		partnerQueryResponse.setResponseCode(merchantResponseCode.getMerchantCode());
		partnerQueryResponse.setResponseMsg(merchantResponseCode.getResponseMessage());
	}

	@SuppressWarnings("deprecation")
	private PartnerQueryRequest initialQueryRequest(HttpServletRequest request) {
		Map<String, String[]> originalRequest = request.getParameterMap();
		originalRequest = JsonUtils.toObject(URLDecoder.decode(JsonUtils.toJsonString(originalRequest)), new TypeReference<Map<String, String[]>>() {});
		PartnerQueryRequest partnerQueryRequest = new PartnerQueryRequest();
		// 记录原始请求信息
		partnerQueryRequest.setOriginalRequest(JsonUtils.toJsonString(originalRequest));
		return partnerQueryRequest;
	}
}
