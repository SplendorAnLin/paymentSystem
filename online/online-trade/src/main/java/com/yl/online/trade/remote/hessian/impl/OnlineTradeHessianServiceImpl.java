package com.yl.online.trade.remote.hessian.impl;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.DateUtils;
import com.yl.online.model.bean.*;
import com.yl.online.model.enums.BusinessType;
import com.yl.online.model.model.CustomerConfig;
import com.yl.online.model.model.Order;
import com.yl.online.trade.dao.CustomerConfigDao;
import com.yl.online.trade.dao.TradeOrderDao;
import com.yl.online.trade.exception.BusinessException;
import com.yl.online.trade.hessian.OnlineTradeHessianService;
import com.yl.online.trade.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 在线交易服务接口实现
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年8月13日
 */
@Service("onlineTradeHessianService")
public class OnlineTradeHessianServiceImpl implements OnlineTradeHessianService {
    @Resource
    private Map<BusinessType, TradeHandler> tradeHandlers;
    @Resource
    private TradeOrderService tradeOrderService;
    @Resource
    private CustomerConfigService customerConfigService;
    @Resource
    private InterfacePolicyService routerService;
    @Resource
    private TradeOrderDao tradeOrderDao;
    @Resource
    private CustomerConfigDao customerConfigDao;
    @Resource
    private PaymentService paymentService;

    @Override
    public String createOrder(CreateOrderBean createOrderBean) {
        TradeHandler tradeHandler = tradeHandlers.get(createOrderBean.getBusinessType());
        Order order = null;
        try {
            order = tradeHandler.createOrder(createOrderBean);
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        }
        return order.getCode();
    }

    @Override
    public PayResultBean createPayment(CreatePaymentBean createPaymentBean) {
        TradeHandler tradeHandler = tradeHandlers.get(createPaymentBean.getBusinessType());
        PayResultBean result = null;
        try {
            result = tradeHandler.createPayment(createPaymentBean);
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public void complete(Map<String, String> requestParameters) {
        TradeHandler tradeHandler = tradeHandlers.get(BusinessType.valueOf(requestParameters.get("businessType")));
        TradeContext tradeContext = new TradeContext();
        tradeContext.setRequestParameters(requestParameters);
        try {
            tradeHandler.complete(tradeContext);
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Order queryOrderBy(String interfaceRequestID) {
        return tradeOrderService.queryByInterfaceRequestID(interfaceRequestID);
    }

    @Override
    public Order queryOrderBy(String partnerCode, String requestCode) {
        return tradeOrderService.queryByRequestCode(partnerCode, requestCode);
    }

    @Override
    public Order queryOrderByCode(String orderCode) {
        return tradeOrderService.queryByCode(orderCode);
    }

    @Override
    public CustomerConfig queryConfig(String customerNo, String payType) {
        return customerConfigDao.findByCustomerNo(customerNo, payType);
    }

    @Override
    public Double orderAmountSum(Date orderTime, String receiver, String payType) {
        double i = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            Map<String, Double> list = tradeOrderDao.orderAmountSum(sdf.format(DateUtils.getMinTime(orderTime)), sdf.format(DateUtils.getMaxTime(orderTime)), receiver, payType);
            if (list != null && list.size() > 0) {
                i = AmountUtils.round(list.get("amount"), 2, RoundingMode.HALF_UP);
            }
            return i;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Payment queryLastPayment(String orderCode) {
        return paymentService.queryLastPayment(orderCode);
    }

}