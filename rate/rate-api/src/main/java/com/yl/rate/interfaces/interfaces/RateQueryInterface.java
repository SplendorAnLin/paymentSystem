package com.yl.rate.interfaces.interfaces;

import com.lefu.commons.utils.Page;
import com.yl.rate.interfaces.beans.AddOwnerRateBean;
import com.yl.rate.interfaces.beans.HistoryOwnerRateBean;
import com.yl.rate.interfaces.beans.OwnerRateBean;
import java.util.Map;

/**
 * 费率查询远程服务接口
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/12/12
 */
public interface RateQueryInterface {

    /**
     * 分页查询所有 费率配置
     * @param page
     * @param params
     * @return
     */
    Page findAllFeeConfig(Page page, Map<String, Object> params);

    /**
     * 分页查询所有 费率配置 历史
     * @param page
     * @param code
     * @return
     */
    Page findAllFeeConfigHistory(Page page, String code);

    /**
     * 根据配置编号查询 费率配置
     * @param code
     * @return
     */
    OwnerRateBean findFeeConfigByCode(String code);

    /**
     * 修改所有者费率配置
     * @param ownerRateBean
     * @return
     */
    int update(OwnerRateBean ownerRateBean, String oper);

    /**
     * 新增自定义所有者费率
     * @param addOwnerRateBean
     * @param oper
     * @return
     */
    int createCustomize(AddOwnerRateBean addOwnerRateBean, String oper);

    /**
     * 根据CODE 查询自定义费率信息
     * @param code
     * @return
     */
    AddOwnerRateBean queryByCode(String code);

    /**
     * 修改自定义配置费率信息
     * @param addOwnerRateBean
     * @param oper
     * @return
     */
    int updateCustomize(AddOwnerRateBean addOwnerRateBean, String oper);

    /**
     * 角色 产品类型 模式 获取模板信息
     * @param ownerRole
     * @param productType
     * @return
     */
    String getTemplateInfo(String ownerRole, String productType);

    /**
     * 根据 id 查询自定义费率信息历史
     * @param id
     * @return
     */
    HistoryOwnerRateBean queryByIdHistory(Long id);
}