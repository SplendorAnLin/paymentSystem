package com.yl.payinterface.core.dao;

import com.yl.payinterface.core.model.AliPayCollectionCode;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 支付宝个人码数据访问接口
 * 
 * @author AnLin
 * @version V1.0.0
 * @since 2018/8/15
 */
public interface AliPayCollectionCodeDao {
    
    /**
     * 新增个人码
     * @param aliPayCollectionCode
     */
    void save(AliPayCollectionCode aliPayCollectionCode);

    /**
     * 更新个人码
     * @param aliPayCollectionCode
     */
    void update(@Param("aliPayCollectionCode") AliPayCollectionCode aliPayCollectionCode);

    /**
     * 根据金额获取收款码
     * @param amount
     * @return
     */
    List<AliPayCollectionCode> queryCode(@Param("amount")String amount);

    /**
     * 根据收款码账号修改状态
     * @param aliPayAcc
     * @param status
     */
    void updateStatus(@Param("aliPayAcc") String aliPayAcc, @Param("status") String status);
}