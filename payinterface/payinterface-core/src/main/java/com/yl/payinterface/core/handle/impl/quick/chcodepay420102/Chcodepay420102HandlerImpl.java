package com.yl.payinterface.core.handle.impl.quick.chcodepay420102;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.cache.util.CacheUtils;
import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.boss.api.bean.TransCardBean;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.handler.BindCardHandler;
import com.yl.payinterface.core.handler.ChannelReverseHandler;
import com.yl.payinterface.core.handler.QuickPayFilingHandler;
import com.yl.payinterface.core.handler.QuickPayHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.model.QuickPayFilingInfo;
import com.yl.payinterface.core.service.BindCardInfoService;
import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.service.QuickPayFeeService;
import com.yl.payinterface.core.service.QuickPayFilingInfoService;
import com.yl.payinterface.core.utils.AESUtils;
import com.yl.payinterface.core.utils.HttpUtils;
import com.yl.payinterface.core.utils.MD5Util;


@Service("quickChcodepay420102Handler")
public class Chcodepay420102HandlerImpl implements BindCardHandler, QuickPayHandler, ChannelReverseHandler  {

	private static Logger logger = LoggerFactory.getLogger(Chcodepay420102HandlerImpl.class);
	
	private final static String interfaceInfoCode = "QUICKPAY_ChcodePay-420102";
	
	@Resource
    private QuickPayFilingInfoService quickPayFilingInfoService;

    @Resource
    private CustomerInterface customerInterface;

    @Resource
    private QuickPayFeeService quickPayFeeService;

    @Resource
    private QuickPayFilingHandler chcodepay420102FilingHandler;

    @Resource
    private InterfaceRequestService interfaceRequestService;

    @Resource
    private BindCardInfoService bindCardInfoService;
    

	@Override
	public Map<String, String> sendVerifyCode(Map<String, String> params) {
		return null;
	}

	@Override
	public Map<String, String> authPay(Map<String, String> map) {
		return sale(map);
	}

	@Override
	public Map<String, String> sale(Map<String, String> map) {

		Map<String, String> retMap = new HashMap<>();

        InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(map.get("interfaceRequestID"));
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {
        });
        
        try {
			
            //用来存放结算卡信息
            SortedMap<Object, Object> cardMap = new TreeMap<Object, Object>();

            // 获取结算卡信息
            quickPayFeeService.getSettleInfo(cardMap, interfaceRequest.getOwnerID(), map.get("cardNo"));

            // 获取报备信息
            QuickPayFilingInfo filingInfo = quickPayFilingInfoService.find(cardMap.get("cardNo").toString(), interfaceInfoCode);
            
            
            
            // 组装支付请求报文
            Map<String, String> reqMap = new TreeMap<String, String>();
            
            reqMap.put("source_no", properties.getProperty("source_no"));
            reqMap.put("merchant_no", filingInfo.getChannelCustomerCode());
            reqMap.put("trade_amount", new DecimalFormat("#.##").format(Double.valueOf(map.get("amount"))));
            reqMap.put("order_id", interfaceRequest.getInterfaceRequestID());
            reqMap.put("pay_type", "08");
            reqMap.put("goods_name", interfaceRequest.getInterfaceRequestID());
            reqMap.put("settle_type", "100");
            reqMap.put("trans_type", "");
            reqMap.put("step_no", "");
            reqMap.put("open_id", "");
            reqMap.put("pre_order_id", "");
            reqMap.put("sms_code", "");
            reqMap.put("card_uuid", "");
            reqMap.put("front_url", properties.getProperty("front_url"));
            reqMap.put("return_url", properties.getProperty("return_url"));
            reqMap.put("id_number", filingInfo.getIdCardNo());
            reqMap.put("phone_no", filingInfo.getPhone());
            reqMap.put("name", filingInfo.getName());
            reqMap.put("pay_card_no", map.get("cardNo"));
            
            
            logger.info("邦呈快捷支付-统一下单 接口请求号：[{}]，请求原参数：[{}]", interfaceRequest.getInterfaceRequestID(), JsonUtils.toJsonString(reqMap));
            
            // 请求
            String respInfo = HttpUtils.sendReq(properties.getProperty("doPay"), AESUtils.encrypt(getSign(reqMap, properties.getProperty("sign_key")), properties.getProperty("aes_key")), "POST");
            
            logger.info("邦呈快捷支付-统一下单 接口请求号：[{}]，响应信息：[{}]", interfaceRequest.getInterfaceRequestID(), JsonUtils.toJsonString(respInfo));
            
            Map<String, String> respMap = JsonUtils.toObject(respInfo, new TypeReference<Map<String, String>>() {
            });

            retMap.put("interfaceRequestID", map.get("interfaceRequestID"));
            if(respMap.get("response_code").toString().equals("0000")) {
            	
            	retMap.put("responseCode", "00");
            	retMap.put("payPage", "INTERFACE");
            	retMap.put("gateway", "htmlSubmit");
            	retMap.put("html", JsonUtils.toObject(respMap.get("response_body").toString(), new TypeReference<Map<String, String>>() {
                }).get("qrcode_url"));

            }else {

            	retMap.put("responseCode", "9999");
//            	retMap.put("responseMsg", respMap.get("response_desc"));
            	
            }
        	
		} catch (Exception e) {

			logger.error("邦呈快捷支付-统一下单 接口请求号异常 接口请求号:[{}]，异常信息：[{}]", map.get("interfaceRequestID"), e);
			
		}finally {
			
			CacheUtils.del(interfaceInfoCode + "-" + interfaceRequest.getInterfaceRequestID());
            logger.info("邦呈快捷支付-统一下单 接口请求号：[{}]，解锁", interfaceRequest.getInterfaceRequestID());
		}
		
		return retMap;
	}

	@Override
	public Map<String, String> query(Map<String, String> map) {
		return reverse(map);
	}

	@Override
	public Map<String, String> bindCard(Map<String, String> params) {
		Map<String, String> retMap = new HashMap<>();

        String responseCode = "9999";
        String remitFee = params.get("remitFee");
        String quickPayFee = params.get("quickPayFee");

        if (StringUtils.isBlank(remitFee)) {
            remitFee = quickPayFeeService.getRemitFee(params.get("partner"), Double.valueOf(params.get("amount")));
        }
        if (StringUtils.isBlank(quickPayFee)) {
            quickPayFee = quickPayFeeService.getQuickPayFee(params.get("partner"));
        }


        //处理报备
        TransCardBean transCardBean = customerInterface.findByCustAndAccNo(params.get("partner"), params.get("cardNo"));

        QuickPayFilingInfo filingInfo = quickPayFilingInfoService.find(transCardBean.getAccountNo(), params.get("interfaceCode"));

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
            Map<String, String> rtMap = chcodepay420102FilingHandler.filing(params);
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
                Map<String, String> rtMap = chcodepay420102FilingHandler.filing(params);
                if ("SUCCESS".equals(rtMap.get("status"))) {
                    responseCode = "00";
                }
            } else {
                responseCode = "00";
            }
        }

        retMap.put("smsCodeType", "NO_SMS_CODE_BIND");
        retMap.put("responseCode", responseCode);
        retMap.put("result", responseCode);
        retMap.put("gateway", "quickPay/openCard");

        return retMap;
	}

	@Override
	public Map<String, String> queryBindCard(Map<String, String> params) {
        Map<String, String> retMap = new HashMap<>();

        String responseCode = "9999";
        String remitFee = params.get("remitFee");
        String quickPayFee = params.get("quickPayFee");

        if (StringUtils.isBlank(remitFee)) {
            remitFee = quickPayFeeService.getRemitFee(params.get("partner"), Double.valueOf(params.get("amount")));
        }
        if (StringUtils.isBlank(quickPayFee)) {
            quickPayFee = quickPayFeeService.getQuickPayFee(params.get("partner"));
        }


        //处理报备
        TransCardBean transCardBean = customerInterface.findByCustAndAccNo(params.get("partner"), params.get("cardNo"));

        QuickPayFilingInfo filingInfo = quickPayFilingInfoService.find(transCardBean.getAccountNo(), params.get("interfaceCode"));

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
            Map<String, String> rtMap = chcodepay420102FilingHandler.filing(params);
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
                Map<String, String> rtMap = chcodepay420102FilingHandler.filing(params);
                if ("SUCCESS".equals(rtMap.get("status"))) {
                    responseCode = "00";
                }
            } else {
                responseCode = "00";
            }
        }

        retMap.put("responseCode", responseCode);

        return retMap;
	}
	
	@Override
	public Map<String, String> reverse(Map<String, String> params) {
		
		Map<String, String> rtMap = new HashMap<String, String>();
		
		InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(params.get("interfaceRequestID"));
		
		Properties properties = JsonUtils.toObject(params.get("tradeConfigs"), new TypeReference<Properties>() {
		});
		
		Map<String, String> reqMap = new TreeMap<String, String>();
		reqMap.put("source_no", properties.getProperty("source_no"));
		reqMap.put("merchant_no", "");
		reqMap.put("order_id", params.get("interfaceRequestID"));
		reqMap.put("tracking_no", "");
		reqMap.put("trans_date", new SimpleDateFormat("yyyyMMdd").format(interfaceRequest.getRequestTime()));

		try {

			logger.info("邦呈快捷支付-订单查询 接口请求号：[{}]，请求原参数：[{}]", interfaceInfoCode, JsonUtils.toJsonString(reqMap));

			// 请求
			String respInfo = HttpUtils.sendReq(properties.getProperty("orderQueryUrl"), AESUtils.encrypt(getSign(reqMap, properties.getProperty("sign_key")), properties.getProperty("aes_key")), "POST");

			logger.info("邦呈快捷支付-订单查询 接口请求号：[{}]，响应信息：[{}]", interfaceInfoCode, respInfo);

			Map<String, String> respMap = JsonUtils.toObject(respInfo, new TypeReference<Map<String, String>>() {
			});

			if(respMap.get("response_code").toString().equals("0000")) {

				Map<String, String> bodyMap = JsonUtils.toObject(respMap.get("response_body").toString(), new TypeReference<Map<String, String>>() {
				});

				if(bodyMap.get("trade_status").equals("0000")) {
					rtMap.put("resCode", bodyMap.get("trade_status"));
					rtMap.put("resMsg", "付款成功");
					rtMap.put("tranStat", "SUCCESS");
					rtMap.put("requestNo", params.get("order_id"));
					rtMap.put("amount", String.valueOf(AmountUtils.divide(Double.valueOf(bodyMap.get("trade_amount")), 100d, 2, RoundingMode.HALF_UP)));
					rtMap.put("compType", BusinessCompleteType.AUTO_REPAIR.name());
					rtMap.put("interfaceOrderID", bodyMap.get("tracking_no"));
				}else {
					rtMap.put("requestNo", params.get("interfaceRequestID"));
					rtMap.put("amount", bodyMap.get("trade_amount"));
					rtMap.put("tranStat", "UNKNOWN");
				}

			}else {

            	rtMap.put("requestNo", params.get("interfaceRequestID"));
            	rtMap.put("amount", params.get("amount"));
            	rtMap.put("tranStat", "UNKNOWN");

            }

		} catch (Exception e){
            logger.info("邦呈快捷支付-订单查询 失败 接口请求号：[{}]，错误信息：[{}]", interfaceInfoCode, e);
        }
		return rtMap;
	}
	
    /**
     * 签名，并处理请求参数
     * @param params
     * @return 请求报文Json
     */
    private String getSign(Map<String, String> params, String sign_key) {
        Map<String, String> sortMap = new TreeMap<String, String>();

        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> s : new TreeMap<String, String>(params).entrySet()) {
            if (!StringUtils.isBlank(s.getValue())) {// 过滤空值
                sortMap.put(s.getKey(), s.getValue());

                builder.append(s.getKey()).append("=").append(s.getValue()).append("&");
            }
        }

        if (!sortMap.isEmpty()) {
            builder.deleteCharAt(builder.length() - 1);
        }
        builder.append("&").append("key").append("=").append(sign_key);

        sortMap.put("sign", MD5Util.MD5Encode(builder.toString(), "UTF-8").toUpperCase());
        
        logger.info("邦呈快捷支付 接口编号：[{}]，请求签名参数：[{}]", interfaceInfoCode, JsonUtils.toJsonString(sortMap));
        return JsonUtils.toJsonString(sortMap);
    }

}
