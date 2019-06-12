package com.yl.risk.core.dao;

import com.lefu.commons.utils.Page;
import com.yl.risk.core.entity.RiskResponseMessage;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface RiskResponseMessageDao {
    /**
     * 新增风控响应信息
     * @param riskResponseMessage
     */
    void create(RiskResponseMessage riskResponseMessage);

    /**
     * 修改风控响应信息
     * @param riskResponseMessage
     */
    void update(RiskResponseMessage riskResponseMessage);

    /**
     * 根据ID查询风控响应信息
     * @param id
     * @return
     */
    RiskResponseMessage findById(@Param("id")Integer id);

    /**
     * 根据code查询风控响应信息
     * @param code
     * @return
     */
    RiskResponseMessage findByCode(@Param("code")String code);

    /**
     * 分页查询风控响应信息
     * @param page
     * @param params
     * @return
     */
    List<RiskResponseMessage> findAllByParams(@Param("page")Page page, @Param("params")Map<String, Object> params);
}