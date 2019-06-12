package com.yl.chat.service.impl;

import com.yl.chat.service.AppInteractionService;
import com.yl.chat.utils.ConnectionUtils;
import org.springframework.stereotype.Service;
import java.util.Map;

/**
 * APP 数据交互实现
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/11/23
 */
@Service("appInteractionService")
public class AppInteractionServiceImpl implements AppInteractionService {

    @Override
    public String appLogin(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "001");
    }

    @Override
    public String loginOut(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "0001");
    }

    @Override
    public String queryCustomerInfo(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "003");
    }

    @Override
    public String drawCash(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "004");
    }

    @Override
    public String findBalance(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "005");
    }

    @Override
    public String getLoginInfo(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "006");
    }

    @Override
    public String today(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "007");
    }

    @Override
    public String recharge(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "008");
    }

    @Override
    public String selectRequest(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "009");
    }

    @Override
    public String selectTradeOrder(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "010");
    }

    @Override
    public String findAccountChange(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "011");
    }

    @Override
    public String appUpdatePassword(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "012");
    }

    @Override
    public String selectRequestDetailed(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "013");
    }

    @Override
    public String getCustomerSettle(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "0008");
    }

    @Override
    public String getCustomerFeeList(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "0009");
    }

    @Override
    public String getQRcode(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "0011");
    }

    @Override
    public String getProtolAll(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "0012");
    }

    @Override
    public String userFeedbackAdd(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "0014");
    }

    @Override
    public String weeklySales(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "014");
    }

    @Override
    public String selectTradeOrderDetailed(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "015");
    }

    @Override
    public String goDrawCash(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "016");
    }

    @Override
    public String barcodePay(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "017");
    }

    @Override
    public String addTansCard(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "020");
    }

    @Override
    public String unlockTansCard(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "021");
    }

    @Override
    public String findByCustNo(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "022");
    }

    @Override
    public String checkTransCard(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "023");
    }

    @Override
    public String authpay(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "024");
    }

    @Override
    public String getDevices(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "025");
    }

    @Override
    public String saveCustImg(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "026");
    }

    @Override
    public String resetPwd(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "027");
    }

    @Override
    public String getVerifyCode(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "028");
    }

    @Override
    public String quick(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "029");
    }

    @Override
    public String initialization(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "030");
    }

    @Override
    public String appApplication(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "031");
    }

    @Override
    public String appDevice(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "032");
    }

    @Override
    public String getDevicesInfo(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "033");
    }

    @Override
    public String queryCustomerStatus(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "034");
    }

    @Override
    public String custSettleCard(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "035");
    }

    @Override
    public String updateCreateSettle(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "036");
    }

    @Override
    public String querySettle(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "037");
    }

    @Override
    public String updateCustomerBaseInfo(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "038");
    }

    @Override
    public String findBankCustomerByPage(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "039");
    }

    @Override
    public String findMcc(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "040");
    }

    @Override
    public String findProvince(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "041");
    }

    @Override
    public String findCityByProvince(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "042");
    }

    @Override
    public String followBankCustomer(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "043");
    }

    @Override
    public String wechatInfo(Map<String, Object> params) {
        return ConnectionUtils.sendReq(params.get("userName").toString(), params, "044");
    }
}