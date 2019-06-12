package com.yl.payinterface.core.handle.impl.quick.ldz42010103;

import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

import com.lefu.commons.cache.util.CacheUtils;
import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.yl.boss.api.bean.TransCardBean;
import com.yl.boss.api.enums.CardAttr;
import com.yl.boss.api.enums.CardStatus;
import com.yl.boss.api.enums.CardType;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.payinterface.core.C;
import com.yl.payinterface.core.bean.QuickPayOpenCardResponseBean;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.enums.FeeType;
import com.yl.payinterface.core.exception.BusinessRuntimeException;
import com.yl.payinterface.core.handler.BindCardHandler;
import com.yl.payinterface.core.handler.QuickPayFilingHandler;
import com.yl.payinterface.core.handler.QuickPayHandler;
import com.yl.payinterface.core.handler.QuickPayOpenCardHandler;
import com.yl.payinterface.core.model.BindCardInfo;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.model.QuickPayFilingInfo;
import com.yl.payinterface.core.service.BindCardInfoService;
import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.service.QuickPayFeeService;
import com.yl.payinterface.core.service.QuickPayFilingInfoService;
import com.yl.payinterface.core.utils.Base64Utils;
import com.yl.payinterface.core.utils.CodeBuilder;
import com.yl.payinterface.core.utils.FeeUtils;
import com.yl.payinterface.core.utils.RSAUtils;


/**
 * 快捷支付D0，积分
 */
@Service("quickLDZ42010103Handler")
public class LDZ42010103HandlerImpl implements BindCardHandler, QuickPayHandler, QuickPayOpenCardHandler {

    private static Logger logger = LoggerFactory.getLogger(LDZ42010103HandlerImpl.class);

    private final static String interfaceInfoCode = "QUICKPAY_LDZ-42010103";

    @Resource
    private QuickPayFilingInfoService quickPayFilingInfoService;

    @Resource
    private CustomerInterface customerInterface;

    @Resource
    private QuickPayFeeService quickPayFeeService;

    @Resource
    private QuickPayFilingHandler lDZ42010103FilingHandler;

    @Resource
    private InterfaceRequestService interfaceRequestService;

    @Resource
    private BindCardInfoService bindCardInfoService;





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
            Map<String, String> rtMap = lDZ42010103FilingHandler.filing(params);
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
                Map<String, String> rtMap = lDZ42010103FilingHandler.filing(params);
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
            Map<String, String> rtMap = lDZ42010103FilingHandler.filing(params);
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
                Map<String, String> rtMap = lDZ42010103FilingHandler.filing(params);
                if ("SUCCESS".equals(rtMap.get("status"))) {
                    responseCode = "00";
                }
            } else {
                responseCode = "00";
            }
        }


        // 获取交易卡信息
        BindCardInfo bindCardInfo = bindCardInfoService.findEffective(params.get("cardNo"), interfaceInfoCode);

        if(bindCardInfo == null) {
            responseCode = "01";
        }


        retMap.put("responseCode", responseCode);

        return retMap;
    }

    @Override
    public Map<String, String> sendVerifyCode(Map<String, String> params) {

        Map<String, String> retMap = new HashMap<>();

        InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(params.get("interfaceRequestID"));
        Properties properties = JsonUtils.toObject(String.valueOf(params.get("tradeConfigs")), new TypeReference<Properties>() {
        });

        try {
            // 获取交易卡信息
            BindCardInfo bindCardInfo = bindCardInfoService.findEffective(params.get("cardNo"), interfaceRequest.getInterfaceInfoCode());

            //用来存放结算卡信息
            SortedMap<Object, Object> cardMap = new TreeMap<Object, Object>();

            // 获取结算卡信息
            quickPayFeeService.getSettleInfo(cardMap, interfaceRequest.getOwnerID(), params.get("cardNo"));

            QuickPayFilingInfo filingInfo2 = quickPayFilingInfoService.find(cardMap.get("cardNo").toString(), params.get("interfaceCode"));

            // 获取配置最低费率
            String minFee = properties.getProperty("minFee");


            // 计算费率
            String settleType = params.get("settleType");
            double fee = 0d;
         	if (C.SETTLE_TYPE_AMOUNT.equals(settleType)) {
         		String settleAmount = params.get("settleAmount");
         		logger.info("来逗阵 快捷支付D0-积分短信消费 接口请求号：{}，结算金额为：{}", interfaceRequest.getInterfaceRequestID(), settleAmount);
         		fee = AmountUtils.subtract(interfaceRequest.getAmount(), Double.valueOf(settleAmount));
         	} else {
         		String remitFee = params.get("remitFee");
         		String quickPayFee = params.get("quickPayFee");
         		
         		if (StringUtils.isBlank(remitFee)) {
         			remitFee = quickPayFeeService.getRemitFee(interfaceRequest.getOwnerID(), Double.valueOf(params.get("amount")));
         		}
         		if (StringUtils.isBlank(quickPayFee)) {
         			quickPayFee = quickPayFeeService.getQuickPayFee(interfaceRequest.getOwnerID());
         		}
         		logger.info("来逗阵 快捷支付D0-积分短信消费 接口请求号：{}，代付费率：{}，快捷费率：{}", interfaceRequest.getInterfaceRequestID(), remitFee, quickPayFee);
         		// 计算应扣手续费。
         		fee = FeeUtils.computeFee(Double.valueOf(params.get("amount")), FeeType.RATIO, Double.valueOf(quickPayFee));
         		fee = AmountUtils.add(Double.valueOf(remitFee), fee);
         		fee = AmountUtils.round(fee, 2, RoundingMode.HALF_UP);
         		if (Double.compare(fee, Double.valueOf(minFee)) < 0) {
         			fee = Double.valueOf(minFee);
         		}
         	}
         	logger.info("来逗阵 快捷支付D0-积分短信消费 接口请求号：{}，计算手续费为：{}", interfaceRequest.getInterfaceRequestID(), fee);


            // 组装请求参数
            Map<String, Object> reqMap = new LinkedHashMap<String, Object>();
            reqMap.put("orderId", interfaceRequest.getInterfaceRequestID().replace("-", ""));
            reqMap.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            reqMap.put("body", params.get("interfaceRequestID"));
            reqMap.put("token", bindCardInfo.getToken());
            reqMap.put("txnAmt", String.valueOf((int) AmountUtils.multiply(Double.valueOf(interfaceRequest.getAmount()), 100)));
            reqMap.put("userFee", String.valueOf((int) AmountUtils.multiply(fee, 100)));
            reqMap.put("insCode", filingInfo2.getChannelCustomerCode());
            reqMap.put("version", properties.getProperty("version"));

            logger.info("来逗阵 快捷支付D0-积分短信消费 接口请求号：[{}]，请求原参数：[{}]", interfaceRequest.getInterfaceRequestID(), JsonUtils.toJsonString(reqMap));

            Map<String, String> data = new LinkedHashMap<String, String>();
            data.put("data", Base64Utils.encode(RSAUtils.encryptByPublicKey(JsonUtils.toJsonString(reqMap).getBytes("UTF-8"), properties.getProperty("publicKey"))));
            data.put("url", properties.getProperty("SmsSpendUrl") + properties.getProperty("channelType") + "/" + properties.getProperty("appId"));

            logger.info("来逗阵 快捷支付D0-积分短信消费 接口请求号：[{}]，请求报文：[{}]", interfaceRequest.getInterfaceRequestID(), data);

            String respInfo = HttpClientUtils.send(HttpClientUtils.Method.POST, "http://util.bank-pay.com/postForWard", data, false, "UTF-8", 10000);

            logger.info("来逗阵 快捷支付D0-积分短信消费 接口请求号：[{}]，响应报文：[{}]", interfaceRequest.getInterfaceRequestID(), respInfo);

            Map<String, Object> respMap = JsonUtils.toObject(respInfo, new TypeReference<Map>() {});

            if(Boolean.valueOf(respMap.get("success").toString())) {
                Map<String, String> order = JsonUtils.toObject(JsonUtils.toJsonString(respMap.get("attributes")), new TypeReference<Map>() {
                });


                CacheUtils.setEx("QUICKPAY_LDZ-42010103-" + interfaceRequest.getInterfaceRequestID() + "-" + params.get("tradeOrderCode"), JsonUtils.toJsonString(order), 900);

                logger.info("来逗阵 快捷支付D0-积分短信消费 接口请求号：[{}]，加锁", interfaceRequest.getInterfaceRequestID());


                retMap.put("interfaceRequestID", interfaceRequest.getInterfaceRequestID());
                retMap.put("orderCode", interfaceRequest.getBussinessOrderID());
                retMap.put("payCardNo", params.get("cardNo"));
                retMap.put("settleCardNo", cardMap.get("cardNo").toString());
                retMap.put("payerName", cardMap.get("realName").toString());
                retMap.put("certNo", cardMap.get("identityCard").toString());
                retMap.put("phone", filingInfo2.getPhone());
                retMap.put("responseCode", "00");
                retMap.put("token", bindCardInfo.getToken());
                retMap.put("amount", Double.toString(interfaceRequest.getAmount()));
                retMap.put("interfaceCode", interfaceRequest.getInterfaceInfoCode());
                retMap.put("responseCode", "00");


                retMap.put("key", params.get("cardNo"));

                retMap.put("responseCode", "00");
                retMap.put("gateway", "quickPay");

            }else {
                retMap.put("responseCode", "2001");
                retMap.put("responseMessage", "短信验证码发送失败");
                retMap.put("gateway", "quickPay");
            }

            retMap.put("cardNo", params.get("cardNo"));
            retMap.put("interfaceInfoCode", interfaceRequest.getInterfaceInfoCode());


        } catch (Exception e) {
            if (e instanceof BusinessRuntimeException) {
                retMap.put("responseCode", e.getMessage().equals("2002") || e.getMessage().equals("2003") ? "2001" : e.getMessage());
                retMap.put("interfaceRequestID", params.get("interfaceRequestID"));
                return retMap;
            }
            logger.error("来逗阵 快捷支付D0-积分短信消费异常 接口请求号:[{}],卡号:[{}],异常信息：[{}]", interfaceRequest.getInterfaceRequestID(), params.get("cardNo"), e);
            throw new RuntimeException(e.getMessage());
        }

        return retMap;
    }

    @Override
    public Map<String, String> authPay(Map<String, String> map) {
        return sendVerifyCode(map);
    }

    @Override
    public Map<String, String> sale(Map<String, String> map) {
        Map<String, String> retMap = new HashMap<>();

        InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(map.get("interfaceRequestID"));
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {
        });


        try {
            //获取缓存信息
            Map<String, String> respStr = JsonUtils.toObject(CacheUtils.get("QUICKPAY_LDZ-42010103-" + interfaceRequest.getInterfaceRequestID() + "-" + map.get("tradeOrderCode"), String.class), new TypeReference<Map<String, String>>() {
            });


            Map<String, Object> reqMap = new LinkedHashMap<String, Object>();
            reqMap.put("orderId", respStr.get("tradeNo"));
            reqMap.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            reqMap.put("queryId", respStr.get("queryId"));
            reqMap.put("body", interfaceRequest.getInterfaceRequestID());
            reqMap.put("smsCode", map.get("smsCode"));
            reqMap.put("backUrl", properties.getProperty("backUrl"));
            reqMap.put("version", properties.getProperty("version"));

            logger.info("来逗阵 快捷支付D0-积分 确认支付 接口请求号：[{}]，请求原参数：[{}]", interfaceRequest.getInterfaceRequestID(), JsonUtils.toJsonString(reqMap));

            Map<String, String> data = new LinkedHashMap<String, String>();
            data.put("data", Base64Utils.encode(RSAUtils.encryptByPublicKey(JsonUtils.toJsonString(reqMap).getBytes("UTF-8"), properties.getProperty("publicKey"))));
            data.put("url", properties.getProperty("payUrl") + properties.getProperty("channelType") + "/" + properties.getProperty("appId"));

            logger.info("来逗阵 快捷支付D0-积分 确认支付 接口请求号：[{}]，请求报文：[{}]", interfaceRequest.getInterfaceRequestID(), data);

            String respInfo = HttpClientUtils.send(HttpClientUtils.Method.POST, "http://util.bank-pay.com/postForWard", data, false, "UTF-8",10000);

            logger.info("来逗阵 快捷支付D0-积分 确认支付 接口请求号：[{}]，响应报文：[{}]", interfaceRequest.getInterfaceRequestID(), respInfo);

            Map<String, Object> respMap = JsonUtils.toObject(respInfo, new TypeReference<Map>() {});

            retMap.put("authTranStat", "SUCCESS");
            retMap.put("compType", BusinessCompleteType.NORMAL.name());
            retMap.put("tranStat", "UNKNOWN");
            retMap.put("responseCode", "01");
            if(Boolean.valueOf(respMap.get("success").toString())) {
                retMap.put("responseMsg", "交易成功，扣款状态未知");
            }else {
                retMap.put("responseMsg", respMap.get("msg").toString());
            }

        } catch (Exception e) {
            logger.error("来逗阵 快捷支付D0-积分 确认支付异常 接口请求号:[{}]，异常信息：[{}]", map.get("interfaceRequestID"), e);
        }finally {
            CacheUtils.del("QUICKPAY_LDZ-42010103-" + interfaceRequest.getInterfaceRequestID() + "-" + map.get("tradeOrderCode"));
            logger.info("来逗阵 快捷支付D0-积分 确认支付  接口请求号：[{}]，解锁", interfaceRequest.getInterfaceRequestID());
        }

        return retMap;
    }

    @Override
    public Map<String, String> query(Map<String, String> map) {
        return null;
    }

    @Override
    public QuickPayOpenCardResponseBean sendOpenCardVerifyCode(Map<String, String> params) {
        return null;
    }

    @Override
    public QuickPayOpenCardResponseBean openCardInfo(Map<String, String> params) {

        QuickPayOpenCardResponseBean responseBean = new QuickPayOpenCardResponseBean();
        //设置默认状态为失败
        responseBean.setStatus("FAILED");

        try {

            Properties properties = JsonUtils.toObject(String.valueOf(params.get("tradeConfigs")), new TypeReference<Properties>() {
            });

            //获取交易卡信息
            TransCardBean transCardBean = customerInterface.findTransCardByAccNo(params.get("customerNo"), params.get("cardNo"), CardAttr.TRANS_CARD);
            if (transCardBean == null) {
                throw new BusinessRuntimeException("3009");
            }
            if (transCardBean.getCardStatus() != CardStatus.NORAML) {
                throw new BusinessRuntimeException("3008");
            }


            Map<String, Object> reqMap = new LinkedHashMap<String, Object>();
            reqMap.put("orderId", CodeBuilder.getOrderIdByUUId());
            reqMap.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            reqMap.put("accNo", params.get("cardNo"));
            reqMap.put("phoneNo", transCardBean.getPhone());

            String str = params.get("expireDate");
            String[] strNt = new String[]{str.substring(0,2), str.substring(2,4)};
            str = strNt[1] + strNt[0];

            reqMap.put("expired", str);
            reqMap.put("cvn2", params.get("cvn"));
            reqMap.put("realName", transCardBean.getAccountName());
            reqMap.put("identityNo", transCardBean.getIdNumber());

            //校验当前是否是信用卡，若不是，则直接返回失败
            if(!CardType.CREDIT.name().equals(transCardBean.getCardType().name())) {
                return responseBean;
            }

            reqMap.put("accattr", "2");
            //银行代码
            String terminalOpenBankCode = properties.getProperty(transCardBean.getBankCode());
            
            if(terminalOpenBankCode == null) {
            	return responseBean;
            }
            
            reqMap.put("terminalOpenBankCode", terminalOpenBankCode);

            //用来存放结算卡信息
            SortedMap<Object, Object> cardMap = new TreeMap<Object, Object>();

            // 获取结算卡信息
            quickPayFeeService.getSettleInfo(cardMap, params.get("customerNo"), params.get("cardNo"));

            QuickPayFilingInfo filingInfo2 = quickPayFilingInfoService.find(cardMap.get("cardNo").toString(), interfaceInfoCode);
            if(filingInfo2 == null) {
                return responseBean;
            }

            reqMap.put("insCode", filingInfo2.getChannelCustomerCode());
            reqMap.put("version", properties.getProperty("version"));

            logger.info("来逗阵 快捷支付D0-积分绑卡 接口编号：[{}]，请求原参数：[{}]", interfaceInfoCode, JsonUtils.toJsonString(reqMap));

            Map<String, String> data = new LinkedHashMap<String, String>();
            data.put("data", Base64Utils.encode(RSAUtils.encryptByPublicKey(JsonUtils.toJsonString(reqMap).getBytes("UTF-8"), properties.getProperty("publicKey"))));
            data.put("url", properties.getProperty("bindCardUrl") + properties.getProperty("channelType") + "/" + properties.getProperty("appId"));

            logger.info("来逗阵 快捷支付D0-积分绑卡 接口编号：[{}]，请求报文：[{}]", interfaceInfoCode, data);

            String respInfo = HttpClientUtils.send(HttpClientUtils.Method.POST, "http://util.bank-pay.com/postForWard", data, false, "UTF-8",10000);

            logger.info("来逗阵 快捷支付D0-积分绑卡 接口编号：[{}]，响应报文：[{}]", interfaceInfoCode, respInfo);

            Map<String, Object> respMap = JsonUtils.toObject(respInfo, new TypeReference<Map>() {});

            if(Boolean.valueOf(respMap.get("success").toString())) {
                responseBean.setStatus("SUCCESS");
                responseBean.setToken(JsonUtils.toObject(JsonUtils.toJsonString(respMap.get("attributes")), new TypeReference<Map>() {
                }).get("token").toString());
            }

            responseBean.setResponseCode(String.valueOf(respMap.get("errorCode")));
            responseBean.setResponseMessage(respMap.get("msg").toString());

        } catch (Exception e) {
            logger.info("来逗阵 快捷支付D0-积分绑卡 接口编号：[{}]，错误信息：[{}]", interfaceInfoCode, e);
        }

        return responseBean;
    }

}
