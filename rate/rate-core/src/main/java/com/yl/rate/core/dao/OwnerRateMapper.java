package com.yl.rate.core.dao;

import com.lefu.commons.utils.Page;
import com.yl.rate.core.entity.OwnerRate;
import com.yl.rate.interfaces.beans.OwnerRateBean;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface OwnerRateMapper {

    int insert(@Param("ownerRate") OwnerRate ownerRate);

    int insertSelective(@Param("ownerRate") OwnerRate ownerRate);

    int insertList(@Param("ownerRates") List<OwnerRate> ownerRates);

    /**
     * 修改所有者费率配置
     * @param ownerRate
     * @return
     */
    int update(@Param("ownerRate") OwnerRate ownerRate);

    /**
     * 查询商户费率
     *
     * @param ownerId
     * @param ownerRole
     * @param productType
     * @return
     */
    OwnerRate queryByOwnerId(@Param("ownerId") String ownerId, @Param("ownerRole") String ownerRole, @Param("productType") String productType);

    /**
     * 查询商户费率
     * @param ownerId
     * @param ownerRole
     * @param productType
     * @param productCode
     * @return
     */
    OwnerRate queryByOwnerIdAndProductCode(@Param("ownerId") String ownerId, @Param("ownerRole") String ownerRole,
                                           @Param("productType") String productType, @Param("productCode") String productCode);

    /**
     * 根据编号查询费率信息
     * @param code
     * @return
     */
    OwnerRate queryByCode(@Param("code")String code);

    /**
     * 分页查询所有 费率配置
     * @param page
     * @param params
     * @return
     */
    List<Map<String, Object>> findAllFeeConfig(@Param("page")Page page, @Param("params")Map<String, Object> params);
}