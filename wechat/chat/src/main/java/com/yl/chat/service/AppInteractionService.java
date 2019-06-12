package com.yl.chat.service;

import com.lefu.commons.utils.lang.StringUtils;

import java.util.Map;

/**
 * APP 数据交互访问接口
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/11/23
 */
public interface AppInteractionService {

    String appLogin(Map<String, Object> params);

    String loginOut(Map<String, Object> params);

    String queryCustomerInfo(Map<String, Object> params);

    String drawCash(Map<String, Object> params);

    String findBalance(Map<String, Object> params);

    String getLoginInfo(Map<String, Object> params);

    String today(Map<String, Object> params);

    String recharge(Map<String, Object> params);

    String selectRequest(Map<String, Object> params);

    String selectTradeOrder(Map<String, Object> params);

    String findAccountChange(Map<String, Object> params);

    String appUpdatePassword(Map<String, Object> params);

    String selectRequestDetailed(Map<String, Object> params);

    String getCustomerSettle(Map<String, Object> params);

    String getCustomerFeeList(Map<String, Object> params);

    String getQRcode(Map<String, Object> params);

    String getProtolAll(Map<String, Object> params);

    String userFeedbackAdd(Map<String, Object> params);

    String weeklySales(Map<String, Object> params);

    String selectTradeOrderDetailed(Map<String, Object> params);

    String goDrawCash(Map<String, Object> params);

    String barcodePay(Map<String, Object> params);

    String addTansCard(Map<String, Object> params);

    String unlockTansCard(Map<String, Object> params);

    String findByCustNo(Map<String, Object> params);

    String checkTransCard(Map<String, Object> params);

    String authpay(Map<String, Object> params);

    String getDevices(Map<String, Object> params);

    String saveCustImg(Map<String, Object> params);

    String resetPwd(Map<String, Object> params);

    String getVerifyCode(Map<String, Object> params);

    String quick(Map<String, Object> params);

    String initialization(Map<String, Object> params);

    String appApplication(Map<String, Object> params);

    String appDevice(Map<String, Object> params);

    String getDevicesInfo(Map<String, Object> params);

    String queryCustomerStatus(Map<String, Object> params);

    String custSettleCard(Map<String, Object> params);

    String updateCreateSettle(Map<String, Object> params);

    String querySettle(Map<String, Object> params);

    String updateCustomerBaseInfo(Map<String, Object> params);

    String findBankCustomerByPage(Map<String, Object> params);

    String findMcc(Map<String, Object> params);

    String findProvince(Map<String, Object> params);

    String findCityByProvince(Map<String, Object> params);

    String followBankCustomer(Map<String, Object> params);

    String wechatInfo(Map<String, Object> params);
}