package com.yl.payinterface.front.web.controller;

import com.lefu.commons.cache.util.CacheUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.bean.AliPayCollectionCodeBean;
import com.yl.payinterface.core.bean.InterfaceInfoBean;
import com.yl.payinterface.core.bean.InterfaceRequestQueryBean;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.hessian.InterfaceRequestHessianService;
import com.yl.payinterface.core.hessian.PayInterfaceHessianService;
import com.yl.payinterface.core.hessian.WalletpayHessianService;
import com.yl.payinterface.front.utils.AESUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 个人码收款通知
 * 
 * @author AnLin
 * @version V1.0.0
 * @since 2018/8/16
 */
@Controller
@RequestMapping("collectionCodeNotify")
public class CollectionCodeCompleteController {
    
    private static final Logger logger = LoggerFactory.getLogger(CollectionCodeCompleteController.class);

    private static final String ALIPAY = "CC100001-ALIPAY_H5";

    @Resource
    private PayInterfaceHessianService payInterfaceHessianService;

    @Resource
    private WalletpayHessianService walletpayHessianService;

    @Resource
    private InterfaceRequestHessianService interfaceRequestHessianService;

    @RequestMapping("completeTest")
    public void completeTest(HttpServletRequest request, HttpServletResponse response, String interfaceRequestID, String aliPayAcc){
        try {
            Map<String, String> params = retrieveParams(request.getParameterMap());
            logger.info("支付宝个人收款码后台异步通知原文：{}", params);
            InterfaceRequestQueryBean interfaceRequestQueryBean = interfaceRequestHessianService.findRequestByInterfaceReqId(interfaceRequestID);
            Map<String, String> walletPayResponseBean = new HashMap<>();
            walletPayResponseBean.put("tranStat", "SUCCESS");
            walletPayResponseBean.put("responseCode", "0000");
            walletPayResponseBean.put("responseMessage", "交易成功");
            walletPayResponseBean.put("amount", String.valueOf(interfaceRequestQueryBean.getAmount()));
            walletPayResponseBean.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            walletPayResponseBean.put("completeTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            walletPayResponseBean.put("businessCompleteType", BusinessCompleteType.NORMAL.name());
            walletPayResponseBean.put("interfaceOrderID", aliPayAcc);
            walletPayResponseBean.put("interfaceRequestID", interfaceRequestID);
            walletPayResponseBean.put("interfaceCode", ALIPAY);
            walletpayHessianService.complete(walletPayResponseBean);
            response.getWriter().write("SUCCESS");
        } catch (Exception e) {
            logger.error("异常!异常信息：{}", e);
        }
    }

    @RequestMapping("complete")
    public void complete(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> params = retrieveParams(request.getParameterMap());
            logger.info("支付宝个人收款码后台异步通知原文：{}", params);
            InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(ALIPAY);
            Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>(){});
            params = AESUtils.decode(params, properties.getProperty("key"));
            String cache = CacheUtils.get(params.get("partnerNo") + ":" + params.get("totalAmount") + params.get("goodsTitle"), String.class);
            if (StringUtils.isBlank(cache)){
                response.getWriter().write("FAILURE");
                logger.info("收款码缓存为空 - :{}", params);
                return;
            }
            AliPayCollectionCodeBean aliPayCollectionCode = JsonUtils.toObject(cache, new TypeReference<AliPayCollectionCodeBean>() {
            });
            InterfaceRequestQueryBean interfaceRequestQueryBean = interfaceRequestHessianService.findRequestByInterfaceReqId(aliPayCollectionCode.getInterfaceRequestID());
            Map<String, String> walletPayResponseBean = new HashMap<>();
            walletPayResponseBean.put("tranStat", "SUCCESS");
            walletPayResponseBean.put("responseCode", "0000");
            walletPayResponseBean.put("responseMessage", "交易成功");
            walletPayResponseBean.put("amount", String.valueOf(interfaceRequestQueryBean.getAmount()));
            walletPayResponseBean.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            walletPayResponseBean.put("completeTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            walletPayResponseBean.put("businessCompleteType", BusinessCompleteType.NORMAL.name());
            walletPayResponseBean.put("interfaceOrderID", params.get("tradeNo"));
            walletPayResponseBean.put("interfaceRequestID", aliPayCollectionCode.getInterfaceRequestID());
            walletPayResponseBean.put("interfaceCode", ALIPAY);
            walletpayHessianService.complete(walletPayResponseBean);
            CacheUtils.del("ALIPAYCODE:" + aliPayCollectionCode.getInterfaceRequestID());
            CacheUtils.del("ALIPAYCODELOCK:" + aliPayCollectionCode.getId());
            CacheUtils.del(aliPayCollectionCode.getAliPayAcc() + ":" + aliPayCollectionCode.getAmount());
            try {
                payInterfaceHessianService.updateAliPayCollection(aliPayCollectionCode);
            } catch (Exception e) {
                logger.error("收款码更新成功次数异常!异常信息：{}", e);
            }
            response.getWriter().write("SUCCESS");
        } catch (Exception e) {
            logger.error("个人码通知处理错误！", e);
            try {
                response.getWriter().write("ERROR");
            } catch (Exception ex) {
                logger.error("json返回异常!异常信息：{}", ex);
            }
        }
    }

    @RequestMapping("updateStatus")
    public void updateStatus(HttpServletResponse response, String aliPayAcc, String status) {
        try {
            if (StringUtils.isNotBlank(aliPayAcc) && StringUtils.isNotBlank(status)) {
                payInterfaceHessianService.updateAliPayStatus(aliPayAcc, status);
                response.getWriter().write("SUCCESS");
            }
        } catch (Exception e) {
            logger.error("异常!异常信息：{}", e);
        }
    }

    /**
     * 参数转换
     */
    private Map<String, String> retrieveParams(Map<String, String[]> requestMap) {
        Map<String, String> resultMap = new HashMap<String, String>();
        for (String key : requestMap.keySet()) {
            String[] value = requestMap.get(key);
            if (value != null) {
                resultMap.put(key, Array.get(value, 0).toString().trim());
            }
        }
        return resultMap;
    }
}