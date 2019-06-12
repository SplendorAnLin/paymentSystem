package com.yl.payinterface.core.handle.impl.quick.yinsheng584001;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.boss.api.bean.TransCardBean;
import com.yl.boss.api.enums.CardAttr;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.payinterface.core.C;
import com.yl.payinterface.core.bean.QuickPayOpenCardResponseBean;
import com.yl.payinterface.core.exception.BusinessRuntimeException;
import com.yl.payinterface.core.handle.impl.quick.yinsheng584001.utils.YinSheng584001Utils;
import com.yl.payinterface.core.handle.impl.wallet.cib330000.CommonsUtil;
import com.yl.payinterface.core.handler.BindCardHandler;
import com.yl.payinterface.core.handler.QuickPayHandler;
import com.yl.payinterface.core.handler.QuickPayOpenCardHandler;
import com.yl.payinterface.core.model.BindCardInfo;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.service.BindCardInfoService;
import com.yl.payinterface.core.service.InterfaceInfoService;
import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.service.QuickPayFeeService;

/**
 * @ClassName YinSheng584001Handler
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author Administrator
 * @date 2017年12月20日 下午2:52:49
 */
@Service("quickYinSheng584001Handler")
public class YinSheng584001Handler implements QuickPayHandler, BindCardHandler, QuickPayOpenCardHandler {

	private static Logger logger = LoggerFactory.getLogger(YinSheng584001Handler.class);

	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	@Resource
	private CustomerInterface customerInterface;
	@Resource
	private InterfaceRequestService interfaceRequestService;
	@Resource
	private InterfaceInfoService interfaceInfoService;
	@Resource
	private QuickPayFeeService quickPayFeeService;
	@Resource
	private BindCardInfoService bindCardInfoService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yl.payinterface.core.handler.BindCardHandler#bindCard(java.util.Map)
	 */
	@Override
	public Map<String, String> bindCard(Map<String, String> params) {
		params.put("smsCodeType", "NOT_SMS_CODE");
		params.put("responseCode", "00");
		params.put("result", "00");
		params.put("gateway", "quickPay/openCard");
		return params;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yl.payinterface.core.handler.BindCardHandler#queryBindCard(java.util
	 * .Map)
	 */
	@Override
	public Map<String, String> queryBindCard(Map<String, String> params) {
		params.put("responseCode", "01");
		return params;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yl.payinterface.core.handler.QuickPayHandler#sendVerifyCode(java.
	 * util.Map)
	 */
	@Override
	public Map<String, String> sendVerifyCode(Map<String, String> params) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yl.payinterface.core.handler.QuickPayHandler#authPay(java.util.Map)
	 */
	@Override
	public Map<String, String> authPay(Map<String, String> params) {
		Properties properties = JsonUtils.toObject(String.valueOf(params.get("tradeConfigs")), new TypeReference<Properties>() {
		});
		InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(params.get("interfaceRequestID"));
		String key = properties.getProperty("key");
		String agent = properties.getProperty("agent");
		String frontUrl = properties.getProperty("frontUrl");
		String url = properties.getProperty("url");
		String cardNo = params.get("cardNo");
		String interfaceCode = params.get("interfaceCode");
		String interfaceRequestID = params.get("interfaceRequestID");

		String settleType = params.get("settleType");
		if (C.SETTLE_TYPE_AMOUNT.equals(settleType)) {
			throw new BusinessRuntimeException("9010");
		}
		String remitFee = params.get("remitFee");
		String quickPayFee = params.get("quickPayFee");

		if (StringUtils.isBlank(remitFee)) {
			remitFee = quickPayFeeService.getRemitFee(interfaceRequest.getOwnerID(), interfaceRequest.getAmount());
		}
		if (StringUtils.isBlank(quickPayFee)) {
			quickPayFee = quickPayFeeService.getQuickPayFee(interfaceRequest.getOwnerID());
		}
		logger.info("接口请求号：{}，代付费率：{}，快捷费率：{}", interfaceRequest.getInterfaceRequestID(), remitFee, quickPayFee);

		// 检查交易卡信息
		TransCardBean transCardBean = customerInterface.findTransCardByAccNo(interfaceRequest.getOwnerID(), cardNo, CardAttr.TRANS_CARD);
		// 获取结算卡信息
		TransCardBean settleInfo = quickPayFeeService.getSettleInfo(interfaceRequest.getOwnerID(), cardNo);
		// 保存交易卡历史信息
		quickPayFeeService.saveTransCardHis(cardNo, interfaceRequest);

		BindCardInfo bindCardInfo = bindCardInfoService.find(cardNo, interfaceCode);

		SortedMap<String, String> map = new TreeMap<>();
		map.put("version", "1");
		map.put("agent", agent);
		map.put("mobile", transCardBean.getPhone());
		map.put("debit", settleInfo.getAccountNo());
		map.put("name", settleInfo.getAccountName());
		map.put("idno", settleInfo.getIdNumber());
		map.put("credit", cardNo);
		map.put("cvn", bindCardInfo.getCvv());
		map.put("expiry", bindCardInfo.getValidityMonth() + bindCardInfo.getValidityYear());
		map.put("out_order_id", interfaceRequestID);
		map.put("amount", String.valueOf((int) AmountUtils.multiply(interfaceRequest.getAmount(), 100)));
		map.put("front_url", frontUrl);
		map.put("sumer_fee", String.valueOf(AmountUtils.multiply(Double.valueOf(quickPayFee), 100)));
		map.put("sumer_amt", String.valueOf((int)AmountUtils.multiply(Double.valueOf(remitFee), 100)));
		map.put("noise", CommonsUtil.getNonceStr());
		map.put("sign", YinSheng584001Utils.createSign(map, key));
		logger.info("接口请求号：{}，请求原文：{}", interfaceRequest.getInterfaceRequestID(), map);
		map.put("url", url);
		map.put("interfaceRequestID", interfaceRequestID);
		map.put("payPage", "INTERFACE");
		map.put("gateway", "quickPaysubmit");
		map.put("responseCode", "00");
		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yl.payinterface.core.handler.QuickPayHandler#sale(java.util.Map)
	 */
	@Override
	public Map<String, String> sale(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yl.payinterface.core.handler.QuickPayHandler#query(java.util.Map)
	 */
	@Override
	public Map<String, String> query(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		SortedMap<String, String> map = new TreeMap<>();
		map.put("version", "1");
		map.put("agent", "5f02e571-40fa-4367-bfe6-15678d804a01");
		map.put("mobile", "18510412233");
		map.put("debit", "6214680033323591");
		map.put("name", "孙勃洋");
		map.put("idno", "23010719910228247X");
		map.put("credit", "6221558812340000");
		map.put("cvn", "123");
		map.put("expiry", "1123");
		map.put("out_order_id", System.currentTimeMillis() + "");
		map.put("amount", "1000");
		map.put("front_url", "http://www.sunboyang.com:8080/myshop/complete_get");
		map.put("sumer_fee", "0.5");
		map.put("sumer_amt", "200");
		map.put("noise", CommonsUtil.getNonceStr());
		map.put("sign", YinSheng584001Utils.createSign(map, "536a3a174c4c9d448aa944292a730be68a01d8e1"));
	}

	/* (non-Javadoc)
	 * @see com.yl.payinterface.core.handler.QuickPayOpenCardHandler#sendOpenCardVerifyCode(java.util.Map)
	 */
	@Override
	public QuickPayOpenCardResponseBean sendOpenCardVerifyCode(Map<String, String> params) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.yl.payinterface.core.handler.QuickPayOpenCardHandler#openCardInfo(java.util.Map)
	 */
	@Override
	public QuickPayOpenCardResponseBean openCardInfo(Map<String, String> params) {
		// TODO Auto-generated method stub
		return null;
	}

}
