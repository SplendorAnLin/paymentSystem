package com.yl.client.front.handler.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.security.DigestUtils;
import com.yl.boss.api.bean.Customer;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.boss.api.interfaces.ShareProfitInterface;
import com.yl.client.front.common.AppRuntimeException;
import com.yl.client.front.common.CodeBuilder;
import com.yl.client.front.common.DictUtils;
import com.yl.client.front.common.HttpUtils;
import com.yl.client.front.enums.AppExceptionEnum;
import com.yl.client.front.handler.AppTradeHandler;
import com.yl.client.front.service.impl.FeeInfoServiceImpl;
import com.yl.online.trade.hessian.OnlineTradeQueryHessianService;
import com.yl.pay.pos.api.interfaces.PosOrderHessianService;

import net.sf.json.JSONObject;

@Service("onlineHandLer")
@PropertySource("classpath:/serverHost.properties")
public class AppTradeHandlerImpl implements AppTradeHandler{
	
	private static Logger log = LoggerFactory.getLogger(AppTradeHandler.class);
	
	private static final String API_CODE = "YL-PAY";
	private static final String INPUT_CHARSET = "UTF-8";
	private static final String SIGN_TYPE = "MD5";
	
	@Resource
	OnlineTradeQueryHessianService onlineTradeQueryHessianService;

	@Resource
	CustomerInterface customerInterface;
	
	@Value("${orderComplete}")
	private String orderComplete;
	
	@Value("${notifyUrl}")
	private String notifyUrl;
	
	@Value("${payUrl}")
	private String payUrl;
	
	@Resource
	ShareProfitInterface shareProfitInterface;
	@Resource
	PosOrderHessianService posOrder;
	
	@Resource
	FeeInfoServiceImpl feeInfoService;
	
	@Override
	public Map<String, Object> today(Map<String, Object> param) throws AppRuntimeException{
		try {
			List<Map<String, String>> resule = onlineTradeQueryHessianService.orderAmountSumByDay(param.get("orderTime").toString(), param.get("orderTime").toString(), param.get("customerNo").toString());
			double amount = 0;
			int count = 0;
			String online = JsonUtils.toJsonString(resule.get(0));
			JSONObject jsonObject = JSONObject.fromObject(online);
			if (jsonObject.getInt("counts") != 0) {
				amount = jsonObject.getDouble("amount");
				count = jsonObject.getInt("counts");
			}
			Map<String, Object> map = new HashMap<>();
			map.put("amount", amount);
			map.put("count", count);
			return map;
		} catch (Exception e) {
			log.error("当日交易金额和笔数查询失败，商户编号：{},错误原因：{}",param.get("customerNo"),e);
			throw new AppRuntimeException(AppExceptionEnum.TODAY.getCode(),AppExceptionEnum.TODAY.getMessage());
		}
	}

	@Override
	public Map<String, Object> recharge(Map<String, Object> param) throws AppRuntimeException {
		try {
			String key = customerInterface.getCustomerMD5Key(param.get("customerNo").toString());
			return pay(param.get("customerNo").toString(), key, Double.parseDouble(param.get("amount").toString()), param.get("payType").toString());
		} catch (Exception e) {
			log.error("充值请求发送失败，商户号：{}，错误原因：{}",param.get("customerNo"),e);
			throw new AppRuntimeException(AppExceptionEnum.APP_RECHARGE.getCode(),AppExceptionEnum.APP_RECHARGE.getMessage());
		}
	}
	
	@Override
	public Map<String, Object> barcodePay(Map<String, Object> param) throws Exception {
		try {
			String key = customerInterface.getCustomerMD5Key(param.get("customerNo").toString());
			return authCodePay(param.get("customerNo").toString(), key, Double.parseDouble(param.get("amount").toString()), param.get("authCode").toString());
		} catch (Exception e) {
			log.error("微信条码支付请求发起失败，商户号：{}，错误原因：{}",param.get("customerNo"),e);
			throw new AppRuntimeException(AppExceptionEnum.BARCODEPAY.getCode(),AppExceptionEnum.BARCODEPAY.getMessage());
		}
	}
	
	public Map<String, Object> authCodePay(String partnerNo, String key, double amount, String authCode) throws AppRuntimeException {
		String sign = "";
		SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmmss");
		String orderId = CodeBuilder.build("CO", "yyyyMMdd");
		Map<String, String> params = new HashMap<>();
		params.put("apiCode", API_CODE);
		params.put("inputCharset", INPUT_CHARSET);
		params.put("partner", partnerNo);
		params.put("outOrderId", orderId);
		params.put("amount", String.valueOf(amount));
		params.put("payType", "WXMICROPAY");
		params.put("authCode", authCode);
		params.put("retryFalg", "TRUE");
		params.put("submitTime", time.format(new Date()));
		params.put("timeout", "1D");
		params.put("product", "APP在线交易");
		params.put("redirectUrl", "http://www.baidu.com/");
		params.put("notifyUrl", notifyUrl);
		params.put("accMode", "GATEWAY");
		params.put("wxNativeType", "PAGE");
		params.put("signType", SIGN_TYPE);
		ArrayList<String> paramNames = new ArrayList<>(params.keySet());
		Collections.sort(paramNames);
		Iterator<String> iterator = paramNames.iterator();
		StringBuffer signSource = new StringBuffer(50);
		while (iterator.hasNext()) {
			String paramName = iterator.next();
			if (StringUtils.notBlank(String.valueOf(params.get(paramName)))) {
				signSource.append(paramName).append("=").append(params.get(paramName));
				if (iterator.hasNext()) signSource.append("&");
			}
		}
		signSource.append(key);
		try {
			sign = DigestUtils.md5DigestAsHex(signSource.toString().getBytes(INPUT_CHARSET));
			params.put("sign", sign);
			String resStr = HttpUtils.sendReq(JsonUtils.toJsonString(params), payUrl, INPUT_CHARSET);
			params = JsonUtils.toObject(resStr, new TypeReference<Map<String, String>>(){});
			Map<String, Object> map = new HashMap<>();
			map.put("responseMsg", params.get("responseMsg"));
			map.put("orderCode", params.get("orderCode"));
			return map;
		} catch (Exception e) {
			log.error("交易发起失败，商户编号：{}，错误原因：{}",partnerNo,e);
			throw new AppRuntimeException(AppExceptionEnum.BARCODEPAY.getCode(),AppExceptionEnum.BARCODEPAY.getMessage());
		}
	}
	
	public Map<String, Object> pay(String partnerNo,String key, double amount, String payType) throws AppRuntimeException {
		String sign = "";
		String orderId = CodeBuilder.build("CO", "yyyyMMdd");
		Map<String, String> params = new HashMap<>();
		params.put("apiCode", API_CODE);
		params.put("inputCharset", INPUT_CHARSET);
		params.put("partner", partnerNo);
		params.put("outOrderId", orderId);
		params.put("amount", String.valueOf(amount));
		params.put("payType", payType);
		params.put("notifyUrl", notifyUrl);
		params.put("signType", SIGN_TYPE);
		params.put("product", "APP在线交易");
		ArrayList<String> paramNames = new ArrayList<>(params.keySet());
		Collections.sort(paramNames);
		Iterator<String> iterator = paramNames.iterator();
		StringBuffer signSource = new StringBuffer(50);
		while (iterator.hasNext()) {
			String paramName = iterator.next();
			if (StringUtils.notBlank(String.valueOf(params.get(paramName)))) {
				signSource.append(paramName).append("=").append(params.get(paramName));
				if (iterator.hasNext()) signSource.append("&");
			}
		}
		signSource.append(key);
		try {
			sign = DigestUtils.md5DigestAsHex(signSource.toString().getBytes(INPUT_CHARSET));
			params.put("sign", sign);
			String resStr = HttpUtils.sendReq(JsonUtils.toJsonString(params), payUrl, INPUT_CHARSET);
			params = JsonUtils.toObject(resStr, new TypeReference<Map<String, String>>(){});
			if (params.get("responseCode").equals("0000")) {
				Map<String, Object> map = new HashMap<>();
				map.put("orderCode", params.get("orderCode"));
				map.put("qrCodeUrl", params.get("qrCodeUrl"));
				return map;
			}
			if (params.get("responseCode").equals("9999")) {
				throw new AppRuntimeException(AppExceptionEnum.PAY.getCode(),AppExceptionEnum.PAY.getMessage());
			}
			if (params.get("responseCode").equals("0001")) {
				throw new AppRuntimeException(AppExceptionEnum.DOESNOTEXIST.getCode(),AppExceptionEnum.DOESNOTEXIST.getMessage());
			}
			if (params.get("responseCode").equals("0002")) {
				throw new AppRuntimeException(AppExceptionEnum.PARAMETERERROR.getCode(),AppExceptionEnum.PARAMETERERROR.getMessage());
			}
			if (params.get("responseCode").equals("0003")) {
				throw new AppRuntimeException(AppExceptionEnum.SIGNATUREERROR.getCode(),AppExceptionEnum.SIGNATUREERROR.getMessage());
			}
			if (params.get("responseCode").equals("0004")) {
				throw new AppRuntimeException(AppExceptionEnum.PAYMENTWRONG.getCode(),AppExceptionEnum.PAYMENTWRONG.getMessage());
			}
			if (params.get("responseCode").equals("0006")) {
				throw new AppRuntimeException(AppExceptionEnum.BUSINESSERROR.getCode(),AppExceptionEnum.BUSINESSERROR.getMessage());
			}
			if (params.get("responseCode").equals("0007")) {
				throw new AppRuntimeException(AppExceptionEnum.BUSINESSSTATUSISABNORMAL.getCode(),AppExceptionEnum.BUSINESSSTATUSISABNORMAL.getMessage());
			}
			if (params.get("responseCode").equals("0008")) {
				throw new AppRuntimeException(AppExceptionEnum.BUSINESSDOESNOTOPENTHISBUSINESS.getCode(),AppExceptionEnum.BUSINESSDOESNOTOPENTHISBUSINESS.getMessage());
			}
			if (params.get("responseCode").equals("0009")) {
				throw new AppRuntimeException(AppExceptionEnum.THEKEYDOESNOTEXIST.getCode(),AppExceptionEnum.THEKEYDOESNOTEXIST.getMessage());
			}
			if (params.get("responseCode").equals("0201")) {
				throw new AppRuntimeException(AppExceptionEnum.TIMEERROR.getCode(),AppExceptionEnum.TIMEERROR.getMessage());
			}
			if (params.get("responseCode").equals("0202")) {
				throw new AppRuntimeException(AppExceptionEnum.AMOUNTCAP.getCode(),AppExceptionEnum.AMOUNTCAP.getMessage());
			}
			if (params.get("responseCode").equals("0203")) {
				throw new AppRuntimeException(AppExceptionEnum.SINGLEAMOUNTERROR.getCode(),AppExceptionEnum.SINGLEAMOUNTERROR.getMessage());
			}
			if (params.get("responseCode").equals("0204")) {
				throw new AppRuntimeException(AppExceptionEnum.AMOUNTCAPDAY.getCode(),AppExceptionEnum.AMOUNTCAPDAY.getMessage());
			}
			if (params.get("responseCode").equals("0205")) {
				throw new AppRuntimeException(AppExceptionEnum.AMOUNTERROR.getCode(),AppExceptionEnum.AMOUNTERROR.getMessage());
			}
			if (params.get("responseCode").equals("0207")) {
				throw new AppRuntimeException(AppExceptionEnum.THEROUTEERROR.getCode(),AppExceptionEnum.THEROUTEERROR.getMessage());
			}
			return null;
		} catch (Exception e) {
			log.error("交易发起失败，商户编号：{}，错误原因：{}",partnerNo,e);
			throw new AppRuntimeException(AppExceptionEnum.PAY.getCode(),AppExceptionEnum.PAY.getMessage());
		}
	}
	
	@Override
	public Map<String, Object> selectTradeOrder(Map<String,Object> params) throws AppRuntimeException {
		int pageSize=10;
		int currentPage=1;
		try {
			SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String startSuccessPayTime=params.get("startSuccessPayTime").toString()+" 00:00:00";
			String endSuccessPayTime=params.get("endSuccessPayTime").toString()+" 23:59:59";
			if(params.get("pageSize")!=null&&StringUtils.notBlank(params.get("pageSize")+"")){
				pageSize=Integer.parseInt(params.get("pageSize").toString());
			}
			if(params.get("currentPage")!=null&&StringUtils.notBlank(params.get("currentPage")+"")){
				currentPage=Integer.parseInt(params.get("currentPage").toString());
			}
			String ownerId="",productType=null;
			if (params.get("customerNo")!=null&&StringUtils.notBlank(params.get("customerNo").toString())) {
				ownerId=params.get("customerNo").toString();
			}else {
				throw new AppRuntimeException(AppExceptionEnum.PARAMSERR.getCode(),AppExceptionEnum.PARAMSERR.getMessage());
			}
			if (params.get("payType")!=null&&!"".equals(params.get("payType"))&&!"ALL".equals(params.get("payType").toString())) {
				productType=params.get("payType").toString();
			}
			Map<String, Object> map=new HashMap<String, Object>();
			List<Map<String, Object>> order=shareProfitInterface.findOnlineSales(ownerId, currentPage, pageSize, 
					productType, time.parse(startSuccessPayTime),  time.parse(endSuccessPayTime));
			//onlineTradeQueryHessianService.selectTradeOrder(params);
			List<Map<String, Object>> maps=JsonUtils.toObject(shareProfitInterface.sumOnlineSales(ownerId,time.parse(startSuccessPayTime),  time.parse(endSuccessPayTime)), new  TypeReference<List<Map<String, Object>>>(){});
			List<Map<String, Object>> feeList=feeInfoService.getCustomerFeeIn(ownerId);	
			if (maps!=null&&maps.size()>0) {
				for (int i = 0; i < maps.size(); i++) {
					Iterator<Map<String, Object>> it = feeList.iterator();
					while(it.hasNext()) {
						Map<String, Object> temp=it.next();
						if(!temp.get("productType").equals(maps.get(i).get("payType"))){
						}
						else{
							maps.get(i).put("payTypeId",temp.get("id"));
							it.remove();
						}
					}
				}
			}
			if (maps==null) {
				maps=new ArrayList<>();
			}
			for(int i=0;i<feeList.size();i++){
				Map<String, Object> mapset=new HashMap<String, Object>();
				mapset.put("count", 0.00);
				mapset.put("sum", 0.00);	
				mapset.put("payType", feeList.get(i).get("productType"));
				mapset.put("PayTypeId",feeList.get(i).get("id"));
				maps.add(mapset);
			}
			map.put("resultOrderSum", 
					DictUtils.listOfDict(DictUtils.listOfDict(maps,"ONLINE_PAYTYPE", "payType"),"PAYTYPE_IMG","payType","_IMG"));
			if (order!=null&&order.size()>0) {
				map.put("resultOrder", 
					DictUtils.listOfDict(DictUtils.listOfDict(order,"ONLINE_PAYTYPE", "PayType"),"PAYTYPE_IMG","PayType","_IMG"));
			}else {
				map.put("resultOrder", null);
			}
			
			return map;
		} catch (Exception e) {	
			log.error("交易订单查询失败！错误原因：{}",e);
			throw new AppRuntimeException(AppExceptionEnum.RETURNEMPTY.getCode(),AppExceptionEnum.RETURNEMPTY.getMessage());
		}
	}

	@Override
	public Map<String, Object> selectTradeOrderDetailed(Map<String, Object> params) throws AppRuntimeException {
		Map<String, Object> map=new HashMap<String, Object>();
		Object payType=params.get("payType");
		if (payType!=null) {
			Map<String, Object> maps=null;
			if (payType.toString().equals("POS")) {
				JSONObject order=JSONObject.fromObject(posOrder.findOrderByCode(params.get("code").toString()));
				map.put("receiver", order.get("customerNo"));
				map.put("code", order.get("externalId"));
				map.put("payType_CN", "POS");
				map.put("payType", "POS");
				map.put("status", order.get("status"));
				map.put("status_CN", "成功");
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				map.put("successTime",formatter.format(new Date(Long.valueOf(order.get("completeTime").toString()))));
				map.put("orderTime",formatter.format(new Date(Long.valueOf(order.get("createTime").toString()))));
				map.put("requestNo", order.get("posRequestId"));
				map.put("amount", order.get("amount"));
				map.put("receiverFee", order.get("customerFee"));
				map.put("actualAmount", Double.valueOf(order.get("amount").toString())-Double.valueOf(order.get("customerFee").toString()));
				map.put("clearingStatus", order.get("creditStatus"));
				map.put("clearingStatus_CN", "清算成功");
			}else {
				maps=onlineTradeQueryHessianService.selectTradeOrderDetailed(params).get(0);
				map.putAll(DictUtils.mapOfDict(DictUtils.mapOfDict(DictUtils.mapOfDict(maps,"TRADE_ORDER_STATUS", "status"),"ONLINE_PAYTYPE","payType"),"LIQUIDATION_STATUS","clearingStatus"));
			}
			Customer cust=customerInterface.getCustomer(params.get("customerNo").toString());
			map.put("fullName", cust.getFullName());
			return map;
		}else {
			throw new AppRuntimeException(AppExceptionEnum.PARAMSERR.getCode(),AppExceptionEnum.PARAMSERR.getMessage());
		}
	}
}