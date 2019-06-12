package com.yl.payinterface.core.handle.impl.quick.chcodepay420102;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.payinterface.core.handler.QuickPayFilingHandler;
import com.yl.payinterface.core.model.QuickPayFilingInfo;
import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.service.QuickPayFilingInfoService;
import com.yl.payinterface.core.utils.AESUtils;
import com.yl.payinterface.core.utils.CodeBuilder;
import com.yl.payinterface.core.utils.HttpUtils;
import com.yl.payinterface.core.utils.MD5Util;
import com.yl.payinterface.core.utils.RandomAddressUtils;

@Service("chcodepay420102FilingHandler")
public class Chcodepay420102FilingHandlerImpl implements QuickPayFilingHandler {

    private static Logger logger = LoggerFactory.getLogger(Chcodepay420102FilingHandlerImpl.class);

    private final static String interfaceInfoCode = "QUICKPAY_ChcodePay-420102";

    @Resource
    private QuickPayFilingInfoService quickPayFilingInfoService;
    @Resource
    private InterfaceRequestService interfaceRequestService;
	
	@Override
	public Map<String, String> filing(Map<String, String> map) {

		Map<String, String> rtMap = new HashMap<String, String>();
		rtMap.put("status", "FAILED");
        
        try {
			
            Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {
            });
            
            String bankName = properties.getProperty(map.get("clearBankCode"));
            // 查看当前结算卡所属银行是否支持
            if(bankName == null || bankName.equals("")) {
            	return rtMap;
            }
            
            
            DecimalFormat df = new DecimalFormat("0.0000");
            
            
            // 保存OR更新数据库
            QuickPayFilingInfo quickPayFilingInfo = new QuickPayFilingInfo();
            quickPayFilingInfo.setBankCardNo(map.get("bankCardNo"));
            quickPayFilingInfo.setBankName(map.get("bankName"));
            quickPayFilingInfo.setClearBankCode(map.get("clearBankCode"));
            quickPayFilingInfo.setIdCardNo(map.get("idCardNo"));
            quickPayFilingInfo.setName(map.get("name"));
            quickPayFilingInfo.setPhone(map.get("phone"));
            quickPayFilingInfo.setQuickPayFee(map.get("quickPayFee"));
            quickPayFilingInfo.setRemitFee(map.get("remitFee"));
            quickPayFilingInfo.setTransCardNo(map.get("cardNo"));
            quickPayFilingInfo.setInterfaceInfoCode(interfaceInfoCode);
            quickPayFilingInfo.setCustomerCode(map.get("customerCode") == null ? CodeBuilder.getOrderIdByUUId() : map.get("customerCode"));
            
            
            // 添加OR更改报备
            if(map.get("updateFlag").equals("0")) {
            	
                // 组装新增报备参数
            	Map<String, String> reqMap = new HashMap<String, String>();
            	
                reqMap.put("source_no", properties.getProperty("source_no"));
                reqMap.put("merchant_code", map.get("customerCode") == null ? CodeBuilder.getOrderIdByUUId() : map.get("customerCode"));
                reqMap.put("merchant_name", "个体户" + map.get("name"));
                reqMap.put("merchant_short_name", map.get("name"));
                reqMap.put("is_cert", "0");
                reqMap.put("license_no", "");
                reqMap.put("license_expire_dt", "");
                reqMap.put("wx_industry_id", "");
                reqMap.put("ali_industry_id", "");
                reqMap.put("legal_name", map.get("name"));
                reqMap.put("id_number", map.get("idCardNo"));
                reqMap.put("phone_no", map.get("phone"));
                
                String[] address = RandomAddressUtils.getAddr();
                
                reqMap.put("province", address[0]);
                reqMap.put("city", address[1]);
                reqMap.put("merchant_address", address[2]);
                reqMap.put("settle_type", properties.getProperty("settle_type"));
                reqMap.put("wx_rate", "0.0000");
                reqMap.put("ali_rate", "0.0000");
                reqMap.put("up_rate", df.format(Double.valueOf(map.get("quickPayFee"))));
                reqMap.put("up_bonus_rate", df.format(Double.valueOf(map.get("quickPayFee"))));
                reqMap.put("out_fee", String.format("%.2f", Double.valueOf(map.get("remitFee"))));
                reqMap.put("acct_name", map.get("name"));
                reqMap.put("bank_card_no", map.get("bankCardNo"));
                reqMap.put("bank_name", bankName);
                reqMap.put("bank_sub_name", bankName);
                reqMap.put("bank_unionpay_code", "");
            	
                logger.info("邦呈快捷支付-报备 接口编号：[{}]，请求原参数：[{}]", interfaceInfoCode, JsonUtils.toJsonString(reqMap));
                
                // 请求
                String respInfo = HttpUtils.sendReq(properties.getProperty("filingUrl"), AESUtils.encrypt(getSign(reqMap, properties.getProperty("sign_key")), properties.getProperty("aes_key")), "POST");
                
                logger.info("邦呈快捷支付-报备 接口编号：[{}]，响应信息：[{}]", interfaceInfoCode, JsonUtils.toJsonString(respInfo));
                
                Map<String, Object> respMap = JsonUtils.toObject(respInfo, new TypeReference<Map<String, Object>>() {
                });

                if(respMap.get("response_code").equals("0000")) {

                    // 获取平台商户号
                    quickPayFilingInfo.setChannelCustomerCode(JsonUtils.toObject(respMap.get("response_body").toString(), new TypeReference<Map<String, String>>() {
                    }).get("merchant_no"));
                    
                    // 保存到数据库
                    quickPayFilingInfoService.save(quickPayFilingInfo);
                    
                    rtMap.put("status", "SUCCESS");
                    
                }
                
            }else {
            	
            	// 组装修改报备参数
                Map<String, String> reqMap = new HashMap<String, String>();
                reqMap.put("source_no", properties.getProperty("source_no"));
                reqMap.put("merchant_code", map.get("channelCustomerCode"));
                reqMap.put("settle_type", "M1");
                reqMap.put("wx_rate", "0.0000");
                reqMap.put("ali_rate", "0.0000");
                reqMap.put("up_rate", df.format(Double.valueOf(map.get("quickPayFee"))));
                reqMap.put("up_bonus_rate", df.format(Double.valueOf(map.get("quickPayFee"))));
                reqMap.put("out_fee", String.format("%.2f", Double.valueOf(map.get("remitFee"))));
                
                logger.info("邦呈快捷支付-修改报备 接口编号：[{}]，请求原参数：[{}]", interfaceInfoCode, JsonUtils.toJsonString(reqMap));

                // 请求
                String respInfo = HttpUtils.sendReq(properties.getProperty("filingUpdateUrl"), AESUtils.encrypt(getSign(reqMap, properties.getProperty("sign_key")), properties.getProperty("aes_key")), "POST");
                
                logger.info("邦呈快捷支付-修改报备 接口编号：[{}]，响应信息：[{}]", interfaceInfoCode, JsonUtils.toJsonString(respInfo));
                
                Map<String, Object> respMap = JsonUtils.toObject(respInfo, new TypeReference<Map<String, Object>>() {
                });

                if(respMap.get("response_code").equals("0000")) {

                	//更新到数据库
                    quickPayFilingInfo.setChannelCustomerCode(map.get("channelCustomerCode"));
                    quickPayFilingInfo.setCode(map.get("filingInfoCode"));
                    quickPayFilingInfoService.update(quickPayFilingInfo);
                    
                    rtMap.put("status", "SUCCESS");
                    
                }
                
            }
            
		} catch (Exception e) {
			logger.info("交易卡号：{}上送商户报错：{}", map.get("cardNo"), e);
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
