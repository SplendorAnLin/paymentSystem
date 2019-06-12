package com.yl.online.gateway.service.impl;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.security.DigestUtils;
import com.lefu.hessian.bean.AuthBean;
import com.lefu.hessian.util.HessianSignUtils;
import com.yl.boss.api.bean.CustomerKey;
import com.yl.boss.api.enums.KeyType;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.online.gateway.Constants;
import com.yl.online.gateway.ExceptionMessages;
import com.yl.online.gateway.bean.PayParam;
import com.yl.online.gateway.enums.SignType;
import com.yl.online.gateway.exception.BusinessException;
import com.yl.online.gateway.exception.BusinessRuntimeException;
import com.yl.online.gateway.service.TradeHandler;
import com.yl.online.gateway.utils.DateCompUtils;
import com.yl.online.model.bean.*;
import com.yl.online.model.enums.*;
import com.yl.online.model.model.Order;
import com.yl.online.model.model.PartnerRequest;
import com.yl.online.trade.hessian.OnlineTradeHessianService;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * 网关交易处理接口实现
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年9月25日
 */
@Service("salesTradeHandler")
public class SalesTradeHandler implements TradeHandler {

    private static Logger logger = LoggerFactory.getLogger(SalesTradeHandler.class);
    @Resource
    private Validator validator;
    @Resource
    private OnlineTradeHessianService onlineTradeHessianService;
    @Resource
    private CustomerInterface customerInterface;

    @Override
    public String execute(PartnerRequest partnerRequest) throws BusinessException {
        validate(partnerRequest);
        SalesTradeRequest tradeRequest = JsonUtils.toObject(partnerRequest.getInfo(), new TypeReference<SalesTradeRequest>() {
        });
        CreateSalesOrderBean bean = new CreateSalesOrderBean();
        bean.setBusinessType(BusinessType.SAILS);
        bean.setPayerRole(PartnerRole.UN_REGISTERED);
        bean.setPayer(StringUtils.concatToSB(tradeRequest.getBuyer(), "|", tradeRequest.getBuyerContactType(), "|", tradeRequest.getBuyerContact()).toString());
        bean.setReceiverRole(PartnerRole.CUSTOMER);
        bean.setReceiver(partnerRequest.getPartner());
        bean.setOutOrderId(partnerRequest.getOutOrderId());
        bean.setAmount(new Double(partnerRequest.getAmount()));
        bean.setRedirectURL(tradeRequest.getRedirectURL());
        if (StringUtils.notBlank(partnerRequest.getNotifyUrl())) {
            bean.setNotifyURL(partnerRequest.getNotifyUrl());
        } else {
            bean.setNotifyURL(tradeRequest.getNotifyURL());
        }
        if (StringUtils.notBlank(tradeRequest.getTimeout())) {
            bean.setTimeout(DateCompUtils.addDate(new Date(), tradeRequest.getTimeout()));
        } else {
            bean.setTimeout(DateCompUtils.addDate(new Date(), "1D"));
        }
        if (StringUtils.notBlank(tradeRequest.getRetryFalg())) {
            bean.setRetryFalg(RepeatFlag.valueOf(tradeRequest.getRetryFalg()));
        } else {
            bean.setRetryFalg(RepeatFlag.FALSE);
        }
        bean.setExtParam(partnerRequest.getExtParam());
        bean.setProducts(partnerRequest.getProduct());
        if (StringUtils.notBlank(partnerRequest.getPayType())) {
            bean.setPayType(PayType.valueOf(partnerRequest.getPayType()));
        }

        AuthBean authBean = new AuthBean();
        authBean.setInvokeSystem(Constants.APP_NAME);
        authBean.setTimestamp(System.currentTimeMillis());
        authBean.setOperator("system");
        String signSource = StringUtils.concatToSB(authBean.toString(), bean.toString(), "123456").toString();
        authBean.setSign(HessianSignUtils.md5DigestAsHex(signSource.getBytes()));
        return onlineTradeHessianService.createOrder(bean);

    }

    private void validate(PartnerRequest partnerRequest) throws BusinessException {
        Map<String, String[]> OriginalParams = JsonUtils.toObject(partnerRequest.getOriginalRequest(), new TypeReference<Map<String, String[]>>() {
        });
        Map<String, String> params = new HashMap<>();
        for (String key : OriginalParams.keySet()) {
            params.put(key, OriginalParams.get(key)[0]);
        }
        SalesTradeRequest tradeRequest = new SalesTradeRequest();
        tradeRequest.setBuyer(params.get("buyer"));
        tradeRequest.setBuyerContactType(params.get("buyerContactType"));
        tradeRequest.setBuyerContact(params.get("buyerContact"));
        if (StringUtils.notBlank(params.get("payType"))) {
            tradeRequest.setPaymentType(params.get("payType"));
        } else {
            tradeRequest.setPaymentType(params.get("paymentType"));
        }
        tradeRequest.setInterfaceCode(params.get("interfaceCode"));
        tradeRequest.setRetryFalg(params.get("retryFalg"));
        tradeRequest.setSubmitTime(params.get("submitTime"));
        tradeRequest.setTimeout(params.get("timeout"));
        tradeRequest.setClientIP(params.get("clientIP"));
        tradeRequest.setProductName(params.get("productName"));
        tradeRequest.setProductPrice(params.get("productPrice"));
        tradeRequest.setProductNum(params.get("productNum"));
        tradeRequest.setProductDesc(params.get("productDesc"));
        tradeRequest.setProductURL(params.get("productURL"));
        tradeRequest.setRedirectURL(params.get(Constants.PARAM_NAME_REDIRECTURL));
        tradeRequest.setNotifyURL(params.get(Constants.PARAM_NAME_NOTIFYURL));
        partnerRequest.setInfo(JsonUtils.toJsonString(tradeRequest));
        Set<ConstraintViolation<PartnerRequest>> violations = validator.validate(partnerRequest);
        if (violations.size() > 0) {
            String originalMsg = violations.iterator().next().getPropertyPath().toString();
            String errorMsg = originalMsg.substring(originalMsg.lastIndexOf(".") + 1, originalMsg.length());
            if (violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(NotNull.class))
                throw new BusinessException(
                        StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", errorMsg).toString());
            else
                throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_ERROR, "-", errorMsg).toString());
        }
        // 检查页面支付结果通知和后台是否同时为空
        /*
         * if (StringUtils.isBlank(params.get(Constants.PARAM_NAME_REDIRECTURL)) && StringUtils.isBlank(params.get(Constants.PARAM_NAME_NOTIFYURL))) {
         * throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", Constants.PARAM_NAME_REDIRECTURL, "-",
         * Constants.PARAM_NAME_NOTIFYURL).toString());
         * }
         */
    }

    @Override
    public PayResultBean pay(PayParam payParam) throws BusinessException {
        CreatePaymentBean bean = new CreatePaymentBean();
        bean.setBusinessType(BusinessType.SAILS);
        bean.setTradeOrderCode(payParam.getTradeOrderCode());
        bean.setInterfaceCode(payParam.getInterfaceCode());
        bean.setClientIP(payParam.getClientIP());
        bean.setAuthCode(payParam.getOpenid());
        bean.setPageNotifyUrl(payParam.getPageNotifyUrl());
        if (payParam.getMobileInfoBean() != null) {
            bean.setBankCardNo(payParam.getMobileInfoBean().getBankCardNo());
            bean.setCvv(payParam.getMobileInfoBean().getCvv());
            bean.setPayerMobNo(payParam.getMobileInfoBean().getPayerMobNo());
            bean.setPayerName(payParam.getMobileInfoBean().getPayerName());
            bean.setValidity(payParam.getMobileInfoBean().getValidity());
            bean.setCertNo(payParam.getMobileInfoBean().getCertNo());
            bean.setVerifycode(payParam.getMobileInfoBean().getVerifycode());
            bean.setSettleAmount(payParam.getMobileInfoBean().getSettleAmount());
            bean.setSettleType(payParam.getMobileInfoBean().getSettleType());
            bean.setRemitFee(payParam.getMobileInfoBean().getRemitFee());
            bean.setQuickPayFee(payParam.getMobileInfoBean().getQuickPayFee());
            bean.setSettleCardNo(payParam.getMobileInfoBean().getSettleCardNo());
        }
        return onlineTradeHessianService.createPayment(bean);
    }

    @Override
    public String createResponse(Order order, PartnerRequest partnerRequest, boolean isRedirectUrl) throws BusinessException {
        // 处理结果
        String handlerResult = "";
        if (OrderStatus.SUCCESS.equals(order.getStatus())) handlerResult = "SUCCESS"; // 交易成功
        if (OrderStatus.FAILED.equals(order.getStatus())) handlerResult = "FAILED"; // 交易失败
        if (order.getRefundStatus().equals(RefundStatus.REFUND_ALL) || order.getRefundStatus().equals(RefundStatus.REFUND_PART))
            handlerResult = "0002"; // 交易已经退款

        Payment payment = onlineTradeHessianService.queryLastPayment(order.getCode());

        Map<String, String> params = new LinkedHashMap<String, String>();
        // 接口编号
        params.put("apiCode", partnerRequest.getApiCode());
        // 订单金额
        params.put("amount", partnerRequest.getAmount());
        // 处理结果
        params.put("partnerOrderStatus", handlerResult);
        // 交易订单号
        params.put("tradeOrderCode", order.getCode());
        // 合作方唯一订单号
        params.put("outOrderId", partnerRequest.getOutOrderId());
        // 参数编码字符集
        params.put("inputCharset", "UTF-8");
        // 下单时间
//		params.put("orderTime", DateFormatUtils.format(order.getCreateTime(), "yyyyMMddHHmmss"));
        // 支付时间
//		params.put("payTime", DateFormatUtils.format(order.getSuccessPayTime(), "yyyyMMddHHmmss"));
        // 签名方式
        params.put("signType", partnerRequest.getSignType());
        // 合作方编号
        params.put("partner", partnerRequest.getPartner());
        // 回传参数
        params.put("returnParam", partnerRequest.getReturnParam());
        // 响应码
        params.put("resultCode", payment.getResponseCode());
        // 响应信息
        params.put("resultMsg", payment.getResponseInfo());

        // 密钥查询
        CustomerKey customerKey = customerInterface.getCustomerKey(partnerRequest.getPartner(), KeyType.MD5);
        if (customerKey == null) throw new BusinessRuntimeException(ExceptionMessages.RECEIVER_KEY_NOT_EXISTS);
        String key = customerKey.getKey();

        // 按参数名排序
        ArrayList<String> paramNames = new ArrayList<>(params.keySet());
        Collections.sort(paramNames);

        // 组织待签名信息
        StringBuilder signSource = new StringBuilder();
        Iterator<String> iterator = paramNames.iterator();
        while (iterator.hasNext()) {
            String paramName = iterator.next();
            if (!Constants.PARAM_NAME_SIGN.equals(paramName) && StringUtils.notBlank(params.get(paramName))) {
                signSource.append(paramName).append("=").append(params.get(paramName));
                if (iterator.hasNext()) signSource.append("&");
            }
        }

        String paramsStr = signSource.toString();
        // 计算签名
        String calSign = null;
        if (SignType.MD5.name().equals(partnerRequest.getSignType())) {
            signSource.append(key);
            try {
                logger.info("订单号：{}，异步通知签名原文串：{}", order.getCode(), signSource.toString());
                calSign = DigestUtils.md5DigestAsHex(signSource.toString().getBytes(partnerRequest.getInputCharset()));
            } catch (UnsupportedEncodingException e) {
                throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_ERROR, "-", Constants.PARAM_NAME_INPUT_CHARSET).toString());
            }
        }

        params.put("sign", calSign);
        paramsStr = paramsStr + "&sign=" + calSign;
        if (isRedirectUrl) {
            // XXX 同步通知，未带参数，待确认
            try {
                return order.getRedirectURL() + "?" + URLEncoder.encode(paramsStr, "UTF8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return order.getRedirectURL();
        } else {
            return order.getNotifyURL() + "?" + JsonUtils.toJsonString(params);
        }
    }
}
