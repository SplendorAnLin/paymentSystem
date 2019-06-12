package com.yl.dpay.core.task;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.web.spring.ApplicationContextUtils;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.dpay.core.Utils.Base64Utils;
import com.yl.dpay.core.Utils.RSAUtils;
import com.yl.dpay.core.bean.NotifyInfo;
import com.yl.dpay.core.entity.CustomerReqInfo;
import com.yl.dpay.core.entity.Request;
import com.yl.dpay.core.enums.NotifyStatus;
import com.yl.dpay.core.enums.RequestStatus;
import com.yl.dpay.core.service.CustomerReqInfoService;
import com.yl.dpay.core.service.RequestService;
import com.yl.dpay.core.service.ServiceConfigService;
import com.yl.dpay.hessian.enums.DpayExceptionEnum;

/**
 * 代付接口通知定时任务
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月16日
 * @version V1.0.0
 */
public class InterfaceNotifyTask {

	private static final Logger log = LoggerFactory.getLogger(InterfaceNotifyTask.class);
	private static final int NOTIFY_COUNT = 10;
	private static final int NUMS = 1000;
	private RequestService requestService;
	private CustomerReqInfoService customerReqInfoService;
	private CustomerInterface customerInterface;
	private ServiceConfigService serviceConfigService;

	public void execute() {
		log.info("notifyTask start!");
		try {
			requestService = (RequestService) ApplicationContextUtils.getApplicationContext().getBean("requestService");
			customerReqInfoService = (CustomerReqInfoService) ApplicationContextUtils.getApplicationContext().getBean("customerReqInfoService");
			customerInterface = (CustomerInterface) ApplicationContextUtils.getApplicationContext().getBean("customerInterface");
			serviceConfigService= (ServiceConfigService) ApplicationContextUtils.getApplicationContext().getBean("serviceConfigService");
			List<Request> requests = requestService.findWaitNotifyRequest(NOTIFY_COUNT, NUMS);
			for (Request request : requests) {

				if (!checkTime(request.getNotifyTime(), request.getNotifyCount())) {
					continue;
				}

				boolean flag = false;
				try {
					// 组装通知参数
					CustomerReqInfo customerReqInfo = customerReqInfoService.findByCutomerOrderCode(request.getOwnerId(), request.getRequestNo(), "TRADE");
					NotifyInfo notifyInfo = new NotifyInfo();
					notifyInfo.setCustomerOrderId(request.getRequestNo());
					notifyInfo.setDpayOrderId(request.getFlowNo());
					notifyInfo.setOrderTime(request.getCompleteDate());
					if (request.getStatus() == RequestStatus.SUCCESS) {
						notifyInfo.setResponseCode(DpayExceptionEnum.REMITSUCCESS.getCode());
						notifyInfo.setResponseMsg(DpayExceptionEnum.REMITSUCCESS.getMessage());
					} else {
						String errMsg = request.getCompleteMsg();
						if (StringUtils.isNotBlank(errMsg)) {
							errMsg = "," + errMsg;
						}
						notifyInfo.setResponseCode(DpayExceptionEnum.REMITFAILED.getCode());
						notifyInfo.setResponseMsg(DpayExceptionEnum.REMITFAILED.getMessage() + errMsg);
					}
					notifyInfo.setResponseCode(DpayExceptionEnum.getConvertCode(notifyInfo.getResponseCode()));
					notifyInfo.setResponseMsg(DpayExceptionEnum.getConvertMsg(notifyInfo.getResponseCode()));
					notifyInfo.setAmount(Double.toString(request.getAmount()));
					notifyInfo.setFee(Double.toString(request.getFee()));
					log.info("商户号为:{},订单号为:{}的代付通知信息:{}", request.getOwnerId(),request.getFlowNo(), notifyInfo);

					// 查密钥
					String password = serviceConfigService.find(request.getOwnerId()).getPrivateKey();
//					String password = customerInterface.getCustomerKey(request.getOwnerId(), KeyType.RSA).getPrivateKey();
					String encryptResult = Base64Utils.encode(RSAUtils.encryptByPrivateKey(JsonUtils.toJsonString(notifyInfo).getBytes(), password));
					Map<String,String> notifyParams = new HashMap<String, String>();
					notifyParams.put("cutomerNo", request.getOwnerId());
					notifyParams.put("versionCode", "1.0");
					notifyParams.put("customerParam", customerReqInfo.getCustomerParam());
					notifyParams.put("cipherText", encryptResult);
					log.info("商户号为:{},订单号为:{}的代付通知参数:{}", request.getOwnerId(),request.getFlowNo(), notifyParams);

					// 通知
					String resMsg = sendReq(JsonUtils.toJsonString(notifyParams), customerReqInfo.getNotifyUrl());
					
					log.info("商户号为:{},订单号为:{}的代付通知响应参数:{}", request.getOwnerId(),request.getFlowNo(), resMsg);
					if (resMsg != null && resMsg.equals("SUCCESS")) {
						flag = true;
					}
				} catch (Exception e) {
					log.error("商户号为:{},订单号为:{}通知异常, 异常信息:{}", request.getOwnerId(),request.getFlowNo(), e);
				}

				// 更新通知信息
				request.setNotifyCount(request.getNotifyCount() + 1);
				request.setNotifyTime(new Date());
				if (flag) {
					request.setNotifyStatus(NotifyStatus.SUCCESS);
				}
				try {
					requestService.updateNotify(request);
				} catch (Exception e) {
					log.error("商户号为:{},订单号为:{}通知异常, 异常信息:{}", request.getOwnerId(),request.getFlowNo(), e);
				}
			}
		} catch (Exception e) {
			log.error("", e);
		}
		log.info("notifyTask end!");
	}

	public static String sendReq(String reqJson, String url) throws Exception {

		java.net.HttpURLConnection urlConnection = null;
		BufferedOutputStream out;
		StringBuffer respContent = new StringBuffer();

		java.net.URL aURL = new java.net.URL(url);
		urlConnection = (java.net.HttpURLConnection) aURL.openConnection();

		urlConnection.setRequestMethod("POST");

		urlConnection.setDoInput(true);
		urlConnection.setDoOutput(true);
		urlConnection.setUseCaches(false);

		urlConnection.setRequestProperty("Connection", "close");
		urlConnection.setRequestProperty("Content-Length", String.valueOf(reqJson.getBytes("UTF-8").length));
		urlConnection.setRequestProperty("Content-Type", "text/html");

		urlConnection.setConnectTimeout(60000);
		urlConnection.setReadTimeout(60000);

		out = new BufferedOutputStream(urlConnection.getOutputStream());

		out.write(reqJson.getBytes("UTF-8"));
		out.flush();
		out.close();
		int responseCode = urlConnection.getResponseCode();

		if (responseCode != 200) {
			throw new Exception("代付接口通知 请求失败 : " +responseCode);
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
		String line;

		while ((line = reader.readLine()) != null) {
			respContent.append(line);
		}

		urlConnection.disconnect();
		return respContent.toString();
	}
	
	public static boolean checkTime(Date date,int counts){
		if(counts == 0 || date == null){
			return true;
		}
		if(times.get(counts) == null){
			return false;
		}
		if(new Date().getTime() - date.getTime() > times.get(counts).longValue()){
			return true;
		}
		return false;
	}
	
	public static Map<Integer,Integer> times = new HashMap<Integer, Integer>();
	static{
		times.put(1, 5000);
		times.put(2, 60000);
		times.put(3, 120000);
		times.put(4, 240000);
		times.put(5, 480000);
		times.put(6, 960000);
		times.put(7, 960000);
		times.put(8, 960000);
		times.put(9, 960000);
	}
}
