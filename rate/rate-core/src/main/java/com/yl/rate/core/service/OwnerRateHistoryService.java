package com.yl.rate.core.service;

import com.lefu.commons.utils.Page;
import com.yl.rate.core.entity.OwnerRateHistory;
import java.util.List;
import java.util.Map;

/**
 * 费率信息历史记录访问接口
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/12/12
 */
public interface OwnerRateHistoryService {

    /**
     * 新增历史记录
     * @param ownerRateHistory
     * @return
     */
    int insert(OwnerRateHistory ownerRateHistory);

    /**
     * 分页查询所有 费率配置 历史
     * @param page
     * @param code
     * @return
     */
    Page findAllFeeConfigHistory(Page page, String code);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    OwnerRateHistory queryById(Long id);

    /**
     * 修改所有者费率历史
     * @param ownerRateHistory
     * @return
     */
    int updateStatus(OwnerRateHistory ownerRateHistory);
}