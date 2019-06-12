package com.yl.rate.core.service;

import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.rate.core.entity.RateTemplate;
import org.apache.ibatis.annotations.Param;

public interface RateTemplateService{

    int insert(RateTemplate rateTemplate);


    /**
     * 查询费率模板
     * @param code
     * @param productType
     * @return
     */
    RateTemplate queryRateTemplate(String code, String productType);

    /**
     * 查询默认费率模板
     * @param productType
     * @param ownerRole
     * @return
     */
    RateTemplate queryDefaultRateTemplate(String productType, String ownerRole);

    /**
     * 分页查询费率模板
     * @param params
     * @param page
     * @return
     */
    Page findAllRateTemplate(Map<String, Object> params, Page page);

    /**
     * 费率模板新增
     * @param params
     */
    void rateTemplateAdd(Map<String, Object> params) throws Exception;

    /**
     * 根据费率模板编号查询模板关联详情
     * @param code
     * @return
     */
    Map<String, Object> queryRateTemplateDetails(String code);

    /**
     * 角色 产品类型 模式 获取模板信息
     * @param ownerRole
     * @param productType
     * @return
     */
    List<Map<String, Object>> getTemplateInfo(String ownerRole, String productType);

    /**
     * 修改费率模板
     * @param params
     * @return
     */
    void rateTemplateUpdate(Map<String, Object> params) throws Exception;

    /**
     * 效验当前产品类型是否存在默认模板
     * @param productType
     * @return
     */
    boolean checkDefaultRate(String productType, String ownerRole);

    /**
     * 根据编号校验当前是否存在
     * @param code
     * @return
     */
    boolean checkRateByCode(String code);

}
