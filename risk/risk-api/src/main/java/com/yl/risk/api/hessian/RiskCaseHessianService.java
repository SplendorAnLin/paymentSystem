package com.yl.risk.api.hessian;

import com.lefu.commons.utils.Page;
import com.yl.risk.api.bean.RiskCase;
import java.util.List;
import java.util.Map;

public interface RiskCaseHessianService {

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    RiskCase findById(int id);

    /**
     * 查询符合条件的所有信息
     * @param params
     * @return
     */
    Page findAllRiskCase(Map<String, Object> params, Page page);

    /**
     * 根据条件修改
     * @param riskCase
     */
    void modify(RiskCase riskCase);

    /**
     * 新增
     * @param riskCase
     */
    void insert(RiskCase riskCase);


}
