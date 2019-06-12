package com.yl.payinterface.core.remote.hessian.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lefu.hessian.exception.BusinessRuntimeException;
import com.yl.payinterface.core.exception.ExceptionMessages;
import com.yl.payinterface.core.handler.BindCardHandler;
import com.yl.payinterface.core.hessian.BindCardHessianService;
import com.yl.payinterface.core.model.BindCardInfo;
import com.yl.payinterface.core.model.InterfaceInfo;
import com.yl.payinterface.core.service.InterfaceInfoService;
import com.yl.payinterface.core.service.BindCardInfoService;

/**
 * 快捷绑卡远程接口
 * @author xiaojie.zhang
 * @since 20170816
 */
@Service("bindCardHessianService")
public class BindCardHessianServiceImpl implements BindCardHessianService {

    @Resource
    private Map<String, BindCardHandler> bindCardHandlers;
    @Resource
    private InterfaceInfoService interfaceInfoService;
    @Resource
    private BindCardInfoService bindCardInfoService;

    @Override
    public Map<String, String> bindCard(Map<String, String> params) {
        BindCardHandler tradeHandler = bindCardHandlers.get(params.get("interfaceCode"));
        if (tradeHandler == null){
            throw new BusinessRuntimeException(ExceptionMessages.TRADE_PARAMS_NOT_EXISTS);
        }
        // 获取交易配置
        InterfaceInfo interfaceInfo = interfaceInfoService.queryByCode(params.get("interfaceCode"));
        params.put("tradeConfigs", interfaceInfo.getTradeConfigs());
        return tradeHandler.bindCard(params);
    }

    @Override
    public Map<String, String> queryBindCard(Map<String, String> params) {
    	// 先查询认证支付绑卡信息表，如果有存token则直接返回存的token
        if ((!"QUICKPAY_UnionPay-120001".equals(params.get("interfaceCode"))) && (!"QUICKPAY_ChcodePay-420101".equals(params.get("interfaceCode"))) && (!"QUICKPAY_ChcodePay-420102".equals(params.get("interfaceCode")))) {
            BindCardInfo bindCardInfo = bindCardInfoService.findEffective(params.get("cardNo"), params.get("interfaceCode"));
            if (bindCardInfo!= null) {
                params.put("token", bindCardInfo.getToken());
                params.put("responseCode", "00");
                return params;
            }
        }
        BindCardHandler tradeHandler = bindCardHandlers.get(params.get("interfaceCode"));
        if (tradeHandler == null){
            throw new BusinessRuntimeException(ExceptionMessages.TRADE_PARAMS_NOT_EXISTS);
        }
        // 获取交易配置
        InterfaceInfo interfaceInfo = interfaceInfoService.queryByCode(params.get("interfaceCode"));
        params.put("tradeConfigs", interfaceInfo.getTradeConfigs());
        return tradeHandler.queryBindCard(params);
    }
}
