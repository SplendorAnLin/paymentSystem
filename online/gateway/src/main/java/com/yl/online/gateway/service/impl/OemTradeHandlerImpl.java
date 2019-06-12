package com.yl.online.gateway.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.security.DigestUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.lefu.commons.utils.web.HttpClientUtils.Method;
import com.yl.boss.api.bean.CustomerCertBean;
import com.yl.boss.api.bean.CustomerKey;
import com.yl.boss.api.enums.KeyType;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.online.gateway.Constants;
import com.yl.online.gateway.ExceptionMessages;
import com.yl.online.gateway.enums.SignType;
import com.yl.online.gateway.exception.BusinessException;
import com.yl.online.gateway.exception.BusinessRuntimeException;
import com.yl.online.gateway.service.OemTradeHandler;
import com.yl.online.model.enums.PayType;
import com.yl.online.model.model.PartnerRequest;

@Service("oemTradeHandler")
public class OemTradeHandlerImpl implements OemTradeHandler {
	private static final Logger logger = LoggerFactory.getLogger(OemTradeHandlerImpl.class);
	
	private final static String SETTLE_TYPE_FEE = "FEE";
	private final static String SETTLE_TYPE_AMOUNT = "AMOUNT";

	@Resource
	private CustomerInterface customerInterface;

	public String requestOme(PartnerRequest partnerRequest) throws BusinessException {

		String oemCustomerNo;
		String oemPosCati;
		if (StringUtils.isBlank(partnerRequest.getExtParam())) {
			return "SUCCESS";
		} else {
			try {
				Map<String, String> Extmap = JsonUtils.toObject(partnerRequest.getExtParam(), new TypeReference<Map<String, String>>() {
				});
				oemCustomerNo = Extmap.get("oemCustomerNo");
                oemPosCati = Extmap.get("OEM_CUST_CATI");
				if (StringUtils.isBlank(oemCustomerNo) && StringUtils.isBlank(oemPosCati)) {
					return "SUCCESS";
				}
			} catch (Exception e) {
				return "SUCCESS";
			}
		}

		// 查询对于系统请求地址
		CustomerCertBean customerCertBean = customerInterface.findCustomerCertByCustNo(partnerRequest.getPartner());
		String url = customerCertBean.getEnterpriseUrl() + "/trade";
		// 组装相关参数
		Map<String, String> map = new HashMap<>();
		map.put("apiCode", partnerRequest.getApiCode());
		map.put("inputCharset", "UTF-8");
		map.put("signType", "MD5");
		map.put("orderType", "TRADE");
		map.put("baseCustomerNo", partnerRequest.getPartner());
        map.put("basePosCati", oemPosCati);

		Map<String, String> params = new LinkedHashMap<String, String>();

		// 合作方唯一订单号
		params.put("outOrderId", partnerRequest.getOutOrderId());
		// 签名方式
		params.put("signType", partnerRequest.getSignType());
		// 合作方编号
		params.put("partner", partnerRequest.getPartner());
		// 回传参数
		params.put("returnParam", partnerRequest.getReturnParam());
		// 接口编号
		params.put("interfaceCode", partnerRequest.getInterfaceCode());
		if ("WXJSAPI".equals(partnerRequest.getPayType())) {
			params.put("interfaceCode", "WXJSAPI_YLZF");
		} else  if ("ALIPAYJSAPI".equals(partnerRequest.getPayType())) {
            params.put("interfaceCode", "ALIPAYJSAPI_YLZF");
        }
		// payType
		params.put("payType", partnerRequest.getPayType());
		// Oem系统商户号
		params.put("oemCustomerNo", oemCustomerNo);
		params.put("amount", partnerRequest.getAmount());

		map.put("bussinessBody", JsonUtils.toJsonString(params));

		// 密钥查询
		CustomerKey customerKey = customerInterface.getCustomerKey(partnerRequest.getPartner(), KeyType.MD5);
		if (customerKey == null)
			throw new BusinessRuntimeException(ExceptionMessages.RECEIVER_KEY_NOT_EXISTS);
		String key = customerKey.getKey();

		// 按参数名排序
		ArrayList<String> paramNames = new ArrayList<>(map.keySet());
		Collections.sort(paramNames);

		// 组织待签名信息
		StringBuilder signSource = new StringBuilder();
		Iterator<String> iterator = paramNames.iterator();
		while (iterator.hasNext()) {
			String paramName = iterator.next();
			if (!Constants.PARAM_NAME_SIGN.equals(paramName) && StringUtils.notBlank(map.get(paramName))) {
				signSource.append(paramName).append("=").append(map.get(paramName));
				if (iterator.hasNext())
					signSource.append("&");
			}
		}

		String paramsStr = signSource.toString();
		// 计算签名
		String calSign = null;
		if (SignType.MD5.name().equals(partnerRequest.getSignType())) {
			String signStr = key + map.get("bussinessBody");
			try {
				calSign = DigestUtils.md5DigestAsHex(signStr.getBytes(partnerRequest.getInputCharset()));
			} catch (UnsupportedEncodingException e) {
				throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_ERROR, "-", Constants.PARAM_NAME_INPUT_CHARSET).toString());
			}
		}

		map.put("sign", calSign);
		paramsStr = paramsStr + "&sign=" + calSign;

		String notifyResult = "FAILED";
		try {
			logger.info("请求OEM系统参数为：{}", paramsStr);
			String returnParam = HttpClientUtils.send(Method.POST, url, paramsStr, true, "UTF-8", 10000);
			logger.info("请求OEM系统返回内容为：{}", returnParam);
			Map<String, Object> resMap = JsonUtils.toObject(returnParam, new TypeReference<Map<String, Object>>() {
			});
			if ("SUCCESS".equals(resMap.get("result"))) {
				if (resMap.get("bussinessBody") != null) {
					Map<String, Object> business = JsonUtils.toObject(resMap.get("bussinessBody").toString(), new TypeReference<Map<String, Object>>() {});
					if ("SUCCESS".equals(business.get("status"))) {
						partnerRequest.setNotifyUrl(business.get("returnUrl").toString());
						notifyResult = "SUCCESS";
						if (PayType.QUICKPAY.name().equals(partnerRequest.getPayType())) {
							notifyResult = "FAILED";
							Object settleType = business.get("settleType");
							if (settleType == null || SETTLE_TYPE_AMOUNT.equals(settleType)) {
								if (business.get("settleAmount") != null) {
									partnerRequest.setSettleAmount(business.get("settleAmount").toString());
									partnerRequest.setSettleType(SETTLE_TYPE_AMOUNT);
									notifyResult = "SUCCESS";
								}
							} else if (SETTLE_TYPE_FEE.equals(settleType)){
								if (business.get("quickPayFee") != null && business.get("remitFee") != null) {
									partnerRequest.setQuickPayFee(business.get("quickPayFee").toString());
									partnerRequest.setRemitFee(business.get("remitFee").toString());
									partnerRequest.setSettleType(SETTLE_TYPE_FEE);
									notifyResult = "SUCCESS";
								}
							}
						}
					}
				} else {
					notifyResult = resMap.get("respCode").toString();
				}
			}
		} catch (Exception e) {
			logger.info("生成OEM订单报错:{}", e);
		}
		return notifyResult;
	}
	
	public static void main(String[] args) {
		Map map = new HashMap();
		map.put("oemCustomerNo", "OC100156");
		System.out.println(JsonUtils.toJsonString(map));
		
	}
}
