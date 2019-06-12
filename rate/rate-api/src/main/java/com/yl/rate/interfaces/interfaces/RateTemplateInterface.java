package com.yl.rate.interfaces.interfaces;

import com.lefu.commons.utils.Page;
import com.yl.rate.interfaces.beans.RateTemplateHistoryBean;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 费率模板远程接口
 */
public interface RateTemplateInterface {

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
     * 根据费率模板编号查询详情信息
     * @param code
     * @return
     */
    Map<String, Object> queryRateTemplateDetails(String code);

    void rateTemplateUpdate(Map<String, Object> params) throws Exception;

    Page findAllRateTemplateHistoryByCode(String code, Page page);

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

    Map<String, Object> queryRateTemplateHistoryDetails(Long id);

}
