package com.yl.rate.core.dao;


import com.lefu.commons.utils.Page;
import com.yl.rate.core.entity.RateTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface RateTemplateMapper {
    int insert(@Param("rateTemplate") RateTemplate rateTemplate);

    /**
     * 查询费率模板
     *
     * @param code
     * @param productType
     * @return
     */
    RateTemplate queryRateTemplate(@Param("code") String code, @Param("productType") String productType);

    /**
     * 查询默认费率模板
     *
     * @param productType
     * @param ownerRole
     * @return
     */
    RateTemplate queryDefaultRateTemplate(@Param("productType") String productType, @Param("ownerRole") String ownerRole);

    /**
     * 分页查询费率模板
     * @param params
     * @param page
     * @return
     */
    List<RateTemplate> findAllRateTemplate(@Param("params") Map<String, Object> params, @Param("page") Page page);

    /**
     * 根据编号校验当前是否存在
     * @param code
     * @return
     */
    int checkRateByCode(@Param("code") String code);

    RateTemplate queryRateTemplateDetails(@Param("code") String code);

    /**
     * 角色 产品类型 模式 获取模板信息
     * @param ownerRole
     * @param productType
     * @return
     */
    List<Map<String, Object>> getTemplateInfo(@Param("ownerRole")String ownerRole, @Param("productType")String productType);

    void update(@Param("rateTemplate")RateTemplate rateTemplate);

    int checkDefaultRate(@Param("productType") String productType, @Param("ownerRole") String ownerRole);

    void defaultRateUpdate(@Param("productType") String productType, @Param("ownerRole") String ownerRole, @Param("status") String status);

}