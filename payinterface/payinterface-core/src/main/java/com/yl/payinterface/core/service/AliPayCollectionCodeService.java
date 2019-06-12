package com.yl.payinterface.core.service;

import com.yl.payinterface.core.model.AliPayCollectionCode;
import java.util.List;

/**
 * 支付宝个人码服务接口
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/8/15
 */
public interface AliPayCollectionCodeService {

    /**
     * 新增个人码
     * @param aliPayCollectionCode
     */
    void save(AliPayCollectionCode aliPayCollectionCode);

    /**
     * 更新个人码
     * @param aliPayCollectionCode
     */
    void update(AliPayCollectionCode aliPayCollectionCode);

    /**
     * 根据金额获取收款码
     * @param amount
     * @return
     */
    List<AliPayCollectionCode> queryCode(String amount);

    /**
     * 根据金额获取匹配的收款码
     * @param amount
     * @return
     */
    AliPayCollectionCode getCodeByAmt(String amount);

    /**
     * 更新个人码状态
     * @param aliPayAcc
     * @param status
     */
    void updateAliPayStatus(String aliPayAcc, String status);
}