package com.yl.realAuth.front.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.security.DigestUtils;
import com.yl.realAuth.enums.AuthOrderStatus;
import com.yl.realAuth.enums.AuthResult;
import com.yl.realAuth.enums.SignType;
import com.yl.realAuth.front.Constants;
import com.yl.realAuth.front.ExceptionMessages;
import com.yl.realAuth.front.dao.RealNameAuthRequestDao;
import com.yl.realAuth.front.service.RealNameAuthRequestService;
import com.yl.realAuth.hessian.AuthTradeHessianService;
import com.yl.realAuth.hessian.bean.AuthResponseResult;
import com.yl.realAuth.hessian.bean.CreateOrderBean;
import com.yl.realAuth.hessian.exception.BusinessException;
import com.yl.realAuth.model.RealNameAuthOrder;
import com.yl.realAuth.model.RealNameAuthRequest;

@Service("realNameAuthRequestService")
public class RealNameAuthRequestServiceImpl implements RealNameAuthRequestService {

	private Logger logger = LoggerFactory.getLogger(RealNameAuthRequestServiceImpl.class);
	@Resource
	private RealNameAuthRequestDao realNameAuthRequestDao;
	@Resource
	private Validator validator;
	@Resource
	private AuthTradeHessianService authTradeHessianService;
	// @Resource
	// private CipherInterface cipherInterface;

	@Override
	public AuthResponseResult execute(RealNameAuthRequest reaNameAuthRequest) throws BusinessException {
		// 请求参数校验
		Set<ConstraintViolation<RealNameAuthRequest>> violations = validator.validate(reaNameAuthRequest);
		if (violations.size() > 0) {
			String originalMsg = violations.iterator().next().getPropertyPath().toString();
			String errorMsg = originalMsg.substring(originalMsg.lastIndexOf(".") + 1, originalMsg.length());
			if (violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(NotNull.class)) throw new BusinessException(
					StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", errorMsg).toString());
			else throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_ERROR, "-", errorMsg).toString());
		}

		// 生成请求记录
		realNameAuthRequestDao.insertAuthRequest(reaNameAuthRequest);

		// 创建订单记录bean
		CreateOrderBean createOrderBean = new CreateOrderBean();
		createOrder(reaNameAuthRequest, createOrderBean);
		// 创建订单信息
		/*AuthResponseResult authResponseResult = new AuthResponseResult();
		authResponseResult.setResponseCode("0001");
		authResponseResult.setRequestCode(reaNameAuthRequest.getRequestCode());
		authResponseResult.setResponseMsg("认证成功，结果正确");
		authResponseResult.setTradeOrderCode(System.currentTimeMillis()+"");*/
		//return authResponseResult;
		return authTradeHessianService.createOrder(createOrderBean);
	}

	/**
	 * 生成订单记录
	 * @param reaNameAuthRequest 实名认证请求实体
	 * @param createOrderBean 订单实体
	 */
	public void createOrder(RealNameAuthRequest reaNameAuthRequest, CreateOrderBean createOrderBean) {
		createOrderBean.setBankCardNo(reaNameAuthRequest.getBankCardNo());
		createOrderBean.setBankCardNoEncrypt(reaNameAuthRequest.getBankCardNoEncrypt());
		createOrderBean.setBusiType(reaNameAuthRequest.getBusiType());
		createOrderBean.setCertNo(reaNameAuthRequest.getCertNo());
		createOrderBean.setCertNoEncrypt(reaNameAuthRequest.getCertNoEncrypt());
		createOrderBean.setNotifyURL(reaNameAuthRequest.getNotifyURL());
		createOrderBean.setCustomerNo(reaNameAuthRequest.getCustomerNo());;
		createOrderBean.setPayerMobNo(reaNameAuthRequest.getPayerMobNo());
		createOrderBean.setPayerName(reaNameAuthRequest.getPayerName());
		createOrderBean.setRequestCode(reaNameAuthRequest.getRequestCode());
	}

	@Override
	public RealNameAuthRequest queryPartnerRequestByRequestCode(String requestCode, String customerNo) {
		return realNameAuthRequestDao.queryPartnerRequestByRequestCode(requestCode, customerNo);
	}

	@Override
	public String createResponse(RealNameAuthOrder reaNameAuthOrder, RealNameAuthRequest reaNameAuthRequest) throws BusinessException {
		// 处理结果
		String handlerResult = "";
		if (AuthOrderStatus.SUCCESS.equals(reaNameAuthOrder.getAuthOrderStatus())) {
			if (AuthResult.AUTH_SUCCESS.name().equals(reaNameAuthOrder.getAuthResult().name())) handlerResult = "0001"; // 认证信息正确
			if (AuthResult.AUTH_FAILED.name().equals(reaNameAuthOrder.getAuthResult().name())) handlerResult = "0002"; // 认证信息错误
		} else {
			handlerResult = "0003"; // 认证失败
		}

		Map<String, String> params = new LinkedHashMap<String, String>();
		// 接口编号
		params.put("apiCode", reaNameAuthRequest.getApiCode());
		// 版本号
		params.put("versionCode", reaNameAuthRequest.getVersionCode());
		// 参数编码字符集
		params.put("inputCharset", "UTF-8");
		// 签名方式
		params.put("signType", reaNameAuthRequest.getSignType());
		// 合作方编号
		params.put("partner", reaNameAuthRequest.getCustomerNo());
		// 合作方唯一订单号
		params.put("requestCode", reaNameAuthRequest.getRequestCode());
		// 回传参数
		params.put("returnParam", reaNameAuthRequest.getReturnParam());
		// 业务类型
		params.put("busiType", reaNameAuthRequest.getBusiType().toString());
		// 交易订单号
		params.put("tradeOrderCode", reaNameAuthOrder.getCode());
		// 处理结果
		params.put("handlerResult", handlerResult);

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

		String resSignSource = signSource.toString();
		// 计算签名
		String calSign = null;
		if (SignType.MD5.name().equals(reaNameAuthRequest.getSignType())) {
			String publicKey = "";// cipherInterface.getPublicKey(reaNameAuthRequest.getCustomerNo(), SignType.MD5.name());
			signSource.append(publicKey);
			logger.info("商户通知原始数据：{}", signSource);
			try {
				calSign = DigestUtils.md5DigestAsHex(signSource.toString().getBytes(reaNameAuthRequest.getInputCharset()));
			} catch (UnsupportedEncodingException e) {
				throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_ERROR, "-", Constants.PARAM_NAME_INPUT_CHARSET).toString());
			}
		}

		// 响应合作方信息
		StringBuilder responseCustomerUrl = new StringBuilder();

		responseCustomerUrl.append(reaNameAuthOrder.getNotifyURL()); // 后台通知方式
		responseCustomerUrl.append("?");
		responseCustomerUrl.append(resSignSource);
		responseCustomerUrl.append("&");
		responseCustomerUrl.append("sign=");
		responseCustomerUrl.append(calSign);

		return responseCustomerUrl.toString();
	}
}
