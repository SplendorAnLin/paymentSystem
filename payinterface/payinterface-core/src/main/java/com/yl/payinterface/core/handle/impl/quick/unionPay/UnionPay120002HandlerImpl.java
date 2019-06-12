package com.yl.payinterface.core.handle.impl.quick.unionPay;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.cache.util.CacheUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.boss.api.bean.TransCardBean;
import com.yl.boss.api.enums.CardAttr;
import com.yl.boss.api.enums.CardStatus;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.payinterface.core.bean.QuickPayOpenCardResponseBean;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.exception.BusinessRuntimeException;
import com.yl.payinterface.core.handle.impl.quick.unionPay.utils.UnionPay120001Utils;
import com.yl.payinterface.core.handler.BindCardHandler;
import com.yl.payinterface.core.handler.QuickPayFilingHandler;
import com.yl.payinterface.core.handler.QuickPayHandler;
import com.yl.payinterface.core.handler.QuickPayOpenCardHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.model.QuickPayFilingInfo;
import com.yl.payinterface.core.service.BindCardInfoService;
import com.yl.payinterface.core.service.InterfaceInfoService;
import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.service.QuickPayFeeService;
import com.yl.payinterface.core.service.QuickPayFilingInfoService;

/**
 * 
 * @ClassName UnionPay120001HandlerImpl
 * @Description 银联快捷支付
 * @author 聚合支付
 * @date 2017年11月18日 下午2:55:57
 */
@Service("quickUnionPay120002Handler")
public class UnionPay120002HandlerImpl implements QuickPayHandler, BindCardHandler, QuickPayOpenCardHandler {
	
	private static final String interfaceInfoCode = "QUICKPAY_UnionPay-120002";
	
	private static Logger logger = LoggerFactory.getLogger(UnionPay120002HandlerImpl.class);

	@Resource
	private CustomerInterface customerInterface;
	@Resource
	private InterfaceRequestService interfaceRequestService;
	@Resource
	private InterfaceInfoService interfaceInfoService;
	@Resource
	private QuickPayFeeService quickPayFeeService;
	@Resource
	private QuickPayFilingHandler unionPay120002FilingHandler;
	@Resource
	private QuickPayFilingInfoService quickPayFilingInfoService;
	@Resource
	private BindCardInfoService bindCardInfoService;

	@Override
	public Map<String, String> bindCard(Map<String, String> params) {
		String responseCode = "9999";
		String cardNo = params.get("cardNo");
		String interfaceInfoCode = params.get("interfaceCode");
		String ownerId = params.get("partner");
		String remitFee = params.get("remitFee");
		String quickPayFee = params.get("quickPayFee");
		String amount = params.get("amount");
		
		if (StringUtils.isBlank(remitFee)) {
			remitFee = quickPayFeeService.getRemitFee(ownerId, Double.valueOf(amount));
		}
		if (StringUtils.isBlank(quickPayFee)) {
			quickPayFee = quickPayFeeService.getQuickPayFee(ownerId);
		}
		
		TransCardBean transCardBean = customerInterface.findByCustAndAccNo(ownerId, cardNo);
		
		QuickPayFilingInfo filingInfo = quickPayFilingInfoService.find(transCardBean.getAccountNo(), interfaceInfoCode);
		
		params.put("name", transCardBean.getAccountName());
		params.put("idCardNo", transCardBean.getIdNumber());
		params.put("phone", transCardBean.getPhone());
		params.put("clearBankCode", transCardBean.getClearBankCode());
		params.put("bankName", transCardBean.getBankName());
		params.put("bankCardNo", transCardBean.getAccountNo());
		if (filingInfo == null) {
			params.put("remitFee", remitFee);
			params.put("quickPayFee", quickPayFee);
			params.put("updateFlag", "0");
			Map<String, String> rtMap = unionPay120002FilingHandler.filing(params);
			if ("SUCCESS".equals(rtMap.get("status"))) {
				responseCode = "00";
			}
		} else {
			if (!filingInfo.getRemitFee().equals(remitFee) || !filingInfo.getQuickPayFee().equals(quickPayFee) ||
					!filingInfo.getPhone().equals(transCardBean.getPhone())) {
				params.put("remitFee", remitFee);
				params.put("quickPayFee", quickPayFee);
				params.put("updateFlag", "1");
				params.put("customerCode", filingInfo.getCustomerCode());
				params.put("channelCustomerCode", filingInfo.getChannelCustomerCode());
				params.put("filingInfoCode", filingInfo.getCode());
				Map<String, String> rtMap = unionPay120002FilingHandler.filing(params);
				if ("SUCCESS".equals(rtMap.get("status"))) {
					responseCode = "00";
				}
			} else {
				responseCode = "00";
			}
		}
		params.put("smsCodeType", "NO_YLZF_CODE");
		params.put("responseCode", responseCode);
		params.put("result", responseCode);
		params.put("gateway", "quickPay/openCard");
		return params;
	}

	@Override
	public Map<String, String> queryBindCard(Map<String, String> params) {
		String responseCode = "9999";
		String cardNo = params.get("cardNo");
		String interfaceInfoCode = params.get("interfaceCode");
		String ownerId = params.get("partner");
		String remitFee = params.get("remitFee");
		String quickPayFee = params.get("quickPayFee");
		String amount = params.get("amount");
		
		if (StringUtils.isBlank(remitFee)) {
			remitFee = quickPayFeeService.getRemitFee(ownerId, Double.valueOf(amount));
		}
		if (StringUtils.isBlank(quickPayFee)) {
			quickPayFee = quickPayFeeService.getQuickPayFee(ownerId);
		}
		
		TransCardBean transCardBean = customerInterface.findByCustAndAccNo(ownerId, cardNo);
		
		QuickPayFilingInfo filingInfo = quickPayFilingInfoService.find(transCardBean.getAccountNo(), interfaceInfoCode);
		
		params.put("name", transCardBean.getAccountName());
		params.put("idCardNo", transCardBean.getIdNumber());
		params.put("phone", transCardBean.getPhone());
		params.put("clearBankCode", transCardBean.getClearBankCode());
		params.put("bankName", transCardBean.getBankName());
		params.put("bankCardNo", transCardBean.getAccountNo());
		if (filingInfo == null) {
			params.put("remitFee", remitFee);
			params.put("quickPayFee", quickPayFee);
			params.put("updateFlag", "0");
			Map<String, String> rtMap = unionPay120002FilingHandler.filing(params);
			if ("SUCCESS".equals(rtMap.get("status"))) {
				responseCode = "00";
			}
		} else {
			if (!filingInfo.getRemitFee().equals(remitFee) || !filingInfo.getQuickPayFee().equals(quickPayFee) ||
					!filingInfo.getPhone().equals(transCardBean.getPhone())) {
				params.put("remitFee", remitFee);
				params.put("quickPayFee", quickPayFee);
				params.put("updateFlag", "1");
				params.put("customerCode", filingInfo.getCustomerCode());
				params.put("channelCustomerCode", filingInfo.getChannelCustomerCode());
				params.put("filingInfoCode", filingInfo.getCode());
				Map<String, String> rtMap = unionPay120002FilingHandler.filing(params);
				if ("SUCCESS".equals(rtMap.get("status"))) {
					responseCode = "00";
				}
			} else {
				responseCode = "00";
			}
		}
		/*if ("00".equals(responseCode)) {
			BindCardInfo bindCardInfo = bindCardInfoService.findEffective(params.get("cardNo"), params.get("interfaceCode"));
    		if (bindCardInfo!= null) {
    			params.put("token", bindCardInfo.getToken());
    			params.put("responseCode", "00");
    		}else {
    			params.put("responseCode", "01");
    		}
		}*/
		
		params.put("responseCode", responseCode);
		
		return params;
	}

	@Override
	public Map<String, String> sendVerifyCode(Map<String, String> params) {

		InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(params.get("interfaceRequestID"));
		String code = "H197";
		String cardNo = params.get("cardNo");
		params.put("amount", Double.toString(interfaceRequest.getAmount()));
		// 检查交易卡信息
		checkCardInfo(params, interfaceRequest.getOwnerID(), cardNo);
		// 获取结算卡信息
		Map<Object,Object> settMap = new HashMap<>();
		quickPayFeeService.getSettleInfo(settMap, interfaceRequest.getOwnerID(), cardNo);
		// 保存交易卡历史信息
		quickPayFeeService.saveTransCardHis(cardNo, interfaceRequest);
		// 获取渠道商户号
		params.put("trano", getTrano(interfaceRequest.getOwnerID(), cardNo));
		// 发起交易
		Map<String, String> map = UnionPay120001Utils.getRequestMessage(code, params);

		Map<String, String> retMap = new HashMap<>();
		try {
			if ("00".equals(map.get("respCode"))) {
				retMap.put("interfaceRequestID", interfaceRequest.getInterfaceRequestID());
				retMap.put("orderCode", interfaceRequest.getBussinessOrderID());
				retMap.put("payCardNo", params.get("cardNo"));
				retMap.put("settleCardNo", settMap.get("cardNo").toString());
				retMap.put("payerName", settMap.get("realName").toString());
				retMap.put("certNo", settMap.get("identityCard").toString());
				retMap.put("phone", params.get("phone").toString());
				retMap.put("responseCode", "00");
				retMap.put("token", params.get("token"));
				retMap.put("amount", Double.toString(interfaceRequest.getAmount()));
				retMap.put("interfaceCode", interfaceRequest.getInterfaceInfoCode());
				retMap.put("responseCode", "00");

				Map<String, String> bindCardInfo = new HashMap<>();
				bindCardInfo.put("interfaceRequestID", interfaceRequest.getInterfaceRequestID());
				bindCardInfo.put("interfaceCode", params.get("interfaceInfoCode"));
				bindCardInfo.put("cardNo", cardNo);
				bindCardInfo.put("serial", map.get("serial"));
				bindCardInfo.put("realName", settMap.get("realName").toString());
				retMap.put("key", cardNo);
				try {
					CacheUtils.setEx("BINDCARD:" + cardNo, JsonUtils.toJsonString(bindCardInfo), 900);
				} catch (Exception e) {
					logger.error("恒信通认证支付 缓存绑卡信息异常 接口编号:[{}],卡号:[{}],异常信息：[{}]", params.get("interfaceInfoCode"), params.get("cardNo"), e);
				}
				retMap.put("gateway", "quickPay");
			} else if ("77".equals(map.get("respCode"))) {
				retMap.put("smsCodeType", "NO_YLZF_CODE");
				retMap.put("interfaceCode", params.get("interfaceInfoCode"));
				retMap.put("gateway", "quickPay/openCard");
				retMap.put("interfaceRequestID", interfaceRequest.getInterfaceRequestID());
				retMap.put("responseCode", "77");
			} else {
				retMap.put("responseCode", "2001");
				retMap.put("responseMessage", "短信验证码发送失败");
				retMap.put("gateway", "quickPay");
			} 
			retMap.put("cardNo", cardNo);
			retMap.put("interfaceInfoCode", params.get("interfaceInfoCode"));
		} catch (Exception e) {
			if (e instanceof BusinessRuntimeException) {
				retMap.put("responseCode", e.getMessage().equals("2002") || e.getMessage().equals("2003") ? "2001" : e.getMessage());
				retMap.put("interfaceRequestID", params.get("interfaceRequestID"));
				return retMap;
			}
			logger.error("恒信通认证支付 获取验证码异常 接口请求号:[{}],卡号:[{}],异常信息：[{}]", interfaceRequest.getInterfaceRequestID(), params.get("cardNo"), e);
			throw new RuntimeException(e.getMessage());
		}

		return retMap;
	}

	@Override
	public Map<String, String> authPay(Map<String, String> map) {
		return sendVerifyCode(map);
	}

	@Override
	public Map<String, String> sale(Map<String, String> params) {
		Map<String, String> respMap = new HashMap<>();
		
		String interfaceRequestId = params.get("interfaceRequestID");
		String cardNo = params.get("cardNo");
		InterfaceRequest interfaceRequest = JsonUtils.toObject(params.get("interfaceRequest"), InterfaceRequest.class);
		
		String check = CacheUtils.get("BINDCARD:" + cardNo, String.class);
		Map<String, String> bindCardInfo = JsonUtils.toObject(check, new TypeReference<Map<String, String>>() {});
		
		String bankSerial = bindCardInfo.get("serial");
		params.put("bankSerial", bankSerial);
		params.put("amount", String.valueOf(interfaceRequest.getAmount()));
		params.put("realName", bindCardInfo.get("realName"));
		// 获取渠道商户号
		params.put("trano", getTrano(interfaceRequest.getOwnerID(), cardNo));
				
		String code = "H196";
		Map<String, String> map = UnionPay120001Utils.getRequestMessage(code, params);
		respMap.put("interfaceRequestID", interfaceRequestId);
        respMap.put("amount", Double.toString(interfaceRequest.getAmount()));
        respMap.put("authTranStat", "UNKNOWN");
		if ("00".equals(map.get("respCode"))) {
			respMap.put("authTranStat", "SUCCESS");
            respMap.put("compType", BusinessCompleteType.NORMAL.name());
            
            respMap.put("tranStat", "UNKNOWN");
            respMap.put("responseCode", "01");
            respMap.put("responseMsg", "交易成功，扣款状态未知");
            return respMap;
		} else if ("77".equals(map.get("respCode"))) {
			respMap.put("smsCodeType", "NO_SMS_CODE_BIND");
			respMap.put("interfaceCode", params.get("interfaceInfoCode"));
			respMap.put("gateway", "quickPay/openCard");
			respMap.put("interfaceRequestID", interfaceRequest.getInterfaceRequestID());
			respMap.put("responseCode", "77");
		} else if ("71".equals(map.get("respCode"))) {
            respMap.put("responseCode", "9008");
            respMap.put("responseMsg", "验证码输入错误");
		} else {
			respMap.put("responseCode", "9003");
            respMap.put("responseMsg", map.get("respMsg"));
		}
		return respMap;
	}

	@Override
	public Map<String, String> query(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QuickPayOpenCardResponseBean sendOpenCardVerifyCode(Map<String, String> params) {

		QuickPayOpenCardResponseBean responseBean = new QuickPayOpenCardResponseBean();
		String code = "H199";
		// 获取渠道商户号
		String customerNo = params.get("customerNo");
		params.put("trano", getTrano(customerNo, params.get("cardNo")));
		Map<String, String> map = UnionPay120001Utils.getRequestMessage(code, params);
		if ("00".equals(map.get("respCode"))) {
			responseBean.setStatus("SUCCESS");
		} else {
			responseBean.setStatus("FAILED");
		}
		responseBean.setResponseCode(map.get("respCode"));
		responseBean.setResponseMessage(map.get("respMsg"));
		return responseBean;
	}

	@Override
	public QuickPayOpenCardResponseBean openCardInfo(Map<String, String> params) {
		QuickPayOpenCardResponseBean responseBean = new QuickPayOpenCardResponseBean();
		String code = "H198";
		// 获取渠道商户号
		String customerNo = params.get("customerNo");
		params.put("trano", getTrano(customerNo, params.get("cardNo")));
		Map<String, String> map = UnionPay120001Utils.getRequestMessage(code, params);
		if ("00".equals(map.get("respCode"))) {
			responseBean.setStatus("SUCCESS");
		} else {
			responseBean.setStatus("FAILED");
		}
		responseBean.setResponseCode(map.get("respCode"));
		responseBean.setResponseMessage(map.get("respMsg"));
		return responseBean;
	}
	
	private void checkCardInfo(Map<String, String> map, String ownerId, String cardNo) {
		TransCardBean transCardBean = customerInterface.findTransCardByAccNo(ownerId, cardNo, CardAttr.TRANS_CARD);
		if (transCardBean == null) {
			throw new BusinessRuntimeException("3009");
		}
		if (transCardBean.getCardStatus() != CardStatus.NORAML) {
			throw new BusinessRuntimeException("3008");
		}
		map.put("phone", transCardBean.getPhone());
	}
	
	private String getTrano(String ownerId, String bankCardNo) {
		TransCardBean transCardBean = customerInterface.findByCustAndAccNo(ownerId, bankCardNo);
		QuickPayFilingInfo quickPayFilingInfo = quickPayFilingInfoService.find(transCardBean.getAccountNo(), interfaceInfoCode);
		return quickPayFilingInfo.getChannelCustomerCode();
	}

}
