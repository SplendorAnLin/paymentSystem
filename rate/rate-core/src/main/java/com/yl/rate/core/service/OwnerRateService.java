package com.yl.rate.core.service;

import java.util.List;
import java.util.Map;
import com.lefu.commons.utils.Page;
import com.yl.rate.core.entity.OwnerRate;
import com.yl.rate.interfaces.beans.OwnerRateBean;

public interface OwnerRateService{

    int insert(OwnerRate ownerRate, String oper);

    int insertSelective(OwnerRate ownerRate, String oper);

    int insertList(List<OwnerRate> ownerRates, String oper);

    int update(OwnerRate ownerRate, String oper);

    /**
     * 根据所有者Id查询费率信息
     * @param ownerId 角色id
     * @param ownerRole 角色
     * @param productType 产品类型
     * @param productCode 接口编号
     * @return 所有者费率
     */
    OwnerRate queryByOwnerId (String ownerId, String ownerRole, String productType, String productCode);

    /**
     * 根据编号查询费率信息
     * @param code
     * @return
     */
    OwnerRate queryByCode(String code);

    /**
     * 分页查询所有 费率配置
     * @param page
     * @param params
     * @return
     */
    Page findAllFeeConfig(Page page, Map<String, Object> params);

    /**
     * 修改状态 内部调用不新增历史
     * @param ownerRate
     * @return
     */
    int updateStatus(OwnerRate ownerRate);

    /**
     * 新增 费率信息
     * @param ownerRate
     * @param oper
     * @return 历史记录ID
     */
    Long addOwnerRate(OwnerRate ownerRate, String oper);

    /**
     * 修改 费率信息
     * @param ownerRate
     * @param oper
     * @return 历史记录ID
     */
    Long updateOwnerRate(OwnerRate ownerRate, String oper);
}