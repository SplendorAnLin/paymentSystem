package com.yl.payinterface.core.remote.hessian.impl;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.hessian.bean.AuthBean;
import com.lefu.hessian.exception.BusinessException;
import com.lefu.hessian.exception.BusinessRuntimeException;
import com.yl.online.trade.hessian.OnlineTradeHessianService;
import com.yl.payinterface.core.bean.InternetbankQueryBean;
import com.yl.payinterface.core.bean.InternetbankSalesResponseBean;
import com.yl.payinterface.core.bean.InternetbankSalesTradeBean;
import com.yl.payinterface.core.bean.TradeContext;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.enums.CardType;
import com.yl.payinterface.core.enums.InterfaceInfoStatus;
import com.yl.payinterface.core.enums.InterfaceTradeStatus;
import com.yl.payinterface.core.exception.ExceptionMessages;
import com.yl.payinterface.core.handler.InternetbankHandler;
import com.yl.payinterface.core.hessian.InternetbankHessianService;
import com.yl.payinterface.core.model.InterfaceInfo;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.service.InterfaceInfoService;
import com.yl.payinterface.core.service.InterfaceRequestReverseService;
import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.utils.FeeUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * 支付接口服务网银支付Hessian接口实现
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年10月22日
 */
@Service("internetbankHessianService")
public class InternetbankHessianServiceImpl implements InternetbankHessianService {

    private static final Logger logger = LoggerFactory.getLogger(InternetbankHessianServiceImpl.class);

    @Resource
    private Validator validator;
    @Resource
    private Map<String, InternetbankHandler> internetbankHandlers;
    @Resource
    private InterfaceInfoService interfaceInfoService;
    @Resource
    private OnlineTradeHessianService onlineTradeHessianService;
    @Resource
    private InterfaceRequestService interfaceRequestService;
    @Resource
    private InterfaceRequestReverseService interfaceRequestReverseService;

    @SuppressWarnings("deprecation")
    @Override
    public Object trade(InternetbankSalesTradeBean internetbankSalesTradeBean) {
        try {
            // 参数校验
            Set<ConstraintViolation<InternetbankSalesTradeBean>> violations = validator.validate(internetbankSalesTradeBean);
            if (violations.size() > 0) {
                String originalMsg = violations.iterator().next().getPropertyPath().toString();
                String errorMsg = originalMsg.substring(originalMsg.lastIndexOf(".") + 1, originalMsg.length());
                if (violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(NotNull.class))
                    throw new BusinessException(
                            StringUtils.concatToSB(ExceptionMessages.TRADE_PARAMS_NOT_EXISTS, "-", errorMsg).toString());
                else
                    throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.TRADE_PARAMS_NOT_EXISTS, "-", errorMsg).toString());
            }

            TradeContext tradeContext = new TradeContext();
            InternetbankHandler tradeHandler = internetbankHandlers.get(internetbankSalesTradeBean.getInterfaceCode());
            if (tradeHandler == null) throw new BusinessRuntimeException(ExceptionMessages.TRADE_PARAMS_NOT_EXISTS);

            String temp = internetbankSalesTradeBean.getBusinessOrderID();
            internetbankSalesTradeBean.setBusinessOrderID(internetbankSalesTradeBean.getBussinessFlowID());
            internetbankSalesTradeBean.setBussinessFlowID(temp);
            // 记录支付接口请求
            InterfaceRequest interfaceRequest = interfaceRequestService.save(internetbankSalesTradeBean);

            // 生成接口请求
            tradeContext.setInterfaceRequest(interfaceRequest);

            // 获取交易配置
            InterfaceInfo interfaceInfo = interfaceInfoService.queryByCode(internetbankSalesTradeBean.getInterfaceCode());

            if (interfaceInfo.getStatus() == InterfaceInfoStatus.FALSE) {
                throw new RuntimeException(ExceptionMessages.INTERFACE_DISABLE);
            }
            if (interfaceInfo.getSingleAmountLimit() != null && interfaceInfo.getSingleAmountLimit() > 0) {
                if (!AmountUtils.leq(interfaceRequest.getAmount(), interfaceInfo.getSingleAmountLimit())) {
                    throw new RuntimeException(ExceptionMessages.EXCEED_LIMIT);
                }
            }
            if (interfaceInfo.getSingleAmountLimitSmall() != null && interfaceInfo.getSingleAmountLimitSmall() > 0) {
                if (AmountUtils.less(interfaceRequest.getAmount(), interfaceInfo.getSingleAmountLimitSmall())) {
                    throw new RuntimeException(ExceptionMessages.LOWER_LIMIT);
                }
            }

            if (StringUtils.notBlank(interfaceInfo.getStartTime()) && StringUtils.notBlank(interfaceInfo.getEndTime())) {
                Date now = new Date();
                if (Integer.parseInt((now.getHours() + "" + (now.getMinutes() < 10 ? "0" + now.getMinutes() : now.getMinutes()))) < Integer.parseInt(interfaceInfo.getStartTime().replace(":", "")) ||
                        Integer.parseInt((now.getHours() + "" + (now.getMinutes() < 10 ? "0" + now.getMinutes() : now.getMinutes()))) >= Integer.parseInt(interfaceInfo.getEndTime().replace(":", ""))) {
                    throw new RuntimeException(ExceptionMessages.NOT_CHENNEL_TIME);
                }
            } else if (StringUtils.notBlank(interfaceInfo.getStartTime())) {
                Date now = new Date();
                if (Integer.parseInt((now.getHours() + "" + (now.getMinutes() < 10 ? "0" + now.getMinutes() : now.getMinutes()))) < Integer.parseInt(interfaceInfo.getStartTime().replace(":", ""))) {
                    throw new RuntimeException(ExceptionMessages.NOT_CHENNEL_TIME);
                }
            } else if (StringUtils.notBlank(interfaceInfo.getEndTime())) {
                Date now = new Date();
                if (Integer.parseInt((now.getHours() + "" + (now.getMinutes() < 10 ? "0" + now.getMinutes() : now.getMinutes()))) >= Integer.parseInt(interfaceInfo.getEndTime().replace(":", ""))) {
                    throw new RuntimeException(ExceptionMessages.NOT_CHENNEL_TIME);
                }
            }

            if("UNICOM100001-B2C".equals(interfaceInfo.getCode())){
                // 记录补单信息
                interfaceRequestReverseService.recordInterfaceRequestReverse(interfaceRequest, interfaceInfo.getType().toString());
            }

            Map<String, String> map = new HashMap<String, String>();
            // 商品名称
            map.put("goodsName", internetbankSalesTradeBean.getProductName());
            // 交易配置
            map.put("transConfig", interfaceInfo.getTradeConfigs());
            // 卡类型
            map.put("cardType", internetbankSalesTradeBean.getCardType());
            // 卡信息
            map.put("cardBean", JsonUtils.toJsonString(internetbankSalesTradeBean.getMobileInfoBean()));
            // 用户IP
            map.put("clientIp", internetbankSalesTradeBean.getClientIp());
            // 接口提供方编码
            map.put("providerCode", internetbankSalesTradeBean.getInterfaceProviderCode());
            // 网银取货地址
            map.put("pageNotifyUrl", internetbankSalesTradeBean.getOrderBackUrl());
            if (internetbankSalesTradeBean.getMobileInfoBean() != null && StringUtils.isNotBlank(internetbankSalesTradeBean.getMobileInfoBean().getBankCardNo())) {
            	map.put("bankCardNo", internetbankSalesTradeBean.getMobileInfoBean().getBankCardNo());
            }

            tradeContext.setRequestParameters(map);
            // 交易处理
            return tradeHandler.trade(tradeContext)[1];
        } catch (Exception e) {
            logger.error("", e);
            throw new BusinessRuntimeException(e.getMessage());
        }

    }

    @Override
    public void complete(AuthBean auth, InternetbankSalesResponseBean internetbankResponseBean) {
        Map<String, String> completeMap = internetbankResponseBean.getInternetResponseMsg();

        InterfaceInfo interfaceInfo = interfaceInfoService.queryByCode(completeMap.get("interfaceCode"));
        InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(completeMap.get("interfaceRequestID"), completeMap.get("interfaceCode"));
        if (interfaceRequest.getStatus() != InterfaceTradeStatus.UNKNOWN) {
            logger.warn("接口订单:{} 已处理", interfaceRequest.getInterfaceRequestID());
            return;
        }

        if (!AmountUtils.eq(interfaceRequest.getAmount(), Double.valueOf(completeMap.get("amount")))) {
            throw new RuntimeException("接口订单:" + interfaceRequest.getInterfaceRequestID() + ",完成时金额不一致");
        }

        interfaceRequest.setInterfaceOrderID(completeMap.get("interfaceOrderID"));
        interfaceRequest.setCompleteTime(new Date());
        interfaceRequest.setFee(FeeUtils.computeFee(interfaceRequest.getAmount(), interfaceInfo.getFeeType(), interfaceInfo.getFee()));
        interfaceRequest.setResponseCode(completeMap.get("responseCode"));
        interfaceRequest.setResponseMessage(completeMap.get("responseMessage"));
        interfaceRequest.setStatus(InterfaceTradeStatus.valueOf(completeMap.get("tranStat")));
        interfaceRequest.setBusinessCompleteType(BusinessCompleteType.valueOf(completeMap.get("businessCompleteType").toString()));
        interfaceRequestService.modify(interfaceRequest);

        try {
            callBackOnlineTradeResult(interfaceRequest, interfaceInfo, internetbankResponseBean.isSynchronize());
        } catch (Exception e) {
            logger.error("支付接口 订单:{},通知交易系统失败:{} ", completeMap.get("interfaceRequestID"), e);
        }
    }

    /**
     * 支付结果通知
     *
     * @param interfaceRequest 提供方接口请求记录
     * @param isSynchronized   是否实时通知交易系统标记
     */
    public void callBackOnlineTradeResult(InterfaceRequest interfaceRequest, InterfaceInfo interfaceInfo, boolean isSynchronized) throws BusinessException {
        // 组装通知交易系统参数
        Map<String, String> payResultParams = new LinkedHashMap<String, String>();
        payResultParams.put("interfaceOrderID", interfaceRequest.getInterfaceOrderID());
        payResultParams.put("interfaceProvider", interfaceRequest.getInterfaceProviderCode());
        payResultParams.put("cardType", StringUtils.isBlank(interfaceRequest.getCardType()) ? CardType.DEBIT_CARD.name() : interfaceRequest.getCardType());
        payResultParams.put("transStatus", interfaceRequest.getStatus().toString());
        payResultParams.put("responseMessage", interfaceRequest.getResponseMessage());
        payResultParams.put("responseCode", interfaceRequest.getResponseCode());
        payResultParams.put("businessOrderID", interfaceRequest.getBussinessOrderID());
        payResultParams.put("businessFlowID", interfaceRequest.getBussinessFlowID());
        payResultParams.put("amount", String.valueOf(interfaceRequest.getAmount()));
        payResultParams.put("interfaceFee", String.valueOf(interfaceRequest.getFee()));
        payResultParams.put("businessType", "SAILS");
        payResultParams.put("interfaceRequestID", interfaceRequest.getInterfaceRequestID());
        onlineTradeHessianService.complete(payResultParams);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, String> query(AuthBean auth, InternetbankQueryBean internetbankQueryBean) {
        InternetbankHandler tradeHandler = internetbankHandlers.get(internetbankQueryBean.getInterfaceInfoCode());
        if (tradeHandler == null) throw new BusinessRuntimeException(ExceptionMessages.TRADE_PARAMS_NOT_EXISTS);
        // 获取交易配置
        InterfaceInfo interfaceInfo = interfaceInfoService.queryByCode(internetbankQueryBean.getInterfaceInfoCode());
        if (interfaceInfo == null) throw new BusinessRuntimeException(ExceptionMessages.TRADE_PARAMS_NOT_EXISTS);

        //支付订单号
        String interfaceRequestID = internetbankQueryBean.getInterfaceRequestID();

        try {
            Map<String, String> queryParams = new HashMap<String, String>();
            queryParams.put("transConfig", interfaceInfo.getTradeConfigs());
            queryParams.put("originalRequestTime", DateFormatUtils.format(internetbankQueryBean.getOriginalInterfaceRequestTime(), "yyyyMMddHHmmss"));
            // 业务完成方式
            queryParams.put("businessCompleteType", internetbankQueryBean.getBusinessCompleteType());
            TradeContext tradeContext = new TradeContext();
            InterfaceRequest interfaceRequest = new InterfaceRequest();
            interfaceRequest.setInterfaceRequestID(internetbankQueryBean.getInterfaceRequestID());
            interfaceRequest.setInterfaceInfoCode(internetbankQueryBean.getInterfaceInfoCode());
            interfaceRequest.setOriginalInterfaceRequestID(interfaceRequestID);
            interfaceRequest.setAmount(internetbankQueryBean.getAmount());
            tradeContext.setInterfaceRequest(interfaceRequest);
            tradeContext.setRequestParameters(queryParams);
            // 查询处理
            Map<String, String> resultMap = (Map<String, String>) tradeHandler.query(tradeContext)[0];
            // 查询支付结果
            String queryStatus = resultMap.get("queryStatus").toString();

            logger.info("补单系统查询状态：\r\nqueryStatus={}\r\ninterfaceRequestID={}\r\n", queryStatus, interfaceRequestID);
            Map<String, String> queryResponseMap = new HashMap<String, String>();
            // 非成功
            if (!InterfaceTradeStatus.UNKNOWN.name().equals(queryStatus)) {
                InternetbankSalesResponseBean internetbankSalesResponseBean = new InternetbankSalesResponseBean();
                internetbankSalesResponseBean.setInterfaceCode(internetbankQueryBean.getInterfaceInfoCode());
                resultMap.put("interfaceRequestID", interfaceRequestID);
                resultMap.put("interfaceCode", internetbankQueryBean.getInterfaceInfoCode());
                internetbankSalesResponseBean.setInternetResponseMsg(resultMap);
                internetbankSalesResponseBean.setSynchronize(true);
                complete(null, internetbankSalesResponseBean);
                queryResponseMap.put("handleResult", queryStatus);
                queryResponseMap.put("tranStat", interfaceRequest.getStatus().name());
            } else queryResponseMap.put("handleResult", queryStatus);
            return queryResponseMap;
        } catch (BusinessException e) {
            logger.error("channel: {},interfaceRequestId:{},errorCode:{}", interfaceInfo.getCode(), interfaceRequestID, e.getMessage());
            throw new BusinessRuntimeException(StringUtils.isBlank(e.getMessage()) ? ExceptionMessages.UNKNOWN_ERROR : e.getMessage());
        }
    }
}
