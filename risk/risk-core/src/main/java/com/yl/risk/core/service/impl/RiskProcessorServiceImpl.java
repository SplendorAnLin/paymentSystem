package com.yl.risk.core.service.impl;

import com.lefu.commons.utils.Page;
import com.yl.risk.core.dao.RiskProcessorDao;
import com.yl.risk.core.entity.RiskProcessor;
import com.yl.risk.core.service.RiskProcessorService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("riskProcessorService")
public class RiskProcessorServiceImpl implements RiskProcessorService {

    @Resource
    private RiskProcessorDao riskProcessorDao;

    /**
     * 新增风险处理
     * @param riskProcessor
     */
    public void create(RiskProcessor riskProcessor) {
        riskProcessor.setOptimistic(0);
        riskProcessorDao.create(riskProcessor);
    }

    /**
     * 修改风险处理
     * @param riskProcessor
     */
    public void update(RiskProcessor riskProcessor) {
        riskProcessorDao.update(riskProcessor);
    }

    /**
     * 根据编号查询风险处理信息
     * @param id
     * @return
     */
    public RiskProcessor findById(Integer id) {
        return riskProcessorDao.findById(id);
    }

    public Page findAllRiskProcessor(Map<String, Object> params, Page page) {
        page.setObject(riskProcessorDao.findAllRiskProcessor(params, page));
        return page;
    }

    public List<Map<String, Object>> queryAllName() {
        return riskProcessorDao.queryAllName();
    }

    public String queryProcessorCode(String responseCode) {
        if(riskProcessorDao.queryProcessorCode(responseCode) > 0){
            return "TRUE";
        }
        return "FALSE";
    }

}