package com.yl.rate.core.service.impl;

import com.lefu.commons.utils.Page;
import com.yl.rate.core.dao.OwnerRateMapper;
import com.yl.rate.core.entity.OwnerRate;
import com.yl.rate.core.entity.OwnerRateHistory;
import com.yl.rate.core.service.OwnerRateHistoryService;
import com.yl.rate.core.service.OwnerRateService;
import com.yl.rate.interfaces.beans.OwnerRateBean;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("ownerRateService")
public class OwnerRateServiceImpl implements OwnerRateService{

    @Resource
    private OwnerRateMapper ownerRateMapper;

    @Resource
    private OwnerRateHistoryService ownerRateHistoryService;

    @Override
    public int insert(OwnerRate ownerRate, String oper){
        OwnerRateHistory ownerRateHistory = new OwnerRateHistory(ownerRate, oper);
        ownerRateHistory.setCreateTime(new Date());
        ownerRateHistoryService.insert(ownerRateHistory);
        return ownerRateMapper.insert(ownerRate);
    }

    @Override
    public Long addOwnerRate(OwnerRate ownerRate, String oper) {
        OwnerRateHistory ownerRateHistory = new OwnerRateHistory(ownerRate, oper);
        ownerRateHistory.setCreateTime(new Date());
        ownerRateHistoryService.insert(ownerRateHistory);
        ownerRateMapper.insert(ownerRate);
        return ownerRateHistory.getId();
    }

    @Override
    public Long updateOwnerRate(OwnerRate ownerRate, String oper) {
        OwnerRateHistory ownerRateHistory = new OwnerRateHistory(ownerRate, oper);
        ownerRateHistory.setCreateTime(new Date());
        ownerRateHistoryService.insert(ownerRateHistory);
        ownerRateMapper.update(ownerRate);
        return ownerRateHistory.getId();
    }

    @Override
    public int insertSelective(OwnerRate ownerRate, String oper){
        OwnerRateHistory ownerRateHistory = new OwnerRateHistory(ownerRate, oper);
        ownerRateHistoryService.insert(ownerRateHistory);
        return ownerRateMapper.insertSelective(ownerRate);
    }

    @Override
    public int insertList(List<OwnerRate> ownerRates, String oper){
        for (int i = 0; i < ownerRates.size(); i++) {
            OwnerRateHistory ownerRateHistory = new OwnerRateHistory(ownerRates.get(i), oper);
            ownerRateHistoryService.insert(ownerRateHistory);
        }
        return ownerRateMapper.insertList(ownerRates);
    }

    @Override
    public int update(OwnerRate ownerRate, String oper){
        OwnerRateHistory ownerRateHistory = new OwnerRateHistory(ownerRate, oper);
        ownerRateHistory.setCreateTime(new Date());
        ownerRateHistoryService.insert(ownerRateHistory);
        return ownerRateMapper.update(ownerRate);
    }

    @Override
    public int updateStatus(OwnerRate ownerRate) {
        return ownerRateMapper.update(ownerRate);
    }

    @Override
    public OwnerRate queryByOwnerId(String ownerId, String ownerRole, String productType, String productCode) {
        OwnerRate ownerRate = ownerRateMapper.queryByOwnerId(ownerId,ownerRole,productType);
        if (ownerRate == null) {
            ownerRate = ownerRateMapper.queryByOwnerIdAndProductCode(ownerId,ownerRole,productType,productCode);
        }
        return ownerRate;
    }

    @Override
    public OwnerRate queryByCode(String code) {
        return ownerRateMapper.queryByCode(code);
    }

    @Override
    public Page findAllFeeConfig(Page page, Map<String, Object> params) {
        page.setObject(ownerRateMapper.findAllFeeConfig(page, params));
        return page;
    }
}