package com.yl.online.trade.hessian;

import com.yl.online.model.bean.CreateOrderBean;
import com.yl.online.model.bean.CreatePaymentBean;
import com.yl.online.model.bean.PayResultBean;
import com.yl.online.model.bean.Payment;
import com.yl.online.model.model.CustomerConfig;
import com.yl.online.model.model.Order;

import java.util.Date;
import java.util.Map;

/**
 * 在线交易服务接口
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年8月13日
 */
public interface OnlineTradeHessianService {

    /**
     * 创建交易订单
     *
     * @param createOrderBean 创建交易订单参数Bean
     * @return 订单号
     */
    String createOrder(CreateOrderBean createOrderBean);

    /**
     * 创建交易记录
     *
     * @param createPaymentBean 创建交易记录参数Bean
     * @return PayResultBean
     */
    PayResultBean createPayment(CreatePaymentBean createPaymentBean);

    /**
     * 交易完成后处理
     *
     * @param requestParameters 交易完成请求参数
     */
    void complete(Map<String, String> requestParameters);

    /**
     * 根据银行支付流水查询支付订单
     *
     * @param interfaceOrderID 银行支付流水
     * @return Order
     */
    Order queryOrderBy(String interfaceOrderID);

    /**
     * 根据合作方编号、合作方订单号查询订单信息
     *
     * @param partnerCode 合作方编号
     * @param requestCode 合作方订单号
     * @return Order
     */
    Order queryOrderBy(String partnerCode, String requestCode);

    /**
     * 根据支付订单编号查询支付订单实体信息
     *
     * @param orderCode 支付订单编号
     * @return Order
     */
    Order queryOrderByCode(String orderCode);

    /**
     * 查询交易限制
     */
    CustomerConfig queryConfig(String customerNo, String payType);

    /**
     * 查询当日交易额度
     */
    Double orderAmountSum(Date orderTime, String receiver, String payType);

    /**
     * 查询最后一条支付流水
     *
     * @param orderCode
     * @return
     */
    Payment queryLastPayment(String orderCode);

}