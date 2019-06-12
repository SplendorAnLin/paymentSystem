package com.yl.realAuth.front.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.security.DigestUtils;
import com.yl.boss.api.bean.CustomerKey;
import com.yl.boss.api.enums.KeyType;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.realAuth.enums.AuthBusiType;
import com.yl.realAuth.enums.AuthConfigStatus;
import com.yl.realAuth.enums.PartnerStatus;
import com.yl.realAuth.enums.SignType;
import com.yl.realAuth.front.Constants;
import com.yl.realAuth.front.ExceptionMessages;
import com.yl.realAuth.front.MerchantResponseCode;
import com.yl.realAuth.front.context.RequestProxy;
import com.yl.realAuth.front.service.RealNameAuthRequestService;
import com.yl.realAuth.hessian.AuthConfigHessianService;
import com.yl.realAuth.hessian.bean.AuthResponseResult;
import com.yl.realAuth.hessian.exception.BusinessException;
import com.yl.realAuth.hessian.exception.BusinessRuntimeException;
import com.yl.realAuth.hessian.utils.CheckIDCard;
import com.yl.realAuth.hessian.utils.CommonUtils;
import com.yl.realAuth.model.Partner;
import com.yl.realAuth.model.RealNameAuthRequest;

/**
 * 实名认证交易处理
 * @author Shark
 * @since 2015年6月3日
 */
@Controller
@RequestMapping("/auth")
public class TradeController {
	
	private static final Logger logger = LoggerFactory.getLogger(TradeController.class);
	@Resource
	private AuthConfigHessianService authConfigHessianService;
	@Resource
	private RealNameAuthRequestService realNameAuthRequestService;
	// @Resource
	// private CipherInterface cipherInterface;

	@Resource
	private CustomerInterface customerInterface;
	
	private static Properties prop = new Properties();

	static {
		try {
			prop.load(TradeController.class.getClassLoader().getResourceAsStream("serverHost.properties"));
		} catch (IOException e) {
			logger.error("", e);
		}
	}

	/**
	 * 实名认证下单
	 * @param model 模型
	 * @param request 请求信息
	 * @return
	 */
	@RequestMapping("trade")
	public void trade(Model model, HttpServletRequest request, HttpServletResponse response) {
		// 设置编码
		response.setContentType("text/plain;charset=UTF-8");

		// 获取请求参数
		Map<String, String[]> OriginalParams = request.getParameterMap();
		Map<String, String> params = new HashMap<>();
		for (String key : OriginalParams.keySet()) {
			params.put(key, OriginalParams.get(key)[0]);
		}
		// 商户订单号
		String outOrderId = params.get(Constants.PARAM_NAME_OUT_ORDER_ID);

		logger.info("商户订单号为【" + outOrderId + "】的实名认证原始请求信息：{}", params);
		// 创建响应实体
		AuthResponseResult responseResult = new AuthResponseResult();
		try {
			RequestProxy requestProxy = new RequestProxy(request);
			request = requestProxy;

			// 校验参数
			RealNameAuthRequest reaNameAuthRequest = signValidate(params);
			// 生成实名认证请求信息
			responseResult = realNameAuthRequestService.execute(reaNameAuthRequest);
		} catch (Exception e) {
			logger.error("商户订单号为【" + outOrderId + "】的实名认证交易请求异常：{}", e);
			responseResult = this.transferredErrorMsg(request, e);
			responseResult.setRequestCode(outOrderId);
		}

		try {
			response.getWriter().write(JsonUtils.toJsonString(responseResult));
			response.getWriter().close();
		} catch (IOException e) {
			logger.error("实名认证同步响应异常：{}", e);
		}
	}

	/**
	 * 错误码转义
	 * @param request 请求实体
	 * @param e 异常实体
	 */
	private AuthResponseResult transferredErrorMsg(HttpServletRequest request, Exception e) {
		// 实名认证响应结果
		Map<String, String> merchantResponseCodeMap = new HashMap<String, String>();
		AuthResponseResult responseResult = new AuthResponseResult();
		String errorMsg = "";
		if (e.getMessage() != null) {
			if (e.getClass().isAssignableFrom(RuntimeException.class)) {
				errorMsg = e.getMessage().substring(e.getMessage().lastIndexOf(":") + 2, e.getMessage().length());
			} else if (e.getClass().isAssignableFrom(BusinessException.class)) {
				errorMsg = e.getMessage();
			}
			merchantResponseCodeMap = MerchantResponseCode.getMerchantCode(errorMsg);
		}

		responseResult.setResponseCode(merchantResponseCodeMap.get("responseCode"));
		responseResult.setResponseMsg(merchantResponseCodeMap.get("responseMsg"));
		return responseResult;
	}

	/**
	 * 合作方签名检验
	 * @param reaNameAuthRequest 合作方请求信息
	 * @param params 原请求信息
	 * @throws BusinessException
	 */
	private RealNameAuthRequest signValidate(Map<String, String> params) throws BusinessException {
		// 请求实体
		RealNameAuthRequest reaNameAuthRequest = new RealNameAuthRequest();
		// 检查接口名称
		String apiCode = params.get(Constants.PARAM_NAME_API_CODE);
		if (StringUtils.isBlank(apiCode)) {
			throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", Constants.PARAM_NAME_API_CODE).toString());
		}
		reaNameAuthRequest.setApiCode(apiCode);
		// 检查版本号
		String versionCode = params.get(Constants.PARAM_NAME_VERSION_CODE);
		if (StringUtils.isBlank(versionCode)) {
			throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", Constants.PARAM_NAME_VERSION_CODE).toString());
		}
		reaNameAuthRequest.setVersionCode(versionCode);
		// 检查输入字符集
		String inputCharset = params.get(Constants.PARAM_NAME_INPUT_CHARSET);
		if (StringUtils.isBlank(inputCharset)) {
			throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", Constants.PARAM_NAME_INPUT_CHARSET).toString());
		}
		reaNameAuthRequest.setInputCharset(inputCharset);
		// 检查业务类型
		final String busiType = params.get(Constants.PARAM_NAME_BUSITYPE);
		if (StringUtils.isBlank(busiType)) {
			throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", Constants.PARAM_NAME_BUSITYPE).toString());
		}
		reaNameAuthRequest.setBusiType(busiType);

		// 检查合作方
		final String partnerCode = params.get(Constants.PARAM_NAME_PARTNER);
		if (StringUtils.isBlank(partnerCode)) {
			throw new RuntimeException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", Constants.PARAM_NAME_PARTNER).toString());
		} else {
			// 判断实名认证服务是否开通
			String authStatus = authConfigHessianService.queryAuthConfigByCustomerNoAndBusiType(partnerCode, busiType);
			if (AuthConfigStatus.FALSE.name().equals(authStatus) || "".equals(authStatus)) {
				throw new BusinessException(ExceptionMessages.RECEIVER_NOT_OPEN_SERVICE.toString());
			}
		}
		reaNameAuthRequest.setCustomerNo(partnerCode);
		// 检查签名方式
		String signType = params.get(Constants.PARAM_NAME_SIGN_TYPE);
		if (StringUtils.isBlank(signType)) {
			throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", Constants.PARAM_NAME_SIGN_TYPE).toString());
		}
		boolean supportSignType = false;
		for (SignType _signType : SignType.values()) {
			if (_signType.name().equals(signType)) {
				supportSignType = true;
				break;
			}
		}
		if (!supportSignType) {
			throw new BusinessException(ExceptionMessages.SIGN_ALGORITHM_NOT_SUPPORT.toString());
		}
		reaNameAuthRequest.setSignType(signType);

		// 检查签名
		String sign = params.get(Constants.PARAM_NAME_SIGN);
		if (StringUtils.isBlank(sign)) {
			throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", Constants.PARAM_NAME_SIGN).toString());
		}
		reaNameAuthRequest.setSign(sign);

		// 检查商户订单号
		String outOrderId = params.get(Constants.PARAM_NAME_OUT_ORDER_ID);
		if (StringUtils.isBlank(outOrderId)) {
			throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", Constants.PARAM_NAME_OUT_ORDER_ID).toString());
		}
		reaNameAuthRequest.setRequestCode(outOrderId);;

		// 回传参数
		String returnParam = params.get(Constants.PARAM_NAME_RETURNPARAM);
		if (StringUtils.notBlank(returnParam)) {
			reaNameAuthRequest.setReturnParam(returnParam);
		}

		// 手机号
		if (!StringUtils.isBlank(params.get("payerMobNo"))) {
			if (isMobile(params.get("payerMobNo"))) {
				reaNameAuthRequest.setPayerMobNo(params.get("payerMobNo"));
			} else {
				throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_ERROR, "-", Constants.PARAM_NAME_MOBILE_NO).toString());
			}
		}
		// 身份证号
		if (StringUtils.isBlank(params.get("certNo"))) {
			throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", Constants.PARAM_NAME_CERTNO).toString());
		} else {
			String certNo = params.get("certNo").toUpperCase();
			if (StringUtils.notBlank(CheckIDCard.IDCardValidate(certNo))) {
				throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_ERROR, "-", Constants.PARAM_NAME_CERTNO).toString());
			};
			// 身份证号
			Map<String, String> certNoInfo = CommonUtils.certNoEncrypt(certNo);
			reaNameAuthRequest.setCertNo(certNoInfo.get("certNo"));
			reaNameAuthRequest.setCertNoEncrypt(certNoInfo.get("certNoEncrypt"));

		}
		// 姓名
		if (StringUtils.isBlank(params.get("payerName"))) {
			throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", Constants.PARAM_NAME_PAYER_NAME).toString());
		};
		if (AuthBusiType.BINDCARD_AUTH.name().equals(busiType)) {
			if (StringUtils.isBlank(params.get("bankCardNo"))) {
				throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", Constants.PARAM_NAME_BANK_CARD_NO).toString());
			} //else {
				//if (!CheckBankNumber.checkCard(params.get("bankCardNo"))) {
					//throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_ERROR, "-", Constants.PARAM_NAME_BANK_CARD_NO).toString());
				//}
				// 卡号
				Map<String, String> cardInfo = CommonUtils.cardNoEncrypt(params.get("bankCardNo"));
				reaNameAuthRequest.setBankCardNo(cardInfo.get("bankCardNo"));
				reaNameAuthRequest.setBankCardNoEncrypt(cardInfo.get("bankCardNoEncrypt"));
			//}
		}

		// 查询卖方信息
	/*	Partner partner = new Partner();CacheUtils.execute(null, new CacheCallback<Partner>() {
			@SuppressWarnings("unchecked")
			@Override
			public Partner doCallback(Redis redis) {
				List<String> params = redis.hmget(
						StringUtils.concatToSB(Constants.PARTNER_CACHE_PERFIX, PartnerRole.CUSTOMER.name(), Constants.NODE_SEPATOR, partnerCode).toString(), "name",
						"status", "antiPhishingFlag", "referer", "interfaceType", "antiPhishingType", "ip");
				Partner partner = new Partner();
				if (params == null || params.size() == 0) return partner;
				partner.setCode(partnerCode);
				partner.setName(params.get(0));
				partner.setStatus(PartnerStatus.valueOf(params.get(1)));
				partner.setInterfaceType(JsonUtils.toObject(params.get(4), List.class));
				return partner;
			}
		});

		if (partner == null) throw new BusinessRuntimeException(ExceptionMessages.RECEIVER_NOT_EXISTS);

		if (partner.getStatus() == null || !partner.getStatus().equals(PartnerStatus.NORMAL)) {
			throw new BusinessException(ExceptionMessages.RECEIVER_STATUS_ERROR);
		}

		// 开通相关业务
		if (!partner.getInterfaceType().contains("ONLINE")) throw new BusinessException(ExceptionMessages.RECEIVER_NOT_OPEN_SERVICE);
*/
		// 按参数名排序
		ArrayList<String> paramNames = new ArrayList<>(params.keySet());
		Collections.sort(paramNames);
		// 组织待签名信息
		StringBuilder signSource = new StringBuilder();
		Iterator<String> iterator = paramNames.iterator();
		String payerName = "";
		while (iterator.hasNext()) {
			String paramName = iterator.next();
			if (!Constants.PARAM_NAME_SIGN.equals(paramName) && StringUtils.notBlank(params.get(paramName))) {
				if ("payerName".equals(paramName)) {
					try {
						payerName = URLDecoder.decode(params.get("payerName"), "UTF-8");
						signSource.append(paramName).append("=").append(payerName);
					} catch (UnsupportedEncodingException e) {
						logger.error("{}", e);
					}
				} else {
					signSource.append(paramName).append("=").append(params.get(paramName));
				}
				if (iterator.hasNext()) signSource.append("&");
			}
		}

		// 计算签名
		String calSign = null;
		if (SignType.MD5.name().equals(signType)) {
			CustomerKey key = customerInterface.getCustomerKey(partnerCode, KeyType.MD5);
			String cipherStr = key.getKey();
			signSource.append(cipherStr);
			try {
				logger.info("当前字符集:{},商户交易请求原文串：{}", inputCharset, signSource.toString());
				calSign = DigestUtils.md5DigestAsHex(signSource.toString().getBytes(inputCharset));
			} catch (UnsupportedEncodingException e) {
				throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_ERROR, "-", Constants.PARAM_NAME_INPUT_CHARSET).toString());
			}
		}

		if (!sign.equals(calSign)) {
			logger.info("sign check error\r\nseller={}\r\nsign={}\r\ncalSign={}", partnerCode, sign, calSign);
			throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.SIGN_ERROR).toString());
		}

		// 姓名
		reaNameAuthRequest.setPayerName(payerName);
		// 扩展参数
		reaNameAuthRequest.setExtParam(params.get("extParam"));
		// 异步通知地址
		reaNameAuthRequest.setNotifyURL(params.get("notifyURL"));
		return reaNameAuthRequest;
	}

	/**
	 * 手机号验证
	 * @param str
	 * @return 验证通过返回true
	 */
	public boolean isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,8,7][0-9]{9}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches();
		return b;
	}

	public static void main(String[] args) {
		String cipherStr = "c1b45b8ddc8041b48440955934f916d7";
		try {
			String calSign = DigestUtils.md5DigestAsHex(cipherStr.toString().getBytes("UTF-8"));
			System.out.println(calSign);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
