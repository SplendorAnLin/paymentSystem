package com.yl.rate.core.dao;

import com.lefu.commons.utils.Page;
import com.yl.rate.core.entity.OwnerRateHistory;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface OwnerRateHistoryMapper {

    /**
     * 新增历史记录
     * @param ownerRateHistory
     * @return
     */
    int insert(@Param("ownerRateHistory")OwnerRateHistory ownerRateHistory);

    /**
     * 分页查询所有 费率配置 历史
     * @param page
     * @param code
     * @return
     */
    List<Map<String, Object>> findAllFeeConfigHistory(@Param("page")Page page, @Param("code")String code);

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
    int updateStatus(@Param("ownerRateHistory")OwnerRateHistory ownerRateHistory);
}